package skieg.travel.post;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import skieg.travel.CalendarEventActivity;
import skieg.travel.DatabaseParse;
import skieg.travel.InputValidation;
import skieg.travel.PersonalProfileActivity;
import skieg.travel.R;

public class PostActivity extends AppCompatActivity {

    PostAdapter PDAdapter;
    PostFragment postFragment;
    DatabaseReference db;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> posts = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> postIds = new ArrayList<>();
    ArrayList<String> countries = new ArrayList<>();
    String currentCountry = null;
    ImageView currentCountryFlag;
    private final String countryFlagsAPI = "https://countryflagsapi.com/png/"; // the country name has to follow the final "/"
    Spinner countrySpin;
    private final String countriesAPI = "https://restcountries.com/v2/all?fields=name";
    ArrayAdapter<String> countryAdapter;

    /**
     * On create method for when the activity is first created.
     *
     * @param savedInstanceState a bundle holding the saved instance data from the previous activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Button postButton = findViewById(R.id.makePost);
        LinearLayout userInfo = findViewById(R.id.userInfo);
        LinearLayout userImageContainer = findViewById(R.id.userPhoto);

        postButton.setBackgroundColor(Color.parseColor("#BEDEFC"));
        userInfo.setBackgroundColor(Color.parseColor("#BEDEFC"));
        userImageContainer.setBackgroundColor(Color.parseColor("#BEDEFC"));

        postFragment = new PostFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.recyclerPosts, postFragment);
        fragmentTransaction.commit();
        getDataFromFirebase();

        DatabaseReference db2 = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Forum").child("posts");

        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        countrySpin = findViewById(R.id.countries);
        currentCountryFlag = findViewById(R.id.flag);
        readCountryAPI();
        canadaFlag();

        // listener for when a new country is selected.
        countrySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Event listener for when an item is selected in the country spinner.
             *
             * @param parent an adapter view parent.
             * @param view a view.
             * @param position the position in the adapter.
             * @param id the id of the item.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSpinnerItem = parent.getItemAtPosition(position).toString();
                currentCountry = selectedSpinnerItem;
                DownloadImageTask dtsk = new DownloadImageTask(currentCountryFlag);

                dtsk.execute(countryFlagsAPI + selectedSpinnerItem);
                if (!selectedSpinnerItem.equals("Choose Country")) {
                    getDataFromFirebase(selectedSpinnerItem);
                    PDAdapter = new PostAdapter(names, posts, dates, ids, postIds, countries);
                    postFragment.initializeAdapter(PDAdapter);
                }
            }

            /**
             * for when nothing is selected in the adapter.
             *
             * @param adapterView an adapter view.
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        db.addValueEventListener(new ValueEventListener() {
            /**
             * listener for when data changes in the our database.
             *
             * @param snapshot a snapshot of our database reference.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("DATA HAS BEEN CHANGED");
                PDAdapter = null;

                names = new ArrayList<>();
                posts = new ArrayList<>();
                dates = new ArrayList<>();
                ids = new ArrayList<>();
                postIds = new ArrayList<>();
                countries = new ArrayList<>();

                countrySpin.setSelection(0);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    try {
                        Gson gson = new Gson();
                        String json = gson.toJson(dataSnapshot.getValue());
                        JSONObject ob = new JSONObject(json);
                        String date = ob.getString("date");
                        String country = ob.getString("country");
                        String content = ob.getString("information");
                        String postID = ob.getString("postID");
                        String userID = ob.getString("userID");
                        String username = ob.getString("username");
                        names.add(username);
                        dates.add(date);
                        posts.add(content);
                        ids.add(userID);
                        postIds.add(postID);
                        countries.add(country);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                PDAdapter = new PostAdapter(names, posts, dates, ids, postIds, countries);
                postFragment.initializeAdapter(PDAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        Button profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, PersonalProfileActivity.class);
            startActivity(intent);
        });

        Button eventsBtn = findViewById(R.id.eventsBtn);
        eventsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, CalendarEventActivity.class);

            // Set default date to today
            LocalDate currentDate = LocalDate.now();
            String selectedDate = currentDate.getYear() + "-" + InputValidation.makeValidDateValue(currentDate.getMonthValue()) + "-" + InputValidation.makeValidDateValue(currentDate.getDayOfMonth());

            System.out.println("DATE: " + selectedDate);

            Bundle bundle = new Bundle();
            bundle.putString("selectedDate", selectedDate);
            intent.putExtras(bundle);

            startActivity(intent);
        });
    }

    private void canadaFlag() {
        DownloadImageTask flagRunner = new DownloadImageTask(currentCountryFlag);
        flagRunner.execute("canada");
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void readCountryAPI() {
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(countriesAPI);
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(PostActivity.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    countryAdapter.add("Choose Country");
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject arr = response.getJSONObject(i);
                            String countryName = (String) arr.get("name");
                            countryAdapter.add(countryName);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    countrySpin.setAdapter(countryAdapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("ERROR: " + error);
                    Toast.makeText(PostActivity.this, "Error Getting Countries", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
            return null;
        }
    }


    private void getDataFromFirebase(String countryVal) {
        // all the posts in line 1
        names = new ArrayList<>();
        posts = new ArrayList<>();
        dates = new ArrayList<>();
        ids = new ArrayList<>();
        postIds = new ArrayList<>();
        countries = new ArrayList<>();

        db = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Forum").child("posts");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        Gson gson = new Gson();
                        String json = gson.toJson(dataSnapshot.getValue());
                        JSONObject ob = new JSONObject(json);
                        String date = ob.getString("date");
                        String country = ob.getString("country");
                        String content = ob.getString("information");
                        String postID = ob.getString("postID");
                        String userID = ob.getString("userID");
                        String username = ob.getString("username");

                        if (country.equals(countryVal)) {
                            names.add(username);
                            dates.add(date);
                            posts.add(content);
                            ids.add(userID);
                            postIds.add(postID);
                            countries.add(country);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                PDAdapter = new PostAdapter(names, posts, dates, ids, postIds, countries);
                postFragment.initializeAdapter(PDAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LOG", "DB ERROR");
            }
        });


    }


    private void getDataFromFirebase() {
        System.out.println("CALLING FIREBASE");
        System.out.println("CALLING FIREBASE");
        // all the posts in line 1
        names = new ArrayList<>();
        posts = new ArrayList<>();
        dates = new ArrayList<>();
        ids = new ArrayList<>();
        postIds = new ArrayList<>();
        countries = new ArrayList<>();

        db = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Forum").child("posts");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    try {
                        Gson gson = new Gson();
                        String json = gson.toJson(dataSnapshot.getValue());
                        JSONObject ob = new JSONObject(json);
                        String date = ob.getString("date");
                        String country = ob.getString("country");
                        String content = ob.getString("information");
                        String postID = ob.getString("postID");
                        String userID = ob.getString("userID");
                        String username = ob.getString("username");
                        names.add(username);
                        dates.add(date);
                        posts.add(content);
                        ids.add(userID);
                        postIds.add(postID);
                        countries.add(country);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                PDAdapter = new PostAdapter(names, posts, dates, ids, postIds, countries);
                postFragment.initializeAdapter(PDAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LOG", "DB ERROR");
            }
        });
    }

    public void makePost(View view) {
        System.out.println("CURRENT CITY: " + currentCountry);
        if (currentCountry == "Choose Country") {
            Toast.makeText(PostActivity.this, "Please choose a country to post to.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(PostActivity.this, PostPage.class);
            intent.putExtra("country", currentCountry);
            startActivity(intent);
        }
    }


    public void getPostsByCountry() {

    }
}
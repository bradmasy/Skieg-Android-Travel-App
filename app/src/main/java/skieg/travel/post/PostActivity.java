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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.AsyncTaskLoader;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import skieg.travel.CalendarEventActivity;
import skieg.travel.DatabaseParse;
import skieg.travel.InputValidation;
import skieg.travel.MainActivity;
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
    ImageView currentCountryFlag;
    Drawable current;
    private final String countryFlagsAPI = "https://countryflagsapi.com/png/"; // the country name has to follow the final "/"
    String endURL = ":filetype/:code";
    Spinner countrySpin;
    private final String countriesAPI = "https://restcountries.com/v2/all?fields=name";
    ArrayAdapter<String> countryAdapter;

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

        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        countrySpin = findViewById(R.id.countries);
        currentCountryFlag = findViewById(R.id.flag);




        readCountryAPI();
        canadaFlag();
        // Create a listener every time a spinner item is selected
        countrySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Override the onItemSelected method defined in AdapterView.OnItemSelectedListener
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Store the selected item's value in a string
                String selectedSpinnerItem = parent.getItemAtPosition(position).toString();
               // chooseCountry(selectedSpinnerItem);
                DownloadImageTask dtsk = new DownloadImageTask(currentCountryFlag);
                dtsk.execute(countryFlagsAPI + selectedSpinnerItem);
            }
            // Override the onNothingSelected method defined in AdapterView.OnItemSelectedListener
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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

    private void canadaFlag(){
        DownloadImageTask flagRunner = new DownloadImageTask(currentCountryFlag);
        flagRunner.execute(countryFlagsAPI);
    }

    private void setFirstCountryFlag() throws IOException {
        URL url = new URL(countryFlagsAPI + "canada");
        System.out.println("URL: " + url);
        InputStream is = (InputStream) url.getContent();
        current  = Drawable.createFromStream(is, "canada");
        System.out.println(current);
    }

    private void chooseCountry(String item) throws MalformedURLException {
        URL url = new URL(countryFlagsAPI + item);
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

    private class AsyncTaskRunnerFlag extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            System.out.println(Arrays.toString(strings));
            try {
                setFirstCountryFlag();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
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

    private void getDataFromFirebase() {
        // all the posts in line 1
        names = new ArrayList<>();
        posts = new ArrayList<>();
        dates = new ArrayList<>();
        ids = new ArrayList<>();
        postIds = new ArrayList<>();
        db = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Forum").child("posts");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String currSnapshot = String.valueOf(dataSnapshot.getValue());
                    String[] dataValues = currSnapshot.split(",");
                    String date = DatabaseParse.parseDataValue(dataValues[0]);
                    String content = DatabaseParse.parseDataValue(dataValues[1]);
                    String postID = DatabaseParse.parseDataValue(dataValues[2]);
                    String userID = DatabaseParse.parseDataValue(dataValues[3]);
                    String username = DatabaseParse.parseLastDataValue(dataValues[4]);

                    names.add(username);
                    dates.add(date);
                    posts.add(content);
                    ids.add(userID);
                    postIds.add(postID);
                }

                PDAdapter = new PostAdapter(names, posts, dates, ids, postIds);
                postFragment.initializeAdapter(PDAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LOG", "DB ERROR");
            }
        });
    }

    public void makePost(View view) {
        Intent intent = new Intent(PostActivity.this, PostPage.class);
        startActivity(intent);
    }
}
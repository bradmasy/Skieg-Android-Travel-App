package skieg.travel;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import skieg.travel.planner.fragments.MainPlanner;
import skieg.travel.post.PostActivity;
import skieg.travel.user.User;

public class MainActivity extends AppCompatActivity {

    public static User USER;

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "fa211ad253385ab5e5f303af6dfebb44";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        // Only set the current user when directed to main page from the login/signup pages
        if (bundle != null) {
            String id = bundle.getString("id");
            String username = bundle.getString("username");
            String password = bundle.getString("password");

            String firstName = bundle.getString("firstName");
            String lastName = bundle.getString("lastName");
            String city = bundle.getString("city");
            String email = bundle.getString("email");

            USER = new User(id, firstName, lastName, city, username, email, password);
            Log.d("MAIN", USER.toString());
        }

        String cityName = USER.getCity();
        System.out.println(cityName);
        String tempUrl = url + "?q=" + cityName + "&appid=" + appid;

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(tempUrl);

    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObjectMain = response.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelslike = jsonObjectMain.getDouble("feels_like") - 273.15;

                        JSONArray weather = response.getJSONArray("weather");
                        JSONObject jsonObjectWeather = weather.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");

                        JSONObject jsonObjectSys = response.getJSONObject("sys");
                        String currentCountry = jsonObjectSys.getString("country");
                        String currentCity = response.getString("name");

                        // Get Icon
                        String currentIcon = jsonObjectWeather.getString("icon");
                        ImageView imageView = findViewById(R.id.weatherIcon);

                        TextView city = findViewById(R.id.city);
                        TextView temperature = findViewById(R.id.temperature);
                        TextView feels = findViewById(R.id.feelslike);
                        TextView weatherDescription = findViewById(R.id.description);

                        String cityAndCountry = currentCity + ", " + currentCountry;
                        String temperatureValue = df.format(temp) + " \u2103";
                        String feelsLikeValue = df.format(feelslike) + " \u2103";
                        city.setText(cityAndCountry);
                        temperature.setText(temperatureValue);
                        feels.setText(feelsLikeValue);
                        weatherDescription.setText(description);

                        Toast.makeText(MainActivity.this, currentIcon, Toast.LENGTH_SHORT).show();

                        // Determine which Icon to use
                        if (currentIcon.equalsIgnoreCase("01d") || currentIcon.equalsIgnoreCase("01n")){
                            imageView.setImageResource(R.drawable.drawable_weather_sunny_01d);
                        }
                        if (currentIcon.equalsIgnoreCase("02d")|| currentIcon.equalsIgnoreCase("02n")){
                            imageView.setImageResource(R.drawable.drawable_weather_fewclouds_02d);
                        }
                        if (currentIcon.equalsIgnoreCase("03d")|| currentIcon.equalsIgnoreCase("03n")){
                            imageView.setImageResource(R.drawable.drawable_weather_scatteredclouds_03d);
                        }
                        if (currentIcon.equalsIgnoreCase("04d")|| currentIcon.equalsIgnoreCase("04n")){
                            imageView.setImageResource(R.drawable.drawable_weather_brokenclouds_04d);
                        }
                        if (currentIcon.equalsIgnoreCase("09d")|| currentIcon.equalsIgnoreCase("09n")){
                            imageView.setImageResource(R.drawable.drawable_weather_showerrain_09d);
                        }
                        if (currentIcon.equalsIgnoreCase("10d")|| currentIcon.equalsIgnoreCase("10n")){
                            // Same as Rain, 09d
                            imageView.setImageResource(R.drawable.drawable_weather_showerrain_09d);
                        }
                        if (currentIcon.equalsIgnoreCase("11d")|| currentIcon.equalsIgnoreCase("11n")){
                            imageView.setImageResource(R.drawable.drawable_weather_thunderstorm_11d);
                        }
                        if (currentIcon.equalsIgnoreCase("13d")|| currentIcon.equalsIgnoreCase("13n")){
                            imageView.setImageResource(R.drawable.drawable_weather_snow_13d);
                        }
                        if (currentIcon.equalsIgnoreCase("50d")|| currentIcon.equalsIgnoreCase("50n")){
                            imageView.setImageResource(R.drawable.drawable_weather_mist_50d);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error getting the weather", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
            return null;
        }
    }

    public void clickMainEventsButton(View view) {
       Intent intent = new Intent(this, EventsActivity.class);

       // Set background initially invisible
       view.getBackground().setAlpha(0);

       // Set Background
       view.setBackgroundResource(R.drawable.drawable_button_ripple);

       startActivity(intent);
    }

    public void clickMainPlannerButton(View view) {
        Intent intent = new Intent(this, MainPlanner.class);

        // Set background initially invisible
        view.getBackground().setAlpha(0);

        // Set Background
        view.setBackgroundResource(R.drawable.drawable_button_ripple);

        startActivity(intent);
    }

    public void clickMainCalendarButton(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);

        // Set background initially invisible
        view.getBackground().setAlpha(0);

        // Set Background
        view.setBackgroundResource(R.drawable.drawable_button_ripple);

        startActivity(intent);
    }

    public void clickMainPersonalProfileButton(View view) {
        Intent intent = new Intent(this, PersonalProfileActivity.class);

        // Set background initially invisible
        view.getBackground().setAlpha(0);

        // Set Background
        view.setBackgroundResource(R.drawable.drawable_button_ripple);

        startActivity(intent);
    }

    public void clickMainPostButton(View view) {
        Intent intent = new Intent(this, PostActivity.class);

        // Set background initially invisible
        view.getBackground().setAlpha(0);

        // Set Background
        view.setBackgroundResource(R.drawable.drawable_button_ripple);

        startActivity(intent);
    }

    public void clickMapsButton(View view){
        Intent intent = new Intent(this, MapsActivity.class);

        // Set background initially invisible
        view.getBackground().setAlpha(0);

        // Set Background
        view.setBackgroundResource(R.drawable.drawable_button_ripple);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // Override the default behaviour to disable going back from this page.
        // Do nothing...
    }
}
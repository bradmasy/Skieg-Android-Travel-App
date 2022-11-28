package skieg.travel;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

    // Stores the current user logged in
    public static User USER;

    // Store URL references for the weather API
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "fa211ad253385ab5e5f303af6dfebb44";

    // Decimal formatter for double numbers (weather section)
    DecimalFormat df = new DecimalFormat("#.##");

    /**
     * When main page is loaded.
     * Store the logged in user if there is no current user saved.
     * @param savedInstanceState: bundle, if directed to the page from login or signup,
     *                          bundle contains the logged in user's information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the bundle holds values from a previous page
        Bundle bundle = getIntent().getExtras();
        // Only set the current user when directed to main page from the login/signup pages
        if (bundle != null) {
            // Get all user's attributes
            String id = bundle.getString("id");
            String username = bundle.getString("username");
            String password = bundle.getString("password");
            String firstName = bundle.getString("firstName");
            String lastName = bundle.getString("lastName");
            String city = bundle.getString("city");
            String email = bundle.getString("email");

            // Store all the values into the static user object so it can be accessed in other pages
            USER = new User(id, firstName, lastName, city, username, email, password);
        }

        // Get the user's city and format it in the weather API URL
        String cityName = USER.getCity();
        String tempUrl = url + "?q=" + cityName + "&appid=" + appid;

        // Use async task runner to fetch the weather information for the user's city
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(tempUrl);

    }

    /**
     * Private async task runner class to fetch information from the weather API.
     */
    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        // Use the JSON response to get the values for weather in the user's city
                        JSONObject jsonObjectMain = response.getJSONObject("main");

                        // Get temperature values
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelslike = jsonObjectMain.getDouble("feels_like") - 273.15;

                        // Get weather description values
                        JSONArray weather = response.getJSONArray("weather");
                        JSONObject jsonObjectWeather = weather.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");

                        // Get location values (city, country)
                        JSONObject jsonObjectSys = response.getJSONObject("sys");
                        String currentCountry = jsonObjectSys.getString("country");
                        String currentCity = response.getString("name");

                        // Get Icon
                        String currentIcon = jsonObjectWeather.getString("icon");
                        ImageView imageView = findViewById(R.id.weatherIcon);

                        // Get text view objects from the XML layout
                        TextView city = findViewById(R.id.city);
                        TextView temperature = findViewById(R.id.temperature);
                        TextView feels = findViewById(R.id.feelslike);
                        TextView weatherDescription = findViewById(R.id.description);

                        // Set text view values to the JSON response values with units (ex. degrees)
                        String cityAndCountry = currentCity + ", " + currentCountry;
                        String temperatureValue = df.format(temp) + " \u2103";
                        String feelsLikeValue = df.format(feelslike) + " \u2103";
                        city.setText(cityAndCountry);
                        temperature.setText(temperatureValue);
                        feels.append(feelsLikeValue);
                        weatherDescription.setText(description);

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
                    // If city's weather cannot be found
                    Toast.makeText(MainActivity.this, "Error getting the weather", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
            return null;
        }
    }

    /**
     * Redirects to event activity page when button is clicked.
     * @param view: View
     */
    public void clickMainEventsButton(View view) {
       Intent intent = new Intent(this, EventsActivity.class);

       // Set background initially invisible
       view.getBackground().setAlpha(0);

       // Set Background
       view.setBackgroundResource(R.drawable.drawable_button_ripple);

       startActivity(intent);
    }

    /**
     * Redirects to planner activity page when button is clicked.
     * @param view: View
     */
    public void clickMainPlannerButton(View view) {
        Intent intent = new Intent(this, MainPlanner.class);

        // Set background initially invisible
        view.getBackground().setAlpha(0);

        // Set Background
        view.setBackgroundResource(R.drawable.drawable_button_ripple);

        startActivity(intent);
    }

    /**
     * Redirects to calendar event activity page when button is clicked.
     * @param view: View
     */
    public void clickMainCalendarButton(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);

        // Set background initially invisible
        view.getBackground().setAlpha(0);

        // Set Background
        view.setBackgroundResource(R.drawable.drawable_button_ripple);

        startActivity(intent);
    }

    /**
     * Redirects to profile activity page when button is clicked.
     * @param view: View
     */
    public void clickMainPersonalProfileButton(View view) {
        Intent intent = new Intent(this, PersonalProfileActivity.class);

        // Set background initially invisible
        view.getBackground().setAlpha(0);

        // Set Background
        view.setBackgroundResource(R.drawable.drawable_button_ripple);

        startActivity(intent);
    }

    /**
     * Redirects to post activity page when button is clicked.
     * @param view: View
     */
    public void clickMainPostButton(View view) {
        Intent intent = new Intent(this, PostActivity.class);

        // Set background initially invisible
        view.getBackground().setAlpha(0);

        // Set Background
        view.setBackgroundResource(R.drawable.drawable_button_ripple);

        startActivity(intent);
    }

    /**
     * Redirects to maps activity page when button is clicked.
     * @param view: View
     */
    public void clickMapsButton(View view){
        Intent intent = new Intent(this, MapsActivity.class);

        // Set background initially invisible
        view.getBackground().setAlpha(0);

        // Set Background
        view.setBackgroundResource(R.drawable.drawable_button_ripple);

        startActivity(intent);
    }

    /**
     * When back button is clicked, disable going back.
     */
    @Override
    public void onBackPressed() {
        // Override the default behaviour to disable going back from this page.
        // Do nothing...
    }
}
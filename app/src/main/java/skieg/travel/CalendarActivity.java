package skieg.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;


public class CalendarActivity extends AppCompatActivity {

    // Stores the calendar object on the page
    CalendarView calendarView;

    // Stores the currently selected date on the calendar object
    String selectedDate = "";


    /**
     * When calendar activity page is created.
     * @param savedInstanceState: Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Get the calendar object
        calendarView = findViewById(R.id.calendarView);

        // Set default date to today
        LocalDate currentDate = LocalDate.now();
        selectedDate = currentDate.toString();

        // When a different calendar date is selected, save the new value
        calendarView.setOnDateChangeListener((calendarView1, year, month, day) -> {
            selectedDate = year + "-" + InputValidation.makeValidDateValue((month+1)) + "-"
                    + InputValidation.makeValidDateValue(day);
            Toast toast = Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT);
            toast.show();
        });

    }

    /**
     * Redirects to the main page when the main button is clicked.
     * @param view: View
     */
    public void clickMainButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Redirects to the create calendar event page when the create button is clicked.
     * @param view: View
     */
    public void clickCreateButton(View view) {
        Intent intent = new Intent(this, CreateCalendarEventActivity.class);
        startActivity(intent);
    }

    /**
     * Redirects to the calendar events page for the selected date, when the view events button is clicked.
     * @param view: View
     */
    public void clickCalendarEventsButton(View view) {
        Intent intent = new Intent(this, CalendarEventActivity.class);

        // Pass the selected calendar date to the view events page to get only events from that date
        Bundle bundle = new Bundle();
        bundle.putString("selectedDate", selectedDate);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}

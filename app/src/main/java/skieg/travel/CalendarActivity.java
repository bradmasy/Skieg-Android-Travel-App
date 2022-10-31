package skieg.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;


public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;

    String selectedDate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);

        // Set default date to today
        LocalDate currentDate = LocalDate.now();
        selectedDate = currentDate.toString();

        calendarView.setOnDateChangeListener((calendarView1, year, month, day) -> {
            selectedDate = year + "-" + InputValidation.makeValidDateValue((month+1)) + "-"
                    + InputValidation.makeValidDateValue(day);
            Toast toast = Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT);
            toast.show();
        });

    }

    public void clickMainButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickCreateButton(View view) {
        Intent intent = new Intent(this, CreateCalendarEventActivity.class);
        startActivity(intent);
    }

    public void clickCalendarEventsButton(View view) {
        Intent intent = new Intent(this, CalendarEventActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("selectedDate", selectedDate);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}

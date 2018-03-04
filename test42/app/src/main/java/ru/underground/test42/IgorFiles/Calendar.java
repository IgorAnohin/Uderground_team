package ru.underground.test42.IgorFiles;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import ru.underground.test42.InnerThings.Future_history;
import ru.underground.test42.InnerThings.Recipe;
import ru.underground.test42.MainActivity;
import ru.underground.test42.R;

import static ru.underground.test42.MainActivity.staticRecipe;

public class Calendar extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView text;
    private Button button;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        text = (TextView) findViewById(R.id.MyDate);
        button  = (Button) findViewById(R.id.little_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = (i1+1) +"/" + i2 + "/" + i;
                text.setText(date);
            }
        });


    }
}

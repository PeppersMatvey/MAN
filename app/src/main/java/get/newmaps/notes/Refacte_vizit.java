package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class Refacte_vizit extends AppCompatActivity {
    public static int Vizit_id;
    public static Imports.Vizit vizit;
    private EditText str_set;
    TextView currentDateTime;
    Calendar dateAndTime= Calendar.getInstance();
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_refacte_vizit);
        currentDateTime = findViewById(R.id.currentDateTime);
        dateAndTime.setTime(vizit.DATE);
        str_set=findViewById(R.id.refacte_str);
        //if(vizit.NOTES==null){
        //    vizit.NOTES="";
        //}
        str_set.setText(vizit.NOTES);
        findViewById(R.id.sawe_refacte).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Imports.Vizit[] vizitsS = new Imports().readHome(Views_Home_Open.oneHome.ADRESS).VIZIT_ARRAY;
                    vizitsS[Vizit_id] = new Imports.Vizit(new Date(dateAndTime.getTimeInMillis()), str_set.getText().toString());
                    Views_Home_Open.oneHome.VIZIT_ARRAY = vizitsS;
                    new Imports().write_home(Views_Home_Open.oneHome, Views_Home_Open.oneHome.ADRESS);
                    FilterList.load();
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        setInitialDateTime();
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(Refacte_vizit.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(Refacte_vizit.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {
        currentDateTime.setText("Время визита: "+DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Refacte_vizit.this,Views_Home_Open.class));
        finish();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };
}
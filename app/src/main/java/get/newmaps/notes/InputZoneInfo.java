package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class InputZoneInfo extends AppCompatActivity {
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    EditText editText;
    TextView info;
    AppCompatButton button;
    public int zoneNUMBER;
    public int kw;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_zone_info);
        t1=findViewById(R.id.chain1);
        t2=findViewById(R.id.chain2);
        t3=findViewById(R.id.chain3);
        t4=findViewById(R.id.chain4);
        if(StorageApp.isNightMode(InputZoneInfo.this)){
            t1.setBackground(getDrawable(R.drawable.oval_white));
            t2.setBackground(getDrawable(R.drawable.oval_white));
            t3.setBackground(getDrawable(R.drawable.oval_white));
            t4.setBackground(getDrawable(R.drawable.oval_white));
        }else{
            t1.setBackground(getDrawable(R.drawable.oval));
            t2.setBackground(getDrawable(R.drawable.oval));
            t3.setBackground(getDrawable(R.drawable.oval));
            t4.setBackground(getDrawable(R.drawable.oval));
        }
        editText=findViewById(R.id.edit_kw_import);
        if(StorageApp.isNightMode(this)){
            editText.setTextColor(getColor(R.color.white));
        }else{
            editText.setTextColor(getColor(R.color.black));
        }
        info=findViewById(R.id.textView_import_vibor_user);
        button=findViewById(R.id.button_import);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(InputZoneInfo.this, Menu_list.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final boolean[] b1 = {true};
        final boolean[] b2 = {true};
        final boolean[] b3 = {true};
        final boolean[] b4 = {true};
        t1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(b1[0]){
                    if(StorageApp.isNightMode(InputZoneInfo.this)){
                        t2.setBackground(getDrawable(R.drawable.oval_white));
                        t3.setBackground(getDrawable(R.drawable.oval_white));
                        t4.setBackground(getDrawable(R.drawable.oval_white));
                    }else{
                        t2.setBackground(getDrawable(R.drawable.oval));
                        t3.setBackground(getDrawable(R.drawable.oval));
                        t4.setBackground(getDrawable(R.drawable.oval));
                    }
                    t1.setBackground(getDrawable(R.drawable.oval1));
                    b1[0] =(!b1[0]);
                }else{
                    if(StorageApp.isNightMode(InputZoneInfo.this)){
                        t1.setBackground(getDrawable(R.drawable.oval_white));
                    }else{
                        t1.setBackground(getDrawable(R.drawable.oval));
                    }
                    b1[0] =(!b1[0]);
                }
                zoneNUMBER=1;
                if(zoneNUMBER!=0&kw!=0){
                    info.setText("Зона: "+zoneNUMBER+"\nНомер: "+kw);
                }
                try {
                    new TimerMiliSecond().startTimer(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        t2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(b2[0]){
                    if(StorageApp.isNightMode(InputZoneInfo.this)){
                        t1.setBackground(getDrawable(R.drawable.oval_white));
                        t3.setBackground(getDrawable(R.drawable.oval_white));
                        t4.setBackground(getDrawable(R.drawable.oval_white));
                    }else{
                        t1.setBackground(getDrawable(R.drawable.oval));
                        t3.setBackground(getDrawable(R.drawable.oval));
                        t4.setBackground(getDrawable(R.drawable.oval));
                    }
                    t2.setBackground(getDrawable(R.drawable.oval1));
                    b2[0] =(!b2[0]);
                }else{
                    if(StorageApp.isNightMode(InputZoneInfo.this)){
                        t2.setBackground(getDrawable(R.drawable.oval_white));
                    }else{
                        t2.setBackground(getDrawable(R.drawable.oval));
                    }
                    b2[0] =(!b2[0]);
                }
                zoneNUMBER=2;
                if(zoneNUMBER!=0&kw!=0){
                    info.setText("Зона: "+zoneNUMBER+"\nНомер: "+kw);
                }
                try {
                    new TimerMiliSecond().startTimer(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        t3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(b3[0]){
                    if(StorageApp.isNightMode(InputZoneInfo.this)){
                        t1.setBackground(getDrawable(R.drawable.oval_white));
                        t2.setBackground(getDrawable(R.drawable.oval_white));
                        t4.setBackground(getDrawable(R.drawable.oval_white));
                    }else{
                        t1.setBackground(getDrawable(R.drawable.oval));
                        t2.setBackground(getDrawable(R.drawable.oval));
                        t4.setBackground(getDrawable(R.drawable.oval));
                    }
                    t3.setBackground(getDrawable(R.drawable.oval1));
                    b3[0] =(!b3[0]);
                }else{
                    if(StorageApp.isNightMode(InputZoneInfo.this)){
                        t3.setBackground(getDrawable(R.drawable.oval_white));
                    }else{
                        t3.setBackground(getDrawable(R.drawable.oval));
                    }
                    b3[0] =(!b3[0]);
                }
                zoneNUMBER=3;
                if(zoneNUMBER!=0&kw!=0){
                    info.setText("Зона: "+zoneNUMBER+"\nНомер: "+kw);
                }
                try {
                    new TimerMiliSecond().startTimer(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        t4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(b4[0]){
                    if(StorageApp.isNightMode(InputZoneInfo.this)){
                        t1.setBackground(getDrawable(R.drawable.oval_white));
                        t2.setBackground(getDrawable(R.drawable.oval_white));
                        t3.setBackground(getDrawable(R.drawable.oval_white));
                    }else{
                        t1.setBackground(getDrawable(R.drawable.oval));
                        t2.setBackground(getDrawable(R.drawable.oval));
                        t3.setBackground(getDrawable(R.drawable.oval));
                    }
                    t4.setBackground(getDrawable(R.drawable.oval1));
                    b4[0] =(!b4[0]);
                }else{
                    if(StorageApp.isNightMode(InputZoneInfo.this)){
                        t4.setBackground(getDrawable(R.drawable.oval_white));
                    }else{
                        t4.setBackground(getDrawable(R.drawable.oval));
                    }
                    b4[0] =(!b4[0]);
                }
                zoneNUMBER=4;
                if(zoneNUMBER!=0&kw!=0){
                    info.setText("Зона: "+zoneNUMBER+"\nНомер: "+kw);
                }
                try {
                    new TimerMiliSecond().startTimer(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().toString().trim().equals(""))return;
                kw=Integer.parseInt(editText.getText().toString());
                if(zoneNUMBER!=0&kw!=0){
                    info.setText("Зона: "+zoneNUMBER+"\nНомер: "+kw);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kw>=1&kw<=100){
                    Intent ii=new Intent();
                    ii.putExtra("zonesInfoNumberZone",zoneNUMBER);
                    ii.putExtra("zonesInfoNumberKW",kw);
                    setResult(SettingsActivity.PICKFILE_REQUEST_CODE_IMPORT_ZONE,ii);
                    finish();
                }else{
                    Toast.makeText(InputZoneInfo.this, "Количество не может быть меньше или равно нулю или больше 100", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
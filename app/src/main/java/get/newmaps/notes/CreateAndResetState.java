package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Random;

public class CreateAndResetState extends AppCompatActivity {
private static File ADRESS_STATE;
EditText name_edit;
EditText color_id;
View v_color_backgroung;
AppCompatButton save;
    public static void setAdressState(File adressState) {
        ADRESS_STATE = adressState;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_reset_state);
    }

    @Override
    protected void onResume() {
        save=findViewById(R.id.button_sawe_state);
        name_edit=findViewById(R.id.name_state_edit);
        if(StorageApp.isNightMode(this)){
            name_edit.setTextColor(getColor(R.color.white));
        }else{
            name_edit.setTextColor(getColor(R.color.black));
        }
        color_id=findViewById(R.id.color_id_state_edit);
        if(StorageApp.isNightMode(this)){
            color_id.setTextColor(getColor(R.color.white));
        }else{
            color_id.setTextColor(getColor(R.color.black));
        }
        v_color_backgroung=findViewById(R.id.rectangel_color);
        color_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(isNumberString(color_id.getText().toString())){
                    try {
                        v_color_backgroung.setBackgroundColor(Color.parseColor(color_id.getText().toString()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try {
                        v_color_backgroung.setBackgroundColor(Integer.parseInt(color_id.getText().toString()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        if(ADRESS_STATE==null){
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new File(StorageApp.getAppPatch().getAbsolutePath()+"/state/").mkdirs();
                    //StorageApp.getAppPatch().getAbsolutePath()+"/state/"+new Random(System.currentTimeMillis()).nextLong()+".state")
                    if(name_edit.getText().toString().length()<3|name_edit.getText().toString().length()>20){
                        Toast.makeText(CreateAndResetState.this, "Название не может быть короче 3 символов или длиннее 20", Toast.LENGTH_LONG).show();
                        return;
                    }else{
                        try{
                            TextView textView=new TextView(CreateAndResetState.this);
                            if (isNumberString(color_id.getText().toString())) {
                                textView.setTextColor(Color.parseColor(color_id.getText().toString()));
                            }else{
                                textView.setTextColor(Integer.parseInt(color_id.getText().toString()));
                            }
                        }catch (Exception e){
                            Toast.makeText(CreateAndResetState.this, "Проверьте правильно ли вы ввели id цвета", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            return;
                        }
                        if(isNumberString(color_id.getText().toString())){
                            try {
                                new AddState().stateWriter(new State(name_edit.getText().toString(),Color.parseColor(color_id.getText().toString()),PasswordFormat.gt(name_edit.getText().toString()+color_id.getText().toString()+new Random(System.currentTimeMillis()).nextLong())),new File(StorageApp.getAppPatch().getAbsolutePath()+"/state/"+new Random(System.currentTimeMillis()).nextLong()+".state"));
                            } catch (Exception e) {
                                Toast.makeText(CreateAndResetState.this, "Возникла ошибка при сохранении статуса", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                                return;
                            }
                        }else{
                            try {
                                new AddState().stateWriter(new State(name_edit.getText().toString(),Integer.parseInt(color_id.getText().toString()),PasswordFormat.gt(name_edit.getText().toString()+color_id.getText().toString()+new Random(System.currentTimeMillis()).nextLong())),new File(StorageApp.getAppPatch().getAbsolutePath()+"/state/"+new Random(System.currentTimeMillis()).nextLong()+".state"));
                            } catch (Exception e) {
                                Toast.makeText(CreateAndResetState.this, "Возникла ошибка при сохранении статуса", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                                return;
                            }
                        }
                    }
                    ADRESS_STATE=null;
                    Toast.makeText(CreateAndResetState.this, "Статус успешно сохранён", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }else{
            try {
                State stt=new AddState().readState(ADRESS_STATE);
                v_color_backgroung.setBackgroundColor(stt.colorState);
                name_edit.setText(stt.name);
                color_id.setText((stt.colorState+""));
            } catch (Exception e) {
                e.printStackTrace();
            }
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(name_edit.getText().toString().length()<3|name_edit.getText().toString().length()>20){
                        Toast.makeText(CreateAndResetState.this, "Название не может быть короче 3 символов или длиннее 20", Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        try {
                            TextView textView = new TextView(CreateAndResetState.this);
                            if (isNumberString(color_id.getText().toString())) {
                                textView.setTextColor(Color.parseColor(color_id.getText().toString()));
                            }else{
                                textView.setTextColor(Integer.parseInt(color_id.getText().toString()));
                            }
                        } catch (Exception e) {
                            Toast.makeText(CreateAndResetState.this, "Проверьте правильно ли вы ввели id цвета", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            return;
                        }
                        if (isNumberString(color_id.getText().toString())) {
                            try {
                                new AddState().stateWriter(new State(name_edit.getText().toString(), Color.parseColor(color_id.getText().toString()), new AddState().readState(ADRESS_STATE).state_teg), ADRESS_STATE);
                            } catch (Exception e) {
                                Toast.makeText(CreateAndResetState.this, "Возникла ошибка при сохранении статуса", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                                return;
                            }
                        } else {
                            try {
                                new AddState().stateWriter(new State(name_edit.getText().toString(), Integer.parseInt(color_id.getText().toString()), new AddState().readState(ADRESS_STATE).state_teg), ADRESS_STATE);
                            } catch (Exception e) {
                                Toast.makeText(CreateAndResetState.this, "Возникла ошибка при сохранении статуса", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                                return;
                            }
                        }
                    }
                    ADRESS_STATE=null;
                    Toast.makeText(CreateAndResetState.this, "Статус успешно сохранён", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }
        super.onResume();
    }
    boolean isNumberString(String str){
        try{
            Color.parseColor(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreateAndResetState.this,SettingsActivity.class));
        finish();
    }

    public void setColor(View view) {
        color_id.setText(((ColorDrawable)view.getBackground()).getColor()+"");
        v_color_backgroung.setBackgroundColor(((ColorDrawable)view.getBackground()).getColor());
    }
}
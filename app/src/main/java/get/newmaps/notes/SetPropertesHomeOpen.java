package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SetPropertesHomeOpen extends AppCompatActivity {
    private EditText edit_strit;
    private EditText edit_home_nomer;
    private EditText edit_kw;
    private EditText edit_str;
    private AppCompatButton sawe;
    private AppCompatButton delete;
    public static Imports.OneHome oneHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_propertes_home_open);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SetPropertesHomeOpen.this, Views_Home_Open.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        edit_strit=findViewById(R.id.strit_name_edit);
        edit_strit.setText(oneHome.STRIT_NAME);
        edit_home_nomer=findViewById(R.id.home_nomer_edit);
        edit_home_nomer.setText(oneHome.HOME_NOMER);
        edit_kw=findViewById(R.id.home_set_kv_edit);
        edit_kw.setText(new String(""+oneHome.KV));
        edit_str=findViewById(R.id.home_str_set_edit);
        edit_str.setText(oneHome.STR);
        if(StorageApp.isNightMode(this)){
            edit_strit.setTextColor(getColor(R.color.white));
            edit_str.setTextColor(getColor(R.color.white));
            edit_kw.setTextColor(getColor(R.color.white));
            edit_home_nomer.setTextColor(getColor(R.color.white));
        }else{
            edit_strit.setTextColor(getColor(R.color.black));
            edit_str.setTextColor(getColor(R.color.black));
            edit_kw.setTextColor(getColor(R.color.black));
            edit_home_nomer.setTextColor(getColor(R.color.black));
        }
        sawe=findViewById(R.id.sawe_new_propertes_home);
        delete=findViewById(R.id.delete_home);
        sawe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    String str=edit_strit.getText().toString();
                    if(str.equals("")|str.length()>35){
                        Toast.makeText(SetPropertesHomeOpen.this, "Название улицы не может быть пустым или более 35 букв", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                {
                    String str=edit_home_nomer.getText().toString();
                    if(str.equals("")|str.length()>15){
                        Toast.makeText(SetPropertesHomeOpen.this, "Номер дома не может быть пустым или более 15 символов", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                {
                    String str=edit_kw.getText().toString();
                    if(str.equals("")|str.length()>4){
                        Toast.makeText(SetPropertesHomeOpen.this, "Номер квартиры не может быть пустым или более 4 символов", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                DialogShower dialogShower=new DialogShower();
                dialogShower.showDialog(new WarningDialog("Вы действительно хотите изменить свойства дома?", "Отменить", "Изменить", new WarningDialog.Canceled() {
                    @Override
                    public void cancel() {

                    }
                }, new WarningDialog.Run() {
                    @Override
                    public void run() {
                        try {
                            oneHome=new Imports().readHome(oneHome.ADRESS);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        oneHome.STRIT_NAME=edit_strit.getText().toString();
                        oneHome.HOME_NOMER=edit_home_nomer.getText().toString();
                        oneHome.KV= Integer.parseInt(edit_kw.getText().toString());
                        oneHome.STR=edit_str.getText().toString();
                        try {
                            new Imports().write_home(oneHome,oneHome.ADRESS);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        oneHome.NEW_NAME=new Formater().getFormateString(oneHome.STRIT_NAME+oneHome.HOME_NOMER);
                        Toast.makeText(SetPropertesHomeOpen.this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
                        FilterList.load();
                        startActivity(new Intent(SetPropertesHomeOpen.this, Menu_list.class));
                        finish();
                    }
                }),SetPropertesHomeOpen.this);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogShower dialogShower=new DialogShower();
                String info;
                if(oneHome.KV==-1){
                    info = "Ул. " + oneHome.STRIT_NAME + ", Д. " + oneHome.HOME_NOMER + "(частный)";
                    //Spannable sp = new SpannableString(str);
                    //sp.setSpan(new ForegroundColorSpan(Green_Color), str.length() - 9, str.length(), SpannableString.SPAN_COMPOSING);
                    //mName.setText(sp);
                }else{
                    info="Ул. "+oneHome.STRIT_NAME+", Д."+oneHome.HOME_NOMER+", Кв."+oneHome.KV;
                }
                dialogShower.showDialog(new WarningDialog(info, "Отмена", "Удалить", new WarningDialog.Canceled() {
                    @Override
                    public void cancel() {

                    }
                }, new WarningDialog.Run() {
                    @Override
                    public void run() {
                        FileOperations.writeNullAndDelete(oneHome.ADRESS);
                        Toast.makeText(SetPropertesHomeOpen.this, "Дом удалён", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SetPropertesHomeOpen.this, Menu_list.class));
                        finish();
                    }
                }),SetPropertesHomeOpen.this);
            }
        });
    }
}
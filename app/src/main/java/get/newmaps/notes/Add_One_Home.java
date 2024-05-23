package get.newmaps.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.commons.lang3.ArrayUtils;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

public class Add_One_Home extends AppCompatActivity {

    private EditText EDstrit;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_one_home);
        findViewById(R.id.cancel_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        EDstrit= findViewById(R.id.strit_nomer);
        EditText EDhome=findViewById(R.id.home_nomer);
        AppCompatButton appCompatButton=findViewById(R.id.addSlash);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(EDhome,InputMethodManager.SHOW_IMPLICIT);
                if(EDhome.getInputType()== InputType.TYPE_NUMBER_VARIATION_NORMAL){
                    EDhome.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                }else {
                    EDhome.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
                }
                // EDhome.setText(EDhome.getText()+"/");
                EDhome.setSelection(EDhome.getText().length());
            }
        });
        if(StorageApp.isNightMode(this)){
            appCompatButton.setBackgroundDrawable(getDrawable(R.drawable.drow));
        }
        //EditText EDhome=findViewById(R.id.home_nomer);
        EditText EDlength=findViewById(R.id.Length_apartaments);
        EditText EDstr=findViewById(R.id.str_edit);
        if(StorageApp.isNightMode(this)){
            EDhome.setTextColor(getColor(R.color.white));
        }else{
            EDhome.setTextColor(getColor(R.color.black));
        }
        if(StorageApp.isNightMode(this)){
            EDlength.setTextColor(getColor(R.color.white));
        }else{
            EDlength.setTextColor(getColor(R.color.black));
        }
        if(StorageApp.isNightMode(this)){
            EDstr.setTextColor(getColor(R.color.white));
        }else{
            EDstr.setTextColor(getColor(R.color.black));
        }
        if(StorageApp.isNightMode(this)){
            EDstrit.setTextColor(getColor(R.color.white));
        }else{
            EDstrit.setTextColor(getColor(R.color.black));
        }
        findViewById(R.id.create_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioGroup rg=findViewById(R.id.Radio_bottom_group);

                String strit=EDstrit.getText().toString().trim();
                String home=EDhome.getText().toString().trim();
                String length=EDlength.getText().toString().trim();
                String str=EDstr.getText().toString().trim();
                if(str.length()==0){
                    str="нет примечаний";
                }
                File fileOut=null;

                if(rg.getCheckedRadioButtonId()==R.id.unoRadio){
                    fileOut=new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)1));
                }else if(rg.getCheckedRadioButtonId()==R.id.dusRadio){
                    fileOut=new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)2));
                }else if(rg.getCheckedRadioButtonId()==R.id.tresRadio){
                    fileOut=new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)3));
                }else if(rg.getCheckedRadioButtonId()==R.id.cuatrisRadio){
                    fileOut=new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)4));
                }else{
                    Toast.makeText(Add_One_Home.this, "Поля необходимо заполнить в соответствии с инструкциями", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strit.length()==0|home.length()==0|length.length()==0){
                    Toast.makeText(Add_One_Home.this, "Поля необходимо заполнить в соответствии с инструкциями", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    new Imports().sawe(fileOut,strit,home,Integer.parseInt(length),"Нет примечаний","","");
                    //new StartAsyncTask(new StartAsyncTask.EndRun() {
                    //    @Override
                    //    public void finalize() throws Exception {
                    //        Toast.makeText(create_xml.this, "Добавление завершено", Toast.LENGTH_SHORT).show();
                    //    }
                    //}).runExecute(StartAsyncTask.MODE_Execute.ADD_HOME,homeReader,fileOut,0,0);
                    FilterList.load();
                    Toast.makeText(Add_One_Home.this, "Добавление завершено", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    Toast.makeText(Add_One_Home.this, "Ошибка добавления", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }
    private String[] deleteProbel(String[] arrayString){
        //Log.d("tegggg", String.valueOf(arrayString.length));
        for(int i=0;i<arrayString.length;i++){
            if(arrayString[i]==null){
                ArrayUtils.remove(arrayString,i);
                continue;
            }
            if(arrayString[i].equals("")|arrayString[i].equals(" ")){
                ArrayUtils.remove(arrayString,i);
            }
        }
        //Log.d("tegggg", String.valueOf(arrayString.length));
        return arrayString;
    }

    public static final String codeRezults="bauosf308fgPCB";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EditText EDstrit=findViewById(R.id.strit_nomer);
        try {
            EDstrit.setText(data.getStringExtra(codeRezults));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean razBack=false;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Add_One_Home.this, Menu_list.class));
        finish();
    }
}
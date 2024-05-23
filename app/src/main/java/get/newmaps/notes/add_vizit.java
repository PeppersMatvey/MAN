package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class add_vizit extends AppCompatActivity {
    public static Imports.OneHome oneHome;
    public static finalInterface finish;
    interface finalInterface{
        void finalis();
    }
    public AutoCompleteTextView AutoEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_vizit);
        AutoEdit = findViewById(R.id.auto_complete_add_state);
        ArrayAdapter adapter=null;
        try{
            adapter = new ArrayAdapter(this, R.layout.tv_view, getArrayString());
        }catch (Exception e){
            e.printStackTrace();
        }
        AutoEdit.setAdapter(adapter);
        EditText editText=findViewById(R.id.edit_str_vizit_dialog);
        AppCompatButton appCompatButton=findViewById(R.id.button_str_vizit);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=editText.getText().toString();
                if(str.equals("")){
                    str="Нет описания визита";
                }
                String teg=searshTeg(AutoEdit.getText().toString());
                if(teg==null){
                    Toast.makeText(add_vizit.this, "Выберите тег", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTegState(teg,oneHome.ADRESS);
                saweVizit(new Imports.Vizit(new Date(), str),oneHome.ADRESS);
                if(finish!=null){
                    finish.finalis();
                }
                finish();
            }
        });
    }
    private void saweVizit(Imports.Vizit vizit, File f) {
        try{
            //Log.d("ggg",vizits.length);
            Imports.OneHome oneHome=new Imports().readHome(f);
            Imports.Vizit[] vizits= oneHome.VIZIT_ARRAY;
            Imports.Vizit[] newvizits= new Imports.Vizit[vizits.length+1];
            for(int i=0;i<vizits.length;i++){
                newvizits[i]=vizits[i];
            }
            newvizits[newvizits.length-1]=vizit;
            oneHome.VIZIT_ARRAY=newvizits;
            new Imports().write_home(oneHome,f);
            FilterList.load();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setTegState(String teg, File f) {
        try{
            Imports.OneHome oneHome=new Imports().readHome(f);
            oneHome.STATE=teg;
            new Imports().write_home(oneHome,f);
            FilterList.load();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String searshTeg(String str){
        try{
            ArrayList<State> stateArrayList=new AddState().readState();
            for(State s:stateArrayList){
                if(s.name.equals(str)){
                    return s.state_teg;
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private String[] getArrayString() throws Exception{
        ArrayList<State> List_state=new AddState().readState();
        String[] array = new String[List_state.size()];
        for(int i=0;i< List_state.size();i++){
            array[i]=List_state.get(i).name;
        }
        return array;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(add_vizit.this, Views_Home_Open.class));
        finish();
    }
}
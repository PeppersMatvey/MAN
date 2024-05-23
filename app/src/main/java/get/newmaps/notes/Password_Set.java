package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class Password_Set extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_set);
        setClaviatura();
        if (StorageApp.isNightMode(this)){
            TextView tv=findViewById(R.id.text_input_password_set);
            tv.setBackgroundColor(getColor(R.color.blackProzr));
            tv.setTextColor(Color.WHITE);
            findViewById(R.id.non_pass).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k0_set).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k1_set).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k2_set).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k3_set).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k4_set).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k5_set).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k6_set).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k7_set).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k8_set).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k9_set).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k_delete_set).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k_enter_set).setBackgroundColor(getColor(R.color.blackProzr));
        }else{
            TextView tv=findViewById(R.id.text_input_password_set);
            tv.setBackgroundColor(getColor(R.color.seri));
            tv.setTextColor(Color.BLACK);
            findViewById(R.id.text_input_password_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.non_pass).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k0_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k1_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k2_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k3_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k4_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k5_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k6_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k7_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k8_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k9_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k_delete_set).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k_enter_set).setBackgroundColor(getColor(R.color.seri));
        }
    }
    private void setClaviatura() {
        findViewById(R.id.non_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { setPassvordText("non"); }
        });
        findViewById(R.id.k0_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("0");
            }
        });
        findViewById(R.id.k1_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("1");
            }
        });
        findViewById(R.id.k2_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("2");
            }
        });
        findViewById(R.id.k3_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("3");
            }
        });
        findViewById(R.id.k4_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("4");
            }
        });
        findViewById(R.id.k5_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("5");
            }
        });
        findViewById(R.id.k6_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("6");
            }
        });
        findViewById(R.id.k7_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("7");
            }
        });
        findViewById(R.id.k8_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("8");
            }
        });
        findViewById(R.id.k9_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("9");
            }
        });
        findViewById(R.id.k_delete_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("delete");
            }
        });
        findViewById(R.id.k_enter_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("enter");
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Password_Set.this,Menu_list.class));
    }

    private boolean textpassword=false;
    private void setPassvordText(String s){
        //Log.d("pass_set","Diagn 01");
        TextView password=findViewById(R.id.text_input_password_set);
        if(!textpassword){
            if(StorageApp.isNightMode(this)){
                password.setTextColor(getResources().getColor(R.color.white));
            }else{
                password.setTextColor(getResources().getColor(R.color.black));
            }
            password.setGravity(Gravity.START);
            password.setText("");
            textpassword=true;
        }
        //Log.d("pass_set","Diagn 02");
        switch (s){
            case "enter":
                //Log.d("pass_set","Diagn 03");
                if (password.length() >= 10) {
                    ((TextView)findViewById(R.id.is_password_correct_view)).setText("Пароль не может быть длиннее 10 символов");
                    Toast.makeText(this, "Пароль не может быть длиннее 10 символов", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 4) {
                    ((TextView)findViewById(R.id.is_password_correct_view)).setText("Пароль не может быть короче 4 символов");
                    Toast.makeText(this, "Пароль не может быть короче 4 символов", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    //Log.d("pass_set","Diagn 04");
                    enter(password.getText().toString());
                    //Log.d("pass_set","Diagn 05");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return;
            case "delete":
                if (password.length() <= 0) {
                    textpassword=false;
                    password.setTextColor(getResources().getColor(R.color.silverText));
                    password.setGravity(Gravity.CENTER);
                    password.setText(getResources().getText(R.string.pin));
                    return;
                }
                password.setText(password.getText().toString().substring(0,password.getText().toString().length()-1));
                if (password.length() <= 0) {
                    textpassword=false;
                    password.setTextColor(getResources().getColor(R.color.silverText));
                    password.setGravity(Gravity.CENTER);
                    password.setText(getResources().getText(R.string.pin));
                    return;
                }
                return;
            case "non":
            {
                try {
                    enter("standarte_pass");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        password.setText(password.getText().toString()+s);
    }
    private void enter(String pass) throws Exception {
        //Log.d("pass_set","Diagn 041");
        FileOutputStream fos=new FileOutputStream(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,CreatePatch.SPEC_CODE));
        fos.write(MainActivity.testPass.getBytes());
        fos.close();
        FileOperations.AESEncrypte(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, CreatePatch.SPEC_CODE)), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, CreatePatch.SPEC_CODE)),PasswordFormat.gt(pass), FileOperations.ModeCipher.Encrypt);
        FileWriter fw=new FileWriter(new File(getFilesDir().getAbsolutePath()+"/pasword"));
        fw.write(PasswordFormat.gt(pass));
        fw.close();
        if(!Permission.isPermission(getApplicationContext())){
            //Toast.makeText(MainActivity.this, "Предоставьте разрешение", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Password_Set.this, Permission.class));
            finish();
        }else{
            //Toast.makeText(Password_Set.this, "Разрешение предоставлено", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Password_Set.this, Menu_list.class));
            finish();
        }
        /*
        else{
            // FileOutputStream fos=new FileOutputStream(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,CreatePatch.SPEC_CODE));
            // fos.write(testPass.getBytes());
            // fos.close();
            FileOperations.AESEncrypte(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, CreatePatch.SPEC_CODE)), new File(getCacheDir().getAbsolutePath()+"/uwebuvyciv.txt"),PasswordFormat.gt(pass), FileOperations.ModeCipher.Decrypt);
            //System.out.println(FileOperations.filesCompareByByte(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/uwebuvyciv.txt"),new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, CreatePatch.SPEC_CODE))));
            //System.out.println(FileOperations.compareByMemoryMappedFiles(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/uwebuvyciv.txt"),new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, CreatePatch.SPEC_CODE))));
            if(!FileOperations.compareFileString(new File(getCacheDir().getAbsolutePath()+"/uwebuvyciv.txt"),new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, CreatePatch.SPEC_CODE)))){
                Toast.makeText(this, "Вы ввели не верный пароль", Toast.LENGTH_SHORT).show();
                ((TextView)findViewById(R.id.is_password_correct_view)).setText("Введите правильный пароль. Если вы его забыли то необходимо стереть данные приложения чтобы снова иметь возможность использовать его");
                //Toast.makeText(this, new File(getCacheDir().getAbsolutePath()+"/uwebuvyciv").getAbsolutePath(), Toast.LENGTH_LONG).show();
                return;
            }
            new File(getCacheDir().getAbsolutePath()+"/uwebuvyciv.txt").delete();
        }
        */

        // TODO добавить потом возможность пользователю самостоятельно менять шифруемую фразу
        /*
        new encrypteTask(new encrypteTask.END_OPERATIONS() {
            @Override
            public void finall() {
                Toast.makeText(MainActivity.this, "Зашифровка завершена", Toast.LENGTH_SHORT).show();
            }
        }).execute(pass);
        Thread.sleep(2000);
         */
        //new DdecrypteTask().execute(pass);
        //Log.d("pass_set","Diagn 042");
    }
    /*
    class DdecrypteTask extends AsyncTask<String,Void,Void> {
        @Override
        protected void onPreExecute() {
            //Log.d("pass_set","Diagn 1");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... voids) {
            //Log.d("pass_set","Diagn 1");
            String pss=PasswordFormat.gt(voids[0]);
            try{
                encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 1)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 1)+"/"),pss);
                //Log.d("pass_set","Diagn 2");
                encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 2)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 2)+"/"),pss);
                encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 3)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 3)+"/"),pss);
                encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 4)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 4)+"/"),pss);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        private void encrypteDir(File in, File out, String password) throws Exception{
            DirToFile dirToFile=new DirToFile();
            dirToFile.clear();
            dirToFile.cht(in);
            for(File f:dirToFile.getSPISOCFile()){
                FileOperations.AESEncrypte(f,new File(out.getAbsolutePath()+"/"+f.getName()),password, FileOperations.ModeCipher.Decrypt);
            }
            for(File f:dirToFile.getSPISOCFile()){
                f.delete();
            }
            dirToFile.clear();
        }

        @Override
        protected void onPostExecute(Void unused) {
            //Log.d("pass_set","Diagn 3");
            Toast.makeText(Password_Set.this, "Зашифровка завершена", Toast.LENGTH_SHORT).show();
            if(!Permission.isPermission(getApplicationContext())){
                //Toast.makeText(MainActivity.this, "Предоставьте разрешение", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Password_Set.this, Permission.class));
                finish();
            }else{
                //Toast.makeText(Password_Set.this, "Разрешение предоставлено", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Password_Set.this, Menu_list.class));
                finish();
            }
            super.onPostExecute(unused);
        }
    }
     */
}
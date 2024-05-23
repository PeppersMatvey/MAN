package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
public static final String testPass="!iqx46378fn17!63qrnqwSFB*&765367u6i8o2(*fcvtuyoio6fd7vx8NixMNlqofdbv18c7633qbx45q637ozm9mq934nxxgt6f8m29zf6xmn386t7vgzmh2mf18912x42m78z2h340,.jgc35tx0WXI4MCTG2XQG943MG2F9280XZ29M7ZXG245GM,9smrfcg8945ycg,baAG./SBykmqYwqguEBTxe214@#)%@";
//public static final String testPass="ttd";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StorageApp.setAppPatch(getFilesDir());
        if (ifStandartPassword()){
            try {
                //Toast.makeText(this,"Ghjuyvealcuqwle", Toast.LENGTH_LONG).show();
                enter("standarte_pass");
                //Toast.makeText(this,"Ghjuyvealcuqwle", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        setClaviatura();
        if (StorageApp.isNightMode(this)){
            TextView tv=findViewById(R.id.text_input_password);
            tv.setBackgroundColor(getColor(R.color.blackProzr));
            tv.setTextColor(Color.WHITE);
            findViewById(R.id.k0).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k1).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k2).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k3).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k4).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k5).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k6).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k7).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k8).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k9).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k_delete).setBackgroundColor(getColor(R.color.blackProzr));
            findViewById(R.id.k_enter).setBackgroundColor(getColor(R.color.blackProzr));
        }else{
            TextView tv=findViewById(R.id.text_input_password);
            //if(ContextCompat.getColor(this,R.color.seri)==null){

            //}
            if(tv==null){
                Log.d("ghghghgh","NULL");
            }
            if(tv!=null){
                Log.d("ghghghgh","Not NULL");
            }
            tv.setBackgroundColor(getResources().getColor(R.color.seri));
            tv.setTextColor(Color.BLACK);
            findViewById(R.id.text_input_password).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k0).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k1).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k2).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k3).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k4).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k5).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k6).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k7).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k8).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k9).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k_delete).setBackgroundColor(getResources().getColor(R.color.seri));
            findViewById(R.id.k_enter).setBackgroundColor(getResources().getColor(R.color.seri));
            /*
            tv.setBackgroundColor(getColor(R.color.seri));
            tv.setTextColor(Color.BLACK);
            findViewById(R.id.text_input_password).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k0).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k1).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k2).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k3).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k4).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k5).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k6).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k7).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k8).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k9).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k_delete).setBackgroundColor(getColor(R.color.seri));
            findViewById(R.id.k_enter).setBackgroundColor(getColor(R.color.seri));
             */
        }
    }

    public static boolean ifStandartPassword() {
        try {
            FileOperations.AESEncrypte(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, CreatePatch.SPEC_CODE)), new File(StorageApp.getAppPatch().getAbsolutePath()+"/uwebuvyciv.txt"),PasswordFormat.gt("standarte_pass"), FileOperations.ModeCipher.Decrypt);
            return FileOperations.compareFileString(new File(StorageApp.getAppPatch().getAbsolutePath()+"/uwebuvyciv.txt"),new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, CreatePatch.SPEC_CODE)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void setClaviatura() {
        findViewById(R.id.k0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("0");
            }
        });
        findViewById(R.id.k1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("1");
            }
        });
        findViewById(R.id.k2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("2");
            }
        });
        findViewById(R.id.k3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("3");
            }
        });
        findViewById(R.id.k4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("4");
            }
        });
        findViewById(R.id.k5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("5");
            }
        });
        findViewById(R.id.k6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("6");
            }
        });
        findViewById(R.id.k7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("7");
            }
        });
        findViewById(R.id.k8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("8");
            }
        });
        findViewById(R.id.k9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("9");
            }
        });
        findViewById(R.id.k_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("delete");
            }
        });
        findViewById(R.id.k_enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassvordText("enter");
            }
        });
    }
    private boolean textpassword=false;
    private void setPassvordText(String s){
        TextView password=findViewById(R.id.text_input_password);
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
        switch (s){
            case "enter":
                //if (password.length() < 4) {
                //    if(!new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,CreatePatch.SPEC_CODE)).exists()){
                //        Toast.makeText(this, "Пароль не может быть короче 4 символов", Toast.LENGTH_SHORT).show();
                //        return;
                 //   }
                    //Toast.makeText(this, "Пароль не может быть короче 4 символов", Toast.LENGTH_SHORT).show();
                    //return;
                //}
                try{
                    enter(password.getText().toString());
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
        }
        /*
        if (password.length() >= 10) {
            if(!new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,CreatePatch.SPEC_CODE)).exists()){
                Toast.makeText(this, "Пароль не может быть длиннее 10 символов", Toast.LENGTH_SHORT).show();
                return;
            }
        }
         */
        password.setText(password.getText().toString()+s);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
    }

    private void enter(String pass) throws Exception {
        //Toast.makeText(this, pass, Toast.LENGTH_LONG).show();
        //if(!new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,CreatePatch.SPEC_CODE)).exists()){
        //    FileOutputStream fos=new FileOutputStream(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,CreatePatch.SPEC_CODE));
        //    fos.write(testPass.getBytes());
        //    fos.close();
        //    FileOperations.AESEncrypte(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, CreatePatch.SPEC_CODE)), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, CreatePatch.SPEC_CODE)),PasswordFormat.gt(pass), FileOperations.ModeCipher.Encrypt);
        //}else{
            // FileOutputStream fos=new FileOutputStream(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,CreatePatch.SPEC_CODE));
            // fos.write(testPass.getBytes());
            // fos.close();
        FileOperations.AESEncrypte(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, CreatePatch.SPEC_CODE)), new File(getFilesDir().getAbsolutePath()+"/uwebuvyciv.txt"),PasswordFormat.gt(pass), FileOperations.ModeCipher.Decrypt);
            //System.out.println(FileOperations.filesCompareByByte(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/uwebuvyciv.txt"),new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, CreatePatch.SPEC_CODE))));
            //System.out.println(FileOperations.compareByMemoryMappedFiles(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/uwebuvyciv.txt"),new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, CreatePatch.SPEC_CODE))));
            if(!FileOperations.compareFileString(new File(getFilesDir().getAbsolutePath()+"/uwebuvyciv.txt"),new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, CreatePatch.SPEC_CODE)))){
                Toast.makeText(this, "Вы ввели не верный пароль", Toast.LENGTH_SHORT).show();
                ((TextView)findViewById(R.id.is_password_correct_view)).setText("Введите правильный пароль. Если вы его забыли то необходимо стереть данные приложения чтобы снова иметь возможность использовать его");
                //Toast.makeText(this, new File(getCacheDir().getAbsolutePath()+"/uwebuvyciv").getAbsolutePath(), Toast.LENGTH_LONG).show();
                return;
            }
            new File(getFilesDir().getAbsolutePath()+"/uwebuvyciv.txt").delete();
        FileWriter fw=new FileWriter(new File(getFilesDir().getAbsolutePath()+"/pasword"));
        fw.write(PasswordFormat.gt(pass));
        fw.close();
        //}

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
        decrypteTask decrypteTask=new decrypteTask();
        decrypteTask.v1=pass;
        decrypteTask.startThread();
        //Toast.makeText(this, "Вы ввели пароль", Toast.LENGTH_SHORT).show();
    }
    class decrypteTask extends AsyncTask<String,Void,Void>{
        public String v1;
        public void startThread(){
            Thread th=new Thread(new Runnable() {
                @Override
                public void run() {
                    String pss=PasswordFormat.gt(v1);
                    try{
                        if(new File(CreatePatch.getPatchImageE(1)).exists()){
                            decrypteDir(new File(CreatePatch.getPatchImageE(1)),new File(CreatePatch.getPatchImage(1)),pss);
                        }
                        if(new File(CreatePatch.getPatchImageE(2)).exists()){
                            decrypteDir(new File(CreatePatch.getPatchImageE(2)),new File(CreatePatch.getPatchImage(2)),pss);
                        }
                        if(new File(CreatePatch.getPatchImageE(3)).exists()){
                            decrypteDir(new File(CreatePatch.getPatchImageE(3)),new File(CreatePatch.getPatchImage(3)),pss);
                        }
                        if(new File(CreatePatch.getPatchImageE(4)).exists()){
                            decrypteDir(new File(CreatePatch.getPatchImageE(4)),new File(CreatePatch.getPatchImage(4)),pss);
                        }
                        decrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 1)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 1)+"/"),pss);
                        decrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 2)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 2)+"/"),pss);
                        decrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 3)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 3)+"/"),pss);
                        decrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 4)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 4)+"/"),pss);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            th.start();
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!Permission.isPermission(getApplicationContext())){
                //Toast.makeText(MainActivity.this, "Предоставьте разрешение", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Permission.class));
                finish();
            }else{
                //Toast.makeText(MainActivity.this, "Разрешение предоставлено", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Menu_list.class));
                finish();
            }
        }
        @Override
        protected Void doInBackground(String... voids) {
            String pss=PasswordFormat.gt(voids[0]);
            try{
                //Log.d("yertrxy","gdsfdgkyxrktrxjyrxiutrx");
                if(new File(CreatePatch.getPatchImageE(1)).exists()){
                    decrypteDir(new File(CreatePatch.getPatchImageE(1)),new File(CreatePatch.getPatchImage(1)),pss);
                }
                if(new File(CreatePatch.getPatchImageE(2)).exists()){
                    decrypteDir(new File(CreatePatch.getPatchImageE(2)),new File(CreatePatch.getPatchImage(2)),pss);
                }
                if(new File(CreatePatch.getPatchImageE(3)).exists()){
                    decrypteDir(new File(CreatePatch.getPatchImageE(3)),new File(CreatePatch.getPatchImage(3)),pss);
                }
                if(new File(CreatePatch.getPatchImageE(4)).exists()){
                    decrypteDir(new File(CreatePatch.getPatchImageE(4)),new File(CreatePatch.getPatchImage(4)),pss);
                }
                decrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 1)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 1)+"/"),pss);
                decrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 2)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 2)+"/"),pss);
                decrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 3)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 3)+"/"),pss);
                decrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 4)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 4)+"/"),pss);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        private void decrypteDir(File in, File out, String password) throws Exception{
            DirToFile dirToFile=new DirToFile();
            dirToFile.clear();
            dirToFile.cht(in);
            for(File f:dirToFile.getSPISOCFile()){
                Log.d("yertrxy","gdsfdgkyxrktrxjyrxiutrx");
                FileOperations.AESEncrypte(f,new File(out.getAbsolutePath()+"/"+f.getName()),password, FileOperations.ModeCipher.Decrypt);
            }
            for(File f:dirToFile.getSPISOCFile()){
                f.delete();
            }
            dirToFile.clear();
        }

        @Override
        protected void onPostExecute(Void unused) {
            //Toast.makeText(MainActivity.this, "Расшифровка завершена", Toast.LENGTH_SHORT).show();
            if(!Permission.isPermission(getApplicationContext())){
                //Toast.makeText(MainActivity.this, "Предоставьте разрешение", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Permission.class));
                finish();
            }else{
                //Toast.makeText(MainActivity.this, "Разрешение предоставлено", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Menu_list.class));
                finish();
            }
            super.onPostExecute(unused);
        }
    }
}
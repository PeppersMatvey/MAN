package get.newmaps.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class SettingsActivity extends AppCompatActivity {
    public static final int PICKFILE_REQUEST_CODE_IMPORT_ZONE=3274611;
    public static final int PICKFILE_REQUEST_CODE_XML_ZONE=3274621;
    public static final int PICKFILE_REQUEST_CODE_EXPORT=24846191;
    public static final int PICKFILE_REQUEST_CODE_IMAGE_ZONE=72141604;
    public static File patchExport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if(StorageApp.isNightMode(this)){
            findViewById(R.id.layout_settings).setBackgroundDrawable(getDrawable(R.drawable.black_gradient_settings));
            ((TextView)findViewById(R.id.set_password_settings)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.set_password_mode_settings)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.import_zone_settings)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.add_home_settings)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.add_state_settings)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.set_state_settings)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.add_map_settings)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.set_size_map_settings)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.exit_terminal_settings)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.import_settings)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.export_settings)).setTextColor(Color.WHITE);
        }else{
            findViewById(R.id.layout_settings).setBackgroundDrawable(getDrawable(R.drawable.white_gradient_settings));
            ((TextView)findViewById(R.id.set_password_settings)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.set_password_mode_settings)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.import_zone_settings)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.add_home_settings)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.add_state_settings)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.set_state_settings)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.add_map_settings)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.set_size_map_settings)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.exit_terminal_settings)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.import_settings)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.export_settings)).setTextColor(Color.BLACK);
        }
    }
    public void set_password(View view) {
        startActivity(new Intent(SettingsActivity.this,Password_Set.class));
    }

    public void terminal_input(View view) {
        startActivity(new Intent(SettingsActivity.this, Terminal_Activity.class));
    }

    public void infoVersion(View view) {
        new DialogShower().showDialog(new InfoDialog("Пока что эта функция доступна только в терминале. Но скоро она появиться и здесь ;-)"),this);
    }

    public void import_zone(View view) {
        Intent intent = new Intent(SettingsActivity.this, FileManager.class);
        intent.setType("file/*");
        FileManager.setFilter(".xml");
        FileManager.setCode(SettingsActivity.PICKFILE_REQUEST_CODE_XML_ZONE);
        startActivityForResult(intent, SettingsActivity.PICKFILE_REQUEST_CODE_XML_ZONE);
    }
    public void exportHome(View view) {
        Intent intent = new Intent(SettingsActivity.this, FileManager.class);
        intent.setType("file/*");
        FileManager.setFilter(".zip");
        FileManager.setCode(SettingsActivity.PICKFILE_REQUEST_CODE_EXPORT);
        startActivityForResult(intent, SettingsActivity.PICKFILE_REQUEST_CODE_EXPORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICKFILE_REQUEST_CODE_XML_ZONE:{
                try {
                    ImportZoneActivity.setAdresXML(FileManager.patchEnd);
                } catch (Exception e) {
                    Toast.makeText(this, "Произошла ошибка "+e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                startActivity(new Intent(SettingsActivity.this, ImportZoneActivity.class));
                break;
            }
            case PICKFILE_REQUEST_CODE_IMAGE_ZONE:{
                try {
                    ImportZoneImage.setPatch(FileManager.patchEnd);
                } catch (Exception e) {
                    Toast.makeText(this, "Произошла ошибка "+e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                startActivity(new Intent(SettingsActivity.this, ImportZoneImage.class));
                break;
            } case PICKFILE_REQUEST_CODE_EXPORT:{
                try {
                    patchExport=FileManager.patchEnd;
                    Log.d("ggggg",patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-patchExport.getName().length()));
                    Log.d("ggggg",patchExport.getName());
                    Log.d("jjjj",patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-patchExport.getName().length()));
                    //unpackZip(patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-patchExport.getName().length()),patchExport.getName(),null);
                    new File(patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4)+"/").mkdirs();
                    unpackZip(patchExport,patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4)+"/",patchExport.getName(),null);
                    exportHome();
                    DirToFile v=new DirToFile();
                    Log.d("del",patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-patchExport.getName().length())+patchExport.getName().substring(0,patchExport.getName().length()-4)+"/");
                    v.cht(patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-patchExport.getName().length())+patchExport.getName().substring(0,patchExport.getName().length()-4)+"/");
                    for(File f:v.getSPISOCFile()){
                        f.delete();
                    }
                    v.clear();
                    File f1=new File(patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-patchExport.getName().length())+patchExport.getName().substring(0,patchExport.getName().length()-4)+"/"+patchExport.getName().substring(0,patchExport.getName().length()-4));
                    f1.delete();
                    File f=new File(patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-patchExport.getName().length())+patchExport.getName().substring(0,patchExport.getName().length()-4)+"/");
                    f.delete();
                } catch (Exception e) {
                    Toast.makeText(this, "Произошла ошибка "+e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                //startActivity(new Intent(SettingsActivity.this, ImportZoneImage.class));
                break;
            }
        }
    }

    private static boolean unpackZip(File in,String path, String zipname,String patchOut) throws Exception {
        InputStream is;
        ZipInputStream zis;
        try {
            is = new FileInputStream(path + zipname);
        }catch (Exception e){
            e.printStackTrace();
            is=new FileInputStream(in);
        }
        zis = new ZipInputStream(new BufferedInputStream(is));
        ZipEntry ze;

        while((ze = zis.getNextEntry()) != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count;

            String filename = ze.getName();
            if(patchOut!=null){
                if(ze.isDirectory()) {
                    File directPath = new File(patchOut + zipname.substring(0,zipname.length()-4) + "/" + filename);
                    directPath.mkdirs();
                } else {
                    File directPath = new File(patchOut + zipname.substring(0,zipname.length()-4) + "/");
                    directPath.mkdir();
                    FileOutputStream fout = new FileOutputStream(patchOut +"/"+ filename);

                    // reading and writing
                    while((count = zis.read(buffer)) != -1)
                    {
                        baos.write(buffer, 0, count);
                        byte[] bytes = baos.toByteArray();
                        fout.write(bytes);
                        baos.reset();
                    }

                    fout.close();
                    zis.closeEntry();
                }
            }else{
                if(ze.isDirectory()) {
                    File directPath = new File(path + zipname.substring(0,zipname.length()-4) + "/" + filename);
                    //Log.d("ttttttt",directPath.getAbsolutePath());
                    directPath.mkdirs();
                } else {
                    File directPath = new File(path + zipname.substring(0,zipname.length()-4) + "/");
                    directPath.mkdir();
                    FileOutputStream fout = new FileOutputStream(path + filename);

                    // reading and writing
                    while((count = zis.read(buffer)) != -1)
                    {
                        baos.write(buffer, 0, count);
                        byte[] bytes = baos.toByteArray();
                        fout.write(bytes);
                        baos.reset();
                    }

                    fout.close();
                    zis.closeEntry();
                }
            }

        }

        zis.close();
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SettingsActivity.this, Menu_list.class));
        finish();
    }

    public void addMap(View view) {
        Intent intent = new Intent(SettingsActivity.this, FileManager.class);
        intent.setType("file/*");
        FileManager.setFilter(".png");
        FileManager.setCode(SettingsActivity.PICKFILE_REQUEST_CODE_IMAGE_ZONE);
        startActivityForResult(intent, SettingsActivity.PICKFILE_REQUEST_CODE_IMAGE_ZONE);
    }

    public void add_state(View view) {
        CreateAndResetState.setAdressState(null);
        startActivity(new Intent(SettingsActivity.this, CreateAndResetState.class));
    }

    public void setCursor(View view) {
        startActivity(new Intent(SettingsActivity.this,SetMapCursor.class));
        finish();
    }

    public void exit_app(View view) {
        try {
            new encrypteTask(new encrypteTask.END_OPERATIONS() {
                @Override
                public void finall() {
                    finishAffinity();
                }
            },true).startThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addHome(View view) {
        startActivity(new Intent(SettingsActivity.this, Add_One_Home.class));
    }

    public void refacteState(View view) {
        startActivity(new Intent(SettingsActivity.this, ListStateRefacte.class));
        finish();
    }
    public void exportHome(){
        DirToFile df=new DirToFile();
        df.cht(patchExport.getAbsolutePath()+"/");
        for(File f:df.getSPISOCFile()){
            //FileOperations.writeNullAndDelete(f);
        }
        df.clear();
        try {/*
            if(zipcorrected(patchExport))unpackZip(patchExport.getAbsolutePath() + "/", "z1.zip");
            if(zipcorrected(patchExport))unpackZip(patchExport.getAbsolutePath() + "/", "z2.zip");
            if(zipcorrected(patchExport))unpackZip(patchExport.getAbsolutePath() + "/", "z3.zip");
            if(zipcorrected(patchExport))unpackZip(patchExport.getAbsolutePath() + "/", "z4.zip");
            */

            unpackZip(patchExport,patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4) + "/", "z1.zip",CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)1));
            unpackZip(patchExport,patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4) + "/", "z2.zip",CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)2));
            unpackZip(patchExport,patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4) + "/", "z3.zip",CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)3));
            unpackZip(patchExport,patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4) + "/", "z4.zip",CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)4));
            unpackZip(patchExport,patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4) + "/", "state.zip",StorageApp.getAppPatch().getAbsolutePath()+"/state/");
            {
                DirToFile dirToFile = new DirToFile();
                dirToFile.cht(patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4)+"/state");
                for (File f1 : dirToFile.getSPISOCFile()) {
                    FileOperations.copy(f1, new File(StorageApp.getAppPatch().getAbsolutePath()+"/state/"));
                    //FileOperations.writeNullAndDelete(f1);
                }
                dirToFile.clear();
            }
            {
                DirToFile dirToFile = new DirToFile();
                dirToFile.cht(patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4)+"/z1");
                for (File f1 : dirToFile.getSPISOCFile()) {
                    FileOperations.copy(f1, new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 1)));
                    //FileOperations.writeNullAndDelete(f1);
                }
                dirToFile.clear();
            }
            {
                DirToFile dirToFile = new DirToFile();
                dirToFile.cht(patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4)+"/z2");
                for (File f1 : dirToFile.getSPISOCFile()) {
                    FileOperations.copy(f1, new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 2)));
                    //FileOperations.writeNullAndDelete(f1);
                }
                dirToFile.clear();
            }
            {
                DirToFile dirToFile = new DirToFile();
                dirToFile.cht(patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4)+"/z3");
                for (File f1 : dirToFile.getSPISOCFile()) {
                    FileOperations.copy(f1, new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 3)));
                    //FileOperations.writeNullAndDelete(f1);
                }
                dirToFile.clear();
            }
            {
                DirToFile dirToFile = new DirToFile();
                dirToFile.cht(patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4)+"/z4");
                for (File f1 : dirToFile.getSPISOCFile()) {
                    FileOperations.copy(f1, new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 4)));
                    //FileOperations.writeNullAndDelete(f1);
                }
                dirToFile.clear();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        DirToFile df1=new DirToFile();
        df1.cht(patchExport.getAbsolutePath().substring(0,patchExport.getAbsolutePath().length()-4)+"/");
        for(File f1:df1.getSPISOCFile()){
            //FileOperations.writeNullAndDelete(f1);
        }
        df1.clear();
        Toast.makeText(this, "Экспорт завершен", Toast.LENGTH_LONG).show();
    }
/*
    private boolean zipcorrected(File patchExport) {
        try {
            unpackZip(patchExport.getAbsolutePath(), patchExport.getName());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

 */

    public void importHome(View view) {
        try{
            ArrayList<File> itog_file=new ArrayList<>(5);
            //new File(getFilesDir().getAbsolutePath()+"/zoneszip/").mkdirs();
            StorageApp.setAppPatch(getFilesDir());
            {
                DirToFile df=new DirToFile();
                df.cht(new File(StorageApp.getAppPatch().getAbsolutePath()+"/state/"));
                new File(getFilesDir()+"/zoneszip/").mkdirs();
                packZip(df.getSPISOCFile(),new File(getFilesDir()+CreatePatch.DIR_STATE_ZIP_));
                df.clear();
                itog_file.add(new File(getFilesDir()+CreatePatch.DIR_STATE_ZIP_));
            }


            if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)1)).exists()){
                DirToFile df=new DirToFile();
                df.cht(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)1));
                packZip(df.getSPISOCFile(),new File(getFilesDir()+CreatePatch.DIR_ZONE1_ZIP));
                df.clear();
                itog_file.add(new File(getFilesDir()+CreatePatch.DIR_ZONE1_ZIP));
            }
            if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)2)).exists()){
                DirToFile df1=new DirToFile();
                df1.cht(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)2));
                packZip(df1.getSPISOCFile(),new File(getFilesDir()+CreatePatch.DIR_ZONE2_ZIP));
                df1.clear();
                itog_file.add(new File(getFilesDir()+CreatePatch.DIR_ZONE2_ZIP));
            }
            if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)3)).exists()){
                DirToFile df=new DirToFile();
                df.cht(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)3));
                packZip(df.getSPISOCFile(),new File(getFilesDir()+CreatePatch.DIR_ZONE3_ZIP));
                df.clear();
                itog_file.add(new File(getFilesDir()+CreatePatch.DIR_ZONE3_ZIP));
            }
            if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)4)).exists()){
                DirToFile df=new DirToFile();
                df.cht(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)4));
                packZip(df.getSPISOCFile(),new File(getFilesDir()+CreatePatch.DIR_ZONE4_ZIP));
                df.clear();
                itog_file.add(new File(getFilesDir()+CreatePatch.DIR_ZONE4_ZIP));
            }
            packZip(itog_file, new File (Environment.getExternalStorageDirectory().getAbsolutePath()+"/rezerv_notes.zip"));
            FileOperations.writeNullAndDelete(new File(getFilesDir()+CreatePatch.DIR_ZONE1_ZIP));
            FileOperations.writeNullAndDelete(new File(getFilesDir()+CreatePatch.DIR_ZONE2_ZIP));
            FileOperations.writeNullAndDelete(new File(getFilesDir()+CreatePatch.DIR_ZONE3_ZIP));
            FileOperations.writeNullAndDelete(new File(getFilesDir()+CreatePatch.DIR_ZONE4_ZIP));
            FileOperations.writeNullAndDelete(new File(getFilesDir()+CreatePatch.DIR_STATE_ZIP_));
            Toast.makeText(this, "Резервная копия создана по адресу "+new File (Environment.getExternalStorageDirectory().getAbsolutePath()+"/rezerv_notes.zip").getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Резервная копия не создана", Toast.LENGTH_SHORT).show();
        }
    }
    public void packZip(ArrayList<File> ss, File itog) throws Exception{
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(itog));
        //int i = 1;
        for (File f : ss) {
            FileInputStream fis = new FileInputStream(f);
            //i++;
            try {
                ZipEntry entry1 = new ZipEntry(f.getName());
                zout.putNextEntry(entry1);
                // считываем содержимое файла в массив byte
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                // добавляем содержимое к архиву
                zout.write(buffer);
                // закрываем текущую запись для новой записи
                zout.closeEntry();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        zout.close();
    }
    public void set_password_mode(View v){
        DialogShower dialogShower=new DialogShower();
        dialogShower.showDialog(new WarningDialog("Вы хотите отключить пароль? При отказе, если пароль был установлен, вам надо будет установить пароль дабы избежать ошибок с системой безопасности", "Включить", "Отключить", new WarningDialog.Canceled() {
            @Override
            public void cancel() {
                FileOutputStream fos= null;
                try {
                    fos = new FileOutputStream(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,CreatePatch.SPEC_CODE));
                    fos.write(MainActivity.testPass.getBytes());
                    fos.close();
                    FileOperations.AESEncrypte(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, CreatePatch.SPEC_CODE)), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, CreatePatch.SPEC_CODE)),PasswordFormat.gt("standarte_pass"), FileOperations.ModeCipher.Encrypt);
                    FileWriter fw=new FileWriter(new File(getFilesDir().getAbsolutePath()+"/pasword"));
                    fw.write(PasswordFormat.gt("standarte_pass"));
                    fw.close();
                    Toast.makeText(SettingsActivity.this, "Стандартный пароль установлен", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(SettingsActivity.this, "ОШИБКА", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new WarningDialog.Run() {
            @Override
            public void run() {
                if(!MainActivity.ifStandartPassword()){
                    startActivity(new Intent(SettingsActivity.this,Password_Set.class));
                }else{
                    return;
                }
            }
        }),this);
    }
}
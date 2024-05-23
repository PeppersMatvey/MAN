package get.newmaps.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class ImportZoneActivity extends AppCompatActivity {
static private File adresXML;
    static private ProgressBar publishProgress;
    static private TextView glavText;
    static private TextView progressText;

    public static void setAdresXML(File adresXML) {
        ImportZoneActivity.adresXML = adresXML;
    }

    public static void setPublishProgress(ProgressBar publishProgress) {
        ImportZoneActivity.publishProgress = publishProgress;
    }

    public static void setGlavText(TextView glavText) {
        ImportZoneActivity.glavText = glavText;
    }

    public static void setProgressText(TextView progressText) {
        ImportZoneActivity.progressText = progressText;
    }

    @Override
    public void onBackPressed() {
        DialogInfoMode dialogInfoMode=new WarningDialog("Вы действительно хотите прервать процесс? Вы сможете начать потом этот процесс заного", "Прервать", "Продолжать", new WarningDialog.Canceled() {
            @Override
            public void cancel() {
                startActivity(new Intent(ImportZoneActivity.this, Menu_list.class));
                finish();
            }
        }, new WarningDialog.Run() {
            @Override
            public void run() {
                return;
            }
        });
        new DialogShower().showDialog(dialogInfoMode,this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_zone_activiti);
        Intent intent = new Intent(ImportZoneActivity.this, InputZoneInfo.class);
        intent.setType("file/*");
        startActivityForResult(intent, SettingsActivity.PICKFILE_REQUEST_CODE_IMPORT_ZONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        publishProgress=findViewById(R.id.progress_import);
        glavText=findViewById(R.id.state_import);
        progressText=findViewById(R.id.progress_import_textView);
        if(StorageApp.isNightMode(this)){
            glavText.setTextColor(getColor(R.color.white));
            progressText.setTextColor(getColor(R.color.white));
        }else{
            glavText.setTextColor(getColor(R.color.black));
            progressText.setTextColor(getColor(R.color.black));
        }
    }
    private volatile int kw;
    private volatile int zone;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        XMLControl.Reader.context=this;
        if(resultCode==SettingsActivity.PICKFILE_REQUEST_CODE_IMPORT_ZONE){
            zone = data.getIntExtra(getString(R.string.zonesinfonumberzone),0);
            kw = data.getIntExtra("zonesInfoNumberKW",0);
        }
        if(new XMLControl.Reader(adresXML,(byte) zone).getListHome().size()==0){
            //Toast.makeText(this, adresXML.getPath(), Toast.LENGTH_LONG).show();
            //Toast.makeText(this, adresXML.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Ошибка чтения файла", Toast.LENGTH_SHORT).show();
            //return;
        }
        new AsynkImport().execute();
    }

    class AsynkImport extends AsyncTask<Object,String,Void>{
        @Override
        protected Void doInBackground(Object... voids) {
            ArrayList<XMLControl.Reader.Home> arrayList=new XMLControl.Reader(adresXML,(byte)zone).getListHome();
            int i=0;
            for(XMLControl.Reader.Home oneHomes: arrayList){
                try {
                    new Imports().addHomes(kw,new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) zone)), oneHomes.strit, oneHomes.nomerHome, Integer.parseInt(oneHomes.nomerApar), oneHomes.str, oneHomes.X, oneHomes.Y);
                    i++;
                    if(i==arrayList.size()){
                        publishProgress("Готово!",createText(i,arrayList.size()),i+"","EXIT");
                    }else{
                        publishProgress("Подождите...",createText(i,arrayList.size()),i+"",arrayList.size()+"");
                    }

                    //MySystemT.println(i+" из "+arrayList.size(),R.color.blue,Terminal_Activity.RESOURSE_INPUT);
                } catch (Exception e) {
                    //MySystemT.println(" Случилась ошибка: "+e.toString(),R.color.red,Terminal_Activity.RESOURSE_INPUT);
                    //MySystemT.println(" Программа перезапущенна",0,Terminal_Activity.RESOURSE_INPUT);
                    e.printStackTrace();
                    continue;
                }
            }
            return null;
        }

        private String createText(int i, int size) {
            String str=i+" / "+size+"\n";
            double unoprocent=((double) size)/100.0;
            int proc= (int) (i/unoprocent);
            str+=proc+"%";
            return str;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(values[3].equals("EXIT")){
                Toast.makeText(ImportZoneActivity.this, "Импортирование завершено!", Toast.LENGTH_LONG).show();
                try {
                    new TimerMiliSecond().startTimer(1500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //finish();
                //finishAffinity();
                FilterList.load();
                startActivity(new Intent(ImportZoneActivity.this, Menu_list.class));
                finish();
                return;
            }
            publishProgress.setMax(Integer.parseInt(values[3]));
            publishProgress.setProgress(Integer.parseInt(values[2]));
            progressText.setText(values[1]);
            glavText.setText(values[0]);
            super.onProgressUpdate(values);
        }
    }
}
package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

public class Permission extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        findViewById(R.id.reguest_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(Permission.this,101);
            }
        });
        new has_permission_action(getApplicationContext()).execute();
    }
    public static boolean isPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(StorageApp.isNightMode(this)){
            findViewById(R.id.reguest_permission).setBackgroundColor(Color.BLACK);
        }else{
            findViewById(R.id.reguest_permission).setBackgroundColor(Color.WHITE);
        }
    }

    public static void requestPermissions(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", activity.getPackageName())));
                activity.startActivityForResult(intent, requestCode);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                activity.startActivityForResult(intent, requestCode);
            }
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    requestCode);
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    class has_permission_action extends AsyncTask<Void,Void,Void>{
        private final Context context;

        has_permission_action(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while(true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isPermission(context)) return null;
            }
        }

        @Override
        protected void onPostExecute(Void unused) {
            //Toast.makeText(Permission.this, "Разрешение получено", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Permission.this, Menu_list.class));
            finish();
            super.onPostExecute(unused);
        }
    }
}
package get.newmaps.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;

public class MAIN_MAIN_ACTIVITY extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_action);
        StorageApp.setAppPatch(getFilesDir());
        try {
            new StartApp().run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(StorageApp.isNightMode(this)){
            findViewById(R.id.layout_splash).setBackgroundDrawable(getDrawable(R.drawable.black_gradient_settings));
        }else{
            findViewById(R.id.layout_splash).setBackgroundDrawable(getDrawable(R.drawable.white_gradient_settings));
        }

        if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,CreatePatch.SPEC_CODE)).exists()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dalee();
                }
            },1000);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dalee();
                }},3000);
        }
    }
    public void dalee(){
        if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,CreatePatch.SPEC_CODE)).exists()){
            startActivity(new Intent(MAIN_MAIN_ACTIVITY.this,MainActivity.class));
            finish();
        }else{
            startActivity(new Intent(MAIN_MAIN_ACTIVITY.this,Password_Set.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {;
    }
}
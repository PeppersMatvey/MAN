package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class ImportZoneImage extends AppCompatActivity {

    RadioButton radioGroup1;
    RadioButton radioGroup2;
    RadioButton radioGroup3;
    RadioButton radioGroup4;
    int id;
    AppCompatButton sawe;
    public static volatile File patch;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_zone_image);
        View.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=view.getId();
            }
        };
        radioGroup1=findViewById(R.id.radioB_1);
        radioGroup2=findViewById(R.id.radioB_2);
        radioGroup3=findViewById(R.id.radioB_3);
        radioGroup4=findViewById(R.id.radioB_4);
        if(StorageApp.isNightMode(this)){
            radioGroup1.setTextColor(getColor(R.color.white));
            radioGroup2.setTextColor(getColor(R.color.white));
            radioGroup3.setTextColor(getColor(R.color.white));
            radioGroup4.setTextColor(getColor(R.color.white));
        }else{
            radioGroup1.setTextColor(getColor(R.color.black));
            radioGroup2.setTextColor(getColor(R.color.black));
            radioGroup3.setTextColor(getColor(R.color.black));
            radioGroup4.setTextColor(getColor(R.color.black));
        }
        radioGroup1.setOnClickListener(clickListener);
        radioGroup2.setOnClickListener(clickListener);
        radioGroup3.setOnClickListener(clickListener);
        radioGroup4.setOnClickListener(clickListener);
        sawe=findViewById(R.id.button_import_zone_map);
        sawe.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View view) {
                try {
                    Bitmap b = BitmapFactory.decodeStream(new BufferedInputStream(new FileInputStream(patch)));
                } catch (Exception e) {
                    Toast.makeText(ImportZoneImage.this, "Проверьте является ли файл изображением", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return;
                }
                boolean error = false;
                if (id == R.id.radioB_1) {
                    try {
                        FileOperations.copy(patch, new File(CreatePatch.getPatchImage(1)));
                        error = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        error = true;
                    }
                } else if (id == R.id.radioB_2) {
                    try {
                        FileOperations.copy(patch, new File(CreatePatch.getPatchImage(2)));
                        error = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        error = true;
                    }
                } else if (id == R.id.radioB_3) {
                    try {
                        FileOperations.copy(patch, new File(CreatePatch.getPatchImage(3)));
                        error = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        error = true;
                    }
                } else if (id == R.id.radioB_4) {
                    try {
                        FileOperations.copy(patch, new File(CreatePatch.getPatchImage(4)));
                        error = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        error = true;
                    }
                }
                if (error) {
                    Toast.makeText(ImportZoneImage.this, "Во время добавления произошла ошибка", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ImportZoneImage.this, "Добавление успешно завершено", Toast.LENGTH_LONG).show();
                }
                startActivity(new Intent(ImportZoneImage.this, SettingsActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ImportZoneImage.this, Menu_list.class));
        finish();
    }

    public static void setPatch(File patch) {
        ImportZoneImage.patch = patch;
    }
}
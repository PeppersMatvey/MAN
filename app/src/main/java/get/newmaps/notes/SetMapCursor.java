package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SetMapCursor extends AppCompatActivity {
private SeekBar seekBar;
    private SeekBar seekBar2;
private AppCompatButton save_cursor;
private static TextView textView;
    private static TextView textView2;
private volatile int size;
    private volatile int sizeT;
ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_map_cursor);
        seekBar=findViewById(R.id.seekBar);
        seekBar2=findViewById(R.id.seekBar2);
        save_cursor=findViewById(R.id.button_save_cursor);
        imageView=findViewById(R.id.cursor_image);
        textView=findViewById(R.id.text_size);
        textView2=findViewById(R.id.text_size2);
        try {
            File size_int = new File(StorageApp.getAppPatch().getAbsolutePath() + CreatePatch.DIR_SETTINGS_MAP_SIZE);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(size_int));
            size=ois.readInt();
            seekBar.setProgress(size);
            textView.setText("Размер: "+size);
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            File size_int = new File(StorageApp.getAppPatch().getAbsolutePath() + CreatePatch.DIR_SETTINGS_MAP_SIZE_TEXT);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(size_int));
            sizeT=ois.readInt();
            seekBar2.setProgress(sizeT);
            textView2.setText("Размер: "+sizeT);
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        int color=0;
        if(StorageApp.isNightMode(SetMapCursor.this)){
            color=Color.WHITE;
            seekBar2.setThumb(getDrawable(R.drawable.baseline_back_hand_24_white));
            seekBar.setThumb(getDrawable(R.drawable.baseline_back_hand_24_white));
        }else{
            color=Color.BLACK;
            seekBar2.setThumb(getDrawable(R.drawable.baseline_back_hand_24));
            seekBar.setThumb(getDrawable(R.drawable.baseline_back_hand_24));
        }
        imageView.setImageBitmap(MapAction.printf(Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888), 100, 100, color, size,null));
    }

    @Override
    protected void onResume() {
        super.onResume();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                size=seekBar.getProgress();
                textView.setText("Размер: "+size);
                int color=0;
                try {
                    if(StorageApp.isNightMode(SetMapCursor.this)){
                        color=Color.WHITE;
                    }else{
                        color=Color.BLACK;
                    }
                    imageView.setImageBitmap(MapAction.printf(Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888), 100, 100, color, size,null));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sizeT=seekBar.getProgress();
                textView2.setText("Размер текста: "+sizeT);
                int color=0;
                try {
                    if(StorageApp.isNightMode(SetMapCursor.this)){
                        color=Color.WHITE;
                    }else{
                        color=Color.BLACK;
                    }
                    //imageView.setImageBitmap(MapAction.printf(Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888), 100, 100, color, size,null));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        save_cursor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    File size_int = new File(StorageApp.getAppPatch().getAbsolutePath() + CreatePatch.DIR_SETTINGS_MAP_SIZE);
                    size_int.delete();
                    MapAction.setSize(size);
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(size_int));
                    oos.writeInt(size);
                    oos.close();
                    Toast.makeText(SetMapCursor.this, "Насторойки сохранены", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }catch (Exception e){
                    Toast.makeText(SetMapCursor.this, "Приозошла ошибка сохранения", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                try {
                    File size_int = new File(StorageApp.getAppPatch().getAbsolutePath() + CreatePatch.DIR_SETTINGS_MAP_SIZE_TEXT);
                    size_int.delete();
                    MapAction.setSize(sizeT);
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(size_int));
                    oos.writeInt(sizeT);
                    oos.close();
                    Toast.makeText(SetMapCursor.this, "Насторойки сохранены", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }catch (Exception e){
                    Toast.makeText(SetMapCursor.this, "Приозошла ошибка сохранения", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SetMapCursor.this, Menu_list.class));
        finish();
    }
}
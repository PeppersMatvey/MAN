package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MapAction extends AppCompatActivity {
    static boolean modeVisibleButton=false;
    public static ArrayList<MapInfo> list_add_home=new ArrayList<>();
    public static HashMap<String,MapInfo> info2HashMap=new HashMap<>();
    static Imports.OneHome oneHomepublic=null;
    static int I;
    static int sizeText=27;
    //public static final int sizeText=26;
    private static int Size=20;
    public static void setSize(int size){
        if(!(size<10||size>50)){
            Size=size;
        }else{
            Size=20;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MapAction.this, Menu_list.class));
        finish();
    }

    static SubsamplingScaleImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_action);
        try{
            File size_int = new File(StorageApp.getAppPatch().getAbsolutePath() + CreatePatch.DIR_SETTINGS_MAP_SIZE_TEXT);
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(size_int));
            sizeText=ois.readInt();
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        imageView = findViewById(R.id.imageView);
        StorageApp.setAppPatch(getFilesDir());
        AppCompatButton appCompatButton=findViewById(R.id.back_buttom);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapAction.this, Menu_list.class));
                finish();
            }
        });
        if(StorageApp.isNightMode(this)){
            appCompatButton.setBackgroundDrawable(getDrawable(R.drawable.baseline_arrow_back_ios_new_24_white));
        }else{
            appCompatButton.setBackgroundDrawable(getDrawable(R.drawable.baseline_arrow_back_ios_new_24));
        }

        if(!modeVisibleButton){
            Size=40;
            findViewById(R.id.filter_searsh_map).setEnabled(false);
            findViewById(R.id.filter_searsh_map).setVisibility(View.INVISIBLE);
            ArrayList<Imports.OneHome> oonn=new ArrayList<Imports.OneHome>(1);
            oonn.add(oneHomepublic);
            setImage(oneHomepublic.zone,oonn,Size);
            File size_int=new File(StorageApp.getAppPatch().getAbsolutePath()+CreatePatch.DIR_SETTINGS_MAP_SIZE);
            try{
                ObjectInputStream ois=new ObjectInputStream(new FileInputStream(size_int));
                MapAction.setSize(ois.readInt());
                ois.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            File size_int=new File(StorageApp.getAppPatch().getAbsolutePath()+CreatePatch.DIR_SETTINGS_MAP_SIZE);
            try{
                ObjectInputStream ois=new ObjectInputStream(new FileInputStream(size_int));
                MapAction.setSize(ois.readInt());
                ois.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            FilterList.setSearsh_str("");
            FilterList.setState(null);
            DialogShower.v=findViewById(R.id.filter_searsh_map);
            DialogShower dialogShower=new DialogShower();
            DialogShower.set_mod=true;
            try {
                dialogShower.showDialog(new FilterStateMapDialog(new AddState().readState(), new FilterStateMapDialog.OnClic() {
                    @Override
                    public void clic(State[] state,int zone) {
                        Log.d("testtest","1");
                        cont=MapAction.this;
                        clicDialogMap(state,zone);
                        //startActivity(new Intent(MapAction.this,LoadAction.class));
                        //LoadAction.state=state;
                        //LoadAction.zone=zone;
                    }
                }),MapAction.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            findViewById(R.id.filter_searsh_map).setEnabled(true);
            findViewById(R.id.filter_searsh_map).setVisibility(View.VISIBLE);
            findViewById(R.id.filter_searsh_map).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FilterList.setSearsh_str("");
                    FilterList.setState(null);
                    DialogShower dialogShower=new DialogShower();
                    DialogShower.set_mod=false;
                    try {
                        dialogShower.showDialog(new FilterStateMapDialog(new AddState().readState(), new FilterStateMapDialog.OnClic() {
                            @Override
                            public void clic(State[] state,int zone) {
                                cont=MapAction.this;
                                Log.d("testtest","2");
                                clicDialogMap(state,zone);
                                //startActivity(new Intent(MapAction.this,LoadAction.class));
                                //LoadAction.state=state;
                                //LoadAction.zone=zone;
                            }
                        }),MapAction.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    static MapAction cont;
    @SuppressLint("ResourceType")
    public void clicDialogMap(State[] state, int zone){

        /*
        LayoutInflater inflater = cont.getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_load_action,
                (ViewGroup) cont.findViewById(R.id.layaut_load));

        Toast toast = new Toast(cont.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

         */
        /*
        Toast toast = Toast.makeText(cont.getApplicationContext(),
                "Загрузка... Пожалуйста подождите... ", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ProgressBar p=new ProgressBar(cont);
        p.setMax(50);
        p.setProgress(49);
        p.setIndeterminate(true);
        toastContainer.addView(cont.findViewById(R.layout.activity_load_action));
        // Устанавливаем прозрачность у контейнера
        toastContainer.setBackgroundColor(Color.TRANSPARENT);
        toast.show();

         */
        //Toast t=new Toast(cont);
        //t.setView(new ProgressBar(cont));
        //t.show();
        //Toast.makeText(cont, "Загрузка...Пожалуйста подождите...", Toast.LENGTH_LONG).show();
        //Toast.makeText(cont, MapAction.class, "Загрузка...Подождите...", Toast.LENGTH_LONG).show();
        ArrayList<Imports.OneHome> oneHomeArrayList=new ArrayList<>();
        for(State st:state){
            FilterList.setState(st);
            for(Imports.OneHome home:FilterList.getFilterList()){
                if(home.zone==zone){
                    oneHomeArrayList.add(home);
                }
            }
        }
        setImage(zone,oneHomeArrayList,Size);
    }
    protected static void setImage(int zone, ArrayList<Imports.OneHome> filterList, int size){
        ViewMap(filterList,new File(CreatePatch.getPatchImage(zone)),size);
    }
    protected static void ViewMap(ArrayList<Imports.OneHome> filterList,File zoneImage, int size) {
        Bitmap bitmap = null;
        final Bitmap[] bitmap1 = {null};
        try {
            /*
            if(zoneImage.exists()){
                Log.d("tefff","test");
            }else{
                Log.d("tefff","NOTest");
            }
             */

            //bitmap=BitmapFactory.decodeFile(zoneImage.getAbsolutePath());
            //bitmap1[0]=BitmapFactory.decodeFile(zoneImage.getAbsolutePath());
            bitmap=BitmapFactory.decodeStream(new FileInputStream(zoneImage));
            //bitmap1[0]=BitmapFactory.decodeStream(new FileInputStream(zoneImage)).copy(Bitmap.Config.ARGB_8888,true);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(cont, "Карты не загружены", Toast.LENGTH_LONG).show();
            cont.startActivity(new Intent(cont, Menu_list.class));
            cont.finish();
            return;
        }

        ArrayList<MapInfo> f1=new ArrayList<>();
        ArrayList<MapInfo> f2=new ArrayList<>();
        for(Imports.OneHome oneHome:filterList){
            f1.add(new MapInfo(oneHome,sizeText));
        }
        for(Imports.OneHome oneHome:filterList){
            f2.add(new MapInfo(oneHome,sizeText));
        }
        for(int i=0;i<f1.size();i++){
            for(int ii=0;ii<f1.size();ii++){
                if(contains(f1,f2.get(ii))){
                    if(f2.get(ii).oneHome.KV==-1){
                        continue;
                    }
                    if(f1.get(i).oneHome.KV==f2.get(ii).oneHome.KV){
                        continue;
                    }
                    String str=f1.get(i).oneHome.KV+"";
                    //MapInfo mapInfo=new MapInfo(f1.get(i).oneHome,str+"  \n  "+f2.get(ii).oneHome.KV+"");
                    MapInfo mapInfo=new MapInfo(f1.get(i).oneHome,0);
                    f1.set(i,mapInfo);
                }
            }
        }
        /*
        ArrayList<MapInfo> f1=new ArrayList<>();
        for(int i=0;i<filterList.size();i++){
            Imports.OneHome home=filterList.get(i);
            boolean add=false;
            Imports.OneHome home1=null;
            for(MapInfo oneHome:f1){
                if(stringObraz(oneHome.oneHome).equals(stringObraz(home))){
                    MapInfo mapInfo=new MapInfo(home,home1.KV+"");
                    f1.set(i,mapInfo);
                }else{
                    home1=home;
                    f1.add(new MapInfo(home1,""));
                }
*/
            /*
            if(add){
                MapInfo mapInfo=new MapInfo(home,home1.KV+"");
                f1.remove(i);
                f1.add(mapInfo);
            }
        }
*/
        bitmap1[0] = bitmap.copy( Bitmap.Config.ARGB_8888 , true);
        imageView.setImage(ImageSource.bitmap(bitmap1[0]));
        MapInfo[] h12 = {null};
        for(int i=0;i<f1.size(); i++) {
            MapInfo oneHome=f1.get(i);
            I=i;
            h12[0] = oneHome;
            try {
                bitmap1[0] = printf(bitmap1[0], (int) Double.parseDouble(h12[0].oneHome.X), (int) Double.parseDouble(h12[0].oneHome.Y),new ReadState().getState(oneHome.oneHome.STATE).colorState,size,oneHome);
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
        info2HashMap.clear();
        info2HashMap=null;
        info2HashMap=new HashMap<>();
        //list_add_home.clear();
        //list_add_home=null;
    }

    private static boolean contains(ArrayList<MapInfo> f1, MapInfo mapInfo) {
        for (MapInfo m:f1){
            if(stringObraz(m.oneHome).equals(stringObraz(mapInfo.oneHome))){
                return true;
            }
        }
        return false;
    }
    private static boolean contains1(ArrayList<Imports.OneHome> f1, Imports.OneHome mapInfo) {
        for (Imports.OneHome m:f1){
            if(stringObraz(m).equals(stringObraz(mapInfo))){
                return true;
            }
        }
        return false;
    }
    private static boolean contains(ArrayList<MapInfo> f1, Imports.OneHome mapInfo) {
        for (MapInfo m:f1){
            if(stringObraz(m.oneHome).equals(stringObraz(mapInfo))){
                return true;
            }
        }
        return false;
    }

    //public static Bitmap printf(Bitmap gc,int XX,int YY,int color){
        /// TODO: 27.08.2023
    public static String stringObraz(Imports.OneHome oneHome){
        return oneHome.STRIT_NAME+oneHome.HOME_NOMER;
    }
    public static Bitmap printf(Bitmap gc,int XX,int YY,int color, int size, MapInfo nomer_home){
        if(XX<=30|YY<=30){
            return gc;
        }
        if(XX==0&YY==0){
            return gc;
        }
        for(int i=YY;i< YY+size;i++){
        //for(int i=YY;i< YY+20;i++){
            for(int ii=1;ii<i-YY;ii++){
                gc.setPixel(XX-ii,i, Color.BLACK);
                gc.setPixel(XX+ii,i, Color.BLACK);
                gc.setPixel(XX,i, Color.BLACK);
                if(ii<i-(YY+2)){
                    gc.setPixel(XX-ii,i, color);
                    gc.setPixel(XX+ii,i, color);
                }
            }
        }
        if(nomer_home==null){
            return gc;
        }
        if(nomer_home.oneHome.KV==-1){
            return gc;
        }
        if(!info2HashMap.containsKey((XX+"")+YY)){
            info2HashMap.put((XX+"")+YY,nomer_home);
            int ots=info2HashMap.get((XX+"")+YY).kv;
            Canvas canvas=new Canvas(gc);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            paint.setColor(color);
            paint.setTextSize(sizeText);
            canvas.drawText(String.valueOf(nomer_home.oneHome.KV), XX-size/2, YY+size+ots, paint);
        }else{
            MapInfo mapInfoe=info2HashMap.get((XX+"")+YY);
            mapInfoe.kv=mapInfoe.kv+sizeText;
            //info2HashMap.replace()
            Canvas canvas=new Canvas(gc);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            paint.setColor(color);
            paint.setTextSize(sizeText);
            canvas.drawText(String.valueOf(nomer_home.oneHome.KV), XX-size/2, YY+size+ mapInfoe.kv, paint);
        }
        /*
        if(!contains(list_add_home,nomer_home)){
            list_add_home.add(nomer_home);
            Canvas canvas=new Canvas(gc);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
            canvas.drawText(String.valueOf(nomer_home.oneHome.KV), XX-size/2, YY+size+nomer_home.kv, paint);
            //nomer_home.kv=nomer_home.kv+20;
            //nomer_home.plus();
            //nomer_home.plus();
        }else{
            nomer_home.kv=nomer_home.kv+20;
            Canvas canvas=new Canvas(gc);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
            canvas.drawText(String.valueOf(nomer_home.oneHome.KV), XX-size/2, YY+size+nomer_home.kv, paint);
            //nomer_home.kv=nomer_home.kv+20;
            //nomer_home.kv=nomer_home.kv+20;
            //nomer_home.plus();
            //list_add_home.set(I,nomer_home);

            list_add_home.add(nomer_home);
            nomer_home.plus();
            //list_add_home.set(I,nomer_home);
            Canvas canvas=new Canvas(gc);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
            canvas.drawText(String.valueOf(nomer_home.oneHome.KV), XX-size/2, YY+size+nomer_home.kv, paint);
            list_add_home.add(nomer_home);
            nomer_home.plus();
        }

         */
        return gc;
    }
    static class MapInfo{
        Imports.OneHome oneHome;
        int kv=20;

        MapInfo(Imports.OneHome oneHome,int str) {
            this.oneHome = oneHome;
            //this.kv=str+"\n"+kv;
            this.kv=str+kv;
        }
        MapInfo() {
        }
        void plus(){
            kv=kv+20;
        }
    }
}
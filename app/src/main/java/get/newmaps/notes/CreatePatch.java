package get.newmaps.notes;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CreatePatch {
    public static final String DIR_SETTINGS="/sett";
    public static final String DIR_SETTINGS_MAP="/sett/map";
    public static final String DIR_SETTINGS_MAP_SIZE="/sett/map/SIZE.int";
    public static final String DIR_SETTINGS_MAP_SIZE_TEXT="/sett/map/SIZE_TEXT.int";
    public static final String DIR_ZONE_ZIP="/zoneszip/";
    public static final String DIR_ZONE1_ZIP="/zoneszip/z1.zip";
    public static final String DIR_PASS="/timepass.ps";
    public static final String DIR_STATE_ZIP_="/zoneszip/state.zip";
    public static final String DIR_ZONE2_ZIP="/zoneszip/z2.zip";
    public static final String DIR_ZONE3_ZIP="/zoneszip/z3.zip";
    public static final String DIR_ZONE4_ZIP="/zoneszip/z4.zip";
    public static final String DIR_ZONE1_IMAGE="/zonesimage/z1.png";
    public static final String DIR_ZONE2_IMAGE="/zonesimage/z2.png";
    public static final String DIR_ZONE3_IMAGE="/zonesimage/z3.png";
    public static final String DIR_ZONE4_IMAGE="/zonesimage/z4.png";
    public static final String DIR_ZONE1_IMAGE_E="/zonesimage/z1.pngE";
    public static final String DIR_ZONE2_IMAGE_E="/zonesimage/z2.pngE";
    public static final String DIR_ZONE3_IMAGE_E="/zonesimage/z3.pngE";
    public static final String DIR_ZONE4_IMAGE_E="/zonesimage/z4.pngE";
    public static final String DIR_ZONE1="/zones/z1";
    public static final String DIR_ZONE2="/zones/z2";
    public static final String DIR_ZONE3="/zones/z3";
    public static final String DIR_ZONE4="/zones/z4";
    public static final String ENCRYPTE_DIR_ZONE1="/zones/en_z1";
    public static final String ENCRYPTE_DIR_ZONE2="/zones/en_z2";
    public static final String ENCRYPTE_DIR_ZONE3="/zones/en_z3";
    public static final String ENCRYPTE_DIR_ZONE4="/zones/en_z4";
    public static final byte SPEC_CODE=-14;
    public static int getZone(File f){
        String patch=f.getParent();
        //Log.d("testtt",patch);
        //Log.d("testtt",StorageApp.getAppPatch().getAbsolutePath()+DIR_ZONE1);
        if(patch.equals(StorageApp.getAppPatch().getAbsolutePath()+DIR_ZONE1)){
            return 1;
        }
        if(patch.equals(StorageApp.getAppPatch().getAbsolutePath()+DIR_ZONE2)){
            return 2;
        }
        if(patch.equals(StorageApp.getAppPatch().getAbsolutePath()+DIR_ZONE3)){
            return 3;
        }
        if(patch.equals(StorageApp.getAppPatch().getAbsolutePath()+DIR_ZONE4)){
            return 4;
        }
        return 0;
    }
    public static Properties readPropertes(File f) throws Exception{
        Properties properties = new Properties();
        properties.load(new FileReader(f));
        return properties;
    }
    public static void sawePropertes(Properties p,File f) throws IOException {
        p.store(new FileOutputStream(f),"");
    }
    public static String getPatchImageE(int i) {
        String str=StorageApp.getAppPatch().getAbsolutePath();
        switch (i){
            case 1: return str+DIR_ZONE1_IMAGE_E;
            case 2: return str+DIR_ZONE2_IMAGE_E;
            case 3: return str+DIR_ZONE3_IMAGE_E;
            case 4: return str+DIR_ZONE4_IMAGE_E;
            default: return null;
        }
    }

    enum MODE_TYPE{
        ENCRYPTE,
        DECRYPTE;
    }
    public static String getPatch(MODE_TYPE m,byte zone){
        String str=StorageApp.getAppPatch().getAbsolutePath();
        //String str = Environment.getExternalStorageDirectory().getAbsolutePath();
        if(SPEC_CODE==zone){
            switch (m){
                case DECRYPTE:
                    new File(str+"/de_pass/").mkdirs();
                    return str+"/de_pass/test.txt";
                case ENCRYPTE:
                    new File(str+"/en_pass/").mkdirs();
                    return str+"/en_pass/test.txt";
            }
        }
        new File(str+"/zones/").mkdirs();
        new File(str+"/zonesimage/").mkdirs();
        new File(str+DIR_ZONE1).mkdirs();
        new File(str+DIR_ZONE2).mkdirs();
        new File(str+DIR_ZONE3).mkdirs();
        new File(str+DIR_ZONE4).mkdirs();
        new File(str+ENCRYPTE_DIR_ZONE1).mkdirs();
        new File(str+ENCRYPTE_DIR_ZONE2).mkdirs();
        new File(str+ENCRYPTE_DIR_ZONE3).mkdirs();
        new File(str+ENCRYPTE_DIR_ZONE4).mkdirs();
        /*
        try{
            FileOutputStream fos=new FileOutputStream(str+DIR_ZONE1+"/test.txt");
            fos.write("Приветик!! Mеня не помню как зовут".getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
         */
        switch (m){
            case ENCRYPTE:
                switch (zone){
                    case 1: return str+ENCRYPTE_DIR_ZONE1;
                    case 2: return str+ENCRYPTE_DIR_ZONE2;
                    case 3: return str+ENCRYPTE_DIR_ZONE3;
                    case 4: return str+ENCRYPTE_DIR_ZONE4;
                }
            case DECRYPTE:
                switch (zone){
                    case 1: return str+DIR_ZONE1;
                    case 2: return str+DIR_ZONE2;
                    case 3: return str+DIR_ZONE3;
                    case 4: return str+DIR_ZONE4;
                }
        }
        return null;
    }
    public static String getPatchImage(int zone){
        String str=StorageApp.getAppPatch().getAbsolutePath();
        switch (zone){
            case 1: return str+DIR_ZONE1_IMAGE;
            case 2: return str+DIR_ZONE2_IMAGE;
            case 3: return str+DIR_ZONE3_IMAGE;
            case 4: return str+DIR_ZONE4_IMAGE;
            default: return null;
        }
    }
}

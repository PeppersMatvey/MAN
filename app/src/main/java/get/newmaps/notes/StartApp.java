package get.newmaps.notes;

import android.graphics.Color;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StartApp {
    public void run() throws Exception {
        DirToFile dirToFile=new DirToFile();
        dirToFile.clear();
        dirToFile.cht(StorageApp.getAppPatch().getAbsolutePath()+"/state/");
        ArrayList<File> fileList=dirToFile.getSPISOCFile();
        if(fileList.size()==0){
            new AddState().addState(AddState.STANDART_NULL_STATE.name, AddState.STANDART_NULL_STATE.colorState, AddState.STANDART_NULL_STATE.state_teg);
        }
        dirToFile.clear();


        new File(StorageApp.getAppPatch().getAbsolutePath()+CreatePatch.DIR_SETTINGS+"/").mkdirs();
        new File(StorageApp.getAppPatch().getAbsolutePath()+CreatePatch.DIR_SETTINGS_MAP+"/").mkdirs();
        File size_int=new File(StorageApp.getAppPatch().getAbsolutePath()+CreatePatch.DIR_SETTINGS_MAP_SIZE);
        if(size_int.exists()){
            //Log.d("testQ","Он есть");
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(size_int));
            MapAction.setSize(ois.readInt());
            ois.close();
        }else{
            //Log.d("testQ","Его нет");
            MapAction.setSize(20);
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(size_int));
            oos.writeInt(20);
            oos.close();
        }
    }
}

package get.newmaps.notes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirToFile {
    //проверяет что путь это дирректория
    private static ArrayList<File> SPISOC=new ArrayList<>();

    synchronized public boolean isDir(File f){
        return f.isDirectory();
    }

    //записывает все файлы в классовую коллекцию
    synchronized public void cht(String ff){
        File fff=new File(ff);
        cht(fff);
    }

    synchronized public void cht(File ff){
        try{
            if(isDir(ff)){
                File[] arrFiles = ff.listFiles();
                List<File> lst = Arrays.asList(arrFiles);
                for(File f : lst){
                    if(isDir(f)){
                        cht(f);
                    }else{
                        addd(f);
                    }
                }
            }else{
                addd(ff);
            }
        }catch (java.lang.NullPointerException ignor){

        }
    }
    synchronized private void addd(File f){
        if(!SPISOC.contains(f)&f.exists()&(!f.isDirectory())){
            SPISOC.add(f);
        }
    }
    synchronized public void clear(){
        SPISOC.clear();
        SPISOC=null;
        SPISOC=new ArrayList<>();
        Runtime.getRuntime().gc();
    }

    synchronized public ArrayList<File> getSPISOCFile(){
        return SPISOC;
    }
}

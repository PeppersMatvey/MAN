package get.newmaps.notes;

import android.util.Log;

import java.time.Instant;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterList {
    private static State state;
    private static String searsh_str="";
    private static ArrayList<Imports.OneHome> finalList;

    public static State getState() {
        return state;
    }

    public static void setState(State state) {
        if(state==null){
            FilterList.state=null;
            return;
        }
        if(state.state_teg.equals("00")){
            FilterList.state = null;
        }else{
            FilterList.state = state;
        }

    }
    public static void load(){
        try {
            finalList=FileOperations.readHomes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Imports.OneHome> getListLoad() throws Exception{
        if(finalList==null){
            finalList=FileOperations.readHomes();
        }
        return finalList;
    }
    public static String getSearsh_str() {
        return searsh_str;
    }

    public static void setSearsh_str(String searsh_str) {
        FilterList.searsh_str = new Formater().getFormateString(searsh_str);
    }
    public static ArrayList<Imports.OneHome> getFilterList(){
        long l1=0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            l1=Instant.now().toEpochMilli();
        }
        Runtime.getRuntime().gc();
        ArrayList<Imports.OneHome> oneHomeArrayList=null;
        ArrayList<Imports.OneHome> itogList=new ArrayList<>();
        ArrayList<Imports.OneHome> itogList2=new ArrayList<>();
        try {
            oneHomeArrayList=getListLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!searsh_str.equals("")){
            for(Imports.OneHome oneHome:oneHomeArrayList){
                if(isAdd(searsh_str,oneHome.NEW_NAME)){
                    itogList.add(oneHome);
                }
            }
        }else{
            itogList=oneHomeArrayList;
        }
        oneHomeArrayList=null;
        Runtime.getRuntime().gc();
        if (state != null) {
            for(Imports.OneHome oneHome: itogList){
                if(state.state_teg.equals(oneHome.STATE)){
                    itogList2.add(oneHome);
                }
            }
        } else{
            itogList2=itogList;
        }
        oneHomeArrayList=null;
        itogList=null;
        Runtime.getRuntime().gc();
        long l2=0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            l2=Instant.now().toEpochMilli();
        }
        Log.d("timeS",(l2-l1)+"");
        return itogList2;
    }
    private static boolean isAdd(String search,String newName){
        Pattern p=Pattern.compile(search);
        Matcher m=p.matcher(newName);
        return m.find();
    }
}

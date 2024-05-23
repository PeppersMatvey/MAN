package get.newmaps.notes;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Imports implements Serializable {
    private static final long serialVersionUID = 6951684352432691905L;
    public void addHomes(int nomer,File dirZone,String strit_name,String home_nomer,int kol_vo,String str,String X,String Y) throws Exception {
        if(kol_vo==1){
            if(isAddPrivateHome(home_nomer, nomer)){
                sawe(dirZone,strit_name,home_nomer,-1,str,X,Y);
            }
        }else{
            for(int i=nomer;i<=kol_vo;i+=100){
                sawe(dirZone,strit_name,home_nomer,i,str,X,Y);
            }
        }
    }

    public void sawe(File dirZone, String stritName, String homeNomer, int i, String str,String X,String Y) throws Exception {
        dirZone.mkdirs();
        File f=new File(dirZone,new Formater().getFormateString(stritName+homeNomer.replaceAll("/","")+(i==-1?"m1":i)));
        f=new File(f.getAbsolutePath()+".t");
        if(f.exists()){
            OneHome oneHome=readHome(f);
            oneHome.STR=str;
            oneHome.NEW_NAME=new Formater().getFormateString(stritName+homeNomer);
            write_home(oneHome,f);
        }else{
            write_home(new OneHome(stritName,homeNomer,new Formater().getFormateString(stritName+homeNomer),str,i, AddState.STANDART_NULL_STATE.state_teg, X, Y),f);
        }
    }

    public void write_home(OneHome oneHome,File f) throws Exception {
        Properties properties=new Properties();
        properties.setProperty(OneHome.STRIT_NAME_PROPERTES, oneHome.STRIT_NAME);
        properties.setProperty(OneHome.KV_PROPERTES, String.valueOf(oneHome.KV));
        properties.setProperty(OneHome.HOME_ADRESS_PROPERTES, oneHome.HOME_NOMER);
        properties.setProperty(OneHome.NEW_NAME_PROPERTES, oneHome.NEW_NAME);
        properties.setProperty(OneHome.STR_PROPERTES, oneHome.STR);
        properties.setProperty(OneHome.STATE_PROPERTES, oneHome.STATE);
        properties.setProperty(OneHome.X_PROPERTES, oneHome.X);
        properties.setProperty(OneHome.Y_PROPERTES, oneHome.Y);
        List<Vizit> properties1=create_Propertes(oneHome.VIZIT_ARRAY);
        int ii=0;
        for(Vizit key: properties1){
            ii++;
            properties.setProperty(key.Names,key.NOTES);
        }
        FileOutputStream fos=new FileOutputStream(f);
        properties.store(fos,"");
        fos.close();
    }
    public static void test() {
        Thread current = Thread.currentThread();
        StackTraceElement[] methods = current.getStackTrace();
        for (StackTraceElement info : methods){
            Log.d("hhh", info.toString());
        }
    }
    private List<Vizit> create_Propertes(Vizit[] vizitArray) throws Exception{
        ArrayList<Vizit> itogg=new ArrayList<>();
        int i=0;
        //Log.d("hhh0",vizitArray.length+" PPP "+itogg.size());
        try {
            for (; i < vizitArray.length; i++) {
                Vizit v = new Vizit();
                v.Names = OneHome.VIZIT_PLUS_PROPERTES + i;
                v.NOTES = vizit_create(vizitArray[i]);
                itogg.add(v);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //Log.d("hhh",vizitArray.length+" PPP "+itogg.size());
        return itogg;
    }
    /*public static void main (String[] args) throws Exception
    {
        //эту строку должен будет прочитать Reader
        String test = "Hi!\n My name is Richard\n I'm a photographer\n";
        //заворачиваем строку в StringReader
        StringReader reader = new StringReader(test);

        //Создаем объект StringWriter
        StringWriter writer = new StringWriter();

        //переписываем строки из Reader во Writer, предварительно развернув их
        executor(reader, writer);

        //получаем текст, который был записан во Writer
        String result = writer.toString();

        //выводим полученный из Writer’а текст на экран
        System.out.println("Результат: "+result);
    }
     */

    public static void executor(Reader reader, Writer writer) throws Exception {
        BufferedReader br = new BufferedReader(reader);
        String line;
        while ((line = br.readLine()) != null) {
            StringBuilder sb = new StringBuilder(line);
            String newLine = sb.reverse().toString();
            writer.write(newLine);
        }
    }
    private String vizit_create(Vizit vizit) throws Exception{
        //File f=new File(StorageApp.getAppPatch().getAbsolutePath()+"/test.properties");
        Properties p=new Properties();
        p.setProperty(Vizit.DATE_PROPERTES,vizit.DATE.getTime()+"");
        p.setProperty(Vizit.NOTES_PROPERTES,vizit.NOTES);
        //p.store(new FileOutputStream(f),"");
        StringWriter stringWriter=new StringWriter();
        p.store(stringWriter,"");
        String s=stringWriter.toString();
        //String s=readFile(f.getAbsolutePath());
        s=parseStringStore(isAdd1(s)+"\r\n"+isAdd(s));
        return s;
    }

    private String isAdd1(String s) {
        Pattern p=Pattern.compile(Vizit.NOTES_PROPERTES);
        Matcher m=p.matcher(s);
        if(m.find()){
            return s.substring(m.start(),s.length());
        }else{
            return null;
        }
    }

    public static String isAdd(String s){
        Pattern p=Pattern.compile(Vizit.DATE_PROPERTES);
        Matcher m=p.matcher(s);
        if(m.find()){
            return s.substring(m.start(),s.length());
        }else{
            return null;
        }
    }
    static String parseStringStore(String n){
        //Log.d("ff","uoblh"+n);
        n=n.replaceAll("\r\n","TYUYRE").replaceAll("=","&&");
        //Log.d("ff1","uoblh"+n);
        return n;
    }

    public static String readFile(String path) throws IOException {
        List<String> lines = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            lines = Files.readAllLines(Paths.get(path));
        }
        return String.join(System.lineSeparator(), lines);
    }
    static String parseStringLoad(String n){
        n=n.replaceAll("TYUYRE","\r\n").replaceAll("&&","=");
        return n;
    }
    public OneHome readHome(File f) throws Exception{
        try {
            OneHome oh = new OneHome();
            oh.ADRESS = f;
            oh.zone = CreatePatch.getZone(f);
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(f);
            properties.load(fis);
            oh.KV = Integer.parseInt(properties.getProperty(OneHome.KV_PROPERTES));
            oh.STRIT_NAME = properties.getProperty(OneHome.STRIT_NAME_PROPERTES);
            oh.HOME_NOMER = properties.getProperty(OneHome.HOME_ADRESS_PROPERTES);
            oh.NEW_NAME = properties.getProperty(OneHome.NEW_NAME_PROPERTES);
            oh.STR = properties.getProperty(OneHome.STR_PROPERTES);
            oh.STATE = properties.getProperty(OneHome.STATE_PROPERTES);
            oh.X = properties.getProperty(OneHome.X_PROPERTES);
            oh.Y = properties.getProperty(OneHome.Y_PROPERTES);
            oh.VIZIT_ARRAY = load_propertes(properties);
            return oh;
        }catch (Exception e){
            e.printStackTrace();
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));
            return (OneHome)ois.readObject();
        }
    }

    private Vizit[] load_propertes(Properties properties) throws Exception{
        ArrayList<Vizit> vizitArrayList=new ArrayList<>();
        for(String key: properties.stringPropertyNames()){
            if(key.length()<7){
                continue;
            }
            try{
                if(key.substring(0,8).equals(OneHome.VIZIT_PLUS_PROPERTES)){
                    vizitArrayList.add(getVizitPropertes(properties.getProperty(key)));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Vizit[] v=new Vizit[vizitArrayList.size()];
        vizitArrayList.toArray(v);
        return v;
    }

    private Vizit getVizitPropertes(String property) throws Exception{
        //File f=new File(StorageApp.getAppPatch().getAbsolutePath()+"/test.properties");
        //if(f.exists()){
        //    f.delete();
        //}
        //BufferedWriter bw=new BufferedWriter(new FileWriter(f));
        //bw.write(parseStringLoad(property));
        //bw.close();
        StringReader stringReader=new StringReader(parseStringLoad(property));
        Properties properties=new Properties();
        //properties.load(new FileInputStream(f));
        properties.load(stringReader);
        Vizit v=new Vizit();
        v.DATE=new Date(Long.parseLong(properties.getProperty(Vizit.DATE_PROPERTES)));
        v.NOTES=properties.getProperty(Vizit.NOTES_PROPERTES);
        return v;
    }

    public boolean isAddPrivateHome(String home_nomer,int nomer){
        return isNomer(formateNomer(home_nomer),nomer);
    }
    public int formateNomer(String home_nomer){
        Pattern pattern=Pattern.compile("[^1234567890]");
        Matcher matcher=pattern.matcher(home_nomer);
        if(matcher.find()){
            return Integer.parseInt(home_nomer.substring(0, matcher.start()));
        }else{
            return Integer.parseInt(home_nomer);
        }
    }
    public boolean isNomer(int nomer_home,int nomer){
        if(nomer_home==nomer) return true;
        for(int i=nomer;i<4000;i+=100){
            if(nomer_home==i){
                return true;
            }
        }
        return false;
    }
    class OneHome implements Serializable{
        public static final String HOME_ADRESS_PROPERTES="NOMER";
        public static final String STRIT_NAME_PROPERTES="STRIT";
        public static final String NEW_NAME_PROPERTES="NEWNAME";
        public static final String STR_PROPERTES="STR";
        public static final String KV_PROPERTES="KV";
        public static final String STATE_PROPERTES="STATE";
        public static final String X_PROPERTES="X";
        public static final String Y_PROPERTES="Y";
        public static final String VIZIT_PLUS_PROPERTES="VIZITES_";
        private static final long serialVersionUID = 842977747432946843L;
        public String STRIT_NAME;
        public String HOME_NOMER;
        public String NEW_NAME;
        public String STR;
        public int KV;
        transient public int zone;
        public transient File ADRESS;
        public String STATE;
        public String X;
        public String Y;
        public Vizit[] VIZIT_ARRAY=new Vizit[0];
        OneHome(){
            Y = "0";
            X = "0";
        }

        OneHome(String stritName, String homeNomer, String newName, String str, int kv, String state, String x, String y) {
            this.STRIT_NAME = stritName;
            this.HOME_NOMER = homeNomer;
            this.NEW_NAME = newName;
            this.STR = str;
            this.KV = kv;
            this.zone = zone;
            this.STATE = state;
            X = x;
            Y = y;
        }
    }
    static class Vizit implements Serializable{
        private static final long serialVersionUID = 934390898736283150L;
        public Date DATE;
        public static final String DATE_PROPERTES="DATESS";
        public static final String NOTES_PROPERTES="NOTESS";
        public String NOTES="";
        public String Names="";
        Vizit(){

        }
        Vizit(Date date, String notes) {
            DATE = date;
            NOTES = notes;
        }
    }
}

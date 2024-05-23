package get.newmaps.notes;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class AddState implements Terminal_Programm{
public static final State STANDART_NULL_STATE=new State("Я здесь ещё небыл", Color.BLACK, PasswordFormat.gt("Я здесь ещё небыл"));

    @Override
    public void run() {
        try{
            while(true){
                MySystemT.println("Введите название статуса",0,Terminal_Activity.RESOURSE_INPUT);
                String name=MySystemT.nextLine(null);
                if(name.length()<3||name.length()>25){
                    MySystemT.println("Название не может быть короче 3 символов или длиннее 25",R.color.red,Terminal_Activity.RESOURSE_INPUT);
                    continue;
                }
                MySystemT.println("Выберите цвет",0,Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #1 (standart)",0,Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #2",Color.RED,Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #3",Color.parseColor("#FF8400"),Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #4",Color.YELLOW,Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #5",Color.GREEN,Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #6",Color.parseColor("#0A9300"),Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #7",Color.CYAN,Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #8",Color.parseColor("#0072FF"),Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #9",Color.BLUE,Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #10",Color.MAGENTA,Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #11",Color.parseColor("#840093"),Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #12",Color.GRAY,Terminal_Activity.CLASS_COLOR_INPUT);
                MySystemT.println("Color #13",Color.DKGRAY,Terminal_Activity.CLASS_COLOR_INPUT);
                String colorSt=MySystemT.nextLine(null);
                int colorInt=0;
                int colorItog=0;
                if(!isInt(colorSt,"Вы ввели не номер. Программа перезапущенна",null)){
                    continue;
                }else{
                    colorInt=Integer.parseInt(colorSt);
                }
                switch (colorInt){
                    case 1: {
                        colorItog=Color.BLACK;
                        break;
                    }
                    case 2: {
                        colorItog=Color.RED;
                        break;
                    }
                    case 3: {
                        colorItog=Color.parseColor("#FF8400");
                        break;
                    }
                    case 4: {
                        colorItog=Color.YELLOW;
                        break;
                    }
                    case 5: {
                        colorItog=Color.GREEN;
                        break;
                    }
                    case 6: {
                        colorItog=Color.parseColor("#0A9300");
                        break;
                    }
                    case 7: {
                        colorItog=Color.CYAN;
                        break;
                    }
                    case 8: {
                        colorItog=Color.parseColor("#0072FF");
                        break;
                    }
                    case 9: {
                        colorItog=Color.BLUE;
                        break;
                    }
                    case 10: {
                        colorItog=Color.MAGENTA;
                        break;
                    }
                    case 11: {
                        colorItog=Color.parseColor("#840093");
                        break;
                    }
                    case 12: {
                        colorItog=Color.GRAY;
                        break;
                    }
                    case 13: {
                        colorItog=Color.DKGRAY;
                        break;
                    }
                    default:{
                        MySystemT.println("Цвета с таким номером нет. Программа перезапущенна",0,Terminal_Activity.RESOURSE_INPUT);
                        continue;
                    }
                }
                try {
                    addState(name,colorItog,null);
                    MySystemT.println("Статус добавлен",0,Terminal_Activity.RESOURSE_INPUT);
                    return;
                } catch (IOException e) {
                    MySystemT.println("Случилась ошибка: "+e.toString(),R.color.red,Terminal_Activity.RESOURSE_INPUT);
                    MySystemT.println("Программа перезапущенна",0,Terminal_Activity.RESOURSE_INPUT);
                    e.printStackTrace();
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*public void setStates() throws Exception {
        DirToFile d=new DirToFile();
        d.cht(new File(StorageApp.getAppPatch().getAbsolutePath()+"/state/"));
        for(File f:d.getSPISOCFile()){
            State st=readState(f);
            st.state_teg=PasswordFormat.gt(st.name);
            stateWriter(st,st.patch);
        }
        for(File f:d.getSPISOCFile()){
            State st=readState(f);
            st.state_teg=PasswordFormat.gt(st.name);
            f.renameTo(new File(f.getAbsolutePath().substring(0,f.getAbsolutePath().length()-f.getName().length())+PasswordFormat.gt(st.name).replaceAll("/","f")+".state"));
        }
        d.clear();
    }

     */
    public void addState(String str,int colorNumber,String teg) throws Exception {
        new File(StorageApp.getAppPatch().getAbsolutePath()+"/state/").mkdirs();
        //File adress=new File(StorageApp.getAppPatch().getAbsolutePath()+"/state/"+PasswordFormat.gt(str).replaceAll("/","f").replaceAll("/?","")+".state");
        File adress;
        if(teg.equals(STANDART_NULL_STATE.state_teg)){
            adress=new File(StorageApp.getAppPatch().getAbsolutePath()+"/state/"+123232232134567L+".state");
        }else{
            adress=new File(StorageApp.getAppPatch().getAbsolutePath()+"/state/"+System.currentTimeMillis()+".state");
        }
        State state=new State();
        state.colorState=colorNumber;
        state.name=str;
        if(teg==null){
            state.state_teg=PasswordFormat.gt(str);
        }else{
            state.state_teg=teg;
        }
        Log.d("adressC",adress.getAbsolutePath());
        //Log.d("adress",PasswordFormat.gt(str).replaceAll("/","f").replaceAll("/?",""));
        stateWriter(state,adress);
    }
    public synchronized State readState(File patchXML) throws Exception {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        SoftReference<Document> document=new SoftReference<Document>(documentBuilder.parse(patchXML));
        Node root=document.get().getDocumentElement();
        NodeList nl=root.getChildNodes();
        State home=new State();
        home.patch=patchXML;
        home.name=nl.item(0).getTextContent();
        home.state_teg=nl.item(1).getTextContent();
        home.colorState=Integer.parseInt(nl.item(2).getTextContent());
        return home;
    }
    public ArrayList<State> readState() throws Exception{
        DirToFile dirToFile=new DirToFile();
        dirToFile.clear();
        dirToFile.cht(StorageApp.getAppPatch().getAbsolutePath()+"/state/");
        Log.d("adress",StorageApp.getAppPatch().getAbsolutePath()+"/state/");
        ArrayList<File> fileList=dirToFile.getSPISOCFile();
        ArrayList<State> stateList=new ArrayList<>();
        for(File f:fileList){
            State st=readState(f);
            stateList.add(st);
        }
        return stateList;
    }
    public void stateWriter(State st,File adress) throws ParserConfigurationException, IOException, SAXException {
        try {
            if (adress.exists()) {
                //adress.delete();
            }
        }catch (Exception e){
            //e.printStackTrace();
        }
        Document document;
        DocumentBuilder documentBuilder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
        if(adress.exists()){
            document=documentBuilder.parse(adress);
        }else{
            document=documentBuilder.newDocument();
        }
        boolean mod=adress.exists()?true:false;
        Node root=null;
        if(mod){
            root = document.getDocumentElement();
        }else{
            root=document.createElement("StritList");
        }
        Element item=document.createElement("item");
        item.setTextContent(st.name);
        Element item2=document.createElement("item2");
        item2.setTextContent(st.state_teg);
        Element item3=document.createElement("item3");
        item3.setTextContent(st.colorState+"");
        if(mod){
            root.appendChild(item);
            root.appendChild(item2);
            root.appendChild(item3);
        }else{
            root.appendChild(item);
            root.appendChild(item2);
            root.appendChild(item3);
            document.appendChild(root);
        }
        new XMLControl.Writer().writeDocument(document,adress);
    }
}
class State implements Serializable{
    private static final long serialVersionUID=5465789106118910037L;
    public String name;
    public int colorState;
    public transient File patch;
    public String state_teg;
    State(){
    }
    State(String name, int colorState, String state_teg) {
        this.name = name;
        this.colorState = colorState;
        this.state_teg = state_teg;
    }
}

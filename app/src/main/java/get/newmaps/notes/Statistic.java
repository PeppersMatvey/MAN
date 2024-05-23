package get.newmaps.notes;

import android.graphics.Color;

import java.io.File;
import java.util.ArrayList;

public class Statistic implements Terminal_Programm{
    @Override
    public void run() {
        try{
            ArrayList<Imports.OneHome> oneHomeArrayList1=FileOperations.readListHome((byte) 1);
            ArrayList<Imports.OneHome> oneHomeArrayList2=FileOperations.readListHome((byte) 2);
            ArrayList<Imports.OneHome> oneHomeArrayList3=FileOperations.readListHome((byte) 3);
            ArrayList<Imports.OneHome> oneHomeArrayList4=FileOperations.readListHome((byte) 4);
            if(oneHomeArrayList1.size()!=0){
                MySystemT.println("Зона 1",0,Terminal_Activity.RESOURSE_INPUT);
                printState(oneHomeArrayList1);
                MySystemT.println("",0,Terminal_Activity.RESOURSE_INPUT);
            }
            MySystemT.println("",0,Terminal_Activity.RESOURSE_INPUT);
            if(oneHomeArrayList2.size()!=0){
                MySystemT.println("Зона 2",0,Terminal_Activity.RESOURSE_INPUT);
                printState(oneHomeArrayList2);
                MySystemT.println("",0,Terminal_Activity.RESOURSE_INPUT);
            }
            if(oneHomeArrayList3.size()!=0){
                MySystemT.println("Зона 3",0,Terminal_Activity.RESOURSE_INPUT);
                printState(oneHomeArrayList3);
                MySystemT.println("",0,Terminal_Activity.RESOURSE_INPUT);
            }
            MySystemT.println("",0,Terminal_Activity.RESOURSE_INPUT);
            if(oneHomeArrayList4.size()!=0){
                MySystemT.println("Зона 4",0,Terminal_Activity.RESOURSE_INPUT);
                printState(oneHomeArrayList4);
                MySystemT.println("",0,Terminal_Activity.RESOURSE_INPUT);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void printState(ArrayList<Imports.OneHome> list) throws Exception{
        for(State st:new AddState().readState()){
            int stInt=0;
            for(Imports.OneHome oneHome:list){
                if(oneHome.STATE.equals(st.state_teg)){
                    stInt++;
                }
            }
            MySystemT.println(st.name+" найдено "+stInt,st.colorState== Color.BLACK?Color.WHITE:st.colorState,Terminal_Activity.CLASS_COLOR_INPUT);
        }
    }
}

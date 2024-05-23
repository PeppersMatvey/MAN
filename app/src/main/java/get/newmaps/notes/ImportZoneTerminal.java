package get.newmaps.notes;

import java.io.File;
import java.util.ArrayList;

public class ImportZoneTerminal implements Terminal_Programm{
    private final filal_task finish;

    public ImportZoneTerminal(filal_task finish) {
        this.finish = finish;
    }
    public ImportZoneTerminal(){
        this.finish = null;
    }

    interface filal_task{
    void finalize();
}
    @Override
    public void run() {
        while(true){
            MySystemT.println(" Введите путь к файлу зоны",0,Terminal_Activity.RESOURSE_INPUT);
            String str=MySystemT.nextLine(null);
            if(isExit(str)){
                MySystemT.println(" Импортирование прервано",0,Terminal_Activity.RESOURSE_INPUT);
                return;
            }
            File f=new File(str);
            String out=null;
            int color=0;
            try{
                if(f.exists()){
                    out=" Путь к файлу введён верно";
                    color=0;
                }else{
                    out=" Файл не существует";
                    color=R.color.red;
                }
            }catch (Exception e){
                out=" Путь к файлу не верен";
                color=R.color.red;
                e.printStackTrace();
            }
            MySystemT.println(out,color,Terminal_Activity.RESOURSE_INPUT);
            byte zone;
            try{
                MySystemT.println(" Введите номер зоны",0,Terminal_Activity.RESOURSE_INPUT);
                String strZ=MySystemT.nextLine(null);
                if(isExit(strZ)){
                    MySystemT.println(" Импортирование прервано",0,Terminal_Activity.RESOURSE_INPUT);
                    return;
                }
                zone=Byte.parseByte(strZ);
            }catch (Exception e){
                e.printStackTrace();
                MySystemT.println(" Вы ввели не номер зоны, программа перезапущенна",R.color.red,Terminal_Activity.RESOURSE_INPUT);
                continue;
            }
            byte nomerkw;
            try{
                MySystemT.println(" Введите номер ваших квартир",0,Terminal_Activity.RESOURSE_INPUT);
                String strKW=MySystemT.nextLine(null);
                if(isExit(strKW)){
                    MySystemT.println(" Импортирование прервано",0,Terminal_Activity.RESOURSE_INPUT);
                    return;
                }
                nomerkw=Byte.parseByte(strKW);
                if(nomerkw<1){
                    MySystemT.println(" Номер не может быть меньше одного, программа перезапущенна",R.color.red,Terminal_Activity.RESOURSE_INPUT);
                    continue;
                }
                if(nomerkw>99){
                    MySystemT.println(" Номер не может быть больше 99, программа перезапущенна",R.color.red,Terminal_Activity.RESOURSE_INPUT);
                    continue;
                }
            }catch (Exception e){
                e.printStackTrace();
                MySystemT.println(" Вы ввели не количество, программа перезапущенна",R.color.red,Terminal_Activity.RESOURSE_INPUT);
                continue;
            }
            ArrayList<XMLControl.Reader.Home> arrayList=new XMLControl.Reader(f,zone).getListHome();
            int i=0;
            for(XMLControl.Reader.Home oneHomes: arrayList){
                try {
                    new Imports().addHomes(nomerkw,new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) zone)), oneHomes.strit, oneHomes.nomerHome, Integer.parseInt(oneHomes.nomerApar), oneHomes.str, oneHomes.X, oneHomes.Y);
                    i++;
                    MySystemT.println(i+" из "+arrayList.size(),R.color.blue,Terminal_Activity.RESOURSE_INPUT);
                } catch (Exception e) {
                    MySystemT.println(" Случилась ошибка: "+e.toString(),R.color.red,Terminal_Activity.RESOURSE_INPUT);
                    MySystemT.println(" Программа перезапущенна",0,Terminal_Activity.RESOURSE_INPUT);
                    e.printStackTrace();
                    continue;
                }
            }
            MySystemT.println(" Импорт завершён",0,Terminal_Activity.RESOURSE_INPUT);
            MySystemT.println(" Для стабильности сейчас нужно будет выполнить выход из приложения",0,Terminal_Activity.RESOURSE_INPUT);
            try {
                new TimerMiliSecond().startTimer(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(finish!=null){
                finish.finalize();
            }
            return;
        }
    }
}

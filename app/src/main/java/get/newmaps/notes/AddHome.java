package get.newmaps.notes;

import java.io.File;

public class AddHome implements Terminal_Programm{

    @Override
    public void run() {
        while(true){
            MySystemT.println("AI это функция находящая в доме нужные квартиры и добавляет только их. Например в доме где 100 квартир программа добавить только одну, например 20-тую. Где 400 квартир добавит 320, 220, 120 и 20-ую. В доме где 8 квартир не добавит ни одной");
            MySystemT.println("Добавить с AI(Y/N)?");
            String str=MySystemT.nextLine(null);
            boolean bool;
            if(isExit(str)){
                return;
            }
            if(str.trim().toLowerCase().equals("y")){
                bool=true;
            } else if(str.trim().toLowerCase().equals("n")){
                bool=false;
            } else {
                MySystemT.println("Программа перезапущенна1");
                continue;
            }
            MySystemT.println("Введите название улицы");
            String nameStrit=MySystemT.nextLine(null);
            if(isExit(nameStrit)){
                return;
            }
            MySystemT.println("Введите номер дома");
            String nameHome=MySystemT.nextLine(null);
            if(isExit(nameHome)){
                return;
            }
            MySystemT.println("Введите зону");
            String zone=MySystemT.nextLine(null);
            if(isExit(zone)){
                return;
            }
            MySystemT.println("Введите координаты X и Y (\"0\" если не знаете)");
            String coord=MySystemT.nextLine(null);
            if(isExit(coord)){
                return;
            }
            String X;
            String Y;
            if(coord.trim().equals("0")){
                X="0";
                Y="0";
            }else{
                X=coord.substring(0,coord.indexOf(",")-1).trim();
                Y=coord.substring(coord.indexOf(",")+1,coord.length()).trim();
            }
            if(bool){
                MySystemT.println("Введите номер квартир вашей зоны");
                String nomerKV=MySystemT.nextLine(null);
                if(isExit(nomerKV)){
                    return;
                }
                MySystemT.println("Введите количество квартир в доме");
                String colVo=MySystemT.nextLine(null);
                if(isExit(colVo)){
                    return;
                }
                try{
                    Integer.parseInt(colVo);
                }catch (Exception e){
                    e.printStackTrace();
                    MySystemT.println("Вы ввели не число. Программа перезапущенна\n");
                    continue;
                }
                try {
                    new Imports().addHomes(Integer.parseInt(nomerKV),new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) Integer.parseInt(zone))),nameStrit,nameHome, Integer.parseInt(colVo),"Нет примечаний",X,Y);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    MySystemT.println("Программа перезапущенна из-за ошибки (Введите всё правильно)");
                    continue;
                }
            }else{
                MySystemT.println("Введите номер квартиры");
                String nomerKV=MySystemT.nextLine(null);
                if(isExit(nomerKV)){
                    return;
                }
                try {
                    new Imports().sawe(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) Integer.parseInt(zone))),nameStrit,nameHome,Integer.parseInt(nomerKV),"Нет примечаний",X,Y);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    MySystemT.println("Программа перезапущенна из-за ошибки (Введите всё правильно)");
                    continue;
                }
            }
        }
    }
}

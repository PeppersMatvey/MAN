package get.newmaps.notes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ClearZone implements Terminal_Programm{
    @Override
    public void run() {
        while(true){
            MySystemT.println("Какую зону вы хотите удалить (введите 5 если хотите удалить все)",0,Terminal_Activity.RESOURSE_INPUT);
            String str=MySystemT.nextLine(null);
            if(isExit(str)){
                MySystemT.println("Удаление прервано",0,Terminal_Activity.RESOURSE_INPUT);
                return;
            }
            int zone;
            try{
                zone=Integer.parseInt(str);
            }catch (Exception e){
                MySystemT.println("Вы ввели не число, программа перезапущенна",0,Terminal_Activity.RESOURSE_INPUT);
                continue;
            }
            MySystemT.println("Удалить с перезаписью? (Y/N)",0,Terminal_Activity.RESOURSE_INPUT);
            String str1=MySystemT.nextLine(null);
            if(isExit(str1)){
                MySystemT.println("Удаление прервано",0,Terminal_Activity.RESOURSE_INPUT);
                return;
            }
            boolean writeAndDelete=false;
            if(str1.toLowerCase().equals("y")|str1.toLowerCase().equals("n")){
                if(str1.toLowerCase().equals("y")){
                    writeAndDelete=true;
                }else if(str1.toLowerCase().equals("n")){
                    writeAndDelete=false;
                }
            }else{
                MySystemT.println("Вы ввели не то значение, программа перезапущенна",0,Terminal_Activity.RESOURSE_INPUT);
                continue;
            }
            delete(writeAndDelete,zone);
            return;
        }
    }
    public void delete(boolean mode, int zone){
        ArrayList<File> fileArrayList=new ArrayList<>();
        MySystemT.println("Чтение списка файлов...",0,Terminal_Activity.RESOURSE_INPUT);
        if(zone==5){
            if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)1)).exists()){
                ArrayList<File> arrayList1=read_zone((byte)1);
                for(File one:arrayList1){
                    fileArrayList.add(one);
                }
            }
            if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)2)).exists()){
                ArrayList<File> arrayList1=read_zone((byte)2);
                for(File one:arrayList1){
                    fileArrayList.add(one);
                }
            }
            if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)3)).exists()){
                ArrayList<File> arrayList1=read_zone((byte)3);
                for(File one:arrayList1){
                   fileArrayList.add(one);
                }
            }
            if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)4)).exists()){
                ArrayList<File> arrayList1=read_zone((byte)4);
                for(File one:arrayList1){
                    fileArrayList.add(one);
                }
            }
        }else{
            fileArrayList=read_zone((byte)zone);
        }

        if(mode){
            MySystemT.println("Удаление с перезаписью...",0,Terminal_Activity.RESOURSE_INPUT);
            for(File delete:fileArrayList){
                FileOperations.writeNullAndDelete(delete);
            }
        }else{
            MySystemT.println("Удаление...",0,Terminal_Activity.RESOURSE_INPUT);
            for(File delete:fileArrayList){
                delete.delete();
            }
        }
        MySystemT.println("Удаление зоны "+zone+" завершено",0,Terminal_Activity.RESOURSE_INPUT);
    }
    public ArrayList<File> read_zone(byte zone){
        DirToFile dirToFile=new DirToFile();
        dirToFile.clear();
        dirToFile.cht(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)zone));
        return dirToFile.getSPISOCFile();
    }
}

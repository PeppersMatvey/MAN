package get.newmaps.notes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SetSizeMap implements Terminal_Programm{
    @Override
    public void run() {
        while (true){
            MySystemT.println("ВВедите новый размер курсора карт. Не менее 15 и не более 40");
            String str=MySystemT.nextLine(null);
            if (isExit(str)) {
                MySystemT.println(" Загрузка прервана",0,Terminal_Activity.RESOURSE_INPUT);
                return;
            }
            if(isInt(str,"Вы ввели не число, повторите заново","")){
                if(Integer.parseInt(str)>=15&Integer.parseInt(str)<=40){
                    try {
                        File size_int=new File(StorageApp.getAppPatch().getAbsolutePath()+CreatePatch.DIR_SETTINGS_MAP_SIZE);
                        size_int.delete();
                        MapAction.setSize(Integer.parseInt(str));
                        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(size_int));
                        oos.writeInt(Integer.parseInt(str));
                        oos.close();
                        MySystemT.println("Настройки сохранены");
                        return;
                    } catch (Exception e) {
                        MySystemT.println("Вы ввели не число. Попробуйте ещё раз");
                        e.printStackTrace();
                        continue;
                    }
                }else{
                    continue;
                }
            }else{
                continue;
            }
        }
    }
}

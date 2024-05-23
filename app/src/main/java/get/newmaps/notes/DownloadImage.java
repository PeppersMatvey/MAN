package get.newmaps.notes;

import java.io.File;
import java.io.FileInputStream;

public class DownloadImage implements Terminal_Programm{
    @Override
    public void run() {
        while (true){
            MySystemT.println("Введите путь к файлу с фото зоны");
            String str=MySystemT.nextLine(null);
            if (isExit(str)) {
                MySystemT.println(" Загрузка прервана",0,Terminal_Activity.RESOURSE_INPUT);
                return;
            }
            try{
                new FileInputStream(str).read();
            }catch (Exception e){
                MySystemT.println(" Файл или папка не найдены. Попробуйте ещё раз",0,Terminal_Activity.RESOURSE_INPUT);
                continue;
            }
            MySystemT.println("Введите номер зоны. 1, 2, 3 или 4");
            String str1=MySystemT.nextLine(null);
            if (isExit(str1)) {
                MySystemT.println(" Загрузка прервана",0,Terminal_Activity.RESOURSE_INPUT);
                return;
            }
            if(isInt(str1,"Вы ввели не число, повторите заново","")){
                if(Integer.parseInt(str1)<5&Integer.parseInt(str1)>0){
                    try {
                        FileOperations.copy(new File(str),new File(CreatePatch.getPatchImage(Integer.parseInt(str1))));
                        MySystemT.println("Копирование успешно выполнено");
                        return;
                    } catch (Exception e) {
                        MySystemT.println("Во время копирования произошла ошибка. Попробуйте ещё раз");
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

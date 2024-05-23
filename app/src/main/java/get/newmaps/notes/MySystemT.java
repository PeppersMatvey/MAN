package get.newmaps.notes;

import android.util.Log;

public class MySystemT{
    public static void println(String print,int color,byte mode_input) {
        //Log.d("DeagnostAP", "StartTerminal WHILE 01");
        Terminal_Activity.print = "\t" + print;
        Terminal_Activity.MODE_TEXT = new ModeTEXT(color, mode_input);
        Terminal_Activity.mode_text = true;
        while (true) {
           // Log.d("DeagnostAP", "StartTerminal WHILE");
            if (Terminal_Activity.print_finish) {
                Terminal_Activity.print_finish = false;
                Terminal_Activity.mode_text = false;
                return;
            }
        }
    }
    public static void println(String print){
        println(print,0,Terminal_Activity.RESOURSE_INPUT);
    }
    public static String nextLine(ModeEDIT modeEDIT) {
        Terminal_Activity.MODE_EDIT=modeEDIT;
        Terminal_Activity.mode_edit=true;
        while(Terminal_Activity.scanner==null){
            try {
                new TimerMiliSecond().startTimer(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String str=Terminal_Activity.scanner;
        Terminal_Activity.scanner=null;
        Terminal_Activity.mode_edit=false;
        return str;
    }
}

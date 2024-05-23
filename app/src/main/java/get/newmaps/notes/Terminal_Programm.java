package get.newmaps.notes;

public interface Terminal_Programm {
    public void run();
    default boolean isExit(String comand) {
        switch (comand) {
            case "exit p":
                return true;
            case "exit programm":
                return true;
            default:
                return false;
        }
    }
    default boolean isInt(String intSTR, String falseSTR, String trueSTR){
        try{
            Integer.parseInt(intSTR);
            if(trueSTR!=null){
                MySystemT.println(trueSTR,0,Terminal_Activity.RESOURSE_INPUT);
            }
            return true;
        }catch (Exception e){
            if(falseSTR!=null){
                MySystemT.println(falseSTR,R.color.red,Terminal_Activity.RESOURSE_INPUT);
            }
            return false;
        }
    }
}


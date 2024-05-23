package get.newmaps.notes;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

public class PasswordFormat {
    private final MODE md;
    private PasswordFormat(MODE h){
        this.md=h;
    }

    public static String gt(String str){
        PasswordFormat passwordFormat=PasswordFormat.getInstance(MODE.INDEX_MINUS_MODE);
        try {
            return TrueString(passwordFormat,str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String TrueString(PasswordFormat passwordFormat,String str) throws Exception{
        while (true) {
            String s1= passwordFormat.format(str);
            String s2= passwordFormat.format(str);
            String s3= passwordFormat.format(str);
            String s4= passwordFormat.format(str);
            String s5= passwordFormat.format(str);
            String s6= passwordFormat.format(str);
            String s7= passwordFormat.format(str);
            String s8= passwordFormat.format(str);
            boolean b1=s1.equals(s2);
            boolean b2=s3.equals(s4);
            boolean b3=s5.equals(s6);
            boolean b4=s7.equals(s8);
            if(b1&b2&b3&b4){
                return s1;
            }
        }
        //return null;
    }

    public static PasswordFormat getInstance(MODE mod){
        return new PasswordFormat(mod);
    }

    public String format(String start) throws Exception{
        if(md==MODE.INDEX_MINUS_MODE){
            return Format_INDEX_MODE(start);
        }
        return "";
    }

    private String Format_INDEX_MODE(String str) throws Exception{
        MessageDigest md=MessageDigest.getInstance("SHA-256");
        //byte[] bt=str.getBytes();
        byte[] bt1=md.digest(str.getBytes());
        return Format_poradok(bt1);
        //return Format_poradok(str.getBytes());
    }

    private String Format_poradok(byte[] st){
        byte[] stt= Arrays.copyOf(st,128);
        ArrayList<Byte> bt=new ArrayList<>();
        for(byte b:stt){
            if(b>5){
                bt.add(Byte.valueOf(b));
            }
        }

        for(int i=0;i<bt.size();i++){
            int ind;
            if(i>=bt.size()){
                ind=1;
            }else{
                ind=i-1;
            }
            if(ind==-1){
                ind=0;
            }
            stt[bt.get(i)]=(byte)((byte)stt[bt.get(i)]-(byte)stt[bt.get(ind)]);
        }
        return BytesToString(stt);
    }
    private String BytesToString(byte[] stt){
        ArrayList<Byte>bt=new ArrayList<>();
        for(byte b:stt){
            if(Character.isValidCodePoint(b)){
                bt.add(Byte.valueOf(b));
            }
        }
        byte[] itog=new byte[bt.size()];
        for(int i = 0;i<bt.size();i++){
            itog[i]=bt.get(i);
        }
//  String strg;
//  strg.replaceFirst("" ,"");
        //return new String(itog);
        return new String(itog)
                .replaceFirst("" ,"")
                .replaceFirst(" ","");
    }
}

enum MODE{
    INDEX_MINUS_MODE
}

package get.newmaps.notes;

import java.util.Locale;

class Formater{
    public String getFormateString(String search_guery){
        String str1=FormateProbel(search_guery);
        String str2=FormateNextStr(str1);
        String str3=replaser(str2);
        return str3;
    }
    protected String FormateProbel(String str){
        String formate=str.trim();
        for(int i=0;i<100;i++){
            String probel=" ";
            for(int ii=0;ii<i;ii++){
                probel=probel+" ";
            }
            formate=formate.replaceAll(probel," ");
        }
        formate=formate.replaceAll(" ","");
        return formate;
    }

    public String FormateNextStr(String str1) {
        String formate=str1;
        for(int i=0;i<100;i++){
            String probel="\n";
            for(int ii=0;ii<i;ii++){
                probel=probel+"\n";
            }
            formate=formate.replaceAll(probel,"");
        }
        formate=formate.replaceAll("\n","");
        return formate;
    }
    public String replaser(String st){
        String str1=st.toLowerCase(Locale.ROOT);
        str1=str1.replaceAll(" ","_");
        str1=str1.replaceAll("и","i");
        str1=str1.replaceAll("й","y");
        str1=str1.replaceAll("ц","c");
        str1=str1.replaceAll("у","u");
        str1=str1.replaceAll("к","k");
        str1=str1.replaceAll("е","e");
        str1=str1.replaceAll("ё","e");
        str1=str1.replaceAll("н","n");
        str1=str1.replaceAll("г","g");
        str1=str1.replaceAll("ш","sh");
        str1=str1.replaceAll("щ","shch");
        str1=str1.replaceAll("з","z");
        str1=str1.replaceAll("х","h");

        str1=str1.replaceAll("ф","f");
        str1=str1.replaceAll("ы","y");
        str1=str1.replaceAll("в","v");
        str1=str1.replaceAll("а","a");
        str1=str1.replaceAll("п","p");
        str1=str1.replaceAll("р","r");
        str1=str1.replaceAll("о","o");
        str1=str1.replaceAll("л","l");
        str1=str1.replaceAll("д","d");
        str1=str1.replaceAll("ж","j");
        str1=str1.replaceAll("э","е");

        str1=str1.replaceAll("я","ya");
        str1=str1.replaceAll("ч","tsch");
        str1=str1.replaceAll("с","s");
        str1=str1.replaceAll("м","m");
        str1=str1.replaceAll("т","t");
        str1=str1.replaceAll("ь","g");
        str1=str1.replaceAll("б","b");
        str1=str1.replaceAll("ю","yu");
        return str1;
    }
}

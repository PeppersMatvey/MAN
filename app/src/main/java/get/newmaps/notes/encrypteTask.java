package get.newmaps.notes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class encrypteTask extends AsyncTask<String,Void,Void> {
    private final boolean isTime;
    public encrypteTask(END_OPERATIONS end_operations, boolean isTime) {
        this.isTime = isTime;
        this.end_operations = end_operations;
    }

    interface END_OPERATIONS{
    void finall();
}
public void startThread() throws Exception{
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                String pss=null;
                File f=new File(StorageApp.getAppPatch().getAbsolutePath()+"/pasword");
                try {
                    FileReader fr=new FileReader(f);
                    BufferedReader br=new BufferedReader(fr);
                    pss=br.readLine();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try{
                    if(new File(CreatePatch.getPatchImage(1)).exists()){
                        encrypteDir(new File(CreatePatch.getPatchImage(1)),new File(CreatePatch.getPatchImageE(1)),pss);
                    }
                    if(new File(CreatePatch.getPatchImage(2)).exists()){
                        encrypteDir(new File(CreatePatch.getPatchImage(2)),new File(CreatePatch.getPatchImageE(2)),pss);
                    }
                    if(new File(CreatePatch.getPatchImage(3)).exists()){
                        encrypteDir(new File(CreatePatch.getPatchImage(3)),new File(CreatePatch.getPatchImageE(3)),pss);
                    }
                    if(new File(CreatePatch.getPatchImage(4)).exists()){
                        encrypteDir(new File(CreatePatch.getPatchImage(4)),new File(CreatePatch.getPatchImageE(4)),pss);
                    }
                    encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 1)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 1)+"/"),pss);
                    encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 2)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 2)+"/"),pss);
                    encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 3)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 3)+"/"),pss);
                    encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 4)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 4)+"/"),pss);
                }catch (Exception e){
                    e.printStackTrace();
                }
                f.delete();
            }
        });
        th.start();
    th.join();
    if (end_operations!=null){
        end_operations.finall();
    }
    return;
}

private final END_OPERATIONS end_operations;
        @Override
        protected Void doInBackground(String... voids) {
            try {
                new TimerMiliSecond().startTimer(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String pss=null;
            File f=new File(StorageApp.getAppPatch().getAbsolutePath()+"/pasword");
            try {
                FileReader fr=new FileReader(f);
                BufferedReader br=new BufferedReader(fr);
                pss=br.readLine();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                if(new File(CreatePatch.getPatchImage(1)).exists()){
                    encrypteDir(new File(CreatePatch.getPatchImage(1)),new File(CreatePatch.getPatchImageE(1)),pss);
                }
                if(new File(CreatePatch.getPatchImage(2)).exists()){
                    encrypteDir(new File(CreatePatch.getPatchImage(2)),new File(CreatePatch.getPatchImageE(2)),pss);
                }
                if(new File(CreatePatch.getPatchImage(3)).exists()){
                    encrypteDir(new File(CreatePatch.getPatchImage(3)),new File(CreatePatch.getPatchImageE(3)),pss);
                }
                if(new File(CreatePatch.getPatchImage(4)).exists()){
                    encrypteDir(new File(CreatePatch.getPatchImage(4)),new File(CreatePatch.getPatchImageE(4)),pss);
                }
                encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 1)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 1)+"/"),pss);
                encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 2)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 2)+"/"),pss);
                encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 3)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 3)+"/"),pss);
                encrypteDir(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE, (byte) 4)+"/"), new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.ENCRYPTE, (byte) 4)+"/"),pss);
            }catch (Exception e){
                e.printStackTrace();
            }
            f.delete();
            return null;
        }
        private void encrypteDir(File in, File out, String password) throws Exception{
            DirToFile dirToFile=new DirToFile();
            dirToFile.cht(in);
            for(File f:dirToFile.getSPISOCFile()){
                Log.d("text","tfthfhdyxncrtxxh");
                FileOperations.AESEncrypte(f,new File(out.getAbsolutePath()+"/"+f.getName()),password, FileOperations.ModeCipher.Encrypt);
            }
            for(File f:dirToFile.getSPISOCFile()){
                FileOperations.writeNullAndDelete(f);
            }
            dirToFile.clear();
        }

        @Override
        protected void onPostExecute(Void unused) {
            if (end_operations!=null){
                end_operations.finall();
            }
            super.onPostExecute(unused);
        }
    }

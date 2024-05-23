package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.File;
import java.io.IOException;

public class LoadAction extends AppCompatActivity {
    public static State[] state;
    public static int zone;
    public static FilterStateMapDialog dialogInfoMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_action);
    }

    @Override
    protected void onStart() {
        super.onStart();

            Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
            th.start();
        //MapAction.clicDialogMap1(state,zone);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        try {
            //new TimerMiliSecond().startTimer(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DialogShower.MapSawe m= null;
        try {
            m = DialogShower.read(new File(StorageApp.getAppPatch().getAbsoluteFile()+DialogShower.file_adress_properties));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        dialogInfoMode.onClic.clic(m.starr,m.zone);
         */
        //MapAction.clicDialogMap(LoadAction.state,LoadAction.zone);
        finish();
        return;
    }
}
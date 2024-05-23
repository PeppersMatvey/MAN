package get.newmaps.notes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.service.controls.actions.FloatAction;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class DialogShower implements Serializable{
    public static final String file_adress_properties="/map_prperties.prop";
    public final String State_list="state";
    public final String zone_z="zone";
    public static boolean set_mod;
    static View v;
    //TODO написать реализацию класса показывающего все диалоги
    public void showDialog(DialogInfoMode dialogInfoMode, Context context) {
        try {
            if (dialogInfoMode instanceof InfoDialog) {
                showInfoDialog(context,(InfoDialog) dialogInfoMode);
            } else if(dialogInfoMode instanceof WarningDialog){
                showWarningDialog(context,(WarningDialog) dialogInfoMode);
            } else if(dialogInfoMode instanceof FilterStateDialog){
                showStateListDialog(context,(FilterStateDialog) dialogInfoMode);
            }else if(dialogInfoMode instanceof FilterStateMapDialog){
                showStateListMapDialog(context,(FilterStateMapDialog) dialogInfoMode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showStateListMapDialog(Context context, FilterStateMapDialog dialogInfoMode) {
        if(set_mod&new File(StorageApp.getAppPatch().getAbsoluteFile()+file_adress_properties).exists() & isRead(new File(StorageApp.getAppPatch().getAbsoluteFile()+file_adress_properties))){
            try {
                //LoadAction.dialogInfoMode=dialogInfoMode;
                //context.startActivity(new Intent(context, LoadAction.class));
                MapSawe m=read(new File(StorageApp.getAppPatch().getAbsoluteFile()+file_adress_properties));
                dialogInfoMode.onClic.clic(m.starr,m.zone);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.filter_dialog);
        dialog.setCanceledOnTouchOutside(true);
        LinearLayout linearLayout=dialog.findViewById(R.id.filter_dialog_layout);
        TextView tv=new TextView(context);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(ContextCompat.getColor(context,R.color.silverText));
        tv.setText("Выберите зону...");
        tv.setTextSize(25);
        linearLayout.addView(tv);
        int zoneIsUno=0;
        byte not=0;
        for(int i=1;i<5;i++){
            if(!new File(CreatePatch.getPatchImage(i)).exists()){
                not++;
                continue;
            }
            View v=new View(context);
            zoneIsUno=i;
            v.setBackground(context.getDrawable(R.color.black));
            v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
            linearLayout.addView(v);
            TextView textView=new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(10,10,10,10);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    showStateListDialog2(context,dialogInfoMode,Integer.parseInt(textView.getText().toString()));
                }
            });
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            textView.setText(""+i);
            textView.setTextSize(25);
            linearLayout.addView(textView);
        }
        if(not==4){
            Snackbar snackbar = Snackbar.make(v, "Вы не загрузили ни одной карты", Snackbar.LENGTH_LONG);
            snackbar.setTextColor(0XFF0277BD);
            snackbar.setBackgroundTint(0XFF555555);
            snackbar.show();
            return;
        }
        if(not==3){
            showStateListDialog2(context,dialogInfoMode,zoneIsUno);
            return;
        }
        dialog.show();
    }

    private void showStateListDialog2(Context context, FilterStateMapDialog dialogInfoMode,int zone) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.filter_dialog);
        dialog.setCanceledOnTouchOutside(true);
        LinearLayout linearLayout=dialog.findViewById(R.id.filter_dialog_layout);
        final ArrayList<State>[] stateArrayList = new ArrayList[]{new ArrayList<>()};
        for(State state: dialogInfoMode.states){
            final boolean[] bMode = {true};
            TextView textView=new TextView(context);
            textView.setTextColor(state.colorState);
            textView.setTextSize(35);
            textView.setPadding(10,10,10,10);
            textView.setText(state.name);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bMode[0]) {
                        bMode[0] =false;
                        stateArrayList[0].add(state);
                        textView.setBackground(context.getDrawable(R.color.orangeProcr));
                    }else{
                        bMode[0] =true;
                        textView.setBackground(context.getDrawable(R.color.white));
                        ArrayList<State> states=new ArrayList<>();
                        for(State stTT: stateArrayList[0]){
                            if(!state.state_teg.equals(stTT.state_teg)){
                                states.add(stTT);
                            }
                        }
                        stateArrayList[0] =states;
                    }
                }
            });
            View v1=new View(context);
            v1.setBackground(context.getDrawable(R.color.black));
            v1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
            linearLayout.addView(textView);
            linearLayout.addView(v1);
        }
        TextView textView=new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(10,10,10,10);
        textView.setBackgroundColor(context.getColor(R.color.orange));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                State[] starr=new State[stateArrayList[0].size()];
                stateArrayList[0].toArray(starr);
                try {
                    write(new File(StorageApp.getAppPatch().getAbsoluteFile()+file_adress_properties),new MapSawe(starr,zone));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialogInfoMode.onClic.clic(starr ,zone);
            }
        });
        textView.setTextColor(ContextCompat.getColor(context,R.color.black));
        textView.setTextSize(25);
        textView.setText("Показать");
        linearLayout.addView(textView);
        dialog.show();
    }
    public class MapSawe implements Serializable {
        MapSawe(State[] starr, int zone){

            this.starr = starr;
            this.zone = zone;
        }
        MapSawe(){

        }
        private static final long serialVersionUID = 32745693450L;
        State[] starr;
        int zone;
    }
    public static boolean isRead(File file){
        try{
            MapSawe m=(MapSawe) new ObjectInputStream(new FileInputStream(file)).readObject();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static MapSawe read(File file) throws IOException, ClassNotFoundException {
        return (MapSawe) new ObjectInputStream(new FileInputStream(file)).readObject();
    }
    public void write(File file, MapSawe m) throws IOException {
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(m);
        oos.close();
    }

    private void showInfoDialog(Context context,InfoDialog infoDialog){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.info_dialog);
        dialog.setCanceledOnTouchOutside(true);
        TextView textView=dialog.findViewById(R.id.info_dialog_textView);
        textView.setText(infoDialog.info);
        dialog.show();
    }
    private void showWarningDialog(Context context,WarningDialog warningDialog){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.is_dialog);
        dialog.setCanceledOnTouchOutside(true);
        TextView textView=dialog.findViewById(R.id.is_dialog_info);
        AppCompatButton appCompatButtonCancel=dialog.findViewById(R.id.is_dialog_cancel);
        AppCompatButton appCompatButtonRun=dialog.findViewById(R.id.is_dialog_task);
        textView.setText(warningDialog.info);
        appCompatButtonCancel.setText(warningDialog.canceled);
        appCompatButtonRun.setText(warningDialog.run);
        appCompatButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningDialog.canceled_button.cancel();
                dialog.cancel();
            }
        });
        appCompatButtonRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningDialog.run_buttom.run();
                dialog.cancel();
            }
        });
        dialog.show();
    }
    public void showStateListDialog(Context context, FilterStateDialog filterStateDialog){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.filter_dialog);
        LinearLayout linearLayout=dialog.findViewById(R.id.filter_dialog_layout);
        TextView tv=new TextView(context);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(ContextCompat.getColor(context,R.color.silverText));
        tv.setText("Выберите статус...");
        tv.setTextSize(25);
        linearLayout.addView(tv);
        TextView textView11=new TextView(context);
        textView11.setTextColor(ContextCompat.getColor(context,R.color.black));
        textView11.setTextSize(35);
        textView11.setPadding(10,10,10,10);
        textView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if(filterStateDialog.onClic!=null){
                    filterStateDialog.onClic.clic(new State(null,0,"00"));
                }
            }
        });
        textView11.setText("Показать все");
        linearLayout.addView(textView11);
        View v=new View(context);
        v.setBackground(context.getDrawable(R.color.black));
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
        linearLayout.addView(v);
        for(State state: filterStateDialog.states){
            TextView textView=new TextView(context);
            textView.setTextColor(state.colorState);
            textView.setTextSize(35);
            textView.setPadding(10,10,10,10);
            textView.setText(state.name);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    if(filterStateDialog.onClic!=null){
                        filterStateDialog.onClic.clic(state);
                    }
                }
            });
            View v1=new View(context);
            v1.setBackground(context.getDrawable(R.color.black));
            v1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
            linearLayout.addView(textView);
            linearLayout.addView(v1);
        }
        dialog.show();
    }
/*
    private void showDialogVizitAdd(Context context, DialogAddVizit dialogInfoMode) throws IOException, ClassNotFoundException {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_vizit);
        dialog.setCanceledOnTouchOutside(false);
        AutoEdit = dialog.findViewById(R.id.auto_complete_add_state);
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.tv_view, getArrayString());
        AutoEdit.setAdapter(adapter);
        EditText editText = dialog.findViewById(R.id.edit_str_vizit_dialog);
        AppCompatButton appCompatButton = dialog.findViewById(R.id.button_str_vizit);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                if (!str.equals("")) {
                    str = "Нет описания визита";
                }
                String teg = searshTeg(AutoEdit.getText().toString());
                if (teg == null) {
                    Toast.makeText(context, "Выберите тег", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTegState(teg, dialogInfoMode.oneHome.ADRESS);
                saweVizit(new Imports.Vizit("" + new Date(), str), dialogInfoMode.oneHome.ADRESS);
                if (dialogInfoMode.finish != null) {
                    dialogInfoMode.finish.finalis();
                }
            }
        });
        dialog.show();
    }
 */
}
class FilterStateDialog implements DialogInfoMode{
    public final ArrayList<State> states;
    public final OnClic onClic;

    FilterStateDialog(ArrayList<State> states, OnClic onClic) {
        this.states = states;
        this.onClic = onClic;
    }

    interface OnClic{
        void clic(State state);
    }
}
class FilterStateMapDialog implements DialogInfoMode{
    public final ArrayList<State> states;
    public final OnClic onClic;

    FilterStateMapDialog(ArrayList<State> states, OnClic onClic) {
        this.states = states;
        this.onClic = onClic;
    }

    interface OnClic{
        void clic(State[] state,int zone);
    }
}
class WarningDialog implements DialogInfoMode{
    public final String info;
    public final String canceled;
    public final String run;
    public final Canceled canceled_button;
    public final Run run_buttom;

    interface Canceled{
        void cancel();
    }
    interface Run{
        void run();
    }

    WarningDialog(String info, String canceled, String run, Canceled canceled_button, Run run_buttom) {
        this.info = info;
        this.canceled = canceled;
        this.run = run;
        this.canceled_button = canceled_button;
        this.run_buttom = run_buttom;
    }
}
class InfoDialog implements DialogInfoMode{
    public final String info;

    InfoDialog(String info) {
        this.info = info;
    }
}
interface DialogInfoMode{}

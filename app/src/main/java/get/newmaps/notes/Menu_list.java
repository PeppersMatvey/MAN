package get.newmaps.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

public class Menu_list extends AppCompatActivity {
    public ItemTouchHelper itemTouchHelper;
    private static resycler resycler_v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FilterList.setSearsh_str("");
        FilterList.setState(null);
        b=true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
    }

    @Override
    protected void onResume() {
        resycler.context=this;
        AppCompatButton appCompatButtonMap=findViewById(R.id.map_buttom);
        View.OnClickListener cliced=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new File(StorageApp.getAppPatch().getAbsoluteFile()+DialogShower.file_adress_properties).exists() & DialogShower.isRead(new File(StorageApp.getAppPatch().getAbsoluteFile()+DialogShower.file_adress_properties))){
                    // Создание Toast сообщения
                    Toast toast4 = new Toast(getApplicationContext());
                    // Позиционирование сообщения
                    toast4.setGravity(Gravity.CENTER, 0, 0);
                    // Определение продолжительности
                    toast4.setDuration(Toast.LENGTH_LONG);

                    // Создание View из контента файла toast.xml:
                    LayoutInflater inflater = getLayoutInflater();
                    View vw = inflater.inflate(R.layout.activity_load_action, null);
                    // Определение View компонента
                    toast4.setView(vw);
                    // Представление сообщения
                    toast4.show();
                }
                //startActivity(new Intent(Menu_list.this,LoadAction.class));
                MapAction.modeVisibleButton=true;
                startActivity(new Intent(Menu_list.this, MapAction.class));
            }
        };
        appCompatButtonMap.setOnClickListener(cliced);
        findViewById(R.id.map_buttom_layout).setOnClickListener(cliced);
        findViewById(R.id.map_buttom_text).setOnClickListener(cliced);
        try{
            setResycler();
        }catch (Exception e){
            e.printStackTrace();
        }
        //appCompatButtonMap.setBackgroundDrawable(getDrawable(R.drawable.outline_map_24_white));
        EditText editText=findViewById(R.id.edit_searsh_menu_list);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                FilterList.setSearsh_str(editText.getText().toString());
                resycler_v.setObch(FilterList.getFilterList());
            }
        });

        findViewById(R.id.filter_searsh).setOnClickListener(new View.OnClickListener() {
                                                                       @Override
                                                                       public void onClick(View v) {
                                                                           DialogShower dialogShower=new DialogShower();
                                                                           try {
                                                                               dialogShower.showDialog(new FilterStateDialog(new AddState().readState(), new FilterStateDialog.OnClic() {
                                                                                   @Override
                                                                                   public void clic(State state) {
                                                                                       try{
                                                                                           FilterList.setState(state);
                                                                                           resycler_v.setObch(FilterList.getFilterList());
                                                                                       }catch (Exception e){
                                                                                           e.printStackTrace();
                                                                                       }
                                                                                   }
                                                                               }),Menu_list.this);
                                                                           } catch (Exception e) {
                                                                               e.printStackTrace();
                                                                           }
                                                                       }
                                                                   });
        AppCompatButton appCompatButtonSettings= findViewById(R.id.settings_buttom);
        appCompatButtonSettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Menu_list.this, SettingsActivity.class));
                    }
                });
        if(StorageApp.isNightMode(this)){
            appCompatButtonSettings.setBackgroundDrawable(getDrawable(R.drawable.baseline_settings_24_white));
            appCompatButtonMap.setBackgroundDrawable(getDrawable(R.drawable.outline_map_24_white));
        }else{
            appCompatButtonSettings.setBackgroundDrawable(getDrawable(R.drawable.baseline_settings_24));
            appCompatButtonMap.setBackgroundDrawable(getDrawable(R.drawable.outline_map_24));
        }
        super.onResume();
    }
boolean b=false;
    @Override
    public void onBackPressed() {
        if(b){
           finishAffinity();
        }else{
            b=true;
            Toast.makeText(this, "Нажмите ещё раз чтобы выйти из приложения", Toast.LENGTH_LONG).show();
        }
    }

    private void setResycler() {
        /*
        FloatingActionButton floatingActionButton=findViewById(R.id.exit_app_floating_buttom);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.rotete_floating_buttom);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionButton.startAnimation(animation);

                try {
                    new encrypteTask(new encrypteTask.END_OPERATIONS() {
                        @Override
                        public void finall() {
                            //Toast.makeText(Menu_list.this, "Сейчас будет выполнен выход из приложения", Toast.LENGTH_SHORT).show();
                            finishAffinity();
                        }
                    },true).startThread();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
         */
        StorageApp.setAppPatch(getFilesDir());
        LinearLayout linearLayout=findViewById(R.id.layout_menu_list);
        linearLayout.removeAllViews();
        DirToFile dirToFile=new DirToFile();
        dirToFile.clear();
        dirToFile.cht(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)1));
        dirToFile.cht(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)2));
        dirToFile.cht(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)3));
        dirToFile.cht(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)4));
        int size=dirToFile.getSPISOCFile().size();
        dirToFile.clear();
        if(size==0){
            TextView textView=new TextView(this);
            textView.setText("Чтобы добавить зону нажмите на кнопку \"Импортровать зону\" в настройках");
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(30);
            textView.setTextColor(getResources().getColor(R.color.silverText));
            linearLayout.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return;
        }
        RecyclerView recyclerView=new RecyclerView(getApplicationContext());
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        resycler_v=new resycler(recyclerView, new resycler.ClicListener() {
            @Override
            public void onOpening(Imports.OneHome obchenie, int i) {
                Views_Home_Open.oneHome=obchenie;
                startActivity(new Intent(Menu_list.this,Views_Home_Open.class));
                finish();
            }
        });
        resycler.Green_Color=getResources().getColor(R.color.green);
        try {
            resycler_v.setObch(FileOperations.readHomes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int drag=ItemTouchHelper.UP;
                int swiped=ItemTouchHelper.END;
                return makeMovementFlags(drag,swiped);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Imports.OneHome oneHome=resycler.mData.get(viewHolder.getAdapterPosition());
                String is_del="Вы действительно хотите удалить дом по адресу ";
                String itog;
                if(oneHome.KV==-1){
                    String str = "Ул. " + oneHome.STRIT_NAME + ", Д. " + oneHome.HOME_NOMER + "(частный)?";
                    //Spannable sp = new SpannableString(str);
                    //sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), str.length() - 9, str.length(), SpannableString.SPAN_COMPOSING);
                    itog=is_del+str;
                }else{
                    itog=is_del+"Ул. "+oneHome.STRIT_NAME+", Д."+oneHome.HOME_NOMER+", Кв."+oneHome.KV+"?";
                }
                DialogShower dialogShower=new DialogShower();
                Animation can_anim=AnimationUtils.loadAnimation(Menu_list.this,R.anim.cancel_delete_anim);
                can_anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        recyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                dialogShower.showDialog(new WarningDialog(itog, "Отменить", "Удалить", new WarningDialog.Canceled() {
                    @Override
                    public void cancel() {
                        viewHolder.itemView.startAnimation(can_anim);
                        //resycler_v.setObch(resycler.mData);
                        Runtime.getRuntime().gc();
                    }
                }, new WarningDialog.Run() {
                    @Override
                    public void run() {
                        FileOperations.writeNullAndDelete(oneHome.ADRESS);
                        try {
                            resycler_v.setObch(FileOperations.readHomes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FilterList.load();
                    }
                }),Menu_list.this);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        linearLayout.addView(recyclerView);
    }
}
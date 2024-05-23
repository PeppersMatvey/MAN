package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Terminal_Activity extends AppCompatActivity {
    public static volatile boolean exit;
    public static volatile LinearLayout layout;
    public static  volatile boolean mode_edit;
    public static volatile String scanner;
    public static volatile ModeTEXT MODE_TEXT;
    public static volatile ModeEDIT MODE_EDIT;
    public static volatile boolean mode_text;
    //public static volatile boolean stop_asynk;
    public static volatile boolean mode_set_password;
    public static volatile boolean mode_exit_app;
    public static volatile boolean print_finish;
    public static volatile String print;
    private static volatile AsyncPrint asyncPrint;
    public static ImportZoneTerminal.filal_task filalTaskImport=null;
    //public statice boolean stoped;
    private static volatile ScrollView scrollView;
    public static volatile Context context;
    private static volatile Animation animation_text_view;
    public static volatile int COLOR_USER_INPUT;
    public static volatile Thread th;
    public static volatile InputMethodManager imm;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(asyncPrint!=null){
            asyncPrint.cancel(true);
            exit=false;
        }else{
            asyncPrint=new AsyncPrint();
            asyncPrint.execute();
        }
        //Log.d("Debagggg","onResume");
        filalTaskImport = new ImportZoneTerminal.filal_task() {
            @Override
            public void finalize() {
                finishAffinity();
            }
        };
        layout = findViewById(R.id.add_layout_terminal);
        animation_text_view= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.edit_animations);
        imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        COLOR_USER_INPUT=getResources().getColor(R.color.green_user_input);
        scrollView=findViewById(R.id.scroll_terminal);
        asyncPrint=new AsyncPrint();
        asyncPrint.execute();
        context=getApplicationContext();
        if (th != null) {
            th=new Thread(new StartTerminal());
            th.setPriority(Thread.MAX_PRIORITY);
            th.setDaemon(true);
            th.start();
            //th.interrupt();
        }else{
            th=new Thread(new StartTerminal());
            th.setPriority(Thread.MAX_PRIORITY);
            th.setDaemon(true);
            th.start();
        }
    }

    @Override
    public void onBackPressed() {
        exit=false;
        asyncPrint.cancel(true);
        finish();
    }
    public static void add_edit() {

        LinearLayout layout1=new LinearLayout(context);
        layout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView=new TextView(context);
        textView.setTextSize(18);
        textView.setText("user");
        textView.setTextColor(COLOR_USER_INPUT);
        TextView textView1=new TextView(context);
        textView1.setTextSize(18);
        textView1.startAnimation(animation_text_view);
        textView1.setText(">");
        textView1.setTextColor(COLOR_USER_INPUT);
        EditText ed = new EditText(context);
        ed.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ed.setTextColor(Color.WHITE);
        ed.setTextSize(18);
        ed.setPadding(26,0,0,0);
        ed.setBackground(context.getDrawable(R.drawable.style_edit_consol));
        ed.requestFocus();
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                ed.setCursorVisible(true);
            }
        });
        ed.setCursorVisible(true);
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
           // ed.setTextCursorDrawable();
        //}
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        /*
        ed.setOnKeyListener(new View.OnKeyListener() {
           @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN &
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String str = ed.getText().toString();
                    ed.setEnabled(false);
                    scanner = str.trim();
                }
                return false;
            }
        });
         */
       ed.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               String str = ed.getText().toString();
               if(str.length()<2){
                   return;
               }
               if ((str.toCharArray()[str.length()-1]) == '\n') {
                   ed.setEnabled(false);
                   stopAnimation(textView1);
                   imm.hideSoftInputFromWindow(ed.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                   scanner = str.trim();
               }
           }
       });
        //tv.setText(text);
        //tv.setTextColor(getResources().getColor(R.color.white));
        //tv.setTextSize(18);
        //if (id_color != 0) {
        //    tv.setTextColor(getResources().getColor(id_color));
        //}
        layout1.addView(textView);
        layout1.addView(textView1);
        layout1.addView(ed);
        layout.addView(layout1);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }
    public static void stopAnimation(View v) {
        v.clearAnimation();
        if (canCancelAnimation()) {
            v.animate().cancel();
        }
    }
    public static boolean canCancelAnimation() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }
    public static final byte RESOURSE_INPUT=1;
    public static final byte CLASS_COLOR_INPUT=0;
    public void add_text(String text, int id_color,byte mode) {
        //Log.d("Deagnost","onProgressText1");
        TextView tv = new TextView(getBaseContext());
        imm.hideSoftInputFromWindow(tv.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        if(text.trim().equals("")){
            tv.setText(text);
        }else{
            Spannable sp = new SpannableString("Terminal\\ "+text);
            sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.terminal)), 0, "Terminal\\ ".length(), SpannableString.SPAN_COMPOSING);
            tv.setText(sp);
        }
        tv.requestFocus();
        tv.setTextSize(18);
        //Log.d("Deagnost","onProgressText2");
        if (id_color != 0) {
            if(mode==1){
                tv.setTextColor(getResources().getColor(id_color));
            }else if(mode==0){
                tv.setTextColor(id_color);
            }
        }else{
            tv.setTextColor(getResources().getColor(R.color.white));
        }
        layout.addView(tv);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        print_finish=true;
        //Log.d("Deagnost","onProgressText3");
    }
    class AsyncPrint extends AsyncTask<Void,String,Void>{
        @Override
        protected void onProgressUpdate(String... values) {
            switch (values[0]){
                case "txt": {
                    //Log.d("Deagnost","onProgressText0");
                    add_text(print,MODE_TEXT.color,MODE_TEXT.mode_input);
                    //Log.d("Deagnost","onProgressText4");
                    break;
                }
                case "edit": {
                    add_edit();
                    break;
                }
                case "finish": {
                    imm.hideSoftInputFromWindow(new View(Terminal_Activity.this).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    asyncPrint.cancel(true);
                    startActivity(new Intent(Terminal_Activity.this,Menu_list.class));
                    finish();
                    return;
                }
                case "set password": {
                    add_text("Запомните этот пароль",0,Terminal_Activity.RESOURSE_INPUT);
                    try {
                        new TimerMiliSecond().startTimer(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    imm.hideSoftInputFromWindow(new View(Terminal_Activity.this).getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    startActivity(new Intent(Terminal_Activity.this,Password_Set.class));
                    asyncPrint.cancel(true);
                    finish();
                    break;
                }
                case "exit app encrypte":{
                    add_text("Выход будет осуществлён после зашифровки ваших данных",0,Terminal_Activity.RESOURSE_INPUT);
                    asyncPrint.cancel(true);
                    try {
                        new encrypteTask(new encrypteTask.END_OPERATIONS() {
                            @Override
                            public void finall() {
                                finishAffinity();
                            }
                        }, true).startThread();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Log.d("Deagnost","onProgressText -3");
            //Log.d("Debagggg","task Asunk");
            try{
                while(true){
                    //Log.d("Deagnost","onProgressText -2");
                    if(isCancelled()){
                        return null;
                    }
                    try {
                        new TimerMiliSecond().startTimer(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(exit){
                        exit=false;
                        publishProgress("finish");
                        return null;
                    }
                    if(mode_text){
                        //Log.d("Deagnost","onProgressText -1");
                        publishProgress("txt");
                        mode_text=false;
                    }
                    if(mode_edit){
                        publishProgress("edit");
                        mode_edit=false;
                    }
                    if(mode_set_password){
                        publishProgress("set password");
                        mode_set_password=false;
                    }
                    if(mode_exit_app){
                        publishProgress("exit app encrypte");
                        mode_exit_app=false;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
class ModeEDIT{
    public final int color;

    ModeEDIT(int color) {
        this.color = color;
    }
}
class ModeTEXT{
    public final int color;
    public byte mode_input;

    ModeTEXT(int color,byte mode_input) {
        this.color = color;
        this.mode_input=mode_input;
    }
}

class StartTerminal implements Runnable{
    public void run(){
        while (!Terminal_Activity.exit) {
            if(Terminal_Activity.th.isInterrupted()){
                return;
            }
            MySystemT.println(" Введите команду или название микропрограммы",0,Terminal_Activity.RESOURSE_INPUT);
            MySystemT.println(" Введите help для получения списка всех доступных команд",0,Terminal_Activity.RESOURSE_INPUT);
            Terminal_Programm terminal_programm=null;
            switch (MySystemT.nextLine(null).trim()){
                case "set password":{
                    Terminal_Activity.mode_set_password=true;
                    break;
                }
                case "exit activity": {
                    Terminal_Activity.exit=true;
                    return;
                }
                case "exit act": {
                    Terminal_Activity.exit=true;
                    return;
                }
                case "import zone": {
                    terminal_programm=new ImportZoneTerminal(Terminal_Activity.filalTaskImport);
                    break;
                }
                case "add state": {
                    terminal_programm=new AddState();
                    break;
                }
                case "read state":{
                    terminal_programm=new ReadState();
                    break;
                }
                case "delete":{
                    terminal_programm=new ClearZone();
                    break;
                }
                case "statistica":{
                    terminal_programm=new Statistic();
                    break;
                }
                case "exit app":{
                    Terminal_Activity.mode_exit_app=true;
                    return;
                }
                case "exit":{
                    Terminal_Activity.mode_exit_app=true;
                    return;
                }
                case "add home":{
                    terminal_programm=new AddHome();
                    break;
                }
                case "di":{
                    terminal_programm=new DownloadImage();
                    break;
                }
                case "set size":{
                    terminal_programm=new SetSizeMap();
                    break;
                }
                case "help":{
                    terminal_programm= new Terminal_Programm() {
                        @Override
                        public void run() {
                            MySystemT.println((String) Terminal_Activity.context.getText(R.string.help_terminal));
                        }
                    };
                }
            }
            if(Terminal_Activity.th.isInterrupted()){
                return;
            }
            if(terminal_programm!=null){
                terminal_programm.run();
            }
            if(Terminal_Activity.th.isInterrupted()){
                return;
            }
        }
    }
}


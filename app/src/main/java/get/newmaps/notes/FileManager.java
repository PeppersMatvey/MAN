package get.newmaps.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager extends AppCompatActivity {
    public TextView pathDir;
    public String pathDirStr;
    public static String filter;
    protected static Context context;

    public static String getFilter() {
        return filter;
    }

    public static void setFilter(String filter) {
        FileManager.filter = filter;
    }

    static volatile int code;
    static volatile File patchEnd;
    AppCompatButton appCompatButton;
    public static void setCode(int code) {
        FileManager.code = code;
    }
   // public final int CODE_FILE_MANAGER=546164026;
public LinearLayout linearLayout;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if(pathDirStr.replaceFirst(Environment.getExternalStorageDirectory().getAbsolutePath(),"").length()==0){
            startActivity(new Intent(FileManager.this, SettingsActivity.class));
            finish();
        }else{
            setList(pathDirStr);
            pathDir.setText(pathDirStr.replaceFirst(Environment.getExternalStorageDirectory().getAbsolutePath(),""));
        }
        pathDirStr=pathDirStr.substring(0,pathDirStr.lastIndexOf("/"));
        setList(pathDirStr);
        pathDir.setText(pathDirStr.replaceFirst(Environment.getExternalStorageDirectory().getAbsolutePath(),"Память устройства"));
        Log.d("path",pathDirStr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pathDir=findViewById(R.id.dir_patch);
        //pathDir.setText(Environment.getExternalStorageDirectory().getAbsolutePath()+"/");
        pathDirStr=Environment.getExternalStorageDirectory().getAbsolutePath();
        pathDir.setText(pathDirStr.replaceFirst(Environment.getExternalStorageDirectory().getAbsolutePath(),"Память устройства"));
        appCompatButton=findViewById(R.id.on_back_dir);
        StorageApp.setAppPatch(getFilesDir());
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setList(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (StorageApp.isNightMode(this)) {
            appCompatButton.setBackgroundDrawable(getDrawable(R.drawable.baseline_arrow_back_ios_new_24_white));
        }else{
            appCompatButton.setBackgroundDrawable(getDrawable(R.drawable.baseline_arrow_back_ios_new_24));
        }
        resyclerV.context=this;
    }

    private void setList(String s) {
        linearLayout=findViewById(R.id.layout_file);
        linearLayout.removeAllViews();
        RecyclerView recyclerView=new RecyclerView(getApplicationContext());
        linearLayout.addView(recyclerView);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        resyclerV resyclerV=new resyclerV(recyclerView, new resyclerV.ClicListener() {
            @Override
            public void onOpening(String obchenie, int i) {
                Log.d("path",pathDirStr+"/"+obchenie);
                if(new File(pathDirStr+"/"+obchenie).isFile()){
                    patchEnd=new File(pathDirStr+"/"+obchenie);
                    Intent intent=new Intent();
                    intent.putExtra("file_name",pathDirStr+"/"+obchenie);
                    setResult(code,intent);
                    finish();
                    return;
                }else{
                    try{
                        pathDirStr=pathDirStr+"/"+obchenie;
                        pathDir.setText(pathDirStr.replaceFirst(Environment.getExternalStorageDirectory().getAbsolutePath(),"Память устройства"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //pathDir.setText(s+obchenie+"/");
                    setList(pathDirStr);
                }
            }
        });
        try {
            //DirToFile dirToFile=new DirToFile();
            //dirToFile.cht(new File(s));
            //ArrayList<String> arrayList=new ArrayList<>();
            //for(File f:dirToFile.getSPISOCFile()){
            //}
            //    arrayList.add(f.getAbsolutePath());
            //resyclerV.setObch(arrayList);
            ArrayList<String> filesnew=new ArrayList<>();
            for(String f:Arrays.asList(new File(s).list())){
                String formatNameFile="";
                try {
                    formatNameFile = new File(f).getName().substring(new File(f).getName().lastIndexOf('.'), new File(f).getName().length());
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(formatNameFile.equals(filter)){
                    filesnew.add(f);
                }else if((new File(s+"/"+f).isDirectory())){
                    filesnew.add(f);
                } else{
                    Log.d("tefff341",s+"/"+f);
                }
                Log.d("tefff1",filter+"  format123  " +formatNameFile);
                /*
                //if(!new File(f).isDirectory()){
                    if(formatNameFile.equals(filter)){
                        filesnew.add(f);
                        Log.d("tefffBHHH",f);
                    }else if(new File(f).isDirectory()){
                        filesnew.add(f);
                        continue;
                    }
                }else {
                    filesnew.add(f);
                    continue;
                }
                 */
            }
            resyclerV.setObch(filesnew);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class resyclerV {
    public static Context context;

    public interface ClicListener {
        void onOpening(String obchenie,int i);
    }
    public RecyclerView rv;
    private ClicListener bch1;

    public void setBch1(ClicListener bch1) {
        this.bch1 = bch1;
    }

    private final Adapter ObchAdap;
    public resyclerV(RecyclerView rv, ClicListener bch1){
        this.bch1 = bch1;
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        ObchAdap=new Adapter();
        rv.setAdapter(ObchAdap);
    }
    public resyclerV(RecyclerView rv){
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        ObchAdap=new Adapter();
        rv.setAdapter(ObchAdap);
    }
    public static List<String> mData;
    public void setObch(List<String> list){
        mData=list;
        ObchAdap.setData(list);
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Vh>{
        public List<String> mData;
        void setData(List<String> data){
            mData=data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Vh(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Vh holder, int position) {
            holder.bind(mData.get(position));
        }

        @Override
        public int getItemCount() {
            try {
                return mData.size();
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }

        class Vh extends RecyclerView.ViewHolder{
            private final TextView mName;

            Vh(ViewGroup parent) {
                super(View.inflate(parent.getContext(), R.layout.uno_file2, null));
                mName=itemView.findViewById(R.id.file_name);
                //mName.setBackground(context.getDrawable(R.color.black));
                mName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                if (StorageApp.isNightMode(context)) {
                    mName.setTextColor(ContextCompat.getColor(context,R.color.white));
                }else{
                    mName.setTextColor(ContextCompat.getColor(context,R.color.black));
                }
                //попробовать использовать null вместо последнего параметра parent
                //itemView.findViewById(R.id.strit_home).setOnClickListener(v -> bch1.onOpening(mData.get(getAdapterPosition()),getAdapterPosition()));
                mName.setOnClickListener(v -> bch1.onOpening(mData.get(getAdapterPosition()),getAdapterPosition()));
            }
            void bind(String obchenie){
                //String formatNameFile="";
               // try {
                   // formatNameFile = obchenie.substring(obchenie.lastIndexOf('.'), obchenie.length());
               // }catch (Exception e){
               //     e.printStackTrace();
               // }
               // if(formatNameFile.equals(FileManager.getFilter())){
                   // itemView.setBackgroundColor(Color.parseColor("#4DDC4600"));
              //  }
                mName.setText(obchenie);
            }
        }
    }
}
package get.newmaps.notes;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class resycler {
    private static volatile ArrayList<State> stateArrayList;

    static {
        try {
            stateArrayList = new AddState().readState();
            if(stateArrayList.size()<1){
                Log.d(":gggg","null ObjAAA "+stateArrayList.size());
            }else{
                Log.d(":gggg","NOT null ObjAAA "+stateArrayList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int Green_Color;
    public static Context context;
    public interface ClicListener {
        void onOpening(Imports.OneHome obchenie,int i);
    }
    public RecyclerView rv;
    private ClicListener bch1;

    public void setBch1(ClicListener bch1) {
        this.bch1 = bch1;
    }

    private final Adapter ObchAdap;
    public resycler(RecyclerView rv, ClicListener bch1){
        this.bch1 = bch1;
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        ObchAdap=new Adapter();
        rv.setAdapter(ObchAdap);
        try {
            stateArrayList=new AddState().readState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public resycler(RecyclerView rv){
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        ObchAdap=new Adapter();
        rv.setAdapter(ObchAdap);
        try {
            stateArrayList=new AddState().readState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<Imports.OneHome> mData;
    public void setObch(List<Imports.OneHome> list){
        mData=list;
        ObchAdap.setData(list);
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Vh>{
        public List<Imports.OneHome> mData;
        void setData(List<Imports.OneHome> data){
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
            return mData.size();
        }

        class Vh extends RecyclerView.ViewHolder{
            private final TextView mName;
            private final TextView info;
            private final TextView stateTW;

            Vh(ViewGroup parent) {
                super(View.inflate(parent.getContext(), R.layout.uno_home, null));
                //попробовать использовать null вместо последнего параметра parent
                mName=itemView.findViewById(R.id.strit_home);
                stateTW=itemView.findViewById(R.id.state_info);
                stateTW.setOnClickListener(v -> bch1.onOpening(mData.get(getAdapterPosition()),getAdapterPosition()));
                itemView.findViewById(R.id.strit_home).setOnClickListener(v -> bch1.onOpening(mData.get(getAdapterPosition()),getAdapterPosition()));
                info=itemView.findViewById(R.id.info);
                mName.setOnClickListener(v -> bch1.onOpening(mData.get(getAdapterPosition()),getAdapterPosition()));
                info.setOnClickListener(v -> bch1.onOpening(mData.get(getAdapterPosition()),getAdapterPosition()));
            }
            void bind(Imports.OneHome obchenie){
                if(obchenie.KV==-1){
                    String str = "Ул. " + obchenie.STRIT_NAME + ", Д. " + obchenie.HOME_NOMER + "(частный)";
                    Spannable sp = new SpannableString(str);
                    sp.setSpan(new ForegroundColorSpan(Green_Color), str.length() - 9, str.length(), SpannableString.SPAN_COMPOSING);
                    mName.setText(sp);
                }else{
                    mName.setText("Ул. "+obchenie.STRIT_NAME+", Д."+obchenie.HOME_NOMER+", Кв."+obchenie.KV);
                }
                info.setText("Примечание: "+obchenie.STR);
                State state=null;
                try {
                    state=new ReadState().getState(obchenie.STATE,stateArrayList);
                    stateTW.setText(state.name);
                    stateTW.setTextColor(state.colorState);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(state==null){
                    Log.d(":gggg","null Obj");
                }else{
                    Log.d(":gggg","NOT null Obj");
                }
                if(stateArrayList.size()<1){
                    Log.d(":gggg","null ObjAAA "+stateArrayList.size());
                }else{
                    Log.d(":gggg","NOT null ObjAAA "+stateArrayList.size());
                }
                if(StorageApp.isNightMode(context)){
                    info.setTextColor(ContextCompat.getColor(context,R.color.white));
                    mName.setTextColor(ContextCompat.getColor(context,R.color.white));
                }else{
                    info.setTextColor(ContextCompat.getColor(context,R.color.black));
                    mName.setTextColor(ContextCompat.getColor(context,R.color.black));
                }
                if(StorageApp.isNightMode(context)){
                    if(state.colorState== Color.BLACK){
                        stateTW.setTextColor(Color.WHITE);
                    }
                }else{
                    if(state.colorState==Color.WHITE){
                        stateTW.setTextColor(Color.BLACK);
                    }
                }
            }
        }
    }
}

package get.newmaps.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.ArrayUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Views_Home_Open extends AppCompatActivity {
private TextView Home_Name_TextView;
private AppCompatButton Set_Propertes_Button;
public static int Vizit_id;
private AppCompatButton add_vizit_Button;
private TextView State_TextView;
private TextView Str_View;
private RecyclerView RecyclerView_Vizit;
public static Imports.OneHome oneHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views_home_open);
        Home_Name_TextView=findViewById(R.id.text_name_home_home_open);
        Set_Propertes_Button=findViewById(R.id.set_propertes_home);
        State_TextView=findViewById(R.id.text_state_open_home);
        Str_View=findViewById(R.id.text_str_home);
        RecyclerView_Vizit=findViewById(R.id.vizit_recycler);
        add_vizit_Button=findViewById(R.id.add_vizit);
        if(StorageApp.isNightMode(this)){
            Set_Propertes_Button.setBackgroundDrawable(getDrawable(R.drawable.baseline_settings_ethernet_24_white));
            findViewById(R.id.map_buttom_home_open).setBackgroundDrawable(getDrawable(R.drawable.outline_map_24_white));
        }else{
            findViewById(R.id.map_buttom_home_open).setBackgroundDrawable(getDrawable(R.drawable.outline_map_24));
            Set_Propertes_Button.setBackgroundDrawable(getDrawable(R.drawable.baseline_settings_ethernet_24));
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Views_Home_Open.this, Menu_list.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            oneHome = new Imports().readHome(oneHome.ADRESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        State state = null;
        try {
            state = new ReadState().getState(oneHome.STATE);
            State_TextView.setText(state.name);
            State_TextView.setTextColor(state.colorState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StorageApp.isNightMode(this)){
            if(state.colorState==Color.BLACK){
                State_TextView.setTextColor(Color.WHITE);
            }
        }else{
            if(state.colorState==Color.WHITE){
                State_TextView.setTextColor(Color.BLACK);
            }
        }
        findViewById(R.id.map_buttom_home_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapAction.modeVisibleButton=false;
                MapAction.oneHomepublic=oneHome;
                startActivity(new Intent(Views_Home_Open.this, MapAction.class));
            }
        });
        Set_Propertes_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPropertesHomeOpen.oneHome = oneHome;
                startActivity(new Intent(Views_Home_Open.this, SetPropertesHomeOpen.class));
                finish();
            }
        });
        try {
            oneHome = new Imports().readHome(oneHome.ADRESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = null;
        if (oneHome.KV == -1) {
            str = "Ул. " + oneHome.STRIT_NAME + ", Д. " + oneHome.HOME_NOMER + "(частный)";
            Spannable sp = new SpannableString(str);
            sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), str.length() - 9, str.length(), SpannableString.SPAN_COMPOSING);
            Home_Name_TextView.setText(sp);
        } else {
            str = "Ул. " + oneHome.STRIT_NAME + ", Д. " + oneHome.HOME_NOMER + ", Кв. " + oneHome.KV;
            Home_Name_TextView.setText(str);
        }
        Str_View.setText(oneHome.STR);
        RecyclerVizit recyclerVizit = new RecyclerVizit(RecyclerView_Vizit);
        recyclerVizit.setBch1(new RecyclerVizit.ClicListener() {
            @Override
            public void onOpening(Imports.Vizit vizit, int i) {
                Refacte_vizit.Vizit_id=i;
                Refacte_vizit.vizit=vizit;
                startActivity(new Intent(Views_Home_Open.this,Refacte_vizit.class));
                //Toast.makeText(Views_Home_Open.this, "Здесь пока что ничего нет", Toast.LENGTH_SHORT).show();
            }
        });
        ArrayList<Imports.Vizit> vizitArrayList = new ArrayList<>(oneHome.VIZIT_ARRAY.length);
        for (Imports.Vizit vizit : oneHome.VIZIT_ARRAY) {
            vizitArrayList.add(vizit);
        }
        recyclerVizit.setObch(vizitArrayList);

        add_vizit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_vizit.oneHome = oneHome;
                add_vizit.finish = new add_vizit.finalInterface() {
                    @Override
                    public void finalis() {
                        recyclerVizit.ObchAdap.update();
                    }
                };

                startActivity(new Intent(Views_Home_Open.this, add_vizit.class));
            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
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
                SimpleDateFormat formater = new SimpleDateFormat("d-MMMM-yyyy H:mm");
                DialogShower dialogShower=new DialogShower();
                int pozVizit=viewHolder.getAdapterPosition();
                Animation can_anim= AnimationUtils.loadAnimation(Views_Home_Open.this,R.anim.cancel_delete_anim);
                can_anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        RecyclerView_Vizit.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                WarningDialog warningDialog=new WarningDialog("Удалить визит за "+formater.format(oneHome.VIZIT_ARRAY[pozVizit].DATE) +"? ", "Отмена", "Удалить", new WarningDialog.Canceled() {
                    @Override
                    public void cancel() {
                        viewHolder.itemView.startAnimation(can_anim);
                        Toast.makeText(Views_Home_Open.this, "Удаление отменено", Toast.LENGTH_SHORT).show();
                    }
                }, new WarningDialog.Run() {
                    @Override
                    public void run() {
                        try {
                            Imports.OneHome home=new Imports().readHome(oneHome.ADRESS);
                            home.VIZIT_ARRAY=removeElement(home.VIZIT_ARRAY,pozVizit);
                            new Imports().write_home(home,oneHome.ADRESS);
                            oneHome=home;
                            vizitArrayList.clear();
                            for (Imports.Vizit vizit : oneHome.VIZIT_ARRAY) {
                                vizitArrayList.add(vizit);
                            }
                            recyclerVizit.setObch(vizitArrayList);
                            FilterList.load();
                            //recyclerVizit.ObchAdap.update();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialogShower.showDialog(warningDialog,Views_Home_Open.this);
            }
        });
        itemTouchHelper.attachToRecyclerView(RecyclerView_Vizit);
    }
    public Imports.Vizit[] removeElement(Imports.Vizit[] oldVizit,int id){
        Imports.Vizit[] homeNEW=new Imports.Vizit[oldVizit.length-1];
        boolean b=true;
        int get=0;
        for(int i=0;i< oldVizit.length-1;i++) {
            if ((b & i == id)) {
                b=false;
                get=1;
                homeNEW[i] = oldVizit[i+get];
            }else{
                homeNEW[i] = oldVizit[i+get];
            }
        }
        return homeNEW;
    }
}
class RecyclerVizit{
    interface ClicListener {
        void onOpening(Imports.Vizit vizit, int i);
    }
    public RecyclerView rv;
    private RecyclerVizit.ClicListener bch1;

    public void setBch1(RecyclerVizit.ClicListener bch1) {
        this.bch1 = bch1;
    }

    public final RecyclerVizit.Adapter ObchAdap;
    public RecyclerVizit(RecyclerView rv, RecyclerVizit.ClicListener bch1){
        this.bch1 = bch1;
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        ObchAdap=new RecyclerVizit.Adapter();
        rv.setAdapter(ObchAdap);
    }
    public RecyclerVizit(RecyclerView rv){
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        ObchAdap=new RecyclerVizit.Adapter();
        rv.setAdapter(ObchAdap);
    }
    public static List<Imports.Vizit> mData;
    public void setObch(List<Imports.Vizit> list){
        mData=list;
        ObchAdap.setData(list);
    }

    class Adapter extends RecyclerView.Adapter<RecyclerVizit.Adapter.Vh>{
        private List<Imports.Vizit> mData;
        void setData(List<Imports.Vizit> data){
            mData=data;
            notifyDataSetChanged();
        }
        void update(){
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerVizit.Adapter.Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerVizit.Adapter.Vh(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerVizit.Adapter.Vh holder, int position) {
            holder.bind(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class Vh extends RecyclerView.ViewHolder{
            private final TextView mName;
            private final TextView info;
            private final TextView state;

            Vh(ViewGroup parent) {
                super(View.inflate(parent.getContext(), R.layout.uno_home, null));
                //попробовать использовать null вместо последнего параметра parent
                mName=itemView.findViewById(R.id.strit_home);
                state=itemView.findViewById(R.id.state_info);
                state.setOnClickListener(v -> bch1.onOpening(mData.get(getAdapterPosition()),getAdapterPosition()));
                itemView.findViewById(R.id.strit_home).setOnClickListener(v -> bch1.onOpening(mData.get(getAdapterPosition()),getAdapterPosition()));
                info=itemView.findViewById(R.id.info);
                mName.setOnClickListener(v -> bch1.onOpening(mData.get(getAdapterPosition()),getAdapterPosition()));
                info.setOnClickListener(v -> bch1.onOpening(mData.get(getAdapterPosition()),getAdapterPosition()));
            }
            void bind(Imports.Vizit obchenie){
                mName.setText("Содержание: "+obchenie.NOTES);
                mName.setTextSize(23);
                mName.setGravity(Gravity.CENTER_VERTICAL);
                SimpleDateFormat formater = new SimpleDateFormat("d-MMMM-yyyy H:mm");
                info.setText("Дата: "+formater.format(obchenie.DATE));
                info.setTextSize(17);
                info.setGravity(Gravity.CENTER_VERTICAL);
                if(StorageApp.isNightMode(info.getContext())){
                    info.setTextColor(Color.WHITE);
                    mName.setTextColor(Color.WHITE);
                }else{
                    info.setTextColor(Color.BLACK);
                    mName.setTextColor(Color.BLACK);
                }
            }
        }
    }
}
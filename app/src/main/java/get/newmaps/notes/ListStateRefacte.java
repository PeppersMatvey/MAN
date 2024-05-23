package get.newmaps.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListStateRefacte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_state_refacte);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linearLayout=findViewById(R.id.layout_refacte_list);
        try{
            for(State state:new AddState().readState()){
                TextView textView=new TextView(this);
                textView.setTextColor(state.colorState);
                if(StorageApp.isNightMode(this)) {
                    linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.silverText2));
                }
                textView.setText(state.name);
                textView.setPadding(10,0,0,0);
                textView.setTextSize(35);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CreateAndResetState.setAdressState(state.patch);
                        startActivity(new Intent(ListStateRefacte.this, CreateAndResetState.class));
                        finish();
                    }
                });
                linearLayout.addView(textView);
                TextView textView1=new TextView(this);
                textView1.setText("\n ");
                textView1.setTextSize(17);
                linearLayout.addView(textView1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
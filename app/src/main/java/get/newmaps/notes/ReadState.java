package get.newmaps.notes;

import android.graphics.Color;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class ReadState implements Terminal_Programm{
    @Override
    public void run() {
        try {
            for(State st:new AddState().readState()){
                if(st.colorState == Color.BLACK){
                    st.colorState=Color.WHITE;
                }
                MySystemT.println(st.name, st.colorState, Terminal_Activity.CLASS_COLOR_INPUT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public State getState(String teg) throws Exception {
        for(State st:new AddState().readState()){
            //Log.d("Diagnostic","Диагностика статуса null "+teg+" "+st.state_teg);
            if(teg.equals(st.state_teg)){
                //Log.d("Diagnostic","Диагностика статуса null "+teg+" "+st.state_teg);
                return st;
            }
        }
        return null;
    }

    public State getState(String teg, ArrayList<State> stateArrayList) {
        for(State st:stateArrayList){
            //Log.d("Diagnostic","Диагностика статуса null "+teg+" "+st.state_teg);
            if(teg.equals(st.state_teg)){
                //Log.d("Diagnostic","Диагностика статуса null "+teg+" "+st.state_teg);
                return st;
            }
        }
        return null;
    }
}

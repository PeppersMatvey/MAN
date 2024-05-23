package get.newmaps.notes;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TimerMiliSecond {

    synchronized public void startTimer(long milicekond) throws InterruptedException {
        Timess tim=new Timess(milicekond);
        ExecutorService Exx= Executors.newFixedThreadPool(1);
        Future<Void> task = Exx.submit(tim);
        while(!task.isDone()){
            super.wait(1);
        }
        Exx.shutdown();
    }
}

class Timess implements Callable {
    private final long mili;
    public Timess(long milisecond){
        this.mili=milisecond;
    }
    public synchronized Void call() throws InterruptedException {
        super.wait(this.mili);
        return null;
    }
}

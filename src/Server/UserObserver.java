package Server;


import java.util.ArrayList;
import java.util.Vector;

public class UserObserver implements Runnable {
    /**
     * Default constructor
     */
    @Override
    public void run() {
        while(true){
            Update();
        }
    }

    /**
     *
     */
    private Vector<UserListener> Listeners=new Vector<>();

    /**
     *
     */
    public void subscribe(UserListener listener) {
        Listeners.add(listener);
    }

    /**
     *
     */
    public void unsubscribe(UserListener listener) {
        Listeners.remove(listener);
    }

    /**
     * Update states with a 1 sec interval
     */
    public void Update() {
        for (int i = 0; i < Listeners.size(); i++) {
            Listeners.get(i).refreshUserList();
        }
    }
}

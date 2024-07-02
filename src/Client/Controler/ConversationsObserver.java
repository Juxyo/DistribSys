package Client.Controler;

import java.util.*;

/**
 * 
 */
public class ConversationsObserver implements Runnable {

    /**
     * Default constructor
     */
    @Override
    public void run() {
        while(true){
            Update();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 
     */
    private Vector<ConversationsListener> Listeners=new Vector<>();

    /**
     * 
     */
    public void subscribe(ConversationsListener listener) {
        Listeners.add(listener);
    }

    /**
     * 
     */
    public void unsubscribe(ConversationsListener listener) {
        Listeners.remove(listener);
    }

    /**
     * Update states with a 1 sec interval
     */
    public void Update() {
        for (int i = 0; i < Listeners.size(); i++) {
            Listeners.get(i).updateConversationsList();
        }
    }

}
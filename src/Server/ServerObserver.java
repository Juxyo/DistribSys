package Server;

import java.util.Vector;

/**
 * 
 */
public class ServerObserver implements Runnable {

    /**
     * Default constructor
     */
    @Override
    public void run() {
        while(true){
            Update();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 
     */
    private Vector<ServerListener> listeners =new Vector<>();

    /**
     * 
     */
    public void subscribe(ServerListener listener) {
        listeners.add(listener);
    }

    /**
     * 
     */
    public void unsubscribe(ServerListener listener) {
        listeners.remove(listener);
    }

    /**
     * Update states with a 1 sec interval
     */
    public void Update() {
        for (ServerListener listener:listeners) {
            listener.updateServer();
        }
    }

}
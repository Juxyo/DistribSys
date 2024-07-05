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

    public void Update() {
        for (ServerListener listener:listeners) {
            listener.updateServer();
        }
    }

}
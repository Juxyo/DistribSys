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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 
     */
    private Vector<Server> Server=new Vector<>();

    /**
     * 
     */
    public void subscribe(Server listener) {
        Server.add(listener);
    }

    /**
     * 
     */
    public void unsubscribe(Server listener) {
        Server.remove(listener);
    }

    /**
     * Update states with a 1 sec interval
     */
    public void Update() {
        for (int i = 0; i < Server.size(); i++) {
            Server.get(i).updateServer();
        }
    }

}
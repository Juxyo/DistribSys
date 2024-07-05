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
    private Vector<MainServer> MainServer =new Vector<>();

    /**
     * 
     */
    public void subscribe(MainServer listener) {
        MainServer.add(listener);
    }

    /**
     * 
     */
    public void unsubscribe(MainServer listener) {
        MainServer.remove(listener);
    }

    /**
     * Update states with a 1 sec interval
     */
    public void Update() {
        for (int i = 0; i < MainServer.size(); i++) {
            MainServer.get(i).updateServer();
        }
    }

}
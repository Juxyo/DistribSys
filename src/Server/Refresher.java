package Server;

import java.util.ArrayList;
import java.util.Vector;

public class Refresher implements Runnable {

    private MainServer server;
    public Refresher(MainServer server){
        this.server=server;
    }

    private void send(){
        Vector<User> users=server.getUserList();
        for (int i=0;i<users.size();i++) {
            User user=users.get(i);
            try {
                Server.UDPUtils.returnAuth("True",server.getDecomposedUserList(),user.getHost());
                user.incrementLastResponse();
                if (user.getLastResponse()>10) {
                    server.getUserList().remove(user);
                    System.out.println("User: "+user.getUserName()+" disconnected");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void run(){

        while(true){
            send();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

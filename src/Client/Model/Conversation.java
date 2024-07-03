package Client.Model;

import java.util.ArrayList;
import java.util.Vector;

public class Conversation {
    private String name;

    public Conversation (String name){
        this.name=name;
    }
    private Vector<Message> messages=new Vector<>();

    public Vector<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message msg){
        messages.add(msg);
    }

    public String getName() {
        return name;
    }

    public void sort(int index){

        if(index==messages.size()-1) {
            return;
        }
        Message m1=messages.get(index);
        Message m2=messages.get(index+1);
        if(VectorClock.clockCompare(m1.getClock(),m2.getClock())) sort(index+1);
        else {
            messages.set(index,m2);
            messages.set(index+1,m1);
            if(index>0) sort(index-1);
            else sort(0);
        }
    }

}

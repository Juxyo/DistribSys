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
}

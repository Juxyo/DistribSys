package Client.Model;

import Client.Controler.ConversationsListener;
import Client.Controler.UDPUtils;
import Client.Model.Message;

import java.util.*;

/**
 * 
 */
public class ConversationList implements ConversationsListener {
    private int port;

    private boolean port_available=true;

    /**
     * Default constructor
     */
    public ConversationList(int port) {
        this.port=port;
    }

    /**
     * 
     */
    private Vector<Conversation> conversations=new Vector<>();

    /**
     * 
     */
    public void updateConversationsList() {
        String[] data=null;
        try {
            data=UDPUtils.receiveData(port).split(";");
        } catch (Exception e) {
            System.out.println("Port already in use");
            port_available=false;
            return;
        }
        String source_address=data[0];
        String conversation_name=data[1];
        String msg=data[2];
        String[] clock=data[3].split(";");
        ArrayList<String> clockstates=new ArrayList<>();
        for (String state:clock) {
            clockstates.add(state);
        }
        System.out.println("Received -->\nSource : "+source_address+"\n"+"Conversation : "+conversation_name+"\n"+"Message :\n"+msg);
        for (Conversation conv:conversations) {
            if(conv.getName().equals(conversation_name)){
                conv.addMessage(new Message(msg,clockstates));
                return;
            }
        }
        System.out.println("Conversation not found!");
    }

    public void addConversation(Conversation conv){
        conversations.add(conv);
    }

    public void removeConversation(Conversation conv){
        conversations.remove(conv);
    }

    public Vector<Conversation> getConversations() {
        return conversations;
    }

    public boolean isPort_available() {
        return port_available;
    }
}
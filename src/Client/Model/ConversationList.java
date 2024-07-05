package Client.Model;

import Client.Controler.ConversationsListener;
import Client.Controler.UDPUtils;
import Client.Controler.Client;

import java.util.*;

/**
 *
 */
public class ConversationList implements ConversationsListener {
    private int port;

    private Client cli;

    /**
     *
     */
    private ArrayList<VectorClock> clocks=new ArrayList<>();

    private boolean port_available=true;

    /**
     * Default constructor
     */
    public ConversationList(Client cli) {
        this.cli=cli;
        this.port=cli.getPort();
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
        //test message type
        if(data.length==2) {
            cli.login(data);
        }else{
            String username="";
            String source_address=data[0];
            String conversation_name=data[1];
            String msg=data[2];
            String clock=data[3];
            System.out.println("Received at "+clock+" -->\nSource : "+source_address+"\n"+"Conversation : "+conversation_name+"\n"+"Message :\n"+msg);
            if(!cli.getKnownHostsaddr().containsValue(source_address)){
                //TODO mettre le nom du nouvel utilisateur au lieu de "userX"
                username="user"+(cli.getKnownHosts().size()+1);
                cli.getKnownHosts().add(username);
                cli.getKnownHostsaddr().put(username,source_address);
                cli.addChanel(username);
                System.out.println("Added "+source_address+" to the known hosts as "+username);
            }else{

                ArrayList<String> hosts=cli.getKnownHosts();
                for (String host:hosts) {
                    if(cli.getKnownHostsaddr().get(host).equals(source_address)){
                        username=host;
                        int userid=cli.getKnownHosts().indexOf(username);
                        if(conversation_name.split("-").length>1) {
                            cli.getClock().setClock(1,clock.split(",")[0]);
                        } else cli.getClock().setClock(userid+1,clock.split(",")[0]);
                    }
                }
            }
            if(!conversation_name.equals("General")){
                ArrayList<String> hosts=cli.getKnownHosts();
                Vector<Conversation> convs=cli.getConvs().getConversations();
                String sourceHostName="";
                for (int i = 0; i < hosts.size(); i++) {
                    if(cli.getKnownHostsaddr().get(hosts.get(i)).equals(source_address)) sourceHostName=hosts.get(i);
                }
                for (int i = 1; i < convs.size(); i++) {
                    Conversation conv =convs.get(i);
                    if(conv.getName().split("-")[1].equals(sourceHostName) && !conv.contains(new Message(username+": "+msg,clock))) {
                        conv.addMessage(new Message(username+": "+msg,clock));
                        conv.sort(0);
                    }
                }
            }else {
                if(!cli.getConvs().getConversations().get(0).contains(new Message(username+": "+msg,clock))) {
                    cli.getConvs().getConversations().get(0).addMessage(new Message(username + ": " + msg, clock));
                    cli.getConvs().getConversations().get(0).sort(0);
                }
            }
        }
    }

    public void addConversation(Conversation conv){
        conversations.add(conv);
        clocks.add(new VectorClock());
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

    public ArrayList<VectorClock> getClocks() {
        return clocks;
    }

}
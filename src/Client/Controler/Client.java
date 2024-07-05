package Client.Controler;

import Client.Model.Conversation;
import Client.Model.ConversationList;
import Client.Model.VectorClock;
import Client.Vue.MainView;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 
 */
public class Client {

    /**
     *
     */
    private int port=UDPUtils.getAvailablePort();


    /**
     * 
     */
    private String address;

    private static String addressServer;
    private static String addressUpdateServer;

    /**
     * 
     */
    private String username="moi";

    /**
     *
     */
    private MainView mainView;

    /**
     *
     */
    private String currentConv="General";

    /**
     *
     */
    private ConversationList convs=new ConversationList(this);

    private boolean loggedIn=false;

    /**
     *
     */
    private ConversationsObserver convObserver;

    /**
     *
     */
    private HashMap<String,String> knownHostsaddr=new HashMap<>();

    /**
     *
     */
    private ArrayList<String> knownHosts=new ArrayList<>();

    /**
     * Default constructor
     */
    public Client(String serverAddress) {

        try {
            address= InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        convObserver=new ConversationsObserver();
        Thread t1=new Thread(convObserver);
        t1.start();

        convObserver.subscribe(convs);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(!convs.isPort_available()){
            System.out.println("Searching a port...");
            convObserver.unsubscribe(convs);
            port=UDPUtils.getAvailablePort();
            System.out.println("Trying port: "+port);
            convs=new ConversationList(this);
            convObserver.subscribe(convs);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Client working on: "+address+":"+port);
        convs.addConversation(new Conversation("General"));
        addressServer=serverAddress+":6969";
        addressUpdateServer=serverAddress+":6970";
        mainView=new MainView(this,false);
    }
    /**
     *
     */
    public void sendLoginRequest(String username,String password){
        mainView.getLoginView().setStatusLabel("Request sent to "+addressServer);
        this.username=username;
        try {
            UDPUtils.sendAuth(address+":"+port,username,password,addressServer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    public void sendMessage(String msg,String conv,ArrayList<String> destinationHosts) {
        if(conv!=null){
            VectorClock clock=getClock();
            clock.increment();
            //System.out.println("Sending message at "+clock.toString());
            for (String host:destinationHosts) {
                try {
                    UDPUtils.sendData(address+":"+port,msg,conv,clock.toString(),knownHostsaddr.get(host));
                    UDPUtils.sendData(address+":"+port,msg,conv,clock.toString(),knownHostsaddr.get(host));
                    UDPUtils.sendData(address+":"+port,msg,conv,clock.toString(),knownHostsaddr.get(host));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }else System.out.println("Conversation not found!");
    }

    /**
     *
     */
    public void sendMessage(String msg,String conv,String destinationHost) {
        if(conv!=null){
            VectorClock clock=getClock();
            clock.increment();
            //System.out.println("Sending message at "+clock.toString());
            try {
                UDPUtils.sendData(address+":"+port,msg,conv,clock.toString(),knownHostsaddr.get(destinationHost));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else System.out.println("Conversation not found!");
    }

    /**
     * 
     */
    public void open(String convName) {
        currentConv=convName;
        mainView.setTitle("Chat: "+convName);
        mainView.getConvView().updateConversationsList();
        System.out.println("Chat: "+convName+" opened!");
    }

    /**
     *
     */
    public void addChanel(String hostName) {
        convs.addConversation(new Conversation(username+"-"+hostName));
        convs.getClocks().get(convs.getClocks().size()-1).addClock("0");
        mainView.getMenuView().refreshChanelList(convs);
        getClock().addClock("1");
    }

    /**
     *
     */
    public void removeChanel(String hostName) {
        Vector<Conversation> conversations=convs.getConversations();
        for (Conversation conv:conversations) {
            if(conv.getName().contains(username) && conv.getName().contains(hostName)){
                convs.removeConversation(conv);
                mainView.getMenuView().refreshChanelList(convs);
                return;
            }
        }
    }

    /**
     * 
     */
    public void login(String[] data) {
        if (data[0].equals("True")){
            loggedIn=true;
            System.out.println("Logged in as "+username+"!");
            knownHostsaddr.clear();
            knownHosts.clear();
            if(data[1].equals("none")){
                mainView.setVisible(false);
                mainView=new MainView(this,true);
                return;
            }
            mainView.setVisible(false);
            mainView=new MainView(this,true);
            for ( String host : data[1].split(",")){
                String[] temp = host.split("-");
                knownHosts.add(temp[0]);
                knownHostsaddr.put(temp[0],temp[1]);
                addChanel(temp[0]);
            }
            sendMessage(username+" connected!","General",knownHosts);
        }else mainView.getLoginView().setStatusLabel("Connexion refused!");
    }

    public void refreshUserList(String[] data){
        String[] users=data[1].split(",");
        for (int i = 0; i < knownHosts.size(); i++) {
            boolean is_user_still_connected=false;
            for (String user:users) {
                if(user.equals("none")) return;
                String[] temp = user.split("-");
                if(knownHosts.get(i).equals(temp[0])) {
                    is_user_still_connected=true;
                }
            }
            if (!is_user_still_connected) {
                removeChanel(knownHosts.get(i));
                knownHostsaddr.remove(knownHosts.get(i));
                knownHosts.remove(knownHosts.get(i));
            }
        }
        try {
            UDPUtils.sendPingToServer(username,addressUpdateServer);
            Thread.sleep(100);
            UDPUtils.sendPingToServer(username,addressUpdateServer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public ConversationsObserver getConvObserver() {
        return convObserver;
    }

    public ConversationList getConvs() {
        return convs;
    }

    public VectorClock getClock() {
        Vector<Conversation> convsVector=convs.getConversations();
        for (int i = 0; i < convsVector.size(); i++) {
            if(convsVector.get(i).getName().equals(currentConv)){
                return convs.getClocks().get(i);
            }
        }
        System.out.println("Error while reaching clock");
        return null;
    }

    public int getPort() {
        return port;
    }

    public ArrayList<String> getKnownHosts() {
        return knownHosts;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public HashMap<String, String> getKnownHostsaddr() {
        return knownHostsaddr;
    }

    public String getCurrentConv() {
        return currentConv;
    }

    public String getUsername() {
        return username;
    }
}
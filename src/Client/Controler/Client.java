package Client.Controler;

import Client.Model.Conversation;
import Client.Model.ConversationList;
import Client.Model.VectorClock;
import Client.Vue.MainView;
import Client.Vue.MenuView;

import java.net.BindException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import static java.net.InetAddress.getLocalHost;

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
    public Client() {

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

        //TODO enlever ce qu'il y a dans le bloc ci-dessous et implémenter le login
        knownHosts.add("moi");
        System.out.println("enter the port to send messages");
        Scanner in = new Scanner(System.in); // using java.util.Scanner;
        knownHostsaddr.put("moi","192.168.56.1:"+in.nextLine());
        //
        login();

        mainView=new MainView(this);
        addChanel("moi");
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
    public void login() {
        // TODO implement here
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
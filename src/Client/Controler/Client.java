package Client.Controler;

import Client.Model.Conversation;
import Client.Model.ConversationList;
import Client.Vue.MainView;

import java.net.BindException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.net.InetAddress.getLocalHost;

/**
 * 
 */
public class Client {

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
            convs=new ConversationList(port);
            convObserver.subscribe(convs);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Client working on: "+address+":"+port);
        convs.addConversation(new Conversation("General"));

        knownHosts.add("moi");
        System.out.println("enter the port to send messages");
        Scanner in = new Scanner(System.in); // using java.util.Scanner;
        knownHostsaddr.put("moi","192.168.56.1:"+in.nextLine());


        new MainView(this);
    }

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
    private String username;

    /**
     *
     */
    private ConversationList convs=new ConversationList(port);

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
     * 
     */
    public void sendMessage(String msg,String conv) {
        if(conv==null){
            for (String host:knownHosts) {
                try {
                    System.out.println("Sending message");
                    UDPUtils.sendData(address+":"+port,msg,"General","0,0",knownHostsaddr.get(host));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 
     */
    public void open() {
        // TODO implement here
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
}
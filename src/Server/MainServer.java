package Server;

import Client.Controler.ConversationsObserver;
import Client.Controler.UDPUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class MainServer implements ServerListener,UserListener{
    private Vector<User> userList;
    private ArrayList<User> whiteList=new ArrayList<>();

    private UserObserver uo=new UserObserver();
    private ServerObserver so=new ServerObserver();

    private Refresher rf=new Refresher(this);

    public static void main(String[] args) {
        if(args.length==1) {
            try {
                if (args[0].split(".")[1].equals("csv")) {
                    MainServer mainServer = new MainServer(args[0]);
                }else System.out.println("Error: please use a .csv file");
            } catch (Error e) {
                System.out.println("file name error");
            }
        }
        else System.out.println("Error: Usage java -jar Server.jar <csvFilePath>");
    }

    public MainServer(String usersFile) {
        userList=new Vector<User>();
        getUsers(usersFile);
        so.subscribe(this);
        String address="";
        try {
            address= InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server is listening on:\n"+address+":6969");
        //System.out.println("Launching user observer");
        uo.subscribe(this);
        Thread t1=new Thread(uo);
        t1.start();
        //System.out.println("Launching login observer");
        so.subscribe(this);
        Thread t2=new Thread(so);
        t2.start();
        //System.out.println("Launching refresh sender");
        Thread t3=new Thread(rf);
        t3.start();
        while (true);
    }



    private void getUsers(String filePath) {
        System.out.println("Getting users from "+filePath);
        try {
            ArrayList<String[]> usersList=CSVFileReader.readCSV(filePath);
            for (String[] user:usersList) {
                whiteList.add(new User(user[0],user[1],""));
            }
        } catch (IOException e) {
            System.out.println("Error while reading the Users files");
        }
    }

    public boolean addUser(User user) {
        boolean is_already_connected=false;
        for (User userFormList:userList) {
            if(userFormList.compareTo(user)) is_already_connected=true;
        }
        if(!is_already_connected){
            userList.add(user);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean authenticate(String username, String password, String host) {
        System.out.println("Attempting login as: "+username);
        for(User u : whiteList){
            System.out.println(u.getUserName()+":"+u.getPassword());
            if(u.getUserName().equals(username) && u.getPassword().equals(password)){

                if (!addUser(new User(username, password, host)))return false;
                return true;
            }
        }
        return false;
    }

    public String getDecomposedUserList() {
        String tempList="";
        if(userList.size()==0) return "none";
        for(User u : userList){
            tempList+=(u.getUserName()+"-"+u.getHost()+",");
        }
        return tempList;
    }

    public void updateServer() {
        String[] data=null;
        try {
            data= UDPUtils.receiveData(6969).split(";");
        } catch (Exception e) {
            System.out.println("Port already in use");
            return;
        }
        String source_address=data[0];
        String login=data[1];
        String password=data[2];
        System.out.println("Auth received :\nSource : "+source_address+"\n"+"login : "+login+" - *******");
        String userlist=getDecomposedUserList();
        if (authenticate(login,password, source_address)) {
            System.out.println("Login successful");
            try {
                Server.UDPUtils.returnAuth("True",userlist,source_address);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("Access denied!");
            try {
                Server.UDPUtils.returnAuth("False","none",source_address);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void refreshUserList() {
        String data=null;
        try {
            data= UDPUtils.receiveData(6970);
        } catch (Exception e) {
            System.out.println("Error: Port already in use");
            return;
        }
        for (User listUser:userList) {
            //System.out.println("refreshing user: "+listUser.getUserName());
            if(listUser.getUserName().equals(data)){
                listUser.resetLastResponse();
            }
        }
    }

    @Override
    public Vector<User> getUserList() {
        return userList;
    }
}
package Server;

import Client.Controler.UDPUtils;

import java.io.IOException;
import java.util.*;

public class Server implements ServerListener{

    public static void main(String[] args) {Server server = new Server();}

    public Server() {
        userList=new ArrayList<User>();
        getUsers();
    }

    private ArrayList<User> userList;
    private ArrayList<User> whiteList;

    private void getUsers() {
        try {
            ArrayList<String[]> usersList=CSVFileReader.readCSV("Users.txt");
            for (String[] user:usersList) {
                whiteList.add(new User(user[0],user[1],""));
            }
        } catch (IOException e) {
            System.out.println("Error while reading the Users files");
        }
    }

    public boolean addUser(User user) {
        if(!userList.contains(user)){
            userList.add(user);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean authenticate(String username, String password, String host) {
        for(User u : userList){
            if(u.getUserName().equals(username) && u.getPassword().equals(password)){
                if (addUser(new User(username, password, host))){}
                return true;
            }
        }
        return false;
    }

    public String getDecomposedUserList() {
        String tempList="";
        for(User u : userList){
            tempList.concat(u.getUserName()+";"+u.getPassword()+"\n");
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
        String clock=data[1];
        String login=data[2];
        String password=data[3];
        System.out.println("Login received at "+ clock +" -->\nSource : "+source_address+"\n"+"login :\n"+login);
        if (authenticate(login,password, source_address)) {
            System.out.println("Login successful");
            // TODO retourner le message de login avec boolean et list des user connecté
        }
    }
}
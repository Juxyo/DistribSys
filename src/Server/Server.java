package Server;

import java.util.*;

public class Server {


    public Server() {
        userList=new ArrayList<User>();
    }

    private ArrayList<User> userList;

    public String addUser(User user) {
        if(!userList.contains(user)){
            userList.add(user);
            /*
            * TODO : Broadcast new user to userList
             */
        }
        else{
            return("User Already Exists");
        }
        return null;
    }

    public boolean authenticate(String username, String password) {
        for(User u : userList){
            if(u.getUserName().equals(username) && u.getPassword().equals(password)){
                return true;
                // TODO : effectively authenticate
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

}
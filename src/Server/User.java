package Server;

/**
 *
 */
public class User {

    public User(String parUserName,String  parPassword, String parHost) {
        this.password=parPassword;
        this.userName=parUserName;
        if (parHost != ""){
            host=parHost;
        }
    }

    private String userName;
    private String password;
    private String host;

    private int lastResponse=0;


    public boolean compareTo(User user) {
        return user.userName.equals(this.userName);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public void incrementLastResponse() {
        lastResponse++;
    }

    public int getLastResponse() {return lastResponse;}

    public void resetLastResponse(){lastResponse=0;}
}
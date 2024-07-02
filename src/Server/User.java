package Server;

/**
 *
 */
public class User {

    public User(String userName,String  password, int port) {
        this.password=password;
        this.userName=userName;
        this.port=port;
    }

    private String userName;
    private String password;
    private int port;


    public int compareTo(User user) {
        return user.userName.compareTo(this.userName);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }
}
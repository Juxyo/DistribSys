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


    public int compareTo(User user) {
        return user.userName.compareTo(this.userName);
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
}
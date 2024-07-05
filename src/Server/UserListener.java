package Server;

import java.util.ArrayList;
import java.util.Vector;

public interface UserListener {
    public void refreshUserList();
    public Vector<User> getUserList();
}

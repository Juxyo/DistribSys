package Client.Vue;

import javax.swing.*;
import java.util.*;
import Client.Controler.Client;

/**
 * 
 */
public class MainView extends JFrame {

    /**
     * Default constructor
     */
    public MainView(Client cli) {
        setTitle("Chat app");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add(new LoginView((cli)));
        add(new ConversationView(cli,null));
        this.setVisible(true);
    }

    /**
     * 
     */
    public void switchView() {
        // TODO implement here
    }

}
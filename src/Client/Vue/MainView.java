package Client.Vue;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import Client.Controler.Client;

/**
 * 
 */
public class MainView extends JFrame {

    /**
     *
     */
    private MenuView menuView;

    private Client cli;

    private LoginView loginView;

    /**
     *
     */
    private ConversationView convView;

    private JPanel mainPanel;

    /**
     * Default constructor
     */
    public MainView(Client cli,boolean connected) {
        this.cli=cli;
        setTitle("Chat: General");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        if(connected) {
            // Create the menu panel
            menuView = new MenuView(cli);

            // Create the string list panel
            convView = new ConversationView(cli, null);

            mainPanel.setLayout(new BorderLayout());
            // Add the menu panel to the top of the main panel
            mainPanel.add(menuView, BorderLayout.NORTH);

            // Add the string list panel to the center of the main panel
            mainPanel.add(convView, BorderLayout.CENTER);
        }else {
            loginView = new LoginView(cli);
            mainPanel.add(loginView);
        }
        // Add the main panel to the frame
        add(mainPanel);
        this.setVisible(true);
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public ConversationView getConvView() {
        return convView;
    }

    public LoginView getLoginView() {
        return loginView;
    }
}
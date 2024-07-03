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

    /**
     *
     */
    private ConversationView convView;

    /**
     * Default constructor
     */
    public MainView(Client cli) {
        setTitle("Chat: General");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add(new LoginView((cli)));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create the menu panel
        menuView = new MenuView(cli);

        // Create the string list panel
        convView = new ConversationView(cli,null);

        // Add the menu panel to the top of the main panel
        mainPanel.add(menuView, BorderLayout.NORTH);

        // Add the string list panel to the center of the main panel
        mainPanel.add(convView, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);
        this.setVisible(true);
    }

    /**
     * 
     */
    public void switchView() {
        // TODO implement here
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public ConversationView getConvView() {
        return convView;
    }
}
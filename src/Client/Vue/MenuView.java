package Client.Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import Client.Controler.Client;
import Client.Model.Conversation;
import Client.Model.ConversationList;

/**
 * 
 */
public class MenuView extends JPanel {

    private JComboBox combo;

    public MenuView(Client cli) {
        setLayout(new BorderLayout());

        // Create the menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align components to the left

        // Create the dropdown list
        String[] options = {"General"};
        combo = new JComboBox<>(options);
        // Create the validate button
        JButton validateButton = new JButton("Validate");

        // Add components to the menu panel
        menuPanel.add(combo);
        menuPanel.add(validateButton);

        // Add the menu panel to the top of the frame
        add(menuPanel, BorderLayout.NORTH);

        // Add action listener to the button
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cli.open((String) combo.getSelectedItem());
            }
        });
    }

    public void refreshChanelList(ConversationList convsList){
        Vector<Conversation> convs=convsList.getConversations();
        combo.removeAllItems();
        for (Conversation conv:convs) {
            combo.addItem(conv.getName());
        }
    }

}
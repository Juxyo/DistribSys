package Client.Vue;

import Client.Controler.Client;
import Client.Controler.ConversationsListener;
import Client.Model.Conversation;
import Client.Model.ConversationList;
import Client.Model.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

/**
 * 
 */
public class ConversationView extends JPanel implements ConversationsListener {

    private Vector<Conversation> convlist;

    /**
     * Default constructor
     */
    private DefaultListModel<String> listModel;
    private JList<String> stringList;
    private JTextField inputField;
    private JButton addButton;

    private Client cli;

    private String currentConv="General";


    public ConversationView(Client cli,String conv) {
        this.cli=cli;
        convlist=cli.getConvs().getConversations();
        cli.getConvObserver().subscribe(this);
        this.setLayout(new BorderLayout());

        // Create the list model and populate it with initial data
        listModel = new DefaultListModel<>();
        stringList = new JList<>(listModel);
        stringList.setVisibleRowCount(10); // Show 10 items at a time
        JScrollPane scrollPane = new JScrollPane(stringList);

        // Create the input panel with a text field and a button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        inputField = new JTextField();
        addButton = new JButton("Add");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Add components to the main frame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Add action listener to the button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMSG();
            }
        });

        // Allow pressing Enter to add the item
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMSG();
            }
        });
    }

    private void sendMSG() {
        String newItem = inputField.getText().trim();
        if (!newItem.isEmpty()) {
            if (cli.getCurrentConv().equals("General")) cli.sendMessage(newItem, cli.getCurrentConv(),cli.getKnownHosts());
            else{
                cli.sendMessage(newItem, cli.getCurrentConv(),cli.getCurrentConv().split("-")[1]);
            }
            inputField.setText(""); // Clear the input field
        }
        for (Conversation conv:convlist) {
            if(conv.getName().equals(cli.getCurrentConv())){
                conv.addMessage(new Message(newItem,cli.getClock().toString()));
                listModel.addElement(newItem);
                for (Message msg:conv.getMessages()) {

                }
            }
        }
    }
    public void updateConversationsList() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        convlist=cli.getConvs().getConversations();
        refreshMessages();
    }

    private void refreshMessages(){
        listModel.removeAllElements();
        for (Conversation conv:convlist) {
            if(conv.getName().equals(cli.getCurrentConv())){
                for (Message msg:conv.getMessages()) {
                    listModel.addElement(msg.getText());
                }
            }
        }
    }
}
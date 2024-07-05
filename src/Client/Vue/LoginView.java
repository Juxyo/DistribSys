package Client.Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Client.Controler.*;

/**
 * 
 */
public class LoginView extends JPanel{
    private JTextField urlField;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton connectButton;
    private JLabel statusLabel;

    private Client cli;

    public LoginView(Client cli) {
        this.cli=cli;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);

        userField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(userField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        // Connect button
        connectButton = new JButton("Connect");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(connectButton, gbc);

        // Status label
        statusLabel = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(statusLabel, gbc);

        // Action listener for the connect button
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });
    }

    private void connect() {
        String user = userField.getText();
        String password = passwordField.getText();
        cli.sendLoginRequest(user,password);
    }

    public void setStatusLabel(String label) {statusLabel.setText(label);}
}
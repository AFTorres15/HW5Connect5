package cs3331.hw5;

import cs3331.hw4.ConnectFive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: Cesar Valenzuela
 * Date: 7/27/2018
 * Course:
 * Assignment:
 * Instructor:
 * T.A:
 */
public class NetworkGUI extends ConnectFive {


    private JButton onlineButton;
    private JButton networkButton;
    private JButton serverButton;
    private ImageIcon NETWORK_ON = createImageIcon("wifi-connected.png");
    private ImageIcon NETWORK_OFF;
    private static JButton host = new JButton("HOST");
    //private NetworkAdapter network;

    NetworkGUI() {
        super();
    }

    NetworkGUI(int boardSize, char difficulty) {
        super(boardSize, difficulty);
    }

    protected JToolBar toolBar() {
        JToolBar toolBar = super.toolBar();
        //NETWORK_OFF = createImageIcon("wifi-disconnected.png");
        //NETWORK_ON = createImageIcon("wifi-connected.png");

        networkButton = new JButton("Client");
        serverButton = new JButton("Server");
        onlineButton = new JButton( "Play Online");

        //networkButton.addActionListener(this::networkbuttonClicked);
        networkButton.setToolTipText("client");
        networkButton.setFocusPainted(false);

        //serverButton.addActionListener(this::serverButtonClicked);
        serverButton.setToolTipText("server");
        serverButton.setFocusPainted(false);


        toolBar.add(networkButton);
        toolBar.add(serverButton);
        toolBar.add(onlineButton);
        return toolBar;
    }

    protected void joinConfirmBox(){


    }

    protected void maker(){
        JTextArea eventBox;

        eventBox = new JTextArea(10,32);
        eventBox.setEditable(false);
        JPanel button;
        button = new JPanel();
        button.add(eventBox);
        button.setBorder(BorderFactory.createEtchedBorder());

        JPanel player;
        player = makePlayerPanel();

        JPanel peer;
        peer = makePeerPanel();

    }

    protected static JPanel makePlayerPanel(){
        JPanel panel = new JPanel();

        //JLabel hostName = new JLabel("Host name: "); // proff told us to remove it
        //JTextField nameField = new JTextField("localhost");

        JLabel ipNumber = new JLabel("IP number: ");
        JTextField ipField = new JTextField();

        JLabel portNum = new JLabel("Port number: ");
        JTextField portField = new JTextField();


        //host = new JButton("Host");

        //hostName.setBounds(10,10,60,30);
        // nameField.setBounds(70,15,270,20);
        // host.addActionListener( (ActionListener) host );
        panel.setBorder(BorderFactory.createTitledBorder("Player"));
        panel.setLayout(new GridLayout(3,2,0,5));
        //panel.add(hostName);
        //panel.add(nameField);
        panel.add(ipNumber);
        panel.add(ipField);
        panel.add(portNum);
        panel.add(portField);
        panel.add(host);
        panel.setVisible(true);

        return panel;
    }

    public static void createOnlinePanel() {
        JFrame f= new JFrame("Connection");
        JPanel panel=new JPanel();
        panel.setBounds(0,0,260,400);
        // panel.setBackground(Color.gray);
        f.setResizable(false);
        f.add(panel);
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);

        JPanel playerPanel = new JPanel();
        playerPanel = makePlayerPanel();
        panel.add(playerPanel);

        JPanel peerPanel = new JPanel();
        peerPanel = makePeerPanel();
        panel.add(peerPanel);
    }

    protected static JPanel makePeerPanel(){
        JPanel panel = new JPanel();

        JLabel hostName = new JLabel("Host name/IP: ");
        JTextField nameField = new JTextField(10);

        JLabel portNum = new JLabel("Port number: ");
        JTextField portField = new JTextField(10);

        JButton connect = new JButton("connect");

        JButton disconnect = new JButton("disconnect");

        panel.setBorder(BorderFactory.createTitledBorder("Peer"));
        panel.setLayout(new GridLayout(3,2,5,5));
        panel.add(hostName);
        panel.add(nameField);
        panel.add(portNum);
        panel.add(portField);
        panel.add(connect);
        panel.add(disconnect);
        panel.setVisible(true);

        return panel;

    }

    void addHostButtonListener(ActionListener abe) {
        host.addActionListener(abe);
    }

    void addOnlineButtonListener(ActionListener abc){
        onlineButton.addActionListener(abc);
    }

    void addNetworkClientListener(ActionListener actionListener) {
        networkButton.addActionListener(actionListener);
    }

    void addNetworkServerListener(ActionListener actionListener) {
        serverButton.addActionListener(actionListener);
    }

}


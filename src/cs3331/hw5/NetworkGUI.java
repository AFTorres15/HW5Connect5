package cs3331.hw5;

import cs3331.hw4.ConnectFive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


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
    private JButton playWithFriend;// = new JButton("Play new Game With Friend");
    private JButton host = new JButton("HOST");
    private JButton connectButton = new JButton( "connect" );
    private JButton disconnectButton = new JButton( "disconnect" );
    private JTextField portField;
    private JTextField nameField;
    private JTextField portField2;



    NetworkGUI() {
        super();
    }

    NetworkGUI(int boardSize, char difficulty) {
        super(boardSize, difficulty);
    }

    protected JToolBar toolBar() {
        JToolBar toolBar = super.toolBar();
        playWithFriend=new JButton("Play with friend.");
        onlineButton = new JButton(createImageIcon("wifi-red.png"));
        onlineButton.setToolTipText("Play against online player.");
        playWithFriend.setToolTipText("Play a new game with your online friend.");
        toolBar.add(onlineButton);

       // if(NetworkController.isConnected()) {
           // onlineButton = new JButton( createImageIcon( "wifi-green.png" ) );
       // }
       /* else*///  onlineButton = new JButton( createImageIcon( "wifi-red.png" ) );

        onlineButton.setToolTipText("Play against Online Player");
        //playWithFriend.setVisible(false);
        toolBar.add(playWithFriend);

        return toolBar;
    }
    private void rePaintToolBar(){
        playWithFriend.setVisible(true);
        toolBar().repaint();
    }
    public JToolBar getToolbar(){
        return toolBar();
    }

    /** gets the port Number from peer panel
     * converts from string to int
     */
    protected  int getPortField2(){
        int portNum = Integer.parseInt(portField2.getText());
        System.out.println(portNum + " this is portNum 2");
        return portNum;
    }

    protected  String getNameField(){
        String ip = nameField.getText();
        return ip;
    }

    protected  int getPortNumber(){
        int portNum = Integer.parseInt(portField.getText());
        System.out.println(portNum + "this is portNUm");
        return portNum;
    }
    /** This is the online panel
     *  HOLDS the player Panel and the peer panel
     * */

    protected  void maker() {
        JTextArea eventBox;

        eventBox = new JTextArea( 10, 32 );
        eventBox.setEditable( false );
        JPanel button;
        button = new JPanel();
        button.add( eventBox );
        button.setBorder( BorderFactory.createEtchedBorder() );

        JPanel player;
        player = makePlayerPanel();

        JPanel peer;
        peer = makePeerPanel();

    }

    protected  JPanel makePlayerPanel() {
        JPanel panel = new JPanel();

        JLabel ipNumber = new JLabel( "IP number: " );

        JTextField ipField = new JTextField("localhost");
        ipField.setEditable(false);

        JLabel portNum = new JLabel( "Port number: " );
        portField = new JTextField("8000",12);

        panel.setBorder( BorderFactory.createTitledBorder( "Player" ) );
        panel.setLayout( new GridLayout( 4, 2, 5, 5 ) );
        panel.add( ipNumber );
        panel.add( ipField );
        panel.add( portNum );
        panel.add( portField );
        panel.add( host );
        panel.setVisible( true );

        return panel;
    }

     void createOnlinePanel() {
        JFrame f = new JFrame( "Connection" );
        JPanel panel = new JPanel();
        panel.setSize( 400,400 );
        // panel.setBackground(Color.blue);
        f.setResizable( false );
        f.add( panel );
        f.setSize( 400, 400 );
        f.setLayout( null );
        f.setVisible( true );

        JPanel playerPanel = makePlayerPanel();
        panel.add( playerPanel );

        JPanel peerPanel = makePeerPanel();
        panel.add( peerPanel );
    }

    protected  JPanel makePeerPanel() {
        JPanel panel = new JPanel();
        JLabel hostName = new JLabel( "Host name/IP: " );

        nameField = new JTextField( "127.0.0.1",12 );
        JLabel portNum = new JLabel( "Port number: " );
        portField2 = new JTextField( "8000",12 );

        panel.setBorder( BorderFactory.createTitledBorder( "Peer" ) );
        panel.setLayout( new GridLayout( 3, 2, 5, 5 ) );
        panel.add( hostName );
        panel.add( nameField );
        panel.add( portNum );
        panel.add( portField2 );
        panel.add( connectButton );
        panel.add( disconnectButton );
        panel.setVisible( true );

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

    void addConnectListener(ActionListener actionListener) {
        connectButton.addActionListener( actionListener );
    }

    void addDisconnectListener(ActionListener actionListener) {
        disconnectButton.addActionListener( actionListener );
    }
    void addPlayWithFriendListener(ActionListener actionListener){

        playWithFriend.addActionListener(actionListener);
    }
    JButton getPlayWithFriend() {
        return getPlayWithFriend();
    }
    void setVisiblePlayWithFriendVisibility(boolean visibility){
        playWithFriend.setVisible(visibility);
        rePaintToolBar();
    }

}
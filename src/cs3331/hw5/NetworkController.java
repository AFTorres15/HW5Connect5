package cs3331.hw5;

import cs3331.hw4.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class NetworkController extends Controller implements NetworkAdapter.MessageListener {

    public NetworkGUI view;
    private Board model;
    private NetworkAdapter network;
    private Sound sound=new Sound();

    private NetworkController(Board model, NetworkGUI gui) {

        super(model, gui);
        view = gui;
        this.model = model;

        view.addOnlineButtonListener(new OnlineListener());
        view.addMouseListener(new ClickAdapter());
        view.addConnectListener(new ClientListener());
        view.addHostButtonListener(new ServerListener());
        view.addPlayWithFriendListener(new PlayWithFriendListener());
        view.addDisconnectListener(new Disconnectlistener());
    }



    @Override
    public void messageReceived(NetworkAdapter.MessageType type, int x, int y, int z, int[] others) {

        switch (type) {
            case JOIN:
                int n = JOptionPane.showConfirmDialog(null, "Join client?",null,JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    network.writeJoinAck(15);
                    sound.playConnectedSound();
                } else {
                    network.writeJoinAck();
                }
                System.out.println("Join");
                justConnected();
                break;
            case JOIN_ACK:
                System.out.println("Join ack");
                if(x==0){
                if(popUpAns("Hey someone has joined the game") == 0){
                    System.out.println("Yes, game joined");
                } else{
                    System.out.println("Game declined");
                }
                }else{
                    popUpAns("Your Connection was rejected");
                }
                justConnected();
                break;
            case NEW:
                System.out.println("NEW");
                writeNewPopUP();

                break;
            case NEW_ACK:
                System.out.println("NEW ACK");
                if(x==1)
                setNewBoard();
                else
                    popUpAns("New Game Was Denied");

                break;
            case FILL:
                System.out.println("FILL CASE!");
                try {
                    //network.writeFillAck(x, y, 0);
                    network.writeFillAck(x,y,z);
                    model.addDisc(x,y,z);
                }catch (InValidDiskPositionException ex){
                    System.out.println("Wrong placement");
                }

                break;
            case FILL_ACK:
               System.out.println("FILL ACK");
                try{
                    System.out.println("Fill Ack x y z");
                    model.addDisc(x,y,z);
                }catch(InValidDiskPositionException ex){
                    System.out.println("Wrong Placement");
            }
                break;
            case QUIT:
                System.out.println("Quitting : One moment");
                network.close();
                break;
            case CLOSE:
                System.out.println("Connection Severed.");
                //disconnectListener();
                network.writeQuit();
                break;
            case UNKNOWN:
                System.out.println("unknown");
                break;
        }
        try {
            System.out.println(type);
            System.out.println(x);
        }catch (NullPointerException e){
            System.out.println(" something in the println went wrong");
        }
    }

    private void onlineGame(int x, int y){

    }

    private int popUpAns(){
        int reponse = JOptionPane.showConfirmDialog(null, "GAME VERIFICATION");
        if(reponse == JOptionPane.YES_OPTION){
            return 0;
        }else {
            return 1;
        }
    }

    private int popUpAns(String message){
        int reponse = JOptionPane.showConfirmDialog(null,message, message,JOptionPane.YES_NO_OPTION);
        if(reponse == JOptionPane.YES_OPTION){
            return 0;
        }else {
            return 1;
        }
    }

    private void writeNewPopUP() {
        int respon = popUpAns("Hey Someone wants a new board");
        if (respon == JOptionPane.YES_OPTION) {
            view.setVisiblePlayWithFriendVisibility(true);
            network.writeNewAck(true);
            //sizeRequest3("Hey Let's Set Up A New Game");//change to jusT A DIALUGPE
        }else {
            network.writeNewAck(false);
        }
    }

    private void pairAServer(Socket socket) {

        network = new NetworkAdapter(socket);
        network.setMessageListener(this);
        //network.writeJoinAck();
        network.receiveMessages();
        network.writeQuit();

    }

    private void pairAsClient(Socket socket) {

        network = new NetworkAdapter(socket);
        network.setMessageListener(this);

        network.writeJoin();
        network.receiveMessagesAsync();
    }

    protected  void sizeRequest3(String text){
        Object[] yesOrNo = {"Yes", "No"};

        sound.playAlertSound();

        int confirm = JOptionPane.showOptionDialog(view,text, "confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, yesOrNo, yesOrNo[1]);
        if (confirm == JOptionPane.YES_OPTION) {
           network.writeNew(15);//here size doesn't matter
        }
    }
    void setNewBoard(){
        Object[] options = {"15x15", "9x9"};
        int n = JOptionPane.showOptionDialog(view,
                "pick a size", "New Game",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null,options, options[1]);
        if (n == JOptionPane.YES_OPTION) {
            System.out.println("new board 15X15");
            super.setNewBoard(15,view.getBoardPanel().getP2().playerType,view.getBoardPanel().getColorP1(),view.getBoardPanel().getColorP2());
        }else{
            System.out.println("New board 9X9");
            super.setNewBoard(9,view.getBoardPanel().getP2().playerType,view.getBoardPanel().getColorP1(),view.getBoardPanel().getColorP2());
        }
        //HERE MAYBE SEND SOMETHING?

    }

    /*  private void disconnectListener() {
          network.close();
          isNetwork();

      }*/
    private boolean isNetwork() {
        if (network == null) {
            return false;
        }
        return true;
    }

    public void justConnected(){
        view.getBoardPanel().setVisible(false);
        view.getMessage().setText("Hey start a new Game");
        view.getBoardPanel().repaint();
    }

    public static void main(String[] args) {
        Board model = new Board(15);
        NetworkGUI view = new NetworkGUI();
        new NetworkController(model, view);
    }

    class ClickAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            //[] dummy = new int[1];
            //super.mousePressed(e);
            int x = view.locateXY(e.getX());
            int y = view.locateXY(e.getY());
            System.out.println("network connected: " + isNetwork());

            if (network != null) {
                network.writeFill(x, y,1);
            }
        }
    }

    class PlayListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sizeRequest3("Start new game?");
        }
    }

    // creates server
    class ServerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(() -> {
                    try {
                    System.out.println("Server Starting");
                    ServerSocket serverSocket = new ServerSocket(view.getPortNumber());
                    Socket incoming = serverSocket.accept();
                    pairAServer(incoming);
                } catch (Exception ex) {
                    System.out.println("SERVER FAILURE");
                }
            }).start();
        }
    }

    // client sends request
    class ClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(() -> {
                System.out.println("client starting");
                try {
                    Socket socket = new Socket();

                    //socket.connect(new InetSocketAddress("172.19.164.80", 8000), 5000);
                    socket.connect(new InetSocketAddress(view.getNameField(), view.getPortField2()), 5000);

                    // Brians 172.19.160.100
                    // ANDREA 172.19.164.80
                    // mine: 172.19.164.228


                    //172.19.160.82

                    pairAsClient(socket);
                    justConnected();
                } catch (Exception e1) {
                    System.out.println("CLIENT FAILURE");
                }
            }).start();
        }
    }

    class PlayWithFriendListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            //if not connected
            new Thread(() -> {
                try {
                    System.out.println("Hey I want to start a new game");
                    //view.getBoardPanel().setVisible(true);
                    sizeRequest3("Create a new game?");
                }catch (NullPointerException ex){
                    System.out.println("PlaywithFriendsListener error");
                }
            }).start();
        }
    }

    class OnlineListener implements ActionListener {
        // ideas
        @Override
        public void actionPerformed(ActionEvent e) {
            view.createOnlinePanel();
        }
    }
    class Disconnectlistener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            network.close();

        }
    }
}
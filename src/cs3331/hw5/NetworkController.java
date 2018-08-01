package cs3331.hw5;

import cs3331.hw4.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
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
    private static final int[] EMPTY_INT_ARRAY = new int[0];

    private NetworkController(Board model, NetworkGUI gui) {

        super(model, gui);
        view = gui;
        this.model = model;

        view.addOnlineButtonListener(new OnlineListener());
        view.addNetworkClientListener(new ClientListener());
        //view.addNetworkServerListener(new ServerListener());
        view.addMouseListener(new ClickAdapter());
        view.addHostButtonListener(new ServerListener());
    }

    private void ackDialog(String text) {
        Object[] options = {"Yes", "No"};
        int confirm = JOptionPane.showOptionDialog(view, text, "r u sure?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if(confirm == JOptionPane.YES_OPTION){
            int n = JOptionPane.showOptionDialog(view,
                    "pick a size", "New Game",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[1]);
            if(n == JOptionPane.YES_OPTION){
                System.out.println("Yes was selected");
            } else {
                network.writeJoinAck();
            }
        }

    }


    class ClickAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            int[] dummy = new int[1];
            super.mousePressed(e);
            int x = view.locateXY(e.getX());
            int y = view.locateXY(e.getY());
            System.out.println("network connected: " + isNetwork());
            if(network!=null){
                network.writeFill(x,y);
            }
        }
    }

    @Override
    public void messageReceived(NetworkAdapter.MessageType type, int x, int y, int z, int[] others) {
        switch (type) {
            case JOIN://client
                System.out.println("Want to Join ClienT??");

                break;
            case JOIN_ACK://server
                // JOptionPane -> Want to join? Yes, No
                // yes return 1
                // no return 0
                System.out.println("JOIN ACKNOWLEDGE????");
                JOptionPane.showConfirmDialog(null,"OK");

                network.writeJoinAck(model.size(),model.sendBoard());
                break;
            case NEW:

                break;
            case NEW_ACK:

                break;
            case FILL:
                try {

                    model.addDisc(x, y, 1);
                } catch (InValidDiskPositionException e) {
                    System.out.println("oops");
                }
                System.out.println("FILL");
                //view.fillDisc(x, y);
                break;
            case FILL_ACK:

                break;
            case QUIT:

                break;
            case CLOSE:

                break;
            case UNKNOWN:
                System.out.println("unknown");
                break;
        }
    }

    class ServerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(() -> {
                try {
                    System.out.println("Server Starting");
                    ServerSocket servSocket = new ServerSocket(8000);
                    Socket incoming = servSocket.accept();
                    pairAServer(incoming);
                } catch (Exception ex) {
                    System.out.println("SERVER FAILURE");
                }
            }).start();
        }

    }

    private void pairAServer(Socket socket) {

        network = new NetworkAdapter(socket);
        network.setMessageListener(this);
        network.writeJoinAck();
        network.receiveMessages();

    }

    class ClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(() -> {
                System.out.println("client starting");
                try {
                    Socket socket = new Socket();

                    socket.connect(new InetSocketAddress("127.0.0.1", 8000), 5000);

                    // Brians 172.19.160.100
                    // ANDREA 172.19.164.38
                    //socket.connect(new InetSocketAddress("172.19.64.38", 8000), 5000);

                    pairAsClient(socket);

                } catch (Exception e1) {
                    System.out.println("CLIENT FAILURE");
                }
            }).start();
        }
    }

    private void pairAsClient(Socket socket) {

        network = new NetworkAdapter(socket);
        network.setMessageListener(this);

        network.writeJoin();
        network.receiveMessagesAsync();
    }

    private boolean isNetwork() {
        if (network == null) {
            return false;
        }
        return true;
    }

    static class OnlineListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            NetworkGUI.createOnlinePanel();
        }
    }

    public static void main(String[] args) {
        Board model = new Board(15);
        NetworkGUI view = new NetworkGUI();
        new NetworkController(model, view);
    }
}

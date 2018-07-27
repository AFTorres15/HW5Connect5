package cs3331.hw5;

import cs3331.hw4.ConnectFive;
import cs3331.hw4.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetworkGUI extends ConnectFive {
    private ImageIcon NETWORK_OFF;
    private ImageIcon NETWORK_ON=createImageIcon("connected.png");
    private JButton networkButton;

    @Override
    protected JToolBar toolBar() {
        JToolBar toolBar = super.toolBar();


            NETWORK_OFF = createImageIcon("noConnection.png");
        if (NETWORK_OFF==null){
            System.out.println("Hi file not found fix it quick");
        }
        networkButton = new JButton(NETWORK_OFF);
        networkButton.addActionListener(this::networkButtonClicked);
        networkButton.setToolTipText("Pair");
        networkButton.setFocusPainted(false);
        toolBar.add(networkButton, toolBar.getComponentCount() );
        return toolBar;
    }
    private void networkButtonClicked(ActionEvent e) {
        new Thread(()-> {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress("127.0.0.1", 8000), 5000); // timeout in millis
                pairAsClient(socket);
            } catch (Exception ee) { }
        }).start();
    }
    private void pairAsClient(Socket socket) {
        network = new NetworkAdapter(socket);
        network.setMessageListener((NetworkAdapter.MessageListener) this); // see the next slide
        network.writeJoin();
        network.receiveMessages(); // loop till disconnected
    }

    private NetworkAdapter network;
    NetworkGUI() {super();}

    NetworkGUI(int boardSize,char difficulty){
        new ConnectFive(boardSize,difficulty);
    }
}

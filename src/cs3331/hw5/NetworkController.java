package cs3331.hw5;

import cs3331.hw4.Board;
import cs3331.hw4.Controller;
import cs3331.hw4.Sound;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkController extends Controller  implements NetworkAdapter.MessageListener {
private static NetworkGUI gui;
private static Board model;
//    protected void fillDisk(int x, int y) {
//        super.fillNumber(x, y);
//        if (network != null) { network.writeFill(x, y); }
//    }
//
//    /** Called when a message is received from the peer. */
//    public void messageReceived(NetworkAdapter.MessageType type, int x, int y, int z, int[] others) {
//        switch (type) {
//            case FILL:
//                // peer filled the square (x, y) with the number z
//                super.fillNumber(x, y);
//                break;
//     // …
//        }
//    }
//

    private NetworkController(Board model, NetworkGUI gui) {
        super(model, gui);
        this.gui=gui;
        System.out.println("Hi");
    }

    @Override
    public void messageReceived(NetworkAdapter.MessageType type, int x, int y, int z, int[] others) {

    }
    public static void main(String[] args){
        Board modle=new Board(15);
        NetworkGUI view=new NetworkGUI();
        new NetworkController(modle,view);
    }
    //@Override
    protected static void sizerequest(String text){
        Object[] options = {"15x15", "9x9"};
        Object[] yesOrNo = {"Yes", "No"};
        Sound.playAlertSound();

        int confirm = JOptionPane.showOptionDialog(gui,text, "confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, yesOrNo, yesOrNo[1]);

        if (confirm == JOptionPane.YES_OPTION) {
            int n = JOptionPane.showOptionDialog(gui,
                    "pick a size", "New Game",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[1]);
            // 15 x 15
            if (n == JOptionPane.YES_OPTION) {
                gui.dispose();
                new NetworkController(new Board(15), new NetworkGUI(15,'j'));
            }else{
                gui.dispose();
                new NetworkController(new Board(9), new NetworkGUI(9,'j'));
            }
        }
    }
    static class NetworkListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        }
    }
}

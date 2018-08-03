package cs3331.hw4;


//import cs3331.hw4.Board;
//import cs3331.hw4.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;


import java.awt.event.*;
import java.util.Random;

import static java.awt.event.InputEvent.*;


/**
 * Frame class for the graphical user interface of connect five.
 * Controller for handling events that affect the model and the view.
 *
 * @author Edgar Padilla
 */
public class ConnectFive extends JFrame {

    /**
     * directory where images are stored
     */
    private final   String IMAGE_DIR = "/image/";

    private JLabel message;
    private BoardPanel boardPanel;
    private int squareSize = 15;

    private JButton playButton;
    private JButton paintButton;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton p1 = new JButton("Player 1");
    private JButton p2 = new JButton("Player 2");
    private JFrame frametmp;
    private JPanel popup;

    //JMenu
    private JMenuItem changeSize;
    private JMenuItem easyDifficulty;
    private JMenuItem mediumDifficulty;
    private JMenuItem changeColors;
    private JMenuItem online;


    // player1 is true, player2 is false
    private boolean turn = true;


    /**
     * Constructor that initializes and adds all the components of the frame
     * including anonymous classes for the handlers.
     */
    protected ConnectFive() {
        setTitle("Connect Five");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        //here we need to change it to be able to change to p2 type
        createGUI(15,'j');

        setVisible(true);
        pack();
    }

    public ConnectFive(int size, char p2Type) {
        super();
        createGUI(size,p2Type);

        squareSize = size;
        setVisible(true);
        pack();
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    void setSquareSize(int size){
        this.squareSize=size;
    }


    /**
     * creates the buttons, and adds the other panels to create
     * the overall GUI of the connect 5 game.
     *
     * @param size the size of the board to be played
     */
    private void createGUI(int size, char p2Type) {
        JPanel boardSizePanel = new JPanel(new FlowLayout());

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(boardSizePanel, BorderLayout.NORTH);
        jPanel.add(boardPan(size,p2Type), BorderLayout.CENTER);
        jPanel.add(statusPanel(), BorderLayout.SOUTH);

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(menuBar(), BorderLayout.NORTH);
        jPanel1.add(toolBar(), BorderLayout.SOUTH);
        getContentPane().add(jPanel, BorderLayout.CENTER);
        getContentPane().add(jPanel1, BorderLayout.NORTH);

    }

    protected JToolBar toolBar() {JToolBar toolBar = new JToolBar("Connect5");

        playButton = new JButton(createImageIcon("play30.png"));
        paintButton = new JButton(createImageIcon("paint30.png"));
        easyButton = new JButton(createImageIcon("easy30.png"));
        mediumButton = new JButton(createImageIcon("medium30.png"));

        playButton.setToolTipText("Play a new game.");
        playButton.setFocusPainted(false);
        paintButton.setToolTipText("Customize Disc Colors");
        easyButton.setToolTipText("Play against Easy Computer");
        mediumButton.setToolTipText("Play against Medium Computer");


        toolBar.add(playButton);
        toolBar.add(paintButton);
        toolBar.add(easyButton);
        toolBar.add(mediumButton);
        return toolBar;

    }

    void addEasyListener(ActionListener e) {
        easyButton.addActionListener(e);
        easyDifficulty.addActionListener(e);
    }

    void addMediumListener(ActionListener e){
        mediumButton.addActionListener(e);
        mediumDifficulty.addActionListener(e);
    }

    void addPlayListener(ActionListener e){
        playButton.addActionListener(e);
        changeSize.addActionListener(e);
    }

    void addPaintListener(ActionListener e) {
        paintButton.addActionListener(e);
        changeColors.addActionListener(e);
    }

    void addPaintHelperListener(ActionListener actionL) {
        p1.addActionListener(actionL);
    }

    void addPaintHelper2Listener(ActionListener aEvent) {
        p2.addActionListener(aEvent);
    }

    public void addMouseListener(MouseListener e) {
        boardPanel.addMouseListener(e);

    }

    private JMenuBar menuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);
        menu.getAccessibleContext().setAccessibleDescription("Game Menu");
        menuBar.add(menu);

        //Create Menu Items & set Icons
        changeSize = new JMenuItem("Change Size");
        changeSize.setIcon(createImageIcon("play.png"));
         easyDifficulty = new JMenuItem("Play V.S COM (EASY)");
        easyDifficulty.setIcon(createImageIcon("easy.png"));
        mediumDifficulty = new JMenuItem("Play V.S COM (MEDIUM)");
        mediumDifficulty.setIcon(createImageIcon("medium.png"));
        changeColors = new JMenuItem("Change Colors");
        changeColors.setIcon(createImageIcon("paint.png"));
        online = new JMenuItem("Play Online");
        online.setIcon(createImageIcon("wifi-green.png"));

        // set keyStrokes
        changeSize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                ActionEvent.ALT_MASK));
        easyDifficulty.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                ActionEvent.ALT_MASK));
        mediumDifficulty.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
                ActionEvent.ALT_MASK));
        changeColors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                ActionEvent.ALT_MASK));
        online.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                ActionEvent.ALT_MASK));

        changeSize.getAccessibleContext().setAccessibleDescription("Play new game");
        //Controller controller=new Controller();
        // listeners
//        changeSize.addActionListener(new Controller.PlayListener());
//        easyDifficulty.addActionListener(new Controller.EasyListener());
//        mediumDifficulty.addActionListener(new Controller.MediumListener());
//        ChageColors.addActionListener(new Controller.PaintListener());

        // add to mennu
        menu.add(changeSize);
        menu.add(easyDifficulty);
        menu.add(mediumDifficulty);
        menu.add(changeColors);
        menu.add(online);

        return menuBar;
    }


    /**
     * creates the board panel that displays the current game
     *
     * @param size this is the size or the board
     * @return a panel that can be added to the window
     */
    private BoardPanel boardPan(int size, char p2Type) {

        boardPanel = new BoardPanel(new Board(size),p2Type);
        boardPanel.setPreferredSize(new Dimension(725, 725));
        return boardPanel;
    }

    /**
     * Creates a panel that displays a text status about the game
     *
     * @return JPanel to be added to a window
     */
    private JPanel statusPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.DARK_GRAY);
        statusPanel.setPreferredSize(new Dimension(650, 50));
        message = new JLabel("Welcome to Connect Five");
        message.setForeground(Color.WHITE);
        message.setFont(new Font(message.getName(), Font.BOLD, 26));
        statusPanel.add(message);

        return statusPanel;
    }

    void colorChooser() {

        //JDialog popUp=new JDialog(new JFrame(),"Testing","this is an example");
        popup = new JPanel();
        //popup.setLayout(new BoxLayout(popup,BoxLayout.Y_AXIS));
        frametmp = new JFrame("Color chooser");
        JLabel text = new JLabel("Please Select Which Player to Chane tile");

        p1.setBackground(boardPanel.getColorP1());
        p2.setBackground(boardPanel.getColorP2());

        popup.add(text);
        popup.add(p1);
        popup.add(p2);
        frametmp.add(popup);
        frametmp.setSize(250, 150);
        frametmp.setVisible(true);
        frametmp.setResizable(true);
        frametmp.repaint();
    }

    void colorChooserHelper(char player) {

        Color color = JColorChooser.showDialog(this, "pick", Color.CYAN);
        if (color == null)
            randomColor();
        else {
            if (player == '1')
                boardPanel.setColorP1(color);
            if (player == '2')
                boardPanel.setColorP2(color);
        }
        boardPanel.repaint();
        frametmp.dispose();
    }



    /**
     * takes the pixels in the window and divides it by board size
     * to return the coordinate of each square in the board grid
     *
     * @param x sdfgh
     * @return int coordinate of the square that was clicked on the board
     */
    public int locateXY(int x) {
        int pxlsize = 675;
        int gridSize = squareSize;
        int distance = pxlsize / gridSize;
        if (x > 25) {
            x = x - 25; // since we start at 25 we remove 25 in the calculations
        }
        int result = Math.round(x / distance);
        return result + 1;
    }

    protected ImageIcon createImageIcon(String filename) {
        URL imageURL = getClass().getResource(IMAGE_DIR + filename);
        if (imageURL != null) {
            return new ImageIcon(imageURL);
        }
        return null;
    }

    public JLabel getMessage() {
        return message;
    }

    boolean isTurn() {
        return turn;
    }

    void setTurn(boolean turn) {
        this.turn = turn;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }
    private Color randomColor()
    {
        Random random=new Random(); // Probably really put this somewhere where it gets executed only once
        int red=random.nextInt(256);
        int green=random.nextInt(256);
        int blue=random.nextInt(256);
        return new Color(red, green, blue);
    }

}
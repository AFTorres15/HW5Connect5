package cs3331.hw4;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Class that acts as the Panel to display the model (board)
 * as a grid of 15 by 15 or 9 by 9.
 *
 * @author Edgar Padilla
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel {

    /**
     * Board model.
     */
    private Board board;
    private int grid; // size of grid

    private Player p1;
    private Player p2;
    //private Color p1.;
    //private Color colorP2;

    /**
     * Creates an instance of this panel for the discs board.
     */
    public BoardPanel(Board board) {
        super(true);
        p1=new Human(1,'1');
        p2=new Human(2,'2');

        this.board = board;
        grid = this.board.size();
        setOpaque(true);
        p1.setTileColor(Color.RED);
        p2.setTileColor(Color.BLACK);
    }


    BoardPanel(Board board, char p2Type){
        super(true);
        p1=new Human(1,'1');
        p1.setTileColor(Color.RED);
        this.board = board;
        grid = this.board.size();
        setOpaque(true);
        setP2(p2Type);
        p2.setTileColor(Color.BLACK);

    }

    public int setSize(int grid) { // set size 9 or 15
        return this.grid = grid;
    }

    /**
     * Draws the discs board by calling the paint method.
     */
    public void drawBoard() {
        repaint();
    }

    public Color getColorP1() {
        return p1.getTileColor();
    }

    void setColorP1(Color color) {
        p1.setTileColor(color);
    }

    public Color getColorP2() {
        return p2.getTileColor();
    }

    void setColorP2(Color colorP2) {
        p2.setTileColor(colorP2);
    }

    Board getBoard() {
        return board;
    }


    /**
     * Paints the state of the board along with the discs placed
     *
     * @param g g is graphics
     */
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g.drawRect(0,0,this.getWidth(),this.getHeight());
        g.setColor(Color.WHITE);
        //Image img=new ImageIcon("/image/boardBackground.png").getImage();
        //g.drawImage(img,0,0,null);
        g.fillRect(0,0,this.getWidth(),this.getHeight());

        g.setColor(Color.DARK_GRAY);
        int size = 675;
        int n = size / grid;
        for (int i = 0; i <= 15; i++) {
            g.drawLine(25 + i * n, 25, 25 + i * n, 700);
            g.drawLine(25, 25 + i * n, 700, 25 + i * n);
        }

        int pxlTotal = 675;
        int temp = pxlTotal / grid;
        int discPxl;
        if (grid == 9) {
            discPxl = 75;
        } else {
            discPxl = 45;
        }
        Graphics2D g22d = (Graphics2D) g;
        for (int i = 0; i < board.size(); i++) {

            for (int j = 0; j < board.size(); j++) {

                if (board.getTiles(i, j) != null && board.getTiles(i, j).getPlayer() == 1) {
                    Ellipse2D.Double circle = new Ellipse2D.Double(i * temp + 25, j * temp + 25, discPxl, discPxl);
                    g22d.setPaint(p1.getTileColor());
                    g22d.fill(circle);
                } else if (board.getTiles(i, j) != null && board.getTiles(i, j).getPlayer() == 2) {
                    Ellipse2D.Double circle = new Ellipse2D.Double(i * temp + 25, j * temp + 25, discPxl, discPxl);
                    g22d.setPaint(p2.getTileColor());
                    g22d.fill(circle);
                }
            }
        }
    }
    public void setP2(char p2Type){
        if(p2Type=='e') {
            p2 = null;
            p2 = new EasyCompAI(2, '2');
        }else if(p2Type=='m')
            p2=new MedCompAI(2,'2');
        else{
            p2=new Human(2,'2');
        }
    }
    public Player getP2(){return p2;}
    public Player getP1(){return p1;}
    void setBoard(Board board){
        this.board=board;
    }
    public void setGrid(int g){this.grid=g;}

}
package po.simulator;

import javax.swing.*;
import java.awt.*;

public class DrawRect extends JPanel {
    private final int RECT_X;
    private final int RECT_Y;
    private final int RECT_WIDTH;
    private final int RECT_HEIGHT;
    private Color[][] RECT_COL;

    public DrawRect(int X, int Y, int RECT_WIDTH, int RECT_HEIGHT, Organizm[][] plansza) {
        this.RECT_X = X;
        this.RECT_Y = Y;
        this.RECT_WIDTH = RECT_WIDTH;
        this.RECT_HEIGHT = RECT_HEIGHT;
        RECT_COL = new Color[X][Y];
        for(int i = 0; i < Y; i ++){
            for(int j = 0; j < X; j++){
                RECT_COL[j][i] = plansza[j][i] == null ? Color.white : plansza[j][i].rysowanie();
            }
        }
    }

    @Override
    public void paintComponent (Graphics g){
        //super.paintComponent(g);
        for(int i = 0; i < RECT_X; i++) {
            for (int j = 0; j < RECT_Y; j++) {
                g.setColor(RECT_COL[j][i]);
                g.fillRect(i * RECT_WIDTH, j * RECT_HEIGHT, RECT_WIDTH, RECT_HEIGHT);
            }
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
    }
}

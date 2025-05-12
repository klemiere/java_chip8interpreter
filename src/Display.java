import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {

    public static final int HEIGHT = 64;
    public static final int WIDTH = 32;
    public static final int SCALE = 10; //Scale multiplier for the pixels

    private boolean display[][] = new boolean[HEIGHT][WIDTH];

    Display(){
        this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }

    public void clear(){
        display = new boolean[HEIGHT][WIDTH];
    }
}

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {

    public static final int HEIGHT = 64;
    public static final int WIDTH = 32;
    public static final int SCALE = 10; //Scale multiplier for the pixels

    private boolean display[][] = new boolean[WIDTH][HEIGHT];

    Display(){
        this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }

    public void clear(){
        display = new boolean[WIDTH][HEIGHT];
    }

    public boolean flipPixel(int x, int y){
        x = x % WIDTH;
        y = y % HEIGHT;
        boolean current = display[x][y];
        display[x][y] = !current;
        return current;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // clear background

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (display[x][y]) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
                }
                // else: skip or paint background (already cleared)
            }
        }
    }
}

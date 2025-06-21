import javax.swing.*;

public class Emuframe extends JFrame {
    Emuframe(Display display, Keyboard input){
        this.add(display);
        this.setTitle("Chip8");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.addKeyListener(input);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
}

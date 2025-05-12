import javax.swing.*;

public class Emuframe extends JFrame {
    Emuframe(){
        this.add(new Display());
        this.setTitle("Chip8");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

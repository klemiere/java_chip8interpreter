import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class Keyboard implements KeyListener {
    private final boolean[] keys = new boolean[16];

    private final Map<Integer, Integer> keyMap = new HashMap<>();

    public Keyboard() {
        // KeyEvent.VK_* → CHIP-8 key mapping for qwerty
        keyMap.put(KeyEvent.VK_1, 0x1);
        keyMap.put(KeyEvent.VK_2, 0x2);
        keyMap.put(KeyEvent.VK_3, 0x3);
        keyMap.put(KeyEvent.VK_4, 0xC);

        keyMap.put(KeyEvent.VK_Q, 0x4);
        keyMap.put(KeyEvent.VK_W, 0x5);
        keyMap.put(KeyEvent.VK_E, 0x6);
        keyMap.put(KeyEvent.VK_R, 0xD);

        keyMap.put(KeyEvent.VK_A, 0x7);
        keyMap.put(KeyEvent.VK_S, 0x8);
        keyMap.put(KeyEvent.VK_D, 0x9);
        keyMap.put(KeyEvent.VK_F, 0xE);

        keyMap.put(KeyEvent.VK_Z, 0xA);
        keyMap.put(KeyEvent.VK_X, 0x0);
        keyMap.put(KeyEvent.VK_C, 0xB);
        keyMap.put(KeyEvent.VK_V, 0xF);
    }

    public boolean isKeyPressed(int chip8Key){
        if (chip8Key < 0 || chip8Key > 15) {
            System.err.println("Warning: Invalid key index: " + chip8Key);
            return false;
        }
        return keys[chip8Key];
    }

    @Override
    public void keyPressed(KeyEvent e){
        Integer chip8Key = keyMap.get(e.getKeyCode());
        if (chip8Key != null){
            keys[chip8Key] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Integer chip8Key = keyMap.get(e.getKeyCode());
        if (chip8Key != null) {
            keys[chip8Key] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Useless here
    }
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Display display = new Display();
        Memory memory = new Memory("test_roms/1-chip8-logo.ch8");
        CPU cpu = new CPU(display, memory);

        new Emuframe(display);
        cpu.run();
        }


    }
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Display display = new Display();
        Keyboard keyboard = new Keyboard();
        Memory memory = new Memory("test_roms/5-quirks.ch8");
        CPU cpu = new CPU(display, keyboard, memory);

        new Emuframe(display, keyboard);
        cpu.run();
        }


    }
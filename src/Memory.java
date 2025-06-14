import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Memory {
    /*chip8 can access up to 4kb of ram, from locations 0x000 to 0xfff, original interpreter located at 0x000 to 0x1ff,
    do not use.
    roms typically start at 0x200, 0x600 is also possible
     */
    private final int[] ram = new int[4096];
    String romPath;

    Memory(String romPath){
        this.romPath = romPath;
        loadRom(romPath);
    }

    public void loadRom(String romPath){
        try {
            InputStream inputStream = new FileInputStream(romPath);
            int byteCount = 512; //assume the rom starts at 0x200
            int nextByte;
            while((nextByte = inputStream.read()) != -1){
                write(byteCount, nextByte);
                byteCount++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public int[] getRam(){
        return ram;
    }

    public int read(int address){
        return ram[address];
    }

    public void write(int address, int value){
        ram[address] = value;
    }
}

import java.util.Timer;

public class CPU {

    private static final double CYCLE = 1000.0/60; //length of a cycle in millis
    private int[] V;// General purpose registers
    private int[] stack;
    private int SP; // Stack Pointer
    private int I, DT, ST; // Index, Delay Timer and Sound Timer registers
    private int PC; // Program Counter

    private Timer timer;
    long lastCycleTime = System.currentTimeMillis();

    private final Display display;
    private final Memory memory;


    CPU(Display display, Memory memory){
        int cycleCount = 0;
        int byteCount = 512;

        this.display = display;
        this.memory = memory;
        this.timer = new Timer();
        this.PC = 0x200;
        this.V = new int[16];
        this.stack = new int[16];
        this.SP = -1;

    }

    public void run(){
        int[] ram = memory.getRam();
        while (true){
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastCycleTime;

            if (elapsedTime >= CYCLE){
                int highByte = ram[PC] & 0xFF;
                int lowByte = ram[PC + 1] & 0xFF;
                int opcode = (highByte << 8) | lowByte;
                instruction(opcode);
                PC += 2;
                lastCycleTime = currentTime;
            }
        }
    }

    public void instruction(int instruction){
        int opcode = (instruction & 0xF000) >> 12;

        switch (opcode){
            case 0x0:
                if((instruction & 0x00FF) == 0xE0) clearScreen(); //00E0
                else returnFromSubroutine(); //00EE
                break;
            case 0x1: //1nnn
                jumpToAddress(instruction & 0x0FFF);
                break;
            case 0x2: //2nnn
                callSubroutineAtAddress(instruction & 0x0FFF);
                break;
            case 0x3: //3xkk
                skipIfEqualImmediate((instruction & 0x0F00) >> 8, (instruction & 0x00FF));
                break;
            case 0x4: //4xkk
                skipIfNotEqualImmediate((instruction & 0x0F00) >> 8, (instruction & 0x00FF));
                break;
            case 0x5: //5xy0
                skipIfEqualRegister((instruction & 0x0F00) >> 8, (instruction & 0x00F0) >> 4);
                break;
            case 0x6: //6xkk
                setValueInRegister((instruction & 0x0F00) >> 8, (instruction & 0x00FF));
                break;
            case 0x7: //7xkk
                addToValueInRegister((instruction & 0x0F00) >> 8, (instruction & 0x00FF));
                break;
            case 0x8:
                switch (instruction & 0x000F){
                    case 0x0: //8xy0
                        loadRegister((instruction & 0x0F00) >> 8, (instruction & 0x00F0) >> 4);
                        break;
                    case 0x1: //8xy1
                        bitwiseXOR((instruction & 0x0F00) >> 8, (instruction & 0x00F0) >> 4);
                        break;
                    case 0x2: //8xy2
                        bitwiseAND((instruction & 0x0F00) >> 8, (instruction & 0x00F0) >> 4);
                        break;
                    case 0x3: //8xy3
                        bitwiseXOR((instruction & 0x0F00) >> 8, (instruction & 0x00F0) >> 4);
                        break;
                    case 0x4: //8xy4
                        addRegisters((instruction & 0x0F00) >> 8, (instruction & 0x00F0) >> 4);
                        break;
                    case 0x5: //8xy5
                        subtractRegYFromRegX((instruction & 0x0F00) >> 8, (instruction & 0x00F0) >> 4);
                        break;
                    case 0x6: //8xy6
                        shiftRight((instruction & 0x0F00) >> 8);
                        break;
                    case 0x7: //8xy7
                        subtractRegXFromRexY((instruction & 0x0F00) >> 8, (instruction & 0x00F0) >> 4);
                        break;
                    case 0xE: //8xyE
                        shiftLeft((instruction & 0x0F00) >> 8);
                        break;
                }
                break;
            case 0x9: //9xy0
                skipIfNotEqualRegister((instruction & 0x0F00) >> 8, (instruction & 0x00F0) >> 4);
                break;
            case 0xA: //Annn
                setIndexRegister(instruction & 0x0FFF);
                break;
            case 0xB: //Bnnn
                jumpToAddressPlusV0(instruction & 0x0FFF);
                break;
            case 0xC: //Cxkk
                randomValue((instruction & 0x0F00) >> 8, (instruction & 0x00FF));
                break;
            case 0xD: //Dxyn
                drawSprite((instruction & 0x0F00) >> 8, (instruction & 0x00F0) >> 4, instruction & 0x000F);
                break;
            case 0xE:
                if ((instruction & 0x00FF) == 0x9E) skipIfKeyPressed((instruction & 0x0F00) >> 8); //Ex9E
                else skipIfNotKeyPressed((instruction & 0x0F00) >> 8 ); //ExA1
                break;
            case 0XF:
                switch (instruction & 0x00FF){
                    case 0x07: //0xFx07
                        loadDelayTimerToRegister((instruction & 0x0F00) >> 8);
                        break;
                    case 0x0A: //0xFx0A
                        loadKeyPressToRegister((instruction & 0x0F00) >> 8);
                        break;
                    case 0x15: //0xFx15
                        loadRegisterToDelayTimer((instruction & 0x0F00) >> 8);
                        break;
                    case 0x18: //0xFx18
                        loadRegisterToSoundTimer((instruction & 0x0F00) >> 8);
                        break;
                    case 0x1E: //0xFx1E
                        addToIndexRegister((instruction & 0x0F00) >> 8);
                        break;
                    case 0x29: //0xFx29
                        setIndexRegisterToSprite((instruction & 0x0F00) >> 8);
                        break;
                    case 0x33: //0xFx33
                        loadDecimalNumberToIndexRegister((instruction & 0x0F00) >> 8);
                        break;
                    case 0x55: //0xFx55
                        storeRegistersInMemory((instruction & 0x0F00) >> 8);
                        break;
                    case 0x65: //0xFx65
                        readRegistersFromMemory((instruction & 0x0F00) >> 8);
                        break;
                }
        }
    }
    /**
     * Clears the display
     */
    public void clearScreen(){
        display.clear();
    }

    /**
     * Sets the program counter to the address at the top of the stack, then subtracts 1 from stack pointer
     */
    public void returnFromSubroutine(){
        PC = stack[SP];
        SP--;
    }

    /**
     * Sets program counter to address
     *
     * @param address The address (nnn) to jump to.
     */
    public void jumpToAddress(int address){
        PC = address -2; //hacky but I can't be bothered rewriting my opcode handler right now
    }

    /**
     * Sets program counter to the given address plus the value of register V0.
     *
     * @param address The base address to add to V0
     */
    public void jumpToAddressPlusV0(int address){

    }

    /**
     * Increments the stack pointer, stores the current program counter (PC) on the stack,
     * and sets the PC to the specified address to call the subroutine.
     *
     * @param address The address (nnn) to call the subroutine on
     */
    public void callSubroutineAtAddress(int address){
        SP++;
        stack[SP] = PC;
        PC = address -2; //still hacky
    }

    /**
     *  Compares register Vx to the value, if they are equal increments the program counter by 2.
     *
     * @param regX The index of the register Vx (0-15)
     * @param value The value to compare to
     */
    public void skipIfEqualImmediate(int regX, int value){
        if (V[regX] == value){
            PC += 2;
        }
    }

    /**
     *  Compares register Vx to the value, if they are NOT equal increments the program counter by 2.
     *
     * @param regX The index of the register Vx (0-15)
     * @param value The value to compare to
     */
    public void skipIfNotEqualImmediate(int regX, int value){
        if (V[regX] != value){
            PC += 2;
        }
    }

    /**
     * Compares Vx and Vy, if equal program counter increments the program counter by 2.
     *
     * @param regX The index of register Vx (0-15)
     * @param regY The index of register Vy (0-15)
     */
    public void skipIfEqualRegister(int regX, int regY){

    }

    /**
     * Compares Vx and Vy, if NOT equal increments the program counter by 2.
     *
     * @param regX The index of register Vx (0-15)
     * @param regY The index of register Vy (0-15)
     */
    public void skipIfNotEqualRegister(int regX, int regY){

    }

    /**
     * Stores value in register Vx
     *
     * @param regX The index of register Vx (0-15)
     * @param value The value to store in register Vx
     */
    public void setValueInRegister(int regX, int value){
        V[regX] = value;
    }

    /**
     * Adds value to the value already stored inside register Vx
     *
     * @param regX The index of register Vx (0-15)
     * @param value The value to add
     */
    public void addToValueInRegister(int regX, int value){
        V[regX] = (V[regX] + value) & 0xFF;
    }

    /**
     * Sets register Vx to the value of register Vy
     *
     * @param regX The index of register Vx (0-15)
     * @param regY The index of register Vy (0-15)
     */
    public void loadRegister(int regX, int regY){

    }

    /**
     * Bitwise OR on the values of Vx and Vy, then stores result in Vx
     *
     * @param regX The index of register Vx (0-15)
     * @param regY The index of register Vy (0-15)
     */
    public void bitwiseOR(int regX, int regY){

    }

    /**
     * Bitwise AND on the values of Vx and Vy, then stores result in Vx
     *
     * @param regX The index of register Vx (0-15)
     * @param regY The index of register Vy (0-15)
     */
    public void bitwiseAND(int regX, int regY){

    }

    /**
     * Bitwise XOR on the values of Vx and Vy, then stores result in Vx
     *
     * @param regX The index of register Vx (0-15)
     * @param regY The index of register Vy (0-15)
     */
    public void bitwiseXOR(int regX, int regY){

    }

    /**
     * Adds values of Vx and Vy,
     * if result > 255 sets VF to 1 (carry) otherwise 0 (no carry),
     * stores lower 8 bits into Vx
     *
     * @param regX The index of register Vx (0-15)
     * @param regY The index of register Vy (0-15)
     */
    public void addRegisters(int regX, int regY){

    }

    /**
     * Subtracts Vy from Vx and stores result in Vx,
     * if Vx > Vy before the subtraction sets VF to 1 (no borrow) otherwise 0 (borrow)
     *
     * @param regX The index of register Vx (0-15)
     * @param regY The index of register Vy (0-15)
     */
    public void subtractRegYFromRegX(int regX, int regY){

    }

    /**
     * Subtracts Vx from Vy and stores result in Vx,
     * if Vy > Vx before the subtraction sets VF to 1 (no borrow) otherwise 0 (borrow)
     *
     * @param regX The index of register Vx (0-15)
     * @param regY The index of register Vy (0-15)
     */
    public void subtractRegXFromRexY(int regX, int regY){

    }

    /**
     * Divides Vx by 2
     * if the LSB of Vx is 1 before the shift, sets VF to 1 (borrow) otherwise 0 (no borrow)
     *
     * @param regX The index of register Vx (0-15)
     */
    public void shiftRight(int regX){

    }

    /**
     * Multiplies Vx by 2
     * if the MSB of Vx is 1 before the multiplication, VF is set to 1 (carry) otherwise 0 (no carry)
     *
     * @param regX The index of register Vx (0-15)
     */
    public void shiftLeft(int regX){

    }

    /**
     * Sets the index register I to the given address
     *
     * @param address The address to put into the index register
     */
    public void setIndexRegister(int address){
        I = address;
    }

    /**
     * Generates a random number from 0 to 255, ANDs it with the given value,
     * and stores it into register Vx/
     *
     * @param regX The index of register Vx (0-15)
     * @param value The value to bitwise AND with a random number
     */
    public void randomValue(int regX, int value){

    }

    /**
     * Draws a sprite at coordinates (Vx, Vy) with the specified height.
     *
     * @param regX The index of Vx (0-15) containing the x coordinate
     * @param regY The index of Vy (0-15) containing the y coordinate
     * @param height The height of the sprites (from memory starting at I)
     */
    public void drawSprite(int regX, int regY, int height){
        V[15] = 0;
        regX = V[regX];
        regY = V[regY];
        int[] lines = new int[height];
        for (int n = 0; n < height; n++){
            lines[n] = memory.read(I+n);
        }
        for (int row = 0; row < lines.length; row++){
            int y = (regY + row);
            for (int column = 0; column < 8; column++){
                int x = (regX + column);
                int mask = 1 << (7 - column);
                if ((lines[row] & mask) != 0){
                    if (display.flipPixel(x, y)) V[15] = 1;
                }
            }
        }
        display.repaint();
    }

    /**
     * Skips the next instruction if the key corresponding to the value of Vx is currently pressed.
     *
     * @param regX The index of Vx (0-15)
     */
    public void skipIfKeyPressed(int regX){

    }

    /**
     * Skips the next instruction if the key corresponding to the value of Vx is NOT currently pressed.
     *
     * @param regX The index of Vx (0-15)
     */
    public void skipIfNotKeyPressed(int regX){

    }

    /**
     * Loads the value of the delay timer (DT) into register Vx
     *
     * @param regX The index of Vx (0-15)
     */
    public void loadDelayTimerToRegister(int regX){

    }

    /**
     * Loads the value contained in register Vx into the delay timer
     * @param regX The index of Vx (0-15)
     */
    public void loadRegisterToDelayTimer(int regX){

    }

    /**
     * Wait for a key press, store the value of the key in register Vx
     *
     * @param regX The index of Vx (0-15)
     */
    public void loadKeyPressToRegister(int regX){

    }

    /**
     * Loads the value contained in register Vx into the sound timer
     *
     * @param regX The index of Vx (0-15)
     */
    public void loadRegisterToSoundTimer(int regX){

    }

    /**
     * Adds the value of register Vx to register I then stores it into I
     *
     * @param regX The index of Vx (0-15)
     */
    public void addToIndexRegister(int regX){

    }

    /**
     * Sets the index register I to the memory location for the sprite of the value in register Vx.
     * The sprite corresponding to the value in Vx is located at the address for the hexadecimal sprite (0x50 to 0xA0).
     *
     * @param regX The index of Vx (0-15)
     */
    public void setIndexRegisterToSprite(int regX){

    }

    /**
     * Stores BCD representation of the value in regX into register I for the hundreds,
     * I+1 for the tens and I+2 for the ones.
     * @param regX The index of Vx (0-15)
     */
    public void loadDecimalNumberToIndexRegister(int regX){

    }

    /**
     * Stores registers V0 through Vx in memory starting at the address in register I.
     *
     * @param regX The index of Vx (0-15)
     */
    public void storeRegistersInMemory(int regX){

    }

    /**
     * Reads registers V0 through Vx from memory starting at location I
     *
     * @param regX The index of Vx (0-15)
     */
    public void readRegistersFromMemory(int regX){

    }

}

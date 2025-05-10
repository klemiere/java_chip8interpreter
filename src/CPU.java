public class CPU {

    int[] V = new int[16]; // General purpose registers
    int I, DT, ST; // Index, Delay Timer and Sound Timer registers


    CPU(){}

    /**
     * Clears the display
     */
    public void clearScreen(){

    }

    /**
     * Sets the program counter to the address at the top of the stack, then subtracts 1 from stack pointer
     */
    public void returnFromSubroutine(){

    }

    /**
     * Sets program counter to address
     *
     * @param address The address (nnn) to jump to.
     */
    public void jumpToAddress(int address){

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

    }

    /**
     *  Compares register Vx to the value, if they are equal increments the program counter by 2.
     *
     * @param regX The index of the register Vx (0-15)
     * @param value The value to compare to
     */
    public void skipIfEqualImmediate(int regX, int value){

    }

    /**
     *  Compares register Vx to the value, if they are NOT equal increments the program counter by 2.
     *
     * @param regX The index of the register Vx (0-15)
     * @param value The value to compare to
     */
    public void skipIfNotEqualImmediate(int regX, int value){

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

    }

    /**
     * Adds value to the value already stored inside register Vx
     *
     * @param regX The index of register Vx (0-15)
     * @param value The value to add
     */
    public void addToValueInRegister(int regX, int value){

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

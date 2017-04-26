package org.esialb.edison.mcp23017;

import mraa.I2c;

public class MCP23017 {
	public static final short MCP23017_ADDRESS = 0x20;

	// registers
	public static final short MCP23017_IODIRA = 0x00;
	public static final short MCP23017_IPOLA = 0x02;
	public static final short MCP23017_GPINTENA = 0x04;
	public static final short MCP23017_DEFVALA = 0x06;
	public static final short MCP23017_INTCONA = 0x08;
	public static final short MCP23017_IOCONA = 0x0A;
	public static final short MCP23017_GPPUA = 0x0C;
	public static final short MCP23017_INTFA = 0x0E;
	public static final short MCP23017_INTCAPA = 0x10;
	public static final short MCP23017_GPIOA = 0x12;
	public static final short MCP23017_OLATA = 0x14;


	public static final short MCP23017_IODIRB = 0x01;
	public static final short MCP23017_IPOLB = 0x03;
	public static final short MCP23017_GPINTENB = 0x05;
	public static final short MCP23017_DEFVALB = 0x07;
	public static final short MCP23017_INTCONB = 0x09;
	public static final short MCP23017_IOCONB = 0x0B;
	public static final short MCP23017_GPPUB = 0x0D;
	public static final short MCP23017_INTFB = 0x0F;
	public static final short MCP23017_INTCAPB = 0x11;
	public static final short MCP23017_GPIOB = 0x13;
	public static final short MCP23017_OLATB = 0x15;

	public static final short MCP23017_INT_ERR = 255;

	public static final short MCP23017_MODE_INPUT = 0;
	public static final short MCP23017_MODE_OUTPUT = 1;

	public static final short MCP23017_MODE_LOW = 0;
	public static final short MCP23017_MODE_HIGH = 1;
	public static final short MCP23017_MODE_CHANGE = 2;
	public static final short MCP23017_MODE_RISING = 3;
	public static final short MCP23017_MODE_FALLING = 4;

//	void Adafruit_MCP23017::begin(mraa_i2c_context i2c, uint8_t addr) {
//		if (addr > 7) {
//			addr = 7;
//		}
//		i2caddr = MCP23017_ADDRESS | addr;
//
//		this->i2c = i2c;
//
//		mraa_i2c_address(i2c, i2caddr);
//		mraa_i2c_write_byte_data(i2c, 0xff, MCP23017_IODIRA);
//
//		mraa_i2c_address(i2c, i2caddr);
//		mraa_i2c_write_byte_data(i2c, 0xff, MCP23017_IODIRB);
//	}

	private I2c i2c;
	private short addr;
	
	public MCP23017(I2c i2c, short addr) {
		this.i2c = i2c;
		this.addr = (short) (MCP23017_ADDRESS | addr);
	}
	
	public void begin() {
		i2c.address(addr);
		i2c.writeReg((short) 0xff, MCP23017_IODIRA);
		
		i2c.address(addr);
		i2c.writeReg((short) 0xff, MCP23017_IODIRB);
	}
	
//	/**
//	 * Bit number associated to a give Pin
//	 */
//	uint8_t Adafruit_MCP23017::bitForPin(uint8_t pin){
//		return pin%8;
//	}
	
	private static short bitForPin(short pin) {
		return (short) (pin % 8);
	}
	
//
//	/**
//	 * Register address, port dependent, for a given PIN
//	 */
//	uint8_t Adafruit_MCP23017::regForPin(uint8_t pin, uint8_t portAaddr, uint8_t portBaddr){
//		return(pin<8) ?portAaddr:portBaddr;
//	}
	
	private static short regForPin(short pin, short portAAddr, short portBAddr) {
		return pin < 8 ? portAAddr : portBAddr;
	}
	
//
//	/**
//	 * Reads a given register
//	 */
//	uint8_t Adafruit_MCP23017::readRegister(uint8_t addr){
//		mraa_i2c_address(i2c, i2caddr);
//		return mraa_i2c_read_byte_data(i2c, addr);
//	}
	
	private short readRegister(short reg) {
		i2c.address(this.addr);
		return i2c.readReg(reg);
	}
	
//
//
//	/**
//	 * Writes a given register
//	 */
//	void Adafruit_MCP23017::writeRegister(uint8_t regAddr, uint8_t regValue){
//		// Write the register
//		mraa_i2c_address(i2c, i2caddr);
//		mraa_i2c_write_byte_data(i2c, regValue, regAddr);
//	}
//
	
	private void writeRegister(short reg, short value) {
		i2c.address(addr);
		i2c.writeReg(reg, value);
	}
	
//	static uint8_t bitWrite(uint8_t val, uint8_t bit, uint8_t data) {
//		if(data)
//			return val | (1 << bit);
//		else
//			return val & ~(1 << bit);
//	}
	
	private static short bitWrite(short val, short bit, short data) {
		if(data != 0)
			return (short) (val | (1 << bit));
		else
			return (short) (val & ~(1 << bit));
	}
	
//
//	static uint8_t bitRead(uint8_t val, uint8_t bit) {
//		return val & (1 << bit);
//	}
//	/**
//	 * Helper to update a single bit of an A/B register.
//	 * - Reads the current register value
//	 * - Writes the new register value
//	 */
//	void Adafruit_MCP23017::updateRegisterBit(uint8_t pin, uint8_t pValue, uint8_t portAaddr, uint8_t portBaddr) {
//		uint8_t regValue;
//		uint8_t regAddr=regForPin(pin,portAaddr,portBaddr);
//		uint8_t bit=bitForPin(pin);
//		regValue = readRegister(regAddr);
//
//		regValue = bitWrite(regValue, bit, pValue);
//
//		writeRegister(regAddr,regValue);
//	}
	
	private void updateRegisterBit(short pin, short pinValue, short portAAddr, short portBAddr) {
		short regValue;
		short regAddr = regForPin(pin, portAAddr, portBAddr);
		short bit = bitForPin(pin);
		regValue = readRegister(regAddr);
		regValue = bitWrite(regValue, bit, pinValue);
		writeRegister(regAddr, regValue);
	}
	
//
//	/**
//	 * Initializes the MCP23017 given its HW selected address, see datasheet for Address selection.
//	 */
//	void Adafruit_MCP23017::begin(uint8_t bus, uint8_t addr) {
//		if (addr > 7) {
//			addr = 7;
//		}
//		i2caddr = MCP23017_ADDRESS | addr;
//
//		i2c = mraa_i2c_init(bus);
//
//		mraa_i2c_address(i2c, i2caddr);
//		mraa_i2c_write_byte_data(i2c, 0xff, MCP23017_IODIRA);
//
//		mraa_i2c_address(i2c, i2caddr);
//		mraa_i2c_write_byte_data(i2c, 0xff, MCP23017_IODIRB);
//	}
//
//	/**
//	 * Initializes the default MCP23017, with 000 for the configurable part of the address
//	 */
//	void Adafruit_MCP23017::begin(void) {
//		begin(1, 0);
//	}
//
//	/**
//	 * Sets the pin mode to either INPUT or OUTPUT
//	 */
//	void Adafruit_MCP23017::pinMode(uint8_t p, uint8_t d) {
//		updateRegisterBit(p,(d==MCP23017_MODE_INPUT),MCP23017_IODIRA,MCP23017_IODIRB);
//	}
	
	public void pinMode(int pin, short direction) {
		updateRegisterBit((short) pin, (short) (direction == MCP23017_MODE_INPUT ? 1 : 0), MCP23017_IODIRA, MCP23017_IODIRB);
	}
	
//
//	/**
//	 * Reads all 16 pins (port A and B) into a single 16 bits variable.
//	 */
//	uint16_t Adafruit_MCP23017::readGPIOAB() {
//		mraa_i2c_address(i2c, i2caddr);
//		return mraa_i2c_read_word_data(i2c, MCP23017_GPIOA);
//	}
	
	public int readGPIOAB() {
		i2c.address(addr);
		return i2c.readWordReg(MCP23017_GPIOA);
	}
	
//
//	/**
//	 * Read a single port, A or B, and return its current 8 bit value.
//	 * Parameter b should be 0 for GPIOA, and 1 for GPIOB.
//	 */
//	uint8_t Adafruit_MCP23017::readGPIO(uint8_t b) {
//		mraa_i2c_address(i2c, i2caddr);
//		return mraa_i2c_read_byte_data(i2c, (b == 0) ? MCP23017_GPIOA : MCP23017_GPIOB);
//	}
	
	public int readGPIO(int b) {
		i2c.address(this.addr);
		return i2c.readReg(b == 0 ? MCP23017_GPIOA : MCP23017_GPIOB);
	}
	
//
//	/**
//	 * Writes all the pins in one go. This method is very useful if you are implementing a multiplexed matrix and want to get a decent refresh rate.
//	 */
//	void Adafruit_MCP23017::writeGPIOAB(uint16_t ba) {
//		mraa_i2c_address(i2c, i2caddr);
//		mraa_i2c_write_word_data(i2c, ba, MCP23017_GPIOA);
//	}
	
	public void writeGPIOAB(int ba) {
		i2c.address(this.addr);
		i2c.writeWordReg(MCP23017_GPIOA, ba);
	}
	
//
//	void Adafruit_MCP23017::digitalWrite(uint8_t pin, uint8_t d) {
//		uint8_t gpio;
//		uint8_t bit=bitForPin(pin);
//
//
//		// read the current GPIO output latches
//		uint8_t regAddr=regForPin(pin,MCP23017_OLATA,MCP23017_OLATB);
//		gpio = readRegister(regAddr);
//
//		// set the pin and direction
//		gpio = bitWrite(gpio, bit, d);
//
//		// write the new GPIO
//		regAddr=regForPin(pin,MCP23017_GPIOA,MCP23017_GPIOB);
//		writeRegister(regAddr,gpio);
//	}
	
	public void digitalWrite(int pin, boolean value) {
		short gpio;
		short bit = bitForPin((short) pin);
		
		short regAddr = regForPin((short) pin, MCP23017_OLATA, MCP23017_OLATB);
		gpio = readRegister(regAddr);
		
		gpio = bitWrite(gpio, bit, (short) (value ? 1 : 0));
		
		regAddr = regForPin((short) pin, MCP23017_GPIOA, MCP23017_GPIOB);
		writeRegister(regAddr, gpio);
	}
	
//
//	void Adafruit_MCP23017::pullUp(uint8_t p, uint8_t d) {
//		updateRegisterBit(p,d,MCP23017_GPPUA,MCP23017_GPPUB);
//	}
	
	public void pullUp(int pin, boolean value) {
		updateRegisterBit((short) pin, (short)(value ? 1 : 0), MCP23017_GPPUA, MCP23017_GPPUB);
	}
	
//
//	uint8_t Adafruit_MCP23017::digitalRead(uint8_t pin) {
//		uint8_t bit=bitForPin(pin);
//		uint8_t regAddr=regForPin(pin,MCP23017_GPIOA,MCP23017_GPIOB);
//		return (readRegister(regAddr) >> bit) & 0x1;
//	}
	
	public boolean digitalRead(short pin) {
		short bit = bitForPin(pin);
		short regAddr = regForPin(pin, MCP23017_GPIOA, MCP23017_GPIOB);
		return ((readRegister(regAddr) >> bit) & 0x1) == 1;
	}
	
//
//	/**
//	 * Configures the interrupt system. both port A and B are assigned the same configuration.
//	 * Mirroring will OR both INTA and INTB pins.
//	 * Opendrain will set the INT pin to value or open drain.
//	 * polarity will set LOW or HIGH on interrupt.
//	 * Default values after Power On Reset are: (false,flase, LOW)
//	 * If you are connecting the INTA/B pin to arduino 2/3, you should configure the interupt handling as FALLING with
//	 * the default configuration.
//	 */
//	void Adafruit_MCP23017::setupInterrupts(uint8_t mirroring, uint8_t openDrain, uint8_t polarity){
//		// configure the port A
//		uint8_t ioconfValue=readRegister(MCP23017_IOCONA);
//		bitWrite(ioconfValue,6,mirroring);
//		bitWrite(ioconfValue,2,openDrain);
//		bitWrite(ioconfValue,1,polarity);
//		writeRegister(MCP23017_IOCONA,ioconfValue);
//
//		// Configure the port B
//		ioconfValue=readRegister(MCP23017_IOCONB);
//		bitWrite(ioconfValue,6,mirroring);
//		bitWrite(ioconfValue,2,openDrain);
//		bitWrite(ioconfValue,1,polarity);
//		writeRegister(MCP23017_IOCONB,ioconfValue);
//	}
//
//	/**
//	 * Set's up a pin for interrupt. uses arduino MODEs: CHANGE, FALLING, RISING.
//	 *
//	 * Note that the interrupt condition finishes when you read the information about the port / value
//	 * that caused the interrupt or you read the port itself. Check the datasheet can be confusing.
//	 *
//	 */
//	void Adafruit_MCP23017::setupInterruptPin(uint8_t pin, uint8_t mode) {
//
//		// set the pin interrupt control (0 means change, 1 means compare against given value);
//		updateRegisterBit(pin,(mode!=MCP23017_MODE_CHANGE),MCP23017_INTCONA,MCP23017_INTCONB);
//		// if the mode is not CHANGE, we need to set up a default value, different value triggers interrupt
//
//		// In a RISING interrupt the default value is 0, interrupt is triggered when the pin goes to 1.
//		// In a FALLING interrupt the default value is 1, interrupt is triggered when pin goes to 0.
//		updateRegisterBit(pin,(mode==MCP23017_MODE_FALLING),MCP23017_DEFVALA,MCP23017_DEFVALB);
//
//		// enable the pin for interrupt
//		updateRegisterBit(pin,MCP23017_MODE_HIGH,MCP23017_GPINTENA,MCP23017_GPINTENB);
//
//	}
//
//	uint8_t Adafruit_MCP23017::getLastInterruptPin(){
//		uint8_t intf;
//
//		// try port A
//		intf=readRegister(MCP23017_INTFA);
//		for(int i=0;i<8;i++) if (bitRead(intf,i)) return i;
//
//		// try port B
//		intf=readRegister(MCP23017_INTFB);
//		for(int i=0;i<8;i++) if (bitRead(intf,i)) return i+8;
//
//		return MCP23017_INT_ERR;
//
//	}
//	uint8_t Adafruit_MCP23017::getLastInterruptPinValue(){
//		uint8_t intPin=getLastInterruptPin();
//		if(intPin!=MCP23017_INT_ERR){
//			uint8_t intcapreg=regForPin(intPin,MCP23017_INTCAPA,MCP23017_INTCAPB);
//			uint8_t bit=bitForPin(intPin);
//			return (readRegister(intcapreg)>>bit) & (0x01);
//		}
//
//		return MCP23017_INT_ERR;
//	}
//
//	mraa_i2c_context Adafruit_MCP23017::getI2cContext() {
//		return i2c;
//	}

}

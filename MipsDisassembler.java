
public class MipsDisassembler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[] instruction = {0x032BA020, 0x8CE90014, 0x12A90003, 0x022DA822, 0xADB30020, 0x02697824, 0xAE8FFFF4,
				0x018C6020, 0x02A4A825, 0x158FFFF7, 0x8ECDFFF0};

		int address = 0x9A040;

		for(int i = 0; i < instruction.length; i++) {
			System.out.print(String.format("%x",address) + "   ");
			int OpCode = instruction[i] >>> 26;
			address = address + 4;

			if(OpCode == 0) {
				rformat(instruction[i]);
			}else{
				iformat(instruction[i], OpCode, address);
			}
		}

	}

	public static void rformat(int instruction) {
		int maskR1 = 0x03E00000;
		int maskR2 = 0x001F0000;
		int maskDs = 0x0000F800;
		int maskFunction = 0x0000003F;
		int function = instruction & maskFunction;
		int s1 = instruction & maskR1;
		s1 = s1 >>> 21;
		int s2 = instruction & maskR2;
		s2 = s2 >>> 16;
		int Ds = instruction & maskDs;
		Ds = Ds >>> 11;

		System.out.println(functionCode(function) + " $" + Ds + ",$" + s1 + ",$" + s2);
	}

	public static void iformat(int instruction, int OpCode, int address) {

		if(OpCode == 4 || OpCode == 5) {
			int maskR1 = 0x03E00000;
			int maskSd = 0x001F0000;
			int s1 = instruction & maskR1;
			s1 = s1 >>> 21;
			int sd = instruction & maskSd;
			sd = sd >>> 16;
			System.out.println(functionCode(OpCode) + " $" + sd + "," +  "$" + 
					s1 + " " + "address" + " " + (String.format("%x",address(instruction, address)))) ;
		}else{
			int maskR1 = 0x03E00000;
			int maskSd = 0x001F0000;
			int s1 = instruction & maskR1;
			s1 = s1 >>> 21;
			int sd = instruction & maskSd;
			sd = sd >>> 16;
			System.out.println(functionCode(OpCode) + " $" + sd + "," + offset(instruction) + "($" + s1 + ")");
		}
	}




	public static int address(int instruction, int address) {
		short insAddress = 0;
		int branchAddress = 0;
		int maskOffset = 0x0000FFFF;
		insAddress = (short)(instruction & maskOffset);
		branchAddress = insAddress << 2;
		branchAddress = branchAddress + address;
		return branchAddress;

	}


	public static short offset(int instruction) {
		short offset = 0;
		int maskOffset = 0x0000FFFF;
		offset = (short)(instruction & maskOffset);
		return offset;
	}

	public static String functionCode(int OpCode) {

		String func = " ";

		switch(OpCode) {
		case 0x20:
			func = "add";
			break;
		case 0x22:
			func = "sub";
			break;
		case 0x24:
			func = "and";
			break;
		case 0x25:
			func = "or";
		case 0x2A:
			func = "slt";
			break;
		case 0x23:
			func = "lw";
			break;
		case 0x2B:
			func = "sw";
			break;
		case 0x04:
			func = "beq";
			break;
		case 0x05:
			func = "bne";
			break;
		default:
			System.out.println("other instructions not available");

		}
		return func;
	}




	
	
	
}

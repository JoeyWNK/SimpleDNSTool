package message.type;

public class PTR implements Type{
	final static int code = 12; 
	
	final static String name = "PTR";

	@Override
	public byte[] Prase(String deformatted) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String Prase(byte[] formatted, int index, int RDL) {
		return new String(formatted, index, RDL);
	}
}

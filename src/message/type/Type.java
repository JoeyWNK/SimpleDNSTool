package message.type;

public interface Type {
	
	public String Prase(byte[] formatted, int index, int RDL);
	
	public byte[] Prase(String deformatted);
	
	public int getCode();
	
	public String getName();

}

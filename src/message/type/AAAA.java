package message.type;

public class AAAA implements Type{
	
	final static int code = 28; 
	
	final static String name = "AAAA";

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
		StringBuffer sb = new StringBuffer(25);
		for (int i = index; i < index+RDL;i++){
			sb.append(String.format("%02x",(formatted[i] & 0xFF)));
			sb.append(String.format("%02x",(formatted[++i] & 0xFF)));
			sb.append(':');
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}

}

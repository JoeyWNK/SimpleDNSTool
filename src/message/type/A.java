package message.type;

public class A implements Type{
	
	final static int code = 1; 
	
	final static String name = "A";

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
		StringBuffer sb = new StringBuffer(15);
		for (int i = index; i < index+RDL;i++){
			sb.append((formatted[i] & 0xFF));
			sb.append('.');
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}

}

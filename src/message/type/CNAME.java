package message.type;

public class CNAME implements Type{
	
	final static int code = 5; 
	
	final static String name = "CNAME";

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
		StringBuffer sb = new StringBuffer();
		int remain = formatted[index] & 0xFF;
		while (remain > 0) {
			if (remain > 191) {
				sb.append(Prase(formatted, formatted[++index] & 0xFF, RDL));
				break;
			}
			sb.append(new String(java.util.Arrays.copyOfRange(formatted,
					++index, index+ remain)));
			remain = formatted[index+= remain] & 0xFF;
			if (remain > 0)
				sb.append(".");
		}
		return sb.toString();
	}

}

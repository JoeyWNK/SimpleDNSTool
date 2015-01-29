package message.type;

public class MX implements Type {

	final static int code = 15;

	final static String name = "MX";

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
	
	private String GetAdd(byte[] formatted, int index){
		StringBuffer sb = new StringBuffer();
		int remain = formatted[index] & 0xFF;
		while (remain > 0) {
			if (remain > 191) {
				sb.append(GetAdd(formatted, formatted[++index] & 0xFF));
				break;
			}
			sb.append(new String(java.util.Arrays.copyOfRange(formatted,
					++index, index + remain)));
			remain = formatted[index += remain] & 0xFF;
			if (remain > 0)
				sb.append(".");
		}
		return sb.toString();
	}

	@Override
	public String Prase(byte[] formatted, int index, int RDL) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append(formatted[index++] << 8 & 0xFF00 | formatted[index++] & 0xFF);
		sb.append("}");
		sb.append(GetAdd(formatted, index));
		return sb.toString();
	}
}

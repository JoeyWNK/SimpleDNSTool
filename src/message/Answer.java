package message;

import message.type.*;

public class Answer {
	public String DomainName;
	public int Type;
	public int Class;
	public int TTL;
	public int RDL;
	public String ResourceData;

	private boolean isset;

	Answer() {
		isset = false;
	}

	public Answer(byte[] querybytes) {
		Generate(querybytes, 0);
	}

	public String NameReTrack(byte[] querybytes, int pointer) {
		StringBuffer sb = new StringBuffer();
		int remain = querybytes[pointer] & 0xFF;
		while (remain > 0) {
			if (remain > 191) {
				sb.append(NameReTrack(querybytes, querybytes[++pointer] & 0xFF));
				break;
			}
			sb.append(new String(java.util.Arrays.copyOfRange(querybytes,
					++pointer, pointer + remain)));
			remain = querybytes[pointer += remain] & 0xFF;
			if (remain > 0)
				sb.append(".");
		}
		return sb.toString();
	}

	public int Generate(byte[] querybytes, int i) {
		int remain = querybytes[i] & 0xFF;
		StringBuffer sb = new StringBuffer(querybytes.length - 14 - i);
		while (remain > 0) {
			if (remain > 191) {
				sb.append(NameReTrack(querybytes, querybytes[++i] & 0xFF));
				break;
			}
			sb.append(new String(java.util.Arrays.copyOfRange(querybytes, ++i,
					i + remain)));
			remain = querybytes[i += remain] & 0xFF;
			if (remain > 0)
				sb.append(".");
		}
		DomainName = sb.toString();

		Type = (querybytes[++i] << 8) & 0xFF00 | querybytes[++i] & 0xFF;
		Class = (querybytes[++i] << 8) & 0xFF00 | querybytes[++i] & 0xFF;
		TTL = (querybytes[++i] << 32) & 0xFF000000 | (querybytes[++i] << 16)
				& 0xFF0000 | (querybytes[++i] << 8) & 0xFF00 | querybytes[++i]
				& 0xFF;
		RDL = (querybytes[++i] << 8) & 0xFF00 | querybytes[++i] & 0xFF;
		ResourceData = getResourceData(querybytes, ++i, RDL);
		isset = true;
		return i+RDL;
	}

	private String getResourceData(byte[] querybytes, int index, int RDL) {
		Type t = null;
		switch (Type) {
		case 1:
			t = new A();
			break;
		case 2:
			break;
		case 5:
			t = new CNAME();
			break;
		case 12:
			t =new PTR();
			break;
		case 15:
			t =new MX();
			break;
		case 28:
			t = new AAAA();
		}
		if (t == null)
			return new String(querybytes, index, RDL);
		return t.Prase(querybytes, index, RDL);
	}

	public byte[] getBytes() {
		if (isset) {
			String[] Domains = DomainName.split("\\.");
			byte[] re = new byte[DomainName.length() + 12 + RDL];
			int index = 0;
			for (String Domain : Domains) {

				re[index++] = (byte) Domain.length();
				// System.out.print(index+":"+re[index - 1]+"; ");
				System.arraycopy(Domain.getBytes(), 0, re, index,
						Domain.length());
				index += Domain.length();
			}
			index++;// [0]结尾0标识地址结束
			re[index++] = (byte) ((Type >>> 8) & 0xFF);
			re[index++] = (byte) (Type & 0xFF);
			re[index++] = (byte) ((Class >>> 8) & 0xFF);
			re[index++] = (byte) (Class & 0xFF);
			re[index++] = (byte) ((TTL >>> 32) & 0xFF);
			re[index++] = (byte) ((TTL >>> 16) & 0xFF);
			re[index++] = (byte) ((TTL >>> 8) & 0xFF);
			re[index++] = (byte) (TTL & 0xFF);
			re[index++] = (byte) ((RDL >>> 8) & 0xFF);
			re[index++] = (byte) (RDL & 0xFF);
			System.arraycopy(ResourceData.getBytes(), 0, re, index, RDL);
			return re;
		}
		return null;
	}

}

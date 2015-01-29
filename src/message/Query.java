package message;

public class Query {
	public String DomainName;
	public int Type;
	public int Class;
	
	private boolean isset;

	Query() {
		isset = false;
	}

	public Query(byte[] querybytes) {
		Generate(querybytes, 0);
	}

	public int Generate(byte[] querybytes, int i) {
		int remain = querybytes[i] & 0xFF;

		StringBuffer sb = new StringBuffer(querybytes.length - 4);
		do {
			sb.append(new String(java.util.Arrays.copyOfRange(querybytes, ++i,
					i + remain)));
			remain = querybytes[i += remain] & 0xFF;
			if (remain > 0)
				sb.append(".");

		} while (remain > 0);
		DomainName = sb.toString();
		Type = (querybytes[++i] <<= 8) & 0xFF00 | querybytes[++i] & 0xFF;
		Class = (querybytes[++i] <<= 8) & 0xFF00 | querybytes[++i] & 0xFF;
		isset = true;
		return i++;
	}

	public byte[] getBytes() {
		if (isset) {
			String[] Domains = DomainName.split("\\.");
			byte[] re= new byte[DomainName.length() + 6];
			int index = 0;
			for (String Domain: Domains){
				
				re[index++] = (byte) Domain.length();
				//System.out.print(index+":"+re[index - 1]+"; ");
				System.arraycopy(Domain.getBytes(), 0, re, index, Domain.length());
				index += Domain.length();
				}
			index++;//[0]结尾0标识地址结束
			re[index++] = (byte) ((Type >>> 8) & 0xFF);
			re[index++] = (byte) (Type & 0xFF);
			re[index++] = (byte) ((Class>>> 8) & 0xFF);
			re[index++] = (byte) (Class & 0xFF);
			return re;
		}
		return null;
	}
/* Write in Message ReadQuery
	public static Query[] readbytes(byte[] querybytes, int counts) {
		Query[] queries = new Query[counts];
		int index = 0;
		for (int i = 0; i < counts; i++) {
			queries[i] = new Query();
			index = queries[i].Generate(querybytes, index);
		}
		return queries;
	}
*/
}

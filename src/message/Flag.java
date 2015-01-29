package message;

/**
 * @author nk
 *
 */
public class Flag {
	/*
	 * Query/Response Flag
	 */
	public boolean QR;

	/*
	 * Authoritative Answer Flag
	 */
	public boolean AA;

	/*
	 * Truncation Flag
	 */
	public boolean TC;

	/*
	 * Recursion Desired
	 */
	public boolean RD;

	public int OP;
	public int RC;

	public int Z;

	/*
	 * Recursion Available
	 */
	public boolean RA;

	public Flag(int i) {
		RC = i & 0x0F;
		i >>>= 4;
		Z = i & 0x07;
		i >>>= 3;
		RA = (i & 0x01) == 1;
		i >>>= 1;
		RD = (i & 0x01) == 1;
		i >>>= 1;
		TC = (i & 0x01) == 1;
		i >>>= 1;
		AA = (i & 0x01) == 1;
		i >>>= 1;
		OP = i & 0x0F;
		i >>>= 4;
		QR = (i & 0x01) == 1;
	}

	public byte[] getBytes() {
		byte[] re = new byte[2];
		re[0] = (byte) ((QR ? 0x1 : 0x0) << 7 | OP << 3 | (AA ? 0x1 : 0x0) << 2
				| (TC ? 0x1 : 0x0) << 1 | (RD ? 0x1 : 0x0));
		re[1] = (byte) ((RA ? 0x1 : 0x0) << 7 | Z << 4 | RC);
		return re;
	}
}

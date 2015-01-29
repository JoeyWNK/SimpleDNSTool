package message;
public class Header {
	public int transationid;
	public Flag flags;
	public int questions;
	public int answers;
	public int authans;
	public int addans;

	public Header(byte[] headerbytes) {
		transationid = headerbytes[0] << 8 & 0xFF00 | headerbytes[1] & 0xFF;
		flags = new Flag(headerbytes[2] << 8 & 0xFF00 | headerbytes[3] & 0xFF);
		questions = headerbytes[4] << 8 & 0xFF00 | headerbytes[5] & 0xFF;
		answers = headerbytes[6] << 8 & 0xFF00 | headerbytes[7] & 0xFF;
		authans = headerbytes[8] << 8 & 0xFF00 | headerbytes[9] & 0xFF;
		addans = headerbytes[10] << 8 & 0xFF00 | headerbytes[11] & 0xFF;
	}
	
	public byte[] getBytes(){
		byte[] re = new byte[12];
		re[0] = (byte) (transationid >>> 8);
		re[1] = (byte) transationid;
		byte[] fb = flags.getBytes();
		re[2] = fb[0];
		re[3] = fb[1];
		re[4] = (byte) (questions >>> 8);
		re[5] = (byte) questions;
		re[6] = (byte) (answers >>> 8);
		re[7] = (byte) answers;
		re[8] = (byte) (authans >>>8);
		re[9] = (byte) authans;
		re[10] =(byte)(addans >>> 8);
		re[11] =(byte) addans;
		return re;
	}

}

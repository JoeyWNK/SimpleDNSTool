package message;

import java.util.Set;

public class Message {

	public Header header;
	public Query[] queries;
	public Answer[] answers;

	public Message(byte[] data) {
		int index = 12;
		byte[] headerbytes = java.util.Arrays.copyOfRange(data, 0, index);

		header = new Header(headerbytes);

		if (header.questions > 0)
			index = readQuery(data, header.questions, index);
		if (header.answers > 0)
			index = readAnswer(data, header.answers, index);
	}

	public int readAnswer(byte[] data, int count, int index) {
		answers = new Answer[count];
		for (int i = 0; i < count; i++) {
			answers[i] = new Answer();
			index = answers[i].Generate(data, index);
		}
		return ++index;
	}

	public int readQuery(byte[] data, int count, int index) {
		queries = new Query[count];
		for (int i = 0; i < count; i++) {
			queries[i] = new Query();
			index = queries[i].Generate(data, index);
		}
		return ++index;
	}

	public boolean isresponse() {
		return header.flags.QR;
	}

	public void setAnswer(Set<String> got) {
		
	}

	public byte[] getbytes() {
		return null;
		// TODO Auto-generated method stub
		
	}
}

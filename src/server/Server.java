package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Set;

import static server.Output.MessageStyle.*;
import message.Answer;
import message.Message;
import message.Query;

public class Server implements Runnable {

	private DatagramSocket ServerHost;

	public Server(int socketnum) {

		try {
			ServerHost = new DatagramSocket(socketnum);
		} catch (IOException e) {
			Output.println(Error, "Server Fail");
		}
	}

	@Override
	public void run() {
		if (ServerHost != null) {
			Output.printf(Success, "Server Run (Port: %d) \n",
					ServerHost.getLocalPort());
			StartServer();
		}
		Output.println(Important, "End");
	}

	private void StartServer() {
		try {
			byte[] recvBuf = new byte[1024];
			DatagramPacket reqPacket = new DatagramPacket(recvBuf,
					recvBuf.length);
			Output.print("Waiting for Request");
			System.out.print("...");
			ServerHost.receive(reqPacket);

			Output.print(Success, "[GOT]");
			Output.printf(" From: %s:%d \n", reqPacket.getAddress(),
					reqPacket.getPort());

			byte[] recv = java.util.Arrays.copyOfRange(reqPacket.getData(), 0,
					reqPacket.getLength());

			Message msag = new Message(recv);
			Output.print(msag.isresponse() ? "[Res]" : "[Req]");
			for (Query q : msag.queries)
				Output.println(Important,
						String.format(" %s [%s]", q.DomainName, q.Type));
			if (msag.isresponse() && msag.answers != null)
				for (Answer a : msag.answers)
					Output.println(Important, String.format("[RE] %s [%s] %s",
							a.DomainName, a.Type, a.ResourceData));
			Output.println("RAW:");
			for (int i : recv)
				Output.printf(Trace, " %02X", i & 0xFF);
			Output.println("\nHeader:");
			for (int i : msag.header.getBytes())
				Output.printf(Trace, " %02X", i & 0xFF);
			Output.print("\nQuery:\n\t\t\t\t    ");
			for (int i : msag.queries[0].getBytes())
				Output.printf(Trace, " %02X", i & 0xFF);
			Output.println();
			if (msag.queries[0].Type == 12) {
				reqPacket.setData(new byte[1]);
				ServerHost.send(reqPacket);
			} else if (!msag.isresponse()) {
				Set<String> got = Requester.getAllIP(
						msag.queries[0].DomainName, new String[] { "8.8.8.8" },
						2000, 3);
				for (String s : got)
					Output.println(s);
				Output.print(Success, "[Got]");
				msag.setAnswer(got);
				reqPacket.setData(msag.getbytes());
				ServerHost.send(reqPacket);
			}

		} catch (IOException e) {
			Output.println(Error, "Server Run Fail:" + e.getMessage());
		}

		StartServer();
	}
}

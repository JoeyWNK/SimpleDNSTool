import org.fusesource.jansi.AnsiConsole;

import server.Server;

public class Main {

	public static void main(String[] args) {
		AnsiConsole.systemInstall();
		Server s = new Server(53);
		new Thread(s).start();
	}

}

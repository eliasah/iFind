package crawler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author isabelle
 *
 */
public class ServerTest {
	
	public static final int PORT = 40000;

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			while (true) {
				Socket s = ss.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String line;
				while ((line = br.readLine()) != null)
					System.out.println(line);
				br.close();
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

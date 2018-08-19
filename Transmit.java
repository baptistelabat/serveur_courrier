import java.io.*;
import java.net.Socket;




public class Transmit {

	public Transmit() {

	}

	public static String transmitPage(Socket sock, String url)
			throws IOException {
		String msg="";
		//connection a la socket passe par param
		out = new PrintWriter(sock.getOutputStream());
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		//on recoit la ligne qui nous dit que l'on est connecté
		
		//on envoit le login
		send("GET " +url+" HTTP/1.0");
		send("Accept: */*");
		send("");
		
		
		
		
		while ( true ) {
             String str = in.readLine();
             if ( str == null ) {break;}
		
		
             System.out.println(str+"\n");
		
		{msg=msg+str+"\n";}
             
      		  }
		
		send("QUIT");
		//on nous dit que ca a bien quitter
		receive();
		//on ferme les flux
		in.close();
		out.close();
		sock.close();

		return msg;
	}
	//methode qui recoit les information du servveur
	private static void receive() throws IOException {
		String line = in.readLine();
		if (line != null) {
			System.out.println(line);
			System.out.println("\n");
		}

	}
	//methode qui envoi des infos au serveur
	private static void send(String s) {
		System.out.print(s);
		System.out.print("\r\n");
		System.out.flush();
		out.print(s);
		out.print("\r\n");
		out.flush();

	}

	private static BufferedReader in;

	private static PrintWriter out;
}

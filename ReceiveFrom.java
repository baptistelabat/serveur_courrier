import java.io.*;
import java.net.Socket;
import java.lang.Math;



public class ReceiveFrom {

	public ReceiveFrom() {

	}

	public static String[] receiveMail(Socket sock, String login,
			String password)
			throws IOException {
		String url=null;
		String sender=null;
		
		//connection a la socket passe par param
		out = new PrintWriter(sock.getOutputStream());
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		//on recoit la ligne qui nous dit que l'on est connecté
		receive();
		//on envoit le login
		send("USER " + login);
		//on nous dit qu'elle a bien etait recu
		receive();
		//on envoit le mot de passe
		send("PASS " +password );
		//on nous dit que ca c'est bien passe
		receive();
		//on regarde l'état de la boite
		send("STAT");
		//on recupere la reponse
		String linestat = in.readLine();
		
			System.out.println(linestat);
			if (linestat==null)send("QUIT");
			else{
		if (!((linestat.substring(4,5)).contentEquals("0")))//si il y a des messages
		{//on recoit le premier message
		send("TOP "+linestat.substring(4,5)+" 20000");
		//on verifie que le serveur soit pret
		int i;
		for (i=1;i<100;i++)
		{
		String line = in.readLine();
		if (line != null) {
			System.out.println(line);
			System.out.println("\n");
			if ((line.substring(0,4)).equalsIgnoreCase("From"))
			{sender=line.substring(line.indexOf("<")
+1,line.length()-1);
			System.out.println("sender"+sender);
			

				}
			if ((line.substring(0,13)).equalsIgnoreCase("Subject: http"))
			{url=line.substring(9,line.length());
			System.out.println("je le savais"+line+url);
			String[] rep= {url,sender};
			return rep;

				}
		}
		
		}
		}
		else {}
		//on envoi plusieur balise utile
		send("QUIT");}
		//on nous dit que ca a bien quitter
		receive();
		//on ferme les flux
		System.out.println("url1:"+url);
		in.close();
		out.close();
		sock.close();
System.out.println("url:"+url);
		String[] rep= {url,sender};
		return rep;
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

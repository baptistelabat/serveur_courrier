import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class MailIo {

	public MailIo() {

	}

	public static boolean sendMail(Socket sock, String localHost,
			String Destinataire, String from, String subject, String message)
			throws IOException {
		//connection a la socket passe par param
		out = new PrintWriter(sock.getOutputStream());
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		//on recoi la ligne qui nous dit que l'on est connecté
		receive();
		//on envoi la premiere ligne qui indique de qui elle provien
		send("HELO " + localHost);
		//on nous dit qu'elle a bien etait recu
		receive();
		//on envoi l'adresse de l'expediteur
		send("MAIL FROM: <" + from + ">");
		//on nous dit que ca c'est bien passe
		receive();
		//on envoi l'adresse du destinataire
		send("RCPT TO: <" + Destinataire + ">");
		//on recupere la reponse
		receive();
		//on passe au chose serieuse pour commencer l'envoi du corps
		send("DATA");
		//on verifie que le serveur soit pret
		receive();
		//on envoi plusieur balise utile
		send("Subject: " + subject);
		send("Date: " + new Date());
		send("Reply-To:" + from);
		send("To: " + Destinataire);
		
		//on envoi cahque ligne separement en utilisant \n comme delimitateur
		StringTokenizer tokenizer = new StringTokenizer(message, "\n");
		//la ligne ci dessous se traduit par : tant qu'il y a encore des ligne
		while (tokenizer.hasMoreTokens())
			send(tokenizer.nextToken());
		//pour finir l'envoi d'un mail il faut envoi sur une ligne un seul point
		send(".");
		//on verifie que le mail a etait accepte
		receive();
		//on quitte
		send("QUIT");
		//on nous dit que ca a bien quitter
		receive();
		//on ferme les flux
		in.close();
		out.close();
		sock.close();

		return true;
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

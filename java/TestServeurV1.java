import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

// AUTEUR : GUMED
// Code lié à la vidéo disponible sur la chaine YOUTUBE 
// https://www.youtube.com/channel/UCVGLho75bCcYmsmlzTIc2JA

public class TestServeurV1 {

	public static void main(String[] args) {		
		try {
			// Etape #0 : On créé un écouteur sur un port spécifique
			// Dans notre cas : 8000						
			ServerSocket ssocket = new ServerSocket(8000);
			
			System.out.println("Serveur en attente sur le port 8000");
			
			// On va attendre une connexion cliente
			// Dès qu'il y en a une qui arrive on récupère le Socket pour pouvoir communiquer
			// avec le client
			Socket sock = ssocket.accept(); // Instruction BLOQUANTE
			
			System.out.println("Client accepté, pret à communiquer");			
			
			// On traite avec le client
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			// On attend qu'un message soit écrit/envoyé par le client - boucle bloquante
			while (!in.ready()) {}
			
			// On va lire le message envoyé par le client caractère par caractère...
			// On utilise un objet StringBuffer pour simplifier la construction de la chaine
			StringBuffer buff = new StringBuffer();
			
			char c;
			// Tant qu'il y a un caractère à lire faire
			while (in.ready()) {
				c = (char)in.read();
				// On ajoute le caractère au buffer en construction
				buff.append(c);
			}
			
			System.out.println("Message reçu du client " + buff.toString());			
			
			//-----------------------------------------------------------------------
			
			// On écrit dans le Socket comme dans un fichier
			BufferedOutputStream out = new BufferedOutputStream(sock.getOutputStream());
			
			// Envoi du message sous forme d'octets
			System.out.println("Envoi du message au serveur : PONG");
			out.write("PONG".getBytes());
			// force l'envoi de tout le contenu au client
			out.flush();

			System.out.println("On ferme 'proprement'...");
			sock.close();
						
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}




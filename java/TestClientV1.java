import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

// AUTEUR : GUMED
// Code lié à la vidéo disponible sur la chaine YOUTUBE 
// https://www.youtube.com/channel/UCVGLho75bCcYmsmlzTIc2JA

public class TestClientV1 {

	public static void main(String[] args) {		
		try {
			
			
			// Client qui va ouvrir une connexion vers le serveur
			// Il s'agit encore une fois d'un Socket
			Socket sock = new Socket("127.0.0.1", 8000);
			
			System.out.println("Connexion au serveur OK !");
			// On se connecte sur la machine locale (127.0.0.1 ou localhost) sur le port 8000
			
			// On écrit dans le Socket comme dans un fichier
			BufferedOutputStream out = new BufferedOutputStream(sock.getOutputStream());
						
			// Envoi du message sous forme d'octets
			System.out.println("Envoi du message au serveur : PING");
			out.write("PING".getBytes());
			// force l'envoi de tout le contenu au serveur
			out.flush();
			
			//----------------------------------------------------------------------
			
			// On traite avec le serveur (retour de message)
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			// On attend qu'un message soit écrit/envoyé par le serveur - boucle bloquante
			while (!in.ready()) {}
			
			// On va lire le message envoyé par le serveur caractère par caractère...
			// On utilise un objet StringBuffer pour simplifier la construction de la chaine
			StringBuffer buff = new StringBuffer();
			
			char c;
			// Tant qu'il y a un caractère à lire faire
			while (in.ready()) {
				c = (char)in.read();
				// On ajoute le caractère au buffer en construction
				buff.append(c);
			}
			
			System.out.println("Message reçu du serveur " + buff.toString());
			
			System.out.println("On ferme 'proprement'...");
			sock.close();
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
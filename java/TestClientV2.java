import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

// AUTEUR : GUMED
// Code lié à la vidéo disponible sur la chaine YOUTUBE 
// https://www.youtube.com/channel/UCVGLho75bCcYmsmlzTIc2JA

public class TestClientV2 {

	public static void main(String[] args) {		
		try {
								
			Client client = new Client("127.0.0.1", 8000);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


class Client{
	
	// 3 attributs
	private Socket sock; // Socket avec le client courant
	private BufferedOutputStream out; // Flux de sortie Serveur <<--- Client
	private BufferedReader in; // Flux d'entrée Serveur -->> Client 
	
	/**
	 * Construit un client qui se connecte à l'hote sur le port indiqué..
	 * @param hote du serveur
	 * @param port du serveur
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Client(String hote, int port) throws UnknownHostException, IOException {
		// Client qui va ouvrir une connexion vers le serveur
		// Il s'agit encore une fois d'un Socket
		this.sock = new Socket(hote, port);		
		System.out.println("Connexion au serveur OK !");
		
		// On écrit dans le Socket comme dans un fichier
		this.out = new BufferedOutputStream(this.sock.getOutputStream());
		
		// On traite avec le serveur (retour de message)
		this.in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
		
		// Lance le traitement
		this.traitementDuClient();
	}
	
	/**
	 * Implante le traitement que doit réaliser le client
	 * @throws IOException
	 */
	private void traitementDuClient() throws IOException {
		
		System.out.println("Envoi de PING au serveur");
		this.envoyerMessageAuServeur("PING");
		
		
		System.out.println("Attente du message du Serveur");
		this.attendreMessageDuServeur();
		
		System.out.println("Lecture du message du Serveur");
		String message = this.lireMessageDuServeur();		
		System.out.println("Message recu "+message);
		
		System.out.println("On ferme la socket avec le Serveur");
		this.fermer();			
	}
	
	
	/**
	 * Attend un message envoyé par le Serveur : BLOQUANT 
	 * @throws IOException
	 */
	private void attendreMessageDuServeur() throws IOException {
		// On attend qu'un message soit écrit/envoyé par le Serveur - boucle bloquante
		while (!this.in.ready()) {}
	}
	
	/**
	 * Lit un message envoyé par le Serveur
	 * @return le message lu
	 * @throws IOException
	 */
	private String lireMessageDuServeur() throws IOException {
		// On va lire le message envoyé par le client caractère par caractère...
		// On utilise un objet StringBuffer pour simplifier la construction de la chaine
		StringBuffer buff = new StringBuffer();
		
		char c;
		// Tant qu'il y a un caractère à lire faire
		while (this.in.ready()) {
			c = (char)this.in.read();
			// On ajoute le caractère au buffer en construction
			buff.append(c);
		}
		
		return buff.toString();
	}
	
	/**
	 * Envoi un message au client
	 * @param message à envoyer au client
	 * @throws IOException
	 */
	private void envoyerMessageAuServeur(String message) throws IOException {
		this.out.write(message.getBytes());
		// force l'envoi de tout le contenu au client
		this.out.flush();
	}
	
	/**
	 * Ferme la communication avec le client
	 * @throws IOException
	 */
	private void fermer() throws IOException {
		System.out.println("On ferme 'proprement'...");
		this.sock.close();
	}
	
}
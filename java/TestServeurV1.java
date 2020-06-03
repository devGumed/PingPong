import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

// AUTEUR : GUMED
// Code li� � la vid�o disponible sur la chaine YOUTUBE 
// https://www.youtube.com/channel/UCVGLho75bCcYmsmlzTIc2JA

public class TestServeurV1 {

	public static void main(String[] args) {		
		try {
			// Etape #0 : On cr�� un �couteur sur un port sp�cifique
			// Dans notre cas : 8000						
			ServerSocket ssocket = new ServerSocket(8000);
			
			System.out.println("Serveur en attente sur le port 8000");
			
			// On va attendre une connexion cliente
			// D�s qu'il y en a une qui arrive on r�cup�re le Socket pour pouvoir communiquer
			// avec le client
			Socket sock = ssocket.accept(); // Instruction BLOQUANTE
			
			System.out.println("Client accept�, pret � communiquer");			
			
			// On traite avec le client
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			// On attend qu'un message soit �crit/envoy� par le client - boucle bloquante
			while (!in.ready()) {}
			
			// On va lire le message envoy� par le client caract�re par caract�re...
			// On utilise un objet StringBuffer pour simplifier la construction de la chaine
			StringBuffer buff = new StringBuffer();
			
			char c;
			// Tant qu'il y a un caract�re � lire faire
			while (in.ready()) {
				c = (char)in.read();
				// On ajoute le caract�re au buffer en construction
				buff.append(c);
			}
			
			System.out.println("Message re�u du client " + buff.toString());			
			
			//-----------------------------------------------------------------------
			
			// On �crit dans le Socket comme dans un fichier
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




import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


// AUTEUR : GUMED
// Code li� � la vid�o disponible sur la chaine YOUTUBE 
// https://www.youtube.com/channel/UCVGLho75bCcYmsmlzTIc2JA

public class TestServeurV2 {

	public static void main(String[] args) {		
		try {		
			Serveur serveur = new Serveur(8000);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}


// On passe en mode "Objet"
class Serveur{	
	// Constructeur
	/**
	 * Construit un serveur qui �coute sur le port sp�cifi�
	 * @param port d'�coute
	 * @throws IOException
	 */
	public Serveur(int port) throws IOException {
		// Etape #0 : On cr�� un �couteur sur un port sp�cifique
		// Dans notre cas : 8000						
		ServerSocket ssocket = new ServerSocket(port);
		
		System.out.println("Serveur en attente sur le port "+port);
		
		while (true) {
			// On va attendre une connexion cliente
			// D�s qu'il y en a une qui arrive on r�cup�re le Socket pour pouvoir communiquer
			// avec le client
			Socket sock = ssocket.accept(); // Instruction BLOQUANTE		
						
			// Traitement du Client
			System.out.println("Client accept�, pret � communiquer");
			new TraitementClient(sock);
		}
	}	
	
	private class TraitementClient extends Thread {
		//3 attributs
		private Socket sock; // Socket avec le client courant
		private BufferedOutputStream out; // Flux de sortie Serveur ->>> Client
		private BufferedReader in; // Flux d'entr�e Serveur <<<-- Client 
		
		
		/**
		 * Instancie un objet permettant de traiter la communication avec le client
		 * @param sock
		 * @throws IOException
		 */
		public TraitementClient(Socket sock) throws IOException {
			this.sock = sock;
			
			// On r�cup�re les flux d'entr�e et de sortie de la Socket
			// Ils seront utilis�s dans les diff�rentes m�thodes de la classe
			this.in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
			
			// On �crit dans le Socket comme dans un fichier
			this.out = new BufferedOutputStream(this.sock.getOutputStream());
			
			// On d�marre le thread (on peut �galement le faire dans le constructeur de Serveur)
			this.start();
		}
		
		/**
		 * cf. classe Thread
		 */
		@Override
		public void run() {		
			try {
				System.out.println("Attente du message du client");
				this.attendreMessageDuClient();
				
				System.out.println("Lecture du message du client");
				String message = this.lireMessageDuClient();
				System.out.println("Message recu "+message);
				
				
				this.sleep(10000); // Pause de 10 sec...
				
				
				System.out.println("Envoi de PONG au client");
				this.envoyerMessageAuClient("PONG");
				
				System.out.println("On ferme la socket avec le client");
				this.fermer();			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		/**
		 * Attend un message envoy� par le client : BLOQUANT 
		 * @throws IOException
		 * @throws InterruptedException 
		 */
		private void attendreMessageDuClient() throws IOException, InterruptedException {
			// On attend qu'un message soit �crit/envoy� par le client - boucle bloquante
			while (!this.in.ready()) {
				this.sleep(500); // On lib�re le CPU et on �vite ainsi sa surcharge... par contre on ne pourra 
				// identifier le message que toutes les 1 seconde et 1/2
			}
		}
		
		/**
		 * Lit un message envoy� par ce client
		 * @return le message lu
		 * @throws IOException
		 */
		private String lireMessageDuClient() throws IOException {
			// On va lire le message envoy� par le client caract�re par caract�re...
			// On utilise un objet StringBuffer pour simplifier la construction de la chaine
			StringBuffer buff = new StringBuffer();
			
			char c;
			// Tant qu'il y a un caract�re � lire faire
			while (this.in.ready()) {
				c = (char)this.in.read();
				// On ajoute le caract�re au buffer en construction
				buff.append(c);
			}
			
			return buff.toString();
		}
		
		/**
		 * Envoi un message au client
		 * @param message � envoyer au client
		 * @throws IOException
		 */
		private void envoyerMessageAuClient(String message) throws IOException {
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

	
}




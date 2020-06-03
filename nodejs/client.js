// AUTEUR : GUMED
// Code li� � la vid�o disponible sur la chaine YOUTUBE 
// https://www.youtube.com/channel/UCVGLho75bCcYmsmlzTIc2JA

// Biblioth�que pour la communication r�seau
const NET = require('net');

// Hote
const HOTE = "127.0.0.1";

// D�finition du port d'�coute
const PORT = 8000 ;

// Cr�ation d'un client
let clientSocket = NET.Socket();

// Connexion
clientSocket.connect(PORT, HOTE, function(){
    console.log("Connexion avec ", HOTE,":", PORT, "OK");
    console.log("Envoi de PING au serveur");
    clientSocket.write("PING");
});

clientSocket.on('data', function(contenu){
    contenu = contenu.toString();
    console.log("Message recu", contenu);
    console.log("Fermeture de la connexion");
    clientSocket.destroy();
});

clientSocket.on('close', function(){
    console.log("Connexion ferm�e");
});

clientSocket.on('error', function(err){
    console.log('Erreur', err);
});


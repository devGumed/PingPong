// AUTEUR : GUMED
// Code lié à la vidéo disponible sur la chaine YOUTUBE 
// https://www.youtube.com/channel/UCVGLho75bCcYmsmlzTIc2JA

// Bibliothèque pour la communication réseau
const NET = require('net');

// Hote
const HOTE = "127.0.0.1";

// Définition du port d'écoute
const PORT = 8000 ;

// Création d'un client
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
    console.log("Connexion fermée");
});

clientSocket.on('error', function(err){
    console.log('Erreur', err);
});


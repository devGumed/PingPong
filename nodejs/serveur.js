// AUTEUR : GUMED
// Code lié à la vidéo disponible sur la chaine YOUTUBE 
// https://www.youtube.com/channel/UCVGLho75bCcYmsmlzTIc2JA

// Bibliothèque pour la communication réseau
const NET = require('net');


// Définition du port d'écoute
const PORT = 8000 ;

// Création d'un serveur
const SERVEUR = new NET.Server();

// Démarrage de l'écoute
SERVEUR.listen(PORT, function(){
    console.log("Serveur en écoute sur le port : ", PORT);    
});

SERVEUR.on('connection', function(socket){
    // La connexion est effective
    console.log('Connexion client OK');

    // Gestion des erreurs
    socket.on('error', function(err){
        console.log("Erreur ", err);
    });

    // Effectue ce traitement lorsqu'un message arrive du client
    socket.on('data', function(contenu){
        contenu = contenu.toString();

        console.log("Demande du client ", contenu);

        // Envoi de la réponse "PONG"
        console.log("Envoi de la réponse PONG au client");
        socket.write("PONG");

        socket.destroy();
    });

    socket.on('close', function(){
        console.log("Connexion avec le client fermée");
    });
});
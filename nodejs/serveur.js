// AUTEUR : GUMED
// Code li� � la vid�o disponible sur la chaine YOUTUBE 
// https://www.youtube.com/channel/UCVGLho75bCcYmsmlzTIc2JA

// Biblioth�que pour la communication r�seau
const NET = require('net');


// D�finition du port d'�coute
const PORT = 8000 ;

// Cr�ation d'un serveur
const SERVEUR = new NET.Server();

// D�marrage de l'�coute
SERVEUR.listen(PORT, function(){
    console.log("Serveur en �coute sur le port : ", PORT);    
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

        // Envoi de la r�ponse "PONG"
        console.log("Envoi de la r�ponse PONG au client");
        socket.write("PONG");

        socket.destroy();
    });

    socket.on('close', function(){
        console.log("Connexion avec le client ferm�e");
    });
});
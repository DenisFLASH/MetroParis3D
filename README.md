MetroParis3D
============

Dans le cadre d’un projet d’étude, nous devons réaliser une application ambitieuse utilisant la bibliothèque Processing, avec des possibilités d’intégrer des algorithmes d'intelligence Artificielle. 

<b>Objectif</b>
Disposer d’une visualisation 3d de l’ensemble des lignes du métro parisien via un itinéraire. Permettre une expérience voyageur pour avoir une vue d’un trajet lambda, via les décisions de l'utilisateur tout en prenant en compte des facteurs internes et externes.

<b>Spécification Fonctionnelles</b>
L’application doit être capable de..

- Représenter en 3D l’ensemble des lignes et stations.
- Disposer des données RATP (intervalle de temps entre stations, nom des stations, lignes, lien entre les stations) 
- Choisir une station de départ parmi les lignes et stations proposées
- Permettre un changement de vue selon l’action de l’utilisateur
- Permettre à l’utilisateur de contrôler la caméra
- Avancer sur la ligne en cours
- Sur une correspondance, pouvoir changer de ligne, donc de direction
- Voir la durée du trajet réel 
- Permettre à l’utilisateur de choisir la vitesse du programme

<b>Données</b>
MCD à faire

<b>Planning </b>

10/10 : Redaction des sprints, mettre en place environnement de travail (processing, git), Finir MCD, Repartition des tâches, prototype eventuel.

11/10 et 12/10 : Penser à l’architecure diagramme de classe et realisation

13/10 au 15/10 : codage / test

16/10 : test et realisation powerpoint

17/10 : soutenance

<b>Maquettes 3D</b>
Voici comment les éléments du programme sont représentés graphiquement:

- Tunnel(Segment) = un cylindre
- Station = une sphère
- Station en cours = une sphère transparente.

<b>Les acteurs</b> : l’utilisateur

<b>Scénario de l’application</b>

Au lancement de l’application, l’utilisateur visualise le plan du métro en 3d en vue de haut. Il 
peut avec la souris visualiser sous différent angle cette carte. 
Ensuite, Il a la possibilité de choisir une station pour démarrer son voyage. Au clic sur la station, l’utilisateur change de vue pour passer en mode (1ere personne). au clic sur la direction le metro demarre.

L’utilisateur peut modiifer la vitesse du voyage via des boutons events en haut à droite.

Il choisi ensuite dans quel sens il souhaite se diriger. Arriver à une station avec correspondance, l’utilisateur peut choisir une autre ligne et d’autre direction.

<b>Glossaire</b>

● Correspondance - une relation entre deux stations, qui appartiennent aux lignes 
différentes. Exemple: la station Jussieu appartient à la fois aux lignes 7 et 10.

● Horaires - l’ensemble des pairs “heure-station” indiquant à quelle heure un train 
arrivera sur cette station.

● Itinéraire - une suite de stations, qui connecte une station de départ avec une 
station d’arrivée.

● Ligne - une ligne de métro, contenant l’ensemble des stations.

● Segment - une voie qui connecte deux stations voisines.

● Station - une station de métro parisien.

● Temps - une propriété du programme global qui représente le temps dans le 
monde réel, permettant de modéliser des horaires du métro.

● Trajet lambda - un trajet concrète parmi l’ensemble de tous les trajets 
possibles.

● Utilisateur - personne réelle qui utilise le logiciel MetroParis3D.

● Voyageur - personne imaginaire qui représente un voyageur dans le métro 
parisien.

● Vue 1ère personne - la vue dans laquelle la caméra se met à la place d’un 
voyageur. Sur l’écran on voit ce qu’on verrait si on était le voyageur.

<b>Bibliothèques à utiliser</b>

ProScene : gestion caméra et monde
ControlP5 : graphique
Chemin le plus court (algo) mot clé : path finding, short test path
Temboo

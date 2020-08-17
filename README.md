_WORK IN PROGRESS_

# FishMarket Project (fr)

Ce projet a été effectué via l'IDE "Eclipse", j'expliquerai donc spécifiquement les manipulations à effectuer pour faire fonctionner celui-ci sans problème.

Ce problème m'a permis de bien comprendre le fonctionnement d'un système multi-agent et donc d'approfondir ma compréhension d'un système composé d'IA. Il m'a permis aussi de pousser ma compréhension concernant le langage JAVA et aussi de découvrir un nouveau framework : JADE.

## Présentation du projet :

### Problématique :

L'objectif de ce projet était de pouvoir comprendre le fonctionnement d'un système multi-agents. En informatique, un système multi-agent (SMA) est un système composé d'un ensemble d'agents (un processus, un robot, un être humain, etc.), situés dans un certain environnement et interagissant selon certaines relations. Un agent est une entité caractérisée par le fait qu'elle est, au moins partiellement, autonome.

Concernant la partie fonctionnelle de ce projet, il s'agissait de simulé un protocole d'enchère au poisson automatisé, suivant certaines règles :

* le système est composé de 3 type d'agents autonome : le.s vendeur.s, le.s preneur.s et un seul marché.
* Initialement un vendeur annonce un prix pour un lot de poissons.
* Un preneur intéressé par l'offre l'indique.
* Si un seul preneur à fait une offre, le vendeur lui attribut donc le lot.
* Si personne n'est intéressé, le vendeur baisse le prix du poisson.
* Si plusieurs preneurs sont intéressé, le vendeur monte le prix.

### Installation :

Voici quelques indications et manipulations à effectuer afin que le projet puisse fonctionner :

* Dans Eclipse, importer le projet.
* Depuis le Package Explorer, ouvrir le package "Cas_de_test" puis faire un clic droit sur l'un des scénario de test, puis "Run As", puis "appTestx" (où x est le numéro du scénario de test voulu).

### Explication des cas de test :

* appTest1 : une enchère, un vendeur, deux acheteurs manuels
* appTest2 : une enchère, un vendeur, un acheteur auto
* appTest3 : une enchère, un vendeur, trois acheteurs auto
* appTest4 : deux enchères, deux vendeurs, trois acheteurs auto

## Organisation :

Mon projet est composé d'un dossier "librairies" contenant les ".jar" nécessaires à son bon fonctionnement, d'un dossier "Cas_de_test" comprenant différents ".launch" et d'un dossier src contenant différents packages :

* agents
* behaviour_Marche
* behaviour_Preneur
* behaviour_Vendeur
* interfaces

Chacun de ces packages étant composé de différentes classe java explicitant le fonctionnement des agents nécessaires au projet.

## Comportement :

1. Traitement annonce
1. Attente première offre
1. Attente seconde offre
1. Attente troisième offres
1. Réponse attribution
1. Attente paiement
1. Transfert paiement
1. Attente livraison
1. Transfert livraison

_Diagramme d'état à venir_

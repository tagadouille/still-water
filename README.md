# Readme du projet

## Comment compiler et exécuter :

#### Compilation :

```bash

$ ./gradlew build
```

#### Exécution :

```bash

$ ./gradlew run
```

 - Si le build échoue, supprimer les ficher build bin et .gradle et relancer

#### Lancement des tests :

```bash

$ ./gradlew test
```

- Si le build échoue, supprimer les ficher build bin et .gradle et relancer

## Fonctionnalités implémentées :

- Algorithme de calcul de gradient amélioré avec méthode des mailles
- Optimisation multithreadée sur les sons et les mises à jours du modèle
- Interface graphique avec affichage des informations de la partie, les curseurs, les particules et etc 60 fois par secondes
- Application des règles de déplacement sur une population de cellules
- Ajout d'IA semi-déterministes rudimentaires
- Multi-joueur local
    - Touche joueur 1 ( Z Q S D )
    - Touche joueur 2 ( I J K L )
- Ajout de musiques et de possibilité de jouer des sons
- Ajout d'un éditeur de niveau
- Chargement des niveaux depuis des fichiers au format JSON
- Ajout de deux possibilités de finir la partie : être le dernier en lice ou bien être le joueur avec le plus de cellules à la fin du temps imparti

## Explication des choix techniques

Résumé du projet
- But : Simulation optimisée du jeu Liquid War avec agents (cellules) se déplaçant par gradients.
- Principaux artefacts produits : `GameManager`, `Team` (et `Team.Cell`), `GradientGrid`, `GameLevel`, tests unitaires et l'éditeur de niveaux.

Principaux modules
- `GameManager` : orchestration du modèle, initialisation des équipes, spawn des cellules, mises à jour concurrentes et remaniement des armées.
- `Team` / `Team.Cell` : règles d'attaque, de soin, conversion et gestion des positions/énergies.
- `GradientGrid` : calcul du champ de distances (flow-field) par BFS optimisé, gestion d'obstacles partagés.
- `GameLevel` : structure des niveaux (obstacles, spawns, image de fond) et points d'entrée de création.

### Design et patterns utilisés
- Strategy Pattern : Implémenté via l'interface Controller. Ce pattern permet d'interchanger dynamiquement la logique de commande (Souris, Clavier ou IA) pour chaque équipe sans modifier le code de la simulation. Il est tout à fait possible d'ajouter un nouveau moyen de contrôler une équipe sans casser la logique du code, il suffit d'ajouter une nouvelle classe qui implémente Controller et d'ajouter la possibilité au joueur d'utiliser ce contrôle.
- Factory Methods : méthodes statiques de construction (`CreateTeam`, `createGradientGrid`, `gameManagerFactory`) centralisent validations et création d'objets.
- Singleton : Garantit l'unicité de l'instance d'une classe (comme fait pour ListListView, les enums peut être vu comme l'application du pattern Singleton)
- Immutabilité : classe comme `GameLevel` et structures internes marquées `final` réduisent les effets de bord.
- Encapsulation : champs `private`/`final` et accès via getters pour préserver invariants.

### Optimisations de performance :
- Concurrency : exécution parallèle des mises à jour d'équipe via un pool de threads (virtual threads quand disponible) pour tirer parti des CPU multi‑coeurs.

- Optimisations orientées données : utilisation de tableaux primitifs pour la file BFS et matrices `boolean`/`int` pour réduire allocations et GC.

### Pourquoi ces choix sont pertinents :
- Robustesse : Les validations intégrées aux usines statiques (Factories) capturent les erreurs de configuration dès l'initialisation.

- Performance : Les choix algorithmiques permettent de maintenir 60 FPS tout en gérant des milliers de particules actives simultanément.

- Maintenabilité : La séparation claire entre le calcul du gradient, les règles des cellules et les contrôleurs facilite l'évolution du projet (ex: ajout de nouveaux types de bots ou de contrôles).

### Fonctionnalités principales du jeu
- Déplacements guidés par gradient.
- Combat : transfert d'énergie et conversion de cellules ennemies.
- Soin : partage d'énergie entre alliés.
- Multijoueur local et modes de fin de partie (dernier en lice ou meilleur nombre de cellules).
- Éditeur de niveaux et chargement depuis JSON.
- Interface graphique JavaFX (FXML) et audio intégré.




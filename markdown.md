# Projet Camping des Campagnes – Gestion des Animations

Ce projet est réalisé par l'ESN  pour le client « Camping des campagnes » . Il consiste en une application de bureau en Java (programmation orientée objet) connectée à une base de données MySQL. Elle permet à la directrice, Anne Himation, de gérer les équipes, de planifier les activités et d'informer les animateurs par e-mail.

---

## Règles métier et contraintes du système

* **Sécurité** : L'accès est sécurisé par un identifiant et un mot de passe chiffré avec BCrypt.
* **Coordonnées** : L'application enregistre l'adresse, l'e-mail et le téléphone de chaque animateur.
* **Suppression** : Un animateur ne peut être supprimé que s'il n'a jamais encadré d'activité.
* **Co-encadrement** : Une animation peut être encadrée par plusieurs animateurs en même temps.
* **Disponibilité** : Un animateur ne peut pas faire deux activités sur le même créneau horaire.
* **Charge maximale** : Un animateur est limité à 7 animations maximum par jour.
* **Notification** : L'application génère un planning PDF hebdomadaire et l'envoie par e-mail à chaque animateur concerné.
* **Évaluation Git** : Le code est stocké sur Git. L'évaluation de l'équipe prend en compte le nombre de commits de chaque membre.

---

## Notice d'utilisation

### 1. Authentification
L'utilisateur saisit ses identifiants au démarrage.
* L'identifiant "ahimation" ouvre l'Espace Directrice.
* Tout autre identifiant valide ouvre l'Espace Animateur.

### 2. Espace Directrice (Anne Himation)
Le menu principal propose 4 choix :

* **1: Gestion Animateur**
  * Saisir un nouvel animateur avec ses coordonnées.
  * Modifier les informations d'un animateur.
  * Supprimer un animateur (si éligible).
  * Modifier le mot de passe d'un employé.

* **2: Gestion Animation**
  * Créer et configurer les activités du camping.

* **3: Planification**
  * Associer les animations et les animateurs pour la semaine.
  * Vérifier automatiquement les conflits d'horaires et la limite de 7 activités par jour.
  * Envoyer le planning de la semaine par e-mail avec le document PDF en pièce jointe.

* **4: Déconnexion**
  * Quitter la session et revenir à l'écran de connexion.

### 3. Espace Animateur
Le menu pour les animateurs propose 4 choix :

* **1: Mes missions** : Consulter son planning de la semaine.
* **2: Démission** : Demander la désactivation de son compte.
* **3: Changer mdp** : Modifier son mot de passe personnel.
* **4: Déconnexion** : Quitter la session.

# 📱 Projet Android - Gestionnaire de Tâches

## 📝 Description

Ce projet est une application Android développée en Kotlin dans le cadre de l'Évaluation 4 du cours de Programmation Android. L'objectif est de créer une application permettant de gérer des tâches stockées dans une base de données locale.

---

## 🛠 Technologies et Outils Utilisés

- **Langage** : Kotlin
- **Interface** : Jetpack Compose & Material Design 3
- **Base de données locale** : Room
- **Architecture** : MVVM avec ViewModel, Flow et Coroutines
- **Navigation** : Navigation Compose (avec bouton de retour)
- **Thème** : Mode clair et mode sombre (Dark/Light)
- **Versionnement** : GitHub
- **Internationalisation** : Français / Anglais

---

## 📅 Remise

- **Date limite** : Mardi 20 mai 2025
- **Méthode de remise** : Fichier `.zip` sur LÉA (Omnivox)
- **Présentation obligatoire** : Le 20 ou 21 mai, ou avant

---

## 🧱 Modèle de données (Base Room)

Chaque **Tâche** contient les champs suivants :

- `id` : Identifiant unique
- `dateCreation` : Date de création
- `priorite` : Élevé / Moyen / Bas
- `nom` : Nom de la tâche
- `note` : Détails ou commentaires
- `completee` : Statut de complétion (Oui/Non)
- `dateEcheance` : Date d’achèvement prévue

---

## ✅ Fonctionnalités à Implémenter

### Affichage
- Afficher la liste des tâches triées par date d’achèvement
- Afficher les détails d’une tâche sélectionnée

### Gestion des tâches
- Ajouter une nouvelle tâche (avec validation)
- Modifier une tâche existante
- Supprimer une tâche (avec confirmation)
- Compléter une tâche depuis la liste

---

## 💡 Interface Utilisateur

L'application contient :

- Un écran principal pour afficher toutes les tâches
- Un écran pour ajouter une nouvelle tâche
- Un écran pour modifier une tâche existante

### Exigences supplémentaires :

- Animation (au moins une)
- Design ergonomique et esthétique
- Support du thème foncé et clair
- Application bilingue (FR/EN)

---

## 👥 Équipe

- Étudiant 1 : `Abdul Rahman Zahid`
- Étudiant 2 : `Lauvens Simon`

---

> Ce projet met l'accent sur la qualité du code, la bonne architecture, l'expérience utilisateur et le respect des bonnes pratiques Android avec Jetpack Compose.

# FeedMe

Cette application a été développée en utilisant Kotlin et Jetpack Compose. Elle permet de rechercher
des recettes alimentaires à partir de l'API Food2Fork.

## Prérequis

- Android Studio Arctic Fox ou version supérieure
- Un compte Food2Fork pour obtenir une clé API

## Configuration

1. Cloner ce dépôt de code source en utilisant la commande suivante :

```
git clone https://github.com/Valt1-0/FeedMe.git
```

2. Créer un compte Food2Fork et obtenir une clé API

3. Ajouter votre clé API à l'emplacement suivant dans le
   fichier `FeedMe\app\src\main\java\com\example\feedme\util\Constants.kt` :

```
AUTH_TOKEN = "Token votre-clé-api"
```

4. Ouvrir le projet dans Android Studio et exécuter l'application sur un émulateur ou un appareil
   Android

## Dépendances

<!-- -   <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" /> -->

- [Kotlin](https://kotlinlang.org/) - Langage de programmation utilisé pour le développement de
  l'application
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Bibliothèque d'interface
  utilisateur pour Android
- Room - ORM (Object-Relational Mapping) permettant de générer un schéma de base de données
- [Retrofit](https://square.github.io/retrofit/) - Bibliothèque HTTP pour Android
- [Coil](https://coil-kt.github.io/coil/getting_started/) - Bibliothèque de chargement et de mise en
  cache
  d'images pour Android

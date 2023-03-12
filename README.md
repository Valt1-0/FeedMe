# FeedMe WORK IN PROGRESS

Cette application a été développée en utilisant Kotlin et Jetpack Compose. Elle permet de rechercher des recettes alimentaires à partir de l'API Food2Fork.

## Fonctionnalités

- Rechercher des recettes par ingrédient
- Afficher les détails d'une recette
- Ajouter une recette à ses favoris

## Prérequis

- Android Studio Arctic Fox ou version supérieure
- Un compte Food2Fork pour obtenir une clé API

## Configuration

1. Cloner ce dépôt de code source en utilisant la commande suivante :

```
git clone https://github.com/Valt1-0/FeedMe.git
```

2. Créer un compte Food2Fork et obtenir une clé API

3. Ajouter votre clé API à l'emplacement suivant dans le fichier `gradle.properties` :

```
FOOD_2_FORK_API_KEY="votre-clé-api"
```

4. Ouvrir le projet dans Android Studio et exécuter l'application sur un émulateur ou un appareil Android

## Dépendances

- [Kotlin](https://kotlinlang.org/) - Langage de programmation utilisé pour le développement de l'application
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Bibliothèque d'interface utilisateur pour Android
- [Retrofit](https://square.github.io/retrofit/) - Bibliothèque HTTP pour Android
- [Glide](https://github.com/bumptech/glide) - Bibliothèque de chargement et de mise en cache d'images pour Android

## Licence

Ce projet est sous licence MIT - voir le fichier `LICENSE` pour plus de détails.

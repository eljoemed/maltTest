# maltTest

Contexte

L'idée est de faire un microservice qui calcule un taux de commissionnement pour une mission donnée.
Vous êtes chez Malt et vous souhaitez créer des règles de pricing d'une mission assez flexible et capable de prendre en compte la durée de la mission, la relation entre les deux personnes ou le pays de transaction.

L'équipe peut ajouter des règles de pricing en base dont la structure sera détaillée en dessous.
L'application appellera l'API avec des infos sur les deux parties pour demander le taux de commissionnement de la mission

Spécifications

Pour cela, il s’agit de réaliser une API en Java. L'utilisation de n'importe quel framework, librairie, outil est autorisé.
Par défaut, le taux de commissionnement est de 10%
Les règles permettent de définir des exceptions.

Voici un exemple de règle en base, elle permet de définir un taux de commissionnement de 8% si :
la durée de la mission est > 2 mois ou client et freelance ont déjà fait une mission depuis plus de deux mois et localisation des deux personnes en Espagne

// RULE SPAIN AND REPEAT :

{
  "name": "spain or repeat",
  "rate": {
    "percent": 8
  },
  "restrictions": {
    "or": [
      {
        "mission.duration": {
          "gt": "2months"
        }
      },
      {
        "commercialrelation.duration": {
          "gt": "2months"
        }
      }
    ],
    "client.location": {
      "country": "ES"
    },
    "freelancer.location": {
      "country": "ES"
    }
  }
}

Pour qu'une règle corresponde, toutes les conditions doivent être validées. Dans l'exemple ci dessus il s'agirait donc de @or, @clientLocation et @freelancerLocation. On peut voir ici que certaines règles peuvent en inclure d'autres (comme @or, @and ...) et cela peut aller jusqu'à une profondeur arbitraire.

Exemple d'un message pour faire une demande de calcul de taux :

{
  "client": {
    "ip": "217.127.206.227"
  },
  "freelancer": {
    "ip": "217.127.206.227"
  },
  "mission": {
    "length": "4months"
  },
  "commercialrelation": {
    "firstmission": "2018-04-16 13:24:17.510Z",
    "last_mission": "2018-07-16 14:24:17.510Z"
  }
}

Resultat :

{
   fees:8,
   reason:'spain or repeat'   // le nom de la règle qui a été utilisé pour changer le taux par défaut
}

ou si les règles ne correspondent pas 

{
   fees: 10
}

Pour la structure des données, le format est donné en exemple mais n’hésitez pas à faire une autre proposition si vous la jugez plus pertinente. Les spécifications sont minimales, pour laisser au candidat la liberté de rajouter les choses qu'ils trouvent nécessaires ou utiles aux routes comme les erreurs, le format des réponses, règles supplémentaires etc…

Si plusieurs règles s'appliquent, on prend systématiquement la plus avantageuse pour le freelance (donc le taux le plus bas).

Consignes
Dans l'exercice il faut au minimum :
Une route pour ajouter une règle.
Une route pour faire un calcul de taux .
Que le calcul en exemple (ci-dessus) fonctionne
Respectez les bonnes pratiques.

Bonus
Vous êtes libre de rajouter à l'envie des éléments : sécurité de la route d'ajout de règles, documentation, déploiement auto etc...

Aides
Pour la geoip, possibilité d'utiliser ipstack (en version gratuite)
https://ipstack.com/


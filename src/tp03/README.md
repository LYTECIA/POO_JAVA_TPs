# TP03 — Calculette graphique en notation postfixe

## Ce que fait ce projet

Une calculette qui évalue des expressions arithmétiques écrites en notation postfixe
(ex: `8 2 3 + *` = 40). L'interface graphique est fournie par l'enseignant —
j'ai implémenté toute la logique métier du paquetage `tp03.model`.

## Ce que j'ai codé

| Classe | Rôle |
|--------|------|
| `Number` | Token nullaire — stocke une valeur entière et la retourne sans consommer d'opérande |
| `Analyzer` | Analyseur lexical — découpe une chaîne de caractères en séquence de tokens |
| `Calculator` | Évaluateur — orchestre l'analyse et le calcul via une pile d'entiers |

## Algorithme central : évaluation par pile postfixe

Pour évaluer `"8 2 3 + *"` (résultat attendu : 40) :
Token      Action              Pile
────────   ────────────────    ────────
Number(8)  push 8          →  [8]
Number(2)  push 2          →  [8, 2]
Number(3)  push 3          →  [8, 2, 3]
Plus       pop 2 et 3      →  [8, 5]      car 2+3=5
Times      pop 8 et 5      →  [40]        car 8*5=40
Résultat : 40 ✓

Règle : si la pile contient exactement 1 élément en fin de parcours, l'expression
est valide. Sinon, erreur de syntaxe.

## Concepts appris

### Iterator Pattern
`Analyzer` implémente l'interface `IAnalyzer` et produit les tokens un par un.
`Calculator` utilise uniquement trois méthodes :

```java
while (analyzer.hasNext()) {   // reste-t-il un token ?
    analyzer.toNext();          // avance
    Token t = analyzer.current(); // lit le token courant
}
```

`Calculator` ne sait pas comment `Analyzer` stocke les données en interne.
Il parle uniquement au contrat défini par `IAnalyzer`. C'est le principe
de séparation des responsabilités : l'un lit, l'autre calcule.

### Programmation vers les interfaces
```java
// ✗ couplage fort — Calculator dépend d'une classe concrète
private final Analyzer analyzer;

// ✓ couplage faible — Calculator dépend d'un contrat
private final IAnalyzer analyzer;
```
Si demain on crée `FastAnalyzer`, une seule ligne change dans le constructeur.
Tout le reste du code `Calculator` reste intact.

### Objets stateless vs stateful

Les opérateurs `Plus`, `Minus`, `Times`... n'ont **aucun attribut**.
Ils définissent un comportement pur :
```java
// Plus n'a rien à stocker — il reçoit tout ce dont il a besoin
int value(int... operands) { return operands[0] + operands[1]; }
```

`Number` a un attribut `val` car il doit stocker la valeur lue
pendant l'analyse pour la restituer plus tard lors du calcul :
```java
class Number implements Token {
    private final int val; // obligatoire — l'état doit survivre entre les appels
    int value(int... operands) { return val; }
}
```

### Single Responsibility Principle
Chaque classe fait **une seule chose** :
- `Analyzer` → lit et découpe la chaîne en tokens
- `Calculator` → orchestre le calcul avec une pile
- `Token` et ses implémentations → définissent chaque opération

## Environnement
Java 21 · Eclipse IDE · Git · Linux

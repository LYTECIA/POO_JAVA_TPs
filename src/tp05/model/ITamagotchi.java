package tp05.model;

/**
 * Modélise un tamagotchi.
 * Un tamagotchi est un être virtuel muni d'un stock d'énergie et de deux
 *  organes : un coeur et un estomac.
 * Pour rester fonctionnels, les organes puisent régulièrement dans le stock
 *  d'energie.
 * Pour recharger le stock d'énergie, le tamagotchi doit se nourrir.
 * Toutefois, les tamagotchis sont tellement gourmands qu'ils peuvent manger
 *  jusqu'à s'en faire exploser la panse (et mourir ainsi prématurément).
 * Enfin, l'espérance de vie maximale (du coeur) du tamagotchi est fixée à
 *  la construction, mais son espérance de vie réelle peut diminuer ou
 *  augmenter selon son alimentation. Quand son âge atteint son espérance
 *  de vie réelle, son coeur cesse de battre.
 * @inv <pre>
 *     describe() != null
 *     energyAmount() >= 0
 *     0 <= age() <= lifeTime() <= maxLifeTime()
 *     0 < lifeTime()
 *     stomachHasExploded() ==> !stomachIsWorking() && !isAlive()
 *     age() == lifeTime() ==> !isAlive()
 *     isVenerable() <==> age() == maxLifeTime()
 *     isStarved() <==> energyAmount() == 0 </pre>
 * @cons <pre>
 *     $DESC$
 *         Un tamagotchi nouveau-né
 *     $ARGS$ int energyInit, int maxLife, int stomachSize
 *     $PRE$
 *         energyInit >= 0
 *         maxLife > 0
 *         stomachSize > 0
 *     $POST$
 *         energyAmount() == energyInit
 *         age() == 0
 *         lifeTime() == maxLife
 *         maxLifeTime() == maxLife
 *         isAlive()
 *         stomachSize() == stomachSize
 *         stomachIsWorking() </pre>  
 */
public interface ITamagotchi {

    // ATTRIBUTS

    /**
     * Quantité de nourriture minimale apportée par un plat.
     */
    int MIN_FOOD_RATION = 2;

    /**
     * Quantité de nourriture maximale apportée par un plat.
     */
    int MAX_FOOD_RATION = 5;

    // REQUETES

    /**
     * Décrit ce tamagotchi à l'aide d'une courte chaîne de caractères.
     */
    String describe();

    /**
     * Indique si le tamagotchi est bien vivant, c'est-à-dire si son coeur bat.
     */
    boolean isAlive();

    /**
     * La durée de vie maximale de ce tamagotchi.
     */
    int maxLifeTime();

    /**
     * L'espérance de vie de ce tamagotchi (à cet instant).
     */
    int lifeTime();

    /**
     * L'âge de ce tamagotchi.
     */
    int age();

    /**
     * Indique si le tamagotchi a atteint sa durée de vie maximale.
     */
    boolean isVenerable();

    /**
     * Indique si le tamagotchi est affamé, c'est-à-dire si son stock d'énergie
     *  est vide.
     */
    boolean isStarved();

    /**
     * Indique si l'estomac du tamagotchi a explosé.
     */
    boolean stomachHasExploded();

    /**
     * Indique si l'estomac du tamagotchi fonctionne.
     */
    boolean stomachIsWorking();

    /**
     * Indique si le tamagotchi est en bonne santé (pas nécessairement vivant).
     */
    //boolean isHealthy();

    /**
     * La taille de l'estomac du tamagotchi.
     */
    int stomachSize();

    /**
     * La quantité d'énergie du tamagotchi.
     */
    int energyAmount();

    // COMMANDES

    /**
     * Nourrit le tamagotchi avec mealNb plats.
     * @pre <pre>
     *     isAlive() && stomachIsWorking()
     *     mealNb > 0 </pre>
     * @post <pre>
     *     La quantité de nourriture présente dans l'estomac du tamagotchi
     *      a augmenté d'une valeur aléatoire entre MIN_FOOD_RATION et
     *      MAX_FOOD_RATION pour chaque plat ingéré
     *     </pre>
     */
    void eat(int mealNb);

    /**
     * Fait vivre ce tamagotchi pendant un tour de jeu.
     * @pre <pre>
     *     isAlive() </pre>
     * @post <pre>
     *     Si l'estomac fonctionnait, il a vécu un tour de jeu
     *     Puis le coeur a vécu un tour de jeu </pre>
     */
    void evolve();
}

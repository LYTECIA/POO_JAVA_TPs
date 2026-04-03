package tp05.model;

import util.Contract;

public class Tamagotchi implements ITamagotchi {

    // ATTRIBUTS

    private final IEnergyStock stock;
    private final IHeart heart;
    private final IStomach stomach;

    // CONSTRUCTEURS

    public Tamagotchi(int energyInit, int maxLife, int stomachSize) {
        Contract.checkCondition(energyInit >= 0);
        Contract.checkCondition(maxLife > 0);
        Contract.checkCondition(stomachSize > 0);

        stock = new EnergyStock(energyInit);
        heart = new Heart(maxLife, stock);
        stomach = new Stomach(stomachSize, stock);
    }

    // REQUETES

    @Override
    public int age() {
        return heart.age();
    }
    
    @Override
    public String describe() {
        return heart.describe() + " " + stomach.describe()
                + " " + stock.describe();
    }

    @Override
    public int energyAmount() {
        return stock.crtLevel();
    }

    @Override
    public boolean isAlive() {
        return heart.isBeating();
    }

    @Override
    public boolean isStarved() {
        return stock.isEmpty();
    }

    @Override
    public boolean isVenerable() {
        return heart.age() == heart.maxLifeTime();
    }

    @Override
    public int lifeTime() {
        return heart.lifeTime();
    }

    @Override
    public int maxLifeTime() {
        return heart.maxLifeTime();
    }

    @Override
    public boolean stomachHasExploded() {
        return stomach.isExploded();
    }

    @Override
    public boolean stomachIsWorking() {
        return stomach.isWorking();
    }
    
    @Override
    public int stomachSize() {
        return stomach.size();
    }

    // COMMANDES

    @Override
    public void eat(int mealNb) {
        Contract.checkCondition(isAlive());
        Contract.checkCondition(stomachIsWorking());
        Contract.checkCondition(mealNb > 0);

        int qty = 0;
        for (int i = 0; i < mealNb; i++) {
            qty += alea(MIN_FOOD_RATION, MAX_FOOD_RATION);
        }
        stomach.fill(qty);
        if (stomach.isExploded()) {
            heart.stopBeating();
        }
    }

    @Override
    public void evolve() {
        Contract.checkCondition(isAlive());

        if (stomachIsWorking()) {
            stomach.evolve();
        }
        heart.evolve();
    }

    // OUTILS

    /**
     * Calcule une valeur « au hasard » entre 'a' et 'b'.
     * @pre <pre>
     *     a <= b </pre>
     */
    private static int alea(int a, int b) {
        assert a <= b;
        return a + (int) (Math.random() * (b - a + 1));
    }
}

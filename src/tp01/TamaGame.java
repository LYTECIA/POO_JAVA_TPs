package tp01;

import java.util.Random;

public class TamaGame {
    private static final Random NB_GENERATOR = new Random(315L); 
    private static final int NB_OF_TURNS = 12;
    private static final double FEED_PROBA = 0.25d;
    
    private static boolean randomChanceToFeed() {
        double p = NB_GENERATOR.nextDouble();
        return p <= FEED_PROBA;
    }
    public static void main(String[] args) {
			Tamagotchi t = new Tamagotchi();
		for (int i=1;i<=NB_OF_TURNS;i++) {
			if(randomChanceToFeed()) {
				t.eat();
			}
			t.evolve();
			System.out.println(t.describe());
		}

	}

}

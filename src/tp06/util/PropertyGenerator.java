package tp06.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import tp06.model.Property;

public class PropertyGenerator {
    
    // CONSTANTES DE CLASSE

    // Les noms sont inspirés du jeu de Monopoly
    private static final String[] ADRESSES = new String[] {
        "Boulevard de Belleville", "Rue Lecourbe",
        "Rue de Vaugirard", "Rue de Courcelles", "Avenue de la République",
        "Boulevard de la Villette", "Avenue de Neuilly", "Rue de Paradis",
        "Avenue Mozart", "Boulevard Saint-Michel", "Place Pigalle",
        "Avenue Matignon", "Boulevard Malesherbes", "Avenue Henri-Martin",
        "Faubourg Saint-Honoré", "Place de la Bourse", "Rue La Fayette",
        "Avenue de Breteuil", "Avenue Foch", "Boulevard des Capucines",
        "Avenue des Champs-Élysées", "Rue de la Paix"
    };
    
    // METHODES DE CLASSE
    
    /**
     * Génération d'une liste aléatoire de biens immobiliers.
     */
    public static List<Property> generateProperties() {
        int n = ADRESSES.length;
        int size = (int) (Math.random() * (n / 2 + 1)) + (n / 2);
        List<Property> data = new ArrayList<>(size);
        List<Integer> indices = new ArrayList<>(
                IntStream.range(0, n).boxed().toList());
        
        for (int i = 0; i < size; i++) {
            int x = (int) (Math.random() * indices.size());
            int index = indices.get(x);
            double s = area(100, 500);
            double p = price(100_000, 1_000_000);
            int nb = roomNb(1, 7);
            Property r = new Property(
                    ADRESSES[index],
                    s,
                    nb,
                    p,
                    squareMeterPrice(s, p));
            data.add(r);
            indices.remove(x);
        }
        return data;
    }
    
    // OUTILS
    
    private static double area(int a, int b) {
        return ((int) (Math.random() * (b - a + 1) / 10)) * 10.0 + a;
    }
    
    private static int roomNb(int a, int b) {
        return ((int) (Math.random() * (b - a + 1))) + a;
    }
    
    private static double price(long a, long b) {
        return ((long) (Math.random() * (b - a + 1) / 100)) * 100.0 + a;
    }

    private static double squareMeterPrice(double space, double price) {
        return price / space;
    }
}

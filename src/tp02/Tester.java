package tp02;

public class Tester {
    
    private static final boolean CODE = true;
    private static final boolean DECODE = false;
    
    private static void printClear(String m) {
        System.out.print("Message clair : ");
        System.out.println(m);
    }

    private static String shiftMessage(String m, int shift) {
        SubstCipher encoder = new SubstCipher(shift);
        encoder.buildShiftedTextFor(m);
        return encoder.getLastShiftedText();
    }
    
    private static void printShifted(boolean clear, String m, int shift) {
        String prompt = clear ?
                "Message clair, chiffré avec un décalage de +"
                : "Message chiffré, déchiffré avec un décalage de -";
        System.out.print(prompt + shift + " : ");
        String shifted = shiftMessage(m, clear ? shift : -shift);
        System.out.println(shifted);
    }
    
    public static void main(String[] args) {
        final String clear = "Les grecs attaquent par derrière !";
        /*
         * À cause du 'è' il y a autant (5) de 'e' que de 'r' dans le message.
         * Selon le décalage utilisé, ce sera le code de 'e' ou celui de 'r'
         *  qui sera sélectionné comme plus petite des lettres les plus
         *  fréquentes dans le message.
         * Le code de 'e' est plus petit que celui de 'r' :
         *   shift =  0.. 8 -> guessedShift = 0..8
         *   shift = 22..25 -> guessedShift = 22..25
         * Le code de 'r' est plus petit que celui de 'e' :
         *   shift =  9..12 -> guessedShift = 22..25
         *   shift = 13..21 -> guessedShift = 0..8
         */
        final int[] shift = new int[] {
                 4,
                10,
                17,
                23,
        };
        final String[] cyphered = new String[] {
                "Piw kvigw exxeuyirx tev hivvmèvi !", //  4
                "Voc qbomc kddkaeoxd zkb nobbsèbo !", // 10
                "Cvj xivtj rkkrhlvek gri uviizèiv !", // 17
                "Ibp dobzp xqqxnrbkq mxo aboofèob !", // 23
        };
        
        printClear(clear);
        System.out.println();

        for (int i = 0 ; i < cyphered.length; i++) {
            printShifted(CODE, clear, shift[i]);
            System.out.print("Décalage deviné à partir du message chiffré : ");
            int guessedShift = SubstCipher.guessShiftFrom(cyphered[i]);
            System.out.println(guessedShift);
            printShifted(DECODE, cyphered[i], guessedShift);
            System.out.println();
        }
    }
}

package tp08.model;

/**
 * La fabrique du réseau des salles.
 * Cette classe est non instanciable car c'est une fabrique.
 * Elle est dotée d'une méthode statique qui donne accès à l'instance de
 *  IRoomNetwork nécessaire au jeu sans que l'on ait connaissance de la classe
 *  génératrice de cette instance.
 */
public final class RoomNetworkFactory {

    // CONSTANTES STATIQUES

    private static final IRoomNetwork NETWORK = new RoomNetwork();

    // CONSTRUCTEURS

    private RoomNetworkFactory() {
        // pas d'instanciation externe possible
    }

    public static IRoomNetwork get() {
        return NETWORK;
    }
}

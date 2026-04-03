package tp06.model;

import java.util.function.Function;

public enum Feature {
    ADDRESS(
            "Adresse",
            String.class,
            Property::address),
    AREA(
            "Surface",
            Double.class,
            Property::area),
    NB_OF_ROOMS(
            "Nb de pièces",
            Integer.class,
            Property::nbOfRooms),
    PRICE(
            "Prix",
            Double.class,
            Property::price),
    SQ_MT_PRICE(
            "Prix au m²",
            Double.class,
            Property::squareMeterPrice);
    
    private final String label;
    private final Class<?> type;
    private final Function<Property, Comparable<?>> keyExtractor;
    
    private Feature(String lbl, Class<?> t,
            Function<Property, Comparable<?>> m) {
        label = lbl;
        type = t;
        keyExtractor = m;
    }
    
    @Override
    public String toString() {
        return label;
    }
    
    public Class<?> type() {
        return type;
    }
    
    @SuppressWarnings("unchecked")
    public <U extends Comparable<? super U>>
            Function<Property, U> keyExtractor() {
        return (Function<Property, U>) keyExtractor;
    }
}

package tp05.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public enum TamaImage {
    ALIVE_HUNGRY("/tp05/images/aliveHungry.gif"),
    ALIVE_STUFFED("/tp05/images/aliveStuffed.gif"),
    DEAD_OLD_SICK("/tp05/images/deadOldSick.gif"),
    DEAD_OLD_HEALTHY("/tp05/images/deadOldHealthy.gif"),
    DEAD_YOUNG_SICK("/tp05/images/deadYoungSick.gif"),
    DEAD_YOUNG_HEALTHY("/tp05/images/deadYoungHealthy.gif");

    private static final TamaImage[] CACHE = values();
    
    private final Image image;
    
    private TamaImage(String pn) {
        image = createImage(pn);
    }

    public Image image() {
        return image;
    }
    
    private Image createImage(String pn) {
        InputStream input = TamaImage.class.getResourceAsStream(pn);
        if (input == null) {
            System.err.println(
                    "Ressource non trouvée : " + pn);
            return null;
        }

        Image result = null;
        try (input) {
            result = ImageIO.read(input);
        } catch (IOException e) {
            System.err.println(
                    "Erreur lors du chargement de l'image : " + pn);
        }        
        return result;
    }

    public static Dimension getMaxImageSize(ImageObserver obs) {
        int maxWidth = 0;
        int maxHeight = 0;
        for (TamaImage ti : CACHE) {
            Image i = ti.image;
            if (i.getWidth(obs) > maxWidth) {
                maxWidth = i.getWidth(obs);
            }
            if (i.getHeight(obs) > maxHeight) {
                maxHeight = i.getHeight(obs);
            }
        }
        final int minWidth = 480;
        final int baseHeight = 260;
        return new Dimension(
                Math.max(maxWidth, minWidth), maxHeight + baseHeight);
    }
}

package tp05.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class GraphicTama extends JComponent {

    // ATTRIBUTS

    private final ObservableTamagotchi model;

    // CONSTRUCTEURS

    public GraphicTama(ObservableTamagotchi tama) {
        model = tama;
        model.addChangeListener(e -> repaint());
        Dimension imageDim = TamaImage.getMaxImageSize(this);
        setPreferredSize(imageDim);
    }

    // REQUETES

    public ObservableTamagotchi getModel() {
        return model;
    }

    // COMMANDES

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = null;
        if (model.isAlive()) {
            if (model.isStarved()) {
                image = TamaImage.ALIVE_HUNGRY.image();
            } else {
                image = TamaImage.ALIVE_STUFFED.image();
            }
        } else {
            if (model.isVenerable()) {
                if (model.stomachIsWorking()) {
                    image = TamaImage.DEAD_OLD_HEALTHY.image();
                } else {
                    image = TamaImage.DEAD_OLD_SICK.image();
                }
            } else {
                if (model.stomachIsWorking()) {
                    image = TamaImage.DEAD_YOUNG_HEALTHY.image();
                } else {
                    image = TamaImage.DEAD_YOUNG_SICK.image();
                }
            }
        }
        int w = image.getWidth(this);
        int h = image.getHeight(this);
        g.drawImage(image,
                (getWidth() - w) / 2, 10 + (getHeight() - h) / 2, null);
    }
}

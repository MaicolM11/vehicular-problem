package vehicular.problem.views;

import java.awt.Dimension;
import java.awt.Graphics;

import vehicular.problem.models.Board;
import vehicular.problem.models.Vehicle;

import java.awt.Toolkit;

public class BoardView extends Board {

    private double hgap, vgap;
    int i = 0;

    public BoardView () {
        super();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        hgap = screenSize.getWidth() / via[0].length;
        vgap = screenSize.getHeight() * .3;
    }
    
    public void paintVehicles(Graphics g) {
        i++;
        for (Vehicle v : vehicles) {
            v.move(i);
            g.setColor(v.color);
            double x = v.getDirection() > 0 ? -hgap * v.getLength():0;
            g.fillOval((int)(v.getX() * hgap + x), (int)((v.getY() +1 )* vgap ), (int)(hgap * v.getLength()), 50);
        }
    }
}

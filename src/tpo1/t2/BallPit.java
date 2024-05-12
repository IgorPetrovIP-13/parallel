package tpo1.t2;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class BallPit {
    private Ellipse2D pit;
    public BallPit(int x, int y, int diameter) {
        pit = new Ellipse2D.Double(x, y, diameter, diameter);
    }
    public void draw(Graphics2D g2) {
        g2.setColor(Color.CYAN);
        g2.fill(pit);
    }
    public boolean contains(int x, int y) {
        return pit.contains(x, y);
    }
}

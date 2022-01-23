package rendering.topDown;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import util.Vector;

public interface ShapeRenderer2D {
  public Shape getShape(Vector centreObjectScreenPos, float zoom);

  public Color getColor();

  public Stroke getStroke();
}

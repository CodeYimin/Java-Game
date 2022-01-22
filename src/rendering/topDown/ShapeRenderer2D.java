package rendering.topDown;

import java.awt.Color;
import java.awt.geom.RectangularShape;

import util.Vector;

public interface ShapeRenderer2D {
  public RectangularShape getShape(Vector centreObjectScreenPos, float zoom);
  public Color getColor();
}

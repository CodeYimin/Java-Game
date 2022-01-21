package rendering;

import java.awt.Dimension;
import java.awt.Graphics;

public interface Drawable {
  public void draw(Graphics graphics, Dimension canvasSize);
}

package rendering;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import util.Vector;

public class RectangleRenderer2D implements ShapeRenderer2D {
  private Vector size;
  private Color color;
  private Vector offset;

  public RectangleRenderer2D(Vector size, Color color, Vector offset) {
    this.size = size;
    this.color = color;
    this.offset = offset;
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public Vector getOffset() {
    return this.offset;
  }

  public void setOffset(Vector offset) {
    this.offset = offset;
  }

  public Vector getSize() {
    return this.size;
  }

  public void setSize(Vector size) {
    this.size = size;
  }

  public RectangularShape getShape(Vector centrePos, float zoom) {
    Vector correctedPoint = centrePos
      .divide(zoom)
      .subtract(getSize().divide(2))
      .add(getOffset().multiply(1, -1))
      .multiply(zoom);

    Vector correctedSize = getSize().multiply(zoom);

    return new Rectangle2D.Double(
      correctedPoint.getX(),
      correctedPoint.getY(),
      correctedSize.getX(),
      correctedSize.getY()
    );
  }

}

package rendering.topDown;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import util.Vector;

public class LineRenderer2D implements ShapeRenderer2D {

  private double length;
  private double angle;
  private Color color;
  private float thickness;
  private Vector offset;

  public LineRenderer2D(
    double length,
    double angle,
    Color color,
    float thickness,
    Vector offset
  ) {
    this.length = length;
    this.angle = angle;
    this.color = color;
    this.thickness = thickness;
    this.offset = offset;
  }

  public LineRenderer2D(
    double length,
    Vector direction,
    Color color,
    float thickness,
    Vector offset
  ) {
    this.length = length;
    this.angle = direction.toGameDegrees();
    this.color = color;
    this.thickness = thickness;
    this.offset = offset;
  }

  public Stroke getStroke() {
    return new BasicStroke(getThickness());
  }

  public double getLength() {
    return this.length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public double getAngle() {
    return this.angle;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public float getThickness() {
    return this.thickness;
  }

  public void setThickness(float thickness) {
    this.thickness = thickness;
  }

  public Vector getOffset() {
    return this.offset;
  }

  public void setOffset(Vector offset) {
    this.offset = offset;
  }

  public Shape getShape(Vector centrePos, float zoom) {
    Vector offsettedPoint = centrePos
      .divide(zoom)
      .add(getOffset().multiply(1, -1))
      .multiply(zoom);

    Vector secondPoint = offsettedPoint
      .divide(zoom)
      .add(
        Vector.fromGameDegrees(getAngle()).multiply(getLength()).multiply(1, -1)
      )
      .multiply(zoom);

    return new Line2D.Double(
      offsettedPoint.toPoint2D(),
      secondPoint.toPoint2D()
    );
  }
}

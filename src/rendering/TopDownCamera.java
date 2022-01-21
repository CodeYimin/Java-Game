package rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;

import entities.RenderableObject;
import util.Vector;

public class TopDownCamera extends Camera {

  private float zoom;

  public float getZoom() {
    return zoom;
  }

  public void setZoom(float zoom) {
    this.zoom = zoom;
  }

  {
    zoom = 50;
  }

  private Vector deltaPositionToScreenPosition(Vector deltaPosition, Dimension canvasSize) {
    return new Vector(
      deltaPosition.getX() * zoom + canvasSize.getWidth() / 2,
      -deltaPosition.getY() * zoom + canvasSize.getHeight() / 2
    );
  }

  @Override
  public void draw(Graphics graphics, Dimension canvasSize) {
    Graphics2D graphics2D = (Graphics2D) graphics;

    Vector myPosition = getTransform().getPosition();

    RenderableObject[] renderedObjects = getObjects()
      .stream()
      .filter(RenderableObject.class::isInstance)
      .map(RenderableObject.class::cast)
      .toArray(RenderableObject[]::new);

    for (RenderableObject renderedObject : renderedObjects) {

      Vector deltaPosition = renderedObject
        .getTransform()
        .getPosition()
        .subtract(myPosition);

      for (ShapeRenderer2D renderer : renderedObject.getShapeRenderers()) {

        Color color = renderer.getColor();
        RectangularShape shape = renderer.getShape(
          deltaPositionToScreenPosition(deltaPosition, canvasSize),
          getZoom()
        );

        graphics2D.setColor(color);
        graphics2D.fill(shape);

      }

    }
  }
}

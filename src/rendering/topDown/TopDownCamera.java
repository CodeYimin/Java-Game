package rendering.topDown;

import entities.RenderableObject2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;
import rendering.Camera;
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

  private Vector deltaPositionToScreenPosition(
    Vector deltaPosition,
    Dimension canvasSize
  ) {
    return new Vector(
      deltaPosition.getX() * zoom + canvasSize.getWidth() / 2,
      -deltaPosition.getY() * zoom + canvasSize.getHeight() / 2
    );
  }

  @Override
  public void draw(Graphics graphics, Dimension canvasSize) {
    Graphics2D graphics2D = (Graphics2D) graphics;

    Vector myPosition = getTransform().getPosition();

    ArrayList<RenderableObject2D> renderableObjects = getObjectsByType(
      RenderableObject2D.class
    );

    for (RenderableObject2D renderableObject : renderableObjects) {
      Vector deltaPosition = renderableObject
        .getTransform()
        .getPosition()
        .subtract(myPosition);

      for (ShapeRenderer2D renderer : renderableObject.getShapeRenderers()) {
        Color color = renderer.getColor();
        Stroke stroke = renderer.getStroke();
        Shape shape = renderer.getShape(
          deltaPositionToScreenPosition(deltaPosition, canvasSize),
          getZoom()
        );

        graphics2D.setColor(color);
        graphics2D.fill(shape);

        if (stroke != null) {
          graphics2D.setStroke(stroke);
          graphics2D.draw(shape);
        }
      }
    }
  }
}

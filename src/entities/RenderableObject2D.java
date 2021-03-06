package entities;

import java.util.ArrayList;
import rendering.topDown.ShapeRenderer2D;

public class RenderableObject2D extends GameObject {

  private final ArrayList<ShapeRenderer2D> shapeRenderers = new ArrayList<>();

  public ArrayList<ShapeRenderer2D> getShapeRenderers() {
    return this.shapeRenderers;
  }

  public void addShapeRenderer(ShapeRenderer2D shapeRenderer) {
    this.shapeRenderers.add(shapeRenderer);
  }

  public void addShapeRenderers(ArrayList<ShapeRenderer2D> shapeRenderers) {
    this.shapeRenderers.addAll(shapeRenderers);
  }

  public void removeShapeRenderer(ShapeRenderer2D shapeRenderer) {
    this.shapeRenderers.remove(shapeRenderer);
  }

  public void removeAllShapeRenderers() {
    this.shapeRenderers.clear();
  }

  public RenderableObject2D(ArrayList<ShapeRenderer2D> shapeRenderers) {
    this.shapeRenderers.addAll(shapeRenderers);
  }

  public RenderableObject2D() {}
}

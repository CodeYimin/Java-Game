package map;

import java.util.ArrayList;

import entities.RenderedObject;
import rendering.ShapeRenderer2D;
import util.Vector;

public class Tile extends RenderedObject {
  public Tile(Vector position) {
    getTransform().setPosition(position);
  }

  public Tile(Vector position, ShapeRenderer2D renderer) {
    getTransform().setPosition(position);
    addShapeRenderer(renderer);
  }

  public Tile(Vector position, ArrayList<ShapeRenderer2D> renderers) {
    getTransform().setPosition(position);
    addShapeRenderers(renderers);
  }
}

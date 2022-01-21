package map;

import java.awt.Color;
import java.util.ArrayList;

import collision.RectangleCollider2D;
import entities.CollidableObject;
import rendering.RectangleRenderer2D;
import rendering.ShapeRenderer2D;
import util.Vector;

public class Tile extends CollidableObject{
  {
    addShapeRenderer(new RectangleRenderer2D(Vector.one, Color.BLACK, Vector.zero));
    addShapeRenderer(new RectangleRenderer2D(Vector.one.multiply(0.9), Color.MAGENTA, Vector.zero));

    addCollider(new RectangleCollider2D(Vector.one));
  }

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

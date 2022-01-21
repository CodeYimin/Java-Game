package collision;

import entities.CollidableObject;
import entities.GameObject;
import entities.Transform;
import util.Vector;

public interface Collider2D {
  public boolean collidesWith(Collider2D otherCollider, Vector myPosition, Vector otherPosition);
}

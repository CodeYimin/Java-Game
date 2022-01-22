package collision;

import util.Vector;

public interface Collider {
  public boolean collidesWith(Collider otherCollider, Vector myPosition, Vector otherPosition);
}

package collision;

import util.Vector;

public class RectangleCollider implements Collider {
  private Vector size;

  public Vector getSize() {
    return this.size;
  }

  public void setSize(Vector size) {
    this.size = size;
  }

  public RectangleCollider(Vector size) {
    this.size = size;
  }

  public boolean collidesWith(Collider otherCollider, Vector myPosition, Vector otherPosition) {
    if (otherCollider instanceof RectangleCollider) {
      RectangleCollider otherRect = (RectangleCollider) otherCollider;

      double myTopEdge = myPosition.getY() + getSize().getY() / 2;
      double myBotEdge = myTopEdge - getSize().getY();
      double myRightEdge = myPosition.getX() + getSize().getX() / 2;
      double myLeftEdge = myRightEdge - getSize().getX();

      double otherTopEdge = otherPosition.getY() + otherRect.getSize().getY() / 2;
      double otherBotEdge = otherTopEdge - otherRect.getSize().getY();
      double otherRightEdge = otherPosition.getX() + otherRect.getSize().getX() / 2;
      double otherLeftEdge = otherRightEdge - otherRect.getSize().getX();

      return (
        myTopEdge > otherBotEdge && otherTopEdge > myBotEdge &&
        myRightEdge > otherLeftEdge && otherRightEdge > myLeftEdge
      );
    } else {
      return false;
    }
  }
}

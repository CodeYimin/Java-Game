package rendering;

import core.gameBehaviour.GameBehaviourUpdate;
import entities.GameObject;
import java.awt.Dimension;
import java.awt.Graphics;

public abstract class Camera
  extends GameObject
  implements Drawable, GameBehaviourUpdate {

  private GameObject objectFollowing;

  public GameObject getObjectFollowing() {
    return this.objectFollowing;
  }

  public void setObjectFollowing(GameObject objectFollowing) {
    this.objectFollowing = objectFollowing;
  }

  @Override
  public void update(long deltaTime) {
    if (objectFollowing != null) {
      getTransform().setPosition(objectFollowing.getTransform().getPosition());
    }
  }

  public void draw(Graphics graphics, Dimension canvasSize) {}
}

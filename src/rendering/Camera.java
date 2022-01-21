package rendering;

import java.awt.Dimension;
import java.awt.Graphics;

import core.GameBehaviour.GameBehaviourPreUpdate;
import core.GameBehaviour.GameBehaviourUpdate;
import entities.GameObject;

public abstract class Camera extends GameObject implements Drawable, GameBehaviourUpdate {
  private GameObject objectFollowing;

  public GameObject getObjectFollowing() {
    return this.objectFollowing;
  }

  public void setObjectFollowing(GameObject objectFollowing) {
    this.objectFollowing = objectFollowing;
  }

  @Override
  public void update() {
    if (objectFollowing != null) {
      getTransform().setPosition(objectFollowing.getTransform().getPosition());
    }
  }

  public void draw(Graphics graphics, Dimension canvasSize) {

  }
}

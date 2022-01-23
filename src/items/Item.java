package items;

import entities.GameObject;
import entities.RenderableObject2D;

public abstract class Item extends RenderableObject2D {

  private GameObject parentObject = null;

  public Item(GameObject parentObject) {
    this.parentObject = parentObject;
  }

  public Item() {}

  public GameObject getParentObject() {
    return this.parentObject;
  }

  public void setParentObject(GameObject parentObject) {
    this.parentObject = parentObject;
  }
}

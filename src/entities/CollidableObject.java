package entities;

import collision.Collider;
import collision.CollisionManager;
import java.util.ArrayList;
import util.Vector;

public class CollidableObject extends RenderableObject2D {

  private final ArrayList<Collider> colliders = new ArrayList<>();

  public ArrayList<Collider> getColliders() {
    return this.colliders;
  }

  public void addCollider(Collider collider) {
    this.colliders.add(collider);
  }

  public void addColliders(ArrayList<Collider> colliders) {
    this.colliders.addAll(colliders);
  }

  public void removeCollider(Collider collider) {
    this.colliders.remove(collider);
  }

  public void removeAllColliders() {
    this.colliders.clear();
  }

  public CollidableObject(ArrayList<Collider> colliders) {
    this.colliders.addAll(colliders);
  }

  public CollidableObject() {}

  public void onCollision(CollidableObject other) {}

  public boolean willCollide(Vector position) {
    CollidableObject testObject = new CollidableObject(getColliders());
    testObject.getTransform().setPosition(position);

    ArrayList<CollidableObject> otherCollidable = getObjectsByType(
      CollidableObject.class
    );
    otherCollidable.remove(this);
    otherCollidable.remove(testObject);

    return CollisionManager.testCollision(testObject, otherCollidable);
  }
}

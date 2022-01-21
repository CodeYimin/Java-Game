package entities;

import java.util.ArrayList;
import java.util.stream.Collectors;

import collision.Collider2D;
import collision.CollisionManager;
import core.Time;
import util.Vector;

public class CollidableObject extends RenderableObject {
  private final ArrayList<Collider2D> colliders = new ArrayList<>();

  public ArrayList<Collider2D> getColliders() {
    return this.colliders;
  }

  public void addCollider(Collider2D collider) {
    this.colliders.add(collider);
  }

  public void addColliders(ArrayList<Collider2D> colliders) {
    this.colliders.addAll(colliders);
  }

  public void removeCollider(Collider2D collider) {
    this.colliders.remove(collider);
  }

  public CollidableObject(ArrayList<Collider2D> colliders) {
    this.colliders.addAll(colliders);
  }

  public CollidableObject() {

  }

  public void onCollision(CollidableObject other) {}

  public boolean willCollide(Vector position) {
    CollidableObject testObject = new CollidableObject(getColliders());
    testObject.getTransform().setPosition(position);

    ArrayList<CollidableObject> otherCollidable = getObjectsByType(CollidableObject.class);
    otherCollidable.remove(this);
    otherCollidable.remove(testObject);

    return CollisionManager.testCollision(testObject, otherCollidable);
  }
}

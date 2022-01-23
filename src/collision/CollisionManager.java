package collision;

import core.gameBehaviour.GameBehaviourPreUpdate;
import entities.CollidableObject;
import entities.Object;
import java.util.ArrayList;
import util.ArrayListUtil;
import util.Vector;

public class CollisionManager extends Object implements GameBehaviourPreUpdate {

  public static boolean testCollision(
    CollidableObject testObject,
    ArrayList<CollidableObject> otherObjects
  ) {
    for (CollidableObject otherObject : otherObjects) {
      if (testCollision(testObject, otherObject)) {
        return true;
      }
    }
    return false;
  }

  public static boolean testCollision(
    CollidableObject object1,
    CollidableObject object2
  ) {
    Vector pos1 = object1.getTransform().getPosition();
    Vector pos2 = object2.getTransform().getPosition();

    for (Collider collider1 : object1.getColliders()) {
      for (Collider collider2 : object2.getColliders()) {
        if (
          collider1.collidesWith(collider2, pos1, pos2) ||
          collider2.collidesWith(collider1, pos2, pos1)
        ) {
          return true;
        }
      }
    }
    return false;
  }

  private void detectCollision(ArrayList<CollidableObject> objects) {
    ArrayList<ArrayList<CollidableObject>> objectCombinations = ArrayListUtil.getAllUniquePairs(
      objects
    );
    for (ArrayList<CollidableObject> objectCombination : objectCombinations) {
      CollidableObject objectA = objectCombination.get(0);
      CollidableObject objectB = objectCombination.get(1);

      if (testCollision(objectA, objectB)) {
        objectA.onCollision(objectB);
        objectB.onCollision(objectA);
      }
    }
  }

  public void preUpdate(long deltaTime) {
    detectCollision(getObjectsByType(CollidableObject.class));
  }
}

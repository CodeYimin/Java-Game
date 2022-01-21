package collision;

import java.util.ArrayList;
import java.util.stream.Collectors;

import core.GameBehaviour.GameBehaviourPreUpdate;
import entities.CollidableObject;
import entities.Object;
import util.ArrayListUtil;

public class CollisionManager extends Object implements GameBehaviourPreUpdate {

  public static boolean testCollision(CollidableObject testObject, ArrayList<CollidableObject> otherObjects) {
    for (CollidableObject otherObject : otherObjects) {
      if (testCollision(testObject, otherObject)) {
        return true;
      }
    }
    return false;
  }

  public static boolean testCollision(CollidableObject object1, CollidableObject object2) {
    for (Collider2D collider1 : object1.getColliders()) {
      for (Collider2D collider2 : object2.getColliders()) {
        if (collider1.collidesWith(collider2, object1.getTransform().getPosition(), object2.getTransform().getPosition())) {
          return true;
        }
      }
    }
    return false;
  }

  private void detectCollision(ArrayList<CollidableObject> objects) {
    ArrayList<ArrayList<CollidableObject>> objectCombinations = ArrayListUtil.getAllUniquePairs(objects);
    for (ArrayList<CollidableObject> objectCombination : objectCombinations) {
      CollidableObject objectA = objectCombination.get(0);
      CollidableObject objectB = objectCombination.get(1);

      if (testCollision(objectA, objectB)) {
        objectA.onCollision(objectB);
        objectB.onCollision(objectA);
      }
    }
  }

  public void preUpdate() {
    detectCollision(getObjectsByType(CollidableObject.class));
  }
  
}

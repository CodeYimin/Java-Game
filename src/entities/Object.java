package entities;

import core.gameBehaviour.GameBehaviourInstantiate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Object {

  private static final ArrayList<Object> objects = new ArrayList<>();

  public static final <T> ArrayList<T> getObjectsByType(Class<T> type) {
    ArrayList<T> objectsByType = Object
      .getObjects()
      .stream()
      .filter(type::isInstance)
      .map(type::cast)
      .collect(Collectors.toCollection(ArrayList::new));

    return objectsByType;
  }

  public static ArrayList<Object> getObjects() {
    return objects;
  }

  public static void instantiate(Object object) {
    objects.add(object);
    if (object instanceof GameBehaviourInstantiate) {
      GameBehaviourInstantiate.class.cast(object).instantiate();
    }
  }

  public static void destroy(Object object) {
    objects.remove(object);
  }

  public static void destroyAll() {
    objects.clear();
  }
}

package entities;

import java.util.ArrayList;

public abstract class Object {
  private static final ArrayList<Object> objects = new ArrayList<>();

  public static ArrayList<Object> getObjects() {
    return objects;
  }

  public static void instantiate(Object object) {
    objects.add(object);
  }

  public static void destroy(Object object) {
    objects.remove(object);
  }

  public static void destroyAll() {
    objects.clear();
  }
}

package util;

public class ArrayUtil {
  public static boolean[] toPrimitiveArray(Boolean[] objectArray) {
    boolean[] primitives = new boolean[objectArray.length];
    int index = 0;
    for (Boolean object : objectArray) {
      primitives[index++] = object;
    }
    return primitives;
  }

  public static boolean[][] toPrimitiveArray(Boolean[][] object2DArray) {
    boolean[][] primitives = new boolean[object2DArray.length][];
    int index = 0;
    for (Boolean[] objectArray : object2DArray) {
      primitives[index++] = toPrimitiveArray(objectArray);
    }
    return primitives;
  }
}

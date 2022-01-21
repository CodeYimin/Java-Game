package util;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListUtil {
  public static <T> ArrayList<ArrayList<T>> getAllUniquePairs(ArrayList<T> list) {
    final ArrayList<ArrayList<T>> pairs = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      for (int j = i + 1; j < list.size(); j++) {
        pairs.add(new ArrayList<>(Arrays.asList(list.get(i), list.get(j))));
      }
    }
    return pairs;
  }
}

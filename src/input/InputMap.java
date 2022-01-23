package input;

import java.util.HashMap;

public class InputMap<T> extends HashMap<Integer, T> {

  public InputMap<T> set(int keyCode, T value) {
    put(keyCode, value);
    return this;
  }
}

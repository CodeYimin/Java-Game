package input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class InputManager extends KeyAdapter {

  private HashMap<Integer, Boolean> keyCodesHeld = new HashMap<>();
  private ArrayList<Consumer<HashMap<Integer, Boolean>>> keyUpdateListeners = new ArrayList<>();
  private HashMap<String, Object> valueByBindName = new HashMap<>();
  private HashMap<String, ArrayList<?>> activatedValuesByBindName = new HashMap<>();

  @Override
  public void keyPressed(KeyEvent event) {
    keyCodesHeld.put(event.getKeyCode(), true);
    callKeyUpdateListeners();
  }

  @Override
  public void keyReleased(KeyEvent event) {
    keyCodesHeld.remove(event.getKeyCode());
    callKeyUpdateListeners();
  }

  private void callKeyUpdateListeners() {
    keyUpdateListeners.stream().forEach((listener) -> {
      listener.accept(keyCodesHeld);
    });
  }

  public void addKeyUpdateListener(Consumer<HashMap<Integer, Boolean>> listener) {
    keyUpdateListeners.add(listener);
  }

  public <T> T getValueByBindName(String name, Class<T> type) {
    if (!valueByBindName.keySet().contains(name)) {
      throw new Error("Value bind with name " + name + " does not exist");
    }

    Object uncheckedValue = valueByBindName.get(name);
    if (!type.isInstance(uncheckedValue)) {
      throw new Error("Value bind with name " + name + " and type " + type.toString() + " does not exist"); 
    }

    @SuppressWarnings("unchecked")
    T checkedValue = (T) uncheckedValue;
    return checkedValue;
  }

  public <T> ArrayList<T> getActivatedValuesByBindName(String name, Class<T> type) {
    if (!activatedValuesByBindName.keySet().contains(name)) {
      throw new Error("Composite bind with name " + name + " does not exist");
    }

    ArrayList<?> uncheckedValues = activatedValuesByBindName.get(name);
    if (!uncheckedValues.stream().allMatch(type::isInstance)) {
      throw new Error("Composite bind with name " + name + " and type " + type.toString() + " does not exist"); 
    }

    @SuppressWarnings("unchecked")
    ArrayList<T> checkedValues = (ArrayList<T>) uncheckedValues;
    return checkedValues;
  }

  public <T> void addCompositeBinding(String name, InputMap<T> inputMap) {
    if (activatedValuesByBindName.keySet().contains(name)) {
      throw new Error("Composite binding with name " + name + " already exists.");
    }

    final ArrayList<T> emptyValues = new ArrayList<>();
    activatedValuesByBindName.put(name, emptyValues);

    addKeyUpdateListener((keyCodesHeld) -> {
      final ArrayList<T> activatedInputValues = keyCodesHeld
        .keySet()
        .stream()
        .filter(inputMap::containsKey)
        .map(inputMap::get)
        .collect(Collectors.toCollection(ArrayList::new));

      activatedValuesByBindName.put(name, activatedInputValues);
    });
  }

  public <T> void addValueBinding(String name, int keyCode, T activatedValue, T defaultValue) {
    if (valueByBindName.keySet().contains(name)) {
      throw new Error("Value binding with name " + name + " already exists.");
    }

    valueByBindName.put(name, defaultValue);

    addKeyUpdateListener((keyCodesHeld) -> {
      if (keyCodesHeld.containsKey(keyCode)) {
        valueByBindName.put(name, activatedValue);
      } else {
        valueByBindName.put(name, defaultValue);
      }
    });
  }

}

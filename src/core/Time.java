package core;

import core.GameBehaviour.GameBehaviourPreUpdate;
import entities.Object;

public class Time extends Object implements GameBehaviourPreUpdate {
  private static long lastUpdateTime;
  private static int deltaTime;

  private static Time singleInstance = null;
  public static Time getInstance() {
    if (singleInstance == null) {
      singleInstance = new Time();
    }

    return singleInstance;
  }

  public static long getDeltaTime() {
    return deltaTime;
  }

  public static void reset() {
    lastUpdateTime = 0;
    deltaTime = 0;
  }

  public void preUpdate() {
    if (lastUpdateTime != 0) {
      deltaTime = (int) (System.nanoTime() - lastUpdateTime) / 1000 / 1000;
    }
    lastUpdateTime = System.nanoTime();
  }
}

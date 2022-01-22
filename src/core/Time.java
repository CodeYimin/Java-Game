package core;


public class Time {
  private long lastUpdateTime;
  private int deltaTime;

  public long getDeltaTime() {
    return deltaTime;
  }

  public void reset() {
    lastUpdateTime = 0;
    deltaTime = 0;
  }

  public void updateDeltaTime() {
    if (lastUpdateTime != 0) {
      deltaTime = (int) (System.nanoTime() - lastUpdateTime) / 1000 / 1000;
    }
    lastUpdateTime = System.nanoTime();
  }
}

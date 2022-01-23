package util.rayCasting;

public class RayCastHit<T> {

  private double distance;
  private T objectHit;

  public RayCastHit(double distance, T objectHit) {
    this.distance = distance;
    this.objectHit = objectHit;
  }

  public double getDistance() {
    return this.distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public T getObjectHit() {
    return this.objectHit;
  }

  public void setObjectHit(T objectHit) {
    this.objectHit = objectHit;
  }
}

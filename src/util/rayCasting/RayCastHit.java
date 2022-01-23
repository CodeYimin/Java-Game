package util.rayCasting;

import util.Vector;

public class RayCastHit<T> {

  private double distance;
  private T object;
  private RayCastSide side;
  private Vector position;

  public RayCastHit(
    double distance,
    T object,
    RayCastSide side,
    Vector position
  ) {
    this.distance = distance;
    this.object = object;
    this.side = side;
    this.position = position;
  }

  public T getObject() {
    return this.object;
  }

  public void setObject(T object) {
    this.object = object;
  }

  public RayCastSide getSide() {
    return this.side;
  }

  public void setSide(RayCastSide side) {
    this.side = side;
  }

  public Vector getPosition() {
    return this.position;
  }

  public void setPosition(Vector position) {
    this.position = position;
  }

  public double getDistance() {
    return this.distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public void setObjectHit(T objectHit) {
    this.object = objectHit;
  }
}

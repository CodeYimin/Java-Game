package entities;

import util.Vector;

public class Transform {

  private Vector position;
  private double angle;

  public Transform(Vector position, double angle) {
    this.position = position;
    this.angle = angle;
  }

  public Transform(Vector position) {
    this.position = position;
    this.angle = 0;
  }

  public Transform() {
    this.position = new Vector();
    this.angle = 0;
  }

  public Vector getPosition() {
    return this.position;
  }

  public void setPosition(Vector position) {
    this.position = position;
  }

  public double getAngle() {
    return this.angle;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  @Override
  public String toString() {
    return (
      "{" +
      " position='" +
      getPosition() +
      "'" +
      ", angle='" +
      getAngle() +
      "'" +
      "}"
    );
  }

  public void translate(Vector translation) {
    this.setPosition(getPosition().add(translation));
  }

  public void rotate(double degrees) {
    this.setAngle(getAngle() + degrees);
  }
}

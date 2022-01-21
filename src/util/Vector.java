package util;

import java.awt.Dimension;
import java.awt.Point;

public class Vector {
  public static final Vector zero = new Vector();
  public static final Vector up = new Vector(0, 1);
  public static final Vector right = new Vector(1, 0);
  public static final Vector down = new Vector(0, -1);
  public static final Vector left = new Vector(-1, 0);
  public static final Vector one = new Vector(1, 1);

  public static final Vector from(Point point) {
    return new Vector(point.getX(), point.getY());
  }

  private double x;
  private double y;

  {
    setX(0);
    setY(0);
  }

  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector() { }

  public double getX() {
    return this.x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return this.y;
  }

  public void setY(double y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "{" +
      " x='" + getX() + "'" +
      ", y='" + getY() + "'" +
      "}";
  }

  public void set(Vector vector) {
    this.x = vector.getX();
    this.y = vector.getY();
  }

  public Vector add(Vector vector) {
    return new Vector(getX() + vector.x, getY() + vector.y);
  }

  public Vector add(int changeX, int changeY) {
    return new Vector(getX() + changeX, getY() + changeY);
  }

  public Vector add(double changeX, double changeY) {
    return new Vector(getX() + changeX, getY() + changeY);
  }

  public Vector subtract(Vector vector) {
    return new Vector(getX() - vector.x, getY() - vector.y);
  }

  public Vector multiply(int amount) {
    return new Vector(getX() * amount, getY() * amount);
  }

  public Vector multiply(float amount) {
    return new Vector(getX() * amount, getY() * amount);
  }

  public Vector multiply(double amount) {
    return new Vector(getX() * amount, getY() * amount);
  }

  public Vector multiply(double amountX, double amountY) {
    return new Vector(getX() * amountX, getY() * amountY);
  }

  public Vector divide(int amount) {
    return new Vector(getX() / amount, getY() / amount);
  }

  public Vector divide(float amount) {
    return new Vector(getX() / amount, getY() / amount);
  }

  public Vector divide(double amount) {
    return new Vector(getX() / amount, getY() / amount);
  }

  public Vector normalized() {
    final double hypotenuse = Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));

    if (hypotenuse == 0) {
      return this;
    }

    final Vector normalizedVector = this.divide(hypotenuse);
    return normalizedVector;
  }

  public Dimension toDimension() {
    return new Dimension((int) getX(), (int) getY());
  }

  public Point toPoint() {
    return new Point((int) getX(), (int) getY());
  }

}

package util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Vector {

  public static final Vector ZERO = new Vector();
  public static final Vector UP = new Vector(0, 1);
  public static final Vector RIGHT = new Vector(1, 0);
  public static final Vector DOWN = new Vector(0, -1);
  public static final Vector LEFT = new Vector(-1, 0);
  public static final Vector ONE = new Vector(1, 1);

  public static Vector fromPoint(Point point) {
    return new Vector(point.getX(), point.getY());
  }

  public static Vector fromRadians(double radians) {
    return new Vector(Math.cos(radians), Math.sin(radians));
  }

  public static Vector fromMathAngle(double angle) {
    double radians = Math.toRadians(angle);
    return fromRadians(radians);
  }

  public static Vector fromGameAngle(double angle) {
    return fromMathAngle(90 - angle);
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

  public Vector() {}

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
    return "{" + " x='" + getX() + "'" + ", y='" + getY() + "'" + "}";
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

  public double getHypotenuse() {
    return MathUtil.hypotenuse(getX(), getY());
  }

  public Vector normalized() {
    double hypotenuse = getHypotenuse();

    if (hypotenuse == 0) {
      return this;
    }

    final Vector normalizedVector = this.divide(hypotenuse);
    return normalizedVector;
  }

  public Dimension toDimension() {
    return new Dimension((int) getX(), (int) getY());
  }

  public Point2D toPoint2D() {
    return new Point2D.Double(getX(), getY());
  }

  public double toRadians() {
    return Math.atan2(getY(), getX());
  }

  public double toMathAngle() {
    return Math.toDegrees(toRadians());
  }

  public double toGameAngle() {
    return 90 - toMathAngle();
  }

  public Vector rotateRadians(double radians) {
    return Vector.fromRadians(toRadians() + radians).multiply(getHypotenuse());
  }

  public Vector rotateGameDegrees(double degrees) {
    return Vector
      .fromGameAngle(toGameAngle() + degrees)
      .multiply(getHypotenuse());
  }

  public Vector rotateMathDegrees(double degrees) {
    return Vector
      .fromMathAngle(toMathAngle() + degrees)
      .multiply(getHypotenuse());
  }
}

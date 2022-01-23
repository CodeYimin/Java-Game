package util;

public class IntVector {

  private int x;
  private int y;

  {
    setX(0);
    setY(0);
  }

  public IntVector(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public IntVector() {}

  public int getX() {
    return this.x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return this.y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void addX(int x) {
    this.x = this.x + x;
  }

  public void addY(int y) {
    this.y = this.y + y;
  }

  @Override
  public String toString() {
    return "{" + " x='" + getX() + "'" + ", y='" + getY() + "'" + "}";
  }
}

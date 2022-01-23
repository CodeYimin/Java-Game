package util;

public class MathUtil {

  public static double hypotenuse(double x, double y) {
    return Math.sqrt(x * x + y * y);
  }

  public static double getDecimal(double number) {
    return number - (int) number;
  }
}

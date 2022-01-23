package util.rayCasting;

import java.util.function.Function;
import util.IntVector;
import util.MathUtil;
import util.Vector;

public class RayCast {

  public static <T> RayCastHit<T> rayCast(
    Vector startingPosition,
    Vector rayDirection,
    T[][] tileMap,
    Function<T, Boolean> isSolid
  ) {
    double yPerX = rayDirection.getY() / rayDirection.getX();
    double xPerY = rayDirection.getX() / rayDirection.getY();

    double distPerX = MathUtil.hypotenuse(1, yPerX);
    double distPerY = MathUtil.hypotenuse(1, xPerY);

    IntVector rayCastTileMapPosition = new IntVector(
      (int) startingPosition.getX(),
      (int) startingPosition.getY()
    );

    IntVector stepDirection = new IntVector(
      rayDirection.getX() > 0 ? 1 : -1,
      rayDirection.getY() > 0 ? 1 : -1
    );

    // Checks for vertical sides
    double xCasterDistance =
      (
        rayDirection.getX() > 0
          ? 1 - MathUtil.getDecimal(startingPosition.getX())
          : MathUtil.getDecimal(startingPosition.getX())
      ) *
      distPerX;
    // Check for horizontal sides
    double yCasterDistance =
      (
        rayDirection.getY() > 0
          ? 1 - MathUtil.getDecimal(startingPosition.getY())
          : MathUtil.getDecimal(startingPosition.getY())
      ) *
      distPerY;

    int currentCaster = 0;

    T hitObject = null;
    while (hitObject == null) {
      // Cast whichever ray was left behind by the other ray
      if (xCasterDistance < yCasterDistance) {
        currentCaster = 0;
        xCasterDistance += distPerX;
        rayCastTileMapPosition.addX(stepDirection.getX());
      } else {
        currentCaster = 1;
        yCasterDistance += distPerY;
        rayCastTileMapPosition.addY(stepDirection.getY());
      }

      T currentTile =
        tileMap[rayCastTileMapPosition.getY()][rayCastTileMapPosition.getX()];

      if (isSolid.apply(currentTile)) {
        hitObject = currentTile;
      }
    }

    double rayCastDistance = currentCaster == 0
      ? xCasterDistance - distPerX
      : yCasterDistance - distPerY;

    return new RayCastHit<T>(rayCastDistance, hitObject);
  }
}

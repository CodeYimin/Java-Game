package collision;

import java.util.Arrays;

import util.Vector;

public class TileMapCollider implements Collider {
  public TileMapCollider(boolean[][] tileMap, double tileSize) {
    this.tileMap = tileMap;
    this.tileSize = tileSize;
  }
  
  public boolean[][] getTileMap() {
    return this.tileMap;
  }

  public double getTileSize() {
    return tileSize;
  }

  private boolean[][] tileMap;
  private double tileSize;

  private boolean positionInTile(Vector myPosition, Vector positionToCheck) {
    boolean[][] tileMap = getTileMap();
    int tileMapHeight = tileMap.length;
    int tileMapWidth = tileMap[0].length;

    double leftX = myPosition.getX() - tileMapWidth * tileSize / 2;
    double topY = myPosition.getY() + tileMapHeight * tileSize / 2;

    int tileMapX = (int) Math.floor((positionToCheck.getX() - leftX) / tileSize);
    int tileMapY = (int) Math.floor((topY - positionToCheck.getY()) / tileSize);

    if (
      (tileMapY < 0 || tileMapY >= tileMapHeight) ||
      (tileMapX < 0 || tileMapX >= tileMapWidth)
    ) {
      return false;
    }

    return tileMap[tileMapY][tileMapX];
  }

  public boolean collidesWith(Collider other, Vector myPosition, Vector otherPosition) {
    if (other instanceof RectangleCollider) {
      RectangleCollider otherRect = (RectangleCollider) other;

      Vector otherSize = otherRect.getSize();

      Vector otherBotLeft = otherPosition.subtract(otherSize.divide(2));
      Vector otherBotRight = otherBotLeft.add(Vector.right.multiply(otherSize.getX()));
      Vector otherTopRight = otherPosition.add(otherSize.divide(2));
      Vector otherTopLeft = otherTopRight.subtract(Vector.right.multiply(otherSize.getX()));

      Vector[] positionsToCheck = new Vector[]{ otherBotLeft, otherBotRight, otherTopRight, otherTopLeft };

      return Arrays.stream(positionsToCheck)
        .anyMatch((positionToCheck) -> positionInTile(myPosition, positionToCheck));
    }

    return false;
  }
}

package rendering.rayCast;

import entities.GameObject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.stream.IntStream;
import map.TileMap;
import rendering.Camera;
import util.Vector;
import util.rayCasting.RayCast;
import util.rayCasting.RayCastHit;

public class RayCastingCamera extends Camera {

  private TileMap tileMap;

  public RayCastingCamera(TileMap tileMap) {
    this.tileMap = tileMap;
  }

  public RayCastingCamera(TileMap tileMap, GameObject following) {
    this.tileMap = tileMap;
    setObjectFollowing(following);
  }

  public TileMap getTileMap() {
    return this.tileMap;
  }

  @Override
  public void update(long deltaTime) {
    super.update(deltaTime);
    if (getObjectFollowing() != null) {
      setTransform(getObjectFollowing().getTransform());
    }
  }

  @Override
  public void draw(Graphics graphics, Dimension canvasSize) {
    Graphics2D graphics2D = (Graphics2D) graphics;

    Vector myTileMapPosition = getTileMap()
      .toTileMapPosition(getTransform().getPosition());

    double myAngle = getTransform().getAngle();
    Vector myDirection = Vector.fromGameDegrees(myAngle);

    int rayAmount = 300;
    Vector cameraPlaneDirection = myDirection.rotateGameDegrees(90);

    for (int ray = 0; ray < rayAmount; ray++) {
      double cameraPlaneX = (double) ray / (rayAmount - 1) * 2 - 1;
      Vector rayDirection = myDirection.add(
        cameraPlaneDirection.multiply(cameraPlaneX)
      );

      RayCastHit<Integer> rayCastHit = RayCast.rayCast(
        myTileMapPosition,
        rayDirection.multiply(1, -1),
        Arrays
          .stream(getTileMap().getLayout())
          .map(row -> IntStream.of(row).boxed().toArray(Integer[]::new))
          .toArray(Integer[][]::new),
        tile -> tile != 0
      );

      double rayCastDistance = rayCastHit.getDistance();

      double verticalLineHeight =
        canvasSize.getHeight() /
        (
          Math.cos(rayDirection.toRadians() - myDirection.toRadians()) *
          rayCastDistance
        );

      Rectangle2D verticalLine = new Rectangle2D.Double(
        (double) ray / rayAmount * canvasSize.getWidth(),
        canvasSize.getHeight() / 2 - verticalLineHeight / 2,
        canvasSize.getWidth() / rayAmount,
        verticalLineHeight
      );

      graphics2D.setColor(Color.BLACK);
      graphics2D.fill(verticalLine);
    }
  }
}

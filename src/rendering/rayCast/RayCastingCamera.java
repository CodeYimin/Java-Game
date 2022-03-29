package rendering.rayCast;

import entities.GameObject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.IntStream;
import map.MapLayouts;
import map.TileMap;
import rendering.Camera;
import util.MathUtil;
import util.Vector;
import util.rayCasting.RayCast;
import util.rayCasting.RayCastHit;
import util.rayCasting.RayCastSide;

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

    Vector myTileMapDecimalPosition = getTileMap()
      .toTileMapDecimalPosition(getTransform().getPosition());

    double myAngle = getTransform().getAngle();
    Vector myDirection = Vector.fromGameAngle(myAngle);

    int rayAmount = canvasSize.width / 2;
    Vector cameraPlane = myDirection.rotateGameDegrees(90).multiply(1);

    // Draw a vertical line on the screen for each ray casted
    for (int ray = 0; ray < rayAmount; ray++) {
      double cameraPlaneX = (double) ray / (rayAmount - 1) * 2 - 1;
      Vector rayDirection = myDirection.add(cameraPlane.multiply(cameraPlaneX));

      RayCastHit<Integer> rayCastHit = RayCast.rayCast(
        myTileMapDecimalPosition,
        // Invert Y because 2D arrays' Y axis are upside down
        rayDirection.multiply(1, -1),
        // int[][] to Integer[][]
        Arrays
          .stream(getTileMap().getLayout())
          .map(row -> IntStream.of(row).boxed().toArray(Integer[]::new))
          .toArray(Integer[][]::new),
        // Stop the raycaster once a tile that's not 0 is hit
        tile -> tile != 0
      );

      Vector hitPosition = rayCastHit.getPosition();
      RayCastSide hitSide = rayCastHit.getSide();
      int hitTile = rayCastHit.getObject();

      double hitDistance = rayCastHit.getDistance();
      double perpendicularRayCastDistance =
        Math.cos(rayDirection.toRadians() - myDirection.toRadians()) *
        hitDistance;

      double verticalLineHeight =
        canvasSize.getHeight() / perpendicularRayCastDistance / 1.5;

      double hitPositionDecimal;
      if (hitSide == RayCastSide.HORIZONTAL) {
        hitPositionDecimal = MathUtil.getDecimal(hitPosition.getX());
      } else {
        hitPositionDecimal = MathUtil.getDecimal(hitPosition.getY());
      }

      BufferedImage wallSprite = MapLayouts.getSpritesByInt().get(hitTile);
      int textureSize = wallSprite.getWidth();
      double pixelHeight = verticalLineHeight / textureSize;

      // Draw each pixel of the vertical line
      for (int row = 0; row < textureSize; row++) {
        Color color = new Color(
          wallSprite.getRGB((int) (hitPositionDecimal / 1 * textureSize), row)
        );

        if (hitSide == RayCastSide.HORIZONTAL) {
          color = color.darker();
        }

        double halfScreenHeight = canvasSize.getHeight() / 2;
        double halfLineHeight = verticalLineHeight / 2;

        Rectangle2D spritePixel = new Rectangle2D.Double(
          (double) ray / rayAmount * canvasSize.getWidth(),
          halfScreenHeight - halfLineHeight + (pixelHeight * row),
          canvasSize.getWidth() / rayAmount,
          pixelHeight
        );

        graphics2D.setColor(color);
        graphics2D.fill(spritePixel);
      }
      // Rectangle2D verticalLine = new Rectangle2D.Double(
      //   (double) ray / rayAmount * canvasSize.getWidth(),
      //   canvasSize.getHeight() / 2 - verticalLineHeight / 2,
      //   canvasSize.getWidth() / rayAmount,
      //   verticalLineHeight
      // );

      // graphics2D.setColor(color);
      // graphics2D.fill(verticalLine);
    }
  }
}

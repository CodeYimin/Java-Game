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

    Vector myTileMapPosition = getTileMap()
      .toTileMapPosition(getTransform().getPosition());

    double myAngle = getTransform().getAngle();
    Vector myDirection = Vector.fromGameDegrees(myAngle);

    int rayAmount = canvasSize.width / 2;
    Vector cameraPlane = myDirection.rotateGameDegrees(90).multiply(1);

    for (int ray = 0; ray < rayAmount; ray++) {
      double cameraPlaneX = (double) ray / (rayAmount - 1) * 2 - 1;
      Vector rayDirection = myDirection.add(cameraPlane.multiply(cameraPlaneX));

      RayCastHit<Integer> rayCastHit = RayCast.rayCast(
        myTileMapPosition,
        rayDirection.multiply(1, -1),
        Arrays
          .stream(getTileMap().getLayout())
          .map(row -> IntStream.of(row).boxed().toArray(Integer[]::new))
          .toArray(Integer[][]::new),
        tile -> tile != 0
      );

      Vector hitPosition = rayCastHit.getPosition();
      RayCastSide hitSide = rayCastHit.getSide();
      int hitTile = rayCastHit.getObject();

      BufferedImage wallSprite = MapLayouts.getSpritesByInt().get(hitTile);

      double hitDistance = rayCastHit.getDistance();
      double perpendicularRayCastDistance =
        Math.cos(rayDirection.toRadians() - myDirection.toRadians()) *
        hitDistance;

      double screenColumnHeight =
        canvasSize.getHeight() / perpendicularRayCastDistance / 1.5;

      double hitWallPositionDecimal;
      if (hitSide == RayCastSide.HORIZONTAL) {
        hitWallPositionDecimal = MathUtil.getDecimal(hitPosition.getX());
      } else {
        hitWallPositionDecimal = MathUtil.getDecimal(hitPosition.getY());
      }

      int textureSize = wallSprite.getWidth();
      double texturePixelScreenHeight = screenColumnHeight / textureSize;

      // Draw each texture pixel of the column
      for (int row = 0; row < textureSize; row++) {
        Color color = new Color(
          wallSprite.getRGB(
            (int) (hitWallPositionDecimal / 1 * textureSize),
            row
          )
        );

        if (hitSide == RayCastSide.HORIZONTAL) {
          color = color.darker();
        }

        double halfScreenHeight = canvasSize.getHeight() / 2;
        double halfColumnHeight = screenColumnHeight / 2;

        Rectangle2D spritePixel = new Rectangle2D.Double(
          (double) ray / rayAmount * canvasSize.getWidth(),
          halfScreenHeight -
          halfColumnHeight +
          (texturePixelScreenHeight * row),
          canvasSize.getWidth() / rayAmount,
          texturePixelScreenHeight
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

package items;

import core.gameBehaviour.GameBehaviourInstantiate;
import core.gameBehaviour.GameBehaviourUpdate;
import entities.GameObject;
import java.awt.Color;
import java.util.Arrays;
import java.util.stream.IntStream;
import map.TileMap;
import rendering.topDown.LineRenderer2D;
import util.Vector;
import util.rayCasting.RayCast;
import util.rayCasting.RayCastHit;

public class RayCastVisualizer
  extends Item
  implements GameBehaviourUpdate, GameBehaviourInstantiate {

  TileMap tileMap;

  public RayCastVisualizer(GameObject parentObject, TileMap tileMap) {
    super(parentObject);
    this.tileMap = tileMap;
  }

  public TileMap getTileMap() {
    return this.tileMap;
  }

  public void setTileMap(TileMap tileMap) {
    this.tileMap = tileMap;
  }

  @Override
  public void instantiate() {}

  @Override
  public void update(long updateTime) {
    setTransform(getParentObject().getTransform());
    removeAllShapeRenderers();

    Vector myTileMapPosition = getTileMap()
      .toTileMapPosition(getTransform().getPosition());

    double myAngle = getTransform().getAngle();

    double fov = 1;
    int rayAmount = 2;

    for (int ray = 0; ray < rayAmount; ray++) {
      double rayAngle =
        myAngle + (double) ray / (rayAmount - 1) * fov - fov / 2;
      Vector rayDirection = Vector.fromGameDegrees(rayAngle);

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

      addShapeRenderer(
        new LineRenderer2D(
          rayCastDistance,
          rayDirection,
          Color.green,
          1,
          Vector.ZERO
        )
      );
    }
  }
}

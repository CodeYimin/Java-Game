package map;

import java.awt.Color;
import java.util.Arrays;

import collision.TileMapCollider;
import entities.CollidableObject;
import rendering.topDown.RectangleRenderer2D;
import util.ArrayUtil;
import util.Vector;

public class TileMap extends CollidableObject {
  private int[][] layout;
  private double tileSize = 1;

  {
    setLayout(MapLayouts.getMainLayout());
  }

  public TileMap() {}

  public TileMap(int[][] layout) {
    setLayout(layout);
  }

  public int[][] getLayout() {
    return this.layout;
  }

  public void setLayout(int[][] layout) {
    this.layout = layout;
    removeAllShapeRenderers();
    removeAllColliders();

    Boolean[][] booleanTileMap = Arrays.stream(layout)
      .map((row) -> Arrays.stream(row)
        .mapToObj((value) -> value != 0)
        .toArray(Boolean[]::new)
      )
      .toArray(Boolean[][]::new);
    
    addCollider(new TileMapCollider(ArrayUtil.toPrimitiveArray(booleanTileMap), getTileSize()));
    createTileRenderers();
  }

  public double getTileSize() {
    return this.tileSize;
  }

  public void setTileSize(double tileSize) {
    this.tileSize = tileSize;
  }

  private void createTileRenderers() {
    int height = layout.length;
    for (int y = 0; y < height; y++) {
      int width = layout[y].length;
      for (int x = 0; x < width; x++) {
        if (layout[y][x] == 0) {
          continue;
        }
        
        Vector tileOffset = new Vector(
          x - (double) width / 2 + 0.5,
          -y + (double) height / 2 - 0.5
        );
        
        Vector tileWorldOffset = tileOffset.multiply(getTileSize());

        addShapeRenderer(new RectangleRenderer2D(
          Vector.one.multiply(getTileSize()), 
          Color.PINK, 
          tileWorldOffset
        ));
      }
    }
  }
}

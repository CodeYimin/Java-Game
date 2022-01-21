package map;

import java.awt.Color;

import entities.Object;
import rendering.RectangleRenderer2D;
import util.Vector;

public class MapRenderer extends Object {
  private int[][] mapLayout = MapLayouts.getMainLayout();

  public MapRenderer() {
    addTiles();
  }

  private void addTiles() {
    for (int row = 0; row < mapLayout.length; row++) {
      for (int col = 0; col < mapLayout[row].length; col++) {
        if (mapLayout[row][col] == 0) {
          continue;
        }

        final float tileWidth = 1;
        Tile tile = new Tile(
          new Vector(
            row + tileWidth / 2 - mapLayout.length / 2, 
            col + tileWidth / 2 - mapLayout[row].length / 2
          )
        );
        tile.addShapeRenderer(new RectangleRenderer2D(Vector.one, Color.BLACK, Vector.zero));
        tile.addShapeRenderer(new RectangleRenderer2D(Vector.one.multiply(0.9), Color.MAGENTA, Vector.zero));

        instantiate(tile);
      }
    }
  }
}

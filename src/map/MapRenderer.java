package map;

import java.awt.Color;

import core.GameBehaviour.GameBehaviourStart;
import entities.Object;
import rendering.RectangleRenderer2D;
import util.Vector;

public class MapRenderer extends Object implements GameBehaviourStart {
  private int[][] mapLayout = MapLayouts.getMainLayout();

  public MapRenderer() {
  }

  public void start () {
    int height = mapLayout.length;
    for (int row = 0; row < height; row++) {
      int rowWidth = mapLayout[row].length;
      for (int col = 0; col < rowWidth; col++) {
        if (mapLayout[row][col] == 0) {
          continue;
        }

        final float tileWidth = 1;
        Tile tile = new Tile(
          new Vector(
            col - (rowWidth / 2) + (tileWidth / 2),
            height - row - (height / 2) - (tileWidth / 2)
          )
        );

        instantiate(tile);
      }
    }
  }
}

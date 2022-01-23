package map;

import collision.TileMapCollider;
import entities.CollidableObject;
import java.awt.Color;
import rendering.topDown.RectangleRenderer2D;
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

  public Vector toTileMapPosition(Vector worldPosition) {
    int mapHeight = getLayout().length;
    int mapWidth = getLayout()[0].length;

    double leftX =
      getTransform().getPosition().getX() - mapWidth * tileSize / 2;
    double topY =
      getTransform().getPosition().getY() + mapHeight * tileSize / 2;

    double tileMapX = (worldPosition.getX() - leftX) / tileSize;
    double tileMapY = (topY - worldPosition.getY()) / tileSize;

    return new Vector(tileMapX, tileMapY);
  }

  public int toTileMapElement(Vector worldPosition) {
    int mapHeight = getLayout().length;
    int mapWidth = getLayout()[0].length;

    double leftX =
      getTransform().getPosition().getX() - mapWidth * tileSize / 2;
    double topY =
      getTransform().getPosition().getY() + mapHeight * tileSize / 2;

    double tileMapX = (worldPosition.getX() - leftX) / tileSize;
    double tileMapY = (topY - worldPosition.getY()) / tileSize;

    return getLayout()[(int) tileMapX][(int) tileMapY];
  }

  public boolean[][] toBooleanTileMap() {
    int[][] layout = getLayout();
    boolean[][] booleanLayout = new boolean[layout.length][layout[0].length];

    for (int i = 0; i < layout.length; i++) {
      for (int j = 0; j < layout[0].length; j++) {
        booleanLayout[i][j] = layout[i][j] != 0;
      }
    }

    return booleanLayout;
  }

  public void setLayout(int[][] layout) {
    this.layout = layout;
    removeAllShapeRenderers();
    removeAllColliders();

    addCollider(new TileMapCollider(toBooleanTileMap(), getTileSize()));
    createTileRenderers2D();
  }

  public double getTileSize() {
    return this.tileSize;
  }

  public void setTileSize(double tileSize) {
    this.tileSize = tileSize;
  }

  private void createTileRenderers2D() {
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

        addShapeRenderer(
          new RectangleRenderer2D(
            Vector.ONE.multiply(getTileSize()),
            Color.PINK,
            tileWorldOffset
          )
        );
      }
    }
  }
}

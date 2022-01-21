package map;

public abstract class MapLayouts {
  private static final int[][] MAIN = new int[][] 
    {
      {1,1,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,1},
      {1,1,1,1,1,1,1,1},
    };

  public static int[][] getMainLayout() {
    return MAIN;
  }
}
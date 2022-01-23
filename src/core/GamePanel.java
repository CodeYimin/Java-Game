package core;

import collision.CollisionManager;
import core.gameBehaviour.GameBehaviourPreUpdate;
import core.gameBehaviour.GameBehaviourStart;
import core.gameBehaviour.GameBehaviourUpdate;
import entities.Object;
import entities.Player;
import input.InputManageable;
import items.RayCastVisualizer;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import map.TileMap;
import rendering.Camera;
import rendering.Drawable;
import rendering.rayCast.RayCastingCamera;
import rendering.topDown.TopDownCamera;

public class GamePanel extends JPanel {

  private Player player;
  private final Time time = new Time();

  public Time getTime() {
    return time;
  }

  {
    Player player = new Player();
    Object.instantiate(player);

    Camera topDownCamera = new TopDownCamera();
    topDownCamera.setObjectFollowing(player);
    // Object.instantiate(topDownCamera);

    TileMap tileMap = new TileMap();
    Object.instantiate(tileMap);

    Camera rayCastingCamera = new RayCastingCamera(tileMap, player);
    Object.instantiate(rayCastingCamera);

    RayCastVisualizer rayCastVisualizer = new RayCastVisualizer(
      player,
      tileMap
    );
    Object.instantiate(rayCastVisualizer);

    CollisionManager collisionManager = new CollisionManager();
    Object.instantiate(collisionManager);
  }

  private void resetGraphics(Graphics graphics) {
    graphics.setColor(Color.white);
    graphics.fillRect(0, 0, getWidth(), getHeight());
  }

  private boolean isFirstRender = true;

  public boolean getIsFirstRender() {
    return isFirstRender;
  }

  private void setIsFirstRender(boolean bool) {
    isFirstRender = bool;
  }

  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    resetGraphics(graphics);

    getTime().updateDeltaTime();

    if (getIsFirstRender()) {
      Object
        .getObjectsByType(GameBehaviourStart.class)
        .stream()
        .forEach(object -> object.start());

      Object
        .getObjectsByType(InputManageable.class)
        .stream()
        .map(object -> object.getInputManager())
        .forEach(this::addKeyListener);
    }

    Object
      .getObjectsByType(GameBehaviourPreUpdate.class)
      .stream()
      .forEach(object -> object.preUpdate(getTime().getDeltaTime()));

    Object
      .getObjectsByType(GameBehaviourUpdate.class)
      .stream()
      .forEach(object -> object.update(getTime().getDeltaTime()));

    Object
      .getObjectsByType(Drawable.class)
      .stream()
      .forEach(object -> object.draw(graphics, getSize()));

    setIsFirstRender(false);
  }
}

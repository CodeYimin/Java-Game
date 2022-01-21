package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import collision.CollisionManager;
import core.Time;
import core.GameBehaviour.GameBehaviourPreUpdate;
import core.GameBehaviour.GameBehaviourStart;
import core.GameBehaviour.GameBehaviourUpdate;
import entities.Object;
import entities.Player;
import input.InputManageable;
import map.MapRenderer;
import rendering.Camera;
import rendering.Drawable;
import rendering.TopDownCamera;

public class GamePanel extends JPanel {

  private Player player;

  {
    Object.instantiate(Time.getInstance());
    Time.reset();

    player = new Player();
    Object.instantiate(player);

    Camera camera = new TopDownCamera();
    camera.setObjectFollowing(player);
    Object.instantiate(camera);

    MapRenderer mapRenderer = new MapRenderer();
    Object.instantiate(mapRenderer);

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

    if (getIsFirstRender()) {
      Object.getObjectsByType(GameBehaviourStart.class)
        .stream()
        .forEach(object -> object.start());

      Object.getObjectsByType(InputManageable.class)
        .stream()
        .map(object -> object.getInputManager())
        .forEach(this::addKeyListener);
    }

    Object.getObjectsByType(GameBehaviourPreUpdate.class)
      .stream()
      .forEach(object -> object.preUpdate());

    Object.getObjectsByType(GameBehaviourUpdate.class)
      .stream()
      .forEach(object -> object.update());

    Object.getObjectsByType(Drawable.class)
      .stream()
      .forEach(object -> object.draw(graphics, getSize()));

    setIsFirstRender(false);
  }
}

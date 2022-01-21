package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

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
    camera.setTransform(player.getTransform());
    Object.instantiate(camera);

    MapRenderer mapRenderer = new MapRenderer();
    Object.instantiate(mapRenderer);
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
    resetGraphics(graphics);

    final ArrayList<Object> objects = Object.getObjects();

    if (getIsFirstRender()) {
      for (Object object : objects) {
        if (object instanceof GameBehaviourStart) {
          GameBehaviourStart gameBehaviour = (GameBehaviourStart) object;
          gameBehaviour.start();
        }
      }
    }

    for (Object object : objects) {
      if (object instanceof GameBehaviourPreUpdate) {
        GameBehaviourPreUpdate gameBehaviour = (GameBehaviourPreUpdate) object;
        gameBehaviour.preUpdate();
      }
    }
    
    for (Object object : objects) {
      if (object instanceof GameBehaviourUpdate) {
        GameBehaviourUpdate gameBehaviour = (GameBehaviourUpdate) object;
        gameBehaviour.update();
      }

      if (object instanceof InputManageable) {
        final InputManageable listener = (InputManageable) object;
        addKeyListener(listener.getInputManager());
      }

      if (object instanceof Drawable) {
        final Drawable drawable = (Drawable) object;
        drawable.draw(graphics, getSize());
      }
    }

    setIsFirstRender(false);
  }
}

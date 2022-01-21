package entities;

import java.awt.Color;
import java.util.ArrayList;

import core.Time;
import core.GameBehaviour.GameBehaviourUpdate;
import input.InputManageable;
import input.InputManager;
import input.InputMap;
import rendering.RectangleRenderer2D;
import util.Vector;

public class Player extends RenderedObject implements GameBehaviourUpdate, InputManageable {
  private float speed = 5;
  private InputManager inputManager = new InputManager();

  public float getSpeed() {
    return this.speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public InputManager getInputManager() {
    return inputManager;
  }

  public void setInputManager(InputManager inputManager) {
    this.inputManager = inputManager;
  }

  {
    final InputMap<Vector> movementKeyMap = new InputMap<Vector>()
      .set(38, Vector.up)
      .set(40, Vector.down)
      .set(37, Vector.left)
      .set(39, Vector.right);

    inputManager.<Vector>addCompositeBinding("movement", movementKeyMap);
    inputManager.<Boolean>addValueBinding("shoot", 65, true, false);

    addShapeRenderer(new RectangleRenderer2D(Vector.one.multiply(1.25), Color.red, Vector.zero));
    addShapeRenderer(new RectangleRenderer2D(Vector.one, Color.orange, Vector.zero));
  }

  public Player(Transform transform) {
    setTransform(transform);
  }

  public Player() {}

  public void update() {
    final ArrayList<Vector> inputMovementVectors = inputManager.getActivatedValuesByBindName("movement", Vector.class);
    final Vector inputMovementVector = inputMovementVectors
      .stream()
      .reduce(Vector.zero, (prev, current) -> prev.add(current));

    final Vector velocity = inputMovementVector
      .normalized()
      .multiply(speed * Time.getDeltaTime() / 1000);

    getTransform().translate(velocity);
  }

}

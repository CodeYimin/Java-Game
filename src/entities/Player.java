package entities;

import java.awt.Color;
import java.util.ArrayList;

import collision.RectangleCollider2D;
import core.Time;
import core.GameBehaviour.GameBehaviourUpdate;
import input.InputManageable;
import input.InputManager;
import input.InputMap;
import rendering.RectangleRenderer2D;
import util.Vector;

public class Player extends CollidableObject implements GameBehaviourUpdate, InputManageable {
  private float speed = 5;
  private Vector velocity;
  private InputManager inputManager = new InputManager();

  public float getSpeed() {
    return this.speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public Vector getVelocity() {
    return this.velocity;
  }

  public void setVelocity(Vector velocity) {
    this.velocity = velocity;
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

    addCollider(new RectangleCollider2D(Vector.one));
  }

  public Player() {}

  public void update() {
    final ArrayList<Vector> inputMovementVectors = inputManager.getActivatedValuesByBindName("movement", Vector.class);
    final Vector inputMovementVector = inputMovementVectors
      .stream()
      .reduce(Vector.zero, (prev, current) -> prev.add(current));

    setVelocity(
      inputMovementVector
        .normalized()
        .multiply(speed * Time.getDeltaTime() / 1000)
    );

    Vector currentPosition = getTransform().getPosition();
    Vector velocityX = new Vector(getVelocity().getX(), 0);
    Vector velocityY = new Vector(0, getVelocity().getY());

    if (!willCollide(currentPosition.add(velocityX))) {
      getTransform().translate(velocityX);
    } 
    if (!willCollide(currentPosition.add(velocityY))) {
      getTransform().translate(velocityY);
    }

  }

  public void onCollision(CollidableObject other) {
    // System.out.println(other);
  }

}

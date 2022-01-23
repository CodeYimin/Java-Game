package entities;

import collision.RectangleCollider;
import core.gameBehaviour.GameBehaviourUpdate;
import input.InputManageable;
import input.InputManager;
import input.InputMap;
import java.awt.Color;
import java.util.ArrayList;
import rendering.topDown.RectangleRenderer2D;
import util.Vector;

public class Player
  extends CollidableObject
  implements GameBehaviourUpdate, InputManageable {

  private float speed = 5;
  private float rotationSpeed = 90;
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

  public float getRotationSpeed() {
    return this.rotationSpeed;
  }

  public void setRotationSpeed(float rotationSpeed) {
    this.rotationSpeed = rotationSpeed;
  }

  public InputManager getInputManager() {
    return inputManager;
  }

  public void setInputManager(InputManager inputManager) {
    this.inputManager = inputManager;
  }

  {
    InputMap<Vector> movementKeyMap = new InputMap<Vector>()
      .set(38, Vector.UP)
      .set(40, Vector.DOWN)
      .set(37, Vector.LEFT)
      .set(39, Vector.RIGHT);

    InputMap<Integer> rotationKeyMap = new InputMap<Integer>()
      .set(59, -1)
      .set(46, 1);

    inputManager.<Vector>addCompositeBinding("movement", movementKeyMap);
    inputManager.<Boolean>addValueBinding("shoot", 65, true, false);
    inputManager.<Integer>addCompositeBinding("rotation", rotationKeyMap);

    addShapeRenderer(
      new RectangleRenderer2D(Vector.ONE, Color.red, Vector.ZERO)
    );
    addShapeRenderer(
      new RectangleRenderer2D(
        Vector.ONE.multiply(0.85),
        Color.orange,
        Vector.ZERO
      )
    );

    addCollider(new RectangleCollider(Vector.ONE));
  }

  public Player() {}

  public void update(long deltaTime) {
    movement(deltaTime);
    rotation(deltaTime);
  }

  private void movement(long deltaTime) {
    ArrayList<Vector> inputMovementVectors = inputManager.getActivatedValuesByBindName(
      "movement",
      Vector.class
    );
    Vector inputMovementVector = inputMovementVectors
      .stream()
      .reduce(Vector.ZERO, (prev, current) -> prev.add(current));

    setVelocity(
      inputMovementVector
        .normalized()
        .multiply(speed * deltaTime / 1000)
        .rotateGameDegrees(getTransform().getAngle())
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

  private void rotation(long deltaTime) {
    ArrayList<Integer> inputRotationScalars = inputManager.getActivatedValuesByBindName(
      "rotation",
      Integer.class
    );
    int inputRotationScalar = inputRotationScalars
      .stream()
      .reduce(0, (prev, current) -> prev + current);

    getTransform()
      .rotate(inputRotationScalar * getRotationSpeed() * deltaTime / 1000);
  }

  public void onCollision(CollidableObject other) {
    // System.out.println(other);
  }
}

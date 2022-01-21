package entities;

public class GameObject extends Object {
  private Transform transform;

  public void setTransform(Transform transform) {
    this.transform = transform;
  }

  public GameObject(Transform transform) {
    this.transform = transform;
  }

  public GameObject() {
    this.transform = new Transform();
  }
  
  public Transform getTransform() {
    return this.transform;
  }

}

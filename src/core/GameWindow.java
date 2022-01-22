package core;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {
  public static final int DEFAULT_WIDTH = 800;
  public static final int DEFAULT_HEIGHT = 600;

  private JPanel panel;

  {
    setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public GameWindow(JPanel panel) {
    super("Best Game");
    setPanel(panel);
  }

  public GameWindow() {
    super("Best Game");
  }

  public JPanel getPanel() {
    return this.panel;
  }

  public void setPanel(JPanel panel) {
    this.panel = panel;
    add(panel);

    panel.setFocusable(true);
    setVisible(true);
  }
}

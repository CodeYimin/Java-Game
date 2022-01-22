import core.GamePanel;
import core.GameWindow;

public class Main {
	public static final int FRAME_RATE = 60;

	public static void main(String[] args) {
		final GameWindow gameWindow = new GameWindow();
		final GamePanel gamePanel = new GamePanel();

		gameWindow.setPanel(gamePanel);

		while (true) {
			gameWindow.getPanel().repaint();
			try {
				Thread.sleep(1000 / FRAME_RATE);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

}

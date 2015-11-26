package canilho.paulo.bacalhau.renderer.player.assets;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	private boolean[] keys_pressed = new boolean[68845];

	@Override
	public void keyPressed(KeyEvent e) {
		keys_pressed[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys_pressed[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public boolean[] getKeysPressed() {
		return keys_pressed;
	}

}

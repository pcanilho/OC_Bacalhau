package canilho.paulo.bacalhau.renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import canilho.paulo.bacalhau.renderer.player.Entity;
import canilho.paulo.bacalhau.renderer.player.Player;
import canilho.paulo.bacalhau.renderer.player.assets.KeyHandler;
import canilho.paulo.bacalhau.renderer.player.assets.MouseHandler;

public class Render extends Canvas implements Runnable {
	private static final long serialVersionUID = -676167817416720957L;

	// THREAD
	private boolean running = false;
	private Thread thread;

	// WINDOW
	private JFrame frame;
	public static int WIDTH = 800, HEIGHT = 600;
	private GameSystem gameSystem;
	private MouseHandler mouse_handler;
	private KeyHandler key_handler;

	// ENTITY
	private Entity player;

	public Render(String... size) {
		init(size);
	}

	private void init(String... size) {
		frame = new JFrame("Bacalhau Invaders");
		if (size != null && size.length == 2) {
			WIDTH = Integer.parseInt(size[0]);
			HEIGHT = Integer.parseInt(size[1]);
		} else
			frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.add(this);
		frame.setIconImage(new ImageIcon("imgs/bacalhau/bacalhau.png").getImage());

		// LOOK AND FEEL
		setCustomLookAndFeel();

		// GAME SYSTEM
		startGameSystem();

		// SETUP HANDLER
		setupHandler();

		// SETUP CANVAS
		setupCanvas();

		// SETUP CURSOR
		setupCursor();

		// START RENDERING
		showWindow();
		start();
	}

	private void setupCursor() {
//		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
//		setCursor(blankCursor);
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	private void setupHandler() {
		mouse_handler = new MouseHandler(gameSystem);
		addMouseListener(mouse_handler);
		addMouseMotionListener(mouse_handler);
		addMouseWheelListener(mouse_handler);

		key_handler = new KeyHandler();
		addKeyListener(key_handler);
	}

	private void setupCanvas() {
		setBounds(0, 0, WIDTH, HEIGHT - 100);
	}

	public void showWindow() {
		if (frame != null)
			frame.setVisible(true);
		else
			System.err.println(this.getClass().getName() + " - FRAME IS NULL");
	}

	public void start() {
		if (!running) {
			thread = new Thread(this, "Render");
			running = true;
			thread.start();
		}
	}

	public void stop() {
		try {
			if (running) {
				running = false;
				thread.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void startGameSystem() {
		player = new Player(null);
		gameSystem = new GameSystem(player);
	}

	private Graphics2D g2d;

	private int fps = 0;
	private int fps_static;

	public void run() {
		// FPS
		long lastLoopTime = System.nanoTime();
		int lastFpsTime = 0;

		while (running) {
			// FPS COUNTER
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			lastFpsTime += updateLength;
			fps++;
			if (lastFpsTime >= 1000000000) {
				fps_static = fps;
				lastFpsTime = 0;
				fps = 0;
			}
			// FPS COUNTER

			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				continue;
			}

			g2d = (Graphics2D) bs.getDrawGraphics();
			{
				// BACKGROUND
				drawBackground();

				// FPS
				drawFPS();

				// PLAYER MOVEMENT
				movePlayer();

				// GAME SYSTEM
				gameSystem.render(g2d, mouse_handler.mouse_location);

			}
			// RENDER
			renderScreen(g2d, bs);
		}
	}

	private void movePlayer() {
		boolean[] keys_pressed = key_handler.getKeysPressed();

		if (keys_pressed[KeyEvent.VK_W]) {
			if (player.getEntityLocation().y - 1 * player.getSpeed() > 0)
				player.move(0, -1);
		}
		if (keys_pressed[KeyEvent.VK_S]) {
			if (player.getEntityLocation().y + 1 * player.getSpeed() + player.getImageDimension().getHeight() < getHeight())
				player.move(0, 1);
		}
		if (keys_pressed[KeyEvent.VK_A]) {
			if (player.getEntityLocation().x - 1 * player.getSpeed() > 0)
				player.move(-1, 0);
		}
		if (keys_pressed[KeyEvent.VK_D]) {
			if(player.getEntityLocation().x + 1 * player.getSpeed() + player.getImageDimension().getHeight() < getWidth())
			player.move(1, 0);
		}
	}

	private void drawFPS() {
		g2d.setColor(Color.yellow);
		g2d.drawString("FPS: " + fps_static, 20, getHeight() - 20 * 2);
	}

	public Graphics2D getG2D() {
		return g2d;
	}

	private void drawBackground() {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());
	}

	private void renderScreen(Graphics2D g2d, BufferStrategy bs) {
		g2d.dispose();
		bs.show();
	}

	private void setCustomLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

}

package canilho.paulo.bacalhau.renderer.player;

import java.awt.Point;

public abstract class Weapon extends Entity implements Runnable {

	public Weapon(String path) {
		super(path);
	}

	private Thread thread;
	private boolean running = false;

	private Point target;
	private Point origin;

	public void setTarget(Point origin, Point target) {
		this.target = target;
		this.origin = origin;
	}

	public Point[] getTarget() {
		return new Point[] { origin, target };
	}

	public void stop() {
		try {
			if (running) {
				running = false;
				thread.join();
			}
		} catch (InterruptedException e) {
		}

	}

	public void start() {
		if(!running){
			thread = new Thread(this, "Weapon Thread");
			running = true;
			thread.start();
		}
	}
	
	public void run(){
		while(running){
			
		}
	}
	
}

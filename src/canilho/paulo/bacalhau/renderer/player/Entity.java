package canilho.paulo.bacalhau.renderer.player;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

public abstract class Entity {

	private String name;

	
	public static final Dimension entity_max_size = new Dimension(50, 50);
	// IMAGE
	private ImageIcon image;
	private int img_width = entity_max_size.width, img_height = entity_max_size.height;
	private int speed = 3;

	// LOCATION
	private Point entity_location = new Point(0, 0);

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public void loadImage(String path) {
		if (path != null)
			image = new ImageIcon(path);
		else
			image = new ImageIcon("imgs/bacalhau/default.png");
//		img_width = image.getIconWidth();
//		img_height = image.getIconHeight();

	}

	public Dimension getImageDimension() {
		return new Dimension(img_width, img_height);
	}

	public Image getImage() {
		return image.getImage();
	}

	public void setName(String name) {
		this.name = name;
	}

	public Entity(String path) {
		if(path == null)
		loadImage("imgs/bacalhau/francisco.png");
		else
			loadImage(path);
	}

	public String getName() {
		return name;
	}

	public Point getEntityLocation() {
		return entity_location;
	}

	public void move(int x, int y) {
		int new_x = entity_location.x + x * speed;
		int new_y = entity_location.y + y * speed;

		entity_location = new Point(new_x, new_y);
	}
}

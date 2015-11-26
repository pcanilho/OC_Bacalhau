package canilho.paulo.bacalhau.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import canilho.paulo.bacalhau.renderer.player.Bacalhau;
import canilho.paulo.bacalhau.renderer.player.Entity;
import canilho.paulo.bacalhau.renderer.player.Weapon;

public class GameSystem {

	

	// RENDER
	private Entity player;
	private List<Weapon> bacalhaus;

	public GameSystem(Entity player) {
		this.player = player;
		init();
	}

	private void init() {
		String name = JOptionPane.showInputDialog("What is your name?");
		if (name == null || name.isEmpty() || name.length() < 3) {
			JOptionPane.showMessageDialog(null, "Name cannot be empty and has to be more than 3 characters!", "Error", JOptionPane.ERROR_MESSAGE);
			name = "Rambo";
			init();
		}

		bacalhaus = new ArrayList<Weapon>();
		player.setName(name);
	}
	

	public void render(Graphics2D g2d, Point mouse_location) {
		// RENDER NAME
		renderName(g2d);
		
		// RENDER PLAYER LOCATION
		renderPlayerLocation(g2d);
		
		// RENDER ENTITY
		renderEntity(g2d, mouse_location);
		
		// RENDER BACALHAU SHOTS
		renderBacalhaus(g2d);
		
	}
	
	private void renderBacalhaus(Graphics2D g2d) {
		for(Weapon bacalhau: bacalhaus){
			g2d.drawImage(bacalhau.getImage(), bacalhau.getEntityLocation().x, bacalhau.getEntityLocation().y, Entity.entity_max_size.width, Entity.entity_max_size.height,null);
		}
	}

	public void fireBacalhau(Point mouse_location){
		Weapon bacalhau = new Bacalhau();
		bacalhau.setTarget(player.getEntityLocation(), mouse_location);
		bacalhau.start();
		
		bacalhaus.add(bacalhau);
	}

	private void renderPlayerLocation(Graphics2D g2d) {
		String message = "X: " + player.getEntityLocation().x + " ,Y: " + player.getEntityLocation().y;
		int font_size = 12;
		if (g2d != null) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Verdana", 0, font_size));
			g2d.drawString(message, 10, 50);
		}
	}

	private void renderEntity(Graphics2D g2d, Point mouse_location) {
		g2d.drawImage(player.getImage(), player.getEntityLocation().x, player.getEntityLocation().y, Entity.entity_max_size.width, Entity.entity_max_size.height, null);
	}

	private void renderName(Graphics2D g2d) {
		int font_size = 17;
		if (g2d != null) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Verdana", 0, 17));
			g2d.drawString("Player: " + player.getName(), 10, font_size + 10);
		}
	}

}

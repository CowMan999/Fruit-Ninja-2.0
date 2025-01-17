// Bullet.java
// Edward Mayen
// 2024-12-16
// simple bullet class, can damage player or enemies and move at a constant rate

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public abstract class Projectile extends Rectangle {
	
	// initialize vatriables
	protected Pane pane;

	protected boolean disabled = false;
	private boolean shouldDestroy = false;
	private double velocityX = 0, velocityY = 0, velocityRot = 0;

	protected boolean bad = false;

	private static int SPEEDX = 3;
	private static int SPEEDXVAR = 3;
	private static int SPEEDY = 18;
	private static int SPEEDYVAR = 16;
	private static double GRAVITY = 0.4;
	private static int SPEEDROT = 10;
	
	public Projectile(Image i, Pane p) {

		// setup underlying rect
		super(100, 100);
		pane = p;

		// random spawn pos
		setX(((pane.getWidth()/2)*Math.random())+pane.getWidth()/4);
		setY(pane.getHeight()+50);
		
		ImagePattern imagePattern = new ImagePattern(i);
		super.setFill(imagePattern);

		// determine initial vel of projectile
		velocityX = SPEEDX + Math.random()*SPEEDXVAR;
		if(Math.random() > 0.5) {
			velocityX = -velocityX;
		}

		velocityY = -(SPEEDY + Math.random()*SPEEDYVAR);
		velocityRot = Math.random()*SPEEDROT*2 - SPEEDROT;

		// adjust vel based on height of pane
		velocityY *= (pane.getHeight()/1080);

		// update pane and add to it
		pane.getChildren().add(this);
	}

	abstract void onOutOfBounds();
	abstract void onContact();
	abstract void additionalUpdate();

	// fly around screen
	void update() {
		if(!disabled) {
			velocityY += GRAVITY;
			setX(getX() + velocityX);
			setY(getY() + velocityY);
			
			// rotate
			setRotate(getRotate() + velocityRot);
		}

		additionalUpdate();
	}

	boolean containsPoint(Point2D point) {
		if(disabled) {
			return false;
		} else {
			return contains(point);
		}
	}

	void disable() {
		disabled = true;
	}

	void destroy() {
		pane.getChildren().remove(this);
		shouldDestroy = true;
	}

	boolean shouldRemove() {
		return shouldDestroy;
	}

	boolean isBad() {
		return bad;
	}
}

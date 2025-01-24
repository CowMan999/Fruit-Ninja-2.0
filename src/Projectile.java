// Projectile.java
// Edward Mayen
// 2025-01-09
// simple projectile class, flies up and is affected by gravity, has abstract methods for further handling of events

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
	private boolean aboveBottom = false;
	private double velocityX = 0, velocityY = 0, velocityRot = 0;
	

	protected boolean bad = false;

	// constants
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

		// set sprite based on param
		ImagePattern imagePattern = new ImagePattern(i);
		super.setFill(imagePattern);

		// determine initial vel of projectile
		velocityX = SPEEDX + Math.random()*SPEEDXVAR;
		if(Math.random() > 0.5) {
			velocityX = -velocityX;
		}

		// random velocity
		velocityY = -(SPEEDY + Math.random()*SPEEDYVAR);
		velocityRot = Math.random()*SPEEDROT*2 - SPEEDROT;

		// adjust vel based on height of pane
		velocityY *= (pane.getHeight()/1080);

		// update pane and add to it
		pane.getChildren().add(this);
	}

	// abstract methods to be implemented
	abstract void onContact();
	abstract void additionalUpdate();
	abstract void onOutOfBounds();
	abstract boolean shouldLoseLife();

	// fly around screen
	void update() {
		if(!disabled) {
			velocityY += GRAVITY;
			setX(getX() + velocityX);
			setY(getY() + velocityY);

			if(getY() < pane.getHeight() && !aboveBottom) {
				aboveBottom = true;
			} else if(getY() > pane.getHeight() && aboveBottom) {
				onOutOfBounds();
				aboveBottom = false;
			}
			
			// rotate                              
			setRotate(getRotate() + velocityRot);
		}

		additionalUpdate();
	}

	// simple collision abstraction
	boolean containsPoint(Point2D point) {
		if(disabled) {
			return false;
		} else {
			return contains(point);
		}
	}

	// getter for disabled
	void disable() {
		disabled = true;
	}

	// destroy current instance
	void destroy() {
		pane.getChildren().remove(this);
		shouldDestroy = true;
	}

	// should be removed from projectile arraylist?
	boolean shouldRemove() {
		return shouldDestroy;
	}

	// launch via velocity
	void launch() {
		velocityX = Math.random()*20;
		velocityY = 10;
	}

	// bad bool getter
	boolean isBad() {
		return bad;
	}
}

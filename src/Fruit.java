// Fruit.java
// Edward Mayen
// 2025-01-15
// subclass of projectile, increases score if slashed and causes 1 life lost if it falls out of frame

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public class Fruit extends Projectile {

	// variables
	Image aftermath;
	int aftermathTimer = 30;
	double afterVel = 0.4;
	private boolean loseLife = false;
	private boolean powerUp = false;

	// simple constructor, calls projectle constructor with random image updates instance vars
	public Fruit(Image[] i, Image i2, Pane p, boolean pUp) {
		super(i[(int)Math.floor(Math.random()*i.length)], p);
		aftermath = i2;
		bad = false;
		powerUp = pUp;
	}

	// on contact
	void onContact() {
		disable();

		// setup for effect
		setFill(new ImagePattern(aftermath));
		setScaleX(0.8 + Math.random()*0.4);
		setScaleY(0.8 + Math.random()*0.4);
	}

	// should lose life if falls off
	void onOutOfBounds() {
		loseLife = true;
		destroy();
	}

	// wrapper for private bool
	boolean isPowerUp() {
		return powerUp;
	}

	// return if life should be lost
	boolean shouldLoseLife() {
		return loseLife;
	}

	// splash effect on back
	void additionalUpdate() {
		if(disabled) {
			aftermathTimer--;

			// move down
			if(aftermathTimer <= 0) {
				setY(getY()+afterVel);
				afterVel *= 1.02;
			}


			// delete on frame exit
			if(getY()>pane.getHeight()+50) {
				destroy();
			}
		}
	}
}

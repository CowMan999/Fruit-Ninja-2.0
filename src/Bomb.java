// Bomb.java
// Edward Mayen
// 2025-01-16
// subclass of projectile, causes the player to lose a life if slashed

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public class Bomb extends Projectile {

	Image aftermath;
	int aftermathTimer = 50;
	
	// simple constructor, calls enemy constructor and determines direction
	public Bomb(Image i, Image i2, Pane p) {
		super(i, p);
		aftermath = i2;
		bad = true;
	}

	// on contact
	void onContact() {
		disable();
		setFill(new ImagePattern(aftermath));
	}

	// destroy if falls off screen
	void onOutOfBounds() {
		destroy();
	}

	// explosion effect
	void additionalUpdate() {
		if(disabled) {
			aftermathTimer--;
			setRotate(90);

			// grow over time
			if(aftermathTimer > 25) {
				setScaleX(getScaleX()+0.1);
				setScaleY(getScaleY()+0.1);
			} else {
				setScaleX(getScaleX()*0.75);
				setScaleY(getScaleY()*0.75);
			}

			// destroy after timer expires
			if(aftermathTimer == 0) {
				destroy();
			}
		}
	}

	// shouldn't cause life loss if falls off
	boolean shouldLoseLife() {
		return false;
	}
}

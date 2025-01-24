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


	void onOutOfBounds() {
		destroy();
	}

	void additionalUpdate() {
		if(disabled) {
			aftermathTimer--;
			setRotate(90);

			if(aftermathTimer > 25) {
				setScaleX(getScaleX()+0.1);
				setScaleY(getScaleY()+0.1);
			} else {
				setScaleX(getScaleX()*0.75);
				setScaleY(getScaleY()*0.75);
			}

			if(aftermathTimer == 0) {
				destroy();
			}
		}
	}
	
	boolean shouldLoseLife() {
		return false;
	}
}

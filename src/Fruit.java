// EnemyDive.java
// Edward Mayen
// 2024-12-16
// subclass of enemy, moves down towards player rapidly

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public class Fruit extends Projectile {

	Image aftermath;
	int aftermathTimer = 30;
	double afterVel = 0.4;

	// simple constructor, calls enemy constructor and determines direction
	public Fruit(Image[] i, Image i2, Pane p) {
		super(i[(int)Math.floor(Math.random()*i.length)], p);
		aftermath = i2;
		bad = false;
	}

	// on contact
	void onContact() {
		disable();
		setFill(new ImagePattern(aftermath));
		setScaleX(0.8 + Math.random()*0.4);
		setScaleY(0.8 + Math.random()*0.4);
	}

	void onOutOfBounds() {
		destroy();
	}

	void additionalUpdate() {
		if(disabled) {
			aftermathTimer--;

			if(aftermathTimer <= 0) {
				setY(getY()+afterVel);
				afterVel *= 1.02;
			}

			if(getY()>pane.getHeight()+50) {
				destroy();
			}
		}
	}
}

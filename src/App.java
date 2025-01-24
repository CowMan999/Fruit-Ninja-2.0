// App.java
// Edward Mayen
// 2025-01-08
// main application class, implements all menus and  main loop/program flow

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

	// initialize variables
	int fruitTimer = 30;
	int fruitTimerCap = 85;
	int scoreCounter = 0;
	Scene scene;
	long globalTimer = 0;
	boolean mousePressed = false;
	int life = 3;

	// powerup handlers
	String powerUp = "None";
	int powerUpTimer = 0;
	int screenShakeTimer = 0;

	// potential powerups
	String[] powerUps = new String[] {
		"Bomb Repel",
		"Fruit Frenzy",
	};

	// list to keep track and update projectiles
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	// sword trail
	private ArrayList<Rectangle> trail = new ArrayList<Rectangle>();

	// called on program start
    @Override
    public void start(Stage primaryStage) {

		// create pane with size 800x600
		Pane pane = new Pane();
		scene = new Scene(pane, 800, 600);

		// load background
		Image backgound = new Image("bg.png");
		scene.setFill(new ImagePattern(backgound));
		
		// setup stage
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fruit Ninja");

		// init main menu
		menuMain(primaryStage);
    }

	// main menu gui 
	public void menuMain(Stage stage) {

		// grid pane for layout purposes
		GridPane grid = new GridPane();

		// title element
		Text title = new Text("Fruit Ninja 2.0");
		title.setFill(Color.YELLOWGREEN);
		title.setScaleX(3); title.setScaleY(3);

		// play button rect
		Rectangle button = new Rectangle(100, 60);
		button.setFill(Color.BLANCHEDALMOND);

		// play button text
		Text buttonText = new Text("Play");
		buttonText.setFill(Color.GREEN);
		buttonText.setScaleX(2); buttonText.setScaleY(2);

		// tutorial button
		Rectangle button2 = new Rectangle(100, 60);
		button2.setFill(Color.BLANCHEDALMOND);

		// tutorial button text
		Text buttonText2 = new Text("Tutorial");
		buttonText2.setFill(Color.BLUEVIOLET);
		buttonText2.setScaleX(2); buttonText2.setScaleY(2);

		// quit button
		Rectangle button3 = new Rectangle(100, 60);
		button3.setFill(Color.BLANCHEDALMOND);

		// quit button text
		Text buttonText3 = new Text("Quit");
		buttonText3.setFill(Color.BLACK);
		buttonText3.setScaleX(2); buttonText3.setScaleY(2);

		// menu images
		Rectangle imageRect = new Rectangle(100, 100);
		
		imageRect.setFill(new ImagePattern(new Image("apple.png")));
		Rectangle imageRect2 = new Rectangle(100, 100);
		imageRect2.setFill(new ImagePattern(new Image("strawberry.png")));
		Rectangle imageRect3 = new Rectangle(100, 100);
		imageRect3.setFill(new ImagePattern(new Image("papaya.png")));
		Rectangle imageRect4 = new Rectangle(100, 100);
		imageRect4.setFill(new ImagePattern(new Image("banana.png")));
		Rectangle imageRect5 = new Rectangle(100, 100);
		imageRect5.setFill(new ImagePattern(new Image("orange.png")));
		Rectangle imageRect6 = new Rectangle(100, 100);
		imageRect6.setFill(new ImagePattern(new Image("avocado.png")));

		// lay everything out on grid, overlap text with respective buttons
		grid.add(title, 1, 0);
		grid.add(button, 1, 1);
		grid.add(buttonText, 1, 1);
		grid.add(button2, 1, 2);
		grid.add(buttonText2, 1, 2);
		grid.add(button3, 1, 3);
		grid.add(buttonText3, 1, 3);
		grid.add(imageRect, 0, 1);
		grid.add(imageRect2, 2, 1);
		grid.add(imageRect3, 0, 2);
		grid.add(imageRect4, 2, 2);
		grid.add(imageRect5, 0, 3);
		grid.add(imageRect6, 2, 3);


		// configure allignment and padding
		grid.setVgap(30);
		grid.setHgap(50);
		grid.setPadding(new Insets(50, 50, 50, 50));
		grid.setAlignment(Pos.CENTER);

		// center text within their buttons
		grid.setHalignment(title, HPos.CENTER);
		grid.setHalignment(buttonText, HPos.CENTER);
		grid.setHalignment(buttonText2, HPos.CENTER);
		grid.setHalignment(buttonText3, HPos.CENTER);

		// set root to new gui grid
		scene.setRoot(grid);

		// button handlers
		scene.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {

				// if mouse within bounds of play button and pressing...
				if(button.getLayoutX() < event.getX() && button.getLayoutX()+button.getWidth() > event.getX()) {
					if(button.getLayoutY() < event.getY() && button.getLayoutY()+button.getHeight() > event.getY()) {

						// detach current event handler to avoid repeat calls, and start game main
						scene.setOnMousePressed(null);
						gameMain(stage);
					}
				}
				
				// if mouse within bounds of quit button and pressing...
				if(button2.getLayoutX() < event.getX() && button2.getLayoutX()+button2.getWidth() > event.getX()) {
					if(button2.getLayoutY() < event.getY() && button2.getLayoutY()+button2.getHeight() > event.getY()) {
						
						// tutorial menu
						scene.setOnMousePressed(null);
						tutorialGui(stage);
					}
				}

				// if mouse within bounds of quit button and pressing...
				if(button3.getLayoutX() < event.getX() && button3.getLayoutX()+button3.getWidth() > event.getX()) {
					if(button3.getLayoutY() < event.getY() && button3.getLayoutY()+button3.getHeight() > event.getY()) {
						
						// terminate program
						System.exit(0);
					}
				}
			}
        });

		// reveal window
		stage.show();
	}

	// game over gui 
	public void gameOverMain(Stage stage) {

		// grid pane for layout
		GridPane grid = new GridPane();

		// title text
		Text title = new Text("Game Over!");
		title.setFill(Color.RED);
		title.setScaleX(3); title.setScaleY(3);

		// score text, update with scorecounter
		Text scoreText = new Text("Score: "+scoreCounter);
		scoreText.setFill(Color.WHITE);
		scoreText.setScaleX(2); scoreText.setScaleY(2);

		// main menu button
		Rectangle button = new Rectangle(145, 60);
		button.setFill(Color.BLANCHEDALMOND);

		// main menu button text
		Text buttonText = new Text("Main Menu");
		buttonText.setFill(Color.BLACK);
		buttonText.setScaleX(2); buttonText.setScaleY(2);

		// organize everything on grid, overlap button with its text
		grid.add(title, 0, 0);
		grid.add(scoreText, 0, 1);
		grid.add(button, 0, 2);
		grid.add(buttonText, 0, 2);

		// allign and format
		grid.setVgap(50);
		grid.setPadding(new Insets(50, 50, 50, 50));
		grid.setAlignment(Pos.CENTER);

		// center all text
		grid.setHalignment(buttonText, HPos.CENTER);
		grid.setHalignment(title, HPos.CENTER);
		grid.setHalignment(scoreText, HPos.CENTER);

		// update root to new menu
		scene.setRoot(grid);
 
		// custom button handler
		scene.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {

				// if mouse within bounds of quit button and pressing...
				if(button.getLayoutX() < event.getX() && button.getLayoutX()+button.getWidth() > event.getX()) {
					if(button.getLayoutY() < event.getY() && button.getLayoutY()+button.getHeight() > event.getY()) {

						// disable event handler, reset score and return to main menu
						scene.setOnMousePressed(null);
						scoreCounter = 0;
						menuMain(stage);
					}
				}
			}
        });

	}

	// tutorial gui 
	public void tutorialGui(Stage stage) {

		// grid pane for layout
		GridPane grid = new GridPane();

		// title text
		Text title = new Text("How to play:");
		title.setFill(Color.RED);
		title.setScaleX(3); title.setScaleY(3);

		// description inforgraphic
		Rectangle tutorialGraphic = new Rectangle(500, 375);
		tutorialGraphic.setFill(new ImagePattern(new Image("tutorial.png")));

		// main menu button
		Rectangle button = new Rectangle(145, 60);
		button.setFill(Color.BLANCHEDALMOND);

		// main menu button text
		Text buttonText = new Text("Main Menu");
		buttonText.setFill(Color.BLACK);
		buttonText.setScaleX(2); buttonText.setScaleY(2);

		// organize everything on grid, overlap button with its text
		grid.add(title, 0, 0);
		grid.add(tutorialGraphic, 0, 1);
		grid.add(button, 0, 2);
		grid.add(buttonText, 0, 2);

		// allign and format
		grid.setVgap(50);
		grid.setPadding(new Insets(50, 50, 50, 50));
		grid.setAlignment(Pos.CENTER);

		// center all text
		grid.setHalignment(buttonText, HPos.CENTER);
		grid.setHalignment(button, HPos.CENTER);
		grid.setHalignment(title, HPos.CENTER);
		grid.setHalignment(tutorialGraphic, HPos.CENTER);

		// update root to new menu
		scene.setRoot(grid);
 
		// custom button handler
		scene.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {

				// if mouse within bounds of quit button and pressing...
				if(button.getLayoutX() < event.getX() && button.getLayoutX()+button.getWidth() > event.getX()) {
					if(button.getLayoutY() < event.getY() && button.getLayoutY()+button.getHeight() > event.getY()) {

						// disable event handler, reset score and return to main menu
						scene.setOnMousePressed(null);
						menuMain(stage);
					}
				}
			}
        });

	}

	// causes pane to rotate rapidly creating screenshake effect
	void screenShake() {
		screenShakeTimer = 8;
	}

	// game main function, main loop handled here
	public void gameMain(Stage stage) {

		// main pane
        Pane pane = new Pane();

		// add and configure score text
		Text scoreText = new Text("Score: 0");
		scoreText.setX(40);
		scoreText.setY(40);
		scoreText.setScaleX(2);
		scoreText.setScaleY(2);
		scoreText.setFill(Color.WHITE);
		pane.getChildren().add(scoreText);

		// add and configure life text
		Text lifeText = new Text("Life: " + life);
		lifeText.setX(140);
		lifeText.setY(40);
		lifeText.setScaleX(2);
		lifeText.setScaleY(2);
		lifeText.setFill(Color.PINK);
		pane.getChildren().add(lifeText);

		// add and configure powerup text
		Text powerUpText = new Text("Current PowerUp:\n"+powerUp);
		powerUpText.setX(65);
		powerUpText.setY(80);
		powerUpText.setScaleX(2);
		powerUpText.setScaleY(2);
		powerUpText.setFill(Color.LIGHTBLUE);
		pane.getChildren().add(powerUpText);

		// load fruit assets in advance
		Image[] fruitImages = new Image[] {
			new Image("apple.png"),
			new Image("papaya.png"),
			new Image("banana.png"),
			new Image("orange.png"),
			new Image("strawberry.png")
		};

		// power up fruit (avocado)
		Image[] powerFruitImages = new Image[] {
			new Image("avocado.png")
		};

		// other images to load in advance
		Image bombImage = new Image("bomb.png");
		Image explosionImage = new Image("explosion.png");
		Image splatImage = new Image("splat.png");
		Image splatAvImage = new Image("avocadosplat.png");

		// cursor init
		Image cursorImage = new Image("cursor.png");
		Rectangle cursor = new Rectangle(125, 100);
		cursor.setFill(new ImagePattern(cursorImage));
		pane.getChildren().add(cursor);

		// main loop animation timer
		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long now) {

				// increment global timer
				globalTimer++;

				// decrement powerup timer if above 0
				if(powerUpTimer > 0) {
					powerUpTimer--;
				} else if(powerUpTimer == 0) {

					// reset powerup, if fruit frenzy remove all active projectiles
					if(powerUp.equals("Fruit Frenzy")) {
						for(int i = 0; i < projectiles.size(); i++) {
							projectiles.get(i).onContact();
						}
					}
					powerUp = "None";
				}

				// if screen is shakeing
				if(screenShakeTimer > 0) {
					screenShakeTimer--;

					// rotate rapidly
					pane.setRotate(Math.random()*10 - 5);

					// reset to 0 after effect is done
					if(screenShakeTimer == 0) {
						pane.setRotate(0);
					}
				}

				// cursor update
				if(mousePressed) {

					// swing animation
					cursor.setRotate(Math.sin(globalTimer*0.4f)*30.f);
					
					// add to trail
					trail.add(new Rectangle(19, 19));
					trail.get(trail.size()-1).setX(cursor.getX()+65);
					trail.get(trail.size()-1).setY(cursor.getY()+30);

					// color depends on powerup state
					if(powerUpTimer == 0) {
						trail.get(trail.size()-1).setFill(Color.rgb(0, 204, 255));
					} else {
						trail.get(trail.size()-1).setFill(Color.rgb(98, 0, 255));
					}

					// rotate for visual interest
					trail.get(trail.size()-1).setRotate(Math.random()*360);
					pane.getChildren().add(trail.get(trail.size()-1));

				} else cursor.setRotate(-30);

				// go through all trail elements
				for(int i = 0; i < trail.size(); i++) {

					// shrink trail elements
					trail.get(i).setWidth(trail.get(i).getWidth()-1);
					trail.get(i).setHeight(trail.get(i).getHeight()-1);

					// if shrunk to zero, remove and reset
					if(trail.get(i).getWidth() == 0) {
						pane.getChildren().remove(trail.get(i));
						trail.remove(i);
						i--;
					}
				}

				// decrease fruit timer, spawn enemy once it hits zero
				fruitTimer--;
				if(fruitTimer <= 0) {

					// if fruit frenzy reset timer to small value
					if(!powerUp.equals("Fruit Frenzy" )) {

						// otherwise reset to fair value
						fruitTimer = (int)((fruitTimerCap+20)*0.7);
					
						// decrease timer cap to increase difficulty over time
						if(--fruitTimerCap < 25) {
							fruitTimerCap = 25;
						}
					} else {
						fruitTimer = 15;
					}
					
					// select random projetcile to spawn
					double choice = Math.random();
					if(choice < 0.2 && !powerUp.equals("Fruit Frenzy" )) {

						// spawn bomb (20%)
						projectiles.add(new Bomb(bombImage, explosionImage, pane));

					} else {

						// spawn fruit (80%)
						if(Math.random() < 0.93 || powerUpTimer > 0) {
							projectiles.add(new Fruit(fruitImages, splatImage, pane, false));
						} else {
							// 7% chance powerup fruit is spawned
							projectiles.add(new Fruit(powerFruitImages, splatAvImage, pane, true));
						}
					}
				}

				// update all projectiles
				for(int i = 0; i < projectiles.size(); i++) {

					// call update
					projectiles.get(i).update();

					// check if proj intersects cursor
					if(mousePressed && projectiles.get(i).containsPoint(new Point2D(cursor.getX()+65, cursor.getY()+30))) {
						
						// if bomb repel active launch bombs away from cursor
						if(powerUp == "Bomb Repel" && projectiles.get(i).isBad()) {
							projectiles.get(i).launch();
							continue;
						}

						// call on contact, implementation of projectile handles
						projectiles.get(i).onContact();

						// struck, perform action based on if its good or bad
						if(projectiles.get(i).isBad()) {

							// if bad, remove life and shake screen
							lifeText.setText("Life: "+ --life);
							screenShake();
						} else {

							// if good, increment score
							scoreText.setText("Score: " + ++scoreCounter);

							// check if powerup
							if(((Fruit)projectiles.get(i)).isPowerUp()) {

								// select random powerup, and set powerup timer
								powerUp = powerUps[(int)Math.floor(Math.random()*powerUps.length)];
								powerUpText.setText("Current PowerUp:\n"+powerUp);
								powerUpTimer = 650;
							}
						}
					}

					// if should be removed
					if(projectiles.get(i).shouldRemove()) {

						if(!powerUp.equals("Fruit Frenzy" )) {
							if(projectiles.get(i).shouldLoseLife()) {

								// remove life if fruit frenzy not active and projectile requests life lost
								lifeText.setText("Life: "+ --life);
								screenShake();
							}
						}

						// delete projectile
						projectiles.remove(i--);
						continue;
					}

					// if zero lives left
					if(life < 1) {

						// reset game
						reset(pane);

						// switch to game over main
						gameOverMain(stage);
						this.stop();
						return;
					}
				}
			}
		};

		// begin mainloop
		timer.start();

		// inform game of mouse press/release
        scene.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
				mousePressed = true;
			} 
        });

		scene.setOnMouseReleased(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
				mousePressed = false;
			}
        });

		// move cursor to follow mouse
		scene.setOnMouseMoved(event -> {
            cursor.setX(event.getSceneX()-30);
            cursor.setY(event.getSceneY()-50);
        });
		
		scene.setOnMouseDragged(event -> {
            cursor.setX(event.getSceneX()-30);
            cursor.setY(event.getSceneY()-50);
        });

		// hide cursor
		scene.setCursor(Cursor.NONE);

		// update root with new game pane
		scene.setRoot(pane);
	}

	// reset game state
	void reset(Pane p) {

		// remove all bullets and enemies
		while(projectiles.size() > 0) {
			p.getChildren().remove(projectiles.get(0));
			projectiles.remove(0);
		}

		// set timer variables to default
		fruitTimer = 30;
		fruitTimerCap = 85;

		// show cursor again
		scene.setCursor(Cursor.DEFAULT);

		// reset variables
		globalTimer = 0;
		mousePressed = false;
		life = 3;
		powerUp = "None";
		powerUpTimer = 0;
		screenShakeTimer = 0;
	}

	// main function, begins gui
    public static void main(String[] args) {
        launch(args);
    }
}
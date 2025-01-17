// Main.java
// Edward Mayen
// 2024-12-13
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

	// lists to keep track of enemies and bullets
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	// called on program start
    @Override
    public void start(Stage primaryStage) {

		// create pane with size 500x500
		Pane pane = new Pane();
		scene = new Scene(pane, 500, 500);

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
		Text title = new Text("Fruit Ninja");
		title.setFill(Color.YELLOWGREEN);
		title.setScaleX(3); title.setScaleY(3);

		// play button rect
		Rectangle button = new Rectangle(100, 60);
		button.setFill(Color.BLANCHEDALMOND);

		// play button text
		Text buttonText = new Text("Play");
		buttonText.setFill(Color.GREEN);
		buttonText.setScaleX(2); buttonText.setScaleY(2);

		// quit button
		Rectangle button2 = new Rectangle(100, 60);
		button2.setFill(Color.BLANCHEDALMOND);

		// quit button text
		Text buttonText2 = new Text("Quit");
		buttonText2.setFill(Color.BLACK);
		buttonText2.setScaleX(2); buttonText2.setScaleY(2);

		// menu images
		Rectangle imageRect = new Rectangle(100, 100);
		
		imageRect.setFill(new ImagePattern(new Image("apple.png")));
		Rectangle imageRect2 = new Rectangle(100, 100);
		imageRect2.setFill(new ImagePattern(new Image("strawberry.png")));
		Rectangle imageRect3 = new Rectangle(100, 100);
		imageRect3.setFill(new ImagePattern(new Image("papaya.png")));
		Rectangle imageRect4 = new Rectangle(100, 100);
		imageRect4.setFill(new ImagePattern(new Image("banana.png")));
                               
		// lay everything out on grid, overlap text with respective buttons
		grid.add(title, 1, 0);
		grid.add(button, 1, 1);
		grid.add(buttonText, 1, 1);
		grid.add(button2, 1, 2);
		grid.add(buttonText2, 1, 2);
		grid.add(imageRect, 0, 1);
		grid.add(imageRect2, 2, 1);
		grid.add(imageRect3, 0, 2);
		grid.add(imageRect4, 2, 2);

		// configure allignment and padding
		grid.setVgap(50);
		grid.setHgap(50);
		grid.setPadding(new Insets(50, 50, 50, 50));
		grid.setAlignment(Pos.CENTER);

		// center text within their buttons
		grid.setHalignment(title, HPos.CENTER);
		grid.setHalignment(buttonText, HPos.CENTER);
		grid.setHalignment(buttonText2, HPos.CENTER);

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

	// game main function, main loop handled here
	public void gameMain(Stage stage) {

		// main pane
        Pane pane = new Pane();
		
		// add and configure score text
		Text scoreText = new Text("Score: 0");
		scoreText.setX(30);
		scoreText.setY(40);
		scoreText.setScaleX(2);
		scoreText.setScaleY(2);
		scoreText.setFill(Color.WHITE);
		pane.getChildren().add(scoreText);

		// add and configure life text
		Text lifeText = new Text("Life: " + life);
		lifeText.setX(130);
		lifeText.setY(40);
		lifeText.setScaleX(2);
		lifeText.setScaleY(2);
		lifeText.setFill(Color.PINK);
		pane.getChildren().add(lifeText);

		// load fruit assets in advance
		Image[] fruitImages = new Image[] {
			new Image("apple.png"),
			new Image("papaya.png"),
			new Image("banana.png"),
			new Image("strawberry.png")
		};

		Image bombImage = new Image("bomb.png");
		Image explosionImage = new Image("explosion.png");
		Image splatImage = new Image("splat.png");

		// cursor init
		Image cursorImage = new Image("cursor.png");
		Rectangle cursor = new Rectangle(125, 100);
		cursor.setFill(new ImagePattern(cursorImage));
		pane.getChildren().add(cursor);

		// main loop animation timer
		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				globalTimer++;

				// cursor update
				if(mousePressed)
					cursor.setRotate(Math.sin(globalTimer*0.4f)*30.f);
				else cursor.setRotate(-30);

				// decrease fruit timer, spawn enemy once it hits zero
				fruitTimer--;
				if(fruitTimer <= 0) {

					// reset fruit timer based on formula that takes screen width into account for fair spawning
					fruitTimer = (int)((fruitTimerCap+20)*(1.4 - Math.sqrt(pane.getWidth()/1920.0)));
					
					// decrease timer cap to increase difficulty over time
					if(--fruitTimerCap < 25) {
						fruitTimerCap = 25;
					}
					
					// select random projetcile to spawn
					double choice = Math.random();
					if(choice < 0.2) {

						// spawn bomb (20%)
						projectiles.add(new Bomb(bombImage, explosionImage, pane));

					} else {

						// spawn fruit (80%)
						projectiles.add(new Fruit(fruitImages, splatImage, pane));
					}
				}

				// update all projectiles
				for(int i = 0; i < projectiles.size(); i++) {

					// call update
					projectiles.get(i).update();

					// check if proj intersects cursor
					if(mousePressed && projectiles.get(i).containsPoint(new Point2D(cursor.getX()+65, cursor.getY()+10))) {
						projectiles.get(i).onContact();

						// struck, perform action based on if its good or bad
						if(projectiles.get(i).isBad()) {
							lifeText.setText("Life: "+ --life);
							if(life < 1) {
								reset(pane);
								gameOverMain(stage);
								this.stop();
								return;
							}
						} else {
							scoreText.setText("Score: " + ++scoreCounter);
						}
					}

					if(projectiles.get(i).shouldRemove()) {
						projectiles.remove(i--);
						continue;
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
		while(projectiles.size() > 0) 
			projectiles.remove(0);

		// set timer variables to default
		fruitTimer = 30;
		fruitTimerCap = 85;

		scene.setCursor(Cursor.DEFAULT);
		globalTimer = 0;
		mousePressed = false;
		life = 3;
	}

	// main function, begins gui
    public static void main(String[] args) {
        launch(args);
    }
}
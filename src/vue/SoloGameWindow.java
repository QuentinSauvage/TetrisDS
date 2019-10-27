package vue;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jeu.Plateau;
import jeu.PlateauSolo;

public class SoloGameWindow extends GameWindow {

	/**
	 * Plateau a afficher
	 */
	private Plateau plateau;
	/**
	 * Le BorderPane
	 */
	private BorderPane pane;
	/**
	 * La scene
	 */
	private Scene scene;
	/**
	 * Label correspondant au score
	 */
	private Label score;
	/**
	 * Label correspondant au nombre de lignes creees
	 */
	private Label lignes;
	/**
	 * Verrou pour la pause
	 */
	private final Object pauseLock = new Object();
	/**
	 * Booleen indiquant si le jeu est en pause
	 */
	private boolean pause;
	
	public SoloGameWindow() {
		super();
		this.plateau = new PlateauSolo();
		this.pane = new BorderPane();
		BorderPane bp = new BorderPane();
		BackgroundImage bg = new BackgroundImage(new Image("mario.png",660,505,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		this.pane.setBackground(new Background(bg));
		score = new Label("SCORE : ");
		this.pane.setTop(score);
		lignes = new Label("0");
		bp.setCenter(lignes);
		bp.setBottom(score);
		BorderPane.setAlignment(bp.getBottom(), Pos.TOP_CENTER);
		bp.setBorder(new Border(new BorderStroke(Color.WHITE, 
	    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		this.pane.setLeft(bp);
		pause=false;
	}

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		scene = new Scene(pane,660,505);
		primaryStage.setTitle("Tetris DS - Mode Standard");
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				close = true;
			}
		});
		update();
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(!pause) {
					switch (event.getCode()) {
					case UP:
						plateau.rotation();
						break;
					case DOWN:
						plateau.descendreTout();
						break;
					case LEFT:
						plateau.deplacementGauche();
						break;
					case RIGHT:
						plateau.deplacementDroite();
						break;
					case CONTROL:
						if (!plateau.defaite()) {
							plateau.descendre();
							update();
						}
						break;
					case H:
						plateau.echange();
						break;
					case P:
						pause=true;
						score.setText("PAUSE");
						break;
					default:
						break;
					}
				} else if(pause && event.getCode() == javafx.scene.input.KeyCode.P) {
					pause=false;
				}
				update();
			}
		});
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				while (!plateau.defaite() && !close) {
					synchronized (pauseLock) {
						if(!pause) {
							plateau.descendre();
							Platform.runLater(() -> update());
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				scene.setOnKeyPressed(null);
				Platform.runLater(() -> gameOver());
				return null;
			}
		};
		new Thread(task).start();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Permet de mettre a jour l'application
	 */
	@Override
	protected void update() {
		if(pause) {
			score.setText("PAUSE");
		} else {
			score.setText("Score : " + Integer.toString(plateau.getScore().getPoints()));
		}
		((BorderPane) pane.getLeft()).setTop(plateau.getInventaire().getHoldNode());
		lignes.setText(Integer.toString(plateau.getNbLignes()));
		pane.setCenter(plateau.getNode());
		pane.setRight(plateau.getInventaire().getStockNode());
	}

	/**
	 * Permet d'afficher le game over
	 */
	@Override
	protected void gameOver() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Tetris DS - Partie terminée");
		alert.setHeaderText(null);
		alert.setContentText("Vous avez perdu !\nScore final : " + plateau.getScore().getPoints());
		alert.showAndWait();
		stage.close();
	}
}

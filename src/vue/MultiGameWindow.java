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
import jeu.PlateauMulti;
import jeu.PlateauSolo;

public class MultiGameWindow extends GameWindow {

	/**
	 * Plateau du joueur en haut
	 */
	private Plateau plateauTop;
	/**
	 * Plateau du joueur en bas
	 */
	private Plateau plateauBot;
	/**
	 * BorderPane du haut
	 */
	private BorderPane top;
	/**
	 * BorderPane du bas
	 */
	private BorderPane bot;
	/**
	 * Label correspondant au score du joueur 1
	 */
	private Label score1;
	/**
	 * Label correspondant au nombre de lignes creees par le joueur 1
	 */
	private Label lignes1;
	/**
	 * Label correspondant au score du joueur 2
	 */
	private Label score2;
	/**
	 * Label correspondant au nombre de lignes creees par le joueur 2
	 */
	private Label lignes2;
	/**
	 * Verrou pour la pause
	 */
	private final Object pauseLock = new Object();
	/**
	 * Booleen indiquant si le jeu est en pause
	 */
	private boolean pause;

	public MultiGameWindow() {
		super();
		this.plateauTop = new PlateauSolo();
		this.plateauBot = new PlateauMulti(plateauTop);
		plateauTop.setAdversaire(plateauBot);
		BackgroundImage bg = new BackgroundImage(new Image("mario.png",660,505,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		this.top = new BorderPane();
		this.bot = new BorderPane();
		BorderPane bpTop = new BorderPane();
		BorderPane bpBot = new BorderPane();
		
		lignes1 = new Label("0");
		score1 = new Label("SCORE : ");
		this.top.setTop(score1);
		bpTop.setCenter(lignes1);
		bpTop.setBottom(score1);
		bpTop.setBorder(new Border(new BorderStroke(Color.WHITE, 
			    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		BorderPane.setAlignment(bpTop.getBottom(), Pos.TOP_CENTER);
		top.setLeft(bpTop);
		this.top.setBackground(new Background(bg));
		
		lignes2 = new Label("0");
		score2 = new Label("SCORE : ");
		this.bot.setBottom(score2);
		bpBot.setCenter(lignes2);
		bpBot.setBottom(score2);
		BorderPane.setAlignment(bpBot.getBottom(), Pos.TOP_CENTER);
		bpBot.setBorder(new Border(new BorderStroke(Color.WHITE, 
			    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		bot.setLeft(bpBot);
		this.bot.setBackground(new Background(bg));
		pause=false;
	}

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		Scene scene = new Scene(new BorderPane(), 660, 1010);
		primaryStage.setTitle("Tetris DS - Mode Push");
		((BorderPane) scene.getRoot()).setTop(top);
		((BorderPane) scene.getRoot()).setBottom(bot);
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
						plateauTop.rotation();
						break;
					case DOWN:
						if (!plateauTop.defaite()) {
							plateauTop.descendreTout();
							update();
						}
						break;
					case LEFT:
						plateauTop.deplacementGauche();
						break;
					case RIGHT:
						plateauTop.deplacementDroite();
						break;
					case CONTROL:
						if (!plateauTop.defaite()) {
							plateauTop.descendre();
							update();
						}
						break;
					case H:
						plateauTop.echange();
						break;
					case S:
						plateauBot.rotation();
						break;
					case Z:
						if (!plateauBot.defaite()) {
							plateauBot.descendreTout();
							update();
						}
						break;
					case D:
						plateauBot.deplacementGauche();
						break;
					case Q:
						plateauBot.deplacementDroite();
						break;
					case A:
						plateauBot.echange();
						break;
					case E:
						if (!plateauBot.defaite()) {
							plateauBot.descendre();
							update();
						}
						break;
					case P:
						pause=true;
						score1.setText("PAUSE");
						score2.setText("PAUSE");
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
				while (!plateauTop.defaite() && !plateauBot.defaite() && !close) {
					synchronized (pauseLock) {
						if(!pause) {
							plateauTop.descendre();
							plateauBot.descendre();
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
			score1.setText("PAUSE");
			score2.setText("PAUSE");
		} else {
			score1.setText("Score : " + Integer.toString(plateauTop.getScore().getPoints()));
			score2.setText("Score : " + Integer.toString(plateauBot.getScore().getPoints()));
		}
		((BorderPane) top.getLeft()).setTop(plateauTop.getInventaire().getHoldNode());
		top.setCenter(plateauTop.getNode());
		top.setRight(plateauTop.getInventaire().getStockNode());
		lignes1.setText(Integer.toString(plateauTop.getNbLignes()));
		((BorderPane) bot.getLeft()).setTop(plateauBot.getInventaire().getHoldNode());
		bot.setCenter(plateauBot.getNode());
		bot.setRight(plateauBot.getInventaire().getStockNode());
		lignes2.setText(Integer.toString(plateauBot.getNbLignes()));
	}

	/**
	 * Permet d'afficher le game over
	 */
	@Override
	protected void gameOver() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Tetris DS - Partie terminée");
		alert.setHeaderText(null);
		alert.setContentText("Partie terminée !\nJoueur 1 : " + plateauTop.getScore().getPoints() + "\nJoueur 2 : "
				+ plateauBot.getScore().getPoints());
		alert.showAndWait();
		stage.close();
	}
}

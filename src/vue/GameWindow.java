package vue;

import javafx.application.Application;
import javafx.stage.Stage;

public abstract class GameWindow extends Application {
	/**
	 * Stage de l'application
	 */
	protected Stage stage;

	/**
	 * Boolean correspondant a l'etat de fermeture de la fenetre
	 */
	protected boolean close = false;

	/**
	 * Permet d'afficher le game over
	 */
	protected abstract void gameOver();

	/**
	 * Permet de mettre a jour l'application
	 */
	protected abstract void update();
}

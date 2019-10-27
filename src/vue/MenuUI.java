package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuUI extends Application {

	/**
	 * Le stage representant le jeu
	 */
	public static Stage stage;
	
	/**
	 * La scene correspondant au menu
	 */
	public static Scene scene;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		new Options().start(new Stage());
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Tetris DS - Menu");
		stage.show();
	}

	/**
	 * @param args
	 *       	Arguments de la ligne de commande
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

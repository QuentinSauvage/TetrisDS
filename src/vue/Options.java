package vue;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Options extends Application {

	public static Scene scene;
	
	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Options.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Tetris DS - Options");
	}
}

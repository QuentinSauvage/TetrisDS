package vue;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ControllerMenu implements Initializable {

	@FXML
	private void launchGame1(ActionEvent event) {
		new SoloGameWindow().start(new Stage());
		Node n = (Node) event.getSource();
		n.getScene().getWindow().hide();
	}

	@FXML
	private void launchGame2(ActionEvent event) {
		new MultiGameWindow().start(new Stage());
		Node n = (Node) event.getSource();
		n.getScene().getWindow().hide();
	}

	@FXML
	private void displayOptions(ActionEvent event) throws IOException {
		MenuUI.stage.setScene(Options.scene);
	}

	@FXML
	private void close(ActionEvent event) {
		Platform.exit();
	}
	
	@FXML
	private void closeOptions(ActionEvent event) {
		MenuUI.stage.setScene(MenuUI.scene);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		return;
	}

}

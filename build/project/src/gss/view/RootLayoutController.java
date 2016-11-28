package gss.view;

import java.io.File;

import gss.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.FileChooser;

public class RootLayoutController {
	public static MainApp mainApp;

	@FXML
	private void handleNew() {
		mainApp.getPersonData().clear();
		mainApp.setPersonFile(null);
	}

	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show open file dialog.
		File file = fileChooser.showOpenDialog(mainApp.getStage());

		if (file != null)
			mainApp.loadPersonDataFromFile(file);
	}
	@FXML
	private void handleSave() {
		File personFile = mainApp.getPersonFile();
		if (personFile != null) {
			mainApp.savePersonDataToFile(personFile);
		} else {
			handleSaveAs();
		}
	}

	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showSaveDialog(mainApp.getStage());
		if (file != null) {
			if (!file.getPath().endsWith(".xml"))
				file = new File(file.getPath() + ".xml");
			mainApp.savePersonDataToFile(file);
		}
	}
	@FXML
	private void handleShowBirthdayStatistics(){
		mainApp.showBirthdayStatistics();
	}

	@FXML
	private void handleAbout() {
		Dialog<?> dialog = new Dialog<>();
		dialog.setTitle("About");
		dialog.setContentText("This program is created by JavaFX 2.0\nAuthor:Gss");
		dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
		dialog.show();
	}

	@FXML
	private void handleExit() {
		System.exit(0);
	}
}

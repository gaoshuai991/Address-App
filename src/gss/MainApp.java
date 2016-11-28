package gss;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import gss.model.Person;
import gss.model.PersonListWrapper;
import gss.util.DialogUtil;
import gss.view.BirthdayStatisticsController;
import gss.view.PersonEditDialogController;
import gss.view.PersonOverviewController;
import gss.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage stage;
	private BorderPane rootLayout;
	private ObservableList<Person> personData = FXCollections.observableArrayList();

	public MainApp() {
		// Add some sample data
		personData.add(new Person("Hans", "Muster"));
		personData.add(new Person("Ruth", "Mueller"));
		personData.add(new Person("Heinz", "Kurz"));
		personData.add(new Person("Cornelia", "Meier"));
		personData.add(new Person("Werner", "Meyer"));
		personData.add(new Person("Lydia", "Kunz"));
		personData.add(new Person("Anna", "Best"));
		personData.add(new Person("Stefan", "Meier"));
		personData.add(new Person("Martin", "Mueller"));
	}

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("GssApp");
		this.stage.getIcons().add(new Image("file:Resource/Images/icon.png"));

		initRootLayout();

		showPersonView();
	}

	public ObservableList<Person> getPersonData() {
		return this.personData;
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			// Show the scene containing the root layout.
			stage.setScene(new Scene(rootLayout));
			// Give the controller access to the main app.
			RootLayoutController.mainApp = this;

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Try to load the last opened person file.
		File file = getPersonFile();
		if (file != null)
			loadPersonDataFromFile(file);
	}

	public void showPersonView() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			rootLayout.setCenter(personOverview);

			// Give the controller access to the main app.
			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean showPersonEditDialog(Person person) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			dialogStage.setScene(new Scene(page));

			PersonEditDialogController controller = loader.getController();
			controller.setStage(dialogStage);
			controller.setPerson(person);

			dialogStage.showAndWait();

			return controller.isOk();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	public Stage getStage() {
		return stage;
	}

	public File getPersonFile() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	public void setPersonFile(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
			// Update the stage title.
			stage.setTitle("GssApp - " + file.getPath());
		} else {
			prefs.remove("filePath");
			stage.setTitle("GssApp");
		}
	}

	public void loadPersonDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			// Reading XML from the file and unmarshalling.
			PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
			personData.clear();
			personData.addAll(wrapper.getPersons());
			// Save the file path to the registry.
			setPersonFile(file);
		} catch (JAXBException e) {
			DialogUtil.getDialog("Error", "Could not load data from file:\n" + file.getPath()).show();
		}
	}

	public void savePersonDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our person data.
			PersonListWrapper wrapper = new PersonListWrapper();
			wrapper.setPersons(personData);
			// Mashalling and saving XML to the file.
			m.marshal(wrapper, file);
			// Saving file path to the registry.
			setPersonFile(file);
		} catch (JAXBException e) {
			DialogUtil.getDialog("Error", "Could not save data to file:\n" + file.getPath()).show();
		}

	}
	public void showBirthdayStatistics(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Birthday Statistics");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			dialogStage.getIcons().add(new Image("file:Resource/Images/icon.png"));
			dialogStage.setScene(new Scene(page));
			
			BirthdayStatisticsController controller = loader.getController();
			controller.setPersonData(personData);
			
			
			dialogStage.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

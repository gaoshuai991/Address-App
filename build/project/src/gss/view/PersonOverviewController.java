package gss.view;

import gss.MainApp;
import gss.model.Person;
import gss.util.DateUtil;
import gss.util.DialogUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> lastNameColumn;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label schoolLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label telLabel;
	@FXML
	private Label birthdayLabel;

	private MainApp mainApp;

	/**
	 * The Constructor. The Constructor is called before the initialize()
	 * method.
	 */
	public PersonOverviewController() {
	}

	/**
	 * Initializes the controller class, This method is automatically called
	 * after thw fxml file has been loaded.
	 */
	@FXML
	public void initialize() {
		// Initialize the person table with the two columns.
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

		showPersonDetails(null);

		personTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
	}

	/**
	 * Is called by MainApp to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		personTable.setItems(mainApp.getPersonData());
	}

	public void showPersonDetails(Person person) {
		if (person != null) {
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			schoolLabel.setText(person.getSchool());
			cityLabel.setText(person.getCity());
			telLabel.setText(person.getTel());
			birthdayLabel.setText(DateUtil.format(person.getBirthday()));
		} else {
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			schoolLabel.setText("");
			cityLabel.setText("");
			telLabel.setText("");
			birthdayLabel.setText("");
		}
	}

	@FXML
	public void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0)
			personTable.getItems().remove(selectedIndex);
		else {
			DialogUtil.getDialog("Error Message", "No selected!").show();
		}
	}

	@FXML
	public void handleNew() {
		Person person = new Person();
		if (mainApp.showPersonEditDialog(person))
			mainApp.getPersonData().add(person);
	}
	@FXML
	public void handleEdit(){
		Person person = personTable.getSelectionModel().getSelectedItem();
		if(person != null){
			if (mainApp.showPersonEditDialog(person))
				showPersonDetails(person);
		}else{
			DialogUtil.getDialog("Error Message", "No selected!").show();;
		}
	}

}

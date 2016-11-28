package gss.view;

import gss.model.Person;
import gss.util.DateUtil;
import gss.util.DialogUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonEditDialogController {
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField schoolField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField telField;
	@FXML
	private TextField birthdayField;

	private Stage dialogStage;
	private Person person;
	private boolean isOk;

	@FXML
	private void initialize() {
	}

	public void setStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPerson(Person person) {
		this.person = person;
		firstNameField.setText(person.getFirstName());
		lastNameField.setText(person.getLastName());
		schoolField.setText(person.getSchool());
		cityField.setText(person.getCity());
		telField.setText(person.getTel());
		birthdayField.setText(DateUtil.format(person.getBirthday()));
		birthdayField.setPromptText("yyyy-MM-dd");
	}

	public boolean isOk() {
		return this.isOk;
	}

	private boolean inputValid() {
		StringBuffer msg = new StringBuffer();
		if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
			msg.append("No valid first name!\n");
		}
		if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
			msg.append("No valid last name!\n");
		}
		if (schoolField.getText() == null || schoolField.getText().length() == 0) {
			msg.append("No valid school!\n");
		}

		if (cityField.getText() == null || cityField.getText().length() == 0) {
			msg.append("No valid city!\n");
		}
		if (telField.getText() == null || telField.getText().length() == 0) {
			msg.append("No valid telephone!\n");
		}

		if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
			msg.append("No valid birthday!\n");
		} else if (!DateUtil.validDate(birthdayField.getText())) {
			msg.append("No valid birthday. Use the format yyyy-MM-dd!\n");
		}
		if (msg.length() != 0) {
			DialogUtil.getDialog("Error Message", "Please correct invalid fields.").show();
			return false;
		} else
			return true;

	}

	@FXML
	public void handleOk() {
		if (inputValid()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setSchool(schoolField.getText());
			person.setCity(cityField.getText());
			person.setTel(telField.getText());
			person.setBirthday(DateUtil.parse(birthdayField.getText()));

			isOk = true;
			dialogStage.close();
		}
	}
	@FXML
	private void handleCancle(){
		dialogStage.close();
	}

}

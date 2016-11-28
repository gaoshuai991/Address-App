package gss.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import gss.util.LocalDateAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	private final StringProperty firstName;
	private final StringProperty lastName;
	private final StringProperty school;
	private final StringProperty city;
	private final StringProperty tel;
	private final ObjectProperty<LocalDate> birthday;

	public Person() {
		this(null, null);
	}

	public Person(String firstName, String lastName) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		// Some initial dummy data, just for convenient testing.
		this.school = new SimpleStringProperty("QUST");
		this.city = new SimpleStringProperty("QingDao");
		this.tel = new SimpleStringProperty("13220879735");
		this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(2016, 11, 11));
	}

	public String getFirstName() {
		return this.firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return this.lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public StringProperty lastNameProperty() {
		return this.lastName;
	}

	public String getSchool() {
		return this.school.get();
	}

	public void setSchool(String school) {
		this.school.set(school);
	}

	public StringProperty schoolProperty() {
		return school;
	}

	public String getTel() {
		return this.tel.get();
	}

	public void setTel(String tel) {
		this.tel.set(tel);
	}

	public StringProperty telProperty() {
		return tel;
	}

	public String getCity() {
		return city.get();
	}

	public void setCity(String city) {
		this.city.set(city);
	}

	public StringProperty cityProperty() {
		return city;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getBirthday() {
		return birthday.get();
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
	}

	public ObjectProperty<LocalDate> birthdayProperty() {
		return birthday;
	}

}

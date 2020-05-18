package gui;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DB;
import db.DbException;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.dao.impl.CompanyDaoJDBC;
import model.dao.impl.InvestorDaoJDBC;
import model.entities.Investor;
import model.exception.ValidationException;

public class InvestorFormController implements Initializable {

	private Investor entity = new Investor();
	
	@FXML private TextField txtFirstName;
	
	@FXML private TextField txtLastName;
	
	@FXML private TextField txtEmail;
	  
	@FXML private TextField txtConfirmationEmail;
	
	@FXML private TextField txtPassword;
	
	@FXML private TextField txtConfirmationPassword;
	
	@FXML private DatePicker datepickerBirthDate;
	
	@FXML private ComboBox<String> comboboxCountry;
	
	@FXML private Button btUploadIdentification;
	
	@FXML private Button btContinue;
	
	private Investor getFormData() {
		Investor obj = new Investor();
		
		ValidationException exception = new ValidationException("Validation error");
		
		if (txtFirstName == null || txtFirstName.getText().trim().equals("")) {
			exception.addError("First Name", "Field can't be empty");
		}
		
		if (txtLastName == null || txtLastName.getText().trim().equals("")) {
			exception.addError("Last Name", "Field can't be empty");
		}
		
		
		if (txtEmail == null || txtEmail.getText().trim().equals("")) {
			exception.addError("Email", "Field can't be empty");
		}
		
		if (txtConfirmationEmail == null || txtConfirmationEmail.getText().trim().equals("")) {
			exception.addError("Confirmation Email", "Field can't be empty");
		}
		
		if (txtConfirmationEmail.getText().trim().equals("") != txtEmail.getText().trim().equals("")) {
			exception.addError("Confirmation Email", "This email is different");
		}
		
		if (txtPassword == null || txtPassword.getText().trim().equals("")) {
			exception.addError("Password", "Field can't be empty");
		}
		
		if (txtConfirmationPassword == null || txtConfirmationPassword.getText().trim().equals("")) {
			exception.addError("Confirmation Password", "Field can't be empty");
		}
		
		if (txtConfirmationPassword.getText().trim().equals("") != txtPassword.getText().trim().equals("")) {
			exception.addError("Confirmation Password", "This password is different");
		}
		
		obj.setId(3);
		obj.setFirstName(txtFirstName.getText());
		obj.setLastName(txtLastName.getText());
		obj.setEmail(txtEmail.getText());
		obj.setPassword(txtPassword.getText());
		obj.setCountry(comboboxCountry.getValue());
		obj.setBirthDate(datepickerBirthDate.getValue().toString());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}
	
	@FXML
	public void onBtContinueAction() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		try {
			entity = getFormData();
			InvestorDaoJDBC jdbcDao = new InvestorDaoJDBC(DB.getConnection());
	        jdbcDao.insert(entity);	
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	ObservableList<String> countries = FXCollections.observableArrayList("Portugal", "Spain", "Italy");
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		comboboxCountry.setItems(countries);
	}

	private void setErrorMessages(Map<String, String>errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("First Name")) {
			txtFirstName.setText(errors.get("First Name"));
		}
		if (fields.contains("Last Name")) {
			txtLastName.setText(errors.get("Last Name"));
		}
		if (fields.contains("Email")) {
			txtEmail.setText(errors.get("Email"));
		}
		if (fields.contains("Confirmation Email")) {
			txtConfirmationEmail.setText(errors.get("Confirmation Email"));
		}
		if (fields.contains("Password")) {
			txtPassword.setText(errors.get("Password"));
		}
		if (fields.contains("Confirmation Password")) {
			txtConfirmationPassword.setText(errors.get("Confirmation Password"));
		}	
	}
}

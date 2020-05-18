package gui;
 
import java.io.File;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.dao.impl.CompanyDaoJDBC;
import model.entities.Company;
import model.exception.ValidationException;
import model.services.CompanyService;


public class CompanyFormController implements Initializable {

	private Company entity = new Company();
	private CompanyService service;
	
	@FXML private TextField txtCompanyName;
	
	@FXML private TextField txtCeoName;
	
	@FXML private TextField txtMobile;
	
	@FXML private TextField txtCountryMobileCode;
	
	@FXML private TextField txtEmail;
	
	@FXML private TextField txtConfirmationEmail;
	
	@FXML private Label labelEmailError;
	
	@FXML private Label labelEmailConfirmationError;
	
	@FXML private TextField txtPassword;
	
	@FXML private TextField txtConfirmationPassword;
	
	@FXML private Label labelPasswordError;
	
	@FXML private Label labelPasswordConfirmationError;
	
	@FXML private ComboBox<String> comboboxCountry;
	
	@FXML private DatePicker datepickerFoundationDate;
	
	@FXML private TextField txtWebsite;
	
	@FXML private Label labelWebsiteError;
	
	@FXML private ComboBox<String> comboboxIndustry;
	
	@FXML private ChoiceBox<String> choiceboxGrossSales;
	
	@FXML private Button btUploadCompanyName;
	
	@FXML private Button btUploadCeoName;
	
	@FXML private Button btUploadFoundationDate;
	
	@FXML private Button btUploadGrossSales;
	
	@FXML private Button btSave;
	
	public void setCompanyService(CompanyService service) {
		this.service = service;
	}
	
	public void onBtUploadCompanyNameAction() {
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);
	}
	
	public void onBtUploadCeoNameAction() {
		
	}
	
	public void onBtUploadFoundationDateAction() {
		
	}
	
	public void onBtUoloadGrossSalesAction() {
		
	}
	
	private Company getFormData() {
		Company obj = new Company();
		
		ValidationException exception = new ValidationException("Validation error");
		
		if (txtCompanyName == null || txtCompanyName.getText().trim().equals("")) {
			exception.addError("Company Name", "Field can't be empty");
		}
		
		if (txtCeoName == null || txtCeoName.getText().trim().equals("")) {
			exception.addError("Ceo Name", "Field can't be empty");
		}
		
		if (txtMobile == null || txtMobile.getText().trim().equals("")) {
			exception.addError("Mobile", "Field can't be empty");
		}
		
		if (txtCountryMobileCode == null || txtCountryMobileCode.getText().trim().equals("")) {
			exception.addError("Country Mobile Code ", "Field can't be empty");
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
		
		if (txtWebsite == null || txtWebsite.getText().trim().equals("")) {
			exception.addError("Website", "Field can't be empty");
		}
		
		obj.setId(3);
		obj.setCompanyName(txtCompanyName.getText());
		obj.setCeoName(txtCeoName.getText());
		obj.setPhoneNumber(txtMobile.getText());
		obj.setEmail(txtEmail.getText());
		obj.setPassword(txtPassword.getText());
		obj.setCountry(comboboxCountry.getValue());
		obj.setFoundationDate(datepickerFoundationDate.getValue().toString());
		obj.setWebsite(txtWebsite.getText());
		obj.setIndustry(comboboxIndustry.getValue());
		obj.setGrossSales(choiceboxGrossSales.getValue());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}
	
	@FXML
	public void onBtSaveAction() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		try {
			entity = getFormData();
			CompanyDaoJDBC jdbcDao = new CompanyDaoJDBC(DB.getConnection());
	        jdbcDao.insert(entity);	
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	public void setCompany(Company entity){
		this.entity = entity;
	}
	
	ObservableList<String> countries = FXCollections.observableArrayList("Portugal", "Spain", "Italy");
	ObservableList<String> grossSales = FXCollections.observableArrayList("Under 100.000","Between 100.000 and 200.000", "Above 200.00");
	ObservableList<String> industry = FXCollections.observableArrayList("Cars", "Tecnology", "Science", "Medicine", "Food");
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		comboboxCountry.setItems(countries);
		choiceboxGrossSales.setItems(grossSales);
		comboboxIndustry.setItems(industry);
	}
	
	private void setErrorMessages(Map<String, String>errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("Company Name")) {
			txtCompanyName.setText(errors.get("Company Name"));
		}
		if (fields.contains("Ceo Name")) {
			txtCeoName.setText(errors.get("Ceo Name"));
		}
		if (fields.contains("Mobile")) {
			txtMobile.setText(errors.get("Mobile"));
		}
		if (fields.contains("Country Mobile Code")) {
			txtCountryMobileCode.setText(errors.get("Country Mobile Code"));
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
		if (fields.contains("Website")) {
			txtWebsite.setText(errors.get("Website"));
		}	
	}
}

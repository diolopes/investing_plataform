package gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import db.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dao.impl.CompanyDaoJDBC;

public class CompanyLoginController implements Initializable  {

	public CompanyDaoJDBC loginModel = new CompanyDaoJDBC(DB.getConnection());
	
	@FXML private TextField txtEmail;
	
	@FXML private TextField txtPassword;
	
	@FXML private Button btLogin;
	
	@FXML private Label lblStatus;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if(loginModel.isDbConnected()) {
			lblStatus.setText("Connected");
		}
		else {
			lblStatus.setText("Not Connected");
		}
	}

	public void Login (ActionEvent event) {
		try {
			if (loginModel.isLogin(txtEmail.getText(), txtPassword.getText())) {
				lblStatus.setText("email and password is correct");
			}
			else {
				lblStatus.setText("email or password is not correct");
			}
		}
		catch (SQLException e) {
			lblStatus.setText("email or password is not correct");
			e.printStackTrace();
		}
	}
}

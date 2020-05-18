package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemAboutUs;

	@FXML
	private MenuItem menuItemCompanyLogin;

	@FXML
	private MenuItem menuItemInvestorLogin;

	@FXML
	private MenuItem menuItemCompanyRegister;

	@FXML
	private MenuItem menuItemInvestorRegister;

	@FXML
	private MenuItem menuItemContactUs;

	@FXML
	public void onmenuItemAboutUsAction() {
		loadView("/gui/About.fxml", x -> {});
	}

	@FXML
	public void onMenuItemCompanyLoginAction() {
		loadView("/gui/CompanyLogin.fxml", x -> {});
	}

	@FXML
	public void onMenuItemInvestorLoginAction() {
		loadView("/gui/InvestorLogin.fxml", x -> {});
	}

	@FXML
	public void onMenuItemCompanyRegisterAction() {
		loadView("/gui/CompanyForm.fxml", (CompanyFormController controller) -> {});
	}

	@FXML
	public void onMenuItemInvestorRegisterAction() {
		loadView("/gui/InvestorForm.fxml", x -> {});
	}

	@FXML
	public void onMenuItemContactUsAction() {
		System.out.println("onMenuItemContactUsAction");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			AnchorPane newVBox = loader.load();

			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
		} 
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}

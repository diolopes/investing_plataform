package model.services;

import java.util.List;

import model.dao.CompanyDao;
import model.dao.DaoFactory;
import model.entities.Company;

public class CompanyService {

	private CompanyDao dao = DaoFactory.createCompanyDao();

	public List<Company> findAll() {
		return dao.findAll();
	}

	
	
	
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/DepartmentForm.fxml", Utils.currentStage(event)));
			}
		});
	}

}

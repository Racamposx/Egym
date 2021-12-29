package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db_configs.DbIntegrityException;
import gui.forms.ExercicioFormController;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.Exercicio;
import model.services.ExercicioService;

public class ExercicioListController implements Initializable, DataChangeListener {

	private ExercicioService service;

	@FXML
	private TableView<Exercicio> tableViewExercicio;

	@FXML
	private TableColumn<Exercicio, Integer> tableColumnId;

	@FXML
	private TableColumn<Exercicio, String> tableColumnNome;

	@FXML
	private TableColumn<Exercicio, Exercicio> tableColumnEditar;

	@FXML
	private TableColumn<Exercicio, Exercicio> tableColumnRemover;

	@FXML
	private Button btNovo;

	private ObservableList<Exercicio> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Exercicio exercicio = new Exercicio();
		createDialogForm(exercicio, "/gui/forms/ExercicioForm.fxml", parentStage);
	}

	public void setExercicioService(ExercicioService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewExercicio.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		List<Exercicio> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewExercicio.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Exercicio exercicio, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ExercicioFormController controller = loader.getController();
			controller.setExercicio(exercicio);
			controller.setExercicioService(new ExercicioService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Informe os dados do exercicio");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO EXCEPTION", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEditar.setCellFactory(param -> new TableCell<Exercicio, Exercicio>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Exercicio exercicio, boolean empty) {
				super.updateItem(exercicio, empty);
				if (exercicio == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(exercicio, "/gui/forms/ExercicioForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<Exercicio, Exercicio>() {
			private final Button button = new Button("excluir");

			@Override
			protected void updateItem(Exercicio exercicio, boolean empty) {
				super.updateItem(exercicio, empty);
				if (exercicio == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(exercicio));
			}

			
		});
	}
	
	private void removeEntity(Exercicio exercicio) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que quer remover?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(exercicio);
				updateTableView();
			}
			catch(DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
}

package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db_configs.DbIntegrityException;
import gui.forms.PlanoFormController;
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
import model.entidades.Plano;
import model.services.PlanoService;

public class PlanoListController implements Initializable, DataChangeListener {

	private PlanoService service;

	@FXML
	private TableView<Plano> tableViewPlano;

	@FXML
	private TableColumn<Plano, Integer> tableColumnId;

	@FXML
	private TableColumn<Plano, String> tableColumnNome;

	@FXML
	private TableColumn<Plano, Double> tableColumnPreco;

	@FXML
	private TableColumn<Plano, String> tableColumnDescricao;

	@FXML
	private TableColumn<Plano, Plano> tableColumnEditar;

	@FXML
	private TableColumn<Plano, Plano> tableColumnRemover;

	@FXML
	private Button btNovo;

	private ObservableList<Plano> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Plano plano = new Plano();
		createDialogForm(plano, "/gui/forms/PlanoForm.fxml", parentStage);
	}

	public void setPlanoService(PlanoService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		Utils.formatTableColumnDouble(tableColumnPreco, 2);
		tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("Preco"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPlano.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		List<Plano> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewPlano.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Plano plano, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			PlanoFormController controller = loader.getController();
			controller.setPlano(plano);
			controller.setPlanoService(new PlanoService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Informe os dados do plano");
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
		tableColumnEditar.setCellFactory(param -> new TableCell<Plano, Plano>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Plano plano, boolean empty) {
				super.updateItem(plano, empty);
				if (plano == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(plano, "/gui/forms/PlanoForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<Plano, Plano>() {
			private final Button button = new Button("excluir");

			@Override
			protected void updateItem(Plano plano, boolean empty) {
				super.updateItem(plano, empty);
				if (plano == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(plano));
			}

			
		});
	}
	
	private void removeEntity(Plano plano) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que quer remover?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(plano);
				updateTableView();
			}
			catch(DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
}

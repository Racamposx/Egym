package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db_configs.DbIntegrityException;
import gui.forms.ClienteFormController;
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
import model.entidades.Cliente;
import model.services.ClienteService;
import model.services.PlanoService;

public class ClienteListController implements Initializable, DataChangeListener {

	private ClienteService service;

	@FXML
	private TableView<Cliente> tableViewCliente;

	@FXML
	private TableColumn<Cliente, Integer> tableColumnId;

	@FXML
	private TableColumn<Cliente, String> tableColumnNome;

	@FXML
	private TableColumn<Cliente, String> tableColumnNomeMeio;

	@FXML
	private TableColumn<Cliente, String> tableColumnUltimoNome;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnCpf;
	
	@FXML
	private TableColumn<Cliente, Date> tableColumnDataNasc;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnTelefone;
	
	@FXML
	private TableColumn<Cliente, Cliente> tableColumnEditar;
	
	@FXML
	private TableColumn<Cliente, Cliente> tableColumnRemover;

	@FXML
	private Button btNovo;

	private ObservableList<Cliente> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Cliente cliente = new Cliente();
		createDialogForm(cliente, "/gui/forms/ClienteForm.fxml", parentStage);
	}

	public void setClienteService(ClienteService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("primeiroNome"));
		tableColumnNomeMeio.setCellValueFactory(new PropertyValueFactory<>("nomeMeio"));
		tableColumnUltimoNome.setCellValueFactory(new PropertyValueFactory<>("ultimoNome"));
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("Cpf"));
		Utils.formatTableColumnDate(tableColumnDataNasc, "dd/MM/yyyy");
		tableColumnDataNasc.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewCliente.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		List<Cliente> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewCliente.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Cliente cliente, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ClienteFormController controller = loader.getController();
			controller.setCliente(cliente);
			controller.setServices(new ClienteService(), new PlanoService());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Informe os dados do cliente");
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
		tableColumnEditar.setCellFactory(param -> new TableCell<Cliente, Cliente>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Cliente cliente, boolean empty) {
				super.updateItem(cliente, empty);
				if (cliente == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(cliente, "/gui/forms/ClienteForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<Cliente, Cliente>() {
			private final Button button = new Button("excluir");

			@Override
			protected void updateItem(Cliente cliente, boolean empty) {
				super.updateItem(cliente, empty);
				if (cliente == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(cliente));
			}

			
		});
	}
	
	private void removeEntity(Cliente cliente) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que quer remover?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(cliente);
				updateTableView();
			}
			catch(DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
}

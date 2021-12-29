package gui.forms;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db_configs.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entidades.Cliente;
import model.entidades.Plano;
import model.exceptions.ValidationException;
import model.services.ClienteService;
import model.services.PlanoService;

public class ClienteFormController implements Initializable {

	private Cliente entidadeCliente;

	private ClienteService service;

	private PlanoService planoService;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtPrimeiroNome;

	@FXML
	private TextField txtNomeMeio;

	@FXML
	private TextField txtUltimoNome;

	@FXML
	private TextField txtCpf;

	@FXML
	private TextField txtTelefone;

	@FXML
	private DatePicker dpDataNascimento;

	@FXML
	private ComboBox<Plano> comboBoxPlano;

	@FXML
	private Label labelErrorPrimeiroNome;

	@FXML
	private Label labelErrorNomeMeio;

	@FXML
	private Label labelErrorUltimoNome;

	@FXML
	private Label labelErrorCpf;

	@FXML
	private Label labelErrorDataNascimento;

	@FXML
	private Label labelErrorTelefone;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	private ObservableList<Plano> obsList;

	public void setCliente(Cliente entidadeCliente) {
		this.entidadeCliente = entidadeCliente;
	}

	public void setServices(ClienteService service, PlanoService planoService) {
		this.service = service;
		this.planoService = planoService;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	public void updateFormData() {
		if (entidadeCliente == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entidadeCliente.getId()));
		txtPrimeiroNome.setText(entidadeCliente.getPrimeiroNome());
		txtNomeMeio.setText(entidadeCliente.getNomeMeio());
		txtUltimoNome.setText(entidadeCliente.getUltimoNome());
		txtCpf.setText(entidadeCliente.getCpf());
		txtTelefone.setText(entidadeCliente.getTelefone());
		if (entidadeCliente.getDataNasc() != null) {
			dpDataNascimento
					.setValue(LocalDate.ofInstant(entidadeCliente.getDataNasc().toInstant(), ZoneId.systemDefault()));
		}
		if(entidadeCliente.getPlanoCliente() == null) {
			comboBoxPlano.getSelectionModel().selectLast();;
		}
		else {
			comboBoxPlano.setValue(entidadeCliente.getPlanoCliente());
		}
	}

	public void loadAssociatedObjects() {
		List<Plano> list = planoService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxPlano.setItems(obsList);
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entidadeCliente == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entidadeCliente = getFormData();
			service.saveOrUpdate(entidadeCliente);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Erro ao salvar", null, e.getMessage(), AlertType.ERROR);
		} catch (ValidationException e) {
			setErrorMessages(e.getErros());
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Cliente getFormData() {
		Cliente cliente = new Cliente();
		
		ValidationException exception = new ValidationException("Validation error");
		
		cliente.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtPrimeiroNome.getText() == null || txtPrimeiroNome.getText().trim().equals("")) {
			exception.addErrors("primeiroNome", "Informe o primeiro nome!");
		}
		cliente.setPrimeiroNome(txtPrimeiroNome.getText());
		
		if(txtNomeMeio.getText() == null || txtNomeMeio.getText().trim().equals("")) {
			exception.addErrors("nomeMeio", "Informe o nome do meio!");
		}
		cliente.setNomeMeio(txtNomeMeio.getText());
		
		if(txtUltimoNome.getText() == null || txtUltimoNome.getText().trim().equals("")) {
			exception.addErrors("ultimoNome", "Informe o último nome!");
		}
		cliente.setUltimoNome(txtUltimoNome.getText());
		
		if(txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addErrors("cpf", "Informe o último nome!");
		}
		cliente.setCpf(txtCpf.getText());
		
		if(dpDataNascimento.getValue() == null) {
			exception.addErrors("dataNasc", "Informe a data de nascimento!");
		}
		else {
			Instant instant = Instant.from(dpDataNascimento.getValue().atStartOfDay(ZoneId.systemDefault()));
			cliente.setDataNasc(Date.from(instant));
		}
		
		if(txtTelefone.getText() == null || txtTelefone.getText().trim().equals("")) {
			exception.addErrors("telefone", "Informe o telefone!");
		}
		
		cliente.setPlanoCliente(comboBoxPlano.getValue());
		
		
		if(exception.getErros().size() > 0) {
			throw exception;
		}
		
		return cliente;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtPrimeiroNome, 25);
		Constraints.setTextFieldMaxLength(txtNomeMeio, 30);
		Constraints.setTextFieldMaxLength(txtUltimoNome, 40);
		Constraints.setTextFieldMaxLength(txtCpf, 11);
		Utils.formatDatePicker(dpDataNascimento, "dd/MM/YYYY");
		
		initializeComboBoxDepartment();
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelErrorPrimeiroNome.setText((fields.contains("primeiroNome") ? errors.get("primeiroNome") : ""));
		labelErrorNomeMeio.setText((fields.contains("nomeMeio") ? errors.get("nomeMeio") : ""));
		labelErrorUltimoNome.setText((fields.contains("ultimoNome") ? errors.get("ultimoNome") : ""));
		labelErrorCpf.setText((fields.contains("cpf") ? errors.get("cpf") : ""));
		labelErrorDataNascimento.setText((fields.contains("dataNasc") ? errors.get("dataNasc") : ""));
		labelErrorTelefone.setText((fields.contains("telefone") ? errors.get("telefone") : ""));
	}

	private void initializeComboBoxDepartment() {
		Callback<ListView<Plano>, ListCell<Plano>> factory = lv -> new ListCell<Plano>() {
			@Override
			protected void updateItem(Plano item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBoxPlano.setCellFactory(factory);
		comboBoxPlano.setButtonCell(factory.call(null));
	}

}

package gui.forms;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db_configs.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Plano;
import model.exceptions.ValidationException;
import model.services.PlanoService;

public class PlanoFormController implements Initializable{
	
	private Plano entidadePlano;
	
	private PlanoService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtPreco;
	
	@FXML
	private TextField txtDescricao;
	
	@FXML
	private Label labelErrorName; 
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar; 
	
	public void setPlano(Plano entidadePlano) {
		this.entidadePlano = entidadePlano;
	}
	
	public void setPlanoService(PlanoService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	public void updateFormData() {
		if(entidadePlano == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entidadePlano.getId()));
		txtNome.setText(entidadePlano.getNome());
		txtPreco.setText(String.format("%.2f", entidadePlano.getPreco()));
		txtDescricao.setText(entidadePlano.getDescricao());
	}
	
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entidadePlano == null) {
			throw new IllegalStateException("Entity was null"); 
		}
		if(service == null) {
			throw new IllegalStateException("Service was null"); 
		}
		try {
			entidadePlano = getFormData();
			service.saveOrUpdate(entidadePlano);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch(DbException e) {
			Alerts.showAlert("Erro ao salvar", null, e.getMessage(), AlertType.ERROR);
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErros());
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Plano getFormData() {
		Plano plano = new Plano();
		
		ValidationException exception = new ValidationException("Validation error");
		
		plano.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addErrors("nome", "Informe o nome!");
		}
		plano.setNome(txtNome.getText());
		
		if(txtPreco.getText() == null || txtPreco.getText().trim().equals("")) {
			exception.addErrors("preco", "Informe o preço!");
		}
		plano.setPreco(Utils.tryParseToDouble(txtPreco.getText()));
		
		if(txtDescricao.getText() == null || txtDescricao.getText().trim().equals("")) {
			exception.addErrors("descricao", "Informe a descrição!");
		}
		plano.setDescricao(txtDescricao.getText());
		
		if(exception.getErros().size() > 0) {
			throw exception;
		}
		
		return plano;
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
		Constraints.setTextFieldMaxLength(txtNome, 20);
		Constraints.setTextFieldDouble(txtPreco);
		Constraints.setTextFieldMaxLength(txtDescricao, 150);
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> field = errors.keySet();
		
		if(field.contains("nome")) {
			labelErrorName.setText(errors.get("nome"));
		}
		
		else if(field.contains("preco")) {
			labelErrorName.setText(errors.get("preco"));
		}
		
		else if(field.contains("descricao")) {
			labelErrorName.setText(errors.get("descricao"));
		}	
	}
	
	
}

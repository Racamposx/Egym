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
import model.entidades.Exercicio;
import model.exceptions.ValidationException;
import model.services.ExercicioService;

public class ExercicioFormController implements Initializable{
	
	private Exercicio entidadeExercicio;
	
	private ExercicioService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Label labelErrorName; 
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar; 
	
	public void setExercicio(Exercicio entidadeExercicio) {
		this.entidadeExercicio = entidadeExercicio;
	}
	
	public void setExercicioService(ExercicioService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	public void updateFormData() {
		if(entidadeExercicio == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entidadeExercicio.getId()));
		txtNome.setText(entidadeExercicio.getNome());
	}
	
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entidadeExercicio == null) {
			throw new IllegalStateException("Entity was null"); 
		}
		if(service == null) {
			throw new IllegalStateException("Service was null"); 
		}
		try {
			entidadeExercicio = getFormData();
			service.saveOrUpdate(entidadeExercicio);
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

	private Exercicio getFormData() {
		Exercicio exercicio = new Exercicio();
		
		ValidationException exception = new ValidationException("Validation error");
		
		exercicio.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addErrors("nome", "Informe o nome!");
		}
		exercicio.setNome(txtNome.getText());
				
		if(exception.getErros().size() > 0) {
			throw exception;
		}
		
		return exercicio;
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
		Constraints.setTextFieldMaxLength(txtNome, 35);
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> field = errors.keySet();
		
		if(field.contains("nome")) {
			labelErrorName.setText(errors.get("nome"));
		}
	}

}

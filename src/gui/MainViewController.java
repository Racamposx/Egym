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
import javafx.scene.layout.VBox;
import model.services.ClienteService;
import model.services.ExercicioService;
import model.services.PlanoService;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItemPlano;
	
	@FXML
	private MenuItem menuItemCliente;
	
	@FXML
	private MenuItem menuItemFicha;
	
	@FXML
	private MenuItem menuItemTreino;
	
	@FXML
	private MenuItem menuItemExercicio;
	
	@FXML
	private MenuItem menuItemEspecificacao;
	
	@FXML
	private MenuItem menuItemInformacoes;
	
	
	@FXML
	public void onMenuItemPlanoAction() {
		loadView("/gui/PlanoList.fxml", (PlanoListController controller) -> {
			controller.setPlanoService(new PlanoService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemClienteAction() {
		loadView("/gui/ClienteList.fxml", (ClienteListController controller) -> {
			controller.setClienteService(new ClienteService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemFichaAction() {
		//loadView("/gui/PlanoList.fxml");
	}
	
	@FXML
	public void onMenuItemTreinoAction() {
		//loadView("/gui/PlanoList.fxml");
	}
	
	@FXML
	public void onMenuItemExercicioAction() {
		loadView("/gui/ExercicioList.fxml", (ExercicioListController controller) -> {
			controller.setExercicioService(new ExercicioService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemEspecificacaoAction() {
		//loadView("/gui/PlanoList.fxml");
	}
	
	@FXML
	public void onMenuItemInformacoes() {
		loadView("/gui/Sobre.fxml", x -> {});
	}
	
	private <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox =  (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVbox.getChildren()); 
			
			T controller = loader.getController();
			initializingAction.accept(controller);
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
		
	}

}

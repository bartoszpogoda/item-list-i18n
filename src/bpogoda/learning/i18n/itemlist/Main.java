package bpogoda.learning.i18n.itemlist;
	
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import bpogoda.learning.i18n.itemlist.i18n.I18nManager;
import bpogoda.learning.i18n.itemlist.view.MainController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			ResourceBundle resourceBundle = ResourceBundle.getBundle("bpogoda.learning.i18n.itemlist.bundles.Messages", Locale.getDefault());

			loadMainView( resourceBundle);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadMainView(ResourceBundle resourceBundle) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setResources(resourceBundle);
		loader.setLocation(getClass().getResource("view/MainView.fxml"));
		
		BorderPane root = (BorderPane) loader.load();
		Scene scene = new Scene(root,800,600);
		
		I18nManager i18nManager = new I18nManager();
		i18nManager.registerMainViewUrl(getClass().getResource("view/MainView.fxml"));
		i18nManager.registerPrimaryStage(primaryStage);
		
		MainController mainController = (MainController) loader.getController();
		mainController.setI18nManager(i18nManager);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

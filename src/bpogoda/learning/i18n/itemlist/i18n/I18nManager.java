package bpogoda.learning.i18n.itemlist.i18n;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Manages change of Locale for application that is build using one primary
 * stage and one main view with controller.
 * 
 * @author BPOGODA
 *
 */
public class I18nManager {

	private Stage primaryStage;
	private URL mainViewUrl;

	public void registerPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void registerMainViewUrl(URL mainViewUrl) {
		this.mainViewUrl = mainViewUrl;
	}

	public void changeLocale(I18nManagedController controller, Locale newLocale) throws IOException {
		AppStateBundle savedAppState = controller.getAppState();

		ResourceBundle resourceBundle = getResourceBundle(newLocale);

		FXMLLoader loader = new FXMLLoader();
		loader.setResources(resourceBundle);
		loader.setLocation(mainViewUrl);

		BorderPane root = (BorderPane) loader.load();
		Scene scene = new Scene(root, 800, 600);

		I18nManagedController newController = (I18nManagedController) loader.getController();
		newController.setI18nManager(this);
		newController.setAppState(savedAppState);
		newController.setPrimaryStage(primaryStage);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundle.getBundle("bpogoda.learning.i18n.itemlist.bundles.Messages", locale);
	}

}

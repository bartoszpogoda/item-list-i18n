package bpogoda.learning.i18n.itemlist.i18n;

import javafx.stage.Stage;

public interface I18nManagedController {
	void setI18nManager(I18nManager manager);
	
	AppStateBundle getAppState();

	void setAppState(AppStateBundle appStateBundle);
	
	void setPrimaryStage(Stage stage);
}

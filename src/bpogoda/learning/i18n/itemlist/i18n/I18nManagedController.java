package bpogoda.learning.i18n.itemlist.i18n;

public interface I18nManagedController {
	void setI18nManager(I18nManager manager);
	
	AppStateBundle getAppState();

	void setAppState(AppStateBundle appStateBundle);
}

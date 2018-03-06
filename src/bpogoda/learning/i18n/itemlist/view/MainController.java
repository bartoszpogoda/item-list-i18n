package bpogoda.learning.i18n.itemlist.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import bpogoda.learning.i18n.itemlist.Main;
import bpogoda.learning.i18n.itemlist.i18n.AppStateBundle;
import bpogoda.learning.i18n.itemlist.i18n.I18nManagedController;
import bpogoda.learning.i18n.itemlist.i18n.I18nManager;
import bpogoda.learning.i18n.itemlist.model.Item;
import bpogoda.learning.i18n.itemlist.model.ItemList;
import bpogoda.learning.i18n.itemlist.model.ItemListWrapper;
import bpogoda.learning.i18n.itemlist.model.MetricsType;
import bpogoda.learning.i18n.itemlist.view.add.AddItemTypeController;
import bpogoda.learning.i18n.itemlist.view.details.DetailsController;
import bpogoda.learning.i18n.itemlist.view.list.ItemListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController implements I18nManagedController, Initializable {

	private I18nManager i18nManager;

	private ResourceBundle resourceBundle;

	@FXML
	private BorderPane rootPane;

	@FXML
	private RadioMenuItem localeEnRadioItem;

	@FXML
	private RadioMenuItem localePlRadioItem;

	private ItemListController itemListController;
	
	private ItemListWrapper currentItemList;
	
	private Stage primaryStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if(currentItemList == null) {
			currentItemList = new ItemListWrapper();
		}
		
		this.resourceBundle = resources;

		Locale locale = resources.getLocale();

		if (locale.getLanguage().equals("pl")) {
			localePlRadioItem.setSelected(true);
		} else {
			localeEnRadioItem.setSelected(true);
		}

		localeEnRadioItem.setUserData(new Locale("en", "EN"));
		localePlRadioItem.setUserData(new Locale("pl", "PL"));

		try {
			DetailsController detailsController = loadDetailsView();
			loadItemListView(detailsController);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void onSaveBtn() throws FileNotFoundException, JAXBException {
		JAXBContext contextObj = JAXBContext.newInstance(ItemListWrapper.class);

		Marshaller marshallerObj = contextObj.createMarshaller();
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		ItemListWrapper itemList = itemListController.getItemList();

		marshallerObj.marshal(itemList, new FileOutputStream("items.xml"));
	}

	@FXML
	private void onLoadBtn() {

		File file = new File("items.xml");
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(ItemListWrapper.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			currentItemList = (ItemListWrapper) jaxbUnmarshaller.unmarshal(file);

			itemListController.setItemList(currentItemList);
		} catch (JAXBException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();
		}

	}

	@FXML
	private void onCloseBtn() {
		this.primaryStage.close();
	}

	@FXML
	private void onLocaleChanged(ActionEvent event) throws IOException {
		RadioMenuItem radioMenuItem = (RadioMenuItem) event.getTarget();
		i18nManager.changeLocale(this, (Locale) radioMenuItem.getUserData());
	}

	private void loadItemListView(DetailsController detailsController) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("list/ItemListView.fxml"));
		loader.setResources(resourceBundle);

		AnchorPane listView = (AnchorPane) loader.load();

		itemListController = (ItemListController) loader.getController();
		itemListController.setDetailsController(detailsController);
		itemListController.setItemList(currentItemList);

		rootPane.setCenter(listView);
	}

	private DetailsController loadDetailsView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("details/DetailsView.fxml"));
		loader.setResources(resourceBundle);

		AnchorPane detaisView = (AnchorPane) loader.load();
		DetailsController detailsController = (DetailsController) loader.getController();

		rootPane.setRight(detaisView);

		return detailsController;
	}

	@Override
	public void setI18nManager(I18nManager manager) {
		this.i18nManager = manager;
	}

	@Override
	public AppStateBundle getAppState() {
		AppStateBundle appStateBundle = new AppStateBundle();
		
		appStateBundle.put("loaded.data", currentItemList);
		appStateBundle.put("selected.index", itemListController.getSelectedIndex());

		return appStateBundle;
	}

	@Override
	public void setAppState(AppStateBundle appStateBundle) {
		
		currentItemList = appStateBundle.get("loaded.data", ItemListWrapper.class);
		if(currentItemList != null) {
			itemListController.setItemList(currentItemList);
		}
		

		Integer selectedIndex = appStateBundle.get("selected.index", Integer.class);
		if(selectedIndex != null) {
			itemListController.setSelectedIndex(selectedIndex.intValue());
		}
		
	}

	@FXML public void onNewItemTypeBtn() throws IOException {
		Stage stage = new Stage();

		FXMLLoader loader = new FXMLLoader();
		loader.setResources(resourceBundle);
		loader.setLocation(Main.class.getResource("view/add/AddItemTypeView.fxml"));
		
		AnchorPane root = (AnchorPane) loader.load();
		Scene scene = new Scene(root,400,500);
		
		AddItemTypeController addItemTypeController = (AddItemTypeController) loader.getController();
		addItemTypeController.setItemList(currentItemList);
		addItemTypeController.setContainingStage(stage);
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}

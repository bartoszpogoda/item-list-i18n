package bpogoda.learning.i18n.itemlist.view.add;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import bpogoda.learning.i18n.itemlist.model.Item;
import bpogoda.learning.i18n.itemlist.model.ItemListWrapper;
import bpogoda.learning.i18n.itemlist.model.MetricsType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddItemTypeController implements Initializable {

	@FXML
	TextField unitpriceTb;
	@FXML
	TextField imagePathTb;
	@FXML
	TextArea descTb;
	@FXML
	ComboBox<MetricsType> typeComboBox;
	@FXML
	TextField nameTb;
	
	private ResourceBundle resources;
	
	private Stage containingStage;

	@FXML
	public void onAcceptBtn() {
		try {
			String name = nameTb.getText();

			if (name == null || name.isEmpty()) {
				throw new Exception();
			}

			BigDecimal unitPrice = new BigDecimal(unitpriceTb.getText());

			String imagePath = imagePathTb.getText();
			if(imagePath == null || imagePath.isEmpty()) {
				imagePath = "/default.png";
			} else if(getClass().getResource(imagePath) == null) {
				throw new Exception();
			}

			String description = descTb.getText();

			MetricsType metricsType = typeComboBox.getValue();

			currentItemList.getItems().add(new Item(name, metricsType, 0, unitPrice, description, imagePath));

			Alert alert = new Alert(AlertType.INFORMATION);

			alert.setTitle(resources.getString("gui.add.alert.success"));
			alert.setHeaderText(resources.getString("gui.add.alert.success"));
			alert.setContentText(resources.getString("gui.add.alert.success.added"));

			alert.showAndWait();
			this.containingStage.close();

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			
			alert.setTitle(resources.getString("gui.add.alert.failure"));
			alert.setHeaderText(resources.getString("gui.add.alert.failure"));
			alert.setContentText(resources.getString("gui.add.alert.failure.error"));

			alert.showAndWait();
		}
	}

	@FXML
	public void onCancelBtn() {
		this.containingStage.close();
	}

	private ItemListWrapper currentItemList;

	public void setItemList(ItemListWrapper currentItemList) {
		this.currentItemList = currentItemList;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.resources = resources;
		
		typeComboBox.getItems().add(MetricsType.ITEM);
		typeComboBox.getItems().add(MetricsType.FLUID);
		typeComboBox.getItems().add(MetricsType.WEIGHT);

		typeComboBox.setCellFactory(listView -> {
			return new ListCell<MetricsType>() {
				@Override
				protected void updateItem(MetricsType metricsType, boolean empty) {
					super.updateItem(metricsType, empty);

					if (metricsType == null || empty) {
						setText(null);
					} else {
						setText(metricsType.toLocalizedString(resources));
					}
				}
			};
		});

		typeComboBox.setButtonCell(new ListCell<MetricsType>() {
			@Override
			protected void updateItem(MetricsType metricsType, boolean empty) {
				super.updateItem(metricsType, empty);

				if (empty) {
					setText("");
				} else {
					setText(metricsType.toLocalizedString(resources));
				}
			}
		});

		typeComboBox.getSelectionModel().select(0);
	}

	public void setContainingStage(Stage containingStage) {
		this.containingStage = containingStage;
	}

}

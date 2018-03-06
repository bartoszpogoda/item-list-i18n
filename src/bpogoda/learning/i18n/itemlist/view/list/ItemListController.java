package bpogoda.learning.i18n.itemlist.view.list;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import bpogoda.learning.i18n.itemlist.model.Item;
import bpogoda.learning.i18n.itemlist.model.ItemList;
import bpogoda.learning.i18n.itemlist.model.ItemListWrapper;
import bpogoda.learning.i18n.itemlist.model.Metrics;
import bpogoda.learning.i18n.itemlist.model.MetricsType;
import bpogoda.learning.i18n.itemlist.view.details.DetailsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ItemListController implements Initializable {
	@FXML
	private TableView<Item> table;

	private ItemListWrapper itemList;

	@FXML
	TableColumn<Item, String> nameCol;

	@FXML
	TableColumn<Item, Metrics> quantityCol;

	@FXML
	TableColumn<Item, BigDecimal> unitPriceCol;

	private DetailsController detailsController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameCol.setCellValueFactory(cellData -> cellData.getValue().name());
		quantityCol.setCellValueFactory(cellData -> cellData.getValue().metrics());

		quantityCol.setCellFactory(column -> {
			return new TableCell<Item, Metrics>() {
				@Override
				protected void updateItem(Metrics item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.toLocalizedString(resources));
					}

				}
			};
		});
		
		unitPriceCol.setCellValueFactory(cellData -> cellData.getValue().price());

		unitPriceCol.setCellFactory(column -> {
			return new TableCell<Item, BigDecimal>() {
				@Override
				protected void updateItem(BigDecimal price, boolean empty) {
					super.updateItem(price, empty);

					if (price == null || empty) {
						setText(null);
					} else {
						Format format = NumberFormat.getCurrencyInstance(resources.getLocale());

						DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) format).getDecimalFormatSymbols();
						decimalFormatSymbols.setCurrencySymbol("");
						((DecimalFormat) format).setDecimalFormatSymbols(decimalFormatSymbols);
						
						String formattedAmount = format.format(price);

						setText(formattedAmount + " $");
					}
				}
			};
		});
		
		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(oldSelection != newSelection) {
				if(newSelection != null) {
					detailsController.showDetails(newSelection);
				}
			}
		});
	}
	
	public ItemListWrapper getItemList() {
		return this.itemList;
	}
	
	public void setItemList(ItemListWrapper itemList) {
		this.itemList = itemList;

		table.setItems(itemList.getItems());
	}

	public void setDetailsController(DetailsController detailsController) {
		this.detailsController = detailsController;
	}

	public int getSelectedIndex() {
		return table.getSelectionModel().getSelectedIndex();
	}
	
	public void setSelectedIndex(int index) {
		table.getSelectionModel().clearAndSelect(index);
	}


}

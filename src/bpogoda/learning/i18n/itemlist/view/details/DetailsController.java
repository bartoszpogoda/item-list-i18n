package bpogoda.learning.i18n.itemlist.view.details;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import bpogoda.learning.i18n.itemlist.model.Item;
import bpogoda.learning.i18n.itemlist.model.Metrics;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

public class DetailsController implements Initializable {
	
	@FXML Text priceTxt;
	@FXML Text descTxt;
	@FXML Text quantityTxt;
	@FXML Text nameTxt;
	
	private ResourceBundle resources;
	
	private Item item;
	@FXML ImageView imageView;
	
	@FXML
	public void plusBtn() {
		if(item != null) {
			item.setMetrics(new Metrics(item.getMetrics().getMetricsType(), item.getMetrics().getQuantity() + 1));
			quantityTxt.setText(item.getMetrics().toLocalizedString(resources));
		}
	}
	
	@FXML
	public void minusBtn() {
		if(item != null) {
			int currentQuantity = item.getMetrics().getQuantity();
			
			if(currentQuantity - 1 >= 0) {
				item.setMetrics(new Metrics(item.getMetrics().getMetricsType(), currentQuantity - 1));
				quantityTxt.setText(item.getMetrics().toLocalizedString(resources));
			}
		}
	}

	public void showDetails(Item item) {
		this.item = item;
		
		nameTxt.setText(item.getName());
		
		Format format = NumberFormat.getCurrencyInstance(resources.getLocale());

		DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) format).getDecimalFormatSymbols();
		decimalFormatSymbols.setCurrencySymbol("");
		((DecimalFormat) format).setDecimalFormatSymbols(decimalFormatSymbols);
		
		String formattedAmount = format.format(item.getPrice());

		priceTxt.setText(formattedAmount + " zł");
		
		quantityTxt.setText(item.getMetrics().toLocalizedString(resources));
		
		descTxt.setText(item.getDescription());
		
		Image image = new Image(item.getImageUrl());
		imageView.setImage(image);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.resources = resources;
	}
}

package bpogoda.learning.i18n.itemlist.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@XmlRootElement  
public class Item {
	private final SimpleStringProperty name = new SimpleStringProperty();
	private final SimpleObjectProperty<Metrics> metrics = new SimpleObjectProperty<>();
	private final SimpleObjectProperty<BigDecimal> price = new SimpleObjectProperty<>();
	private final SimpleStringProperty description = new SimpleStringProperty();
	private final SimpleObjectProperty<BigDecimal> totalPrice = new SimpleObjectProperty<>();
	
	private String imageUrl;

	public Item() {

	}

	public Item(String name, MetricsType type, int quantity, BigDecimal price, String description, String imageUrl) {
		this.name.set(name);
		this.metrics.set(new Metrics(type, quantity));
		this.price.set(price);
		this.description.set(description);
		this.setImageUrl(imageUrl);
		
		totalPrice.set(price.multiply(new BigDecimal(quantity)));
	}

	public final SimpleStringProperty name() {
		return name;
	}
	
	public final SimpleObjectProperty<Metrics> metrics() {
		return metrics;
	}
	
	public final SimpleObjectProperty<BigDecimal> price() {
		return price;
	}
	
	public final SimpleStringProperty description() {
		return description;
	}
	
	public final SimpleObjectProperty<BigDecimal> totalPrice() {
		return totalPrice;
	}
	
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public Metrics getMetrics() {
		return metrics.get();
	}

	public void setMetrics(Metrics metrics) {
		this.metrics.set(metrics);
		
		if(this.getPrice() != null) {			
			this.totalPrice.set(this.getPrice().multiply(new BigDecimal(metrics.getQuantity())));
		}
	}

	public BigDecimal getPrice() {
		return price.get();
	}

	public void setPrice(BigDecimal price) {
		this.price.set(price);

		if(this.getMetrics() != null) {
			this.totalPrice.set(this.getPrice().multiply(new BigDecimal(this.getMetrics().getQuantity())));
		}
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice.get();
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice.set(totalPrice);
	}
	


}

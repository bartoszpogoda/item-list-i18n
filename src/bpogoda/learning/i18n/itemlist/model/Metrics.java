package bpogoda.learning.i18n.itemlist.model;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Metrics {
	private MetricsType metricsType;
	private int quantity;

	public Metrics() {
	}

	public Metrics(MetricsType metricsType, int quantity) {
		this.metricsType = metricsType;
		this.quantity = quantity;
	}

	public MetricsType getMetricsType() {
		return metricsType;
	}

	public void setMetricsType(MetricsType metricsType) {
		this.metricsType = metricsType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String toLocalizedString(ResourceBundle resources) {

		String localizedString = "" + quantity + " ";

		if (metricsType == MetricsType.ITEM) {
			localizedString += resolveMessage(resources, "item.metrics.item", quantity);
		} else if (metricsType == MetricsType.FLUID) {
			localizedString += resolveMessage(resources, "item.metrics.liter", quantity);
		} else if (metricsType == MetricsType.WEIGHT) {
			localizedString += resolveMessage(resources, "item.metrics.kilogram", quantity);
		}

		return localizedString;
	}

	private String resolveMessage(ResourceBundle resourceBundle, String key, Object... args) {
		String pattern = resourceBundle.getString(key);
		return MessageFormat.format(pattern, args);
	}

}

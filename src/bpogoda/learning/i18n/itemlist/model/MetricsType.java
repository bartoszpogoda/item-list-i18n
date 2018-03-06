package bpogoda.learning.i18n.itemlist.model;

import java.util.ResourceBundle;

public enum MetricsType {
	WEIGHT("weight"), FLUID("fluid"), ITEM("item");

	private String resourceKey; 
	
	private MetricsType(String resourceKey) {
		this.resourceKey = resourceKey;
	}
	
	public String toLocalizedString(ResourceBundle resources) {
		return resources.getString(resourceKey);
	}
}

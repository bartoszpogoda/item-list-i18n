package bpogoda.learning.i18n.itemlist.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ItemList {
	private List<Item> items;
	
	public ItemList() {
		items = new ArrayList<Item>();
	}
	
    @XmlElement(name = "item")
    public List<Item> getItems() {
        return items;
    }
}

    
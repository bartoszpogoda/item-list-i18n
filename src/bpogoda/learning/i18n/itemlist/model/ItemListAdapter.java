package bpogoda.learning.i18n.itemlist.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemListAdapter extends XmlAdapter<ItemList, ObservableList<Item>>{
	@Override
	public ItemList marshal(ObservableList<Item> list) throws Exception {
		ItemList itemList = new ItemList();
		list.stream().forEach((item) -> {
			itemList.getItems().add(item);
	    });
	    return itemList;
	}

	@Override
	public ObservableList<Item> unmarshal(ItemList itemList) throws Exception {
	    ObservableList<Item> list = FXCollections.observableArrayList(itemList.getItems());
	    return list;
	}
}
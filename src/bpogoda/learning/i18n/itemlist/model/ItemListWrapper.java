package bpogoda.learning.i18n.itemlist.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.collections.ObservableList;

@XmlRootElement(name="items")
public class ItemListWrapper {
	  private ObservableList<Item> items;


      @XmlJavaTypeAdapter(ItemListAdapter.class)
      public ObservableList<Item> getItems() {
          return items;
      }

      public void setItems(ObservableList<Item> items) {
          this.items = items;
      }
}
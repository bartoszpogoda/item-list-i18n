package bpogoda.learning.i18n.itemlist.i18n;

import java.util.HashMap;
import java.util.Map;

public class AppStateBundle {
	private Map<String, Object> map = new HashMap<>();

	public void put(String key, Object value) {
		map.put(key, value);
	}
	
	public <T> T get(String key, Class<T> type) {
		return type.cast(map.get(key));
	}
}

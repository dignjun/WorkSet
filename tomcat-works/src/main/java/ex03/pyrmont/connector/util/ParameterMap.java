package ex03.pyrmont.connector.util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({ "serial", "rawtypes", "unchecked"})
public final class ParameterMap extends HashMap{
	private boolean locked = false;

	private static final StringManager sm = StringManager
			.getManager("org.apache.catalina.util");

	public ParameterMap() {
	}

	public ParameterMap(int initialCapacity) {
		super(initialCapacity);
	}

	public ParameterMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	
	public ParameterMap(Map map) {
		super(map);
	}

	public boolean isLocked() {
		return this.locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void clear() {
		if (this.locked) {
			throw new IllegalStateException(sm.getString("parameterMap.locked"));
		}
		super.clear();
	}

	public Object put(Object key, Object value) {
		if (this.locked) {
			throw new IllegalStateException(sm.getString("parameterMap.locked"));
		}
		return super.put(key, value);
	}

	public void putAll(Map map) {
		if (this.locked) {
			throw new IllegalStateException(sm.getString("parameterMap.locked"));
		}
		super.putAll(map);
	}

	public Object remove(Object key) {
		if (this.locked) {
			throw new IllegalStateException(sm.getString("parameterMap.locked"));
		}
		return super.remove(key);
	}
}

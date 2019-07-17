package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.*;

import java.util.HashMap;

public class PersistentVariableCacheImpl implements PersistentVariableCache {
	private final CollectionFactory COLLECTION_FACTORY;
	private final MapFactory MAP_FACTORY;
	private final HashMap<String,Object> PERSISTENT_VARIABLES;

	public PersistentVariableCacheImpl(CollectionFactory collectionFactory, MapFactory mapFactory) {
		PERSISTENT_VARIABLES = new HashMap<>();
		COLLECTION_FACTORY = collectionFactory;
		MAP_FACTORY = mapFactory;
	}
	
	@Override
	public String getInterfaceName() {
		return PersistentVariableCache.class.getCanonicalName();
	}

	@Override
	public <T> void setVariable(String name, T value) throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException(
					"PersistentVariableCache.put: value cannot be null");
		}
		if (name == null || name.equals("")) {
			throw new IllegalArgumentException(
					"PersistentVariableCache.put: key cannot be null or empty");
		}
		PERSISTENT_VARIABLES.put(name, value);
	}

	@Override
	public boolean remove(String name) {
		return PERSISTENT_VARIABLES.remove(name) != null;
	}

	@Override
	public int size() {
		return PERSISTENT_VARIABLES.size();
	}

	@Override
	public ReadOnlyMap<String,Object> variablesRepresentation() {
		Map<String,Object> variablesMap = MAP_FACTORY.make("", new Object());
		PERSISTENT_VARIABLES.forEach(variablesMap::put);
		return variablesMap.readOnlyRepresentation();
	}

	@Override
	public ReadOnlyCollection<String> namesRepresentation() {
		Collection<String> names = COLLECTION_FACTORY.make("");
		PERSISTENT_VARIABLES.keySet().forEach(names::add);
		return names.readOnlyRepresentation();
	}

	@Override
	public void clear() {
		PERSISTENT_VARIABLES.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getVariable(String name) {
		return (T) PERSISTENT_VARIABLES.get(name);
	}

}

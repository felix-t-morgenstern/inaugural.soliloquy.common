package inaugural.soliloquy.common;

import soliloquy.specs.common.entities.IPersistentVariableCache;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IMap;

import java.util.HashMap;

public class PersistentVariableCache implements IPersistentVariableCache {
	private final ICollectionFactory COLLECTION_FACTORY;
	private final HashMap<String,Object> PERSISTENT_VARIABLES;

	public PersistentVariableCache(ICollectionFactory collectionFactory) {
		PERSISTENT_VARIABLES = new HashMap<>();
		COLLECTION_FACTORY = collectionFactory;
	}
	
	@Override
	public String getInterfaceName() {
		return IPersistentVariableCache.class.getCanonicalName();
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
	public IMap<String,Object> getVariablesRepresentation() {
		// TODO: Test and implement!!!
		return null;
	}

	@Override
	public ICollection<String> getNamesRepresentation() {
		ICollection<String> persistentVariableNamesRepresentation = COLLECTION_FACTORY.make("");
		PERSISTENT_VARIABLES.keySet().forEach(persistentVariableNamesRepresentation::add);
		return persistentVariableNamesRepresentation;
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

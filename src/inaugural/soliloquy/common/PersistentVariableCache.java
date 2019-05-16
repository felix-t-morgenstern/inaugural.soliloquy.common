package inaugural.soliloquy.common;

import java.util.HashMap;
import java.util.Map.Entry;

import soliloquy.common.specs.*;
import soliloquy.game.primary.specs.IGame;
import soliloquy.logger.specs.ILogger;

public class PersistentVariableCache implements IPersistentVariableCache {
	private final ICollectionFactory COLLECTION_FACTORY;
	private final HashMap<String,IPersistentVariable> PERSISTENT_VARIABLES;

	public PersistentVariableCache(ICollectionFactory collectionFactory) {
		PERSISTENT_VARIABLES = new HashMap<>();
		COLLECTION_FACTORY = collectionFactory;
	}
	
	@Override
	public String getInterfaceName() {
		return IPersistentVariableCache.class.getCanonicalName();
	}

	@Override
	public void put(IPersistentVariable persistentVariable) throws IllegalArgumentException {
		if (persistentVariable == null) {
			throw new IllegalArgumentException(
					"PersistentVariableCache.put: persistent variable cannot be null");
		}
		if (persistentVariable.getName() == null || persistentVariable.getName().equals("")) {
			throw new IllegalArgumentException(
					"PersistentVariableCache.put: key cannot be null or empty");
		}
		if (persistentVariable.getValue() == null) {
			throw new IllegalArgumentException(
					"PersistentVariableCache.put: persistent variable cannot have a null value");
		}
		PERSISTENT_VARIABLES.put(persistentVariable.getName(), persistentVariable);
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
	public ICollection<String> persistentVariableNamesRepresentation() {
		ICollection<String> persistentVariableNamesRepresentation = COLLECTION_FACTORY.make("");
		for(String name : PERSISTENT_VARIABLES.keySet()) {
			persistentVariableNamesRepresentation.add(name);
		}
		return persistentVariableNamesRepresentation;
	}

	@Override
	public void clear() {
		PERSISTENT_VARIABLES.clear();
	}

	@Override
	public IPersistentVariable get(String name) {
		return PERSISTENT_VARIABLES.get(name);
	}

}

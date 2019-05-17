package inaugural.soliloquy.common;

import java.util.HashMap;

import soliloquy.common.specs.*;

public class PersistentValuesHandler extends CanGetInterfaceName
		implements IPersistentValuesHandler {
	private HashMap<String,IPersistentValueTypeHandler<?>> _persistentValueTypeHandlers;
	private IPersistentValueTypeHandler<ICollection> _persistentCollectionHandler;
	private IPersistentValueTypeHandler<IMap> _persistentMapHandler;
	private IPersistentValueTypeHandler<IPair> _persistentPairHandler;

	private final String COLLECTION_GENERIC_INTERFACE_NAME = ICollection.class.getCanonicalName();
	private final String MAP_GENERIC_INTERFACE_NAME = IMap.class.getCanonicalName();
	
	public PersistentValuesHandler() {
		_persistentValueTypeHandlers = new HashMap<>();
	}

	@Override
	public void addPersistentValueTypeHandler(IPersistentValueTypeHandler<?> persistentValueTypeHandler)
			throws IllegalArgumentException {
		String persistentValueType = getProperTypeName(persistentValueTypeHandler.getArchetype());
		if (_persistentValueTypeHandlers.containsKey(persistentValueType)) {
			throw new IllegalArgumentException(
					"PersistentValuesHandler.addPersistentValueTypeHandler: already has handler for " 
							+ persistentValueType);
		}
		_persistentValueTypeHandlers.put(persistentValueType, persistentValueTypeHandler);
	}

	@Override
	public boolean removePersistentValueTypeHandler(String persistentValueType) {
		return _persistentValueTypeHandlers.remove(persistentValueType) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IPersistentValueTypeHandler<T> getPersistentValueTypeHandler(String persistentValueType)
			throws UnsupportedOperationException {
		// TODO: Make sure that pair handling is included here!
		if (interfaceIsOfGenericType(persistentValueType, COLLECTION_GENERIC_INTERFACE_NAME)) {
			return (IPersistentValueTypeHandler<T>) _persistentCollectionHandler;
		} else if (interfaceIsOfGenericType(persistentValueType, MAP_GENERIC_INTERFACE_NAME)) {
			return (IPersistentValueTypeHandler<T>) _persistentMapHandler;
		}else {
			return (IPersistentValueTypeHandler<T>) _persistentValueTypeHandlers.get(persistentValueType);
		}
	}

	private boolean interfaceIsOfGenericType(String valueType, String genericInterfaceName) {
		return valueType.length() >= genericInterfaceName.length()
				&& valueType.substring(0, genericInterfaceName.length())
				.equals(genericInterfaceName);
	}

	@Override
	public ICollection<String> persistentValueTypesHandled() {
		Collection<String> persistentValueTypesHandled = new Collection<>(null);
		for (String type : _persistentValueTypeHandlers.keySet()) {
			persistentValueTypesHandled.add(type);
		}
		return persistentValueTypesHandled;
	}

	@Override
	public void registerPersistentPairHandler(
			IPersistentValueTypeHandler<IPair> persistentPairHandler) {
		_persistentPairHandler = persistentPairHandler;
	}

	@Override
	public void registerPersistentCollectionHandler(
			IPersistentValueTypeHandler<ICollection> persistentCollectionHandler) {
		_persistentCollectionHandler = persistentCollectionHandler;
	}

	@Override
	public void registerPersistentMapHandler(IPersistentValueTypeHandler<IMap> persistentMapHandler) {
		_persistentMapHandler = persistentMapHandler;
	}

	@Override
	public String getInterfaceName() {
		return IPersistentValuesHandler.class.getCanonicalName();
	}

}

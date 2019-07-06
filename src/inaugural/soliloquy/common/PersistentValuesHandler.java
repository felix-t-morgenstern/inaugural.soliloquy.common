package inaugural.soliloquy.common;

import soliloquy.specs.common.infrastructure.*;

import java.util.HashMap;

public class PersistentValuesHandler extends CanGetInterfaceName
		implements IPersistentValuesHandler {
	private HashMap<String, IPersistentValueTypeHandler> _persistentValueTypeHandlers;
	private IPersistentCollectionHandler _persistentCollectionHandler;
	private IPersistentMapHandler _persistentMapHandler;
	private IPersistentPairHandler _persistentPairHandler;

	private final String COLLECTION_GENERIC_INTERFACE_NAME = ICollection.class.getCanonicalName();
	private final String MAP_GENERIC_INTERFACE_NAME = IMap.class.getCanonicalName();
	private final String PAIR_GENERIC_INTERFACE_NAME = IPair.class.getCanonicalName();
	
	public PersistentValuesHandler() {
		_persistentValueTypeHandlers = new HashMap<>();
	}

	@Override
	public void addPersistentValueTypeHandler(
			IPersistentValueTypeHandler persistentValueTypeHandler)
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
	public <T> IPersistentValueTypeHandler<T> getPersistentValueTypeHandler(
			String persistentValueType)
			throws UnsupportedOperationException {
		// TODO: Ensure that these tests work for read-only and read-write infrastructure
		if (interfaceIsOfGenericType(persistentValueType, COLLECTION_GENERIC_INTERFACE_NAME)) {
			return (IPersistentValueTypeHandler<T>) _persistentCollectionHandler;
		} else if (interfaceIsOfGenericType(persistentValueType, MAP_GENERIC_INTERFACE_NAME)) {
			return (IPersistentValueTypeHandler<T>) _persistentMapHandler;
		} else if (interfaceIsOfGenericType(persistentValueType, PAIR_GENERIC_INTERFACE_NAME)) {
			return (IPersistentValueTypeHandler<T>) _persistentPairHandler;
		} else {
			return (IPersistentValueTypeHandler<T>)
					_persistentValueTypeHandlers.get(persistentValueType);
		}
	}

	@Override
	public Object generateArchetype(String valueType) throws IllegalArgumentException {
		if (interfaceIsOfGenericType(valueType, COLLECTION_GENERIC_INTERFACE_NAME)) {
			return _persistentCollectionHandler.generateArchetype(valueType);
		} else if (interfaceIsOfGenericType(valueType, MAP_GENERIC_INTERFACE_NAME)) {
			return _persistentMapHandler.generateArchetype(valueType);
		} else if (interfaceIsOfGenericType(valueType, PAIR_GENERIC_INTERFACE_NAME)) {
			return _persistentPairHandler.generateArchetype(valueType);
		} else {
			IPersistentValueTypeHandler persistentValueTypeHandler =
					getPersistentValueTypeHandler(valueType);
			return persistentValueTypeHandler.getArchetype();
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
	public void registerPersistentPairHandler(IPersistentPairHandler persistentPairHandler) {
		_persistentPairHandler = persistentPairHandler;
	}

	@Override
	public void registerPersistentCollectionHandler(
			IPersistentCollectionHandler persistentCollectionHandler) {
		_persistentCollectionHandler = persistentCollectionHandler;
	}

	@Override
	public void registerPersistentMapHandler(IPersistentMapHandler persistentMapHandler) {
		_persistentMapHandler = persistentMapHandler;
	}

	@Override
	public String getInterfaceName() {
		return IPersistentValuesHandler.class.getCanonicalName();
	}

}

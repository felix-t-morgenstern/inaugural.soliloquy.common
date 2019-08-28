package inaugural.soliloquy.common;

import soliloquy.specs.common.infrastructure.*;

import java.util.HashMap;

public class PersistentValuesHandlerImpl extends CanGetInterfaceName
		implements PersistentValuesHandler {
	private HashMap<String, PersistentValueTypeHandler> _persistentValueTypeHandlers;
	private PersistentCollectionHandler _persistentCollectionHandler;
	private PersistentMapHandler _persistentMapHandler;
	private PersistentPairHandler _persistentPairHandler;
	private PersistentRegistryHandler _persistentRegistryHandler;

	private final String COLLECTION_GENERIC_INTERFACE_NAME = Collection.class.getCanonicalName();
	private final String MAP_GENERIC_INTERFACE_NAME = Map.class.getCanonicalName();
	private final String PAIR_GENERIC_INTERFACE_NAME = Pair.class.getCanonicalName();
	private final String REGISTRY_GENERIC_INTERFACE_NAME = Registry.class.getCanonicalName();
	
	public PersistentValuesHandlerImpl() {
		_persistentValueTypeHandlers = new HashMap<>();
	}

	@Override
	public void addPersistentValueTypeHandler(
			PersistentValueTypeHandler persistentValueTypeHandler)
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
	public <T> PersistentValueTypeHandler<T> getPersistentValueTypeHandler(
			String persistentValueType)
			throws UnsupportedOperationException {
		// TODO: Ensure that these tests work for read-only and read-write infrastructure
		if (interfaceIsOfGenericType(persistentValueType, COLLECTION_GENERIC_INTERFACE_NAME)) {
			return (PersistentValueTypeHandler<T>) _persistentCollectionHandler;
		} else if (interfaceIsOfGenericType(persistentValueType, MAP_GENERIC_INTERFACE_NAME)) {
			return (PersistentValueTypeHandler<T>) _persistentMapHandler;
		} else if (interfaceIsOfGenericType(persistentValueType, PAIR_GENERIC_INTERFACE_NAME)) {
			return (PersistentValueTypeHandler<T>) _persistentPairHandler;
		} else if (interfaceIsOfGenericType(persistentValueType,
				REGISTRY_GENERIC_INTERFACE_NAME)) {
			return (PersistentValueTypeHandler<T>) _persistentRegistryHandler;
		} else {
			return (PersistentValueTypeHandler<T>)
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
			PersistentValueTypeHandler persistentValueTypeHandler =
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
	public Collection<String> persistentValueTypesHandled() {
		CollectionImpl<String> persistentValueTypesHandled = new CollectionImpl<>(null);
		for (String type : _persistentValueTypeHandlers.keySet()) {
			persistentValueTypesHandled.add(type);
		}
		return persistentValueTypesHandled;
	}

	@Override
	public void registerPersistentCollectionHandler(
			PersistentCollectionHandler persistentCollectionHandler) {
		_persistentCollectionHandler = persistentCollectionHandler;
	}

	@Override
	public void registerPersistentMapHandler(PersistentMapHandler persistentMapHandler) {
		_persistentMapHandler = persistentMapHandler;
	}

	@Override
	public void registerPersistentPairHandler(PersistentPairHandler persistentPairHandler) {
		_persistentPairHandler = persistentPairHandler;
	}

	@Override
	public void registerPersistentRegistryHandler(
			PersistentRegistryHandler persistentRegistryHandler) {
		_persistentRegistryHandler = persistentRegistryHandler;
	}

	@Override
	public String getInterfaceName() {
		return PersistentValuesHandler.class.getCanonicalName();
	}

}

package inaugural.soliloquy.common;

import com.google.gson.Gson;
import java.util.HashMap;

import soliloquy.common.specs.*;

public class PersistentValuesHandler extends CanGetInterfaceName
		implements IPersistentValuesHandler {
	private HashMap<String,IPersistentValueTypeHandler<?>> _persistentValueTypeHandlers;
	private IPersistentValueTypeHandler<IPair> _persistentPairHandler;
	private IPersistentValueTypeHandler<ICollection> _persistentCollectionHandler;
	private IPersistentValueTypeHandler<IMap> _persistentMapHandler;

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
	public void readValues(String valuesString, IAction<IPersistentValueToWrite> valuesProcessing) {
		PersistentValueToRead[] persistentValuesToRead =
				new Gson().fromJson(valuesString, PersistentValueToRead[].class);
		for (IPersistentValueToRead persistentValueToRead : persistentValuesToRead) {
			IPersistentValueTypeHandler<?> persistentValueTypeHandler =
					getPersistentValueTypeHandler(persistentValueToRead.typeName());
			IPersistentValueToWrite<?> persistentValueToWrite =
					makePersistentValueToWrite(persistentValueToRead.name(),
							persistentValueTypeHandler.read(persistentValueToRead.value()));
			valuesProcessing.run(persistentValueToWrite);
		}
	}

	@Override
	public String writeValues(ICollection<IPersistentValueToWrite> persistentValuesToProcess) {
		PersistentValueToRead[] persistentValuesToRead =
				new PersistentValueToRead[persistentValuesToProcess.size()];
		int i = 0;
		for (IPersistentValueToWrite<?> persistentValueToProcess : persistentValuesToProcess) {
			PersistentValueToRead persistentValueToRead =
					convertPersistentValueToWriteToPersistentValueToRead(persistentValueToProcess);
			persistentValuesToRead[i++] = persistentValueToRead;
		}
		return new Gson().toJson(persistentValuesToRead, PersistentValueToRead[].class);
	}
	
	@Override
	public IPersistentValueToRead makePersistentValueToRead(String typeName, String name, String value) {
		return new PersistentValueToRead(typeName, name, value);
	}

	@Override
	public <T> IPersistentValueToWrite<T> makePersistentValueToWrite(String name, T value) {
		return new PersistentValueToWrite<>(name, value);
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
	
	private <T> PersistentValueToRead convertPersistentValueToWriteToPersistentValueToRead(
			IPersistentValueToWrite<T> persistentValueToWrite) {
		PersistentValueToRead persistentValueToRead = new PersistentValueToRead();
		persistentValueToRead.name = persistentValueToWrite.name();
		persistentValueToRead.typeName = persistentValueToWrite.typeName();
		String typeName = getProperTypeName(persistentValueToWrite.value());
		IPersistentValueTypeHandler<T> persistentValueTypeHandler = getPersistentValueTypeHandler(typeName);
		persistentValueToRead.value = persistentValueTypeHandler.write(persistentValueToWrite.value());
		return persistentValueToRead;
	}
	
	protected class PersistentValueToRead implements IPersistentValueToRead {
		public String typeName;
		public String name;
		public String value;
		
		public PersistentValueToRead(String typeName, String name, String value) {
			this.typeName = typeName;
			this.name = name;
			this.value = value;
		}
		
		public PersistentValueToRead() {
		}

		@Override
		public String typeName() {
			return this.typeName;
		}

		@Override
		public String name() {
			return this.name;
		}

		@Override
		public String value() {
			return this.value;
		}

		@Override
		public String getInterfaceName() {
			return IPersistentValueToRead.class.getCanonicalName();
		}
	}
	
	protected class PersistentValueToWrite<T> extends CanGetInterfaceName
			implements IPersistentValueToWrite<T> {
		String _typeName;
		String _name;
		T _value;
		
		public PersistentValueToWrite(String name, T value) {
			_typeName = getProperTypeName(value);
			_name = name;
			_value = value;
		}

		@Override
		public String typeName() {
			return _typeName;
		}

		@Override
		public String name() {
			return _name;
		}

		@Override
		public T value() {
			return _value;
		}

		@Override
		public String getInterfaceName() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public T getArchetype() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getUnparameterizedInterfaceName() {
			// Stub method
			return null;
		}
		
	}

}

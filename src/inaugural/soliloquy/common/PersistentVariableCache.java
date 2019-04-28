package inaugural.soliloquy.common;

import java.util.HashMap;
import java.util.Map.Entry;
import soliloquy.common.specs.IAction;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IHasGenericParams;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.common.specs.IPersistentValuesHandler;
import soliloquy.common.specs.IPersistentVariable;
import soliloquy.common.specs.IPersistentVariableCache;
import soliloquy.common.specs.IPersistentVariableFactory;
import soliloquy.common.specs.ISoliloquyClass;
import soliloquy.game.primary.specs.IGame;
import soliloquy.logger.specs.ILogger;

public class PersistentVariableCache extends Map<String,IPersistentVariable> implements IPersistentVariableCache {
	private final ICollectionFactory COLLECTION_FACTORY;
	private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;
	private final ProcessReadValueAction PROCESS_READ_VALUE_ACTION;

	public PersistentVariableCache(IPairFactory pairFactory, String archetype1, IPersistentVariable archetype2,
			ICollectionFactory collectionFactory, IPersistentVariableFactory persistentVariableFactory,
			IPersistentValuesHandler persistentValuesHandler) {
		super(pairFactory, archetype1, archetype2);
		COLLECTION_FACTORY = collectionFactory;
		PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
		
		PROCESS_READ_VALUE_ACTION = new ProcessReadValueAction(_map, persistentVariableFactory);
	}

	@Override
	public void read(String data, boolean overridePreviousData) throws IllegalArgumentException {
		PERSISTENT_VALUES_HANDLER.readValues(data, PROCESS_READ_VALUE_ACTION, overridePreviousData);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String write() throws IllegalArgumentException {
		IPersistentValueToWrite collectionArchetype = new PersistentValueToWrite("", "");
		ICollection<IPersistentValueToWrite<?>> persistentValuesToWrite = COLLECTION_FACTORY
				.make(collectionArchetype);
		for (Entry<String, IPersistentVariable> entry : _map.entrySet()) {
			persistentValuesToWrite.add(new PersistentValueToWrite(entry.getValue().getName(),
					entry.getValue().getValue()));
		}
		return PERSISTENT_VALUES_HANDLER.writeValues(persistentValuesToWrite);
	}
	
	@Override
	public String getInterfaceName() {
		return IPersistentVariableCache.class.getCanonicalName();
	}

	@Override
	public void put(IPersistentVariable persistentVariable) throws IllegalArgumentException {
		if (persistentVariable == null) {
			throw new IllegalArgumentException("PersistentVariableCache.put: persistent variable cannot be null");
		}
		put(persistentVariable.getName(), persistentVariable);
	}
	
	@Override
	public void put(String key, IPersistentVariable value) throws IllegalArgumentException {
		if (key == null || key.equals("")) {
			throw new IllegalArgumentException("PersistentVariableCache.put: key cannot be null or empty");
		}
		if (value == null) {
			throw new IllegalArgumentException("PersistentVariableCache.put: persistent variable cannot be null");
		}
		if (!key.equals(value.getName())) {
			throw new IllegalArgumentException("PersistentVariableCache.put: key does not match PersistentValue name");
		}
		if (value.getValue() == null) {
			throw new IllegalArgumentException("PersistentVariableCache.put: persistent variable cannot have a null value");
		}
		_map.put(key, value);
	}
	
	protected class PersistentValueToWrite<T> implements IPersistentValueToWrite<T> {
		public String typeName;
		public String name;
		public T value;
		
		public PersistentValueToWrite(String name, T value)
		{
			this.typeName = value instanceof IHasGenericParams ?
					((ISoliloquyClass) value).getInterfaceName() :
						value.getClass().getCanonicalName();
			this.name = name;
			this.value = value;
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
		public T value() {
			return this.value;
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
			throw new UnsupportedOperationException();
		}
		
	}
	
	private class ProcessReadValueAction implements IAction<IPair<IPersistentValueToWrite<?>,Boolean>>
	{
		private final HashMap<String,IPersistentVariable> MAP;
		private final IPersistentVariableFactory PERSISTENT_VARIABLE_FACTORY;
		
		private ProcessReadValueAction(HashMap<String,IPersistentVariable> map,
				IPersistentVariableFactory persistentVariableFactory)
		{
			MAP = map;
			PERSISTENT_VARIABLE_FACTORY = persistentVariableFactory;
		}
		
		@Override
		public String id() throws IllegalStateException {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public IPair<IPersistentValueToWrite<?>, Boolean> getArchetype() {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public String getInterfaceName() {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void run(IPair<IPersistentValueToWrite<?>, Boolean> input)
				throws IllegalArgumentException {
			boolean overridePreviousData = input.getItem2();
			IPersistentValueToWrite inputValue = input.getItem1();
			if (overridePreviousData || !MAP.containsKey(inputValue.name()))
			{
				IPersistentVariable persistentVariable = PERSISTENT_VARIABLE_FACTORY
						.make(inputValue.name(), inputValue.value());
				MAP.put(inputValue.name(), persistentVariable);
			}
		}

		@Override
		public String getUnparameterizedInterfaceName() {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public IGame game() {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public ILogger logger() {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}
	}
}

package inaugural.soliloquy.common;

import java.util.HashMap;

import com.google.gson.Gson;

import soliloquy.common.specs.IAction;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IHasGenericParams;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPersistentValueToRead;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.common.specs.IPersistentValueTypeHandler;
import soliloquy.common.specs.IPersistentValuesHandler;

public class PersistentValuesHandler implements IPersistentValuesHandler {
	private HashMap<String,IPersistentValueTypeHandler<?>> _persistentValueTypeHandlers;
	
	public PersistentValuesHandler()
	{
		_persistentValueTypeHandlers = new HashMap<String,IPersistentValueTypeHandler<?>>();
	}
	
	@Override
	public void addPersistentValueTypeHandler(IPersistentValueTypeHandler<?> persistentValueTypeHandler) throws IllegalArgumentException {
		String persistentValueType = persistentValueTypeHandler.getArchetype() instanceof IHasGenericParams ?
				((IHasGenericParams) persistentValueTypeHandler.getArchetype()).getParameterizedClassName() :
					persistentValueTypeHandler.getArchetype().getClass().getCanonicalName();
		if (_persistentValueTypeHandlers.containsKey(persistentValueType))
			throw new IllegalArgumentException("PersistentValuesHandler already has handler for " + persistentValueType);
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
		return (IPersistentValueTypeHandler<T>) _persistentValueTypeHandlers.get(persistentValueType);
	}

	@Override
	public ICollection<String> persistentValueTypesHandled() {
		Collection<String> persistentValueTypesHandled = new Collection<String>(null);
		for (String type : _persistentValueTypeHandlers.keySet()) persistentValueTypesHandled.add(type);
		return persistentValueTypesHandled;
	}

	@Override
	public void readValues(String valuesString, IAction<IPair<IPersistentValueToWrite<?>, Boolean>> valuesProcessing,
			boolean overridePreviousData) {
		PersistentValueToRead[] persistentValuesToRead = new Gson().fromJson(valuesString, PersistentValueToRead[].class);
		for (IPersistentValueToRead persistentValueToRead : persistentValuesToRead)
		{
			IPersistentValueTypeHandler<?> persistentValueTypeHandler = getPersistentValueTypeHandler(persistentValueToRead.typeName());
			IPersistentValueToWrite<?> persistentValueToWrite =
					makePersistentValueToWrite(persistentValueToRead.name(), persistentValueTypeHandler.read(persistentValueToRead.value()));
			valuesProcessing.run(new Pair<IPersistentValueToWrite<?>, Boolean>(persistentValueToWrite, overridePreviousData));
		}
	}

	@Override
	public String writeValues(ICollection<IPersistentValueToWrite<?>> persistentValuesToProcess) {
		PersistentValueToRead[] persistentValuesToRead = new PersistentValueToRead[persistentValuesToProcess.size()];
		Integer i = 0;
		for (IPersistentValueToWrite<?> persistentValueToProcess : persistentValuesToProcess)
		{
			PersistentValueToRead persistentValueToRead = convertPersistentValueToWriteToPersistentValueToRead(persistentValueToProcess);
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
		return new PersistentValueToWrite<T>(name, value);
	}
	
	private <T> PersistentValueToRead convertPersistentValueToWriteToPersistentValueToRead(IPersistentValueToWrite<T> persistentValueToWrite)
	{
		PersistentValueToRead persistentValueToRead = new PersistentValueToRead();
		persistentValueToRead.name = persistentValueToWrite.name();
		persistentValueToRead.typeName = persistentValueToWrite.typeName();
		String typeName = persistentValueToWrite.value() instanceof IHasGenericParams ?
				((IHasGenericParams) persistentValueToWrite.value()).getParameterizedClassName() :
					persistentValueToWrite.value().getClass().getCanonicalName();
		IPersistentValueTypeHandler<T> persistentValueTypeHandler = getPersistentValueTypeHandler(typeName);
		persistentValueToRead.value = persistentValueTypeHandler.write(persistentValueToWrite.value());
		return persistentValueToRead;
	}
	
	protected class PersistentValueToRead implements IPersistentValueToRead {
		public String typeName;
		public String name;
		public String value;
		
		public PersistentValueToRead()
		{
			
		}
		
		public PersistentValueToRead(String typeName, String name, String value)
		{
			this.typeName = typeName;
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
		public String value() {
			return this.value;
		}
	}
	
	protected class PersistentValueToWrite<T> implements IPersistentValueToWrite<T>
	{
		public String typeName;
		public String name;
		public T value;
		
		public PersistentValueToWrite(String name, T value)
		{
			this.typeName = value instanceof IHasGenericParams ?
					((IHasGenericParams) value).getParameterizedClassName() :
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
		public String getParameterizedClassName() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public T getArchetype() {
			throw new UnsupportedOperationException("PersistentValueToWrite.getArchetype() is not supported");
		}
		
	}

}

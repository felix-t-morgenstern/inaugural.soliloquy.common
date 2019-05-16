package inaugural.soliloquy.common;

import soliloquy.common.specs.*;
import soliloquy.game.primary.specs.IGame;
import soliloquy.logger.specs.ILogger;

import java.util.HashMap;

public class GenericParamsSet extends CanGetInterfaceName implements IGenericParamsSet {
	private HashMap<String,IMap<String,?>> _paramsSetsRepository = new HashMap<>();
	private IPersistentValuesHandler _persistentValuesHandler;
	private IMapFactory _mapFactory;
	private ProcessReadValue _processReadValue;
	
	public GenericParamsSet(IPersistentValuesHandler persistentValuesHandler, IMapFactory mapFactory) {
		_persistentValuesHandler = persistentValuesHandler;
		_mapFactory = mapFactory;
		_processReadValue = new ProcessReadValue(this);
	}
	
	public <T> void addParam(String name, T value) throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException("GenericParamsSet.addParam: value must not be null");
		}
		addParam(name, value, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> void addParam(String name, T value, T archetype) throws IllegalArgumentException {
		if (archetype == null) {
			throw new IllegalArgumentException("GenericParamsSet.addParam: archetype must not be null");
		}
		String paramTypeName = getProperTypeName(archetype);
		if (getParamsSet(paramTypeName) == null) {
			addParamsSet(_mapFactory.make("", archetype), archetype);
		}
		((IMap<String,T>) _paramsSetsRepository.get(paramTypeName)).put(name, value);
	}

	@Override
	public <T> void addParamsSet(IMap<String, T> paramsSet, T paramArchetype)
			throws IllegalArgumentException, UnsupportedOperationException {
		if (paramsSet == null) {
			throw new IllegalArgumentException("GenericParamsSet.addParamsSet: Cannot add null paramsSet");
		}
		if (paramArchetype == null) {
			throw new IllegalArgumentException("GenericParamsSet.addParamsSet: paramArchetype cannot be null");
		}
		String paramTypeName = getProperTypeName(paramArchetype);
		if (_paramsSetsRepository.containsKey(paramTypeName)) {
			throw new UnsupportedOperationException("GenericParamsSet.addParamsSet: Params set of type "
					+ paramTypeName + " already exists in this params set");
		}
		_paramsSetsRepository.put(paramTypeName, paramsSet);
	}

	@Override
	public <T> T getParam(String paramTypeName, String paramName) {
		@SuppressWarnings("unchecked")
		IMap<String,T> repository = (IMap<String, T>) _paramsSetsRepository.get(paramTypeName);
		if (repository == null) {
			return null;
		}
		return repository.get(paramName);
	}

	@Override
	public <T> IMap<String, T> getParamsSet(String paramTypeName)
			throws IllegalArgumentException, IllegalStateException {
		@SuppressWarnings("unchecked")
		IMap<String, T> map = (IMap<String, T>) _paramsSetsRepository.get(paramTypeName);
		return map;
	}

	@Override
	public boolean paramExists(String paramTypeName, String paramName) {
		IMap<String,?> repository = _paramsSetsRepository.get(paramTypeName);
		if (repository == null) {
			return false;
		}
		return repository.containsKey(paramName);
	}

	@Override
	public ICollection<String> paramTypes() {
		// Class isn't parameterized, thus no archetype is needed by Collection
		ICollection<String> paramTypeNames = new Collection<>("");
		for(String paramTypeName : _paramsSetsRepository.keySet()) {
			paramTypeNames.add(paramTypeName);
		}
		return paramTypeNames;
	}

	@Override
	public boolean removeParam(String paramTypeName, String paramName) {
		if (getParamsSet(paramTypeName) == null) {
			return false;
		}
		return (_paramsSetsRepository.get(paramTypeName)).removeByKey(paramName) != null;
	}

	@Override
	public void read(String data) throws IllegalArgumentException {
		_persistentValuesHandler.readValues(data, _processReadValue);
	}

	@Override
	public String write() throws IllegalArgumentException {
		// NB: No archetype is required by PersistentValuesHandler.writeValues()
		// TODO: Remove direct dependency on Collection
		ICollection<IPersistentValueToWrite> parameters =
				new Collection<>(null);
		for(String typeName : _paramsSetsRepository.keySet()) {
			IMap<String,?> paramsSet = _paramsSetsRepository.get(typeName);
			for(String name : paramsSet.getKeys()) {
				parameters.add(_persistentValuesHandler
						.makePersistentValueToWrite(name, paramsSet.get(name)));
			}
		}
		return _persistentValuesHandler.writeValues(parameters);
	}

	@Override
	public IGenericParamsSet makeClone() {
		GenericParamsSet cloned = new GenericParamsSet(_persistentValuesHandler, _mapFactory);
		for(IMap<String,?> map : _paramsSetsRepository.values()) {
			for(String key : map.getKeys()) cloned.addParam(key, map.get(key));
		}
		return cloned;
	}

	@Override
	public String getInterfaceName() {
		return IGenericParamsSet.class.getCanonicalName();
	}
	
	private class ProcessReadValue implements IAction<IPersistentValueToWrite> {
		private GenericParamsSet _genericParamsSet;
		
		ProcessReadValue(GenericParamsSet genericParamsSet) {
			_genericParamsSet = genericParamsSet;
		}

		@Override
		public String id() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void run(IPersistentValueToWrite input) throws IllegalArgumentException {
			String typeName = input.typeName();
			String name = input.name();
			Object value = input.value();
			_genericParamsSet.addParam(name, value);
		}

		@Override
		public String getInterfaceName() {
			throw new UnsupportedOperationException();
		}

		@Override
		public IPersistentValueToWrite getArchetype() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getUnparameterizedInterfaceName() {
			throw new UnsupportedOperationException();
		}

		@Override
		public IGame game() {
			throw new UnsupportedOperationException();
		}

		@Override
		public ILogger logger() {
			// TODO: Verify whether this method should truly be unsupported
			throw new UnsupportedOperationException();
		}
		
	}
}

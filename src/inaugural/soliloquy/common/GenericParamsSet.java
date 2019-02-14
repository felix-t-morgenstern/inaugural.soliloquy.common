package inaugural.soliloquy.common;

import java.util.HashMap;

import soliloquy.common.specs.IAction;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.common.specs.IPersistentValuesHandler;
import soliloquy.common.specs.ISoliloquyClass;
import soliloquy.game.primary.specs.IGame;

public class GenericParamsSet implements IGenericParamsSet {
	private HashMap<String,IMap<String,?>> _paramsSetsRepository = new HashMap<String,IMap<String,?>>();
	private IPersistentValuesHandler _persistentValuesHandler;
	private IMapFactory _mapFactory;
	private ProcessReadValue _processReadValue;
	
	public GenericParamsSet(IPersistentValuesHandler persistentValuesHandler, IMapFactory mapFactory)
	{
		_persistentValuesHandler = persistentValuesHandler;
		_mapFactory = mapFactory;
		_processReadValue = new ProcessReadValue(this);
	}
	
	public <T> void addParam(String name, T value) throws IllegalArgumentException
	{
		if (value == null) throw new IllegalArgumentException("value must not be null");
		addParam(name, value, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> void addParam(String name, T value, T archetype) throws IllegalArgumentException
	{
		if (archetype == null)
		{
			throw new IllegalArgumentException("archetype must not be null");
		}
		String paramTypeName = archetype instanceof ISoliloquyClass ? ((ISoliloquyClass) archetype).getInterfaceName() : archetype.getClass().getCanonicalName();
		if (getParamsSet(paramTypeName) == null)
		{
			addParamsSet(_mapFactory.make("", archetype), archetype);
		}
		((IMap<String,T>) _paramsSetsRepository.get(paramTypeName)).put(name, value);
	}

	@Override
	public <T> void addParamsSet(IMap<String, T> paramsSet, T paramArchetype) throws IllegalArgumentException, UnsupportedOperationException {
		if (paramsSet == null) throw new IllegalArgumentException("Cannot add null paramsSet");
		if (paramArchetype == null) throw new IllegalArgumentException("paramArchetype cannot be null");
		String paramTypeName = paramArchetype instanceof ISoliloquyClass ? ((ISoliloquyClass) paramArchetype).getInterfaceName() : paramArchetype.getClass().getCanonicalName();
		if (_paramsSetsRepository.containsKey(paramTypeName)) throw new UnsupportedOperationException("Params set of type " + paramTypeName + " already exists in this params set");
		_paramsSetsRepository.put(paramTypeName, paramsSet);
	}

	@Override
	public <T> T getParam(String paramTypeName, String paramName) {
		@SuppressWarnings("unchecked")
		IMap<String,T> repository = (IMap<String, T>) _paramsSetsRepository.get(paramTypeName);
		if (repository == null) return null;
		return repository.get(paramName);
	}

	@Override
	public <T> IMap<String, T> getParamsSet(String paramTypeName) throws IllegalArgumentException, IllegalStateException {
		@SuppressWarnings("unchecked")
		IMap<String, T> map = (IMap<String, T>) _paramsSetsRepository.get(paramTypeName);
		return map;
	}

	@Override
	public boolean paramExists(String paramTypeName, String paramName) {
		IMap<String,?> repository = _paramsSetsRepository.get(paramTypeName);
		if (repository == null) return false;
		return repository.containsKey(paramName);
	}

	@Override
	public ICollection<String> paramTypes() {
		// Class isn't parameterized, thus no archetype is needed by Collection
		ICollection<String> paramTypeNames = new Collection<String>("");
		for(String paramTypeName : _paramsSetsRepository.keySet()) paramTypeNames.add(paramTypeName);
		return paramTypeNames;
	}

	@Override
	public boolean removeParam(String paramTypeName, String paramName) {
		if (getParamsSet(paramTypeName) == null) return false;
		return (_paramsSetsRepository.get(paramTypeName)).removeByKey(paramName) != null;
	}

	@Override
	public void read(String data, boolean overridePreviousData) throws IllegalArgumentException {
		_persistentValuesHandler.readValues(data, _processReadValue, overridePreviousData);
	}

	@Override
	public String write() throws IllegalArgumentException {
		// NB: No archetype is required by PersistentValuesHandler.writeValues()
		ICollection<IPersistentValueToWrite<?>> parameters = new Collection<IPersistentValueToWrite<?>>(null);
		for(String typeName : _paramsSetsRepository.keySet())
		{
			IMap<String,?> paramsSet = _paramsSetsRepository.get(typeName);
			for(String name : paramsSet.getKeys())
			{
				parameters.add(_persistentValuesHandler.makePersistentValueToWrite(name, paramsSet.get(name)));
			}
		}
		return _persistentValuesHandler.writeValues(parameters);
	}

	@Override
	public IGenericParamsSet makeClone() {
		GenericParamsSet cloned = new GenericParamsSet(_persistentValuesHandler, _mapFactory);
		
		for(IMap<String,?> map : _paramsSetsRepository.values())
		{
			for(String key : map.getKeys()) cloned.addParam(key, map.get(key));
		}
		
		return cloned;
	}

	@Override
	public String getInterfaceName() {
		return IGenericParamsSet.class.getCanonicalName();
	}
	
	private class ProcessReadValue implements IAction<IPair<IPersistentValueToWrite<?>,Boolean>>
	{
		private GenericParamsSet _genericParamsSet;
		
		public ProcessReadValue(GenericParamsSet genericParamsSet)
		{
			_genericParamsSet = genericParamsSet;
		}

		@Override
		public String id() {
			throw new UnsupportedOperationException("GenericParamsSet.ReadValue.id accessed illegally");
		}

		@Override
		public void run(IPair<IPersistentValueToWrite<?>,Boolean> input) throws IllegalArgumentException {
			if (!input.getItem2() && _genericParamsSet.paramExists(input.getItem1().typeName(), input.getItem1().name())) throw new IllegalArgumentException("Parameter " + input.getItem1().name() + ", type " + input.getItem1().typeName() + ", already exists");
			_genericParamsSet.addParam(input.getItem1().name(), input.getItem1().value());
		}

		@Override
		public String getInterfaceName() {
			throw new UnsupportedOperationException("GenericParamsSet.ProcessReadValue.getInterfaceName should never be called");
		}

		@Override
		public IPair<IPersistentValueToWrite<?>, Boolean> getArchetype() {
			throw new UnsupportedOperationException("GenericParamsSet.ProcessReadValue.getArchetype should never be called");
		}

		@Override
		public String getUnparameterizedInterfaceName() {
			throw new UnsupportedOperationException("GenericParamsSet.ProcessReadValue.getUnparameterizedInterfaceName should never be called");
		}

		@Override
		public IGame game() {
			throw new UnsupportedOperationException("GenericParamsSet.ProcessReadValue.game should never be called");
		}
		
	}
}

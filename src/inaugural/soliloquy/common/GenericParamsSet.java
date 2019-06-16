package inaugural.soliloquy.common;

import soliloquy.specs.common.entities.IPersistentValuesHandler;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.common.valueobjects.IMap;

import java.util.HashMap;

public class GenericParamsSet extends CanGetInterfaceName implements IGenericParamsSet {
	private HashMap<String, IMap<String,?>> _paramsSetsRepository = new HashMap<>();
	private IPersistentValuesHandler _persistentValuesHandler;
	private IMapFactory _mapFactory;
	
	public GenericParamsSet(IPersistentValuesHandler persistentValuesHandler, IMapFactory mapFactory) {
		_persistentValuesHandler = persistentValuesHandler;
		_mapFactory = mapFactory;
	}
	
	@SuppressWarnings({"unchecked", "ConstantConditions"})
	public <T> void addParam(String name, T value) throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException("GenericParamsSet.addParam: value must not be null");
		}
		String paramTypeName = getProperTypeName(value);
		if (getParamsSet(paramTypeName) == null) {
			addParamsSet(_mapFactory.make("", value));
		}
		((IMap<String,T>) _paramsSetsRepository.get(paramTypeName)).put(name, value);
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public <T> void addParamsSet(IMap<String, T> paramsSet)
			throws IllegalArgumentException, UnsupportedOperationException {
		// TODO: Add a test to ensure that paramsSet archetypes cannot be null
		if (paramsSet == null) {
			throw new IllegalArgumentException("GenericParamsSet.addParamsSet: Cannot add null paramsSet");
		}
		String paramTypeName = getProperTypeName(paramsSet.getSecondArchetype());
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
}

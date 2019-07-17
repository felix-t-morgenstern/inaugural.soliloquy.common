package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.*;

import java.util.HashMap;

public class GenericParamsSetImpl extends CanGetInterfaceName implements GenericParamsSet {
	private final HashMap<String, Map<String,?>> PARAMS_SETS_REPOSITORY = new HashMap<>();
	private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
	private final MapFactory MAP_FACTORY;
	
	public GenericParamsSetImpl(PersistentValuesHandler persistentValuesHandler,
								MapFactory mapFactory) {
		PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
		MAP_FACTORY = mapFactory;
	}
	
	@SuppressWarnings({"unchecked", "ConstantConditions"})
	public <T> void addParam(String name, T value) throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException("GenericParamsSet.addParam: value must not be null");
		}
		String paramTypeName = getProperTypeName(value);
		if (getParamsSet(paramTypeName) == null) {
			addParamsSet(MAP_FACTORY.make("", value));
		}
		((Map<String,T>) PARAMS_SETS_REPOSITORY.get(paramTypeName)).put(name, value);
	}

	@SuppressWarnings({"ConstantConditions", "unchecked"})
	@Override
	public <T> void addParamsSet(ReadOnlyMap<String, T> paramsSet)
			throws IllegalArgumentException, UnsupportedOperationException {
		// TODO: Add a test to ensure that paramsSet archetypes cannot be null
		if (paramsSet == null) {
			throw new IllegalArgumentException("GenericParamsSet.addParamsSet: Cannot add null paramsSet");
		}
		String paramTypeName = getProperTypeName(paramsSet.getSecondArchetype());
		if (PARAMS_SETS_REPOSITORY.containsKey(paramTypeName)) {
			throw new UnsupportedOperationException("GenericParamsSet.addParamsSet: Params set of type "
					+ paramTypeName + " already exists in this params set");
		}
		Map newParamsSet = MAP_FACTORY.make("", paramsSet.getSecondArchetype());
		for(Pair<String, T> param : paramsSet) {
			newParamsSet.put(param.getItem1(), param.getItem2());
		}

		PARAMS_SETS_REPOSITORY.put(paramTypeName, newParamsSet);
	}

	@Override
	public <T> T getParam(String paramTypeName, String paramName) {
		@SuppressWarnings("unchecked")
		Map<String,T> repository = (Map<String, T>) PARAMS_SETS_REPOSITORY.get(paramTypeName);
		if (repository == null) {
			return null;
		}
		return repository.get(paramName);
	}

	@Override
	public <T> Map<String, T> getParamsSet(String paramTypeName)
			throws IllegalArgumentException, IllegalStateException {
		@SuppressWarnings("unchecked")
		Map<String, T> map = (Map<String, T>) PARAMS_SETS_REPOSITORY.get(paramTypeName);
		return map;
	}

	@Override
	public boolean paramExists(String paramTypeName, String paramName) {
		Map<String,?> repository = PARAMS_SETS_REPOSITORY.get(paramTypeName);
		if (repository == null) {
			return false;
		}
		return repository.containsKey(paramName);
	}

	@Override
	public Collection<String> paramTypes() {
		// Class isn't parameterized, thus no archetype is needed by Collection
		Collection<String> paramTypeNames = new CollectionImpl<>("");
		for(String paramTypeName : PARAMS_SETS_REPOSITORY.keySet()) {
			paramTypeNames.add(paramTypeName);
		}
		return paramTypeNames;
	}

	@Override
	public boolean removeParam(String paramTypeName, String paramName) {
		if (getParamsSet(paramTypeName) == null) {
			return false;
		}
		return (PARAMS_SETS_REPOSITORY.get(paramTypeName)).removeByKey(paramName) != null;
	}

	@Override
	public GenericParamsSet makeClone() {
		GenericParamsSetImpl cloned = new GenericParamsSetImpl(PERSISTENT_VALUES_HANDLER, MAP_FACTORY);
		for(Map<String,?> map : PARAMS_SETS_REPOSITORY.values()) {
			for(String key : map.getKeys()) cloned.addParam(key, map.get(key));
		}
		return cloned;
	}

	@Override
	public String getInterfaceName() {
		return GenericParamsSet.class.getCanonicalName();
	}
}

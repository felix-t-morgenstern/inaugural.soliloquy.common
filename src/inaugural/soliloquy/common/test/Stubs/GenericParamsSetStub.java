package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.*;

import java.util.HashMap;

public class GenericParamsSetStub implements IGenericParamsSet {
	public HashMap<String,HashMap<String,Object>> GENERIC_PARAMS = new HashMap<>();

	private IMapFactory MAP_FACTORY = new MapFactoryStub();
	private ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

	@Override
	public IGenericParamsSet makeClone() {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> void addParam(String name, T value) throws IllegalArgumentException {
		String typeName = value.getClass().getCanonicalName();
		if (!GENERIC_PARAMS.containsKey(typeName)) {
			GENERIC_PARAMS.put(typeName, new HashMap<>());
		}
		GENERIC_PARAMS.get(typeName).put(name, value);
	}

	@Override
	public <T> void addParamsSet(IMap<String, T> map)
			throws IllegalArgumentException, UnsupportedOperationException {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getParam(String paramTypeName, String paramName) {
		return (T) GENERIC_PARAMS.get(paramTypeName).get(paramName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IMap<String, T> getParamsSet(String paramTypeName) throws IllegalArgumentException {
		IMap<String, T> paramsSet = MAP_FACTORY.make("", null);
		GENERIC_PARAMS.get(paramTypeName).forEach(((IMap<String, Object>)paramsSet)::put);
		return paramsSet;
	}

	@Override
	public boolean paramExists(String paramTypeName, String paramName) {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public ICollection<String> paramTypes() {
		ICollection<String> paramTypes = COLLECTION_FACTORY.make("");
		GENERIC_PARAMS.keySet().forEach(paramTypes::add);
		return paramTypes;
	}

	@Override
	public boolean removeParam(String paramTypeName, String paramName) {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public String getInterfaceName() {
		return IGenericParamsSet.class.getCanonicalName();
	}
}

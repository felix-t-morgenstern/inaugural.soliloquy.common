package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadableMap;

import java.util.HashMap;

public class GenericParamsSetStub implements GenericParamsSet {
    private HashMap<String, HashMap<String,Object>> GENERIC_PARAMS = new HashMap<>();

    private MapFactory MAP_FACTORY = new MapFactoryStub();
    private CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

    @Override
    public GenericParamsSet makeClone() {
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
    public <T> void addParamsSet(ReadableMap<String, T> map)
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
    public <T> Map<String, T> getParamsSet(String paramTypeName) throws IllegalArgumentException {
        Map<String, T> paramsSet = MAP_FACTORY.make("", null);
        GENERIC_PARAMS.get(paramTypeName).forEach(((Map<String, Object>)paramsSet)::put);
        return paramsSet;
    }

    @Override
    public boolean paramExists(String paramTypeName, String paramName) {
        // Stub class; no implementation needed
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> paramTypes() {
        Collection<String> paramTypes = COLLECTION_FACTORY.make("");
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
        return GenericParamsSet.class.getCanonicalName();
    }
}

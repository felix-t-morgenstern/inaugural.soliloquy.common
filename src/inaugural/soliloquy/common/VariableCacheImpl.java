package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.*;

import java.util.HashMap;

public class VariableCacheImpl implements VariableCache {
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final HashMap<String,Object> PERSISTENT_VARIABLES;

    public VariableCacheImpl(CollectionFactory collectionFactory, MapFactory mapFactory) {
        PERSISTENT_VARIABLES = new HashMap<>();
        COLLECTION_FACTORY = collectionFactory;
        MAP_FACTORY = mapFactory;
    }

    @SuppressWarnings("unchecked")
    private VariableCacheImpl(CollectionFactory collectionFactory, MapFactory mapFactory,
                              HashMap<String,Object> persistentVariables) {
        COLLECTION_FACTORY = collectionFactory;
        MAP_FACTORY = mapFactory;
        PERSISTENT_VARIABLES = (HashMap<String, Object>) persistentVariables.clone();
    }

    @Override
    public String getInterfaceName() {
        return VariableCache.class.getCanonicalName();
    }

    @Override
    public <T> void setVariable(String name, T value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(
                    "VariableCache.put: value cannot be null");
        }
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException(
                    "VariableCache.put: key cannot be null or empty");
        }
        PERSISTENT_VARIABLES.put(name, value);
    }

    @Override
    public boolean remove(String name) {
        return PERSISTENT_VARIABLES.remove(name) != null;
    }

    @Override
    public int size() {
        return PERSISTENT_VARIABLES.size();
    }

    @Override
    public ReadableMap<String,Object> variablesRepresentation() {
        Map<String,Object> variablesMap = MAP_FACTORY.make("", new Object());
        PERSISTENT_VARIABLES.forEach(variablesMap::put);
        return variablesMap.readOnlyRepresentation();
    }

    @Override
    public ReadableCollection<String> namesRepresentation() {
        Collection<String> names = COLLECTION_FACTORY.make("");
        PERSISTENT_VARIABLES.keySet().forEach(names::add);
        return names.representation();
    }

    @Override
    public void clear() {
        PERSISTENT_VARIABLES.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getVariable(String name) {
        return (T) PERSISTENT_VARIABLES.get(name);
    }

    @Override
    public VariableCache makeClone() {
        return new VariableCacheImpl(COLLECTION_FACTORY, MAP_FACTORY, PERSISTENT_VARIABLES);
    }
}

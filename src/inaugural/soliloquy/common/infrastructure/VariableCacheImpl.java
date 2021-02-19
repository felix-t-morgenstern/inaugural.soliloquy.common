package inaugural.soliloquy.common.infrastructure;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.*;

import java.util.HashMap;

public class VariableCacheImpl implements VariableCache {
    private final ListFactory LIST_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final HashMap<String,Object> PERSISTENT_VARIABLES;

    public VariableCacheImpl(ListFactory listFactory, MapFactory mapFactory) {
        PERSISTENT_VARIABLES = new HashMap<>();
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
    }

    @SuppressWarnings("unchecked")
    private VariableCacheImpl(ListFactory listFactory, MapFactory mapFactory,
                              HashMap<String,Object> persistentVariables) {
        LIST_FACTORY = listFactory;
        MAP_FACTORY = mapFactory;
        PERSISTENT_VARIABLES = (HashMap<String, Object>) persistentVariables.clone();
    }

    @Override
    public String getInterfaceName() {
        return VariableCache.class.getCanonicalName();
    }

    @Override
    public <T> void setVariable(String name, T value) throws IllegalArgumentException {
        PERSISTENT_VARIABLES.put(Check.ifNullOrEmpty(name, "name"),
                Check.ifNullOrEmptyIfString(value, "value"));
    }

    @Override
    public boolean remove(String name) {
        return PERSISTENT_VARIABLES.remove(Check.ifNullOrEmpty(name, "name")) != null;
    }

    @Override
    public int size() {
        return PERSISTENT_VARIABLES.size();
    }

    @Override
    public Map<String,Object> variablesRepresentation() {
        Map<String,Object> variablesMap = MAP_FACTORY.make("", new Object());
        PERSISTENT_VARIABLES.forEach(variablesMap::put);
        return variablesMap;
    }

    @Override
    public List<String> namesRepresentation() {
        List<String> names = LIST_FACTORY.make("");
        names.addAll(PERSISTENT_VARIABLES.keySet());
        return names;
    }

    @Override
    public void clear() {
        PERSISTENT_VARIABLES.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getVariable(String name) {
        return (T) PERSISTENT_VARIABLES.get(Check.ifNullOrEmpty(name, "name"));
    }

    @Override
    public VariableCache makeClone() {
        return new VariableCacheImpl(LIST_FACTORY, MAP_FACTORY, PERSISTENT_VARIABLES);
    }
}

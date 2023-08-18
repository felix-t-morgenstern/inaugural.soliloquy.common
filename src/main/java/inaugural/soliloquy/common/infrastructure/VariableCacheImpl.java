package inaugural.soliloquy.common.infrastructure;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;

import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class VariableCacheImpl implements VariableCache {
    private final Map<String, Object> PERSISTENT_VARIABLES;

    public VariableCacheImpl() {
        PERSISTENT_VARIABLES = mapOf();
    }

    private VariableCacheImpl(Map<String, Object> persistentVariables) {
        PERSISTENT_VARIABLES = mapOf(persistentVariables);
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
    public Map<String, Object> variablesRepresentation() {
        return mapOf(PERSISTENT_VARIABLES);
    }

    @Override
    public List<String> namesRepresentation() {
        return listOf(PERSISTENT_VARIABLES.keySet());
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
        return new VariableCacheImpl(PERSISTENT_VARIABLES);
    }
}

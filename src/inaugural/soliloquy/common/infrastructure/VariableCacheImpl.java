package inaugural.soliloquy.common.infrastructure;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableCacheImpl implements VariableCache {
    private final HashMap<String,Object> PERSISTENT_VARIABLES;

    public VariableCacheImpl() {
        PERSISTENT_VARIABLES = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    private VariableCacheImpl(HashMap<String,Object> persistentVariables) {
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
        return new HashMap<>(PERSISTENT_VARIABLES);
    }

    @Override
    public List<String> namesRepresentation() {
        return new ArrayList<>(PERSISTENT_VARIABLES.keySet());
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

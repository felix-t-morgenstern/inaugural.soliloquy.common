package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IPersistentVariable;
import soliloquy.common.specs.IPersistentVariableCache;

public class PersistentVariableCacheStub implements IPersistentVariableCache {
    private final ICollection<IPersistentVariable> PERSISTENT_VARIABLES = new CollectionStub<>();

    public final static String VARIABLE_1_NAME = "variable1";
    public final static Integer VARIABLE_1_VALUE = 456456;

    public final static String VARIABLE_2_NAME = "variable2";
    public final static String VARIABLE_2_VALUE = "variable2value";

    private final ICollection<IPersistentVariable> P_VARS = new CollectionStub<>();

    public PersistentVariableCacheStub() {
        PERSISTENT_VARIABLES.add(new PersistentVariableStub(VARIABLE_1_NAME, VARIABLE_1_VALUE));
        PERSISTENT_VARIABLES.add(new PersistentVariableStub(VARIABLE_2_NAME, VARIABLE_2_VALUE));
    }

    @Override
    public void put(IPersistentVariable persistentVariable) throws IllegalArgumentException {
        P_VARS.add(persistentVariable);
    }

    @Override
    public boolean remove(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return P_VARS.size();
    }

    @Override
    public ICollection<String> getNamesRepresentation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ICollection<IPersistentVariable> getVariablesRepresentation() {
        if (!P_VARS.isEmpty()) {
            return P_VARS;
        } else {
            return PERSISTENT_VARIABLES;
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IPersistentVariable getVariable(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInterfaceName() {
        return IPersistentVariableCache.class.getCanonicalName();
    }
}

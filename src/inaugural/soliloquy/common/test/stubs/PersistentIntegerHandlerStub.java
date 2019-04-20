package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentIntegerHandlerStub implements IPersistentValueTypeHandler<Integer> {
    @Override
    public Integer read(String valueString) throws IllegalArgumentException {
        return Integer.parseInt(valueString);
    }

    @Override
    public String write(Integer value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    @Override
    public Integer getArchetype() {
        return null;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

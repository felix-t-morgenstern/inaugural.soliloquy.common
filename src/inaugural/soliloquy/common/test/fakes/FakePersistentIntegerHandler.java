package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;

public class FakePersistentIntegerHandler implements PersistentValueTypeHandler<Integer> {
    @Override
    public Integer read(String serializedValue) throws IllegalArgumentException {
        return Integer.parseInt(serializedValue);
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
        return 0;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

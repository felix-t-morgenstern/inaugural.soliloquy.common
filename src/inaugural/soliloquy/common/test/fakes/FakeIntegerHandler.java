package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.persistence.TypeHandler;

public class FakeIntegerHandler implements TypeHandler<Integer> {
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
        return TypeHandler.class.getCanonicalName() + "<" + Integer.class.getCanonicalName() + ">";
    }
}

package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;

public class IntegerHandler extends AbstractTypeHandler<Integer> {

    public IntegerHandler() {
        super(0);
    }

    @SuppressWarnings("ConstantConditions")
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
}

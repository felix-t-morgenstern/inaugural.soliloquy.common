package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;

public class IntegerHandler extends AbstractTypeHandler<Integer> {
    @Override
    public String typeHandled() {
        return Integer.class.getCanonicalName();
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
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

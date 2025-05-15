package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;

public class BooleanHandler extends AbstractTypeHandler<Boolean> {
    @Override
    public String typeHandled() {
        return Boolean.class.getCanonicalName();
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    @Override
    public Boolean read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException(
                    "BooleanHandler.read: serializedValue cannot be null");
        }
        return Boolean.parseBoolean(serializedValue);
    }

    @Override
    public String write(Boolean value) {
        return value.toString();
    }
}

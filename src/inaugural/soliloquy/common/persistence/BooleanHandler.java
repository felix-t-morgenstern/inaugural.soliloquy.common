package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;

public class BooleanHandler extends AbstractTypeHandler<Boolean> {

    public BooleanHandler() {
        super(false);
    }

    @SuppressWarnings("ConstantConditions")
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

    @Override
    public Boolean getArchetype() {
        return Boolean.FALSE;
    }

    @Override
    public int hashCode() {
        return BooleanHandler.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BooleanHandler && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return BooleanHandler.class.getCanonicalName();
    }
}

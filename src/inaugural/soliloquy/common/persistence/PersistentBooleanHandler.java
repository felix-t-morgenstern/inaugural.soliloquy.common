package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.PersistentTypeHandler;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;

public class PersistentBooleanHandler extends PersistentTypeHandler<Boolean>
        implements PersistentValueTypeHandler<Boolean> {

    @SuppressWarnings("ConstantConditions")
    @Override
    public Boolean read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException(
                    "PersistentBooleanHandler.read: serializedValue cannot be null");
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
        return PersistentBooleanHandler.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PersistentBooleanHandler && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return PersistentBooleanHandler.class.getCanonicalName();
    }
}

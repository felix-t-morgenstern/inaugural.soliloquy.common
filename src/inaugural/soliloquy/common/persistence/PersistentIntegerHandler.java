package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.PersistentTypeHandler;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;

public class PersistentIntegerHandler extends PersistentTypeHandler<Integer> {

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

    @Override
    public Integer getArchetype() {
        return 0;
    }

    @Override
    public String toString() {
        return PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                Integer.class.getCanonicalName() + ">";
    }

    @Override
    public int hashCode() {
        return (PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                Integer.class.getCanonicalName() + ">").hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PersistentIntegerHandler && obj.hashCode() == hashCode();
    }

}

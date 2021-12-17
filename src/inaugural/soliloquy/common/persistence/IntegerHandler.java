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

    @Override
    public Integer getArchetype() {
        return 0;
    }

    @Override
    public String toString() {
        return TypeHandler.class.getCanonicalName() + "<" +
                Integer.class.getCanonicalName() + ">";
    }

    @Override
    public int hashCode() {
        return (TypeHandler.class.getCanonicalName() + "<" +
                Integer.class.getCanonicalName() + ">").hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntegerHandler && obj.hashCode() == hashCode();
    }

}

package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;

public class StringHandler extends AbstractTypeHandler<String>
        implements TypeHandler<String> {
    @Override
    public String typeHandled() {
        return String.class.getCanonicalName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public String read(String serializedValue) throws IllegalArgumentException {
        Check.ifNull(serializedValue, "serializedValue");
        return serializedValue;
    }

    @Override
    public String write(String value) {
        return value;
    }
}

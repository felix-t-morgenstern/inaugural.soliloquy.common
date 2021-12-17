package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;

public class StringHandler extends AbstractTypeHandler<String>
    implements TypeHandler<String> {

    public StringHandler() {
        super("");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public String read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException("StringHandler.read: serializedValue cannot be null");
        }
        return serializedValue;
    }

    @Override
    public String write(String value) {
        return value;
    }
}

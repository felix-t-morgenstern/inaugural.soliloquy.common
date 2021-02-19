package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.PersistentTypeHandler;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;

public class PersistentStringHandler extends PersistentTypeHandler<String>
    implements PersistentValueTypeHandler<String> {

    @SuppressWarnings("ConstantConditions")
    @Override
    public String read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException("PersistentStringHandler.read: serializedValue cannot be null");
        }
        return serializedValue;
    }

    @Override
    public String write(String value) {
        return value;
    }

    @Override
    public String getArchetype() {
        return "";
    }
}

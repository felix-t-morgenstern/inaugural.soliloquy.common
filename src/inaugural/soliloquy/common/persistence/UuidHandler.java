package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;

import java.util.UUID;

public class UuidHandler extends AbstractTypeHandler<UUID> {
    public UuidHandler() {
        super(UUID.randomUUID());
    }

    @Override
    public UUID read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null || serializedValue.equals("")) {
            return null;
        }
        else {
            return UUID.fromString(serializedValue);
        }
    }

    @Override
    public String write(UUID uuid) {
        if (uuid == null) {
            return "";
        }
        else {
            return uuid.toString();
        }
    }
}

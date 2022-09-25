package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.persistence.TypeHandler;

import java.util.UUID;

public class FakeUuidHandler implements TypeHandler<UUID> {
    @Override
    public UUID read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(UUID uuid) {
        return null;
    }

    @Override
    public UUID getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

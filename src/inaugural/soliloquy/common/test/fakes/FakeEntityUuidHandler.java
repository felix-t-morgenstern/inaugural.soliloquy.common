package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;

public class FakeEntityUuidHandler implements TypeHandler<EntityUuid> {
    @Override
    public EntityUuid read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(EntityUuid entityUuid) {
        return null;
    }

    @Override
    public EntityUuid getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

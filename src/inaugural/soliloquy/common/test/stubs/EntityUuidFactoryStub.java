package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.valueobjects.EntityUuid;

public class EntityUuidFactoryStub implements EntityUuidFactory {
    @Override
    public EntityUuid createFromLongs(long l, long l1) {
        return new EntityUuidStub();
    }

    @Override
    public EntityUuid createFromString(String s) throws IllegalArgumentException {
        return new EntityUuidStub(s);
    }

    @Override
    public EntityUuid createRandomEntityUuid() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

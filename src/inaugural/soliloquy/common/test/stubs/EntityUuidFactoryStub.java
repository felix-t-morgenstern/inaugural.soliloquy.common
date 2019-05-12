package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.IEntityUuid;
import soliloquy.common.specs.IEntityUuidFactory;

public class EntityUuidFactoryStub implements IEntityUuidFactory {
    @Override
    public IEntityUuid createFromLongs(long l, long l1) {
        return new EntityUuidStub();
    }

    @Override
    public IEntityUuid createFromString(String s) throws IllegalArgumentException {
        return new EntityUuidStub(s);
    }

    @Override
    public IEntityUuid createRandomEntityUuid() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.valueobjects.EntityUuid;

public class EntityUuidFactoryImpl implements EntityUuidFactory {

    @Override
    public EntityUuid createFromLongs(long mostSignificantBits, long leastSignificantBits) {
        return new EntityUuidImpl(mostSignificantBits, leastSignificantBits);
    }

    @Override
    public EntityUuid createFromString(String uuidString) throws IllegalArgumentException {
        return new EntityUuidImpl(uuidString);
    }

    @Override
    public EntityUuid createRandomEntityUuid() {
        return new EntityUuidImpl();
    }

    @Override
    public String getInterfaceName() {
        return EntityUuidFactory.class.getCanonicalName();
    }
}

package inaugural.soliloquy.common;

import soliloquy.specs.common.valueobjects.EntityUuid;

import java.util.UUID;

public class EntityUuidImpl implements EntityUuid {
    private UUID _uuid;

    public EntityUuidImpl(long mostSignificantBits, long leastSignificantBits) {
        _uuid = new UUID(mostSignificantBits, leastSignificantBits);
    }

    public EntityUuidImpl(String uuid) {
        _uuid = UUID.fromString(uuid);
    }

    public EntityUuidImpl(){
        _uuid = UUID.randomUUID();
    }

    @Override
    public long getMostSignificantBits() {
        return _uuid.getMostSignificantBits();
    }

    @Override
    public long getLeastSignificantBits() {
        return _uuid.getLeastSignificantBits();
    }

    public String toString() {
        return _uuid.toString();
    }

    @Override
    public boolean equals(Object comparand) {
        return comparand instanceof EntityUuid
                && _uuid.getMostSignificantBits() == ((EntityUuid) comparand).getMostSignificantBits()
                && _uuid.getLeastSignificantBits() == ((EntityUuid) comparand).getLeastSignificantBits();
    }

    @Override
    public String getInterfaceName() {
        return EntityUuid.class.getCanonicalName();
    }
}

package inaugural.soliloquy.common;

import soliloquy.specs.common.valueobjects.EntityUuid;

import java.util.Objects;
import java.util.UUID;

public class EntityUuidImpl implements EntityUuid {
    private final UUID UUID;

    public EntityUuidImpl(long mostSignificantBits, long leastSignificantBits) {
        UUID = new UUID(mostSignificantBits, leastSignificantBits);
    }

    public EntityUuidImpl(String uuid) {
        UUID = java.util.UUID.fromString(uuid);
    }

    public EntityUuidImpl(){
        UUID = java.util.UUID.randomUUID();
    }

    @Override
    public long getMostSignificantBits() {
        return UUID.getMostSignificantBits();
    }

    @Override
    public long getLeastSignificantBits() {
        return UUID.getLeastSignificantBits();
    }

    @Override
    public String getInterfaceName() {
        return EntityUuid.class.getCanonicalName();
    }

    public String toString() {
        return UUID.toString();
    }

    @Override
    public boolean equals(Object comparand) {
        return comparand instanceof EntityUuid
                && UUID.getMostSignificantBits() ==
                ((EntityUuid) comparand).getMostSignificantBits()
                && UUID.getLeastSignificantBits() ==
                ((EntityUuid) comparand).getLeastSignificantBits();
    }

    @Override
    public int hashCode() {
        return Objects.hash(UUID);
    }
}

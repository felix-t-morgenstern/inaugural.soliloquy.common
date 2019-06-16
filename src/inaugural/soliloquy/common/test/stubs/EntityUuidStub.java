package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.valueobjects.IEntityUuid;

public class EntityUuidStub implements IEntityUuid {
    private final String GUID_STRING;

    EntityUuidStub() {
        GUID_STRING = null;
    }

    public EntityUuidStub(String entityUuidString) {
        GUID_STRING = entityUuidString;
    }

    @Override
    public long getMostSignificantBits() {
        return 0;
    }

    @Override
    public long getLeastSignificantBits() {
        return 0;
    }

    @Override
    public String getInterfaceName() {
        return IEntityUuid.class.getCanonicalName();
    }

    @Override
    public String toString() {
        return GUID_STRING;
    }

    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }
}

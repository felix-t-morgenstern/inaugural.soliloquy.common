package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.valueobjects.EntityUuid;

public class FakeEntityUuid implements EntityUuid {
    private final String GUID_STRING;

    FakeEntityUuid() {
        GUID_STRING = null;
    }

    public FakeEntityUuid(String entityUuidString) {
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
        return EntityUuid.class.getCanonicalName();
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

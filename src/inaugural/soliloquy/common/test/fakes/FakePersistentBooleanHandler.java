package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.persistence.PersistentValueTypeHandler;

public class FakePersistentBooleanHandler implements PersistentValueTypeHandler<Boolean> {
    @Override
    public Boolean read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(Boolean aBoolean) {
        return null;
    }

    @Override
    public Boolean getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

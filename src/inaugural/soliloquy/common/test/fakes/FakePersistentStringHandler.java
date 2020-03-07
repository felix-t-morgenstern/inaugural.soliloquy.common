package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;

public class FakePersistentStringHandler implements PersistentValueTypeHandler<String> {
    public final static String ARCHETYPE = "This is the archetype!";

    @Override
    public String read(String s) throws IllegalArgumentException {
        return s;
    }

    @Override
    public String write(String s) {
        return s;
    }

    @Override
    public String getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

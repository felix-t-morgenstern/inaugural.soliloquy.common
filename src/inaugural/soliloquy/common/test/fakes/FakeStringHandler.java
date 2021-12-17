package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.persistence.TypeHandler;

public class FakeStringHandler implements TypeHandler<String> {
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
        return TypeHandler.class.getCanonicalName() + "<" + String.class.getCanonicalName() + ">";
    }
}

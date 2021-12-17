package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.persistence.TypeWithTwoGenericParamsHandler;

@SuppressWarnings("rawtypes")
public class FakePairHandler implements TypeWithTwoGenericParamsHandler<Pair> {
    @Override
    public Pair read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(Pair Pair) {
        return null;
    }

    @Override
    public Pair getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public Pair generateArchetype(String s1, String s2) throws IllegalArgumentException {
        return null;
    }
}

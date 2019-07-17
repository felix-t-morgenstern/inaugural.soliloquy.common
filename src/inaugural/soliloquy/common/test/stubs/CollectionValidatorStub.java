package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.entities.Function;
import soliloquy.specs.game.Game;
import soliloquy.specs.logger.Logger;

public class CollectionValidatorStub<V> implements Function<V,String> {
    public final static int ILLEGAL_VALUE = 123;

    @Override
    public String run(V v) throws IllegalArgumentException {
        return v.equals(ILLEGAL_VALUE) ? "" : null;
    }

    @Override
    public Game game() {
        return null;
    }

    @Override
    public Logger logger() {
        return null;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public V getFirstArchetype() throws IllegalStateException {
        return null;
    }

    @Override
    public String getSecondArchetype() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

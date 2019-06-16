package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.game.IGame;
import soliloquy.specs.logger.ILogger;

public class CollectionValidatorStub<V> implements IFunction<V,String> {
    public final static int ILLEGAL_VALUE = 123;

    @Override
    public String run(V v) throws IllegalArgumentException {
        return v.equals(ILLEGAL_VALUE) ? "" : null;
    }

    @Override
    public IGame game() {
        return null;
    }

    @Override
    public ILogger logger() {
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

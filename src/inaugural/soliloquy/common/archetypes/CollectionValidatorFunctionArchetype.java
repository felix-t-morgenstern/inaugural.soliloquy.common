package inaugural.soliloquy.common.archetypes;

import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.game.IGame;
import soliloquy.specs.logger.ILogger;

public class CollectionValidatorFunctionArchetype<V> implements IFunction<V,String> {
    private final V ARCHETYPE_1;
    private final String ARCHETYPE_2 = "";

    public CollectionValidatorFunctionArchetype (V archetype1) {
        ARCHETYPE_1 = archetype1;
    }

    @Override
    public String run(V v) throws IllegalArgumentException {
        return null;
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
        return ARCHETYPE_1;
    }

    @Override
    public String getSecondArchetype() throws IllegalStateException {
        return ARCHETYPE_2;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

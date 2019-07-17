package inaugural.soliloquy.common.archetypes;

import soliloquy.specs.common.entities.Function;
import soliloquy.specs.game.Game;
import soliloquy.specs.logger.Logger;

public class CollectionValidatorFunctionArchetype<V> implements Function<V,String> {
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

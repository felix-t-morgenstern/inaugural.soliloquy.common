package inaugural.soliloquy.common.archetypes;

import inaugural.soliloquy.tools.generic.HasTwoGenericParams;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.game.Game;
import soliloquy.specs.logger.Logger;

public class MapValidatorFunctionArchetype<K,V> extends HasTwoGenericParams<Pair<K,V>,String>
        implements Function<Pair<K,V>,String> {
    private final Pair<K,V> ARCHETYPE_1;

    public MapValidatorFunctionArchetype(K keyArchetype, V valueArchetype) {
        ARCHETYPE_1 = new PairArchetype<>(keyArchetype, valueArchetype);
    }

    @Override
    public String run(Pair<K, V> kvPair) throws IllegalArgumentException {
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
        return Function.class.getCanonicalName();
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public Pair<K, V> getFirstArchetype() throws IllegalStateException {
        return ARCHETYPE_1;
    }

    @Override
    public String getSecondArchetype() throws IllegalStateException {
        return "";
    }
}

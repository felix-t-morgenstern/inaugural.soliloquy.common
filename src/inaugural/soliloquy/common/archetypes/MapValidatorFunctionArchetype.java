package inaugural.soliloquy.common.archetypes;

import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.game.IGame;
import soliloquy.specs.logger.ILogger;

public class MapValidatorFunctionArchetype<K,V> implements IFunction<IPair<K,V>,String> {
    private final IPair<K,V> ARCHETYPE_1;

    public MapValidatorFunctionArchetype(K keyArchetype, V valueArchetype) {
        ARCHETYPE_1 = new PairArchetype<>(keyArchetype, valueArchetype);
    }

    @Override
    public String run(IPair<K, V> kviPair) throws IllegalArgumentException {
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
    public IPair<K, V> getFirstArchetype() throws IllegalStateException {
        return ARCHETYPE_1;
    }

    @Override
    public String getSecondArchetype() throws IllegalStateException {
        return "";
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

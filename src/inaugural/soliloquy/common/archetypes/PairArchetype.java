package inaugural.soliloquy.common.archetypes;

import soliloquy.specs.common.infrastructure.Pair;

public class PairArchetype<T1,T2> implements Pair<T1,T2> {
    private final T1 ARCHETYPE_1;
    private final T2 ARCHETYPE_2;

    PairArchetype(T1 archetype1, T2 archetype2) {
        ARCHETYPE_1 = archetype1;
        ARCHETYPE_2 = archetype2;
    }

    @Override
    public T1 getItem1() {
        return null;
    }

    @Override
    public void setItem1(T1 t1) throws IllegalArgumentException {

    }

    @Override
    public T2 getItem2() {
        return null;
    }

    @Override
    public void setItem2(T2 t2) throws IllegalArgumentException {

    }

    @Override
    public T1 getFirstArchetype() throws IllegalStateException {
        return ARCHETYPE_1;
    }

    @Override
    public T2 getSecondArchetype() throws IllegalStateException {
        return ARCHETYPE_2;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

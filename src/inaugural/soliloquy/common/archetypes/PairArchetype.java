package inaugural.soliloquy.common.archetypes;

import inaugural.soliloquy.tools.generic.HasTwoGenericParams;
import soliloquy.specs.common.infrastructure.Pair;

public class PairArchetype<T1,T2> extends HasTwoGenericParams<T1,T2> implements Pair<T1,T2> {
    public PairArchetype(T1 archetype1, T2 archetype2) {
        super(archetype1, archetype2);
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
    public String getUnparameterizedInterfaceName() {
        return Pair.class.getCanonicalName();
    }

    @Override
    public Pair<T1, T2> makeClone() {
        return null;
    }
}

package inaugural.soliloquy.common;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Pair;

public class PairFactoryImpl implements PairFactory {

    @Override
    public <T1, T2> Pair<T1, T2> make(T1 item1, T2 item2)
            throws IllegalArgumentException {
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("item1", item1);
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("item2", item2);
        return new PairImpl<>(item1, item2, item1, item2);
    }

    @Override
    public <T1, T2> Pair<T1, T2> make(T1 item1, T2 item2, T1 archetype1, T2 archetype2)
            throws IllegalArgumentException {
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("archetype1", archetype1);
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("archetype2", archetype2);
        return new PairImpl<>(item1, item2, archetype1, archetype2);
    }

    @Override
    public String getInterfaceName() {
        return PairFactory.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return PairFactoryImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PairFactoryImpl && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return PairFactoryImpl.class.getCanonicalName();
    }
}

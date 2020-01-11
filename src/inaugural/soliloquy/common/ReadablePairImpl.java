package inaugural.soliloquy.common;

import soliloquy.specs.common.infrastructure.ReadablePair;

public class ReadablePairImpl<K, V> extends HasTwoGenericParams<K,V> implements ReadablePair<K, V> {
    K _item1;
    V _item2;

    K _archetype1;
    V _archetype2;

    ReadablePairImpl(K item1, V item2, K archetype1, V archetype2) {
        if (archetype1 == null) {
            throw new IllegalArgumentException("ReadablePairImpl: archetype1 is null");
        }
        if (archetype2 == null) {
            throw new IllegalArgumentException("ReadablePairImpl: archetype2 is null");
        }
        _item1 = item1;
        _item2 = item2;
        _archetype1 = archetype1;
        _archetype2 = archetype2;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return ReadablePair.class.getCanonicalName();
    }

    @Override
    public K getItem1() {
        return _item1;
    }

    @Override
    public V getItem2() {
        return _item2;
    }

    @Override
    public K getFirstArchetype() throws IllegalStateException {
        return _archetype1;
    }

    @Override
    public V getSecondArchetype() throws IllegalStateException {
        return _archetype2;
    }
}

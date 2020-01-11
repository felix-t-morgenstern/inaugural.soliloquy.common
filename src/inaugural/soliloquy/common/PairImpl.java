package inaugural.soliloquy.common;

import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadablePair;

public class PairImpl<K,V> extends ReadablePairImpl<K,V> implements Pair<K,V> {
    @SuppressWarnings("ConstantConditions")
    public PairImpl(K item1, V item2, K archetype1, V archetype2) {
        super(item1, item2, archetype1, archetype2);
    }

    @Override
    public void setItem1(K item) throws IllegalArgumentException {
        _item1 = item;
    }

    @Override
    public void setItem2(V item) throws IllegalArgumentException {
        _item2 = item;
    }

    @Override
    public ReadablePair<K, V> representation() {
        return new ReadablePairImpl<>(_item1, _item2, _archetype1, _archetype2);
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return Pair.class.getCanonicalName();
    }
}

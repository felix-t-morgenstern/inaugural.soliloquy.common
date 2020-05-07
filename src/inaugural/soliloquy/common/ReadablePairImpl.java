package inaugural.soliloquy.common;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.ReadablePair;

import java.util.Objects;

public class ReadablePairImpl<K, V> extends HasTwoGenericParams<K,V>
        implements ReadablePair<K, V> {
    K _item1;
    V _item2;

    K _archetype1;
    V _archetype2;

    public ReadablePairImpl(K item1, V item2, K archetype1, V archetype2) {
        _item1 = item1;
        _item2 = item2;
        _archetype1 = Check.ifNull(archetype1, "archetype1");
        _archetype2 = Check.ifNull(archetype2, "archetype2");
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

    @Override
    public int hashCode() {
        return Objects.hash(_item1, _item2);
    }

    @SuppressWarnings({"rawtypes", "RedundantIfStatement"})
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ReadablePair)) {
            return false;
        }
        ReadablePair pair = (ReadablePair) obj;

        if (_item1 == null) {
            if (pair.getItem1() != null) {
                return false;
            }
        }
        else {
            if (!_item1.equals(pair.getItem1())) {
                return false;
            }
        }

        if (_item2 == null) {
            if (pair.getItem2() != null) {
                return false;
            }
        }
        else {
            if (!_item2.equals(pair.getItem2())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException(
                "ReadablePairImpl.toString: Operation not supported; use " +
                        "PersistentPairHandler.write instead");
    }
}

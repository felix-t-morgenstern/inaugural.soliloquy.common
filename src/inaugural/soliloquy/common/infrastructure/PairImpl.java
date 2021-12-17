package inaugural.soliloquy.common.infrastructure;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.infrastructure.Pair;

import java.util.Objects;

public class PairImpl<T1, T2> implements Pair<T1, T2> {
    private T1 _item1;
    private T2 _item2;

    private final T1 _archetype1;
    private final T2 _archetype2;

    private static final CanGetInterfaceName GET_INTERFACE_NAME = new CanGetInterfaceName();

    public PairImpl(T1 item1, T2 item2, T1 archetype1, T2 archetype2) {
        _item1 = item1;
        _item2 = item2;
        _archetype1 = Check.ifNull(archetype1, "archetype1");
        _archetype2 = Check.ifNull(archetype2, "archetype2");
    }

    @Override
    public T1 getItem1() {
        return _item1;
    }

    @Override
    public T2 getItem2() {
        return _item2;
    }

    @Override
    public void setItem1(T1 item) throws IllegalArgumentException {
        _item1 = item;
    }

    @Override
    public void setItem2(T2 item) throws IllegalArgumentException {
        _item2 = item;
    }

    @Override
    public Pair<T1, T2> makeClone() {
        return new PairImpl<>(_item1, _item2, _archetype1, _archetype2);
    }

    @Override
    public T1 getFirstArchetype() throws IllegalStateException {
        return _archetype1;
    }

    @Override
    public T2 getSecondArchetype() throws IllegalStateException {
        return _archetype2;
    }

    @Override
    public String getInterfaceName() {
        return Pair.class.getCanonicalName() + "<" +
                GET_INTERFACE_NAME.getProperTypeName(_archetype1) + "," +
                GET_INTERFACE_NAME.getProperTypeName(_archetype2) + ">";
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("PairImpl.toString: Not supported; c.f. " +
                "PairHandler");
    }

    @Override
    public int hashCode() {
        return Objects.hash(_item1, _item2);
    }

    @SuppressWarnings({"rawtypes", "RedundantIfStatement"})
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;

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
}

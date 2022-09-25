package inaugural.soliloquy.common.test.fakes;

import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.infrastructure.List;

import java.util.ArrayList;
import java.util.Collection;

public class FakeList<V> extends ArrayList<V> implements List<V> {
    private V _archetype;
    private final static CanGetInterfaceName GET_INTERFACE_NAME = new CanGetInterfaceName();

    public FakeList() {

    }

    public FakeList(V archetype) {
        _archetype = archetype;
    }

    public FakeList(Collection<V> collection) {
        super(collection);
    }

    public FakeList(Collection<V> collection, V archetype) {
        super(collection);
        _archetype = archetype;
    }

    @Override
    public List<V> makeClone() {
        return new FakeList<>(this, _archetype);
    }

    @Override
    public V getArchetype() {
        return _archetype;
    }

    @Override
    public String getInterfaceName() {
        return List.class.getCanonicalName() + "<" +
                GET_INTERFACE_NAME.getProperTypeName(_archetype) + ">";
    }
}

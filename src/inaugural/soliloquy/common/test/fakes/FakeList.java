package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.List;

import java.util.ArrayList;
import java.util.Collection;

public class FakeList<V> extends ArrayList<V> implements List<V> {
    private V _archetype;

    public FakeList() {

    }

    public FakeList(V archetype) {
        _archetype = archetype;
    }

    @SuppressWarnings("ConstantConditions")
    public FakeList(Collection<V> collection) {
        super(collection);
    }

    @Override
    public List<V> makeClone() {
        return null;
    }

    @Override
    public V getArchetype() {
        return _archetype;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

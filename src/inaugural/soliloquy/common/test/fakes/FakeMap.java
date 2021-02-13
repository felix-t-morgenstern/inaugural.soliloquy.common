package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;

import java.util.HashMap;

public class FakeMap<K,V> extends HashMap<K,V> implements Map<K,V> {
    public K _keyArchetype;
    public V _valueArchetype;

    public FakeMap() {

    }

    public FakeMap(K keyArchetype, V valueArchetype) {
        _keyArchetype = keyArchetype;
        _valueArchetype = valueArchetype;
    }

    public FakeMap(java.util.Map<K,V> map, K keyArchetype, V valueArchetype) {
        super(map);
        _keyArchetype = keyArchetype;
        _valueArchetype = valueArchetype;
    }

    @Override
    public Map<K, V> makeClone() {
        return null;
    }

    @Override
    public K getFirstArchetype() throws IllegalStateException {
        return _keyArchetype;
    }

    @Override
    public V getSecondArchetype() throws IllegalStateException {
        return _valueArchetype;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public List<V> getValuesList() {
        return null;
    }
}

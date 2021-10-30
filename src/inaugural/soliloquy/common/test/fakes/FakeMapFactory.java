package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;

public class FakeMapFactory implements MapFactory {

    @Override
    public <K, V> Map<K, V> make(K archetype1, V archetype2) {
        return new FakeMap<>(archetype1, archetype2);
    }

    @Override
    public <K, V> Map<K, V> make(java.util.Map<K, V> map, K archetype1, V archetype2)
            throws IllegalArgumentException {
        return new FakeMap<>(map, archetype1, archetype2);
    }

    @Override
    public String getInterfaceName() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }
}

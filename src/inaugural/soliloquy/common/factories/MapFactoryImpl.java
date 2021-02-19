package inaugural.soliloquy.common.factories;

import inaugural.soliloquy.common.infrastructure.MapImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;

public class MapFactoryImpl implements MapFactory {
    private final ListFactory LIST_FACTORY;

    // TODO: Ensure constructor parameters are non-null
    public MapFactoryImpl(ListFactory listFactory) {
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
    }

    // TODO: Test this method, incl whether archetypes' child archetypes are present
    public <K, V> Map<K,V> make(K archetype1, V archetype2) {
        return new MapImpl<>(LIST_FACTORY, archetype1, archetype2);
    }

    // TODO: Test this method, incl whether archetypes' child archetypes are present
    @Override
    public <K, V> Map<K, V> make(java.util.Map<K, V> map, K k, V v) throws IllegalArgumentException {
        return new MapImpl<>(LIST_FACTORY, map, k, v);
    }

    @Override
    public String getInterfaceName() {
        return MapFactory.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return MapFactoryImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MapFactoryImpl && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return MapFactoryImpl.class.getCanonicalName();
    }
}

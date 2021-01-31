package inaugural.soliloquy.common;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Map;

public class MapFactoryImpl implements MapFactory {
    private final PairFactory PAIR_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;

    // TODO: Ensure constructor parameters are non-null
    public MapFactoryImpl(PairFactory pairFactory, CollectionFactory collectionFactory) {
        PAIR_FACTORY = Check.ifNull(pairFactory, "pairFactory");
        COLLECTION_FACTORY = Check.ifNull(collectionFactory, "collectionFactory");
    }

    public <K, V> Map<K,V> make(K archetype1, V archetype2) {
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("archetype1", archetype1);
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("archetype2", archetype2);
        return new MapImpl<>(PAIR_FACTORY, archetype1, archetype2, COLLECTION_FACTORY);
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

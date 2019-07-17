package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Map;

public class MapFactoryImpl extends CanCheckArchetypeAndArchetypesOfArchetype implements MapFactory {
	private final PairFactory PAIR_FACTORY;
	private final CollectionFactory COLLECTION_FACTORY;
	
	public MapFactoryImpl(PairFactory pairFactory, CollectionFactory collectionFactory) {
		PAIR_FACTORY = pairFactory;
		COLLECTION_FACTORY = collectionFactory;
	}
	
	public <K, V> Map<K,V> make(K archetype1, V archetype2) {
		checkArchetypeAndArchetypesOfArchetype("make", archetype1);
		checkArchetypeAndArchetypesOfArchetype("make", archetype2);
		return new MapImpl<>(PAIR_FACTORY, archetype1, archetype2, COLLECTION_FACTORY);
	}

	@Override
	public String getInterfaceName() {
		return MapFactory.class.getCanonicalName();
	}

	@Override
	protected String className() {
		return "MapFactory";
	}
}

package inaugural.soliloquy.common;

import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPairFactory;

public class MapFactory extends CanCheckArchetypeAndArchetypesOfArchetype implements IMapFactory {
	private final IPairFactory PAIR_FACTORY;
	private final ICollectionFactory COLLECTION_FACTORY;
	
	public MapFactory(IPairFactory pairFactory, ICollectionFactory collectionFactory) {
		PAIR_FACTORY = pairFactory;
		COLLECTION_FACTORY = collectionFactory;
	}
	
	public <K, V> IMap<K,V> make(K archetype1, V archetype2) {
		checkArchetypeAndArchetypesOfArchetype("make", archetype1);
		checkArchetypeAndArchetypesOfArchetype("make", archetype2);
		return new Map<>(PAIR_FACTORY, archetype1, archetype2, COLLECTION_FACTORY);
	}

	@Override
	public String getInterfaceName() {
		return IMapFactory.class.getCanonicalName();
	}

	@Override
	protected String className() {
		return "MapFactory";
	}
}

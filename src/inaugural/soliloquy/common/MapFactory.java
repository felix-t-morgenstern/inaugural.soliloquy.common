package inaugural.soliloquy.common;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPairFactory;

public class MapFactory extends CanCheckArchetypeAndArchetypesOfArchetype implements IMapFactory {
	private IPairFactory _pairFactory;
	
	public MapFactory(IPairFactory pairFactory) {
		_pairFactory = pairFactory;
	}
	
	public <K, V> IMap<K,V> make(K archetype1, V archetype2) {
		checkArchetypeAndArchetypesOfArchetype("make", archetype1);
		checkArchetypeAndArchetypesOfArchetype("make", archetype2);
		return new Map<K,V>(_pairFactory, archetype1, archetype2);
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

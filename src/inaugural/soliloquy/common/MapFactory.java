package inaugural.soliloquy.common;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPairFactory;

public class MapFactory implements IMapFactory {
	private IPairFactory _pairFactory;
	
	public MapFactory(IPairFactory pairFactory) {
		_pairFactory = pairFactory;
	}
	
	public <K, V> IMap<K,V> make(K archetype1, V archetype2) {
		if (archetype1 == null) {
			throw new IllegalArgumentException("archetype1 is null");
		}
		if (archetype2 == null) {
			throw new IllegalArgumentException("archetype2 is null");
		}
		return new Map<K,V>(_pairFactory, archetype1, archetype2);
	}

	@Override
	public String getInterfaceName() {
		return IMapFactory.class.getCanonicalName();
	}
}

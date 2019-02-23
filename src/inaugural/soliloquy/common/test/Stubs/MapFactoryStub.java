package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;

public class MapFactoryStub implements IMapFactory
{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <K, V> IMap<K, V> make(K archetype1, V archetype2) {
		return new MapStub();
	}    	

	@Override
	public String getInterfaceName() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}
}

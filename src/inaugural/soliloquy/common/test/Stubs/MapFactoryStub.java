package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.valueobjects.IMap;

public class MapFactoryStub implements IMapFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <K, V> IMap<K, V> make(K archetype1, V archetype2) {
		return new MapStub(archetype1, archetype2);
	}    	

	@Override
	public String getInterfaceName() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}
}

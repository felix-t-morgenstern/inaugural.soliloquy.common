package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.infrastructure.ICollection;

public class CollectionFactoryStub implements ICollectionFactory {
	@Override
	public <T> ICollection<T> make(T archetype) throws IllegalArgumentException {
		return new CollectionStub<T>(archetype);
	}

	@Override
	public <T> ICollection<T> make(T[] items, T archetype) throws IllegalArgumentException {
		// Stub; not implemented
		throw new UnsupportedOperationException();
	}

	@Override
	public String getInterfaceName() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}
}

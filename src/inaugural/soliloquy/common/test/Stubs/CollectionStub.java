package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IReadOnlyCollection;

public class CollectionStub<V> extends ReadOnlyCollectionStub<V> implements ICollection<V> {
	public CollectionStub() {
		super();
	}
	
	public CollectionStub(V archetype) {
		super(archetype);
	}

	@Override
	public void add(V item) throws UnsupportedOperationException {
		_collection.add(item);
	}

	@Override
	public void addAll(ICollection<? extends V> items) throws UnsupportedOperationException {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public void addAll(V[] items) throws UnsupportedOperationException {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() throws UnsupportedOperationException {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItem(V item) throws UnsupportedOperationException {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public ICollection<IFunction<V, String>> validators() {
		return null;
	}

	@Override
	public IReadOnlyCollection<V> readOnlyRepresentation() {
		return new ReadOnlyCollectionStub<>(_archetype, _collection);
	}

	@Override
	public String getUnparameterizedInterfaceName() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}
}

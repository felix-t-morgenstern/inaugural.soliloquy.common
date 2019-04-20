package inaugural.soliloquy.common.test.stubs;

import java.util.ArrayList;
import java.util.Iterator;

import soliloquy.common.specs.ICollection;

public class CollectionStub<V> implements ICollection<V> {
	private V _archetype;
	
	public CollectionStub() {
	}
	
	public CollectionStub(V archetype) {
		_archetype = archetype;
	}
	
	private ArrayList<V> _collection = new ArrayList<V>();

	@Override
	public Iterator<V> iterator() {
		return _collection.iterator();
	}

	@Override
	public ICollection<V> makeClone() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public V getArchetype() {
		return _archetype;
	}

	@Override
	public String getInterfaceName() {
		return "soliloquy.common.specs.ICollection<" + _archetype.getClass().getCanonicalName() + ">";
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
	public boolean contains(V item) {
		return _collection.contains(item);
	}

	@Override
	public boolean equals(ICollection<V> items) {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(int index) {
		return _collection.get(index);
	}

	@Override
	public boolean isEmpty() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return _collection.size();
	}

	@Override
	public boolean removeItem(V item) throws UnsupportedOperationException {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public String getUnparameterizedInterfaceName() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}
}

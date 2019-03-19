package inaugural.soliloquy.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import soliloquy.common.specs.ICollection;

public class Collection<V> extends HasOneGenericParam<V> implements ICollection<V> {
	private ArrayList<V> _collection;
	private V _archetype;
	
	public Collection(V archetype) {
		_collection = new ArrayList<V>();
		_archetype = archetype;
	}
	
	public Collection(V[] items, V archetype) {
		_collection = new ArrayList<V>(Arrays.asList(items));
		_archetype = archetype;
	}
	
	@Override
	public Iterator<V> iterator() {
		return _collection.iterator();
	}

	@Override
	public ICollection<V> makeClone() {
		Collection<V> clone = new Collection<V>(_archetype);
		for(V item : _collection) clone.add(item);
		return clone;
	}

	@Override
	public void add(V item) throws UnsupportedOperationException {
		_collection.add(item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addAll(ICollection<? extends V> items) throws UnsupportedOperationException {
		this.addAll((V[])items.toArray());
	}

	@Override
	public void addAll(V[] items) throws UnsupportedOperationException {
		_collection.addAll(Arrays.asList(items));
	}

	@Override
	public void clear() throws UnsupportedOperationException {
		_collection.clear();
	}

	@Override
	public boolean contains(V item) {
		return _collection.contains(item);
	}

	@Override
	public boolean equals(ICollection<V> items) {
		if (items == null) return false;
		if (_collection.size() != items.size()) return false;
		for(V item : _collection) if(!items.contains(item)) return false;
		return true;
	}

	@Override
	public V get(int index) {
		return _collection.get(index);
	}

	@Override
	public boolean isEmpty() {
		return _collection.isEmpty();
	}

	@Override
	public Object[] toArray() {
		return _collection.toArray();
	}

	@Override
	public int size() {
		return _collection.size();
	}

	@Override
	public boolean removeItem(V item) throws UnsupportedOperationException {
		return _collection.remove(item);
	}

	@Override
	public V getArchetype() {
		return _archetype;
	}

	@Override
	public String getUnparameterizedInterfaceName() {
		return "soliloquy.common.specs.ICollection";
	}

}

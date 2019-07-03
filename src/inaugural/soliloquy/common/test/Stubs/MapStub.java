package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.common.infrastructure.IReadOnlyMap;

public class MapStub<K,V> extends ReadOnlyMapStub<K,V> implements IMap<K,V> {

	public MapStub() {

	}

	public MapStub(K archetype1, V archetype2) {
		super(archetype1, archetype2);
	}

	@Override
	public void clear() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public void put(K key, V value) throws IllegalArgumentException {
		_map.put(key, value);
	}

	@Override
	public void putAll(ICollection<IPair<K, V>> items) throws IllegalArgumentException {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public V removeByKey(K key) {
		return _map.remove(key);
	}

	@Override
	public boolean removeByKeyAndValue(K key, V value) {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public ICollection<IFunction<IPair<K, V>, String>> validators() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public IReadOnlyMap<K, V> readOnlyRepresentation() {
		return new ReadOnlyMapStub<>(_archetype1, _archetype2, _map);
	}

	@Override
	public String getUnparameterizedInterfaceName() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}
}

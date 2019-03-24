package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.IPair;

public class PairStub<K,V> implements IPair<K,V> {
	private K _item1;
	private V _item2;
	
	public PairStub(K item1, V item2) {
		_item1 = item1;
		_item2 = item2;
	}

	@Override
	public K getFirstArchetype() throws IllegalStateException {
		// Stub method, unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public V getSecondArchetype() throws IllegalStateException {
		// Stub method, unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public String getInterfaceName() {
		// Stub method, unimplemented
		throw new UnsupportedOperationException();
	}

	@Override
	public K getItem1() {
		return _item1;
	}

	@Override
	public void setItem1(K item) throws IllegalArgumentException {
		_item1 = item;
	}

	@Override
	public V getItem2() {
		return _item2;
	}

	@Override
	public void setItem2(V item) throws IllegalArgumentException {
		_item2 = item;
	}

	@Override
	public String getUnparameterizedInterfaceName() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}
	
}

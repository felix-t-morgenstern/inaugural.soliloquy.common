package inaugural.soliloquy.common;

import soliloquy.common.specs.IPair;

public class Pair<K,V> extends HasTwoGenericParams<K,V> implements IPair<K,V> {
	private K _item1;
	private V _item2;
	
	private K _archetype1;
	private V _archetype2;
	
	public Pair(K item1, V item2)
	{
		if (item1 == null) throw new IllegalArgumentException("item1 is null");
		if (item2 == null) throw new IllegalArgumentException("item2 is null");
		_item1 = item1;
		_item2 = item2;
		_archetype1 = item1;
		_archetype2 = item2;
	}
	
	public Pair(K item1, V item2, K archetype1, V archetype2)
	{
		if (archetype1 == null) throw new IllegalArgumentException("archetype1 is null");
		if (archetype2 == null) throw new IllegalArgumentException("archetype2 is null");
		_item1 = item1;
		_item2 = item2;
		_archetype1 = archetype1;
		_archetype2 = archetype2;
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
	public K getFirstArchetype() throws IllegalStateException {
		return _archetype1;
	}

	@Override
	public V getSecondArchetype() throws IllegalStateException {
		return _archetype2;
	}

	@Override
	protected String getUnparameterizedClassName() {
		return "soliloquy.common.specs.IPair";
	}
}

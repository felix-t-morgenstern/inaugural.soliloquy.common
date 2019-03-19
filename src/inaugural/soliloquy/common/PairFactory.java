package inaugural.soliloquy.common;

import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;

public class PairFactory implements IPairFactory {

	@Override
	public <T1, T2> IPair<T1, T2> make(T1 item1, T2 item2)
			throws IllegalArgumentException {
		if (item1 == null) throw new IllegalArgumentException("item1 is null");
		if (item2 == null) throw new IllegalArgumentException("item2 is null");
		return new Pair<T1,T2>(item1, item2, item1, item2);
	}
	
	@Override
	public <T1, T2> IPair<T1, T2> make(T1 item1, T2 item2, T1 archetype1, T2 archetype2)
			throws IllegalArgumentException {
		if (archetype1 == null) throw new IllegalArgumentException("archetype1 is null");
		if (archetype2 == null) throw new IllegalArgumentException("archetype2 is null");
		return new Pair<T1,T2>(item1, item2, archetype1, archetype2);
	}

	@Override
	public String getInterfaceName() {
		return IPairFactory.class.getCanonicalName();
	}

}

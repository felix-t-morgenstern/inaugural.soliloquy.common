package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.IPairFactory;
import soliloquy.specs.common.infrastructure.IPair;

public class PairFactory extends CanCheckArchetypeAndArchetypesOfArchetype
		implements IPairFactory {

	@Override
	public <T1, T2> IPair<T1, T2> make(T1 item1, T2 item2)
			throws IllegalArgumentException {
		checkArchetypeAndArchetypesOfArchetype("make", item1);
		checkArchetypeAndArchetypesOfArchetype("make", item2);
		return new Pair<>(item1, item2, item1, item2);
	}
	
	@Override
	public <T1, T2> IPair<T1, T2> make(T1 item1, T2 item2, T1 archetype1, T2 archetype2)
			throws IllegalArgumentException {
		checkArchetypeAndArchetypesOfArchetype("make", archetype1);
		checkArchetypeAndArchetypesOfArchetype("make", archetype2);
		return new Pair<>(item1, item2, archetype1, archetype2);
	}

	@Override
	public String getInterfaceName() {
		return IPairFactory.class.getCanonicalName();
	}

	@Override
	protected String className() {
		return "PairFactory";
	}
}

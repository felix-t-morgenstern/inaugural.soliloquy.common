package inaugural.soliloquy.common;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;

public class CollectionFactory extends CanCheckArchetypeAndArchetypesOfArchetype
		implements ICollectionFactory {

	@Override
	public <T> ICollection<T> make(T archetype) {
		checkArchetypeAndArchetypesOfArchetype("make", archetype);
		return new Collection<T>(archetype);
	}

	@Override
	public <T> ICollection<T> make(T[] items, T archetype) throws IllegalArgumentException {
		return new Collection<T>(items, archetype);
	}

	@Override
	public String getInterfaceName() {
		return ICollectionFactory.class.getCanonicalName();
	}

	@Override
	protected String className() {
		return "CollectionFactory";
	}
}

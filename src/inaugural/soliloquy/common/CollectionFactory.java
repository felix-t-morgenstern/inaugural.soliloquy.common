package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.infrastructure.ICollection;

public class CollectionFactory extends CanCheckArchetypeAndArchetypesOfArchetype
		implements ICollectionFactory {

	@Override
	public <T> ICollection<T> make(T archetype) {
		checkArchetypeAndArchetypesOfArchetype("make", archetype);
		return new Collection<>(archetype);
	}

	@Override
	public <T> ICollection<T> make(T[] items, T archetype) throws IllegalArgumentException {
		return new Collection<>(items, archetype);
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

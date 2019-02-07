package inaugural.soliloquy.common;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;

public class CollectionFactory implements ICollectionFactory {

	@Override
	public <T> ICollection<T> make(T archetype) {
		if (archetype == null) throw new IllegalArgumentException("archetype is null");
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

}

package inaugural.soliloquy.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import inaugural.soliloquy.common.archetypes.CollectionValidatorFunctionArchetype;
import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.common.valueobjects.ICollection;

public class Collection<V> extends HasOneGenericParam<V> implements ICollection<V> {
	private final ArrayList<V> COLLECTION;
	private final V ARCHETYPE;
	private final ICollection<IFunction<V,String>> VALIDATORS;
	
	public Collection(V archetype) {
		COLLECTION = new ArrayList<>();
		ARCHETYPE = archetype;
		VALIDATORS = new Collection<>(new CollectionValidatorFunctionArchetype<>(archetype), true);
	}
	
	public Collection(V[] items, V archetype) {
		COLLECTION = new ArrayList<>(Arrays.asList(items));
		ARCHETYPE = archetype;
		VALIDATORS = new Collection<>(new CollectionValidatorFunctionArchetype<>(archetype), true);
	}

	private Collection(ArrayList<V> collection, V archetype) {
		COLLECTION = collection;
		ARCHETYPE = archetype;
		VALIDATORS = new Collection<>(new CollectionValidatorFunctionArchetype<>(archetype), true);
	}

	// NB: This is used so that calls to create VALIDATORS in the public constructors above do not
	//    hit an unending loop. This _does_ imply that VALIDATORS should not have any validators.
	@SuppressWarnings("unused")
	private Collection(V archetype, boolean unused) {
		COLLECTION = new ArrayList<>();
		ARCHETYPE = archetype;
		VALIDATORS = null;
	}
	
	@Override
	public Iterator<V> iterator() {
		return COLLECTION.iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ICollection<V> makeClone() {
		ICollection<V> clone =  new Collection<>((ArrayList<V>) COLLECTION.clone(), ARCHETYPE);
		for(IFunction<V,String> validator : validators()) {
			clone.validators().add(validator);
		}
		return clone;
	}

	@Override
	public void add(V item) throws UnsupportedOperationException {
		// NB: VALIDATORS is only null when it is, in fact, a collection of validators; in this
		// case, the added validator must be non-null
		if (VALIDATORS != null) {
			for (IFunction<V, String> validator : VALIDATORS) {
				String exceptionMessage = validator.run(item);
				if (exceptionMessage != null) {
					throw new IllegalArgumentException(
							"Collection.add: Validation on entry failed; " + exceptionMessage);
				}
			}
		} else if(item == null) {
			throw new IllegalArgumentException("Collection.add: You cannot add a null validator");
		}
		COLLECTION.add(item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addAll(ICollection<? extends V> items) throws UnsupportedOperationException {
		this.addAll((V[])items.toArray());
	}

	@Override
	public void addAll(V[] items) throws UnsupportedOperationException {
		for (V item : items) {
			add(item);
		}
	}

	@Override
	public void clear() throws UnsupportedOperationException {
		COLLECTION.clear();
	}

	@Override
	public boolean contains(V item) {
		return COLLECTION.contains(item);
	}

	@Override
	public boolean equals(ICollection<V> items) {
		if (items == null) return false;
		if (COLLECTION.size() != items.size()) return false;
		for(V item : COLLECTION) if(!items.contains(item)) return false;
		return true;
	}

	@Override
	public V get(int index) {
		return COLLECTION.get(index);
	}

	@Override
	public boolean isEmpty() {
		return COLLECTION.isEmpty();
	}

	@Override
	public Object[] toArray() {
		return COLLECTION.toArray();
	}

	@Override
	public int size() {
		return COLLECTION.size();
	}

	@Override
	public boolean removeItem(V item) throws UnsupportedOperationException {
		return COLLECTION.remove(item);
	}

	@Override
	public ICollection<IFunction<V, String>> validators() {
		return VALIDATORS;
	}

	@Override
	public V getArchetype() {
		return ARCHETYPE;
	}

	@Override
	public String getUnparameterizedInterfaceName() {
		return ICollection.class.getCanonicalName();
	}

}

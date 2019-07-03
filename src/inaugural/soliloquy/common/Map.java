package inaugural.soliloquy.common;

import java.util.HashMap;

import inaugural.soliloquy.common.archetypes.MapValidatorFunctionArchetype;
import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.factories.IPairFactory;
import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.common.infrastructure.IReadOnlyMap;

public class Map<K,V> extends ReadOnlyMap<K,V> implements IMap<K,V> {
	final ICollection<IFunction<IPair<K,V>,String>> VALIDATORS;

	@SuppressWarnings("unchecked")
	public Map(IPairFactory pairFactory, K archetype1, V archetype2,
			   ICollectionFactory collectionFactory) {
		super(archetype1, archetype2, pairFactory, collectionFactory);
		VALIDATORS = collectionFactory.make(
				new MapValidatorFunctionArchetype(ARCHETYPE_1, ARCHETYPE_2));
	}
	
	@SuppressWarnings("unchecked")
	Map(IPairFactory pairFactory, K archetype1, V archetype2, ICollectionFactory collectionFactory,
		HashMap<K,V> internalMap) {
		super(archetype1, archetype2, internalMap, pairFactory, collectionFactory);
		VALIDATORS = collectionFactory.make(
				new MapValidatorFunctionArchetype(ARCHETYPE_1, ARCHETYPE_2));
	}

	@Override
	public void clear() {
		MAP.clear();
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public void put(K key, V value) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("Map.put: Key to a Map cannot be null");
		}
		if (key == "") {
			throw new IllegalArgumentException("Map.put: Blank string is an illegal key");
		}
		IPair<K,V> toInsert = PAIR_FACTORY.make(key, value, ARCHETYPE_1, ARCHETYPE_2);
		for(IFunction<IPair<K,V>, String> validator : VALIDATORS) {
			String exceptionMessage = validator.run(toInsert);
			if (exceptionMessage != null) {
				throw new IllegalArgumentException("Map.put: Input failed validation; " +
						exceptionMessage);
			}
		}
		MAP.put(key, value);
	}

	@Override
	public void putAll(ICollection<IPair<K, V>> items) throws IllegalArgumentException {
		for(IPair<K,V> item : items) {
			put(item.getItem1(), item.getItem2());
		}
	}

	@Override
	public V removeByKey(K key) {
		return MAP.remove(key);
	}

	@Override
	public boolean removeByKeyAndValue(K key, V value) {
		return MAP.remove(key, value);
	}

	@Override
	public ICollection<IFunction<IPair<K, V>, String>> validators() {
		return VALIDATORS;
	}

	@Override
	public IMap<K,V> makeClone() {
		IMap<K,V> clonedMap = super.makeClone();
		VALIDATORS.forEach(clonedMap.validators()::add);
		return clonedMap;
	}

	@Override
	public IReadOnlyMap<K, V> readOnlyRepresentation() {
		return new ReadOnlyMap<>(ARCHETYPE_1, ARCHETYPE_2, MAP, PAIR_FACTORY, COLLECTION_FACTORY);
	}

	@Override
	public String getUnparameterizedInterfaceName() {
		return IMap.class.getCanonicalName();
	}

}

package inaugural.soliloquy.common;

import java.util.HashMap;

import inaugural.soliloquy.common.archetypes.MapValidatorFunctionArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.*;

public class MapImpl<K,V> extends ReadableMapImpl<K,V> implements Map<K,V> {
    final Collection<Function<Pair<K,V>,String>> VALIDATORS;

    @SuppressWarnings("ConstantConditions")
    public MapImpl(PairFactory pairFactory, K archetype1, V archetype2,
                   CollectionFactory collectionFactory) {
        super(archetype1, archetype2, pairFactory, collectionFactory);
        VALIDATORS = collectionFactory.make(
                new MapValidatorFunctionArchetype<>(ARCHETYPE_1, ARCHETYPE_2));
    }

    MapImpl(PairFactory pairFactory, K archetype1, V archetype2, CollectionFactory collectionFactory,
            HashMap<K,V> internalMap) {
        super(archetype1, archetype2, internalMap, pairFactory, collectionFactory);
        VALIDATORS = collectionFactory.make(
                new MapValidatorFunctionArchetype<>(ARCHETYPE_1, ARCHETYPE_2));
    }

    @Override
    public void clear() {
        MAP.clear();
    }

    @Override
    public void put(K key, V value) throws IllegalArgumentException {
        Pair<K,V> toInsert = PAIR_FACTORY.make(Check.ifNullOrEmptyIfString(key, "key"), value,
                ARCHETYPE_1, ARCHETYPE_2);
        for(Function<Pair<K,V>, String> validator : VALIDATORS) {
            String exceptionMessage = validator.run(toInsert);
            if (exceptionMessage != null) {
                throw new IllegalArgumentException("Map.put: Input failed validation; " +
                        exceptionMessage);
            }
        }
        MAP.put(key, value);
    }

    @Override
    public void putAll(ReadableCollection<Pair<K, V>> items) throws IllegalArgumentException {
        for(Pair<K,V> item : items) {
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
    public Collection<Function<Pair<K, V>, String>> validators() {
        return VALIDATORS;
    }

    @Override
    public Map<K,V> makeClone() {
        Map<K,V> clonedMap = super.makeClone();
        VALIDATORS.forEach(clonedMap.validators()::add);
        return clonedMap;
    }

    @Override
    public ReadableMap<K, V> readOnlyRepresentation() {
        return new ReadableMapImpl<>(ARCHETYPE_1, ARCHETYPE_2, MAP, PAIR_FACTORY, COLLECTION_FACTORY);
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return Map.class.getCanonicalName();
    }

}

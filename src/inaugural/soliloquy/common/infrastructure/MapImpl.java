package inaugural.soliloquy.common.infrastructure;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;

import java.util.HashMap;

public class MapImpl<K,V> extends HashMap<K,V> implements Map<K,V> {
    private final ListFactory LIST_FACTORY;
    private final K KEY_ARCHETYPE;
    private final V VALUE_ARCHETYPE;

    private static CanGetInterfaceName GET_INTERFACE_NAME = new CanGetInterfaceName();

    // TODO: Ensure that archetype's child archetypes are tested in constructor
    public MapImpl(ListFactory listFactory, K keyArchetype, V valueArchetype) {
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("keyArchetype", keyArchetype);
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("valueArchetype", valueArchetype);
        KEY_ARCHETYPE = Check.ifNull(keyArchetype, "keyArchetype");
        VALUE_ARCHETYPE = Check.ifNull(valueArchetype, "valueArchetype");
    }

    // TODO: Ensure that archetype's child archetypes are tested in constructor
    @SuppressWarnings("ConstantConditions")
    public MapImpl(ListFactory listFactory, java.util.Map<K,V> map,
                   K keyArchetype, V valueArchetype) {
        super(Check.ifNull(map, "map"));
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("keyArchetype", keyArchetype);
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("valueArchetype", valueArchetype);
        KEY_ARCHETYPE = Check.ifNull(keyArchetype, "keyArchetype");
        VALUE_ARCHETYPE = Check.ifNull(valueArchetype, "valueArchetype");
    }

    @Override
    public V get(Object key) {
        return super.get(Check.ifNullOrEmptyIfString(key, "key"));
    }

    @Override
    public V put(K key, V value) {
        return super.put(Check.ifNullOrEmptyIfString(key, "key"), value);
    }

    @Override
    public Map<K, V> makeClone() {
        return new MapImpl<>(LIST_FACTORY, this, KEY_ARCHETYPE, VALUE_ARCHETYPE);
    }

    @Override
    public K getFirstArchetype() throws IllegalStateException {
        return KEY_ARCHETYPE;
    }

    @Override
    public V getSecondArchetype() throws IllegalStateException {
        return VALUE_ARCHETYPE;
    }

    @Override
    public String getInterfaceName() {
        return Map.class.getCanonicalName() + "<" +
                GET_INTERFACE_NAME.getProperTypeName(KEY_ARCHETYPE) + "," +
                GET_INTERFACE_NAME.getProperTypeName(VALUE_ARCHETYPE) + ">";
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("PairImpl.toString: Not supported; c.f. " +
                "MapHandler");
    }

    @Override
    public List<V> getValuesList() {
        return LIST_FACTORY.make(values(), VALUE_ARCHETYPE);
    }
}

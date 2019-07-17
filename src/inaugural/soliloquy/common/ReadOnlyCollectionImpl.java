package inaugural.soliloquy.common;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadOnlyCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

class ReadOnlyCollectionImpl<V> extends HasOneGenericParam<V> implements ReadOnlyCollection<V> {
    final ArrayList<V> COLLECTION;
    final V ARCHETYPE;

    ReadOnlyCollectionImpl(V archetype) {
        COLLECTION = new ArrayList<>();
        ARCHETYPE = archetype;
    }

    ReadOnlyCollectionImpl(V[] items, V archetype) {
        COLLECTION = new ArrayList<>(Arrays.asList(items));
        ARCHETYPE = archetype;
    }

    @Override
    public boolean contains(V item) {
        return COLLECTION.contains(item);
    }

    @Override
    public boolean equals(ReadOnlyCollection<V> items) {
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

    @SuppressWarnings("unchecked")
    @Override
    public Collection<V> makeClone() {
        return new CollectionImpl<>((V[]) COLLECTION.toArray(), ARCHETYPE);
    }

    @Override
    public V getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return ReadOnlyCollection.class.getCanonicalName();
    }

    @Override
    public Iterator<V> iterator() {
        return COLLECTION.iterator();
    }
}
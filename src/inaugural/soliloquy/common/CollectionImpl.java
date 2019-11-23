package inaugural.soliloquy.common;

import inaugural.soliloquy.common.archetypes.CollectionValidatorFunctionArchetype;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;

public class CollectionImpl<V> extends ReadableCollectionImpl<V> implements Collection<V> {
    private final Collection<Function<V,String>> VALIDATORS;

    public CollectionImpl(V archetype) {
        super(archetype);
        VALIDATORS = new CollectionImpl<>(new CollectionValidatorFunctionArchetype<>(archetype), true);
    }

    CollectionImpl(V[] items, V archetype) {
        super(items, archetype);
        VALIDATORS = new CollectionImpl<>(new CollectionValidatorFunctionArchetype<>(archetype), true);
    }

    // NB: This is used so that calls to create VALIDATORS in the public constructors above do not
    //    hit an unending loop. This _does_ imply that VALIDATORS should not have any validators.
    @SuppressWarnings("unused")
    private CollectionImpl(V archetype, boolean unused) {
        super(archetype);
        VALIDATORS = null;
    }

    @Override
    public void add(V item) throws UnsupportedOperationException {
        // NB: VALIDATORS is only null when it is, in fact, a collection of validators; in this
        // case, the added validator must be non-null
        if (VALIDATORS != null) {
            for (Function<V, String> validator : VALIDATORS) {
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
    public void addAll(Collection<? extends V> items) throws UnsupportedOperationException {
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
    public boolean removeItem(V item) throws UnsupportedOperationException {
        return COLLECTION.remove(item);
    }

    @Override
    public Collection<Function<V, String>> validators() {
        return VALIDATORS;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadableCollection<V> readOnlyRepresentation() {
        return new ReadableCollectionImpl<>((V[]) COLLECTION.toArray(), ARCHETYPE);
    }

    @Override
    public Collection<V> makeClone() {
        Collection<V> clone =  super.makeClone();
        assert VALIDATORS != null;
        VALIDATORS.forEach(clone.validators()::add);
        return clone;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return Collection.class.getCanonicalName();
    }

}

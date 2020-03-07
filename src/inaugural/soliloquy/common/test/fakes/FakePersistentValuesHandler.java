package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.*;

public class FakePersistentValuesHandler implements PersistentValuesHandler {

    private final String COLLECTION_GENERIC_INTERFACE_NAME = Collection.class.getCanonicalName();

    @Override
    public void addPersistentValueTypeHandler(PersistentValueTypeHandler<?> PersistentValueTypeHandler) throws IllegalArgumentException {

    }

    @Override
    public boolean removePersistentValueTypeHandler(String s) {
        return false;
    }

    @Override
    public <T> PersistentValueTypeHandler<T> getPersistentValueTypeHandler(String persistentValueType) throws UnsupportedOperationException {
        if (interfaceIsOfGenericType(persistentValueType, COLLECTION_GENERIC_INTERFACE_NAME)) {
            return (PersistentValueTypeHandler<T>) new FakePersistentCollectionHandler();
        } else if (persistentValueType.equals(Integer.class.getCanonicalName())) {
            return (PersistentValueTypeHandler<T>) new FakePersistentIntegerHandler();
        } else if (persistentValueType.equals(String.class.getCanonicalName())) {
            return (PersistentValueTypeHandler<T>) new FakePersistentStringHandler();
        } else if (persistentValueType.equals(FakeHasIdAndName.class.getCanonicalName())) {
            return (PersistentValueTypeHandler<T>) new PersistentHasIdAndNameHandler();
        } else {
            return null;
        }
    }

    private boolean interfaceIsOfGenericType(String valueType, String genericInterfaceName) {
        return valueType.length() >= genericInterfaceName.length()
                && valueType.substring(0, genericInterfaceName.length())
                .equals(genericInterfaceName);
    }

    @Override
    public Object generateArchetype(String valueType) throws IllegalArgumentException {
        if (interfaceIsOfGenericType(valueType, COLLECTION_GENERIC_INTERFACE_NAME)) {
            return new FakePersistentCollectionHandler().generateArchetype(valueType);
        } else {
            return getPersistentValueTypeHandler(valueType).getArchetype();
        }
    }

    @Override
    public Collection<String> persistentValueTypesHandled() {
        return null;
    }

    @Override
    public void registerPersistentPairHandler(PersistentPairHandler PersistentPairHandler) {

    }

    @Override
    public void registerPersistentRegistryHandler(PersistentRegistryHandler persistentRegistryHandler) {

    }

    @Override
    public void registerPersistentCollectionHandler(PersistentCollectionHandler PersistentCollectionHandler) {

    }

    @Override
    public void registerPersistentMapHandler(PersistentMapHandler PersistentMapHandler) {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

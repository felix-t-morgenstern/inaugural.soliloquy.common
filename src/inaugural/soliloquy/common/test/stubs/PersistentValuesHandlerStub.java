package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.*;

public class PersistentValuesHandlerStub implements PersistentValuesHandler {

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
            return (PersistentValueTypeHandler<T>) new PersistentCollectionHandlerStub();
        } else if (persistentValueType.equals(Integer.class.getCanonicalName())) {
            return (PersistentValueTypeHandler<T>) new PersistentIntegerHandlerStub();
        } else if (persistentValueType.equals(String.class.getCanonicalName())) {
            return (PersistentValueTypeHandler<T>) new PersistentStringHandlerStub();
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
            return new PersistentCollectionHandlerStub().generateArchetype(valueType);
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

package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.*;

public class PersistentValuesHandlerStub implements IPersistentValuesHandler {

    private final String COLLECTION_GENERIC_INTERFACE_NAME = ICollection.class.getCanonicalName();

    @Override
    public void addPersistentValueTypeHandler(IPersistentValueTypeHandler<?> iPersistentValueTypeHandler) throws IllegalArgumentException {

    }

    @Override
    public boolean removePersistentValueTypeHandler(String s) {
        return false;
    }

    @Override
    public <T> IPersistentValueTypeHandler<T> getPersistentValueTypeHandler(String persistentValueType) throws UnsupportedOperationException {
        if (interfaceIsOfGenericType(persistentValueType, COLLECTION_GENERIC_INTERFACE_NAME)) {
            return (IPersistentValueTypeHandler<T>) new PersistentCollectionHandlerStub();
        } else if (persistentValueType.equals(Integer.class.getCanonicalName())) {
            return (IPersistentValueTypeHandler<T>) new PersistentIntegerHandlerStub();
        } else if (persistentValueType.equals(String.class.getCanonicalName())) {
            return (IPersistentValueTypeHandler<T>) new PersistentStringHandlerStub();
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
    public ICollection<String> persistentValueTypesHandled() {
        return null;
    }

    @Override
    public void registerPersistentPairHandler(IPersistentPairHandler iPersistentPairHandler) {

    }

    @Override
    public void registerPersistentCollectionHandler(IPersistentCollectionHandler iPersistentCollectionHandler) {

    }

    @Override
    public void registerPersistentMapHandler(IPersistentMapHandler iPersistentMapHandler) {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

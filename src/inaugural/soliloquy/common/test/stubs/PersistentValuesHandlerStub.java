package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.*;

public class PersistentValuesHandlerStub implements IPersistentValuesHandler {
    @Override
    public void addPersistentValueTypeHandler(IPersistentValueTypeHandler<?> iPersistentValueTypeHandler) throws IllegalArgumentException {

    }

    @Override
    public boolean removePersistentValueTypeHandler(String s) {
        return false;
    }

    @Override
    public <T> IPersistentValueTypeHandler<T> getPersistentValueTypeHandler(String persistentValueType) throws UnsupportedOperationException {
        if (persistentValueType.equals(Integer.class.getCanonicalName())) {
            return (IPersistentValueTypeHandler<T>) new PersistentIntegerHandlerStub();
        } else if (persistentValueType.equals(String.class.getCanonicalName())) {
            return (IPersistentValueTypeHandler<T>) new PersistentStringHandlerStub();
        } else {
            return null;
        }
    }

    @Override
    public ICollection<String> persistentValueTypesHandled() {
        return null;
    }

    @Override
    public void readValues(String s, IAction<IPersistentValueToWrite> iAction) {

    }

    @Override
    public String writeValues(ICollection<IPersistentValueToWrite> iCollection) {
        return null;
    }

    @Override
    public IPersistentValueToRead makePersistentValueToRead(String s, String s1, String s2) {
        return null;
    }

    @Override
    public <T> IPersistentValueToWrite<T> makePersistentValueToWrite(String s, T t) {
        return null;
    }

    @Override
    public void registerPersistentPairHandler(IPersistentValueTypeHandler<IPair> iPersistentValueTypeHandler) {

    }

    @Override
    public void registerPersistentCollectionHandler(IPersistentValueTypeHandler<ICollection> iPersistentValueTypeHandler) {

    }

    @Override
    public void registerPersistentMapHandler(IPersistentValueTypeHandler<IMap> iPersistentValueTypeHandler) {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

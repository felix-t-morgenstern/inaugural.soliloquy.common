package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.*;

public class GenericParamsSetPersistentValuesHandlerStub implements IPersistentValuesHandler {
	@Override
	public void addPersistentValueTypeHandler(IPersistentValueTypeHandler<?> persistentValueTypeHandler)
			throws IllegalArgumentException {
		// Not needed for test stub
	}

	@Override
	public boolean removePersistentValueTypeHandler(String persistentValueType) {
		// Not needed for test stub
		return false;
	}

	@Override
	public <T> IPersistentValueTypeHandler<T> getPersistentValueTypeHandler(String persistentValueType)
			throws UnsupportedOperationException {
		// Not needed for test stub
		return null;
	}

	@Override
	public Object generateArchetype(String s) throws IllegalArgumentException {
		return null;
	}

	@Override
	public ICollection<String> persistentValueTypesHandled() {
		// Not needed for test stub
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
		// Stub method, unimplemented
		throw new UnsupportedOperationException();
	}

}

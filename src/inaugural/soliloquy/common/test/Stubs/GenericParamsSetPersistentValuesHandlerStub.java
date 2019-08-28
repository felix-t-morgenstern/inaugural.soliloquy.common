package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.*;

public class GenericParamsSetPersistentValuesHandlerStub implements PersistentValuesHandler {
	@Override
	public void addPersistentValueTypeHandler(PersistentValueTypeHandler<?> persistentValueTypeHandler)
			throws IllegalArgumentException {
		// Not needed for test stub
	}

	@Override
	public boolean removePersistentValueTypeHandler(String persistentValueType) {
		// Not needed for test stub
		return false;
	}

	@Override
	public <T> PersistentValueTypeHandler<T> getPersistentValueTypeHandler(String persistentValueType)
			throws UnsupportedOperationException {
		// Not needed for test stub
		return null;
	}

	@Override
	public Object generateArchetype(String s) throws IllegalArgumentException {
		return null;
	}

	@Override
	public Collection<String> persistentValueTypesHandled() {
		// Not needed for test stub
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
		// Stub method, unimplemented
		throw new UnsupportedOperationException();
	}

}

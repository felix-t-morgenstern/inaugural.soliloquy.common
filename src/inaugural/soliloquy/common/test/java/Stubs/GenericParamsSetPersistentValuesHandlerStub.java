package inaugural.soliloquy.common.test.java.Stubs;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import inaugural.soliloquy.common.Pair;
import soliloquy.common.specs.IAction;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPersistentValueToRead;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.common.specs.IPersistentValueTypeHandler;
import soliloquy.common.specs.IPersistentValuesHandler;

public class GenericParamsSetPersistentValuesHandlerStub implements IPersistentValuesHandler {
	@Override
	public void addPersistentValueTypeHandler(IPersistentValueTypeHandler<?> persistentValueTypeHandler) throws IllegalArgumentException {
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
	public ICollection<String> persistentValueTypesHandled() {
		// Not needed for test stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readValues(String valuesString, IAction<IPair<IPersistentValueToWrite<?>,Boolean>> valueProcessing, boolean overridePreviousData) {
		IPersistentValueToWrite<String> persistentValueToWrite = (IPersistentValueToWrite<String>) mock(IPersistentValueToWrite.class);
		when(persistentValueToWrite.typeName()).thenReturn(String.class.getCanonicalName());
		when(persistentValueToWrite.name()).thenReturn("DummyValue");
		when(persistentValueToWrite.value()).thenReturn(valuesString);
		valueProcessing.run(new Pair<IPersistentValueToWrite<?>,Boolean>(persistentValueToWrite,overridePreviousData));
	}

	@Override
	public String writeValues(ICollection<IPersistentValueToWrite<?>> persistentValuesToProcess) {
		String result = "";
		for(IPersistentValueToWrite<?> persistentValueToProcess : persistentValuesToProcess)
		{
			result += "Name:"+persistentValueToProcess.name()+",Value:"+persistentValueToProcess.value()+";";
		}
		return result;
	}

	@Override
	public IPersistentValueToRead makePersistentValueToRead(String typeName, String name, String value) {
		// Not needed for test stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IPersistentValueToWrite<T> makePersistentValueToWrite(String name, T value) {
		IPersistentValueToWrite<T> resultMock = (IPersistentValueToWrite<T>) mock(IPersistentValueToWrite.class);
		when(resultMock.typeName()).thenReturn(value.getClass().getCanonicalName());
		when(resultMock.name()).thenReturn(name);
		when(resultMock.value()).thenReturn(value);
		return resultMock;
	}

	@Override
	public String getInterfaceName() {
		// Stub method, unimplemented
		throw new UnsupportedOperationException();
	}

}

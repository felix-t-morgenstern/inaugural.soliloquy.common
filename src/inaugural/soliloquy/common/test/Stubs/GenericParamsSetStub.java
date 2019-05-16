package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;

public class GenericParamsSetStub implements IGenericParamsSet {

	@Override
	public void read(String data) throws IllegalArgumentException {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public String write() throws IllegalArgumentException {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public IGenericParamsSet makeClone() {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> void addParam(String name, T value) throws IllegalArgumentException {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> void addParam(String name, T value, T archetype) throws IllegalArgumentException {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> void addParamsSet(IMap<String, T> paramsSet, T paramArchetype)
			throws IllegalArgumentException, UnsupportedOperationException {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getParam(String paramTypeName, String paramName) {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> IMap<String, T> getParamsSet(String paramTypeName) throws IllegalArgumentException {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> boolean paramExists(String paramTypeName, String paramName) {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public ICollection<String> paramTypes() {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeParam(String paramTypeName, String paramName) {
		// Stub class; no implementation needed
		throw new UnsupportedOperationException();
	}

	@Override
	public String getInterfaceName() {
		// Stub method, unimplemented
		throw new UnsupportedOperationException();
	}
}
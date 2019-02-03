package inaugural.soliloquy.common;

import soliloquy.common.specs.IPersistentVariable;
import soliloquy.common.specs.IPersistentVariableFactory;

public class PersistentVariableFactory implements IPersistentVariableFactory {

	@Override
	public <T> IPersistentVariable make(String name, T value) {
		return new PersistentVariable(name, value);
	}

}

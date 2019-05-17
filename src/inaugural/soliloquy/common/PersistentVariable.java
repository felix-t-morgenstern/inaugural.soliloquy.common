package inaugural.soliloquy.common;

import soliloquy.common.specs.IPersistentVariable;

public class PersistentVariable extends CanGetInterfaceName implements IPersistentVariable {
	private final String NAME;
	
	private Object _value;
	
	public PersistentVariable(String name, Object value) {
		NAME = name;
		_value = value;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue() {
		return (T) _value;
	}

	@Override
	public <T> void setValue(T value) {
		_value = value;
	}

	@Override
	public String getInterfaceName() {
		return IPersistentVariable.class.getCanonicalName();
	}

}

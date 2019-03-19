package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentStringHandler extends PersistentTypeHandler<String>
	implements IPersistentValueTypeHandler<String> {

	@Override
	public String read(String valueString) throws IllegalArgumentException {
		if (valueString == null) throw new IllegalArgumentException("valueString cannot be null");
		return valueString;
	}

	@Override
	public String write(String value) {
		return value;
	}

	@Override
	public String getArchetype() {
		return new String();
	}

}

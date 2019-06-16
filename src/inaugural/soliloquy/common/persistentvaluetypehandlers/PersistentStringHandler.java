package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.specs.common.entities.IPersistentValueTypeHandler;

public class PersistentStringHandler extends PersistentTypeHandler<String>
	implements IPersistentValueTypeHandler<String> {

	@SuppressWarnings("ConstantConditions")
	@Override
	public String read(String valueString) throws IllegalArgumentException {
		if (valueString == null) {
			throw new IllegalArgumentException("PersistentStringHandler.read: valueString cannot be null");
		}
		return valueString;
	}

	@Override
	public String write(String value) {
		return value;
	}

	@Override
	public String getArchetype() {
		return "";
	}
}

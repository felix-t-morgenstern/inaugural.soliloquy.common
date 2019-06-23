package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.specs.common.entities.IPersistentValueTypeHandler;

public class PersistentBooleanHandler extends PersistentTypeHandler<Boolean>
		implements IPersistentValueTypeHandler<Boolean> {

	@SuppressWarnings("ConstantConditions")
	@Override
	public Boolean read(String serializedValue) throws IllegalArgumentException {
		if (serializedValue == null) {
			throw new IllegalArgumentException(
					"PersistentBooleanHandler.read: serializedValue cannot be null");
		}
		return Boolean.parseBoolean(serializedValue);
	}

	@Override
	public String write(Boolean value) {
		return value.toString();
	}

	@Override
	public Boolean getArchetype() {
		return Boolean.FALSE;
	}

}

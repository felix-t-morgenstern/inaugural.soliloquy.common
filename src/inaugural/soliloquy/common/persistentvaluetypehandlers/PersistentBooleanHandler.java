package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentBooleanHandler extends PersistentTypeHandler<Boolean> implements IPersistentValueTypeHandler<Boolean> {

	@Override
	public Boolean read(String valueString) throws IllegalArgumentException {
		if (valueString == null) {
			throw new IllegalArgumentException("PersistentBooleanHandler.read: valueString cannot be null");
		}
		return Boolean.parseBoolean(valueString);
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

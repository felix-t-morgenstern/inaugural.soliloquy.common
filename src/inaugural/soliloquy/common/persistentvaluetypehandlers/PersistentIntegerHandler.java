package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;

public class PersistentIntegerHandler extends PersistentTypeHandler<Integer>
		implements PersistentValueTypeHandler<Integer> {

	@SuppressWarnings("ConstantConditions")
	@Override
	public Integer read(String serializedValue) throws IllegalArgumentException {
		return Integer.parseInt(serializedValue);
	}

	@Override
	public String write(Integer value) {
		if (value == null) {
			return "";
		}
		return value.toString();
	}

	@Override
	public Integer getArchetype() {
		return 0;
	}

}

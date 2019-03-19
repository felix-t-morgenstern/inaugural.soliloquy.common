package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentIntegerHandler extends PersistentTypeHandler<Integer> implements IPersistentValueTypeHandler<Integer> {

	@Override
	public Integer read(String valueString) throws IllegalArgumentException {
		return Integer.parseInt(valueString);
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

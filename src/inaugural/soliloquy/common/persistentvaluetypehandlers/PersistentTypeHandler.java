package inaugural.soliloquy.common.persistentvaluetypehandlers;

import inaugural.soliloquy.common.HasOneGenericParam;
import soliloquy.common.specs.IPersistentValueTypeHandler;

public abstract class PersistentTypeHandler<T> extends HasOneGenericParam<T> implements IPersistentValueTypeHandler<T> {

	@Override
	public abstract T getArchetype();

	@Override
	public String getUnparameterizedInterfaceName() {
		return "soliloquy.common.persistentvaluetypehandlers.IPersistentValueTypeHandler";
	}

}

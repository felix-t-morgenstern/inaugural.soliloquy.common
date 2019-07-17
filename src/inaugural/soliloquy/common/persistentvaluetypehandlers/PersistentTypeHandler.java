package inaugural.soliloquy.common.persistentvaluetypehandlers;

import inaugural.soliloquy.common.HasOneGenericParam;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;

public abstract class PersistentTypeHandler<T> extends HasOneGenericParam<T>
	implements PersistentValueTypeHandler<T> {

	@Override
	public abstract T getArchetype();

	@Override
	public String getUnparameterizedInterfaceName() {
		return PersistentValueTypeHandler.class.getCanonicalName();
	}

}

package inaugural.soliloquy.common.persistentvaluetypehandlers;

import inaugural.soliloquy.common.HasOneGenericParam;
import soliloquy.specs.common.entities.IPersistentValueTypeHandler;

public abstract class PersistentTypeHandler<T> extends HasOneGenericParam<T>
	implements IPersistentValueTypeHandler<T> {

	@Override
	public abstract T getArchetype();

	@Override
	public String getUnparameterizedInterfaceName() {
		return IPersistentValueTypeHandler.class.getCanonicalName();
	}

}

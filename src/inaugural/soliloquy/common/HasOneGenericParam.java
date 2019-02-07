package inaugural.soliloquy.common;

import soliloquy.common.specs.IHasOneGenericParam;
import soliloquy.common.specs.ISoliloquyClass;

public abstract class HasOneGenericParam<T> extends HasGenericParams implements IHasOneGenericParam<T> {
	@Override
	public String getInterfaceName() {
		String innerClassName;
		if (getArchetype() instanceof ISoliloquyClass)
		{
			if (getArchetype() == null) throw new IllegalStateException("Archetype of a generic type must not be null");
			innerClassName = ((ISoliloquyClass) getArchetype()).getInterfaceName();
		}
		else
		{
			innerClassName = getArchetype().getClass().getCanonicalName();
		}
		return getUnparameterizedInterfaceName() + "<" + innerClassName + ">";
	}
}

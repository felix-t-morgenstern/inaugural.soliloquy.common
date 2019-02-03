package inaugural.soliloquy.common;

import soliloquy.common.specs.IHasGenericParams;
import soliloquy.common.specs.IHasOneGenericParam;

public abstract class HasOneGenericParam<T> implements IHasOneGenericParam<T> {
	protected abstract String getUnparameterizedClassName();

	@Override
	public String getParameterizedClassName() {
		String innerClassName;
		if (getArchetype() instanceof IHasGenericParams)
		{
			if (getArchetype() == null) throw new IllegalStateException("Archetype of a generic type must not be null");
			innerClassName = ((IHasGenericParams) getArchetype()).getParameterizedClassName();
		}
		else
		{
			innerClassName = getArchetype().getClass().getCanonicalName();
		}
		return getUnparameterizedClassName() + "<" + innerClassName + ">";
	}
}

package inaugural.soliloquy.common;

import soliloquy.common.specs.IHasGenericParams;
import soliloquy.common.specs.IHasTwoGenericParams;

public abstract class HasTwoGenericParams<P1,P2> implements IHasTwoGenericParams<P1,P2> {
	protected abstract String getUnparameterizedClassName();
	
	@Override
	public String getParameterizedClassName() {
		String innerClassName_1;
		String innerClassName_2;
		
		if (getFirstArchetype() instanceof IHasGenericParams)
		{
			if (getFirstArchetype() == null) throw new IllegalStateException("Archetype of a generic type must not be null (first param)");
			innerClassName_1 = ((IHasGenericParams) getFirstArchetype()).getParameterizedClassName();
		}
		else
		{
			innerClassName_1 = getFirstArchetype().getClass().getCanonicalName();
		}
		if (getSecondArchetype() instanceof IHasGenericParams)
		{
			if (getSecondArchetype() == null) throw new IllegalStateException("Archetype of a generic type must not be null (second param)");
			innerClassName_2 = ((IHasGenericParams) getSecondArchetype()).getParameterizedClassName();
		}
		else
		{
			innerClassName_2 = getSecondArchetype().getClass().getCanonicalName();
		}
		
		return getUnparameterizedClassName() + "<" + innerClassName_1 + "," + innerClassName_2 + ">";
	}

}

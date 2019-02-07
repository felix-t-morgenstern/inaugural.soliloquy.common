package inaugural.soliloquy.common;

import soliloquy.common.specs.IHasTwoGenericParams;
import soliloquy.common.specs.ISoliloquyClass;

public abstract class HasTwoGenericParams<P1,P2> extends HasGenericParams implements IHasTwoGenericParams<P1,P2> {	
	@Override
	public String getInterfaceName() {
		String innerClassName_1;
		String innerClassName_2;
		
		if (getFirstArchetype() instanceof ISoliloquyClass)
		{
			if (getFirstArchetype() == null) throw new IllegalStateException("Archetype of a generic type must not be null (first param)");
			innerClassName_1 = ((ISoliloquyClass) getFirstArchetype()).getInterfaceName();
		}
		else
		{
			innerClassName_1 = getFirstArchetype().getClass().getCanonicalName();
		}
		if (getSecondArchetype() instanceof ISoliloquyClass)
		{
			if (getSecondArchetype() == null) throw new IllegalStateException("Archetype of a generic type must not be null (second param)");
			innerClassName_2 = ((ISoliloquyClass) getSecondArchetype()).getInterfaceName();
		}
		else
		{
			innerClassName_2 = getSecondArchetype().getClass().getCanonicalName();
		}
		
		return getUnparameterizedInterfaceName() + "<" + innerClassName_1 + "," + innerClassName_2 + ">";
	}

}

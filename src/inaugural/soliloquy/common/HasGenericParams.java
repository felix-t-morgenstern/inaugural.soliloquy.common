package inaugural.soliloquy.common;

import soliloquy.common.specs.IHasGenericParams;

public abstract class HasGenericParams extends CanGetInterfaceName implements IHasGenericParams {
	public abstract String getUnparameterizedInterfaceName();
}

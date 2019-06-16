package inaugural.soliloquy.common;

import soliloquy.specs.common.shared.IHasGenericParams;

public abstract class HasGenericParams extends CanGetInterfaceName implements IHasGenericParams {
	public abstract String getUnparameterizedInterfaceName();
}

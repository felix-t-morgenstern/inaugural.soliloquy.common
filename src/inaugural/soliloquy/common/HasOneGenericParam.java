package inaugural.soliloquy.common;

import soliloquy.common.specs.IHasOneGenericParam;
import soliloquy.common.specs.ISoliloquyClass;

public abstract class HasOneGenericParam<T> extends HasGenericParams
		implements IHasOneGenericParam<T> {
	private String _parameterizedClassName;

	@Override
	public String getInterfaceName() {
		if (_parameterizedClassName == null) {
			_parameterizedClassName = getUnparameterizedInterfaceName() + "<"
					+ getProperTypeName(getArchetype()) + ">";
		}
		return _parameterizedClassName;
	}
}

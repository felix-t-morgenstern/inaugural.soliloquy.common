package inaugural.soliloquy.common;

public abstract class HasOneGenericParam<T> extends inaugural.soliloquy.common.HasGenericParams
		implements soliloquy.specs.common.shared.HasOneGenericParam<T> {
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

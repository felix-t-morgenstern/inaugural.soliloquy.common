package inaugural.soliloquy.common;

public abstract class HasTwoGenericParams<P1,P2> extends HasGenericParams
		implements soliloquy.specs.common.shared.HasTwoGenericParams<P1,P2> {
	private String _parameterizedClassName;
	
	@Override
	public String getInterfaceName() {
		if (_parameterizedClassName == null) {
			_parameterizedClassName = getUnparameterizedInterfaceName() +
					"<" + getProperTypeName(getFirstArchetype()) + "," +
					getProperTypeName(getSecondArchetype()) + ">";
		}
		return _parameterizedClassName;
	}
}

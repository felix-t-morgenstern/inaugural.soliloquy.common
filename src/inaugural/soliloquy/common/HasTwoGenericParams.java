package inaugural.soliloquy.common;

import soliloquy.specs.common.shared.IHasTwoGenericParams;

public abstract class HasTwoGenericParams<P1,P2> extends HasGenericParams
		implements IHasTwoGenericParams<P1,P2> {
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

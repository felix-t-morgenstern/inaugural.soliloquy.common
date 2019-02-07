package inaugural.soliloquy.common;

import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.ISetting;

public class Setting<T> implements ISetting<T> {
	private final String ID;
	private final T ARCHETYPE;
	private final IGenericParamsSet CONTROL_PARAMS;
	
	private String _name;
	private T _value;
	
	public Setting(String id, String name, T defaultValue, T archetype, IGenericParamsSet controlParams)
	{
		if (archetype == null)
		{
			throw new IllegalArgumentException("archetype was null");
		}
		ID = id;
		_name = name;
		_value = defaultValue;
		ARCHETYPE = archetype;
		CONTROL_PARAMS = controlParams;
	}
	
	@Override
	public String id() throws IllegalStateException {
		return ID;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public T getArchetype() {
		return ARCHETYPE;
	}

	@Override
	public T getValue() {
		return _value;
	}

	@Override
	public void setValue(T value) throws IllegalArgumentException {
		_value = value;
	}

	@Override
	public IGenericParamsSet controlParams() {
		return CONTROL_PARAMS;
	}

	@Override
	public String getInterfaceName() {
		return ISetting.class.getCanonicalName();
	}

	@Override
	public String getUnparameterizedInterfaceName() {
		throw new UnsupportedOperationException("Setting.getUnparameterizedInterfaceName is to never be called");
	}
}

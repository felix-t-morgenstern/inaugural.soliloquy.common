package inaugural.soliloquy.common;

import soliloquy.specs.common.entities.ISetting;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;

public class Setting<T> implements ISetting<T> {
	private final String ID;
	private final T ARCHETYPE;
	private final IGenericParamsSet CONTROL_PARAMS;
	
	private String _name;
	private T _value;
	
	@SuppressWarnings("ConstantConditions")
	public Setting(String id, String name, T defaultValue, T archetype, IGenericParamsSet controlParams) {
		if (id == null) {
			throw new IllegalArgumentException("Setting: id must be non-null");
		}
		if (id.equals("")) {
			throw new IllegalArgumentException("Setting: id must be non-empty");
		}
		ID = id;
		if (name == null) {
			throw new IllegalArgumentException("Setting: name must be non-null");
		}
		if (name.equals("")) {
			throw new IllegalArgumentException("Setting: name must be non-empty");
		}
		_name = name;
		_value = defaultValue;
		if (archetype == null) {
			throw new IllegalArgumentException("Setting: archetype must be non-null");
		}
		ARCHETYPE = archetype;
		if (controlParams == null) {
			throw new IllegalArgumentException("Setting: controlParams must be non-null");
		}
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
		throw new UnsupportedOperationException("Setting.getUnparameterizedInterfaceName: This is not to be called");
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof ISetting)) {
			return false;
		}
		return ((ISetting) o).id().equals(ID);
	}
}

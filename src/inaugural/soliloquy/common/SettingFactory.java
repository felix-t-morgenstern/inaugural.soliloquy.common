package inaugural.soliloquy.common;

import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.ISetting;
import soliloquy.common.specs.ISettingFactory;

public class SettingFactory extends CanCheckArchetypeAndArchetypesOfArchetype
		implements ISettingFactory {
	@Override
	public <T> ISetting<T> make(String id, String name, T defaultValue, IGenericParamsSet controlParams) {
		checkArchetypeAndArchetypesOfArchetype("make", defaultValue);
		return new Setting<>(id, name, defaultValue, defaultValue, controlParams);
	}

	@Override
	public String getInterfaceName() {
		return ISettingFactory.class.getCanonicalName();
	}

	@Override
	protected String className() {
		return "SettingFactory";
	}
}

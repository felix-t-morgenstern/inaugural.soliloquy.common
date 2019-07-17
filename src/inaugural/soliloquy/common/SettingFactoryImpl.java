package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.SettingFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Setting;

public class SettingFactoryImpl extends CanCheckArchetypeAndArchetypesOfArchetype
		implements SettingFactory {
	@Override
	public <T> Setting<T> make(String id, String name, T defaultValue, GenericParamsSet controlParams) {
		checkArchetypeAndArchetypesOfArchetype("make", defaultValue);
		return new SettingImpl<>(id, name, defaultValue, defaultValue, controlParams);
	}

	@Override
	public String getInterfaceName() {
		return SettingFactory.class.getCanonicalName();
	}

	@Override
	protected String className() {
		return "SettingFactory";
	}
}

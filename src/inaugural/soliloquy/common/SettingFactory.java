package inaugural.soliloquy.common;

import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.ISetting;
import soliloquy.common.specs.ISettingFactory;

public class SettingFactory implements ISettingFactory
{
	@Override
	public <T> ISetting<T> make(String id, String name, T defaultValue, IGenericParamsSet controlParams) {
		return new Setting<T>(id, name, defaultValue, defaultValue, controlParams);
	}
}

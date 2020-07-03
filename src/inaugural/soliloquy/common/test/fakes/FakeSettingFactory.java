package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.factories.SettingFactory;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.VariableCache;

public class FakeSettingFactory implements SettingFactory {
    @Override
    public <T> Setting<T> make(String s, String s1, T t, VariableCache variableCache) {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

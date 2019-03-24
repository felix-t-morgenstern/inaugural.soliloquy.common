package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.SettingFactory;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.ISetting;

public class SettingFactoryTests {
	private final String SETTING_ID = "SettingId";
	private final String SETTING_NAME = "SettingName";
	private final Integer SETTING_VALUE = 123;
	private final IGenericParamsSet SETTING_CONTROL_PARAMS = null;
	
	private SettingFactory _settingFactory;
	
    @BeforeEach
    protected void setUp() throws Exception {
    	_settingFactory = new SettingFactory();
    }
    
    @Test
    public void testMake() {
    	ISetting<Integer> setting = _settingFactory.make(SETTING_ID, SETTING_NAME, SETTING_VALUE, SETTING_CONTROL_PARAMS);
    	assertTrue(setting.id().equals(SETTING_ID));
    	assertTrue(setting.getName().equals(SETTING_NAME));
    	assertTrue(setting.getValue() == SETTING_VALUE);
    	assertTrue(setting.controlParams() == SETTING_CONTROL_PARAMS);
    }
}

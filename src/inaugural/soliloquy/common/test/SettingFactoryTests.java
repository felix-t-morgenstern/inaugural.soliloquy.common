package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.stubs.GenericParamsSetStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.SettingFactory;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.ISetting;
import soliloquy.common.specs.ISettingFactory;

import static org.junit.jupiter.api.Assertions.*;

class SettingFactoryTests {
	private SettingFactory _settingFactory;
	
    @BeforeEach
	void setUp() {
    	_settingFactory = new SettingFactory();
    }
    
    @Test
	void testMake() {
		IGenericParamsSet settingControlParams = new GenericParamsSetStub();
		Integer settingValue = 123;
		String settingName = "SettingName";
		String settingId = "SettingId";
		ISetting<Integer> setting = _settingFactory.make(settingId, settingName, settingValue, settingControlParams);
		assertEquals(setting.id(), settingId);
		assertEquals(setting.getName(), settingName);
		assertSame(setting.getValue(), settingValue);
		assertSame(setting.controlParams(), settingControlParams);
    }

    @Test
	void testGetInterfaceName() {
    	assertEquals(ISettingFactory.class.getCanonicalName(), _settingFactory.getInterfaceName());
	}
}

package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.stubs.CollectionStub;
import inaugural.soliloquy.common.test.stubs.GenericParamsSetStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.SettingFactoryImpl;
import soliloquy.specs.common.factories.SettingFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Setting;

import static org.junit.jupiter.api.Assertions.*;

class SettingFactoryTests {
	private SettingFactoryImpl _settingFactory;
	
    @BeforeEach
	void setUp() {
    	_settingFactory = new SettingFactoryImpl();
    }
    
    @Test
	void testMake() {
		GenericParamsSet settingControlParams = new GenericParamsSetStub();
		Integer settingValue = 123;
		String settingName = "SettingName";
		String settingId = "SettingId";
		Setting<Integer> setting = _settingFactory.make(settingId, settingName, settingValue, settingControlParams);
		assertEquals(setting.id(), settingId);
		assertEquals(setting.getName(), settingName);
		assertSame(setting.getValue(), settingValue);
		assertSame(setting.controlParams(), settingControlParams);
    }

    @Test
	void testGetInterfaceName() {
    	assertEquals(SettingFactory.class.getCanonicalName(), _settingFactory.getInterfaceName());
	}

	@SuppressWarnings("unchecked")
	@Test
	void testArchetypeWithNullArchetype() {
		Collection archetype = new CollectionStub(null);

		assertThrows(IllegalArgumentException.class, () -> _settingFactory.make("settingId",
				"settingName", archetype, new GenericParamsSetStub()));
	}
}

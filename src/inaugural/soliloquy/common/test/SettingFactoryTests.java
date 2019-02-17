package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.SettingFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.ISetting;

public class SettingFactoryTests extends TestCase {
	private final String SETTING_ID = "SettingId";
	private final String SETTING_NAME = "SettingName";
	private final Integer SETTING_VALUE = 123;
	private final IGenericParamsSet SETTING_CONTROL_PARAMS = null;
	
	private SettingFactory _settingFactory;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SettingFactoryTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SettingFactoryTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	_settingFactory = new SettingFactory();
    }
    
    public void testMake()
    {
    	ISetting<Integer> setting = _settingFactory.make(SETTING_ID, SETTING_NAME, SETTING_VALUE, SETTING_CONTROL_PARAMS);
    	assertTrue(setting.id().equals(SETTING_ID));
    	assertTrue(setting.getName().equals(SETTING_NAME));
    	assertTrue(setting.getValue() == SETTING_VALUE);
    	assertTrue(setting.controlParams() == SETTING_CONTROL_PARAMS);
    }
}

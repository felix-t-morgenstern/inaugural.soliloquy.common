package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.Setting;
import inaugural.soliloquy.common.test.Stubs.GenericParamsSetStub;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.ISetting;

public class SettingTests extends TestCase {
	private final String SETTING_ID = "SettingId";
	private final String SETTING_NAME_1 = "SettingName1";
	private final String SETTING_NAME_2 = "SettingName2";
	private final Integer SETTING_VALUE_1 = 123;
	private final Integer SETTING_VALUE_2 = 456;
	private final Integer SETTING_ARCHETYPE = 789;
	private final IGenericParamsSet _settingControlParams = new GenericParamsSetStub();
	
	private Setting<Integer> _setting;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SettingTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SettingTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	_setting = new Setting<Integer>(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1, SETTING_ARCHETYPE, _settingControlParams);
    }
    
    public void testId()
    {
    	assertTrue(_setting.id().equals(SETTING_ID));
    }
    
    public void testName()
    {
    	assertTrue(_setting.getName().equals(SETTING_NAME_1));
    	_setting.setName(SETTING_NAME_2);
    	assertTrue(_setting.getName().equals(SETTING_NAME_2));
    }
    
    public void testGetArchetype()
    {
    	assertTrue(_setting.getArchetype() == SETTING_ARCHETYPE);
    }
    
    public void testConstructorWithNullArchetype()
    {
    	try
    	{
    		@SuppressWarnings("unused")
			ISetting<Integer> setting = new Setting<Integer>(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1, null, _settingControlParams);
    		assertTrue(false);
    	}
    	catch(IllegalArgumentException e)
    	{
    		assertTrue(true);
    	}
    	catch(Exception e)
    	{
    		assertTrue(false);
    	}
    }
    
    public void testGetParameterizedClassName()
    {
    	String parameterizedClassName = _setting.getInterfaceName();
    	assertTrue("soliloquy.common.specs.ISetting".equals(parameterizedClassName));
    }
    
    public void testGetValue()
    {
    	assertTrue(_setting.getValue() == SETTING_VALUE_1);
    }
    
    public void testSetValue()
    {
    	_setting.setValue(SETTING_VALUE_2);
    	assertTrue(_setting.getValue() == SETTING_VALUE_2);
    }
    
    public void testControlParams()
    {
    	assertTrue(_setting.controlParams() == _settingControlParams);
    }
}

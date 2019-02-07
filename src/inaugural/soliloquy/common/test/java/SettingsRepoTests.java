package inaugural.soliloquy.common.test.java;

import inaugural.soliloquy.common.SettingsRepo;
import inaugural.soliloquy.common.SettingsRepo.SettingToProcess;
import inaugural.soliloquy.common.test.java.Stubs.CollectionFactoryStub;
import inaugural.soliloquy.common.test.java.Stubs.PairFactoryStub;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.IAction;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IEntityGroupItem;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;
import soliloquy.common.specs.IPersistentValueToRead;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.common.specs.IPersistentValueTypeHandler;
import soliloquy.common.specs.IPersistentValuesHandler;
import soliloquy.common.specs.ISetting;
import static org.mockito.Mockito.*;

public class SettingsRepoTests extends TestCase {
	private SettingsRepo _settingsRepo;
	
	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

	private final IPairFactory PAIR_FACTORY = new PairFactoryStub();
	
	private final IPersistentValuesHandler SETTINGS_REPO_PERSISTENT_VALUES_HANDLER = new PersistentValuesHandlerStub();
	
	private final String SETTING_1_ID = "Setting1Id";
	private final String SETTING_2_ID = "Setting2Id";
	private final String SETTING_3_ID = "Setting3Id";

	private final String SETTING_1_NAME = "Setting1Name";
	private final String SETTING_2_NAME = "Setting2Name";
	private final String SETTING_3_NAME = "Setting3Name";

	private final String SETTINGS_REPO_SUBGROUP_1_ID = "SettingsRepoSubgroup1Id";
	private final String SETTINGS_REPO_SUBGROUP_1_1_ID = "SettingsRepoSubgroup1-1Id";

	private final Integer SETTING_1_VALUE = 123;
	private final Double SETTING_2_VALUE = 4.56;
	private final String SETTING_3_VALUE = "Setting3Value";

	private final Integer SETTING_1_VALUE_ALT = 789;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final ISetting SETTING_ARCHETYPE = new SettingStub(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE, SETTING_1_VALUE, null);
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SettingsRepoTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SettingsRepoTests.class );
    }
    
	@Override
    protected void setUp() throws Exception
    {
    	_settingsRepo = new SettingsRepo(COLLECTION_FACTORY, PAIR_FACTORY, SETTINGS_REPO_PERSISTENT_VALUES_HANDLER, SETTING_ARCHETYPE);
    }
    
    @SuppressWarnings("rawtypes")
	public void testAddItemToTopLevel()
    {
    	ISetting setting = mock(ISetting.class);
    	when(setting.id()).thenReturn(SETTING_1_ID);
    	when(setting.getValue()).thenReturn(SETTING_1_VALUE);
    	
    	_settingsRepo.addEntity(setting, 0, null);
    	
    	assertTrue(_settingsRepo.getSetting(SETTING_1_ID).getValue().equals(SETTING_1_VALUE));
    }
    
    @SuppressWarnings("rawtypes")
	public void testAddTwoItemsToTopLevelWithSameId()
    {
    	ISetting setting1 = mock(ISetting.class);
    	when(setting1.id()).thenReturn(SETTING_1_ID);
    	when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

    	ISetting setting2 = mock(ISetting.class);
    	when(setting2.id()).thenReturn(SETTING_1_ID);
    	when(setting2.getValue()).thenReturn(SETTING_2_VALUE);
    	
    	_settingsRepo.addEntity(setting1, 0, null);
    	
    	try
    	{
    		_settingsRepo.addEntity(setting2, 1, null);
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
    
    @SuppressWarnings("rawtypes")
	public void testAddTwoItemsToTopLevelWithSameOrder()
    {
    	ISetting setting1 = mock(ISetting.class);
    	when(setting1.id()).thenReturn(SETTING_1_ID);
    	when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

    	ISetting setting2 = mock(ISetting.class);
    	when(setting2.id()).thenReturn(SETTING_2_ID);
    	when(setting2.getValue()).thenReturn(SETTING_2_VALUE);
    	
    	_settingsRepo.addEntity(setting1, 0, null);
    	
    	try
    	{
    		_settingsRepo.addEntity(setting2, 0, null);
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
    
    @SuppressWarnings("rawtypes")
	public void testAddItemToNonexistentSubGrouping()
    {
    	ISetting setting1 = mock(ISetting.class);
    	when(setting1.id()).thenReturn(SETTING_1_ID);
    	when(setting1.getValue()).thenReturn(SETTING_1_VALUE);
    	
    	try
    	{
    		_settingsRepo.addEntity(setting1, 0, SETTINGS_REPO_SUBGROUP_1_ID);
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
    
    @SuppressWarnings("rawtypes")
	public void testAddItemToExistentSubGrouping()
    {
    	_settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");
    	
    	ISetting setting1 = mock(ISetting.class);
    	when(setting1.id()).thenReturn(SETTING_1_ID);
    	when(setting1.getValue()).thenReturn(SETTING_1_VALUE);
    	
    	_settingsRepo.addEntity(setting1, 0, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	ISetting getResult = _settingsRepo.getSetting(SETTING_1_ID);
    	
    	assertTrue(getResult.getValue().equals(SETTING_1_VALUE));
    }
    
    public void testSetNonexistentSetting()
    {
    	try
    	{
    		_settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE);
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
    
    public void testGetSettingWithBlankId()
    {
    	try
    	{
    		_settingsRepo.getSetting("");
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
    
    public void testSetSettingWithBlankId()
    {
    	try
    	{
    		_settingsRepo.setSetting("", SETTING_1_VALUE);
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
    
    public void testAddSetAndGetSetting()
    {
    	ISetting<Integer> setting = new SettingStub<Integer>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE, SETTING_1_VALUE, null);
    	
    	_settingsRepo.addEntity(setting, 0, null);
    	
    	_settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE_ALT);
    	
    	assertTrue(_settingsRepo.getSetting(SETTING_1_ID).getValue().equals(SETTING_1_VALUE_ALT));
    }
    
    public void testAddSetAndGetSettingInSubgrouping()
    {
    	ISetting<Integer> setting = new SettingStub<Integer>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE, SETTING_1_VALUE, null);
    	
    	_settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	
    	_settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.addEntity(setting, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);
    	
    	_settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE_ALT);
    	
    	assertTrue(_settingsRepo.getSetting(SETTING_1_ID).getValue().equals(SETTING_1_VALUE_ALT));
    }
    
    public void testGetItemByOrder()
    {
    	ISetting<Integer> setting = new SettingStub<Integer>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE, SETTING_1_VALUE, null);
    	_settingsRepo.addEntity(setting, 123, null);
    	assertTrue(_settingsRepo.getItemByOrder(123).entity().getValue().equals(SETTING_1_VALUE));
    }
    
    public void testGetNonexistentItemByOrder()
    {
    	try
    	{
        	_settingsRepo.getItemByOrder(123);
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
    
    @SuppressWarnings("rawtypes")
	public void testGetAllGrouped()
    {
    	ISetting<Integer> setting1 = new SettingStub<Integer>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE, SETTING_1_VALUE, null);
    	ISetting<Double> setting2 = new SettingStub<Double>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE, SETTING_2_VALUE, null);
    	ISetting<String> setting3 = new SettingStub<String>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE, SETTING_3_VALUE, null);

    	_settingsRepo.addEntity(setting1, 0, null);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	
    	_settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);
    	
    	ICollection<IEntityGroupItem<ISetting>> topLevelGrouped = _settingsRepo.getAllGrouped();
    	
    	assertTrue(topLevelGrouped.size() == 2);
    	assertTrue(topLevelGrouped.get(0).entity().getValue().equals(SETTING_1_VALUE));
    	
    	ICollection<IEntityGroupItem<ISetting>> midLevelGrouped = topLevelGrouped.get(1).group().getAllGrouped();
    	
    	assertTrue(midLevelGrouped.size() == 2);
    	assertTrue(midLevelGrouped.get(0).entity().getValue().equals(SETTING_2_VALUE));
    	
    	ICollection<IEntityGroupItem<ISetting>> bottomLevelGrouped = midLevelGrouped.get(1).group().getAllGrouped();
    	
    	assertTrue(bottomLevelGrouped.size() == 1);
    	assertTrue(bottomLevelGrouped.get(0).entity().getValue().equals(SETTING_3_VALUE));
    }
    
    @SuppressWarnings("rawtypes")
	public void testGetAllUngrouped()
    {
    	ISetting<Integer> setting1 = new SettingStub<Integer>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE, SETTING_1_VALUE, null);
    	ISetting<Double> setting2 = new SettingStub<Double>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE, SETTING_2_VALUE, null);
    	ISetting<String> setting3 = new SettingStub<String>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE, SETTING_3_VALUE, null);

    	_settingsRepo.addEntity(setting1, 0, null);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	
    	_settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);
    	
    	ICollection<ISetting> allSettingsUngrouped = _settingsRepo.getAllUngrouped();
    	
    	assertTrue(allSettingsUngrouped.size() == 3);

    	boolean setting1Used = false;
    	boolean setting2Used = false;
    	boolean setting3Used = false;
    	for(int i = 0; i < allSettingsUngrouped.size(); i++)
    	{
    		ISetting setting = allSettingsUngrouped.get(i);
    		if (setting.id().equals(setting1.id()) && setting.getName().equals(setting1.getName()) && setting.getValue().equals(setting1.getValue()))
    		{
    			setting1Used = true;
    		}
    		if (setting.id().equals(setting2.id()) && setting.getName().equals(setting2.getName()) && setting.getValue().equals(setting2.getValue()))
    		{
    			setting2Used = true;
    		}
    		if (setting.id().equals(setting3.id()) && setting.getName().equals(setting3.getName()) && setting.getValue().equals(setting3.getValue()))
    		{
    			setting3Used = true;
    		}
    	}
    	assertTrue(setting1Used);
    	assertTrue(setting2Used);
    	assertTrue(setting3Used);
    }

    public void testRemoveItem()
    {
    	ISetting<Integer> setting1 = new SettingStub<Integer>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE, SETTING_1_VALUE, null);
    	ISetting<Double> setting2 = new SettingStub<Double>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE, SETTING_2_VALUE, null);
    	ISetting<String> setting3 = new SettingStub<String>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE, SETTING_3_VALUE, null);

    	_settingsRepo.addEntity(setting1, 0, null);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	
    	_settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);
    	
    	assertTrue(_settingsRepo.getSetting(SETTING_2_ID) != null);
    	
    	_settingsRepo.removeItem(SETTING_2_ID);
    	
    	assertTrue(_settingsRepo.getSetting(SETTING_2_ID) == null);
    }
    
    public void testRemoveItemWithNullOrBlankId()
    {
    	try
    	{
        	_settingsRepo.removeItem(null);
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
    	
    	try
    	{
        	_settingsRepo.removeItem("");
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
    
    public void testGetGroupingIdAndOrder()
    {
    	ISetting<Integer> setting1 = new SettingStub<Integer>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE, SETTING_1_VALUE, null);
    	ISetting<Double> setting2 = new SettingStub<Double>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE, SETTING_2_VALUE, null);
    	ISetting<String> setting3 = new SettingStub<String>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE, SETTING_3_VALUE, null);

    	_settingsRepo.addEntity(setting1, 123, null);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	
    	_settingsRepo.addEntity(setting2, 456, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.addEntity(setting3, 789, SETTINGS_REPO_SUBGROUP_1_1_ID);
    	
    	IPair<String,Integer> setting1GroupingIdAndOrder = _settingsRepo.getGroupingIdAndOrder(SETTING_1_ID);
    	assertTrue(setting1GroupingIdAndOrder.getItem1() == null || setting1GroupingIdAndOrder.getItem1().equals(""));
    	assertTrue(setting1GroupingIdAndOrder.getItem2() == 123);

    	IPair<String,Integer> setting2GroupingIdAndOrder = _settingsRepo.getGroupingIdAndOrder(SETTING_2_ID);
    	assertTrue(setting2GroupingIdAndOrder.getItem1().equals(SETTINGS_REPO_SUBGROUP_1_ID));
    	assertTrue(setting2GroupingIdAndOrder.getItem2() == 456);

    	IPair<String,Integer> setting3GroupingIdAndOrder = _settingsRepo.getGroupingIdAndOrder(SETTING_3_ID);
    	assertTrue(setting3GroupingIdAndOrder.getItem1().equals(SETTINGS_REPO_SUBGROUP_1_1_ID));
    	assertTrue(setting3GroupingIdAndOrder.getItem2() == 789);
    	
    	try
    	{
        	_settingsRepo.getGroupingIdAndOrder("ThisIsNotAnId");
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
    	
    	try
    	{
        	_settingsRepo.getGroupingIdAndOrder("");
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
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void testRead()
    {
    	_settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	_settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);

    	ISetting setting1 = new SettingStub(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE, SETTING_1_VALUE, null);
    	ISetting setting2 = new SettingStub(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE, SETTING_2_VALUE, null);
    	ISetting setting3 = new SettingStub(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE, SETTING_3_VALUE, null);

    	_settingsRepo.addEntity(setting1, 1, "");
    	_settingsRepo.addEntity(setting2, 2, SETTINGS_REPO_SUBGROUP_1_ID);
    	_settingsRepo.addEntity(setting3, 3, SETTINGS_REPO_SUBGROUP_1_1_ID);
    	
    	String adHocFormattedSettingsString = SETTING_1_ID+","+SETTING_1_NAME+","+""+","+1+","+"Hello1"+";"
    			+SETTING_2_ID+","+SETTING_2_NAME+","+SETTINGS_REPO_SUBGROUP_1_ID+","+2+","+"Hello2"+";"
    			+SETTING_3_ID+","+SETTING_3_NAME+","+SETTINGS_REPO_SUBGROUP_1_1_ID+","+3+","+"Hello3"+";";
    	_settingsRepo.read(adHocFormattedSettingsString, true);
    	
    	IPair<String,Integer> setting1GroupingIdAndOrder = _settingsRepo.getGroupingIdAndOrder(SETTING_1_ID);
    	assertTrue(setting1GroupingIdAndOrder.getItem1() == null || setting1GroupingIdAndOrder.getItem1().equals(""));
    	assertTrue(setting1GroupingIdAndOrder.getItem2() == 1);
    	assertTrue(_settingsRepo.getSetting(SETTING_1_ID).getValue().equals("Hello1"));
    }
    
    @SuppressWarnings("rawtypes")
	public void testWrite()
    {
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);
    	
		ISetting setting1 = mock(ISetting.class);
		when(setting1.id()).thenReturn(SETTING_1_ID);
		when(setting1.getName()).thenReturn(SETTING_1_NAME);
		when(setting1.getValue()).thenReturn("Hello1");
    	
		ISetting setting2 = mock(ISetting.class);
		when(setting2.id()).thenReturn(SETTING_2_ID);
		when(setting2.getName()).thenReturn(SETTING_2_NAME);
		when(setting2.getValue()).thenReturn("Hello2");
    	
		ISetting setting3 = mock(ISetting.class);
		when(setting3.id()).thenReturn(SETTING_3_ID);
		when(setting3.getName()).thenReturn(SETTING_3_NAME);
		when(setting3.getValue()).thenReturn("Hello3");

		_settingsRepo.addEntity(setting1, 0, null);
		_settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);
		_settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);
		
		String expectedValue = SETTING_1_ID+",Hello1"+";"
    			+SETTING_2_ID+",Hello2"+";"
    			+SETTING_3_ID+",Hello3"+";";
		
		String settingsWrittenOut = _settingsRepo.write();
		
		assertTrue(settingsWrittenOut.equals(expectedValue));
    }
    
    private class SettingStub<T> implements ISetting<T>
    {
    	private final String ID;
    	
    	private String _name;
    	private T _value;
    	
    	public SettingStub(String id, String name, T defaultValue, T archetype, IGenericParamsSet controlParams)
    	{
    		ID = id;
    		_name = name;
    		_value = defaultValue;
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
			return null;
		}

		@Override
		public String getInterfaceName() {
			return null;
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
			return null;
		}

		@Override
		public String getUnparameterizedInterfaceName() {
			// Stub method, unimplemented
			throw new UnsupportedOperationException();
		}
    	
    }
    
    private class PersistentValuesHandlerStub implements IPersistentValuesHandler
    {
		@Override
		public void addPersistentValueTypeHandler(IPersistentValueTypeHandler<?> persistentValueTypeHandler) throws IllegalArgumentException {
			// Stub class; no implementation needed
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removePersistentValueTypeHandler(String persistentValueType) {
			// Stub class; no implementation needed
			throw new UnsupportedOperationException();
		}

		@Override
		public <T> IPersistentValueTypeHandler<T> getPersistentValueTypeHandler(String persistentValueType)
				throws UnsupportedOperationException {
			// Stub class; no implementation needed
			throw new UnsupportedOperationException();
		}

		@Override
		public ICollection<String> persistentValueTypesHandled() {
			// Stub class; no implementation needed
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void readValues(String valuesString, IAction<IPair<IPersistentValueToWrite<?>, Boolean>> valueProcessing,
				boolean overridePreviousData) {
			String[] settingsStrings = valuesString.split(";", -1);
			for(String settingString : settingsStrings)
			{
				if (settingString.equals(""))
				{
					continue;
				}
				String[] settingFields = settingString.split(",", -1);
				if (settingFields.length != 5)
				{
					throw new IllegalArgumentException("Invalid number of Setting fields (" + settingString + ")");
				}
				
				String settingId = settingFields[0];
				String settingName = settingFields[1];
				String settingValue = settingFields[4];
				
				ISetting setting = mock(ISetting.class);
				when(setting.id()).thenReturn(settingId);
				when(setting.getName()).thenReturn(settingName);
				when(setting.getValue()).thenReturn(settingValue);
				
				SettingToProcess settingToProcess = _settingsRepo.new SettingToProcess(setting.id(), setting.getValue());
				
				valueProcessing.run(PAIR_FACTORY.make(settingToProcess,true));
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public String writeValues(ICollection<IPersistentValueToWrite<?>> persistentValuesToProcess) {
			String writtenValues = "";
			for (int i = 0; i < persistentValuesToProcess.size(); i++)
			{
				IPersistentValueToWrite settingToProcess = persistentValuesToProcess.get(i);
				writtenValues += settingToProcess.name() + "," + settingToProcess.value() + ";";
			}
			return writtenValues;
		}

		@Override
		public IPersistentValueToRead makePersistentValueToRead(String typeName, String name, String value) {
			// Stub class; no implementation needed
			throw new UnsupportedOperationException();
		}

		@Override
		public <T> IPersistentValueToWrite<T> makePersistentValueToWrite(String name, T value) {
			// Stub class; no implementation needed
			throw new UnsupportedOperationException();
		}

		@Override
		public String getInterfaceName() {
			// Stub method, unimplemented
			throw new UnsupportedOperationException();
		}
    	
    }
}

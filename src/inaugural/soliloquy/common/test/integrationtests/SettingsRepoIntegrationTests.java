package inaugural.soliloquy.common.test.integrationtests;

import inaugural.soliloquy.common.persistentvaluetypehandlers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.CollectionFactory;
import inaugural.soliloquy.common.GenericParamsSetFactory;
import inaugural.soliloquy.common.MapFactory;
import inaugural.soliloquy.common.PairFactory;
import inaugural.soliloquy.common.PersistentValuesHandler;
import inaugural.soliloquy.common.SettingFactory;
import inaugural.soliloquy.common.SettingsRepo;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IGenericParamsSetFactory;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;
import soliloquy.common.specs.IPersistentValueTypeHandler;
import soliloquy.common.specs.IPersistentValuesHandler;
import soliloquy.common.specs.ISetting;
import soliloquy.common.specs.ISettingFactory;
import soliloquy.common.specs.ISettingsRepo;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SettingsRepoIntegrationTests {
	private ISettingsRepo _settingsRepo;

	private final String SETTING_1_ID = "Setting1Id";
	private final String SETTING_2_ID = "Setting2Id";
	private final String SETTING_3_ID = "Setting3Id";
	private final String SETTING_4_ID = "Setting4Id";
	private final String SETTING_5_ID = "Setting5Id";

	private final Boolean SETTING_1_VALUE = true;
	private final Integer SETTING_2_VALUE = 456;
	private final Integer SETTING_3_VALUE_1 = 123;
	private final Integer SETTING_3_VALUE_2 = 456;
	private final Integer SETTING_3_VALUE_3 = 789;
	private final String SETTING_4_VALUE = "Setting4Value";
	private final String SETTING_5_VALUE_1 = "Value1";
	private final String SETTING_5_VALUE_2 = "Value2";
	private final String SETTING_5_VALUE_3 = "Value3";

	private final String SUBGROUPING_1_ID = "Subgrouping1";

	private final String SUBGROUPING_1_1_ID = "Subgrouping1-1";

	private final String SUBGROUPING_2_ID = "Subgrouping2";

	private final Integer SETTING_1_ORDER = 14;
	private final Integer SETTING_2_ORDER = 17;
	private final Integer SETTING_3_ORDER = 20;
	private final Integer SETTING_4_ORDER = 23;
	private final Integer SETTING_5_ORDER = 26;

	private final String SETTING_1_GROUP_ID = null;
	private final String SETTING_2_GROUP_ID = SUBGROUPING_1_ID;
	private final String SETTING_3_GROUP_ID = SUBGROUPING_1_1_ID;
	private final String SETTING_4_GROUP_ID = SUBGROUPING_2_ID;
	private final String SETTING_5_GROUP_ID = SUBGROUPING_1_ID;

	@BeforeEach
	void setUp() {
		ICollectionFactory _collectionFactory = new CollectionFactory();
		IPairFactory _pairFactory = new PairFactory();
		ISettingFactory _settingFactory = new SettingFactory();

		IMapFactory _mapFactory = new MapFactory(_pairFactory);

		IPersistentValuesHandler _persistentValuesHandler = new PersistentValuesHandler();

		IGenericParamsSetFactory _genericParamsSetFactory = new GenericParamsSetFactory(_persistentValuesHandler, _mapFactory);
    	
    	IPersistentValueTypeHandler<Boolean> persistentBooleanHandler = new PersistentBooleanHandler();
    	IPersistentValueTypeHandler<Integer> persistentIntegerHandler = new PersistentIntegerHandler();
    	IPersistentValueTypeHandler<String> persistentStringHandler = new PersistentStringHandler();
		IPersistentValueTypeHandler<ICollection> persistentCollectionHandler = new PersistentCollectionHandler(_persistentValuesHandler, _collectionFactory);
    	
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentBooleanHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentIntegerHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentStringHandler);
		_persistentValuesHandler.registerPersistentCollectionHandler(persistentCollectionHandler);

		ISetting<String> settingArchetype = _settingFactory.make("archetypeId", "archetypeName", "archetypeValue", _genericParamsSetFactory.make());
		
		_settingsRepo = new SettingsRepo(_collectionFactory, _pairFactory, _persistentValuesHandler, settingArchetype);
		
		IGenericParamsSet setting1ControlParams = _genericParamsSetFactory.make();
		String SETTING_1_CONTROL_PARAM_1_VALUE = "Setting 1 Friendly Name";
		String SETTING_1_CONTROL_PARAM_1_NAME = "friendlyName";
		setting1ControlParams.addParam(SETTING_1_CONTROL_PARAM_1_NAME, SETTING_1_CONTROL_PARAM_1_VALUE);
		Boolean SETTING_1_CONTROL_PARAM_2_VALUE = true;
		String SETTING_1_CONTROL_PARAM_2_NAME = "isCheckbox";
		setting1ControlParams.addParam(SETTING_1_CONTROL_PARAM_2_NAME, SETTING_1_CONTROL_PARAM_2_VALUE);
		String SETTING_1_NAME = "Setting1Name";
		ISetting _setting1 = _settingFactory.make(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE, setting1ControlParams);
		
		IGenericParamsSet setting2ControlParams = _genericParamsSetFactory.make();
		String SETTING_2_CONTROL_PARAM_1_VALUE = "Setting 2 Friendly Name";
		String SETTING_2_CONTROL_PARAM_1_NAME = "friendlyName";
		setting2ControlParams.addParam(SETTING_2_CONTROL_PARAM_1_NAME, SETTING_2_CONTROL_PARAM_1_VALUE);
		String SETTING_2_CONTROL_PARAM_2_VALUE = "slider";
		String SETTING_2_CONTROL_PARAM_2_NAME = "controlType";
		setting2ControlParams.addParam(SETTING_2_CONTROL_PARAM_2_NAME, SETTING_2_CONTROL_PARAM_2_VALUE);
		Integer SETTING_2_CONTROL_PARAM_3_VALUE = 1024;
		String SETTING_2_CONTROL_PARAM_3_NAME = "maxValue";
		setting2ControlParams.addParam(SETTING_2_CONTROL_PARAM_3_NAME, SETTING_2_CONTROL_PARAM_3_VALUE);
		String SETTING_2_NAME = "Setting2Name";
		ISetting _setting2 = _settingFactory.make(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE, setting2ControlParams);

		ICollection<Integer> _setting3Value = _collectionFactory.make(0);
		_setting3Value.add(SETTING_3_VALUE_1);
		_setting3Value.add(SETTING_3_VALUE_2);
		_setting3Value.add(SETTING_3_VALUE_3);
		IGenericParamsSet setting3ControlParams = _genericParamsSetFactory.make();
		String SETTING_3_CONTROL_PARAM_1_VALUE = "Setting 3 Friendly Name";
		String SETTING_3_CONTROL_PARAM_1_NAME = "friendlyName";
		setting3ControlParams.addParam(SETTING_3_CONTROL_PARAM_1_NAME, SETTING_3_CONTROL_PARAM_1_VALUE);
		String SETTING_3_NAME = "Setting3Name";
		ISetting _setting3 = _settingFactory.make(SETTING_3_ID, SETTING_3_NAME, _setting3Value, setting3ControlParams);
		
		IGenericParamsSet setting4ControlParams = _genericParamsSetFactory.make();
		String SETTING_4_CONTROL_PARAM_1_VALUE = "Setting 4 Friendly Name";
		String SETTING_4_CONTROL_PARAM_1_NAME = "friendlyName";
		setting4ControlParams.addParam(SETTING_4_CONTROL_PARAM_1_NAME, SETTING_4_CONTROL_PARAM_1_VALUE);
		String SETTING_4_NAME = "Setting4Name";
		ISetting _setting4 = _settingFactory.make(SETTING_4_ID, SETTING_4_NAME, SETTING_4_VALUE, setting4ControlParams);

		ICollection<String> _setting5Value = _collectionFactory.make("");
		_setting5Value.add(SETTING_5_VALUE_1);
		_setting5Value.add(SETTING_5_VALUE_2);
		_setting5Value.add(SETTING_5_VALUE_3);
		IGenericParamsSet setting5ControlParams = _genericParamsSetFactory.make();
		String SETTING_5_CONTROL_PARAM_1_VALUE = "Setting 5 Friendly Name";
		String SETTING_5_CONTROL_PARAM_1_NAME = "friendlyName";
		setting5ControlParams.addParam(SETTING_5_CONTROL_PARAM_1_NAME, SETTING_5_CONTROL_PARAM_1_VALUE);
		String SETTING_5_NAME = "Setting5Name";
		ISetting _setting5 = _settingFactory.make(SETTING_5_ID, SETTING_5_NAME, _setting5Value, setting5ControlParams);

		int SUBGROUPING_1_ORDER = 5;
		_settingsRepo.newSubgrouping(SUBGROUPING_1_ORDER, SUBGROUPING_1_ID, null);
		int SUBGROUPING_1_1_ORDER = 8;
		_settingsRepo.newSubgrouping(SUBGROUPING_1_1_ORDER, SUBGROUPING_1_1_ID, SUBGROUPING_1_ID);
		int SUBGROUPING_2_ORDER = 11;
		_settingsRepo.newSubgrouping(SUBGROUPING_2_ORDER, SUBGROUPING_2_ID, SUBGROUPING_1_ID);

		_settingsRepo.addEntity(_setting1, SETTING_1_ORDER, SETTING_1_GROUP_ID);
		_settingsRepo.addEntity(_setting2, SETTING_2_ORDER, SETTING_2_GROUP_ID);
		_settingsRepo.addEntity(_setting3, SETTING_3_ORDER, SETTING_3_GROUP_ID);
		_settingsRepo.addEntity(_setting4, SETTING_4_ORDER, SETTING_4_GROUP_ID);
		_settingsRepo.addEntity(_setting5, SETTING_5_ORDER, SETTING_5_GROUP_ID);
    }

	@Test
	void testWrite() {
    	String expectedValue = "[{\"typeName\":\"java.lang.Integer\",\"name\":\"Setting2Id\",\"value\":\"456\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"Setting3Id\",\"value\":\"java.lang.Integer\\u001d0\\u001d123\\u001e456\\u001e789\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"Setting5Id\",\"value\":\"java.lang.String\\u001d\\u001dValue1\\u001eValue2\\u001eValue3\"},{\"typeName\":\"java.lang.String\",\"name\":\"Setting4Id\",\"value\":\"Setting4Value\"},{\"typeName\":\"java.lang.Boolean\",\"name\":\"Setting1Id\",\"value\":\"true\"}]";
    	
    	String writtenSettingsRepo = _settingsRepo.write();

		assertEquals(expectedValue, writtenSettingsRepo);
    }

	@Test
    @SuppressWarnings("unchecked")
	void testRead() {
    	String settingsToRead = "[{\"typeName\":\"java.lang.Integer\",\"name\":\"Setting2Id\",\"value\":\"456\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"Setting3Id\",\"value\":\"java.lang.Integer\\u001d0\\u001d123\\u001e456\\u001e789\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"Setting5Id\",\"value\":\"java.lang.String\\u001d\\u001dValue1\\u001eValue2\\u001eValue3\"},{\"typeName\":\"java.lang.String\",\"name\":\"Setting4Id\",\"value\":\"Setting4Value\"},{\"typeName\":\"java.lang.Boolean\",\"name\":\"Setting1Id\",\"value\":\"true\"}]";
    	
    	_settingsRepo.read(settingsToRead, true);

		assertEquals(5, _settingsRepo.getAllUngrouped().size());
    	
    	IPair<String,Integer> setting1GroupingAndOrder = _settingsRepo.getGroupingIdAndOrder(SETTING_1_ID);
    	assertTrue(groupNamesEqual(setting1GroupingAndOrder.getItem1(), SETTING_1_GROUP_ID));
		assertSame(setting1GroupingAndOrder.getItem2(), SETTING_1_ORDER);
		assertEquals(_settingsRepo.getSetting(SETTING_1_ID).getValue(), SETTING_1_VALUE);
    	
    	IPair<String,Integer> setting2GroupingAndOrder = _settingsRepo.getGroupingIdAndOrder(SETTING_2_ID);
    	assertTrue(groupNamesEqual(setting2GroupingAndOrder.getItem1(), SETTING_2_GROUP_ID));
		assertSame(setting2GroupingAndOrder.getItem2(), SETTING_2_ORDER);
		assertEquals(_settingsRepo.getSetting(SETTING_2_ID).getValue(), SETTING_2_VALUE);
    	
    	IPair<String,Integer> setting3GroupingAndOrder = _settingsRepo.getGroupingIdAndOrder(SETTING_3_ID);
    	assertTrue(groupNamesEqual(setting3GroupingAndOrder.getItem1(), SETTING_3_GROUP_ID));
		assertSame(setting3GroupingAndOrder.getItem2(), SETTING_3_ORDER);
    	ICollection<Integer> setting3Value = (ICollection<Integer>) _settingsRepo.getSetting(SETTING_3_ID).getValue();
		assertEquals(3, setting3Value.size());
    	assertTrue(setting3Value.contains(SETTING_3_VALUE_1));
    	assertTrue(setting3Value.contains(SETTING_3_VALUE_2));
    	assertTrue(setting3Value.contains(SETTING_3_VALUE_3));
    	
    	IPair<String,Integer> setting4GroupingAndOrder = _settingsRepo.getGroupingIdAndOrder(SETTING_4_ID);
    	assertTrue(groupNamesEqual(setting4GroupingAndOrder.getItem1(), SETTING_4_GROUP_ID));
		assertSame(setting4GroupingAndOrder.getItem2(), SETTING_4_ORDER);
		assertEquals(_settingsRepo.getSetting(SETTING_4_ID).getValue(), SETTING_4_VALUE);
    	
    	IPair<String,Integer> setting5GroupingAndOrder = _settingsRepo.getGroupingIdAndOrder(SETTING_5_ID);
    	assertTrue(groupNamesEqual(setting5GroupingAndOrder.getItem1(), SETTING_5_GROUP_ID));
		assertSame(setting5GroupingAndOrder.getItem2(), SETTING_5_ORDER);
    	ICollection<String> setting5Value = (ICollection<String>) _settingsRepo.getSetting(SETTING_5_ID).getValue();
		assertEquals(3, setting5Value.size());
    	assertTrue(setting5Value.contains(SETTING_5_VALUE_1));
    	assertTrue(setting5Value.contains(SETTING_5_VALUE_2));
    	assertTrue(setting5Value.contains(SETTING_5_VALUE_3));
    }
    
    private boolean groupNamesEqual(String groupName1, String groupName2) {
    	if ((groupName1 == null || groupName1.equals("")) && (groupName2 == null || groupName2.equals(""))) {
    		return true;
    	} else {
    		return Objects.equals(groupName1, groupName2);
    	}
    }
}

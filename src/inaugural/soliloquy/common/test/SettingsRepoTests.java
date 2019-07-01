package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.SettingsRepo;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.common.test.stubs.PairFactoryStub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.factories.IPairFactory;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.shared.IEntityGroupItem;

class SettingsRepoTests {
	private SettingsRepo _settingsRepo;
	
	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

	private final IPairFactory PAIR_FACTORY = new PairFactoryStub();
	
	private final IPersistentValuesHandler SETTINGS_REPO_PERSISTENT_VALUES_HANDLER =
			new PersistentValuesHandlerStub();
	
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
	private final ISetting SETTING_ARCHETYPE = new SettingStub(SETTING_1_ID, SETTING_1_NAME,
			SETTING_1_VALUE);
	
    @BeforeEach
	void setUp()  {
    	_settingsRepo = new SettingsRepo(COLLECTION_FACTORY, PAIR_FACTORY,
				SETTINGS_REPO_PERSISTENT_VALUES_HANDLER, SETTING_ARCHETYPE);
    }

    @Test
	void testId() {
		_settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");

		ISettingsRepo settingsRepo = _settingsRepo.getSubgrouping(SETTINGS_REPO_SUBGROUP_1_ID);

		assertEquals(SETTINGS_REPO_SUBGROUP_1_ID, settingsRepo.id());
	}

	@Test
	void testEquals() {
    	ISettingsRepo settingsRepo2 = new SettingsRepo(COLLECTION_FACTORY, PAIR_FACTORY,
				SETTINGS_REPO_PERSISTENT_VALUES_HANDLER, SETTING_ARCHETYPE);
		_settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");
		settingsRepo2.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");

		ISettingsRepo settingsRepoSubgrouping =
				_settingsRepo.getSubgrouping(SETTINGS_REPO_SUBGROUP_1_ID);
		ISettingsRepo settingsRepo2Subgrouping =
				settingsRepo2.getSubgrouping(SETTINGS_REPO_SUBGROUP_1_ID);

		assertEquals(settingsRepoSubgrouping, settingsRepo2Subgrouping);
	}

    @Test
    @SuppressWarnings("rawtypes")
	void testAddItemToTopLevel() {
    	ISetting setting = mock(ISetting.class);
    	when(setting.id()).thenReturn(SETTING_1_ID);
    	when(setting.getValue()).thenReturn(SETTING_1_VALUE);
    	
    	_settingsRepo.addEntity(setting, 0, null);

		assertEquals(_settingsRepo.getSetting(SETTING_1_ID).getValue(), SETTING_1_VALUE);
    }

    @Test
    @SuppressWarnings("rawtypes")
	void testAddTwoItemsToTopLevelWithSameId() {
    	ISetting setting1 = mock(ISetting.class);
    	when(setting1.id()).thenReturn(SETTING_1_ID);
    	when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

    	ISetting setting2 = mock(ISetting.class);
    	when(setting2.id()).thenReturn(SETTING_1_ID);
    	when(setting2.getValue()).thenReturn(SETTING_2_VALUE);
    	
    	_settingsRepo.addEntity(setting1, 0, null);

    	assertThrows(IllegalArgumentException.class,
				() -> _settingsRepo.addEntity(setting2, 1, null));
    }

    @Test
    @SuppressWarnings("rawtypes")
	void testAddTwoItemsToTopLevelWithSameOrder() {
    	ISetting setting1 = mock(ISetting.class);
    	when(setting1.id()).thenReturn(SETTING_1_ID);
    	when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

    	ISetting setting2 = mock(ISetting.class);
    	when(setting2.id()).thenReturn(SETTING_2_ID);
    	when(setting2.getValue()).thenReturn(SETTING_2_VALUE);
    	
    	_settingsRepo.addEntity(setting1, 0, null);

		assertThrows(IllegalArgumentException.class,
				() -> _settingsRepo.addEntity(setting2, 0, null));
    }

    @Test
    @SuppressWarnings("rawtypes")
	void testAddItemToNonexistentSubGrouping() {
    	ISetting setting1 = mock(ISetting.class);
    	when(setting1.id()).thenReturn(SETTING_1_ID);
    	when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

		assertThrows(IllegalArgumentException.class,
				() -> _settingsRepo.addEntity(setting1, 0, SETTINGS_REPO_SUBGROUP_1_ID));
    }

    @Test
    @SuppressWarnings("rawtypes")
	void testAddItemToExistentSubGrouping() {
    	_settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");
    	
    	ISetting setting1 = mock(ISetting.class);
    	when(setting1.id()).thenReturn(SETTING_1_ID);
    	when(setting1.getValue()).thenReturn(SETTING_1_VALUE);
    	
    	_settingsRepo.addEntity(setting1, 0, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	ISetting getResult = _settingsRepo.getSetting(SETTING_1_ID);

		assertEquals(getResult.getValue(), SETTING_1_VALUE);
    }

    @Test
	void testSetNonexistentSetting() {
		assertThrows(IllegalArgumentException.class,
				() -> _settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE));
    }

    @Test
	void testGetSettingWithBlankId() {
		assertThrows(IllegalArgumentException.class, () -> _settingsRepo.getSetting(""));
    }

    @Test
	void testSetSettingWithBlankId() {
		assertThrows(IllegalArgumentException.class,
				() -> _settingsRepo.setSetting("", SETTING_1_VALUE));
    }

    @Test
	void testAddSetAndGetSetting() {
    	ISetting<Integer> setting =
				new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
    	
    	_settingsRepo.addEntity(setting, 0, null);
    	
    	_settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE_ALT);

		assertEquals(_settingsRepo.getSetting(SETTING_1_ID).getValue(), SETTING_1_VALUE_ALT);
    }

    @Test
	void testAddSetAndGetSettingInSubgrouping() {
    	ISetting<Integer> setting =
				new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
    	
    	_settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	
    	_settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.addEntity(setting, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);
    	
    	_settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE_ALT);

		assertEquals(_settingsRepo.getSetting(SETTING_1_ID).getValue(), SETTING_1_VALUE_ALT);
    }

    @Test
	void testGetItemByOrder() {
    	ISetting<Integer> setting =
				new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE
				);
    	_settingsRepo.addEntity(setting, 123, null);
		assertEquals(_settingsRepo.getItemByOrder(123).entity().getValue(), SETTING_1_VALUE);
    }

    @Test
	void testGetNonexistentItemByOrder() {
		assertThrows(IllegalArgumentException.class, () -> _settingsRepo.getItemByOrder(123));
    }

    @Test
    @SuppressWarnings("rawtypes")
	void testGetAllGrouped() {
    	ISetting<Integer> setting1 =
				new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE
				);
    	ISetting<Double> setting2 =
				new SettingStub<>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE
				);
    	ISetting<String> setting3 =
				new SettingStub<>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE
				);

    	_settingsRepo.addEntity(setting1, 0, null);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	
    	_settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);
    	
    	ICollection<IEntityGroupItem<ISetting>> topLevelGrouped = _settingsRepo.getAllGrouped();

		assertEquals(2, topLevelGrouped.size());
		assertEquals(topLevelGrouped.get(0).entity().getValue(), SETTING_1_VALUE);
    	
    	ICollection<IEntityGroupItem<ISetting>> midLevelGrouped = topLevelGrouped.get(1).group().getAllGrouped();

		assertEquals(2, midLevelGrouped.size());
		assertEquals(midLevelGrouped.get(0).entity().getValue(), SETTING_2_VALUE);
    	
    	ICollection<IEntityGroupItem<ISetting>> bottomLevelGrouped = midLevelGrouped.get(1).group().getAllGrouped();

		assertEquals(1, bottomLevelGrouped.size());
		assertEquals(bottomLevelGrouped.get(0).entity().getValue(), SETTING_3_VALUE);
    }

    @Test
    @SuppressWarnings("rawtypes")
	void testGetAllUngrouped() {
    	ISetting<Integer> setting1 =
				new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
    	ISetting<Double> setting2 =
				new SettingStub<>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE);
    	ISetting<String> setting3 =
				new SettingStub<>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE);

    	_settingsRepo.addEntity(setting1, 0, null);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	
    	_settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);
    	
    	ICollection<ISetting> allSettingsUngrouped = _settingsRepo.getAllUngrouped();

		assertEquals(3, allSettingsUngrouped.size());

    	boolean setting1Used = false;
    	boolean setting2Used = false;
    	boolean setting3Used = false;
    	for(int i = 0; i < allSettingsUngrouped.size(); i++) {
    		ISetting setting = allSettingsUngrouped.get(i);
    		if (setting.id().equals(setting1.id()) 
    				&& setting.getName().equals(setting1.getName()) 
    				&& setting.getValue().equals(setting1.getValue())) {
    			setting1Used = true;
    		}
    		if (setting.id().equals(setting2.id()) 
    				&& setting.getName().equals(setting2.getName()) 
    				&& setting.getValue().equals(setting2.getValue())) {
    			setting2Used = true;
    		}
    		if (setting.id().equals(setting3.id()) 
    				&& setting.getName().equals(setting3.getName()) 
    				&& setting.getValue().equals(setting3.getValue())) {
    			setting3Used = true;
    		}
    	}
    	assertTrue(setting1Used);
    	assertTrue(setting2Used);
    	assertTrue(setting3Used);
    }

    @Test
	void testRemoveItem() {
    	ISetting<Integer> setting1 =
				new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
    	ISetting<Double> setting2 =
				new SettingStub<>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE);
    	ISetting<String> setting3 =
				new SettingStub<>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE);

    	_settingsRepo.addEntity(setting1, 0, null);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	
    	_settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);

		assertNotNull(_settingsRepo.getSetting(SETTING_2_ID));
    	
    	_settingsRepo.removeItem(SETTING_2_ID);

		assertNull(_settingsRepo.getSetting(SETTING_2_ID));
    }

    @Test
	void testRemoveItemWithNullOrBlankId() {
		assertThrows(IllegalArgumentException.class, () -> _settingsRepo.removeItem(null));
		assertThrows(IllegalArgumentException.class, () -> _settingsRepo.removeItem(""));
    }

    @Test
	void testGetGroupingIdAndOrder() {
    	ISetting<Integer> setting1 =
				new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE
				);
    	ISetting<Double> setting2 =
				new SettingStub<>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE
				);
    	ISetting<String> setting3 =
				new SettingStub<>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE
				);

    	_settingsRepo.addEntity(setting1, 123, null);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);
    	
    	_settingsRepo.addEntity(setting2, 456, SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID,
				SETTINGS_REPO_SUBGROUP_1_ID);
    	
    	_settingsRepo.addEntity(setting3, 789, SETTINGS_REPO_SUBGROUP_1_1_ID);
    	
    	IPair<String,Integer> setting1GroupingIdAndOrder =
				_settingsRepo.getGroupingIdAndOrder(SETTING_1_ID);
    	assertTrue(setting1GroupingIdAndOrder.getItem1() == null ||
				setting1GroupingIdAndOrder.getItem1().equals(""));
		assertEquals(123, (int) setting1GroupingIdAndOrder.getItem2());

    	IPair<String,Integer> setting2GroupingIdAndOrder =
				_settingsRepo.getGroupingIdAndOrder(SETTING_2_ID);
		assertEquals(setting2GroupingIdAndOrder.getItem1(), SETTINGS_REPO_SUBGROUP_1_ID);
		assertEquals(456, (int) setting2GroupingIdAndOrder.getItem2());

    	IPair<String,Integer> setting3GroupingIdAndOrder =
				_settingsRepo.getGroupingIdAndOrder(SETTING_3_ID);
		assertEquals(setting3GroupingIdAndOrder.getItem1(), SETTINGS_REPO_SUBGROUP_1_1_ID);
		assertEquals(789, (int) setting3GroupingIdAndOrder.getItem2());

    	assertThrows(IllegalArgumentException.class,
				() -> _settingsRepo.getGroupingIdAndOrder("ThisIsNotAnId"));
		assertThrows(IllegalArgumentException.class,
				() -> _settingsRepo.getGroupingIdAndOrder(""));
    }

    @Test
	void testGetInterfaceName() {
    	assertEquals(ISettingsRepo.class.getCanonicalName(), _settingsRepo.getInterfaceName());
	}
    
    private class SettingStub<T> implements ISetting<T> {
    	private final String ID;
    	
    	private String _name;
    	private T _value;
    	
    	SettingStub(String id, String name, T defaultValue) {
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
    
    private class PersistentValuesHandlerStub implements IPersistentValuesHandler {
		@Override
		public void addPersistentValueTypeHandler(IPersistentValueTypeHandler<?> persistentValueTypeHandler)
				throws IllegalArgumentException {
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
		public Object generateArchetype(String s) throws IllegalArgumentException {
			// Stub class; no implementation needed
			throw new UnsupportedOperationException();
		}

		@Override
		public ICollection<String> persistentValueTypesHandled() {
			// Stub class; no implementation needed
			throw new UnsupportedOperationException();
		}

		@Override
		public void registerPersistentPairHandler(IPersistentPairHandler iPersistentPairHandler) {
			// Stub class; no implementation needed
			throw new UnsupportedOperationException();
		}

		@Override
		public void registerPersistentCollectionHandler(IPersistentCollectionHandler iPersistentCollectionHandler) {
			// Stub class; no implementation needed
			throw new UnsupportedOperationException();
		}

		@Override
		public void registerPersistentMapHandler(IPersistentMapHandler iPersistentMapHandler) {
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

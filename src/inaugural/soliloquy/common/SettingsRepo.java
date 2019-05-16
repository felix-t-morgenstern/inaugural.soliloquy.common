package inaugural.soliloquy.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import soliloquy.common.specs.IAction;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IEntityGroup;
import soliloquy.common.specs.IEntityGroupItem;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.common.specs.IPersistentValuesHandler;
import soliloquy.common.specs.ISetting;
import soliloquy.common.specs.ISettingsRepo;
import soliloquy.game.primary.specs.IGame;
import soliloquy.logger.specs.ILogger;

public class SettingsRepo extends CanGetInterfaceName implements ISettingsRepo {
	private final HashMap<Integer,SettingsRepoItem> ITEMS;
	private final String ID;
	private final ICollectionFactory COLLECTION_FACTORY;
	private final IPairFactory PAIR_FACTORY;
	private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;
	@SuppressWarnings("rawtypes")
	private final ISetting SETTING_ARCHETYPE;
	
	@SuppressWarnings("rawtypes")
	public SettingsRepo(ICollectionFactory collectionFactory, IPairFactory pairFactory,
			IPersistentValuesHandler persistentValuesHandler, ISetting settingArchetype) {
		ID = null;
		ITEMS = new HashMap<>();
		
		COLLECTION_FACTORY = collectionFactory;
		PAIR_FACTORY = pairFactory;
		PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
		SETTING_ARCHETYPE = settingArchetype;
	}
	
	@SuppressWarnings("rawtypes")
	public SettingsRepo(String id, ICollectionFactory collectionFactory, IPairFactory pairFactory,
			IPersistentValuesHandler persistentValuesHandler, ISetting settingArchetype) {
		if (id == null || id.equals("")) {
			throw new IllegalArgumentException("SettingsRepo: called with null or empty id");
		}
		ID = id;
		ITEMS = new HashMap<>();

		COLLECTION_FACTORY = collectionFactory;
		PAIR_FACTORY = pairFactory;
		PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
		SETTING_ARCHETYPE = settingArchetype;
	}

	@Override
	public String id() throws IllegalStateException {
		return ID;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ICollection<IEntityGroupItem<ISetting>> getAllGrouped() {
		ICollection<IEntityGroupItem<ISetting>> allGrouped = COLLECTION_FACTORY
				.make(new SettingsRepoItem(SETTING_ARCHETYPE, SETTING_ARCHETYPE));
		Set<Integer> keysSet = ITEMS.keySet();
		int[] keysArray = new int[keysSet.size()];
		int cursor = 0;
		for(Integer key : keysSet) {
			keysArray[cursor++] = key;
		}
		Arrays.sort(keysArray);
		for(cursor = 0; cursor < keysArray.length; cursor++) {
			allGrouped.add(getItemByOrder(keysArray[cursor]));
		}
		return allGrouped;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ICollection<ISetting> getAllUngrouped() {
		ICollection<ISetting> allSettingsUngrouped = COLLECTION_FACTORY.make(SETTING_ARCHETYPE);
		addSettingsRecursively(allSettingsUngrouped);
		return allSettingsUngrouped;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public IEntityGroupItem<ISetting> getItemByOrder(int order) throws IllegalArgumentException {
		IEntityGroupItem<ISetting> item = ITEMS.get(order);
		if (item == null) {
			throw new IllegalArgumentException("SettingsRepo.getItemByOrder: No item found at this order");
		}
		return item;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void read(String data) throws IllegalArgumentException {
		PERSISTENT_VALUES_HANDLER.readValues(data, new InsertSettingAction(this));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String write() throws IllegalArgumentException {
		IPersistentValueToWrite collectionArchetype = new SettingToProcess("", "");
		ICollection<IPersistentValueToWrite> persistentValuesToWrite =
				COLLECTION_FACTORY.make(collectionArchetype);
		populatePersistentValuesToWriteRecursively(persistentValuesToWrite);
		return PERSISTENT_VALUES_HANDLER.writeValues(persistentValuesToWrite);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <V> ISetting<V> getSetting(String settingId) throws IllegalArgumentException	{
		if (settingId == null) {
			throw new IllegalArgumentException("SettingsRepo.getSetting: settingId cannot be null");
		}
		if (settingId.equals("")) {
			throw new IllegalArgumentException("SettingsRepo.getSetting: settingId cannot be blank");
		}
		return getSettingRecursively(settingId);
	}
	
	@Override
	public ISettingsRepo getSubgrouping(String groupId) {
		for (SettingsRepoItem settingsRepoItem : ITEMS.values()) {
			if (settingsRepoItem.isGroup()) {
				SettingsRepo settingsRepo = (SettingsRepo) settingsRepoItem.group();
				if (settingsRepo.id().equals(groupId)) {
					return settingsRepo;
				} else {
					ISettingsRepo childrenResult = settingsRepo.getSubgrouping(groupId);
					if (childrenResult != null) {
						return childrenResult;
					}
				}
			}
		}
		return null;
	}

	@Override
	public <V> void setSetting(String settingId, V value) throws IllegalArgumentException {
		ISetting<V> setting = getSetting(settingId);
		if (setting == null) {
			throw new IllegalArgumentException("SettingsRepo.setSetting: setting with id = "
					+ settingId + " not found");
		}
		setting.setValue(value);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addEntity(ISetting setting, int order, String groupId) throws IllegalArgumentException {
		if (getSettingRecursively(setting.id()) != null) {
			throw new IllegalArgumentException("SettingsRepo.addEntity: Setting with Id of " + setting.id()
					+ " already present in SettingsRepo");
		}
		SettingsRepo targetedGrouping = this;
		if (groupId != null && !groupId.equals("")) {
			targetedGrouping = (SettingsRepo) getSubgrouping(groupId);
			if (targetedGrouping == null) {
				throw new IllegalArgumentException("SettingsRepo.addEntity: Group with id = " + groupId + " not found");
			}
		}
		if (targetedGrouping.ITEMS.get(order) != null) {
			String group = "top-level grouping";
			if (groupId == null || groupId.equals("")) {
				group = "group " + groupId;
			}
			throw new IllegalArgumentException("SettingsRepo.addEntity: Item with order of " + order
					+ " already present in " + group);
		} else {
			targetedGrouping.ITEMS.put(order, new SettingsRepoItem(setting, setting));
		}
	}

	@Override
	public void newSubgrouping(int order, String groupId, String parentGroupId)
			throws IllegalArgumentException {
		SettingsRepo targetParentGrouping;
		if (parentGroupId == null || parentGroupId.equals("")) {
			targetParentGrouping = this;
		} else {
			targetParentGrouping = (SettingsRepo) getSubgrouping(parentGroupId);
		}
		targetParentGrouping.ITEMS.put(order,
				new SettingsRepoItem(new SettingsRepo(groupId, COLLECTION_FACTORY, PAIR_FACTORY,
						PERSISTENT_VALUES_HANDLER, SETTING_ARCHETYPE), SETTING_ARCHETYPE));
	}

	@Override
	public boolean removeItem(String itemId) throws IllegalArgumentException {
		if (itemId == null) {
			throw new IllegalArgumentException("SettingsRepo.removeItem: itemId cannot be null");
		}
		if (itemId.equals("")) {
			throw new IllegalArgumentException("SettingsRepo.removeItem: itemId cannot be blank");
		}
		return removeItemRecursively(itemId);
	}

	@Override
	public IPair<String,Integer> getGroupingIdAndOrder(String itemId) throws IllegalArgumentException {
		if (itemId == null || itemId.equals("")) {
			throw new IllegalArgumentException("SettingsRepo.getGroupingId: itemId cannot be null");
		}
		IPair<String,Integer> groupingIdAndOrderNumber = getGroupingIdRecursively(itemId, true);
		if (groupingIdAndOrderNumber == null) {
			throw new IllegalArgumentException("SettingsRepo.getGrouppingId: No item with itemId of " 
					+ itemId + " found");
		}
		return groupingIdAndOrderNumber;
	}

	@Override
	public String getInterfaceName() {
		return ISettingsRepo.class.getCanonicalName();
	}

	@SuppressWarnings("rawtypes")
	private ISetting getSettingRecursively(String settingId) {
		for(SettingsRepoItem settingsRepoItem : ITEMS.values()) {
			if (!settingsRepoItem.isGroup()) {
				if (settingsRepoItem.entity().id().equals(settingId)) {
					return settingsRepoItem.entity();
				}
			} else {
				SettingsRepo settingsRepo = (SettingsRepo) settingsRepoItem.group();
				ISetting setting = settingsRepo.getSettingRecursively(settingId);
				if (setting != null) {
					return setting;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private void addSettingsRecursively(ICollection<ISetting> allSettingsUngrouped) {
		for(SettingsRepoItem settingsRepoItem : ITEMS.values()) {
			if (!settingsRepoItem.isGroup()) {
				allSettingsUngrouped.add(settingsRepoItem.entity());
			} else {
				((SettingsRepo)settingsRepoItem.group()).addSettingsRecursively(allSettingsUngrouped);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private boolean removeItemRecursively(String settingId) {
		for(Integer order : ITEMS.keySet()) {
			IEntityGroupItem<ISetting> item = ITEMS.get(order);
			if (!item.isGroup()) {
				if (item.entity().id().equals(settingId)) {
					ITEMS.remove(order);
					return true;
				}
			} else {
				if (((SettingsRepo) item.group()).removeItemRecursively(settingId)) {
					return true;
				}
			}
		}
		return false;
	}

	private IPair<String,Integer> getGroupingIdRecursively(String itemId, boolean isTopLevel) {
		for(Integer order : ITEMS.keySet()) {
			SettingsRepoItem settingsRepoItem = ITEMS.get(order);
			if (!settingsRepoItem.isGroup()) {
				if(settingsRepoItem.entity().id().equals(itemId)) {
					return PAIR_FACTORY.make(isTopLevel ? "" : ID, order);
				}
			} else {
				if(((SettingsRepo) settingsRepoItem.group()).ID.equals(itemId)) {
					return PAIR_FACTORY.make(isTopLevel ? "" : ID, order);
				}
				IPair<String,Integer> groupingIdFromRecursiveSearch = 
						((SettingsRepo) settingsRepoItem.group()).getGroupingIdRecursively(itemId, false);
				if (groupingIdFromRecursiveSearch != null) {
					return groupingIdFromRecursiveSearch;
				}
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void populatePersistentValuesToWriteRecursively(
			ICollection<IPersistentValueToWrite> persistentValuesToWrite) {
		for(Integer order : ITEMS.keySet()) {
			SettingsRepoItem settingsRepoItem = ITEMS.get(order);
			if (!settingsRepoItem.isGroup()) {
				ISetting setting = settingsRepoItem.entity();
				SettingToProcess settingToProcess = new SettingToProcess(setting.id(), setting.getValue());
				persistentValuesToWrite.add(settingToProcess);
			} else {
				((SettingsRepo) settingsRepoItem.group())
						.populatePersistentValuesToWriteRecursively(persistentValuesToWrite);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private class SettingsRepoItem implements IEntityGroupItem<ISetting>
	{
		private final ISetting SETTING;
		private final SettingsRepo SETTINGS_REPO;
		
		SettingsRepoItem(ISetting setting, ISetting archetype) {
			SETTING = setting;
			SETTINGS_REPO = null;
		}
		
		SettingsRepoItem(SettingsRepo settingsRepo, ISetting archetype) {
			SETTING = null;
			SETTINGS_REPO = settingsRepo;
		}

		@Override
		public boolean isGroup() {
			if ((SETTING == null) == (SETTINGS_REPO == null)) {
				throw new IllegalStateException(
						"SettingsRepoItem.isGroup: One and only one of SETTING and SETTINGS_REPO must be non-null in SettingsRepo.SettingsRepoItem");
			}
			return SETTINGS_REPO != null;
		}

		@Override
		public void initializeGroup(IEntityGroup<ISetting> group)
				throws IllegalArgumentException, UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

		@Override
		public IEntityGroup<ISetting> group() throws UnsupportedOperationException {
			return SETTINGS_REPO;
		}

		@Override
		public void initializeEntity(ISetting entity) throws IllegalArgumentException, UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

		@Override
		public ISetting entity() throws UnsupportedOperationException {
			if (SETTING == null) {
				throw new UnsupportedOperationException();
			}
			return SETTING;
		}

		@Override
		public ISetting getArchetype() {
			// Class only used internally; method not required
			throw new UnsupportedOperationException();
		}

		@Override
		public String getUnparameterizedInterfaceName() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public String getInterfaceName() {
			// Stub method
			throw new UnsupportedOperationException();
		}
	}
	
	public class SettingToProcess<V> implements IPersistentValueToWrite<V>
	{
		private final String ID;
		private final V VALUE;
		
		public SettingToProcess(String id, V value) {
			ID = id;
			VALUE = value;
		}

		@Override
		public V getArchetype() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public String typeName() {
			return getProperTypeName(VALUE);
		}

		@Override
		public String name() {
			return ID;
		}

		@Override
		public V value() {
			return VALUE;
		}

		@Override
		public String getUnparameterizedInterfaceName() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public String getInterfaceName() {
			// Stub method
			throw new UnsupportedOperationException();
		}
	}
	
	private class InsertSettingAction<V> implements IAction<IPersistentValueToWrite> {
		private final SettingsRepo SETTINGS_REPO;
		
		private InsertSettingAction(SettingsRepo settingsRepo) {
			SETTINGS_REPO = settingsRepo;
		}

		@Override
		public String id() throws IllegalStateException {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public IPersistentValueToWrite getArchetype() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public void run(IPersistentValueToWrite input) throws IllegalArgumentException {
			SETTINGS_REPO.setSetting(input.name(), input.value());
		}

		@Override
		public String getInterfaceName() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public String getUnparameterizedInterfaceName() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public IGame game() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public ILogger logger() {
			// Stub method
			throw new UnsupportedOperationException();
		}
		
	}
}

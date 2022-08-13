package inaugural.soliloquy.common.infrastructure;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.shared.EntityGroup;
import soliloquy.specs.common.shared.EntityGroupItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class SettingsRepoImpl extends CanGetInterfaceName implements SettingsRepo {
    private final HashMap<Integer, SettingsRepoItem> ITEMS;
    private final String ID;
    private final ListFactory LIST_FACTORY;
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    @SuppressWarnings("rawtypes")
    private final Setting SETTING_ARCHETYPE;

    @SuppressWarnings("rawtypes")
    public SettingsRepoImpl(ListFactory listFactory,
                            PersistentValuesHandler persistentValuesHandler,
                            Setting settingArchetype) {
        ID = null;
        ITEMS = new HashMap<>();

        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        PERSISTENT_VALUES_HANDLER = Check.ifNull(persistentValuesHandler,
                "persistentValuesHandler");
        SETTING_ARCHETYPE = Check.ifNull(settingArchetype, "settingArchetype");
    }

    @SuppressWarnings("rawtypes")
    private SettingsRepoImpl(String id, ListFactory listFactory,
                             PersistentValuesHandler persistentValuesHandler,
                             Setting settingArchetype) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException("SettingsRepo: called with null or empty id");
        }
        ID = id;
        ITEMS = new HashMap<>();

        LIST_FACTORY = listFactory;
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        SETTING_ARCHETYPE = settingArchetype;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<EntityGroupItem<Setting>> getAllGroupedRepresentation() {
        List<EntityGroupItem<Setting>> allGrouped = LIST_FACTORY
                .make(new SettingsRepoItem(SETTING_ARCHETYPE));
        Set<Integer> keysSet = ITEMS.keySet();
        int[] keysArray = new int[keysSet.size()];
        int cursor = 0;
        for (Integer key : keysSet) {
            keysArray[cursor++] = key;
        }
        Arrays.sort(keysArray);
        for (cursor = 0; cursor < keysArray.length; cursor++) {
            allGrouped.add(getItemByOrder(keysArray[cursor]));
        }
        return allGrouped;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Setting> getAllUngroupedRepresentation() {
        List<Setting> allSettingsUngrouped = LIST_FACTORY.make(SETTING_ARCHETYPE);
        addSettingsRecursively(allSettingsUngrouped);
        return allSettingsUngrouped;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public EntityGroupItem<Setting> getItemByOrder(int order) throws IllegalArgumentException {
        EntityGroupItem<Setting> item = ITEMS.get(order);
        if (item == null) {
            throw new IllegalArgumentException(
                    "SettingsRepo.getItemByOrder: No item found at this order");
        }
        return item;
    }

    // TODO: Ensure that null is returned for invalid settingIds
    @SuppressWarnings("unchecked")
    @Override
    public <V> Setting<V> getSetting(String settingId) throws IllegalArgumentException {
        if (settingId == null) {
            throw new IllegalArgumentException("SettingsRepo.getSetting: settingId cannot be null");
        }
        if (settingId.equals("")) {
            throw new IllegalArgumentException(
                    "SettingsRepo.getSetting: settingId cannot be blank");
        }
        return getSettingRecursively(settingId);
    }

    @Override
    public SettingsRepo getSubgrouping(String groupId) {
        for (SettingsRepoItem settingsRepoItem : ITEMS.values()) {
            if (settingsRepoItem.isGroup()) {
                SettingsRepoImpl settingsRepo = (SettingsRepoImpl) settingsRepoItem.group();
                if (settingsRepo.id().equals(groupId)) {
                    return settingsRepo;
                }
                else {
                    SettingsRepo childrenResult = settingsRepo.getSubgrouping(groupId);
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
        Setting<V> setting = getSetting(settingId);
        if (setting == null) {
            throw new IllegalArgumentException("SettingsRepo.setSetting: setting with id = "
                    + settingId + " not found");
        }
        setting.setValue(value);
    }

    @Override
    public void addEntity(Setting setting, int order, String groupId)
            throws IllegalArgumentException {
        if (getSettingRecursively(setting.id()) != null) {
            throw new IllegalArgumentException("SettingsRepo.addEntity: Setting with Id of "
                    + setting.id() + " already present in SettingsRepo");
        }
        SettingsRepoImpl targetedGrouping = this;
        if (groupId != null && !groupId.equals("")) {
            targetedGrouping = (SettingsRepoImpl) getSubgrouping(groupId);
            if (targetedGrouping == null) {
                throw new IllegalArgumentException(
                        "SettingsRepo.addEntity: Group with id = " + groupId + " not found");
            }
        }
        if (targetedGrouping.ITEMS.get(order) != null) {
            String group = "top-level grouping";
            if (groupId == null || groupId.equals("")) {
                group = "group " + groupId;
            }
            throw new IllegalArgumentException("SettingsRepo.addEntity: Item with order of " + order
                    + " already present in " + group);
        }
        else {
            targetedGrouping.ITEMS.put(order, new SettingsRepoItem(setting));
        }
    }

    @Override
    public void newSubgrouping(int order, String groupId, String parentGroupId)
            throws IllegalArgumentException {
        Check.ifNonNegative(order, "order");
        Check.ifNullOrEmpty(groupId, "groupId");
        SettingsRepoImpl targetParentGrouping;
        if (parentGroupId == null || parentGroupId.equals("")) {
            targetParentGrouping = this;
        }
        else {
            targetParentGrouping = (SettingsRepoImpl) getSubgrouping(parentGroupId);
        }
        targetParentGrouping.ITEMS.put(order,
                new SettingsRepoItem(new SettingsRepoImpl(groupId, LIST_FACTORY,
                        PERSISTENT_VALUES_HANDLER, SETTING_ARCHETYPE)));
    }

    @Override
    public boolean removeItem(String itemId) throws IllegalArgumentException {
        return removeItemRecursively(Check.ifNullOrEmpty(itemId, "itemId"));
    }

    @Override
    public Pair<String, Integer> getGroupingIdAndOrder(String itemId)
            throws IllegalArgumentException {
        Pair<String, Integer> groupingIdAndOrderNumber =
                getGroupingIdRecursively(Check.ifNullOrEmpty(itemId, "itemId"), true);
        if (groupingIdAndOrderNumber == null) {
            throw new IllegalArgumentException("SettingsRepo.getGroupingId: No item with itemId " +
                    "of " + itemId + " found");
        }
        return groupingIdAndOrderNumber;
    }

    @Override
    public String getInterfaceName() {
        return SettingsRepo.class.getCanonicalName();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int hashCode() {
        java.util.Map<String, Setting> settingsById = new HashMap<>();
        getAllUngroupedRepresentation().forEach(s -> settingsById.put(s.id(), s));
        return settingsById.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof SettingsRepo)) {
            return false;
        }
        return ((SettingsRepo) o).id().equals(ID);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException(
                "SettingsRepoImpl.toString: Operation not supported; use " +
                        "SettingsRepoHandler.write instead");
    }

    @SuppressWarnings("rawtypes")
    private Setting getSettingRecursively(String settingId) {
        for (SettingsRepoItem settingsRepoItem : ITEMS.values()) {
            if (!settingsRepoItem.isGroup()) {
                if (settingsRepoItem.entity().id().equals(settingId)) {
                    return settingsRepoItem.entity();
                }
            }
            else {
                SettingsRepoImpl settingsRepo = (SettingsRepoImpl) settingsRepoItem.group();
                Setting setting = settingsRepo.getSettingRecursively(settingId);
                if (setting != null) {
                    return setting;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    private void addSettingsRecursively(List<Setting> allSettingsUngrouped) {
        for (SettingsRepoItem settingsRepoItem : ITEMS.values()) {
            if (!settingsRepoItem.isGroup()) {
                allSettingsUngrouped.add(settingsRepoItem.entity());
            }
            else {
                ((SettingsRepoImpl) settingsRepoItem.group())
                        .addSettingsRecursively(allSettingsUngrouped);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private boolean removeItemRecursively(String settingId) {
        for (Integer order : ITEMS.keySet()) {
            EntityGroupItem<Setting> item = ITEMS.get(order);
            if (!item.isGroup()) {
                if (item.entity().id().equals(settingId)) {
                    ITEMS.remove(order);
                    return true;
                }
            }
            else {
                if (((SettingsRepoImpl) item.group()).removeItemRecursively(settingId)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Pair<String, Integer> getGroupingIdRecursively(String itemId, boolean isTopLevel) {
        for (Integer order : ITEMS.keySet()) {
            SettingsRepoItem settingsRepoItem = ITEMS.get(order);
            if (!settingsRepoItem.isGroup()) {
                if (settingsRepoItem.entity().id().equals(itemId)) {
                    return new Pair<>(isTopLevel ? "" : ID, order);
                }
            }
            else {
                if (((SettingsRepoImpl) settingsRepoItem.group()).ID.equals(itemId)) {
                    return new Pair<>(isTopLevel ? "" : ID, order);
                }
                Pair<String, Integer> groupingIdFromRecursiveSearch =
                        ((SettingsRepoImpl) settingsRepoItem.group())
                                .getGroupingIdRecursively(itemId, false);
                if (groupingIdFromRecursiveSearch != null) {
                    return groupingIdFromRecursiveSearch;
                }
            }
        }
        return null;
    }

    @SuppressWarnings({"rawtypes", "InnerClassMayBeStatic"})
    private class SettingsRepoItem implements EntityGroupItem<Setting> {
        private final Setting SETTING;
        private final SettingsRepoImpl SETTINGS_REPO;

        SettingsRepoItem(Setting setting) {
            SETTING = setting;
            SETTINGS_REPO = null;
        }

        SettingsRepoItem(SettingsRepoImpl settingsRepo) {
            SETTING = null;
            SETTINGS_REPO = settingsRepo;
        }

        @Override
        public boolean isGroup() {
            if ((SETTING == null) == (SETTINGS_REPO == null)) {
                throw new IllegalStateException(
                        "SettingsRepoItem.isGroup: One and only one of SETTING and SETTINGS_REPO " +
                                "must be non-null in SettingsRepo.SettingsRepoItem");
            }
            return SETTINGS_REPO != null;
        }

        @Override
        public void initializeGroup(EntityGroup<Setting> group)
                throws IllegalArgumentException, UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        @Override
        public EntityGroup<Setting> group() throws UnsupportedOperationException {
            return SETTINGS_REPO;
        }

        @Override
        public void initializeEntity(Setting entity)
                throws IllegalArgumentException, UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Setting entity() throws UnsupportedOperationException {
            if (SETTING == null) {
                throw new UnsupportedOperationException();
            }
            return SETTING;
        }

        @Override
        public Setting getArchetype() {
            // Class only used internally; method not required
            throw new UnsupportedOperationException();
        }

        @Override
        public String getInterfaceName() {
            // Stub method
            throw new UnsupportedOperationException();
        }
    }
}

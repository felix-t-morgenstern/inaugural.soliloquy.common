package inaugural.soliloquy.common.infrastructure;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.shared.EntityGroup;
import soliloquy.specs.common.shared.EntityGroupItem;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;

public class SettingsRepoImpl extends CanGetInterfaceName implements SettingsRepo {
    private final String ITEM_ID = "itemId";
    
    private final Map<Integer, SettingsRepoItem> ITEMS;
    private final String ID;
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    @SuppressWarnings("rawtypes")
    private final Setting SETTING_ARCHETYPE;

    public SettingsRepoImpl(PersistentValuesHandler persistentValuesHandler) {
        ID = null;
        ITEMS = mapOf();

        PERSISTENT_VALUES_HANDLER = Check.ifNull(persistentValuesHandler,
                "persistentValuesHandler");
        SETTING_ARCHETYPE = generateSimpleArchetype(Setting.class);
    }

    @SuppressWarnings("rawtypes")
    private SettingsRepoImpl(String id,
                             PersistentValuesHandler persistentValuesHandler,
                             Setting settingArchetype) {
        ID = Check.ifNullOrEmpty(id, "id");
        ITEMS = mapOf();

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
        List<EntityGroupItem<Setting>> allGrouped = listOf();
        Set<Integer> keysSet = ITEMS.keySet();
        var keysArray = new int[keysSet.size()];
        var cursor = 0;
        for (var key : keysSet) {
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
        List<Setting> allSettingsUngrouped = listOf();
        addSettingsRecursively(allSettingsUngrouped);
        return allSettingsUngrouped;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public EntityGroupItem<Setting> getItemByOrder(int order) throws IllegalArgumentException {
        var item = ITEMS.get(order);
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
        Check.ifNullOrEmpty(settingId, "settingId");
        return getSettingRecursively(settingId);
    }

    @Override
    public SettingsRepo getSubgrouping(String groupId) {
        for (var settingsRepoItem : ITEMS.values()) {
            if (settingsRepoItem.isGroup()) {
                var settingsRepo = (SettingsRepoImpl) settingsRepoItem.group();
                if (settingsRepo.id().equals(groupId)) {
                    return settingsRepo;
                }
                else {
                    var childrenResult = settingsRepo.getSubgrouping(groupId);
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
        var targetedGrouping = this;
        if (groupId != null && !groupId.equals("")) {
            targetedGrouping = (SettingsRepoImpl) getSubgrouping(groupId);
            if (targetedGrouping == null) {
                throw new IllegalArgumentException(
                        "SettingsRepo.addEntity: Group with id = " + groupId + " not found");
            }
        }
        if (targetedGrouping.ITEMS.get(order) != null) {
            var group = "top-level grouping";
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
                new SettingsRepoItem(new SettingsRepoImpl(groupId, PERSISTENT_VALUES_HANDLER,
                        SETTING_ARCHETYPE)));
    }

    @Override
    public boolean removeItem(String itemId) throws IllegalArgumentException {
        return removeItemRecursively(Check.ifNullOrEmpty(itemId, ITEM_ID));
    }

    @Override
    public Pair<String, Integer> getGroupingIdAndOrder(String itemId)
            throws IllegalArgumentException {
        var groupingIdAndOrderNumber =
                getGroupingIdRecursively(Check.ifNullOrEmpty(itemId, ITEM_ID), true);
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
        java.util.Map<String, Setting> settingsById = mapOf();
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
        for (var settingsRepoItem : ITEMS.values()) {
            if (!settingsRepoItem.isGroup()) {
                if (settingsRepoItem.entity().id().equals(settingId)) {
                    return settingsRepoItem.entity();
                }
            }
            else {
                var settingsRepo = (SettingsRepoImpl) settingsRepoItem.group();
                var setting = settingsRepo.getSettingRecursively(settingId);
                if (setting != null) {
                    return setting;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    private void addSettingsRecursively(List<Setting> allSettingsUngrouped) {
        for (var settingsRepoItem : ITEMS.values()) {
            if (!settingsRepoItem.isGroup()) {
                allSettingsUngrouped.add(settingsRepoItem.entity());
            }
            else {
                ((SettingsRepoImpl) settingsRepoItem.group())
                        .addSettingsRecursively(allSettingsUngrouped);
            }
        }
    }

    private boolean removeItemRecursively(String settingId) {
        for (var order : ITEMS.keySet()) {
            var item = ITEMS.get(order);
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
        for (var order : ITEMS.keySet()) {
            var settingsRepoItem = ITEMS.get(order);
            var id = isTopLevel ? "" : ID;
            if (!settingsRepoItem.isGroup()) {
                if (settingsRepoItem.entity().id().equals(itemId)) {
                    return pairOf(id, order);
                }
            }
            else {
                if (((SettingsRepoImpl) settingsRepoItem.group()).ID.equals(itemId)) {
                    return pairOf(id, order);
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
        public Setting archetype() {
            // Class only used internally; method not required
            throw new UnsupportedOperationException();
        }

        @Override
        public String getInterfaceName() {
            // Class only used internally; method not required
            throw new UnsupportedOperationException();
        }
    }
}

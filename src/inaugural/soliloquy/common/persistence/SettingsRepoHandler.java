package inaugural.soliloquy.common.persistence;

import com.google.gson.Gson;
import inaugural.soliloquy.common.archetypes.SettingsRepoArchetype;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;

public class SettingsRepoHandler extends AbstractTypeHandler<SettingsRepo>
        implements TypeHandler<SettingsRepo> {
    private final static SettingsRepo ARCHETYPE = new SettingsRepoArchetype();

    private final SettingsRepo SETTINGS_REPO;
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;

    public SettingsRepoHandler(PersistentValuesHandler persistentValuesHandler,
                               SettingsRepo settingsRepo) {
        super(ARCHETYPE);
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        SETTINGS_REPO = settingsRepo;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public SettingsRepo read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException(
                    "SettingsRepoHandler.read: serializedValue must be non-null");
        }
        if (serializedValue.equals("")) {
            throw new IllegalArgumentException(
                    "SettingsRepoHandler.read: serializedValue must be non-empty");
        }
        SettingDTO[] dto = new Gson().fromJson(serializedValue, SettingDTO[].class);
        for(SettingDTO settingDTO : dto) {
            Setting setting = SETTINGS_REPO.getSetting(settingDTO.id);
            if (setting == null) {
                throw new IllegalArgumentException(
                        "SettingsRepoHandler.read: attempted to read setting with " +
                                "invalid id (" + settingDTO.id + ")");
            }
            String typeName = getProperTypeName(setting.getArchetype());
            TypeHandler handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(typeName);
            setting.setValue(handler.read(settingDTO.serializedValue));
        }
        return SETTINGS_REPO;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public String write(SettingsRepo settingsRepo) {
        if (settingsRepo == null) {
            throw new IllegalArgumentException(
                    "SettingsRepoHandler.write: settingsRepo must be non-null");
        }
        java.util.Collection<Setting> settings = settingsRepo.getAllUngroupedRepresentation();
        SettingDTO[] dto = new SettingDTO[settings.size()];
        int i = 0;
        for(Setting setting : settings) {
            String typeName = getProperTypeName(setting.getArchetype());
            TypeHandler handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(typeName);
            SettingDTO settingDTO = new SettingDTO();
            settingDTO.id = setting.id();
            settingDTO.serializedValue = handler.write(setting.getValue());
            dto[i] = settingDTO;
            i++;
        }
        return new Gson().toJson(dto);
    }

    @Override
    public String toString() {
        return SettingsRepoHandler.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return SettingsRepoHandler.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SettingsRepoHandler && obj.hashCode() == hashCode();
    }

    private static class SettingDTO {
        String id;
        String serializedValue;
    }
}

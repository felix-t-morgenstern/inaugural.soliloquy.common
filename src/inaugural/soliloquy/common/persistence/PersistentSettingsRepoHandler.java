package inaugural.soliloquy.common.persistence;

import com.google.gson.Gson;
import inaugural.soliloquy.common.archetypes.SettingsRepoArchetype;
import inaugural.soliloquy.tools.persistence.PersistentTypeHandler;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

public class PersistentSettingsRepoHandler extends PersistentTypeHandler<SettingsRepo>
        implements PersistentValueTypeHandler<SettingsRepo> {
    private final static SettingsRepo ARCHETYPE = new SettingsRepoArchetype();

    private final SettingsRepo SETTINGS_REPO;
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;

    public PersistentSettingsRepoHandler(PersistentValuesHandler persistentValuesHandler,
                                         SettingsRepo settingsRepo) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        SETTINGS_REPO = settingsRepo;
    }

    @Override
    public SettingsRepo getArchetype() {
        return ARCHETYPE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SettingsRepo read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException(
                    "PersistentSettingsRepoHandler.read: serializedValue must be non-null");
        }
        if (serializedValue.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentSettingsRepoHandler.read: serializedValue must be non-empty");
        }
        SettingDTO[] dto = new Gson().fromJson(serializedValue, SettingDTO[].class);
        for(SettingDTO settingDTO : dto) {
            Setting setting = SETTINGS_REPO.getSetting(settingDTO.id);
            if (setting == null) {
                throw new IllegalArgumentException(
                        "PersistentSettingsRepoHandler.read: attempted to read setting with " +
                                "invalid id (" + settingDTO.id + ")");
            }
            String typeName = getProperTypeName(setting.getArchetype());
            PersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(typeName);
            setting.setValue(handler.read(settingDTO.serializedValue));
        }
        return SETTINGS_REPO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(SettingsRepo settingsRepo) {
        if (settingsRepo == null) {
            throw new IllegalArgumentException(
                    "PersistentSettingsRepoHandler.write: settingsRepo must be non-null");
        }
        java.util.Collection<Setting> settings = settingsRepo.getAllUngrouped();
        SettingDTO[] dto = new SettingDTO[settings.size()];
        int i = 0;
        for(Setting setting : settings) {
            String typeName = getProperTypeName(setting.getArchetype());
            PersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(typeName);
            SettingDTO settingDTO = new SettingDTO();
            settingDTO.id = setting.id();
            settingDTO.serializedValue = handler.write(setting.getValue());
            dto[i] = settingDTO;
            i++;
        }
        return new Gson().toJson(dto);
    }

    private class SettingDTO {
        String id;
        String serializedValue;
    }
}

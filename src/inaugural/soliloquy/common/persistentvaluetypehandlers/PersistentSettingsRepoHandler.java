package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.common.archetypes.SettingsRepoArchetype;
import soliloquy.specs.common.entities.IPersistentValueTypeHandler;
import soliloquy.specs.common.entities.IPersistentValuesHandler;
import soliloquy.specs.common.entities.ISetting;
import soliloquy.specs.common.entities.ISettingsRepo;
import soliloquy.specs.common.valueobjects.ICollection;

public class PersistentSettingsRepoHandler extends PersistentTypeHandler<ISettingsRepo>
        implements IPersistentValueTypeHandler<ISettingsRepo> {
    private final static ISettingsRepo ARCHETYPE = new SettingsRepoArchetype();

    private final ISettingsRepo SETTINGS_REPO;
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;

    public PersistentSettingsRepoHandler(IPersistentValuesHandler persistentValuesHandler,
                                         ISettingsRepo settingsRepo) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        SETTINGS_REPO = settingsRepo;
    }

    @Override
    public ISettingsRepo getArchetype() {
        return ARCHETYPE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ISettingsRepo read(String serializedValue) throws IllegalArgumentException {
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
            ISetting setting = SETTINGS_REPO.getSetting(settingDTO.id);
            if (setting == null) {
                throw new IllegalArgumentException(
                        "PersistentSettingsRepoHandler.read: attempted to read setting with " +
                                "invalid id (" + settingDTO.id + ")");
            }
            String typeName = getProperTypeName(setting.getArchetype());
            IPersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(typeName);
            setting.setValue(handler.read(settingDTO.serializedValue));
        }
        return SETTINGS_REPO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(ISettingsRepo settingsRepo) {
        if (settingsRepo == null) {
            throw new IllegalArgumentException(
                    "PersistentSettingsRepoHandler.write: settingsRepo must be non-null");
        }
        ICollection<ISetting> settings = settingsRepo.getAllUngrouped();
        SettingDTO[] dto = new SettingDTO[settings.size()];
        int i = 0;
        for(ISetting setting : settings) {
            String typeName = getProperTypeName(setting.getArchetype());
            IPersistentValueTypeHandler handler =
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

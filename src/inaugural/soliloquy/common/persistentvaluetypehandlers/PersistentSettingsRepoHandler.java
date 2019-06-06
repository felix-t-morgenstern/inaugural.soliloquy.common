package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.common.archetypes.SettingsRepoArchetype;
import soliloquy.common.specs.*;

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
    public ISettingsRepo read(String valueString) throws IllegalArgumentException {
        if (valueString == null) {
            throw new IllegalArgumentException(
                    "PersistentSettingsRepoHandler.read: valueString must be non-null");
        }
        if (valueString.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentSettingsRepoHandler.read: valueString must be non-empty");
        }
        SettingDTO[] dto = new Gson().fromJson(valueString, SettingDTO[].class);
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
            setting.setValue(handler.read(settingDTO.valueString));
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
            settingDTO.valueString = handler.write(setting.getValue());
            dto[i] = settingDTO;
            i++;
        }
        return new Gson().toJson(dto);
    }

    private class SettingDTO {
        String id;
        String valueString;
    }
}

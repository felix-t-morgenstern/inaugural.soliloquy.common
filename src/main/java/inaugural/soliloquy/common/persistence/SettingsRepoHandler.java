package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;

public class SettingsRepoHandler extends AbstractTypeHandler<SettingsRepo>
        implements TypeHandler<SettingsRepo> {
    private final SettingsRepo SETTINGS_REPO;
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;

    public SettingsRepoHandler(PersistentValuesHandler persistentValuesHandler,
                               SettingsRepo settingsRepo) {
        super(settingsRepo);
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        SETTINGS_REPO = settingsRepo;
    }

    @Override
    public SettingsRepo read(String serializedValue) throws IllegalArgumentException {
        Check.ifNullOrEmpty(serializedValue, "serializedValue");
        var dto = JSON.fromJson(serializedValue, DTO[].class);
        for (var settingDTO : dto) {
            var setting = SETTINGS_REPO.getSetting(settingDTO.id);
            if (setting == null) {
                throw new IllegalArgumentException(
                        "SettingsRepoHandler.read: attempted to read setting with " +
                                "invalid id (" + settingDTO.id + ")");
            }
            var typeName = getProperTypeName(setting.archetype());
            var handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(typeName);
            setting.setValue(handler.read(settingDTO.value));
        }
        return SETTINGS_REPO;
    }

    @Override
    public String write(SettingsRepo settingsRepo) {
        Check.ifNull(settingsRepo, "settingsRepo");
        //noinspection rawtypes
        java.util.Collection<Setting> settings = settingsRepo.getAllUngroupedRepresentation();
        var dto = new DTO[settings.size()];
        var i = 0;
        for (var setting : settings) {
            var typeName = getProperTypeName(setting.archetype());
            var handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(typeName);
            var settingDTO = new DTO();
            settingDTO.id = setting.id();
            settingDTO.value = handler.write(setting.getValue());
            dto[i] = settingDTO;
            i++;
        }
        return JSON.toJson(dto);
    }

    private static class DTO {
        String id;
        String value;
    }
}

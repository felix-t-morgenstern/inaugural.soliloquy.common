package inaugural.soliloquy.common.persistentvaluetypehandlers;

import inaugural.soliloquy.common.archetypes.SettingsRepoArchetype;
import soliloquy.common.specs.*;

public class PersistentSettingsRepoHandler extends PersistentTypeHandler<ISettingsRepo>
        implements IPersistentValueTypeHandler<ISettingsRepo> {
    private final String DELIMITER_OUTER = "\u009b";
    private final String DELIMITER_INNER = "\u0098";

    private final static ISettingsRepo ARCHETYPE = new SettingsRepoArchetype();

    private final ISettingsRepo SETTINGS_REPO;
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;

    public PersistentSettingsRepoHandler(ISettingsRepo settingsRepo,
                                         IPersistentValuesHandler persistentValuesHandler) {
        SETTINGS_REPO = settingsRepo;
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
    }

    @Override
    public ISettingsRepo getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public ISettingsRepo read(String valueString) throws IllegalArgumentException {
        String[] settingStrings = valueString.split(DELIMITER_OUTER);
        for(String settingString : settingStrings) {
            String[] settingStringComponents = settingString.split(DELIMITER_INNER);
            if (settingStringComponents.length != 2) {
                throw new IllegalArgumentException(
                        "PersistentSettingsRepoHandler.read: setting with illegal number of " +
                                "delimiters (" + settingString + ")");
            }
            String name = settingStringComponents[0];
            String valueRepresentation = settingStringComponents[1];
            ISetting setting = SETTINGS_REPO.getSetting(name);
            if (setting == null) {
                throw new IllegalArgumentException(
                        "PersistentSettingsRepoHandler.read: attempted to read setting with " +
                                "invalid name (" + name + ")");
            }
            String typeName = getProperTypeName(setting.getArchetype());
            IPersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(typeName);
            SETTINGS_REPO.setSetting(name, handler.read(valueRepresentation));
        }
        return SETTINGS_REPO;
    }

    @Override
    public String write(ISettingsRepo settingsRepo) {
        if (settingsRepo == null) {
            throw new IllegalArgumentException(
                    "PersistentSettingsRepoHandler.write: settingsRepo must be non-null");
        }
        ICollection<ISetting> settings = settingsRepo.getAllUngrouped();
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        for(ISetting setting : settings) {
            if (isFirst) {
                isFirst = false;
            } else {
                stringBuilder.append(DELIMITER_OUTER);
            }
            String typeName = getProperTypeName(setting.getArchetype());
            IPersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(typeName);
            String valueString = handler.write(setting.getValue());
            stringBuilder.append(setting.getName());
            stringBuilder.append(DELIMITER_INNER);
            stringBuilder.append(valueString);
        }
        return stringBuilder.toString();
    }
}

package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.specs.common.factories.PersistentVariableCacheFactory;
import soliloquy.specs.common.infrastructure.*;

public class PersistentVariableCachePersistenceHandler
        extends PersistentTypeHandler<PersistentVariableCache>
        implements PersistentValueTypeHandler<PersistentVariableCache> {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final PersistentVariableCacheFactory PERSISTENT_VARIABLE_CACHE_FACTORY;
    private final PersistentVariableCache ARCHETYPE;

    public PersistentVariableCachePersistenceHandler(
            PersistentValuesHandler persistentValuesHandler,
            PersistentVariableCacheFactory persistentVariableCacheFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        PERSISTENT_VARIABLE_CACHE_FACTORY = persistentVariableCacheFactory;
        ARCHETYPE = PERSISTENT_VARIABLE_CACHE_FACTORY.make();
    }

    @Override
    public PersistentVariableCache getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public PersistentVariableCache read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException(
                    "PersistentVariableCachePersistenceHandler.read: serializedValue must be non-null");
        }
        if (serializedValue.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentVariableCachePersistenceHandler.read: serializedValue must be non-empty");
        }
        PersistentVariableCache persistentVariableCache =
                PERSISTENT_VARIABLE_CACHE_FACTORY.make();
        PersistentVariableDTO[] dto = new Gson().fromJson(serializedValue,
                PersistentVariableDTO[].class);
        for(PersistentVariableDTO pVarDTO : dto) {
            PersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(pVarDTO.typeName);
            persistentVariableCache.setVariable(pVarDTO.name,
                    handler.read(pVarDTO.serializedValue));
        }
        return persistentVariableCache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(PersistentVariableCache persistentVariableCache) {
        if (persistentVariableCache == null) {
            throw new IllegalArgumentException(
                    "PersistentVariableCachePersistenceHandler.write: persistentVariableCache " +
                            "must be non-null");
        }
        ReadableCollection<String> pVarNames = persistentVariableCache.namesRepresentation();
        PersistentVariableDTO[] dto = new PersistentVariableDTO[pVarNames.size()];
        for(int i = 0; i < pVarNames.size(); i++) {
            String pVarName = pVarNames.get(i);
            PersistentVariableDTO pVarDTO = new PersistentVariableDTO();
            pVarDTO.name = pVarName;
            Object pVarValue = persistentVariableCache.getVariable(pVarName);
            pVarDTO.typeName = getProperTypeName(pVarValue);
            PersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(pVarDTO.typeName);
            pVarDTO.serializedValue = handler.write(pVarValue);
            dto[i] = pVarDTO;
        }
        return new Gson().toJson(dto);
    }

    private class PersistentVariableDTO {
        String name;
        String typeName;
        String serializedValue;
    }
}

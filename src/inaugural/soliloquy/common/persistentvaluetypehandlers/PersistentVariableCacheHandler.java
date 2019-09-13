package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.*;

public class PersistentVariableCacheHandler
        extends PersistentTypeHandler<VariableCache>
        implements PersistentValueTypeHandler<VariableCache> {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final VariableCacheFactory PERSISTENT_VARIABLE_CACHE_FACTORY;
    private final VariableCache ARCHETYPE;

    public PersistentVariableCacheHandler(
            PersistentValuesHandler persistentValuesHandler,
            VariableCacheFactory VariableCacheFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        PERSISTENT_VARIABLE_CACHE_FACTORY = VariableCacheFactory;
        ARCHETYPE = PERSISTENT_VARIABLE_CACHE_FACTORY.make();
    }

    @Override
    public VariableCache getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public VariableCache read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException(
                    "PersistentVariableCacheHandler.read: serializedValue must be non-null");
        }
        if (serializedValue.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentVariableCacheHandler.read: serializedValue must be non-empty");
        }
        VariableCache VariableCache =
                PERSISTENT_VARIABLE_CACHE_FACTORY.make();
        PersistentVariableDTO[] dto = new Gson().fromJson(serializedValue,
                PersistentVariableDTO[].class);
        for(PersistentVariableDTO pVarDTO : dto) {
            PersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(pVarDTO.typeName);
            VariableCache.setVariable(pVarDTO.name,
                    handler.read(pVarDTO.serializedValue));
        }
        return VariableCache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(VariableCache VariableCache) {
        if (VariableCache == null) {
            throw new IllegalArgumentException(
                    "PersistentVariableCacheHandler.write: VariableCache " +
                            "must be non-null");
        }
        ReadableCollection<String> pVarNames = VariableCache.namesRepresentation();
        PersistentVariableDTO[] dto = new PersistentVariableDTO[pVarNames.size()];
        for(int i = 0; i < pVarNames.size(); i++) {
            String pVarName = pVarNames.get(i);
            PersistentVariableDTO pVarDTO = new PersistentVariableDTO();
            pVarDTO.name = pVarName;
            Object pVarValue = VariableCache.getVariable(pVarName);
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

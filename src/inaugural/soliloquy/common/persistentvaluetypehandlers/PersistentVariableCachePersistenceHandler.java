package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.common.specs.*;

public class PersistentVariableCachePersistenceHandler
        extends PersistentTypeHandler<IPersistentVariableCache>
        implements IPersistentValueTypeHandler<IPersistentVariableCache> {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final IPersistentVariableCacheFactory PERSISTENT_VARIABLE_CACHE_FACTORY;
    private final IPersistentVariableCache ARCHETYPE;

    public PersistentVariableCachePersistenceHandler(
            IPersistentValuesHandler persistentValuesHandler,
            IPersistentVariableCacheFactory persistentVariableCacheFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        PERSISTENT_VARIABLE_CACHE_FACTORY = persistentVariableCacheFactory;
        ARCHETYPE = PERSISTENT_VARIABLE_CACHE_FACTORY.make();
    }

    @Override
    public IPersistentVariableCache getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public IPersistentVariableCache read(String valueString) throws IllegalArgumentException {
        if (valueString == null) {
            throw new IllegalArgumentException(
                    "PersistentVariableCachePersistenceHandler.read: valueString must be non-null");
        }
        if (valueString.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentVariableCachePersistenceHandler.read: valueString must be non-empty");
        }
        IPersistentVariableCache persistentVariableCache =
                PERSISTENT_VARIABLE_CACHE_FACTORY.make();
        PersistentVariableDTO[] dto = new Gson().fromJson(valueString,
                PersistentVariableDTO[].class);
        for(PersistentVariableDTO pVarDTO : dto) {
            IPersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(pVarDTO.typeName);
            persistentVariableCache.setVariable(pVarDTO.name, handler.read(pVarDTO.valueString));
        }
        return persistentVariableCache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(IPersistentVariableCache persistentVariableCache) {
        if (persistentVariableCache == null) {
            throw new IllegalArgumentException(
                    "PersistentVariableCachePersistenceHandler.write: persistentVariableCache " +
                            "must be non-null");
        }
        ICollection<String> pVarNames = persistentVariableCache.getNamesRepresentation();
        PersistentVariableDTO[] dto = new PersistentVariableDTO[pVarNames.size()];
        for(int i = 0; i < pVarNames.size(); i++) {
            String pVarName = pVarNames.get(i);
            PersistentVariableDTO pVarDTO = new PersistentVariableDTO();
            pVarDTO.name = pVarName;
            Object pVarValue = persistentVariableCache.getVariable(pVarName);
            pVarDTO.typeName = getProperTypeName(pVarValue);
            IPersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(pVarDTO.typeName);
            pVarDTO.valueString = handler.write(pVarValue);
            dto[i] = pVarDTO;
        }
        return new Gson().toJson(dto);
    }

    private class PersistentVariableDTO {
        String name;
        String typeName;
        String valueString;
    }
}

package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.List;

public class VariableCacheHandler
        extends AbstractTypeHandler<VariableCache>
        implements TypeHandler<VariableCache> {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final VariableCacheFactory PERSISTENT_VARIABLE_CACHE_FACTORY;

    // TODO: Insert null checks
    public VariableCacheHandler(
            PersistentValuesHandler persistentValuesHandler,
            VariableCacheFactory variableCacheFactory) {
        super(variableCacheFactory.make());
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        PERSISTENT_VARIABLE_CACHE_FACTORY = variableCacheFactory;
    }

    @Override
    public VariableCache getArchetype() {
        return ARCHETYPE;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public VariableCache read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException(
                    "VariableCacheHandler.read: serializedValue must be non-null");
        }
        if (serializedValue.equals("")) {
            throw new IllegalArgumentException(
                    "VariableCacheHandler.read: serializedValue must be non-empty");
        }
        VariableCache VariableCache =
                PERSISTENT_VARIABLE_CACHE_FACTORY.make();
        PersistentVariableDTO[] dto = JSON.fromJson(serializedValue,
                PersistentVariableDTO[].class);
        for (PersistentVariableDTO pVarDTO : dto) {
            TypeHandler handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(pVarDTO.typeName);
            VariableCache.setVariable(pVarDTO.name,
                    handler.read(pVarDTO.serializedValue));
        }
        return VariableCache;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public String write(VariableCache VariableCache) {
        if (VariableCache == null) {
            throw new IllegalArgumentException(
                    "VariableCacheHandler.write: VariableCache " +
                            "must be non-null");
        }
        List<String> pVarNames = VariableCache.namesRepresentation();
        PersistentVariableDTO[] dto = new PersistentVariableDTO[pVarNames.size()];
        for (int i = 0; i < pVarNames.size(); i++) {
            String pVarName = pVarNames.get(i);
            PersistentVariableDTO pVarDTO = new PersistentVariableDTO();
            pVarDTO.name = pVarName;
            Object pVarValue = VariableCache.getVariable(pVarName);
            pVarDTO.typeName = getProperTypeName(pVarValue);
            TypeHandler handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(pVarDTO.typeName);
            pVarDTO.serializedValue = handler.write(pVarValue);
            dto[i] = pVarDTO;
        }
        return JSON.toJson(dto);
    }

    private static class PersistentVariableDTO {
        String name;
        String typeName;
        String serializedValue;
    }
}

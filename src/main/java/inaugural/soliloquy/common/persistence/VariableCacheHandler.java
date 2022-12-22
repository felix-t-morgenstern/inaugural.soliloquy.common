package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.List;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class VariableCacheHandler
        extends AbstractTypeHandler<VariableCache>
        implements TypeHandler<VariableCache> {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final VariableCacheFactory PERSISTENT_VARIABLE_CACHE_FACTORY;

    public VariableCacheHandler(
            PersistentValuesHandler persistentValuesHandler,
            VariableCacheFactory variableCacheFactory) {
        super(generateSimpleArchetype(VariableCache.class));
        PERSISTENT_VALUES_HANDLER = Check.ifNull(persistentValuesHandler, "persistentValuesHandler");
        PERSISTENT_VARIABLE_CACHE_FACTORY = Check.ifNull(variableCacheFactory, "variableCacheFactory");
    }

    @Override
    public VariableCache getArchetype() {
        return ARCHETYPE;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public VariableCache read(String serializedValue) throws IllegalArgumentException {
        Check.ifNullOrEmpty(serializedValue, "serializedValue");
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
    public String write(VariableCache variableCache) {
        Check.ifNull(variableCache, "variableCache");
        List<String> pVarNames = variableCache.namesRepresentation();
        PersistentVariableDTO[] dto = new PersistentVariableDTO[pVarNames.size()];
        for (int i = 0; i < pVarNames.size(); i++) {
            String pVarName = pVarNames.get(i);
            PersistentVariableDTO pVarDTO = new PersistentVariableDTO();
            pVarDTO.name = pVarName;
            Object pVarValue = variableCache.getVariable(pVarName);
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

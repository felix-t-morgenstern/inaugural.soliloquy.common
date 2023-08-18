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
    public VariableCache archetype() {
        return ARCHETYPE;
    }

    @Override
    public VariableCache read(String serializedValue) throws IllegalArgumentException {
        Check.ifNullOrEmpty(serializedValue, "serializedValue");
        var VariableCache = PERSISTENT_VARIABLE_CACHE_FACTORY.make();
        var dto = JSON.fromJson(serializedValue, DTO[].class);
        for (var pVarDTO : dto) {
            var handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(pVarDTO.type);
            VariableCache.setVariable(pVarDTO.name, handler.read(pVarDTO.value));
        }
        return VariableCache;
    }

    @Override
    public String write(VariableCache variableCache) {
        Check.ifNull(variableCache, "variableCache");
        var pVarNames = variableCache.namesRepresentation();
        var dto = new DTO[pVarNames.size()];
        for (var i = 0; i < pVarNames.size(); i++) {
            var pVarName = pVarNames.get(i);
            var pVarDTO = new DTO();
            pVarDTO.name = pVarName;
            var pVarValue = variableCache.getVariable(pVarName);
            pVarDTO.type = getProperTypeName(pVarValue);
            var handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(pVarDTO.type);
            pVarDTO.value = handler.write(pVarValue);
            dto[i] = pVarDTO;
        }
        return JSON.toJson(dto);
    }

    private static class DTO {
        String name;
        String type;
        String value;
    }
}

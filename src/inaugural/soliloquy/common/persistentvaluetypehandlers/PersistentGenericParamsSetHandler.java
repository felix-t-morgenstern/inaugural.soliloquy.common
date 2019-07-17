package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.*;

public class PersistentGenericParamsSetHandler extends PersistentTypeHandler<GenericParamsSet>
        implements PersistentValueTypeHandler<GenericParamsSet> {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY;
    private final GenericParamsSet ARCHETYPE;

    public PersistentGenericParamsSetHandler(PersistentValuesHandler persistentValuesHandler,
                                             GenericParamsSetFactory genericParamsSetFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        GENERIC_PARAMS_SET_FACTORY = genericParamsSetFactory;
        ARCHETYPE = genericParamsSetFactory.make();
    }

    @Override
    public GenericParamsSet getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public GenericParamsSet read(String serializedValue) throws IllegalArgumentException {
        if(serializedValue == null) {
            throw new IllegalArgumentException(
                    "PersistentGenericParamsSetHandler.read: serializedValue must be non-null");
        }
        if(serializedValue.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentGenericParamsSetHandler.read: serializedValue must be non-empty");
        }
        TypedParamsSetDTO[] dto = new Gson().fromJson(serializedValue, TypedParamsSetDTO[].class);
        GenericParamsSet genericParamsSet = GENERIC_PARAMS_SET_FACTORY.make();
        for(TypedParamsSetDTO typedDTO : dto) {
            PersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(typedDTO.typeName);
            for(int i = 0; i < typedDTO.paramNames.length; i++) {
                genericParamsSet.addParam(typedDTO.paramNames[i],
                        handler.read(typedDTO.paramValues[i]));
            }
        }
        return genericParamsSet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(GenericParamsSet genericParamsSet) {
        if (genericParamsSet == null) {
            throw new IllegalArgumentException(
                    "PersistentGenericParamsSetHandler.write: genericParamsSet must be non-null");
        }
        TypedParamsSetDTO[] dto = new TypedParamsSetDTO[genericParamsSet.paramTypes().size()];
        int paramCount = 0;
        for(String paramType : genericParamsSet.paramTypes()) {
            dto[paramCount] = new TypedParamsSetDTO();
            dto[paramCount].typeName = paramType;
            Map params = genericParamsSet.getParamsSet(paramType);
            PersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(paramType);
            dto[paramCount].paramNames = new String[params.size()];
            dto[paramCount].paramValues = new String[params.size()];
            int indexCount = 0;
            for(Object param : params) {
                Pair<String,Object> pair = (Pair) param;
                dto[paramCount].paramNames[indexCount] = pair.getItem1();
                dto[paramCount].paramValues[indexCount] = handler.write(pair.getItem2());
                indexCount++;
            }
            paramCount++;
        }
        return new Gson().toJson(dto);
    }

    private class TypedParamsSetDTO {
        String typeName;
        String[] paramNames;
        String[] paramValues;
    }
}

package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.specs.common.entities.IPersistentValueTypeHandler;
import soliloquy.specs.common.entities.IPersistentValuesHandler;
import soliloquy.specs.common.factories.IGenericParamsSetFactory;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.common.valueobjects.IPair;

public class PersistentGenericParamsSetHandler extends PersistentTypeHandler<IGenericParamsSet>
        implements IPersistentValueTypeHandler<IGenericParamsSet> {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final IGenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY;
    private final IGenericParamsSet ARCHETYPE;

    public PersistentGenericParamsSetHandler(IPersistentValuesHandler persistentValuesHandler,
                                             IGenericParamsSetFactory genericParamsSetFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        GENERIC_PARAMS_SET_FACTORY = genericParamsSetFactory;
        ARCHETYPE = genericParamsSetFactory.make();
    }

    @Override
    public IGenericParamsSet getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public IGenericParamsSet read(String serializedValue) throws IllegalArgumentException {
        if(serializedValue == null) {
            throw new IllegalArgumentException(
                    "PersistentGenericParamsSetHandler.read: serializedValue must be non-null");
        }
        if(serializedValue.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentGenericParamsSetHandler.read: serializedValue must be non-empty");
        }
        TypedParamsSetDTO[] dto = new Gson().fromJson(serializedValue, TypedParamsSetDTO[].class);
        IGenericParamsSet genericParamsSet = GENERIC_PARAMS_SET_FACTORY.make();
        for(TypedParamsSetDTO typedDTO : dto) {
            IPersistentValueTypeHandler handler =
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
    public String write(IGenericParamsSet genericParamsSet) {
        if (genericParamsSet == null) {
            throw new IllegalArgumentException(
                    "PersistentGenericParamsSetHandler.write: genericParamsSet must be non-null");
        }
        TypedParamsSetDTO[] dto = new TypedParamsSetDTO[genericParamsSet.paramTypes().size()];
        int paramCount = 0;
        for(String paramType : genericParamsSet.paramTypes()) {
            dto[paramCount] = new TypedParamsSetDTO();
            dto[paramCount].typeName = paramType;
            IMap params = genericParamsSet.getParamsSet(paramType);
            IPersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(paramType);
            dto[paramCount].paramNames = new String[params.size()];
            dto[paramCount].paramValues = new String[params.size()];
            int indexCount = 0;
            for(Object param : params) {
                IPair<String,Object> pair = (IPair) param;
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

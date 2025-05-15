package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.List;
import java.util.Objects;

import static inaugural.soliloquy.tools.collections.Collections.listOf;

@SuppressWarnings("rawtypes")
public class ListHandler extends AbstractTypeHandler<List> implements TypeHandler<List> {
    private final PersistenceHandler PERSISTENCE_HANDLER;

    public ListHandler(PersistenceHandler PersistenceHandler) {
        PERSISTENCE_HANDLER = Check.ifNull(PersistenceHandler,
                "PersistenceHandler");
    }

    @Override
    public String typeHandled() {
        return List.class.getCanonicalName();
    }

    @Override
    public <T extends List> T read(String valuesString) throws IllegalArgumentException {
        Check.ifNullOrEmpty(valuesString, "valuesString");
        var dto = JSON.fromJson(valuesString, DTO.class);

        if (dto.values == null) {
            //noinspection unchecked
            return (T)listOf();
        }

        var handler = PERSISTENCE_HANDLER.getTypeHandler(dto.type);
        //noinspection unchecked
        var list = (T) listOf();
        for (var i = 0; i < dto.values.length; i++) {
            if (dto.values[i] != null) {
                //noinspection unchecked
                list.add(handler.read(dto.values[i]));
            }
            else {
                //noinspection unchecked
                list.add(null);
            }
        }
        return list;
    }

    @Override
    public String write(List list) {
        Check.ifNull(list, "list");
        var dto = new DTO();
        if (!list.isEmpty()) {
            //noinspection unchecked
            var firstNonNull = list.stream().filter(Objects::nonNull).findFirst();
            TypeHandler handler = null;
            if (firstNonNull.isPresent()) {
                var internalType = list.get(0).getClass().getCanonicalName();
                handler = PERSISTENCE_HANDLER.getTypeHandler(internalType);
                dto.type = internalType;
            }
            var serializedValues = new String[list.size()];
            for (var i = 0; i < list.size(); i++) {
                if (handler != null && list.get(i) != null) {
                    //noinspection unchecked
                    serializedValues[i] = handler.write(list.get(i));
                }
                else {
                    serializedValues[i] = null;
                }
            }
            dto.values = serializedValues;
        }
        return JSON.toJson(dto);
    }

    // TODO: Abbreviate DTO param names
    @SuppressWarnings("InnerClassMayBeStatic")
    private class DTO {
        String type;
        String[] values;
    }
}

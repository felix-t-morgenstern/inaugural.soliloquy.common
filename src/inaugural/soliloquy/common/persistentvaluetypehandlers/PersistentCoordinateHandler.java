package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.specs.common.factories.ICoordinateFactory;
import soliloquy.specs.common.infrastructure.IPersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.ICoordinate;

public class PersistentCoordinateHandler extends PersistentTypeHandler<ICoordinate>
        implements IPersistentValueTypeHandler<ICoordinate> {
    private final ICoordinate ARCHETYPE;
    private final ICoordinateFactory COORDINATE_FACTORY;

    private static final String NULL = "NULL";

    public PersistentCoordinateHandler(ICoordinateFactory coordinateFactory) {
        ARCHETYPE = coordinateFactory.make(0,0);
        COORDINATE_FACTORY = coordinateFactory;
    }

    @Override
    public ICoordinate getArchetype() {
        return ARCHETYPE;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public ICoordinate read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException(
                    "PersistentCoordinateHandler.read: serializedValue must be non-null");
        }
        if (serializedValue.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentCoordinateHandler.read: serializedValue must be non-empty");
        }
        if (serializedValue.equals(NULL)){
            return null;
        } else {
            CoordinateDTO dto = new Gson().fromJson(serializedValue, CoordinateDTO.class);
            return COORDINATE_FACTORY.make(dto.x, dto.y);
        }
    }

    @Override
    public String write(ICoordinate coordinate) {
        if (coordinate == null) {
            return NULL;
        } else {
            CoordinateDTO dto = new CoordinateDTO();
            dto.x = coordinate.getX();
            dto.y = coordinate.getY();
            return new Gson().toJson(dto);
        }
    }

    private class CoordinateDTO {
        int x;
        int y;
    }
}

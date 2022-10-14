package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class CoordinateHandler extends AbstractTypeHandler<Coordinate> {
    private static final String NULL = "NULL";

    public CoordinateHandler() {
        super(generateSimpleArchetype(Coordinate.class));
    }

    @Override
    public Coordinate read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException(
                    "CoordinateHandler.read: serializedValue must be non-null");
        }
        if (serializedValue.equals("")) {
            throw new IllegalArgumentException(
                    "CoordinateHandler.read: serializedValue must be non-empty");
        }
        if (serializedValue.equals(NULL)) {
            return null;
        }
        else {
            CoordinateDTO dto = JSON.fromJson(serializedValue, CoordinateDTO.class);
            return Coordinate.of(dto.x, dto.y);
        }
    }

    @Override
    public String write(Coordinate coordinate) {
        if (coordinate == null) {
            return NULL;
        }
        else {
            CoordinateDTO dto = new CoordinateDTO();
            dto.x = coordinate.x();
            dto.y = coordinate.y();
            return JSON.toJson(dto);
        }
    }

    private static class CoordinateDTO {
        int x;
        int y;
    }
}

package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate2d;

import static soliloquy.specs.common.valueobjects.Coordinate2d.coordinate2dOf;

public class Coordinate2dHandler extends AbstractTypeHandler<Coordinate2d> {
    @Override
    public String typeHandled() {
        return Coordinate2d.class.getCanonicalName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Coordinate2d read(String serializedValue) throws IllegalArgumentException {
        Check.ifNullOrEmpty(serializedValue, "serializedValue");
        var dto = JSON.fromJson(serializedValue, DTO.class);
        return coordinate2dOf(dto.x, dto.y);
    }

    @Override
    public String write(Coordinate2d coordinate) {
        Check.ifNull(coordinate, "coordinate");
        var dto = new DTO();
        dto.x = coordinate.X;
        dto.y = coordinate.Y;
        return JSON.toJson(dto);
    }

    private static class DTO {
        int x;
        int y;
    }
}

package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.valueobjects.FloatBox;

import static soliloquy.specs.common.valueobjects.FloatBox.floatBoxOf;

public class FloatBoxHandler extends AbstractTypeHandler<FloatBox> {
    @SuppressWarnings("unchecked")
    @Override
    public FloatBox read(String writtenVal) throws IllegalArgumentException {
        Check.ifNullOrEmpty(writtenVal, "writtenVal");

        var dto = JSON.fromJson(writtenVal, DTO.class);

        return floatBoxOf(dto.topLeft.x, dto.topLeft.y, dto.bottomRight.x, dto.bottomRight.y);
    }

    @Override
    public String write(FloatBox floatBox) {
        Check.ifNull(floatBox, "floatBox");

        var dto = new DTO();
        var topLeft = new VertexHandler.DTO();
        topLeft.x = floatBox.LEFT_X;
        topLeft.y = floatBox.TOP_Y;
        var bottomRight = new VertexHandler.DTO();
        bottomRight.x = floatBox.RIGHT_X;
        bottomRight.y = floatBox.BOTTOM_Y;
        dto.topLeft = topLeft;
        dto.bottomRight = bottomRight;

        return JSON.toJson(dto);
    }

    private static class DTO {
        VertexHandler.DTO topLeft;
        VertexHandler.DTO bottomRight;
    }
}

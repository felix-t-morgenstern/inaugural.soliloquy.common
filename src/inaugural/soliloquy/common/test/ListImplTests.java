package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.ListImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.List;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ListImplTests {
    private List<Integer> _list;

    @BeforeEach
    void setUp() {
        _list = new ListImpl<>(123123);
    }

    // TODO: Add other conditions
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ListImpl<>(null));

        assertThrows(IllegalArgumentException.class,
                () -> new ListImpl<>(new ArrayList<String>(), null));
    }

    @Test
    void testExtendsArrayList() {
        assertTrue(_list instanceof ArrayList);
    }

    @Test
    void testMakeClone() {
        List<Integer> newCollection = _list.makeClone();

        assertEquals(_list, newCollection);
        assertNotNull(newCollection.getArchetype());
    }

    @Test
    void testReadOnlyRepresentation() {
        _list.add(1);
        _list.add(2);
        _list.add(3);
        List<Integer> clonedList = _list.makeClone();

        assertNotNull(clonedList);
        assertNotSame(_list, clonedList);
        assertEquals(_list, clonedList);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Test
    void testGetParameterizedClassName() {
        List<String> strings = new ListImpl<>("");
        assertEquals(List.class.getCanonicalName() + "<" +
                        String.class.getCanonicalName() + ">",
                strings.getInterfaceName());

        List<List<List<String>>> groupsOfGroupsOfStrings =
                new ListImpl<>(new ListImpl<>(new ListImpl<>("")));
        assertEquals(List.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + "<" +
                        String.class.getCanonicalName() + ">>>",
                groupsOfGroupsOfStrings.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertEquals(123123, (int) _list.getArchetype());
    }

    @Test
    void testHashCode() {
        final String string1 = "string1";
        final String string2 = "string2";
        final String string3 = "string3";

        List<String> strings = new ListImpl<>("");

        strings.add(string1);
        strings.add(string2);
        strings.add(string3);

        assertEquals(Objects.hash(string1, string2, string3), strings.hashCode());
    }

    @Test
    void testEquals() {
        ListImpl<Integer> newCollection = new ListImpl<>(0);
        _list.add(1);
        assertNotEquals(_list, newCollection);
        newCollection.add(1);
        assertEquals(_list, newCollection);
        _list.add(2);
        assertNotEquals(_list, newCollection);
        newCollection.add(3);
        assertNotEquals(_list, newCollection);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testToString() {
        assertThrows(UnsupportedOperationException.class, _list::toString);
    }
}

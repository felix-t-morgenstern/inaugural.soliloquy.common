package inaugural.soliloquy.common.infrastructure;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.collections.Collections;
import soliloquy.specs.common.infrastructure.EntityGroup;
import soliloquy.specs.common.shared.HasId;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static soliloquy.specs.common.infrastructure.EntityGroup.Entry.Type.ENTITY;
import static soliloquy.specs.common.infrastructure.EntityGroup.Entry.Type.GROUP;

public class EntityGroupImpl<T extends HasId> implements EntityGroup<T> {
    private final List<Entry<T>> ENTRIES;
    private final String ID;

    @SafeVarargs
    public EntityGroupImpl(Entry<T>... entries) {
        ID = null;
        validateEntries(entries);
        ENTRIES = listOf(entries);
    }

    @SafeVarargs
    private EntityGroupImpl(String id, Entry<T>... entries) {
        ID = Check.ifNullOrEmpty(id, "id");
        validateEntries(entries);
        ENTRIES = listOf(entries);
    }

    @SafeVarargs
    private void validateEntries(Entry<T>... entries) {
        Check.ifNull(entries, "entries");
        Check.ifAnyNull(entries, "entries");
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public int size() {
        return ENTRIES.size();
    }

    @Override
    public Entry<T> getEntryByOrder(int i) throws IllegalArgumentException {
        return ENTRIES.get(i);
    }

    @Override
    public List<Entry<T>> getAllGrouped() {
        return listOf(ENTRIES);
    }

    @Override
    public List<T> allEntities() {
        var allEntities = Collections.<T>listOf();
        for (var entry : ENTRIES) {
            if (entry.type() == ENTITY) {
                allEntities.add(entry.entity());
            }
            else {
                allEntities.addAll(entry.group().allEntities());
            }
        }
        return allEntities;
    }

    @Override
    public T getById(String id) throws IllegalArgumentException {
        Check.ifNullOrEmpty(id, "id");
        for (var entry : ENTRIES) {
            if (entry.type() == ENTITY && entry.entity().id().equals(id)) {
                return entry.entity();
            }
            if (entry.type() == GROUP) {
                var subgroupById = entry.group().getById(id);
                if (subgroupById != null) {
                    return subgroupById;
                }
            }
        }
        return null;
    }

    public static class EntryImpl<T extends HasId> implements Entry<T> {
        private final T ENTITY;
        private final EntityGroup<T> GROUP;
        private final Type TYPE;

        private EntryImpl(T entity) {
            ENTITY = entity;
            GROUP = null;
            TYPE = Type.ENTITY;
        }

        private EntryImpl(EntityGroup<T> group) {
            ENTITY = null;
            GROUP = group;
            TYPE = Type.GROUP;
        }

        public static <T extends HasId> EntryImpl<T> entityOf(T entity) {
            return new EntryImpl<>(Check.ifNull(entity, "entity"));
        }

        @SafeVarargs
        public static <T extends HasId> EntryImpl<T> groupOf(String id, Entry<T>... entries) {
            return new EntryImpl<>(new EntityGroupImpl<>(id, entries));
        }

        @Override
        public Type type() {
            return TYPE;
        }

        @Override
        public EntityGroup<T> group() throws UnsupportedOperationException {
            if (TYPE == Type.ENTITY) {
                throw new UnsupportedOperationException("entry is entity");
            }
            return GROUP;
        }

        @Override
        public T entity() throws UnsupportedOperationException {
            if (TYPE == Type.GROUP) {
                throw new UnsupportedOperationException("entry is group");
            }
            return ENTITY;
        }
    }
}

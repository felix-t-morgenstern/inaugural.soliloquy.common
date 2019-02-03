package inaugural.soliloquy.common;

import soliloquy.common.specs.IHasGenericParams;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.common.specs.IPersistentVariable;

public class PersistentVariable implements IPersistentVariable {
	private final String NAME;
	
	private Object _value;
	
	public PersistentVariable(String name, Object value)
	{
		NAME = name;
		_value = value;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue() {
		return (T) _value;
	}

	@Override
	public <T> void setValue(T value) {
		_value = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IPersistentValueToWrite<T> toWriteRepresentation() {
		String typeName = _value instanceof IHasGenericParams ?
				((IHasGenericParams) _value).getParameterizedClassName() : 
					_value.getClass().getCanonicalName();
		return new PersistentValueToWrite<T>(NAME, typeName, (T) _value);
	}
	
	private class PersistentValueToWrite<T> extends HasOneGenericParam<T> implements IPersistentValueToWrite<T> 
	{
		private final String NAME;
		private final String TYPE_NAME;
		private final T VALUE;
		
		private PersistentValueToWrite(String name, String typeName, T value)
		{
			NAME = name;
			TYPE_NAME = typeName;
			VALUE = value;
		}

		@Override
		public T getArchetype() {
			return VALUE;
		}

		@Override
		public String typeName() {
			return TYPE_NAME;
		}

		@Override
		public String name() {
			return NAME;
		}

		@Override
		public T value() {
			return VALUE;
		}

		@Override
		protected String getUnparameterizedClassName() {
			return "soliloquy.common.specs.IPersistentValueToWrite";
		}
		
	}

}

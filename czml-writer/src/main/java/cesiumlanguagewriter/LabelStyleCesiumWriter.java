package cesiumlanguagewriter;


import agi.foundation.compatibility.*;
import agi.foundation.compatibility.Func1;
import agi.foundation.compatibility.Lazy;
import cesiumlanguagewriter.advanced.*;
import cesiumlanguagewriter.CesiumLabelStyle;
import cesiumlanguagewriter.Reference;

/**
 *  
 Writes a <code>LabelStyle</code> to a  {@link CesiumOutputStream}.  A <code>LabelStyle</code> the style of a label.
 

 */
public class LabelStyleCesiumWriter extends CesiumPropertyWriter<LabelStyleCesiumWriter> {
	/**
	 *  
	The name of the <code>labelStyle</code> property.
	

	 */
	public static final String LabelStylePropertyName = "labelStyle";
	/**
	 *  
	The name of the <code>reference</code> property.
	

	 */
	public static final String ReferencePropertyName = "reference";
	private Lazy<ICesiumValuePropertyWriter<CesiumLabelStyle>> m_asLabelStyle;
	private Lazy<ICesiumValuePropertyWriter<Reference>> m_asReference;

	/**
	 *  
	Initializes a new instance.
	

	 */
	public LabelStyleCesiumWriter(String propertyName) {
		super(propertyName);
		m_asLabelStyle = new Lazy<cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<CesiumLabelStyle>>(new Func1<cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<CesiumLabelStyle>>(
				this, "createLabelStyleAdaptor", new Class[] {}) {
			public cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<CesiumLabelStyle> invoke() {
				return createLabelStyleAdaptor();
			}
		}, false);
		m_asReference = new Lazy<cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<Reference>>(new Func1<cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<Reference>>(this,
				"createReferenceAdaptor", new Class[] {}) {
			public cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<Reference> invoke() {
				return createReferenceAdaptor();
			}
		}, false);
	}

	/**
	 *  
	Initializes a new instance as a copy of an existing instance.
	
	

	 * @param existingInstance The existing instance to copy.
	 */
	protected LabelStyleCesiumWriter(LabelStyleCesiumWriter existingInstance) {
		super(existingInstance);
		m_asLabelStyle = new Lazy<cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<CesiumLabelStyle>>(new Func1<cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<CesiumLabelStyle>>(
				this, "createLabelStyleAdaptor", new Class[] {}) {
			public cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<CesiumLabelStyle> invoke() {
				return createLabelStyleAdaptor();
			}
		}, false);
		m_asReference = new Lazy<cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<Reference>>(new Func1<cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<Reference>>(this,
				"createReferenceAdaptor", new Class[] {}) {
			public cesiumlanguagewriter.advanced.ICesiumValuePropertyWriter<Reference> invoke() {
				return createReferenceAdaptor();
			}
		}, false);
	}

	@Override
	public LabelStyleCesiumWriter clone() {
		return new LabelStyleCesiumWriter(this);
	}

	/**
	 *  
	Writes the <code>labelStyle</code> property.  The <code>labelStyle</code> property specifies the label style.  Valid values are "FILL", "OUTLINE", and "FILL_AND_OUTLINE".
	
	

	 * @param value The label style.
	 */
	public final void writeLabelStyle(CesiumLabelStyle value) {
		String PropertyName = LabelStylePropertyName;
		if (getIsInterval()) {
			getOutput().writePropertyName(PropertyName);
		}
		getOutput().writeValue(CesiumFormattingHelper.labelStyleToString(value));
	}

	/**
	 *  
	Writes the <code>reference</code> property.  The <code>reference</code> property specifies a reference property.
	
	

	 * @param value The reference.
	 */
	public final void writeReference(Reference value) {
		String PropertyName = ReferencePropertyName;
		openIntervalIfNecessary();
		getOutput().writePropertyName(PropertyName);
		CesiumWritingHelper.writeReference(getOutput(), value);
	}

	/**
	 *  
	Writes the <code>reference</code> property.  The <code>reference</code> property specifies a reference property.
	
	

	 * @param value The earliest date of the interval.
	 */
	public final void writeReference(String value) {
		String PropertyName = ReferencePropertyName;
		openIntervalIfNecessary();
		getOutput().writePropertyName(PropertyName);
		CesiumWritingHelper.writeReference(getOutput(), value);
	}

	/**
	 *  
	Writes the <code>reference</code> property.  The <code>reference</code> property specifies a reference property.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyName The property on the referenced object.
	 */
	public final void writeReference(String identifier, String propertyName) {
		String PropertyName = ReferencePropertyName;
		openIntervalIfNecessary();
		getOutput().writePropertyName(PropertyName);
		CesiumWritingHelper.writeReference(getOutput(), identifier, propertyName);
	}

	/**
	 *  
	Writes the <code>reference</code> property.  The <code>reference</code> property specifies a reference property.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyNames The hierarchy of properties to be indexed on the referenced object.
	 */
	public final void writeReference(String identifier, String[] propertyNames) {
		String PropertyName = ReferencePropertyName;
		openIntervalIfNecessary();
		getOutput().writePropertyName(PropertyName);
		CesiumWritingHelper.writeReference(getOutput(), identifier, propertyNames);
	}

	/**
	 *  
	Returns a wrapper for this instance that implements  {@link ICesiumValuePropertyWriter} to write a value in <code>LabelStyle</code> format.  Because the returned instance is a wrapper for this instance, you may call  {@link ICesiumElementWriter#close} on either this instance or the wrapper, but you must not call it on both.
	
	

	 * @return The wrapper.
	 */
	public final ICesiumValuePropertyWriter<CesiumLabelStyle> asLabelStyle() {
		return m_asLabelStyle.getValue();
	}

	final private ICesiumValuePropertyWriter<CesiumLabelStyle> createLabelStyleAdaptor() {
		return new CesiumWriterAdaptor<cesiumlanguagewriter.LabelStyleCesiumWriter, cesiumlanguagewriter.CesiumLabelStyle>(this,
				new CesiumWriterAdaptorWriteCallback<cesiumlanguagewriter.LabelStyleCesiumWriter, cesiumlanguagewriter.CesiumLabelStyle>() {
					public void invoke(LabelStyleCesiumWriter me, CesiumLabelStyle value) {
						me.writeLabelStyle(value);
					}
				});
	}

	/**
	 *  
	Returns a wrapper for this instance that implements  {@link ICesiumValuePropertyWriter} to write a value in <code>Reference</code> format.  Because the returned instance is a wrapper for this instance, you may call  {@link ICesiumElementWriter#close} on either this instance or the wrapper, but you must not call it on both.
	
	

	 * @return The wrapper.
	 */
	public final ICesiumValuePropertyWriter<Reference> asReference() {
		return m_asReference.getValue();
	}

	final private ICesiumValuePropertyWriter<Reference> createReferenceAdaptor() {
		return new CesiumWriterAdaptor<cesiumlanguagewriter.LabelStyleCesiumWriter, cesiumlanguagewriter.Reference>(this,
				new CesiumWriterAdaptorWriteCallback<cesiumlanguagewriter.LabelStyleCesiumWriter, cesiumlanguagewriter.Reference>() {
					public void invoke(LabelStyleCesiumWriter me, Reference value) {
						me.writeReference(value);
					}
				});
	}
}
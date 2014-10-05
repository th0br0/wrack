package cesiumlanguagewriter;


import agi.foundation.compatibility.*;
import agi.foundation.compatibility.DisposeHelper;
import agi.foundation.compatibility.Func1;
import agi.foundation.compatibility.Lazy;
import cesiumlanguagewriter.advanced.*;
import cesiumlanguagewriter.ColorCesiumWriter;
import cesiumlanguagewriter.DoubleCesiumWriter;
import cesiumlanguagewriter.LineCountCesiumWriter;
import cesiumlanguagewriter.LineOffsetCesiumWriter;
import cesiumlanguagewriter.LineThicknessCesiumWriter;
import java.awt.Color;
import java.util.List;

/**
 *  
 Writes a <code>GridMaterial</code> to a  {@link CesiumOutputStream}.  A <code>GridMaterial</code> fills the surface with a two dimensional grid.
 

 */
public class GridMaterialCesiumWriter extends CesiumPropertyWriter<GridMaterialCesiumWriter> {
	/**
	 *  
	The name of the <code>color</code> property.
	

	 */
	public static final String ColorPropertyName = "color";
	/**
	 *  
	The name of the <code>cellAlpha</code> property.
	

	 */
	public static final String CellAlphaPropertyName = "cellAlpha";
	/**
	 *  
	The name of the <code>lineCount</code> property.
	

	 */
	public static final String LineCountPropertyName = "lineCount";
	/**
	 *  
	The name of the <code>lineThickness</code> property.
	

	 */
	public static final String LineThicknessPropertyName = "lineThickness";
	/**
	 *  
	The name of the <code>lineOffset</code> property.
	

	 */
	public static final String LineOffsetPropertyName = "lineOffset";
	private Lazy<ColorCesiumWriter> m_color = new Lazy<cesiumlanguagewriter.ColorCesiumWriter>(new Func1<cesiumlanguagewriter.ColorCesiumWriter>() {
		public cesiumlanguagewriter.ColorCesiumWriter invoke() {
			return new ColorCesiumWriter(ColorPropertyName);
		}
	}, false);
	private Lazy<DoubleCesiumWriter> m_cellAlpha = new Lazy<cesiumlanguagewriter.DoubleCesiumWriter>(new Func1<cesiumlanguagewriter.DoubleCesiumWriter>() {
		public cesiumlanguagewriter.DoubleCesiumWriter invoke() {
			return new DoubleCesiumWriter(CellAlphaPropertyName);
		}
	}, false);
	private Lazy<LineCountCesiumWriter> m_lineCount = new Lazy<cesiumlanguagewriter.LineCountCesiumWriter>(new Func1<cesiumlanguagewriter.LineCountCesiumWriter>() {
		public cesiumlanguagewriter.LineCountCesiumWriter invoke() {
			return new LineCountCesiumWriter(LineCountPropertyName);
		}
	}, false);
	private Lazy<LineThicknessCesiumWriter> m_lineThickness = new Lazy<cesiumlanguagewriter.LineThicknessCesiumWriter>(new Func1<cesiumlanguagewriter.LineThicknessCesiumWriter>() {
		public cesiumlanguagewriter.LineThicknessCesiumWriter invoke() {
			return new LineThicknessCesiumWriter(LineThicknessPropertyName);
		}
	}, false);
	private Lazy<LineOffsetCesiumWriter> m_lineOffset = new Lazy<cesiumlanguagewriter.LineOffsetCesiumWriter>(new Func1<cesiumlanguagewriter.LineOffsetCesiumWriter>() {
		public cesiumlanguagewriter.LineOffsetCesiumWriter invoke() {
			return new LineOffsetCesiumWriter(LineOffsetPropertyName);
		}
	}, false);

	/**
	 *  
	Initializes a new instance.
	

	 */
	public GridMaterialCesiumWriter(String propertyName) {
		super(propertyName);
	}

	/**
	 *  
	Initializes a new instance as a copy of an existing instance.
	
	

	 * @param existingInstance The existing instance to copy.
	 */
	protected GridMaterialCesiumWriter(GridMaterialCesiumWriter existingInstance) {
		super(existingInstance);
	}

	@Override
	public GridMaterialCesiumWriter clone() {
		return new GridMaterialCesiumWriter(this);
	}

	/**
	 *  Gets the writer for the <code>color</code> property.  The returned instance must be opened by calling the  {@link CesiumElementWriter#open} method before it can be used for writing.  The <code>color</code> property defines the color of the surface.
	

	 */
	public final ColorCesiumWriter getColorWriter() {
		return m_color.getValue();
	}

	/**
	 *  
	Opens and returns the writer for the <code>color</code> property.  The <code>color</code> property defines the color of the surface.
	

	 */
	public final ColorCesiumWriter openColorProperty() {
		openIntervalIfNecessary();
		return this.<ColorCesiumWriter> openAndReturn(getColorWriter());
	}

	/**
	 *  
	Writes a value for the <code>color</code> property as a <code>rgba</code> value.  The <code>color</code> property specifies the color of the surface.
	
	

	 * @param color The color.
	 */
	public final void writeColorProperty(Color color) {
		{
			cesiumlanguagewriter.ColorCesiumWriter writer = openColorProperty();
			try {
				writer.writeRgba(color);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>color</code> property as a <code>rgba</code> value.  The <code>color</code> property specifies the color of the surface.
	
	
	
	
	

	 * @param red The red component in the range 0 to 255.
	 * @param green The green component in the range 0 to 255.
	 * @param blue The blue component in the range 0 to 255.
	 * @param alpha The alpha component in the range 0 to 255.
	 */
	public final void writeColorProperty(int red, int green, int blue, int alpha) {
		{
			cesiumlanguagewriter.ColorCesiumWriter writer = openColorProperty();
			try {
				writer.writeRgba(red, green, blue, alpha);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>color</code> property as a <code>rgba</code> value.  The <code>color</code> property specifies the color of the surface.
	
	
	
	
	

	 * @param dates The dates at which the value is specified.
	 * @param colors The color corresponding to each date.
	 * @param startIndex The index of the first element to use in the `colors` collection.
	 * @param length The number of elements to use from the `colors` collection.
	 */
	public final void writeColorProperty(List<JulianDate> dates, List<Color> colors, int startIndex, int length) {
		{
			cesiumlanguagewriter.ColorCesiumWriter writer = openColorProperty();
			try {
				writer.writeRgba(dates, colors, startIndex, length);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>color</code> property as a <code>rgbaf</code> value.  The <code>color</code> property specifies the color of the surface.
	
	
	
	
	

	 * @param red The red component in the range 0 to 1.0.
	 * @param green The green component in the range 0 to 1.0.
	 * @param blue The blue component in the range 0 to 1.0.
	 * @param alpha The alpha component in the range 0 to 1.0.
	 */
	public final void writeColorPropertyRgbaf(float red, float green, float blue, float alpha) {
		{
			cesiumlanguagewriter.ColorCesiumWriter writer = openColorProperty();
			try {
				writer.writeRgbaf(red, green, blue, alpha);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>color</code> property as a <code>reference</code> value.  The <code>color</code> property specifies the color of the surface.
	
	

	 * @param value The reference.
	 */
	public final void writeColorPropertyReference(Reference value) {
		{
			cesiumlanguagewriter.ColorCesiumWriter writer = openColorProperty();
			try {
				writer.writeReference(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>color</code> property as a <code>reference</code> value.  The <code>color</code> property specifies the color of the surface.
	
	

	 * @param value The earliest date of the interval.
	 */
	public final void writeColorPropertyReference(String value) {
		{
			cesiumlanguagewriter.ColorCesiumWriter writer = openColorProperty();
			try {
				writer.writeReference(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>color</code> property as a <code>reference</code> value.  The <code>color</code> property specifies the color of the surface.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyName The property on the referenced object.
	 */
	public final void writeColorPropertyReference(String identifier, String propertyName) {
		{
			cesiumlanguagewriter.ColorCesiumWriter writer = openColorProperty();
			try {
				writer.writeReference(identifier, propertyName);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>color</code> property as a <code>reference</code> value.  The <code>color</code> property specifies the color of the surface.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyNames The hierarchy of properties to be indexed on the referenced object.
	 */
	public final void writeColorPropertyReference(String identifier, String[] propertyNames) {
		{
			cesiumlanguagewriter.ColorCesiumWriter writer = openColorProperty();
			try {
				writer.writeReference(identifier, propertyNames);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  Gets the writer for the <code>cellAlpha</code> property.  The returned instance must be opened by calling the  {@link CesiumElementWriter#open} method before it can be used for writing.  The <code>cellAlpha</code> property defines alpha value for the space between grid lines.  This will be combined with the color alpha.
	

	 */
	public final DoubleCesiumWriter getCellAlphaWriter() {
		return m_cellAlpha.getValue();
	}

	/**
	 *  
	Opens and returns the writer for the <code>cellAlpha</code> property.  The <code>cellAlpha</code> property defines alpha value for the space between grid lines.  This will be combined with the color alpha.
	

	 */
	public final DoubleCesiumWriter openCellAlphaProperty() {
		openIntervalIfNecessary();
		return this.<DoubleCesiumWriter> openAndReturn(getCellAlphaWriter());
	}

	/**
	 *  
	Writes a value for the <code>cellAlpha</code> property as a <code>number</code> value.  The <code>cellAlpha</code> property specifies alpha value for the space between grid lines.  This will be combined with the color alpha.
	
	

	 * @param value The value.
	 */
	public final void writeCellAlphaProperty(double value) {
		{
			cesiumlanguagewriter.DoubleCesiumWriter writer = openCellAlphaProperty();
			try {
				writer.writeNumber(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>cellAlpha</code> property as a <code>number</code> value.  The <code>cellAlpha</code> property specifies alpha value for the space between grid lines.  This will be combined with the color alpha.
	
	
	
	
	

	 * @param dates The dates at which the value is specified.
	 * @param values The value corresponding to each date.
	 * @param startIndex The index of the first element to use in the `values` collection.
	 * @param length The number of elements to use from the `values` collection.
	 */
	public final void writeCellAlphaProperty(List<JulianDate> dates, List<Double> values, int startIndex, int length) {
		{
			cesiumlanguagewriter.DoubleCesiumWriter writer = openCellAlphaProperty();
			try {
				writer.writeNumber(dates, values, startIndex, length);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>cellAlpha</code> property as a <code>reference</code> value.  The <code>cellAlpha</code> property specifies alpha value for the space between grid lines.  This will be combined with the color alpha.
	
	

	 * @param value The reference.
	 */
	public final void writeCellAlphaPropertyReference(Reference value) {
		{
			cesiumlanguagewriter.DoubleCesiumWriter writer = openCellAlphaProperty();
			try {
				writer.writeReference(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>cellAlpha</code> property as a <code>reference</code> value.  The <code>cellAlpha</code> property specifies alpha value for the space between grid lines.  This will be combined with the color alpha.
	
	

	 * @param value The earliest date of the interval.
	 */
	public final void writeCellAlphaPropertyReference(String value) {
		{
			cesiumlanguagewriter.DoubleCesiumWriter writer = openCellAlphaProperty();
			try {
				writer.writeReference(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>cellAlpha</code> property as a <code>reference</code> value.  The <code>cellAlpha</code> property specifies alpha value for the space between grid lines.  This will be combined with the color alpha.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyName The property on the referenced object.
	 */
	public final void writeCellAlphaPropertyReference(String identifier, String propertyName) {
		{
			cesiumlanguagewriter.DoubleCesiumWriter writer = openCellAlphaProperty();
			try {
				writer.writeReference(identifier, propertyName);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>cellAlpha</code> property as a <code>reference</code> value.  The <code>cellAlpha</code> property specifies alpha value for the space between grid lines.  This will be combined with the color alpha.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyNames The hierarchy of properties to be indexed on the referenced object.
	 */
	public final void writeCellAlphaPropertyReference(String identifier, String[] propertyNames) {
		{
			cesiumlanguagewriter.DoubleCesiumWriter writer = openCellAlphaProperty();
			try {
				writer.writeReference(identifier, propertyNames);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  Gets the writer for the <code>lineCount</code> property.  The returned instance must be opened by calling the  {@link CesiumElementWriter#open} method before it can be used for writing.  The <code>lineCount</code> property defines the number of grid lines along each axis.
	

	 */
	public final LineCountCesiumWriter getLineCountWriter() {
		return m_lineCount.getValue();
	}

	/**
	 *  
	Opens and returns the writer for the <code>lineCount</code> property.  The <code>lineCount</code> property defines the number of grid lines along each axis.
	

	 */
	public final LineCountCesiumWriter openLineCountProperty() {
		openIntervalIfNecessary();
		return this.<LineCountCesiumWriter> openAndReturn(getLineCountWriter());
	}

	/**
	 *  
	Writes a value for the <code>lineCount</code> property as a <code>cartesian2</code> value.  The <code>lineCount</code> property specifies the number of grid lines along each axis.
	
	

	 * @param value The value.
	 */
	public final void writeLineCountProperty(Rectangular value) {
		{
			cesiumlanguagewriter.LineCountCesiumWriter writer = openLineCountProperty();
			try {
				writer.writeCartesian2(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineCount</code> property as a <code>cartesian2</code> value.  The <code>lineCount</code> property specifies the number of grid lines along each axis.
	
	
	

	 * @param x The X component.
	 * @param y The Y component.
	 */
	public final void writeLineCountProperty(double x, double y) {
		{
			cesiumlanguagewriter.LineCountCesiumWriter writer = openLineCountProperty();
			try {
				writer.writeCartesian2(x, y);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineCount</code> property as a <code>cartesian2</code> value.  The <code>lineCount</code> property specifies the number of grid lines along each axis.
	
	
	

	 * @param dates The dates at which the vector is specified.
	 * @param values The values corresponding to each date.
	 */
	public final void writeLineCountProperty(List<JulianDate> dates, List<Rectangular> values) {
		{
			cesiumlanguagewriter.LineCountCesiumWriter writer = openLineCountProperty();
			try {
				writer.writeCartesian2(dates, values);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineCount</code> property as a <code>cartesian2</code> value.  The <code>lineCount</code> property specifies the number of grid lines along each axis.
	
	
	
	
	

	 * @param dates The dates at which the vector is specified.
	 * @param values The values corresponding to each date.
	 * @param startIndex The index of the first element to use in the `values` collection.
	 * @param length The number of elements to use from the `values` collection.
	 */
	public final void writeLineCountProperty(List<JulianDate> dates, List<Rectangular> values, int startIndex, int length) {
		{
			cesiumlanguagewriter.LineCountCesiumWriter writer = openLineCountProperty();
			try {
				writer.writeCartesian2(dates, values, startIndex, length);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineCount</code> property as a <code>reference</code> value.  The <code>lineCount</code> property specifies the number of grid lines along each axis.
	
	

	 * @param value The reference.
	 */
	public final void writeLineCountPropertyReference(Reference value) {
		{
			cesiumlanguagewriter.LineCountCesiumWriter writer = openLineCountProperty();
			try {
				writer.writeReference(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineCount</code> property as a <code>reference</code> value.  The <code>lineCount</code> property specifies the number of grid lines along each axis.
	
	

	 * @param value The earliest date of the interval.
	 */
	public final void writeLineCountPropertyReference(String value) {
		{
			cesiumlanguagewriter.LineCountCesiumWriter writer = openLineCountProperty();
			try {
				writer.writeReference(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineCount</code> property as a <code>reference</code> value.  The <code>lineCount</code> property specifies the number of grid lines along each axis.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyName The property on the referenced object.
	 */
	public final void writeLineCountPropertyReference(String identifier, String propertyName) {
		{
			cesiumlanguagewriter.LineCountCesiumWriter writer = openLineCountProperty();
			try {
				writer.writeReference(identifier, propertyName);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineCount</code> property as a <code>reference</code> value.  The <code>lineCount</code> property specifies the number of grid lines along each axis.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyNames The hierarchy of properties to be indexed on the referenced object.
	 */
	public final void writeLineCountPropertyReference(String identifier, String[] propertyNames) {
		{
			cesiumlanguagewriter.LineCountCesiumWriter writer = openLineCountProperty();
			try {
				writer.writeReference(identifier, propertyNames);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  Gets the writer for the <code>lineThickness</code> property.  The returned instance must be opened by calling the  {@link CesiumElementWriter#open} method before it can be used for writing.  The <code>lineThickness</code> property defines the thickness of grid lines along each axis, in pixels.
	

	 */
	public final LineThicknessCesiumWriter getLineThicknessWriter() {
		return m_lineThickness.getValue();
	}

	/**
	 *  
	Opens and returns the writer for the <code>lineThickness</code> property.  The <code>lineThickness</code> property defines the thickness of grid lines along each axis, in pixels.
	

	 */
	public final LineThicknessCesiumWriter openLineThicknessProperty() {
		openIntervalIfNecessary();
		return this.<LineThicknessCesiumWriter> openAndReturn(getLineThicknessWriter());
	}

	/**
	 *  
	Writes a value for the <code>lineThickness</code> property as a <code>cartesian2</code> value.  The <code>lineThickness</code> property specifies the thickness of grid lines along each axis, in pixels.
	
	

	 * @param value The value.
	 */
	public final void writeLineThicknessProperty(Rectangular value) {
		{
			cesiumlanguagewriter.LineThicknessCesiumWriter writer = openLineThicknessProperty();
			try {
				writer.writeCartesian2(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineThickness</code> property as a <code>cartesian2</code> value.  The <code>lineThickness</code> property specifies the thickness of grid lines along each axis, in pixels.
	
	
	

	 * @param x The X component.
	 * @param y The Y component.
	 */
	public final void writeLineThicknessProperty(double x, double y) {
		{
			cesiumlanguagewriter.LineThicknessCesiumWriter writer = openLineThicknessProperty();
			try {
				writer.writeCartesian2(x, y);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineThickness</code> property as a <code>cartesian2</code> value.  The <code>lineThickness</code> property specifies the thickness of grid lines along each axis, in pixels.
	
	
	

	 * @param dates The dates at which the vector is specified.
	 * @param values The values corresponding to each date.
	 */
	public final void writeLineThicknessProperty(List<JulianDate> dates, List<Rectangular> values) {
		{
			cesiumlanguagewriter.LineThicknessCesiumWriter writer = openLineThicknessProperty();
			try {
				writer.writeCartesian2(dates, values);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineThickness</code> property as a <code>cartesian2</code> value.  The <code>lineThickness</code> property specifies the thickness of grid lines along each axis, in pixels.
	
	
	
	
	

	 * @param dates The dates at which the vector is specified.
	 * @param values The values corresponding to each date.
	 * @param startIndex The index of the first element to use in the `values` collection.
	 * @param length The number of elements to use from the `values` collection.
	 */
	public final void writeLineThicknessProperty(List<JulianDate> dates, List<Rectangular> values, int startIndex, int length) {
		{
			cesiumlanguagewriter.LineThicknessCesiumWriter writer = openLineThicknessProperty();
			try {
				writer.writeCartesian2(dates, values, startIndex, length);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineThickness</code> property as a <code>reference</code> value.  The <code>lineThickness</code> property specifies the thickness of grid lines along each axis, in pixels.
	
	

	 * @param value The reference.
	 */
	public final void writeLineThicknessPropertyReference(Reference value) {
		{
			cesiumlanguagewriter.LineThicknessCesiumWriter writer = openLineThicknessProperty();
			try {
				writer.writeReference(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineThickness</code> property as a <code>reference</code> value.  The <code>lineThickness</code> property specifies the thickness of grid lines along each axis, in pixels.
	
	

	 * @param value The earliest date of the interval.
	 */
	public final void writeLineThicknessPropertyReference(String value) {
		{
			cesiumlanguagewriter.LineThicknessCesiumWriter writer = openLineThicknessProperty();
			try {
				writer.writeReference(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineThickness</code> property as a <code>reference</code> value.  The <code>lineThickness</code> property specifies the thickness of grid lines along each axis, in pixels.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyName The property on the referenced object.
	 */
	public final void writeLineThicknessPropertyReference(String identifier, String propertyName) {
		{
			cesiumlanguagewriter.LineThicknessCesiumWriter writer = openLineThicknessProperty();
			try {
				writer.writeReference(identifier, propertyName);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineThickness</code> property as a <code>reference</code> value.  The <code>lineThickness</code> property specifies the thickness of grid lines along each axis, in pixels.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyNames The hierarchy of properties to be indexed on the referenced object.
	 */
	public final void writeLineThicknessPropertyReference(String identifier, String[] propertyNames) {
		{
			cesiumlanguagewriter.LineThicknessCesiumWriter writer = openLineThicknessProperty();
			try {
				writer.writeReference(identifier, propertyNames);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  Gets the writer for the <code>lineOffset</code> property.  The returned instance must be opened by calling the  {@link CesiumElementWriter#open} method before it can be used for writing.  The <code>lineOffset</code> property defines the offset of grid lines along each axis, as a percentage from 0 to 1.
	

	 */
	public final LineOffsetCesiumWriter getLineOffsetWriter() {
		return m_lineOffset.getValue();
	}

	/**
	 *  
	Opens and returns the writer for the <code>lineOffset</code> property.  The <code>lineOffset</code> property defines the offset of grid lines along each axis, as a percentage from 0 to 1.
	

	 */
	public final LineOffsetCesiumWriter openLineOffsetProperty() {
		openIntervalIfNecessary();
		return this.<LineOffsetCesiumWriter> openAndReturn(getLineOffsetWriter());
	}

	/**
	 *  
	Writes a value for the <code>lineOffset</code> property as a <code>cartesian2</code> value.  The <code>lineOffset</code> property specifies the offset of grid lines along each axis, as a percentage from 0 to 1.
	
	

	 * @param value The value.
	 */
	public final void writeLineOffsetProperty(Rectangular value) {
		{
			cesiumlanguagewriter.LineOffsetCesiumWriter writer = openLineOffsetProperty();
			try {
				writer.writeCartesian2(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineOffset</code> property as a <code>cartesian2</code> value.  The <code>lineOffset</code> property specifies the offset of grid lines along each axis, as a percentage from 0 to 1.
	
	
	

	 * @param x The X component.
	 * @param y The Y component.
	 */
	public final void writeLineOffsetProperty(double x, double y) {
		{
			cesiumlanguagewriter.LineOffsetCesiumWriter writer = openLineOffsetProperty();
			try {
				writer.writeCartesian2(x, y);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineOffset</code> property as a <code>cartesian2</code> value.  The <code>lineOffset</code> property specifies the offset of grid lines along each axis, as a percentage from 0 to 1.
	
	
	

	 * @param dates The dates at which the vector is specified.
	 * @param values The values corresponding to each date.
	 */
	public final void writeLineOffsetProperty(List<JulianDate> dates, List<Rectangular> values) {
		{
			cesiumlanguagewriter.LineOffsetCesiumWriter writer = openLineOffsetProperty();
			try {
				writer.writeCartesian2(dates, values);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineOffset</code> property as a <code>cartesian2</code> value.  The <code>lineOffset</code> property specifies the offset of grid lines along each axis, as a percentage from 0 to 1.
	
	
	
	
	

	 * @param dates The dates at which the vector is specified.
	 * @param values The values corresponding to each date.
	 * @param startIndex The index of the first element to use in the `values` collection.
	 * @param length The number of elements to use from the `values` collection.
	 */
	public final void writeLineOffsetProperty(List<JulianDate> dates, List<Rectangular> values, int startIndex, int length) {
		{
			cesiumlanguagewriter.LineOffsetCesiumWriter writer = openLineOffsetProperty();
			try {
				writer.writeCartesian2(dates, values, startIndex, length);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineOffset</code> property as a <code>reference</code> value.  The <code>lineOffset</code> property specifies the offset of grid lines along each axis, as a percentage from 0 to 1.
	
	

	 * @param value The reference.
	 */
	public final void writeLineOffsetPropertyReference(Reference value) {
		{
			cesiumlanguagewriter.LineOffsetCesiumWriter writer = openLineOffsetProperty();
			try {
				writer.writeReference(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineOffset</code> property as a <code>reference</code> value.  The <code>lineOffset</code> property specifies the offset of grid lines along each axis, as a percentage from 0 to 1.
	
	

	 * @param value The earliest date of the interval.
	 */
	public final void writeLineOffsetPropertyReference(String value) {
		{
			cesiumlanguagewriter.LineOffsetCesiumWriter writer = openLineOffsetProperty();
			try {
				writer.writeReference(value);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineOffset</code> property as a <code>reference</code> value.  The <code>lineOffset</code> property specifies the offset of grid lines along each axis, as a percentage from 0 to 1.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyName The property on the referenced object.
	 */
	public final void writeLineOffsetPropertyReference(String identifier, String propertyName) {
		{
			cesiumlanguagewriter.LineOffsetCesiumWriter writer = openLineOffsetProperty();
			try {
				writer.writeReference(identifier, propertyName);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}

	/**
	 *  
	Writes a value for the <code>lineOffset</code> property as a <code>reference</code> value.  The <code>lineOffset</code> property specifies the offset of grid lines along each axis, as a percentage from 0 to 1.
	
	
	

	 * @param identifier The identifier of the object which contains the referenced property.
	 * @param propertyNames The hierarchy of properties to be indexed on the referenced object.
	 */
	public final void writeLineOffsetPropertyReference(String identifier, String[] propertyNames) {
		{
			cesiumlanguagewriter.LineOffsetCesiumWriter writer = openLineOffsetProperty();
			try {
				writer.writeReference(identifier, propertyNames);
			} finally {
				DisposeHelper.dispose(writer);
			}
		}
	}
}
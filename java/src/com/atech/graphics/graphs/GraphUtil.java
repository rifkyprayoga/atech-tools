
package com.atech.graphics.graphs;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.RenderingHints.Key;
import java.util.HashMap;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;

/**
 * This is a replacement for AbstractGraphView using JFreeChart. It contains
 * parts common to all graphs.
 * 
 * @author rumbi
 */
public abstract class GraphUtil //extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = -1579716091265096686L;
    Color backgroundColor = Color.WHITE;
    JFreeChart chart;

    ChartPanel chartPanel;
    protected ATDataAccessAbstract m_da = null; //DataAccess.getInstance();
    protected RenderingHints renderingHints;

    protected I18nControlAbstract m_ic; // = I18nControl.getInstance();
    GraphConfigProperties config = null;
    TimeZone empty_tzi;
    private static Shape shapes[];
    
    public static final int SHAPE_SQUARE = 0;
    public static final int SHAPE_CIRCLE = 1;
    public static final int SHAPE_TRIANGLE_UP = 2;
    public static final int SHAPE_RHOMB = 3;
    public static final int SHAPE_RECTANGLE = 4;
    public static final int SHAPE_TRIANGLE_DOWN = 5;
    public static final int SHAPE_ELIPSE = 6;
    public static final int SHAPE_TRIANGLE_RIGHT = 7;
    public static final int SHAPE_RECTANGLE_UP = 8;
    public static final int SHAPE_TRIANGLE_DOWN_RIGHT = 9;
    
    
    
    
    /**
     * Constructor 
     * 
     * @param da
     */
    public GraphUtil(ATDataAccessAbstract da)
    {
        this.m_da = da;
        this.config = da.getGraphConfigProperties();
        
        
        // removed for now Andy !!!!
        //getRenderingQuality();
        
        initLocal();
        init();
    }

    
    public abstract void initLocal();

    
    private void initTZ()
    {
        TimeZone tz = TimeZone.getDefault();
        //this.empty_tzi = 
        this.empty_tzi = new SimpleTimeZone(0, //tz.getRawOffset(),
                    tz.getID(), 0, 0, 0, 0, 0, 0, 0, 0);        
        
    }

    
    /**
     *  Init
     */
    public void init()
    {
        if (GraphUtil.shapes==null)
        {
            GraphUtil.shapes = DefaultDrawingSupplier.createStandardSeriesShapes();
        }
        
        initTZ();
    }
    
    
    public TimeZone getEmptyTimeZone()
    {
        return this.empty_tzi;
    }
    
    
    public RenderingHints getRenderingHints()
    {
        return this.renderingHints;
    }
    
    /**
     * Set Shape And Color
     * 
     * @param shape
     * @param color
     * @param renderer
     */
    @SuppressWarnings("deprecation")
    public void setShapeAndColor(int shape, Color color, XYLineAndShapeRenderer renderer)
    {
        renderer.setShape(GraphUtil.shapes[shape]);
        renderer.setFillPaint(color);
    }
    
    
    
    /**
     * Gets the current rendering quality settings from the
     * <code>{@link ATDataAccessAbstract}</code>.
     */
    protected void getRenderingQuality()
    {
        HashMap<Key, Object> hintsMap = new HashMap<Key, Object>();
        switch (this.config.getAntiAliasing())
        {
        case 1:
            hintsMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            break;
        case 2:
            hintsMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            break;
        default:
            hintsMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        }

        switch (this.config.getColorRendering())
        {
        case 1:
            hintsMap.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            break;
        case 2:
            hintsMap.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
            break;
        default:
            hintsMap.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
        }

        switch (this.config.getDithering())
        {
        case 1:
            hintsMap.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
            break;
        case 2:
            hintsMap.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            break;
        default:
            hintsMap.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DEFAULT);
        }

        switch (this.config.getFractionalMetrics())
        {
        case 1:
            hintsMap.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            break;
        case 2:
            hintsMap.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            break;
        default:
            hintsMap.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
        }

        switch (this.config.getInterpolation())
        {
        case 1:
            hintsMap.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            break;
        case 2:
            hintsMap.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            break;
        default:
            hintsMap.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        }

        switch (this.config.getRendering())
        {
        case 1:
            hintsMap.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            break;
        case 2:
            hintsMap.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
            break;
        default:
            hintsMap.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
        }

        switch (this.config.getTextAntiAliasing())
        {
        case 1:
            hintsMap.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            break;
        case 2:
            hintsMap.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            break;
        default:
            hintsMap.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        }

        renderingHints = new RenderingHints(hintsMap);
    }


    
    public Color getColor(int key)
    {
        return new Color(key);
    }
    
    

}

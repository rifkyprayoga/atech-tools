package com.atech.graphics.graphs;

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.DateRange;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.data.TimeZoneUtil;

// TODO: Auto-generated Javadoc
/**
 * This is a replacement for AbstractGraphView using JFreeChart. It contains
 * parts common to all graphs.
 * 
 * @author rumbi
 */
public abstract class GraphUtil // extends JPanel
{

    private static final long serialVersionUID = -1579716091265096686L;

    private static RenderingHints renderingHintsDefault;

    /**
     * The background color.
     */
    Color backgroundColor = Color.WHITE;

    /**
     * The chart.
     */
    JFreeChart chart;

    /**
     * The chart panel.
     */
    ChartPanel chartPanel;

    /**
     * The dataAccess.
     */
    protected ATDataAccessAbstract m_da = null; // DataAccess.getInstance();

    /**
     * The rendering hints.
     */
    protected RenderingHints renderingHints;

    /**
     * The m_ic.
     */
    // protected I18nControlAbstract m_ic; // = I18nControl.getInstance();

    // private static ATDataAccessAbstract dataAccess;

    protected I18nControlAbstract i18nControl;

    /**
     * The config.
     */
    GraphConfigProperties config = null;

    /**
     * The empty_tzi.
     */
    TimeZone empty_tzi;
    private static Shape shapes[];

    /**
     * The Constant SHAPE_SQUARE.
     */
    public static final int SHAPE_SQUARE = 0;

    /**
     * The Constant SHAPE_CIRCLE.
     */
    public static final int SHAPE_CIRCLE = 1;

    /**
     * The Constant SHAPE_TRIANGLE_UP.
     */
    public static final int SHAPE_TRIANGLE_UP = 2;

    /**
     * The Constant SHAPE_RHOMB.
     */
    public static final int SHAPE_RHOMB = 3;

    /**
     * The Constant SHAPE_RECTANGLE.
     */
    public static final int SHAPE_RECTANGLE = 4;

    /**
     * The Constant SHAPE_TRIANGLE_DOWN.
     */
    public static final int SHAPE_TRIANGLE_DOWN = 5;

    /**
     * The Constant SHAPE_ELIPSE.
     */
    public static final int SHAPE_ELIPSE = 6;

    /**
     * The Constant SHAPE_TRIANGLE_RIGHT.
     */
    public static final int SHAPE_TRIANGLE_RIGHT = 7;

    /**
     * The Constant SHAPE_RECTANGLE_UP.
     */
    public static final int SHAPE_RECTANGLE_UP = 8;

    /**
     * The Constant SHAPE_TRIANGLE_DOWN_RIGHT.
     */
    public static final int SHAPE_TRIANGLE_DOWN_RIGHT = 9;


    /**
     * Constructor 
     * 
     * @param da
     */
    public GraphUtil(ATDataAccessAbstract da)
    {
        this.m_da = da;
        this.i18nControl = da.getI18nControlInstance();
        this.config = da.getGraphConfigProperties();

        // removed for now Andy !!!!
        // getRenderingQuality();

        initLocal();
        init();
    }


    /**
     * Inits the local.
     */
    public abstract void initLocal();


    private void initTZ()
    {
        TimeZone tz = TimeZone.getDefault();
        // this.empty_tzi =
        this.empty_tzi = new SimpleTimeZone(0, // tz.getRawOffset(),
                tz.getID(), 0, 0, 0, 0, 0, 0, 0, 0);

    }


    /**
     *  Init
     */
    public void init()
    {
        if (GraphUtil.shapes == null)
        {
            GraphUtil.shapes = DefaultDrawingSupplier.createStandardSeriesShapes();
        }

        initTZ();
    }


    /**
     * Gets the empty time zone.
     * 
     * @return the empty time zone
     */
    public TimeZone getEmptyTimeZone()
    {
        return this.empty_tzi;
    }


    /**
     * Gets the rendering hints.
     * 
     * @return the rendering hints
     */
    public RenderingHints getRenderingHints()
    {
        return this.renderingHints;
    }


    /**
     * Gets the rendering hints.
     *
     * @return the rendering hints
     */
    public static RenderingHints getRenderingHintsDefault()
    {
        if (renderingHintsDefault == null)
            createDefaultRenderingHints();

        return renderingHintsDefault;
    }


    /**
     * Set Shape And Color
     * 
     * @param shape
     * @param color
     * @param renderer
     */
    public void setShapeAndColor(int shape, Color color, XYLineAndShapeRenderer renderer)
    {
        renderer.setBaseShape(GraphUtil.shapes[shape]);
        renderer.setBaseFillPaint(color);
    }


    /**
     * Set Shape And Color
     *
     * @param shape
     * @param color
     * @param renderer
     */
    public void setShapeAndColor(int shape, Color color, XYBarRenderer renderer)
    {
        renderer.setBaseShape(GraphUtil.shapes[shape]);
        renderer.setBaseFillPaint(color);
    }


    public static Shape getShape(int shapeIndex)
    {
        createShapesIfRequired();

        return shapes[shapeIndex];
    }


    public static void createShapesIfRequired()
    {
        if (shapes == null)
        {
            shapes = DefaultDrawingSupplier.createStandardSeriesShapes();
        }
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


    public static void createDefaultRenderingHints()
    {
        HashMap<Key, Object> hintsMap = new HashMap<Key, Object>();

        hintsMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        hintsMap.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);

        hintsMap.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DEFAULT);
        hintsMap.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);

        hintsMap.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        hintsMap.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
        hintsMap.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);

        renderingHintsDefault = new RenderingHints(hintsMap);
    }


    /**
     * Gets the color.
     * 
     * @param key the key
     * 
     * @return the color
     */
    public Color getColor(int key)
    {
        return new Color(key);
    }


    // public static void setDataAccess(ATDataAccessAbstract dataAccess)
    // {
    // dataAccess = dataAccess;
    // i18nControl = dataAccess.getI18nControlInstance();
    // }

    public DateAxis prepareDateAxis(DateAxis dateAxis, DateAxisSupportInterface dateAxisSupportInterface)
    {
        return prepareDateAxis(dateAxis, dateAxisSupportInterface.getFrom(), dateAxisSupportInterface.getTill());
    }


    public DateAxis prepareDateAxis(DateAxis dateAxis, GregorianCalendar calendarToday)
    {
        return prepareDateAxis(dateAxis, prepareFromCalendar(calendarToday), prepareTillCalendar(calendarToday));
    }


    public static DateAxis prepareDateAxis(DateAxis dateAxis, GregorianCalendar calendarToday,
            I18nControlAbstract i18nControl)
    {
        return prepareDateAxis(dateAxis, prepareFromCalendar(calendarToday), prepareTillCalendar(calendarToday),
            i18nControl, null);
    }


    public static DateAxis prepareDateAxis(DateAxis dateAxis, GregorianCalendar calendarToday,
            I18nControlAbstract i18nControl, String axisLabel)
    {
        return prepareDateAxis(dateAxis, prepareFromCalendar(calendarToday), prepareTillCalendar(calendarToday),
            i18nControl, axisLabel);
    }


    public DateAxis prepareDateAxis(DateAxis dateAxis, GregorianCalendar from, GregorianCalendar till)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(i18nControl.getMessage("FORMAT_DATE_HOURS"));
        sdf.setTimeZone(TimeZoneUtil.getInstance().getEmptyTimeZone());

        dateAxis.setDateFormatOverride(sdf);
        dateAxis.setAutoRange(false);
        dateAxis.setRange(from.getTime(), till.getTime());
        dateAxis.setDefaultAutoRange(new DateRange(from.getTime(), till.getTime()));
        dateAxis.setTimeZone(TimeZoneUtil.getInstance().getEmptyTimeZone());

        return dateAxis;
    }


    public static DateAxis prepareDateAxis(DateAxis dateAxis, GregorianCalendar from, GregorianCalendar till,
            I18nControlAbstract i18nControl, String axisLabel)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(i18nControl.getMessage("FORMAT_DATE_HOURS"));
        sdf.setTimeZone(TimeZoneUtil.getInstance().getEmptyTimeZone());

        dateAxis.setDateFormatOverride(sdf);
        dateAxis.setAutoRange(false);
        dateAxis.setRange(from.getTime(), till.getTime());
        dateAxis.setDefaultAutoRange(new DateRange(from.getTime(), till.getTime()));
        dateAxis.setTimeZone(TimeZoneUtil.getInstance().getEmptyTimeZone());

        if (axisLabel != null)
        {
            dateAxis.setLabel(i18nControl.getMessage("AXIS_TIME_LABEL"));
            dateAxis.setLabelFont(dateAxis.getLabelFont().deriveFont(Font.BOLD));
        }

        // dateAxis.se

        return dateAxis;
    }


    public static GregorianCalendar prepareFromCalendar(GregorianCalendar calendar)
    {
        GregorianCalendar fromDate = (GregorianCalendar) calendar.clone();

        fromDate.set(Calendar.HOUR_OF_DAY, 0);
        fromDate.set(Calendar.MINUTE, 0);
        fromDate.set(Calendar.SECOND, 0);
        fromDate.setTimeZone(TimeZoneUtil.getInstance().getEmptyTimeZone());

        return fromDate;
    }


    public static GregorianCalendar prepareTillCalendar(GregorianCalendar calendar)
    {
        GregorianCalendar toDate = (GregorianCalendar) calendar.clone();
        toDate.set(Calendar.HOUR_OF_DAY, 23);
        toDate.set(Calendar.MINUTE, 59);
        toDate.set(Calendar.SECOND, 59);
        toDate.setTimeZone(TimeZoneUtil.getInstance().getEmptyTimeZone());

        return toDate;
    }

}

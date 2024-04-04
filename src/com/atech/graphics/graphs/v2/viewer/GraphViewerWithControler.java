package com.atech.graphics.graphs.v2.viewer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.*;

import org.apache.commons.collections4.CollectionUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.RangeType;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.data.NotImplementedException;
import com.atech.graphics.components.DateComponent;
import com.atech.graphics.graphs.GraphUtil;
import com.atech.graphics.graphs.v2.data.GraphDefinitionDto;
import com.atech.graphics.graphs.v2.data.GraphMarkerDto;
import com.atech.graphics.graphs.v2.data.GraphTimeDataCollection;
import com.atech.graphics.graphs.v2.data.GraphTimeDataDto;
import com.atech.graphics.graphs.v2.defs.DataRangeValue;
import com.atech.graphics.graphs.v2.defs.PlotDataType;
import com.atech.graphics.graphs.v2.defs.PlotType;
import com.atech.graphics.layout.TableLayoutUtil;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;
import com.atech.utils.data.ATechDate;
import com.atech.utils.data.ATechDateType;
import com.atech.utils.graphics.ColorUtils;
import info.clearthought.layout.TableLayout;

/**
 * Created by andy on 19.12.15.
 */

/**
 * This is temporary solution, something between framework v1 and v2. It works partly with
 * defintion (but not yet with Xml one), and so far it needs to be partitaly implemented by
 * source module. At later time we will probably just extended to use
 *
 */

/**
 * NEW V2 Graph
 */
public class GraphViewerWithControler extends JDialog implements ActionListener, HelpCapable
{

    private static final long serialVersionUID = -5774789654269000419L;
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphViewerWithControler.class);

    protected GraphDefinitionDto graphDefintionDto;
    protected I18nControlAbstract i18nControlAbstract;
    protected ATDataAccessAbstract dataAccess;

    DateComponent dateComponentFrom, dateComponentTill;
    GregorianCalendar lastDate;
    GraphTimeDataCollection dataCollection;
    JPanel graphPanel;
    ChartPanel chartPanel;
    JFreeChart chart;
    JDialog currentDialog;

    XYSeriesCollection xySeriesCollection;
    TimeSeriesCollection timeSeriesCollection;

    protected Color backgroundColor = new Color(197, 230, 241);

    protected Dimension newSize;
    protected JButton helpButton;


    public GraphViewerWithControler(JFrame dfra, final ATDataAccessAbstract dataAccess, GraphDefinitionDto defintionDto,
            boolean initFinished)
    {
        super(dfra, "", true);
        this.currentDialog = this;
        this.dataAccess = dataAccess;
        this.dataAccess.addComponent(this);
        this.graphDefintionDto = defintionDto;
        this.i18nControlAbstract = dataAccess.getI18nControlInstance();

        lastDate = new GregorianCalendar();

        this.addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                dataAccess.removeComponent(currentDialog);
            }
        });

        // FIXME remove
        // lastDate.add(Calendar.MONTH, -3);

        if (initFinished)
            finishInit();
    }


    public void finishInit()
    {
        initGui();
        readData();
        redrawGraph();

        if (newSize != null)
        {
            // System.out.println("New size: " + newSize);
            this.setSize(newSize);
            this.dataAccess.centerJDialog(this);
        }

        this.setVisible(true);
    }


    protected void readData()
    {
        LOGGER.debug("read Data [from=" + dataAccess.getGregorianCalendarAsString(dateComponentFrom.getDateObject())
                + ", till=" + dataAccess.getGregorianCalendarAsString(dateComponentTill.getDateObject()) + "]");

        this.dataCollection = dataAccess.getGraphDbDataRetriever().getGraphTimeData(dateComponentFrom.getDateObject(),
            dateComponentTill.getDateObject(), this.graphDefintionDto);

        LOGGER.debug("Data collection: " + this.dataCollection);
    }


    protected void redrawGraph()
    {
        // System.out.println("redraw Graph");

        if (chartPanel != null)
        {
            graphPanel.remove(chartPanel);
        }

        // System.out.println("GraphPanel: " + graphPanel.getComponents());

        // graphPanel.removeAll();

        // test 2
        graphPanel.setLayout(new BorderLayout());

        // graphPanel.add(createDailyChartForReport());

        // graphPanel.removeComponentListener(null);//

        chartPanel = new ChartPanel(chart = createDailyChartForReport(), graphPanel.getWidth(), graphPanel.getHeight(),
                120, 100, 1024, 768, true, true, true, true, true, true);

        // public ChartPanel(JFreeChart chart, int width, int height, int
        // minimumDrawWidth, int minimumDrawHeight,
        // int maximumDrawWidth, int maximumDrawHeight,
        // boolean useBuffer, boolean properties, boolean save, boolean print,
        // boolean zoom, boolean tooltips) {

        // System.out.println("Ccc: " + chart.getPlot());

        chartPanel.setDoubleBuffered(true);

        graphPanel.add(chartPanel, BorderLayout.CENTER);

    }


    private void initGui()
    {
        ATSwingUtils.initLibrary();

        JPanel mainPanel = new JPanel();
        // mainPanel.setBackground(Color.red);
        // mainPanel.setBounds(0, 0, 640, 480);

        this.setTitle(getMessage(graphDefintionDto.getTitle()));

        double sizesMain[][] = { { TableLayout.FILL }, //
                                 { TableLayout.FILL } };

        this.getContentPane().setLayout(new TableLayout(sizesMain));
        this.getContentPane().add(mainPanel, "0, 0");

        mainPanel.setLayout(new TableLayout(TableLayoutUtil.createSizesForVerticalLayout(TableLayout.FILL, 130)));

        JPanel controlerPanel = new JPanel();
        // controlerPanel.setBackground(Color.green);

        mainPanel.add(graphPanel = new JPanel(), "0, 0");
        mainPanel.add(controlerPanel, "0, 1");

        controlerPanel.setLayout(TableLayoutUtil.createHorizontalLayout(TableLayout.FILL, 0.33d, 0.33d));

        controlerPanel.add(createDateControler(), "0, 0");
        controlerPanel.add(createSwitchControler(), "1, 0");
        controlerPanel.add(createOtherControler(), "2, 0");

        this.setSize(this.graphDefintionDto.getSize());

        this.dataAccess.centerJDialog(this);
    }


    private JPanel createSwitchControler()
    {
        JPanel controlerSwitchPanel = new JPanel();
        controlerSwitchPanel.setBackground(Color.magenta);

        controlerSwitchPanel.setLayout(TableLayoutUtil.createVerticalLayout(TableLayout.FILL, 0.33d, 0.33d));
        controlerSwitchPanel.add(new JPanel(), "0, 0");

        JPanel p2 = new JPanel();
        p2.setLayout(TableLayoutUtil.createHorizontalLayout(0.07d, 0.33d, TableLayout.FILL, 0.33d, 0.07d));

        createButton(null, "previous_time", "arrow_left_blue.png", p2, "1, 0");
        createButton(null, "next_time", "arrow_right_blue.png", p2, "3, 0");

        // 'arrow_left_blue.png''arrow_left_blue.png'

        // p2.add(b, "1, 0");
        // p2.add(createButton(">", "next_time", null), "3, 0");

        controlerSwitchPanel.add(p2, "0, 1");

        controlerSwitchPanel.add(new JPanel(), "0, 2");

        return controlerSwitchPanel;
    }


    private JButton createButton(String text, String actionCommand, String image, JPanel panel,
            String layoutConstraints)
    {
        // return ATSwingUtils.getButton(text, ATSwingUtils.FONT_NORMAL_BOLD_P2,
        // image, actionCommand, this, dataAccess);

        return ATSwingUtils.getButton(text, null, null, null, null, panel, layoutConstraints,
            ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD_P2), image, actionCommand, this, dataAccess,
            new int[] { 20, 20 }, panel);

    }


    private JPanel createOtherControler()
    {
        JPanel controlerOtherPanel = new JPanel();
        controlerOtherPanel.setLayout(TableLayoutUtil.createVerticalLayout(TableLayout.FILL, 40, 5));

        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(TableLayoutUtil.createHorizontalLayout(0.01, 0.44, 0.01, 0.43, 0.01));

        JButton button = new JButton();
        button.setText("Help");

        buttonPanel.add(helpButton = ATSwingUtils.createHelpButton(this, dataAccess), "1, 0");
        helpButton.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD_P2));
        // buttonPanel.add(createButton(i18nControlAbstract.getMessage("GRAPH_CLOSE"),
        // "close", null), "3, 0");
        createButton(i18nControlAbstract.getMessage("GRAPH_CLOSE"), "close", null, buttonPanel, "3, 0");

        controlerOtherPanel.add(buttonPanel, "0, 1");

        if (this.graphDefintionDto.getHelpId() != null)
        {
            this.helpButton.setEnabled(true);
        }
        else
        {
            this.helpButton.setEnabled(false);
        }

        return controlerOtherPanel;
    }


    private JPanel createDateControler()
    {
        JPanel controlerDatePanel = new JPanel();

        DateComponent.LEFT_MARGIN = 5;

        controlerDatePanel
                .setLayout(new TableLayout(TableLayoutUtil.createSizesForVerticalLayout(0.25d, 0.25d, 0.25d, 0.25d)));

        ATSwingUtils.getLabel(" " + getMessage("DTCMP_FROM"), controlerDatePanel, "0, 0",
            ATSwingUtils.FONT_NORMAL_BOLD);

        dateComponentFrom = new DateComponent(1800, 5, this.dataAccess);
        dateComponentFrom.setEnabled(true);
        controlerDatePanel.add(dateComponentFrom, "0, 1");

        ATSwingUtils.getLabel(" " + getMessage("DTCMP_TILL"), controlerDatePanel, "0, 2",
            ATSwingUtils.FONT_NORMAL_BOLD);

        dateComponentTill = new DateComponent(1800, 5, this.dataAccess);

        dateComponentTill.setEnabled(false);
        controlerDatePanel.add(dateComponentTill, "0, 3");

        setRangeOnComponent(this.lastDate);

        dateComponentFrom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setRangeOnTill(ATechDate.getGregorianCalendar(ATechDateType.DateOnly, dateComponentFrom.getDate()));
            }
        });


        return controlerDatePanel;
    }


    private void setRangeOnComponent(GregorianCalendar calendar)
    {
        dateComponentTill.setDate(calendar);

        DataRangeValue dataRangeValue = graphDefintionDto.getDataRangeValue();

        // FIXME remove
        // dataRangeValue = DataRangeValue.Month;

        if (dataRangeValue == DataRangeValue.Day)
        {
            dateComponentFrom.setDate(calendar);
        }
        else
        {
            GregorianCalendar gc = (GregorianCalendar) calendar.clone();
            gc.add(dataRangeValue.getCalendarType(), //
                -1 * dataRangeValue.getValue());
            dateComponentFrom.setDate(gc);
        }
    }


    private void setRangeOnTill(GregorianCalendar calendar)
    {
        DataRangeValue dataRangeValue = graphDefintionDto.getDataRangeValue();

        //System.out.println("dataRangeValue: " + dataRangeValue);
        //System.out.println("Before: " + ATDataAccessAbstract.getGregorianCalendarAsDateString(gc));

        if (dataRangeValue == DataRangeValue.Day)
        {
            dateComponentTill.setDate(calendar);

            this.lastDate = calendar;
            dateComponentTill.setDate(calendar);

            redrawGraphFull();
        }
        else
        {
            GregorianCalendar gc = (GregorianCalendar) calendar.clone();
            gc.add(dataRangeValue.getCalendarType(), //
                    1 * dataRangeValue.getValue());

            //System.out.println("After: " + ATDataAccessAbstract.getGregorianCalendarAsDateString(gc));

            this.lastDate = gc;
            dateComponentTill.setDate(gc);

            redrawGraphFull();
        }
    }



    public String getMessage(String key)
    {
        if (i18nControlAbstract == null)
            return key;
        else
            return i18nControlAbstract.getMessage(key);
    }


    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();

        if (action.contains("previous_time"))
        {
            changeTime(-1);
        }
        else if (action.contains("next_time"))
        {
            changeTime(1);
        }
        else if (action.contains("close"))
        {
            this.dispose();
        }
        else
        {
            System.out.println("Unknown action: " + action);
        }

    }


    private void changeTime(int i)
    {
        // System.out.println("Change time: " + i);

        GregorianCalendar newLastDate = (GregorianCalendar) lastDate.clone();

        DataRangeValue dataRangeValue = graphDefintionDto.getDataRangeValue();

        newLastDate.add(dataRangeValue.getCalendarType(), i);

        if (!isSameDay(newLastDate))
        {
            this.lastDate = newLastDate;
            this.setRangeOnComponent(this.lastDate);
            redrawGraphFull();
        }

    }

    private void redrawGraphFull() {
        this.readData();
        this.redrawGraph();

        // this.chartPanel.repaint();
        // this.graphPanel.repaint();

        // this.chartPanel.invalidate();
        this.chartPanel.updateUI();

        // chart.fireChartChanged();
        // chart.plotChanged());

    }


    private boolean isSameDay(GregorianCalendar newDate)
    {
        GregorianCalendar today = new GregorianCalendar();
        today.add(Calendar.DAY_OF_YEAR, 1);

        return ((today.get(Calendar.DAY_OF_YEAR) == newDate.get(Calendar.DAY_OF_YEAR))
                && (today.get(Calendar.YEAR) == newDate.get(Calendar.YEAR)));
    }


    public JFreeChart createDailyChartForReport()
    {
        I18nControlAbstract m_ic = this.i18nControlAbstract;

        JFreeChart chart = createCorrectChart();

        this.setPlot(chart);
        return chart;
    }


    private JFreeChart createCorrectChart()
    {
        JFreeChart chart;

        if (this.graphDefintionDto.getPlotType().getPlotDataType() == PlotDataType.XYDataset)
        {
            xySeriesCollection = new XYSeriesCollection();

            createSeriesFromCollection(xySeriesCollection);

            if (this.graphDefintionDto.getPlotType() == PlotType.Scatter)
            {

                chart = ChartFactory.createScatterPlot(null, i18nControlAbstract.getMessage("AXIS_TIME_LABEL"),
                    String.format(i18nControlAbstract.getMessage("AXIS_VALUE_LABEL"), "unitLabel"), xySeriesCollection,
                    PlotOrientation.VERTICAL, false, false, false);
            }
            else
            {
                throw new NotImplementedException(this.getClass(), "Plot Types except Scatter not supported.");
            }
        }
        else if (this.graphDefintionDto.getPlotType().getPlotDataType() == PlotDataType.TimeDataset)
        {
            timeSeriesCollection = new TimeSeriesCollection();

            createSeriesFromCollection(timeSeriesCollection);

            if (this.graphDefintionDto.getPlotType() == PlotType.ScatterTime)
            {
                chart = ChartFactory.createScatterPlot(null, i18nControlAbstract.getMessage("AXIS_TIME_LABEL"),
                    String.format(i18nControlAbstract.getMessage("AXIS_VALUE_LABEL"),
                        this.graphDefintionDto.getValueType().getUnit()),
                    timeSeriesCollection, PlotOrientation.VERTICAL, true, true, false);
            }
            else
            {
                throw new NotImplementedException(this.getClass(), "Plot Types except Scatter not supported.");
            }
        }

        else
        {
            throw new NotImplementedException(this.getClass(), "Data Types except XYDataset is not supported.");
        }

        return chart;

    }


    public void createSeriesFromCollection(TimeSeriesCollection collection)
    {
        for (Long dateKey : this.dataCollection.keySet())
        {
            ATechDate atd = new ATechDate(ATechDateType.DateOnly, dateKey);

            String dateString = atd.getDateString();

            TimeSeries ts = new TimeSeries(dateString, Minute.class);

            for (GraphTimeDataDto entry : this.dataCollection.get(dateKey))
            {
                if (isNotAllowedValue(entry.getValue()))
                {
                    continue;
                }

                ts.addOrUpdate(getMinute(entry.getDateTime()), convertDisplayValue(entry.getValue()));
            }
            collection.addSeries(ts);
        }

    }


    private boolean isNotAllowedValue(double value)
    {
        if ((this.graphDefintionDto.getMinimalAllowedValue() != null)
                && (value < this.graphDefintionDto.getMinimalAllowedValue()))
        {
            return true;
        }

        return ((this.graphDefintionDto.getMaximalAllowedValue() != null)
                && (value > this.graphDefintionDto.getMaximalAllowedValue()));
    }


    public void createSeriesFromCollection(XYSeriesCollection collection)
    {
        for (Long dateKey : this.dataCollection.keySet())
        {
            XYSeries bgReadingsSeries = new XYSeries(
                    dataAccess.getI18nControlInstance().getMessage("CGMS_DEVICE_SHORT"), true, true);

            for (GraphTimeDataDto entry : this.dataCollection.get(dateKey))
            {
                if (isNotAllowedValue(entry.getValue()))
                {
                    continue;
                }

                bgReadingsSeries.add(
                    ATechDate.getGregorianCalendar(ATechDateType.DateAndTimeSec, entry.getDateTime()).getTimeInMillis(),
                    convertDisplayValue(entry.getValue())); //
            }

            collection.addSeries(bgReadingsSeries);
        }

    }


    private Minute getMinute(long dateTime)
    {
        GregorianCalendar gc = ATechDate.getGregorianCalendar(ATechDateType.DateAndTimeSec, dateTime);

        return new Minute(gc.get(Calendar.MINUTE), gc.get(Calendar.HOUR_OF_DAY), //
                this.lastDate.get(Calendar.DAY_OF_MONTH), //
                this.lastDate.get(Calendar.MONTH) + 1, //
                this.lastDate.get(Calendar.YEAR));
    }


    /**
     * If display value can be converted, you need to override this method, with correct logic
     *
     * @param value input value
     * @return converterd value (default returns same value)
     */
    protected double convertDisplayValue(double value)
    {
        return value;
    }


    /**
     * Set Plot
     *
     * @param chart JFreeChart instance
     */
    public void setPlot(JFreeChart chart)
    {
        if (graphDefintionDto.getPlotType().getPlotDataType() == PlotDataType.XYDataset)
        {
            XYPlot plot = chart.getXYPlot();

            XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) plot.getRenderer();

            DateAxis dateAxis;

            plot.setRangeGridlinePaint(Color.darkGray);
            plot.setDomainGridlinePaint(Color.gray);

            RenderingHints rh = GraphUtil.getRenderingHintsDefault();

            if (rh != null)
            {
                chart.setRenderingHints(rh);
            }

            chart.setBorderVisible(false);

            plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
            plot.mapDatasetToRangeAxis(1, 1);

            plot.setRangeGridlinesVisible(true);
            plot.setDomainGridlinesVisible(true);

            System.out.println("XYSeries Count: " + this.xySeriesCollection.getSeriesCount());

            chart.setBackgroundPaint(Color.white);
            plot.setBackgroundPaint(backgroundColor);

            setColorsOnRenderer(xyLineAndShapeRenderer, this.xySeriesCollection.getSeriesCount());

            xyLineAndShapeRenderer.setBasePaint(Color.white);

            dateAxis = GraphUtil.prepareDateAxis(new DateAxis(), this.lastDate, this.i18nControlAbstract);
            plot.setDomainAxis(dateAxis);

            setRangeAxis(plot);
            addMarkers(plot);

        }
        else if (graphDefintionDto.getPlotType().getPlotDataType() == PlotDataType.TimeDataset)
        {
            XYPlot plot = chart.getXYPlot();

            XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) plot.getRenderer();

            DateAxis dateAxis;

            plot.setRangeGridlinePaint(Color.darkGray);
            plot.setDomainGridlinePaint(Color.gray);

            RenderingHints rh = GraphUtil.getRenderingHintsDefault();

            if (rh != null)
            {
                chart.setRenderingHints(rh);
            }

            chart.setBorderVisible(false);

            plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
            plot.mapDatasetToRangeAxis(1, 1);
            plot.setRangeGridlinesVisible(true);
            plot.setDomainGridlinesVisible(true);

            chart.setBackgroundPaint(Color.white);
            plot.setBackgroundPaint(backgroundColor);
            setColorsOnRenderer(xyLineAndShapeRenderer, this.timeSeriesCollection.getSeriesCount());
            xyLineAndShapeRenderer.setBasePaint(Color.white);

            dateAxis = GraphUtil.prepareDateAxis(new DateAxis(), this.lastDate, this.i18nControlAbstract,
                "AXIS_TIME_LABEL");
            plot.setDomainAxis(dateAxis);

            xyLineAndShapeRenderer.setBaseToolTipGenerator( //
                new StandardXYToolTipGenerator("{0}: {1} - {2} " + this.graphDefintionDto.getValueType().getUnit(),
                        new SimpleDateFormat(i18nControlAbstract.getMessage("FORMAT_DATE_HOURS")),
                        new DecimalFormat(this.graphDefintionDto.getValueType().getFormatForValue())));

            setRangeAxis(plot);
            addMarkers(plot);

        }
        else
        {
            throw new NotImplementedException(this.getClass(), "Data Types except XYDataset is not supported.");
        }

    }


    private void setColorsOnRenderer(XYLineAndShapeRenderer renderer, int collectionItemsCount)
    {
        // long start = System.currentTimeMillis();
        Color[] colors = ColorUtils.generateVisuallyDistinctColors(collectionItemsCount + 3, .9f, .1f);

        // long d = System.currentTimeMillis() - start;

        // System.out.println("Colors generated: " + d + " ms.");

        int colorIndex = 0;

        for (int i = 0; i < collectionItemsCount; i++)
        {
            while (isSameColorAsInMarker(colors[colorIndex]))
            {
                // System.out.println("Same color:");
                colorIndex++;
            }

            renderer.setSeriesPaint(i, colors[colorIndex]);
            renderer.setSeriesShapesVisible(i, false);
            renderer.setSeriesLinesVisible(i, true);

            colorIndex++;
        }

    }


    private boolean isSameColorAsInMarker(Color color)
    {
        List<GraphMarkerDto> markers = this.graphDefintionDto.getMarkers();

        if (CollectionUtils.isNotEmpty(markers))
        {
            for (GraphMarkerDto marker : markers)
            {
                if (marker.getColor() == color)
                {
                    return true;
                }
            }
        }

        if (backgroundColor == color)
        {
            return true;
        }

        return false;
    }


    private void setRangeAxis(XYPlot plot)
    {
        NumberAxis bgAxis = (NumberAxis) plot.getRangeAxis();
        bgAxis.setAutoRangeIncludesZero(true);
        bgAxis.setAutoRange(false);
        bgAxis.setRangeType(RangeType.POSITIVE);

        if (this.graphDefintionDto.getMinimalValue() != null && this.graphDefintionDto.getMaximalValue() != null)
        {
            bgAxis.setRange(convertDisplayValue(this.graphDefintionDto.getMinimalValue()),
                convertDisplayValue(this.graphDefintionDto.getMaximalValue()));
        }
    }


    private void addMarkers(XYPlot plot)
    {
        // markers
        List<GraphMarkerDto> markers = this.graphDefintionDto.getMarkers();

        if (CollectionUtils.isNotEmpty(markers))
        {
            for (GraphMarkerDto marker : markers)
            {
                ValueMarker valueMarker = new ValueMarker(convertDisplayValue(marker.getValue()));
                valueMarker.setPaint(marker.getColor());

                plot.addRangeMarker(valueMarker);
            }
        }
    }


    public String getHelpId()
    {
        return this.graphDefintionDto.getHelpId();
    }


    public JButton getHelpButton()
    {
        return this.helpButton;
    }


    public Component getComponent()
    {
        return this;
    }
}

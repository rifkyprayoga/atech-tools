package com.atech.print.engine;

public interface PrintRequester
{

    void setErrorMessages(String errorMessage, String errorMessageTip);

    /**
     * Get Pdf Viewer (path to software)
     * 
     * @return path with executable for External PdfViewer 
     */
    String getExternalPdfViewer();

    /**
     * Get Pdf Viewer parameters. If you want name of file we are reading in this
     * parameters, you need to add %PDF_FILE% variable into string. This one is
     * then resolved.
     * 
     * @return 
     */
    String getExternalPdfViewerParameters();

    /**
     * Is External PdfViewer Activated
     * 
     * Per default we use internal PdfViewer, but user can also use external PdfViewer if he wants.
     * 
     * @return 
     */
    boolean isExternalPdfViewerActivated();

    /**
     * Disable setting of Look And Feel For Internal Pdf Viewer.
     * 
     * @return
     */
    public abstract boolean disableLookAndFeelSettingForInternalPdfViewer();

}

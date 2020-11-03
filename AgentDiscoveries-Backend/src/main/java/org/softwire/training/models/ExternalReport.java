package org.softwire.training.models;

/**
 * A report to be sent to the external API
 */
public class ExternalReport {

    private String callSign;
    private String reportBody;
    private String reportTitle;

    public ExternalReport(String callSign, String reportBody) {
        this.callSign = callSign;
        this.reportBody = reportBody;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setAgentId(String callSign) {
        this.callSign = callSign;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle=reportTitle;
    }

    public String getReportBody() {
        return reportBody;
    }

    public void setReportBody(String reportBody) {
        this.reportBody=reportBody;
    }

}

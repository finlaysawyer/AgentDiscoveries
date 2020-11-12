package org.softwire.training.models;

import lombok.Getter;
import lombok.Setter;

/**
 * A report to be sent to the external API
 */
@Getter
@Setter
public class ExternalReport {

    private String callSign;
    private String reportBody;
    private String reportTitle;

    public ExternalReport(String callSign, String reportBody) {
        this.callSign = callSign;
        this.reportBody = reportBody;
    }


}

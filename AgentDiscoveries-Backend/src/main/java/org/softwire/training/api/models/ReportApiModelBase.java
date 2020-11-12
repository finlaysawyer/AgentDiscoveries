package org.softwire.training.api.models;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ReportApiModelBase {

    private int reportId;
    private int status;
    private ZonedDateTime reportTime;
    private String reportTitle;
    private String reportBody;
    private int agentId;

}

package org.softwire.training.api.models;

import lombok.Getter;
import lombok.Setter;

/**
 * The LocationStatusReportApiModel is a version of the LocationStatusReport storage model.
 *
 * This version uses a zoned date time for the report time to allow the offset to be specified by API clients.
 */
@Getter @Setter
public class LocationStatusReportApiModel extends ReportApiModelBase {

    private int locationId;

}

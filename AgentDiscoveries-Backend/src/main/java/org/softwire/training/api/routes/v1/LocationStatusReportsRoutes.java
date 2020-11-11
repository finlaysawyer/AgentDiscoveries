package org.softwire.training.api.routes.v1;

import org.softwire.training.api.core.PermissionsVerifier;
import org.softwire.training.api.models.ErrorCode;
import org.softwire.training.api.models.FailedRequestException;
import org.softwire.training.api.models.LocationStatusReportApiModel;
import org.softwire.training.db.daos.LocationReportsDao;
import org.softwire.training.db.daos.LocationsDao;
import org.softwire.training.db.daos.UsersDao;
import org.softwire.training.db.daos.searchcriteria.*;
import org.softwire.training.models.Location;
import org.softwire.training.models.LocationStatusReport;
import spark.QueryParamsMap;
import spark.Request;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Strings.isNullOrEmpty;

public class LocationStatusReportsRoutes extends ReportsRoutesBase<LocationStatusReportApiModel, LocationStatusReport> {

    private final LocationsDao locationsDao;

    @Inject
    public LocationStatusReportsRoutes(LocationReportsDao locationReportsDao, LocationsDao locationsDao, UsersDao usersDao, PermissionsVerifier permissionsVerifier) {
        super(
                LocationStatusReportApiModel.class,
                locationReportsDao,
                usersDao,
                permissionsVerifier);
        this.locationsDao = locationsDao;
    }
    int a = 1000;
    int b = 0;

    @Override
    protected LocationStatusReport validateThenMap(LocationStatusReportApiModel apiModel) {
        if (apiModel.getStatus() > a || apiModel.getStatus() < b) {
            throw new FailedRequestException(ErrorCode.INVALID_INPUT, "Location status must be between 0 and 1000");
        }

        // Ignore any supplied report time
        LocalDateTime reportTimeUtc = LocalDateTime.now(ZoneOffset.UTC);

        LocationStatusReport model = new LocationStatusReport();
        model.setAgentId(apiModel.getAgentId());
        model.setLocationId(apiModel.getLocationId());
        model.setStatus(apiModel.getStatus());
        model.setReportTime(reportTimeUtc);
        model.setReportTitle(apiModel.getReportTitle());
        model.setReportBody(apiModel.getReportBody());

        return model;
    }

    @Override
    protected LocationStatusReportApiModel mapToApiModel(LocationStatusReport model) {
        Optional<Location> location = locationsDao.getLocation(model.getLocationId());
        if (!location.isPresent()) {
            throw new FailedRequestException(ErrorCode.UNKNOWN_ERROR, "Could not successfully get location info");
        }

        return mapReportAndTimezoneToApiModel(model, location.get().getTimeZone());
    }

    private LocationStatusReportApiModel mapReportAndTimezoneToApiModel(LocationStatusReport model, String timeZone) {
        LocationStatusReportApiModel apiModel = new LocationStatusReportApiModel();

        ZoneId locationTimeZone = ZoneId.of(timeZone);

        apiModel.setReportId(model.getReportId());
        apiModel.setAgentId(model.getAgentId());
        apiModel.setLocationId(model.getLocationId());
        apiModel.setStatus(model.getStatus());
        apiModel.setReportTime(model.getReportTime().atZone(locationTimeZone));
        apiModel.setReportTitle(model.getReportTitle());
        apiModel.setReportBody(model.getReportBody());

        return apiModel;
    }

    @Override
    protected List<ReportSearchCriterion> parseSearchCriteria(Request req) {
        QueryParamsMap queryMap = req.queryMap();
        List<ReportSearchCriterion> searchCriteria = new ArrayList<>();

        if (!isNullOrEmpty(queryMap.get("agentId").value())) {
            searchCriteria.add(new AgentCallSignSearchCriterion(queryMap.get("agentId").integerValue()));
        }

        if (!isNullOrEmpty(queryMap.get("locationId").value())) {
            searchCriteria.add(new LocationIdSearchCriterion(queryMap.get("locationId").integerValue()));
        }

        if (!isNullOrEmpty(queryMap.get("reportTitle").value())) {
            searchCriteria.add(new ReportTitleSearchCriterion(queryMap.get("reportTitle").value()));
        }

        if (!isNullOrEmpty(queryMap.get("fromTime").value())) {
            searchCriteria.add(new FromTimeSearchCriterion(ZonedDateTime.parse(queryMap.get("fromTime").value())));
        }

        if (!isNullOrEmpty(queryMap.get("toTime").value())) {
            searchCriteria.add(new ToTimeSearchCriterion(ZonedDateTime.parse(queryMap.get("toTime").value())));
        }

        return searchCriteria;
    }
}

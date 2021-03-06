package org.softwire.training.api.routes.v1;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softwire.training.api.core.JsonRequestUtils;
import org.softwire.training.api.models.ErrorCode;
import org.softwire.training.api.models.FailedRequestException;
import org.softwire.training.api.models.LocationStatusReportApiModel;
import org.softwire.training.db.daos.AgentsDao;
import org.softwire.training.db.daos.UsersDao;
import org.softwire.training.models.Agent;
import org.softwire.training.models.ExternalReport;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.Optional;

public class ExternalReportRoutes {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String externalApiAddress;
    private final UsersDao usersDao;
    private final AgentsDao agentsDao;

    @Inject
    public ExternalReportRoutes(
            Configuration configuration,
            UsersDao usersDao,
            AgentsDao agentsDao) {
        this.externalApiAddress = configuration.getString("external-api-address");
        this.usersDao = usersDao;
        this.agentsDao = agentsDao;
    }

    public Object forwardReport(Request req, Response res) throws Exception {
        Agent agent = usersDao.getUser(req.attribute("user_id"))
                .flatMap(user -> Optional.ofNullable(user.getAgentId()))
                .flatMap(agentsDao::getAgent)
                .orElseThrow(() -> new FailedRequestException(ErrorCode.OPERATION_FORBIDDEN, "Insufficient permissions"));

        LocationStatusReportApiModel report = JsonRequestUtils.readBodyAsType(req, LocationStatusReportApiModel.class);

        ExternalReport externalReport = new ExternalReport(
                agent.getCallSign(),
                report.getReportBody());

        HttpResponse<String> externalResponse = Unirest.post(externalApiAddress)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(JsonRequestUtils.writeJsonString(externalReport))
                .asString();

        if (externalResponse.getStatus() >= 200 && externalResponse.getStatus() < 300) {
            res.status(204);
            return new Object();
        } else {
            log.error("Error posting to external API. Got {} {}: {}",
                    externalResponse.getStatus(), externalResponse.getStatusText(), externalResponse.getBody());
            throw new FailedRequestException(ErrorCode.UNKNOWN_ERROR, "Error posting to external API");
        }
    }
}

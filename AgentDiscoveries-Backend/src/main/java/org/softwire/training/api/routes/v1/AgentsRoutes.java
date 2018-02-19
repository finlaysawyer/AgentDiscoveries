package org.softwire.training.api.routes.v1;

import org.apache.commons.lang3.StringUtils;
import org.softwire.training.api.core.JsonRequestUtils;
import org.softwire.training.api.models.ErrorCode;
import org.softwire.training.api.models.FailedRequestException;
import org.softwire.training.db.daos.AgentsDao;
import org.softwire.training.models.Agent;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.List;

public class AgentsRoutes {

    private final AgentsDao agentsDao;

    @Inject
    public AgentsRoutes(AgentsDao agentsDao) {
        this.agentsDao = agentsDao;
    }

    public Agent createAgent(Request req, Response res) throws FailedRequestException {
        Agent agentModel = JsonRequestUtils.readBodyAsType(req, Agent.class);
        agentsDao.addAgent(agentModel);

        // Create requests should return 201
        res.status(201);

        return agentModel;
    }

    public Agent readAgent(Request req, Response res, int id) throws FailedRequestException {
        return agentsDao.getAgentByUserId(id)
                .orElseThrow(() -> new FailedRequestException(ErrorCode.NOT_FOUND, "Agent not found"));
    }

    public List<Agent> readAgents(Request req, Response res) {
        return agentsDao.getAgents();
    }

    public Agent updateAgent(Request req, Response res, int id) throws FailedRequestException {
        Agent agent = JsonRequestUtils.readBodyAsType(req, Agent.class);
        agent.setUserId(id);
        agentsDao.updateAgent(agent);
        return agent;
    }

    public Object deleteAgent(Request req, Response res, int id) throws Exception {
        if (StringUtils.isNotEmpty(req.body())) {
            throw new FailedRequestException(ErrorCode.INVALID_INPUT, "Agent delete request should have no body");
        }

        // Do not do anything with output, if nothing to delete request is successfully done (no-op)
        agentsDao.deleteAgent(id);
        res.status(204);

        return new Object();
    }
}

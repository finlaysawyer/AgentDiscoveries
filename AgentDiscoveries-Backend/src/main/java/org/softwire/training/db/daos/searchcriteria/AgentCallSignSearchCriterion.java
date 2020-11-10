package org.softwire.training.db.daos.searchcriteria;

import java.util.Collections;
import java.util.Map;

public final class AgentCallSignSearchCriterion extends ReportSearchCriterion {

    private static final String AGENT_CALLSIGN_BINDING_NAME = "call_sign_sc_call_sign";
    private final int agentId;

    public AgentCallSignSearchCriterion(int agentId) {
        this.agentId = agentId;
    }

    @Override
    public String getSqlForWhereClause() {
        return "call_sign = :" + AGENT_CALLSIGN_BINDING_NAME;
    }

    @Override
    public Map<String, Object> getBindingsForSql() {
        return Collections.singletonMap(AGENT_CALLSIGN_BINDING_NAME, agentId);
    }
}

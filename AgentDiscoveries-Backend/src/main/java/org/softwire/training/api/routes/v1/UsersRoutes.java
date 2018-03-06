package org.softwire.training.api.routes.v1;

import org.softwire.training.api.core.JsonRequestUtils;
import org.softwire.training.api.core.PasswordHasher;
import org.softwire.training.api.models.ErrorCode;
import org.softwire.training.api.models.FailedRequestException;
import org.softwire.training.api.models.UserApiModel;
import org.softwire.training.db.daos.AgentsDao;
import org.softwire.training.db.daos.UsersDao;
import org.softwire.training.models.Agent;
import org.softwire.training.models.User;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

import javax.inject.Inject;
import java.util.List;

public class UsersRoutes implements EntityCRUDRoutes {

    private final UsersDao usersDao;
    private final AgentsDao agentsDao;
    private final PasswordHasher passwordHasher;

    @Inject
    public UsersRoutes(UsersDao usersDao, AgentsDao agentsDao, PasswordHasher passwordHasher) {
        this.usersDao = usersDao;
        this.agentsDao = agentsDao;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public UserApiModel createEntity(Request req, Response res) throws FailedRequestException {
        UserApiModel userApiModel = JsonRequestUtils.readBodyAsType(req, UserApiModel.class);

        if (userApiModel.getUserId() != 0) {
            throw new FailedRequestException(ErrorCode.INVALID_INPUT, "agentId cannot be specified on create");
        }

        User user = new User(userApiModel.getUsername(), passwordHasher.hashPassword(userApiModel.getPassword()));

        int newUserId = usersDao.addUser(user);

        //TODO creating new user should have choice to also create a corresponding agent.
        //And should provide the relevant parameters
        //Per Card https://trello.com/c/iDGPOsLq/47-admin-should-be-able-to-create-new-users-agents
        if(false){
            Agent agent = new Agent(newUserId,"","",null,0,"");
            agentsDao.addAgent(agent);
        }

        // Set the agentId and for security remove the password
        userApiModel.setPassword(null);
        userApiModel.setUserId(newUserId);

        // Create requests should return 201
        res.status(201);

        return userApiModel;
    }

    @Override
    public UserApiModel readEntity(Request req, Response res, int id) throws FailedRequestException {
        return usersDao.getUser(id)
                .map(this::mapModelToApiModel)
                .orElseThrow(() -> new FailedRequestException(ErrorCode.NOT_FOUND, "User not found"));
    }

    @Override
    public List<User> readEntities(Request req, Response res){
        return usersDao.getUsers();
    }

    @Override
    public UserApiModel updateEntity(Request req, Response res, int id) throws FailedRequestException {
        UserApiModel userApiModel = JsonRequestUtils.readBodyAsType(req, UserApiModel.class);

        if (userApiModel.getUserId() != id && userApiModel.getUserId() != 0) {
            throw new FailedRequestException(ErrorCode.INVALID_INPUT, "agentId cannot be specified differently to URI");
        }

        User user = new User(userApiModel.getUsername(), passwordHasher.hashPassword(userApiModel.getPassword()));
        user.setUserId(id);

        usersDao.updateUser(user);

        return mapModelToApiModel(user);
    }

    private UserApiModel mapModelToApiModel(User user) {
        UserApiModel userApiModel = new UserApiModel();
        userApiModel.setUserId(user.getUserId());
        userApiModel.setUsername(user.getUsername());
        // Deliberately do not set the password for security reasons

        return userApiModel;
    }

    @Override
    public Object deleteEntity(Request req, Response res, int id) throws Exception {
        if (StringUtils.isNotEmpty(req.body())) {
            throw new FailedRequestException(ErrorCode.INVALID_INPUT, "User delete request should have no body");
        }

        // Do not do anything with output, if nothing to delete request is successfully done (no-op)
        usersDao.deleteUser(id);
        res.status(204);

        return new Object();
    }

}

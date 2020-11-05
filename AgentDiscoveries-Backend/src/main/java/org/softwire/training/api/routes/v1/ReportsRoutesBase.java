package org.softwire.training.api.routes.v1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.softwire.training.api.core.JsonRequestUtils;
import org.softwire.training.api.core.PermissionsVerifier;
import org.softwire.training.api.models.ErrorCode;
import org.softwire.training.api.models.FailedRequestException;
import org.softwire.training.api.models.ReportApiModelBase;
import org.softwire.training.db.daos.ReportsDao;
import org.softwire.training.db.daos.UsersDao;
import org.softwire.training.db.daos.searchcriteria.ReportSearchCriterion;
import org.softwire.training.models.ReportBase;
import org.softwire.training.models.TableCreator;
import rst.pdfbox.layout.elements.Paragraph;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Base class for routing for different report types
 *
 * @param <T> the API model class
 * @param <U> the DB model class
 */
public abstract class ReportsRoutesBase<T extends ReportApiModelBase, U extends ReportBase> {

    private final ReportsDao<U> reportsDao;
    private final Class<T> apiModelClass;
    private final UsersDao usersDao;

    protected PermissionsVerifier permissionsVerifier;

    protected ReportsRoutesBase(
            Class<T> apiModelClass,
            ReportsDao<U> reportsDao,
            UsersDao usersDao,
            PermissionsVerifier permissionsVerifier) {
        this.apiModelClass = apiModelClass;
        this.reportsDao = reportsDao;
        this.usersDao = usersDao;
        this.permissionsVerifier = permissionsVerifier;
    }

    /**
     * Mapping methods between API and DB models
     */
    protected abstract U validateThenMap(T model);
    protected abstract T mapToApiModel(U model);

    /**
     * Parse a search request criteria
     */
    protected abstract List<ReportSearchCriterion> parseSearchCriteria(Request req);

    public T createReport(Request req, Response res) {
        int agentId = usersDao.getUser(req.attribute("user_id"))
                .flatMap(user -> Optional.ofNullable(user.getAgentId()))
                .orElseThrow(() -> new FailedRequestException(ErrorCode.OPERATION_FORBIDDEN, "Insufficient permissions"));

        T reportApiModel = JsonRequestUtils.readBodyAsType(req, apiModelClass);

        if (reportApiModel.getReportId() != 0) {
            throw new FailedRequestException(ErrorCode.INVALID_INPUT, "reportId cannot be specified on create");
        }

        reportApiModel.setAgentId(agentId);

        // Validate report model before storing
        U reportModel = validateThenMap(reportApiModel);

        int newReportId = reportsDao.createReport(reportModel);

        // Create requests should return 201
        reportApiModel.setReportId(newReportId);
        res.status(201);

        return reportApiModel;
    }

    public T readReport(Request req, Response res, int id) {
        permissionsVerifier.verifyAdminPermission(req);
        return mapToApiModel(reportsDao.getReport(id)
                .orElseThrow(() -> new FailedRequestException(ErrorCode.NOT_FOUND, "Report not found")));
    }

    public Object generateReportPDF(Request req, Response res, int id) throws IOException {
        permissionsVerifier.verifyAdminPermission(req);

        T mapped = mapToApiModel(reportsDao.getReport(id)
                .orElseThrow(() -> new FailedRequestException(ErrorCode.NOT_FOUND, "Report not found")));

        //create document
        PDDocument document = new PDDocument();

        //assign information
        PDDocumentInformation pdd = document.getDocumentInformation();

        String author = "Agent " + mapped.getAgentId();
        String title = mapped.getReportTitle();
        String subject = mapped.getReportTitle();

        pdd.setAuthor(author);
        pdd.setTitle(title);
        pdd.setSubject(subject);

        //create page
        PDPage page = new PDPage();

        //enter information on page 1
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        document.addPage(page);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(25, 500);

        contentStream.newLine();
        contentStream.showText(mapped.getReportTitle());
        contentStream.showText(" | Status: " + mapped.getStatus());
        contentStream.showText(" | Agent ID: " + mapped.getAgentId());
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(25, 400);
        contentStream.showText(mapped.getReportBody());
        contentStream.endText();

        contentStream.close();

        document.save(new File("./summary.pdf"));

        document.close();

        res.status(204);
        return new Object();
    }

    public Object deleteReport(Request req, Response res, int id) throws Exception {
        permissionsVerifier.verifyAdminPermission(req);
        if (StringUtils.isNotEmpty(req.body())) {
            throw new FailedRequestException(ErrorCode.INVALID_INPUT, "Report delete request should have no body");
        }

        // Do not do anything with output, if nothing to delete request is successfully done (no-op)
        reportsDao.deleteReport(id);
        res.status(204);

        return new Object();
    }

    public List<T> searchReports(Request req, Response res) {
        permissionsVerifier.verifyAdminPermission(req);

        return reportsDao.searchReports(parseSearchCriteria(req))
                .stream()
                .map(this::mapToApiModel)
                .collect(Collectors.toList());
    }
}

package org.softwire.training.models;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;
import rst.pdfbox.layout.text.TextFragment;

import java.awt.*;
import java.io.IOException;

import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA;
import static org.vandeseer.easytable.settings.HorizontalAlignment.JUSTIFY;
import static org.vandeseer.easytable.settings.VerticalAlignment.BOTTOM;

public class TableCreator {
    private static final String FILE_NAME = "region_summary.pdf";

    //NEED TO CONNECT TO THE SQL DATABASE

    public TextFragment tableCreator() throws IOException {
        ReportBase reportBase = new ReportBase();

        String reportId = String.valueOf(reportBase.getReportId());
        //String locationId = String.valueOf(reportBase.getLocationId());
        String agentId = String.valueOf(reportBase.getAgentId());
        String status = String.valueOf(reportBase.getStatus());
        String reportTime = String.valueOf(reportBase.getReportTime());
        //String reportTitle = String.valueOf(reportBase.getReportTitle());
        String reportBody = String.valueOf(reportBase.getReportBody());


        Table.TableBuilder table = Table.builder()
                .addColumnsOfWidth(170, 170)
                .fontSize(8)
                .font(HELVETICA)
                .wordBreak(true)
                .backgroundColor(Color.LIGHT_GRAY)
                .horizontalAlignment(JUSTIFY)
                .verticalAlignment(BOTTOM);

    //header -- CONTENT
        table.addRow(Row.builder()
                .add(TextCell.builder().text("Report ID:")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text(reportId)
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());

        /*table.addRow(Row.builder()
                .add(TextCell.builder().text("Location ID:")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text(locationId)
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());*/

        table.addRow(Row.builder()
                .add(TextCell.builder().text("Agent ID:")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text(agentId)
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());

        table.addRow(Row.builder()
                .add(TextCell.builder().text("Status code:")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text(status)
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());

        table.addRow(Row.builder()
                .add(TextCell.builder().text("Report time:")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text(reportTime)
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());

        /*table.addRow(Row.builder()
                .add(TextCell.builder().text("Report title:")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text(reportTitle)
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());*/

        table.addRow(Row.builder()
                .add(TextCell.builder().text("Report body:")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text(reportBody)
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());
        return (TextFragment) table;
    }
}

package org.softwire.training.models;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;

import java.awt.*;
import java.io.IOException;

import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA;
import static org.vandeseer.easytable.settings.HorizontalAlignment.JUSTIFY;
import static org.vandeseer.easytable.settings.VerticalAlignment.BOTTOM;

public class TableCreator {
    private static final String FILE_NAME = "region_summary.pdf";

    //NEED TO CONNECT TO THE SQL DATABASE

    public void tableCreator() throws IOException {
        Table.TableBuilder tableBuilder = Table.builder()
                .addColumnsOfWidth(170, 170)
                .fontSize(8)
                .font(HELVETICA)
                .wordBreak(true)
                .backgroundColor(Color.LIGHT_GRAY)
                .horizontalAlignment(JUSTIFY)
                .verticalAlignment(BOTTOM);

    //header -- CONTENT
        tableBuilder.addRow(Row.builder()
                .add(TextCell.builder().text("INSERT REPORT ID SQL CODE HERE")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text("REPORT ID CONTENT")
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());

        tableBuilder.addRow(Row.builder()
                .add(TextCell.builder().text("INSERT LOCATION ID SQL CODE HERE")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text("LOCATION ID CONTENT")
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());

        tableBuilder.addRow(Row.builder()
                .add(TextCell.builder().text("INSERT AGENT ID SQL CODE HERE")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text("AGENT ID CONTENT")
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());

        tableBuilder.addRow(Row.builder()
                .add(TextCell.builder().text("INSERT STATUS SQL CODE HERE")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text("REPORT ID CONTENT")
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());

        tableBuilder.addRow(Row.builder()
                .add(TextCell.builder().text("INSERT DATE SQL CODE HERE")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text("REPORT ID CONTENT")
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());

        tableBuilder.addRow(Row.builder()
                .add(TextCell.builder().text("INSERT TITLE SQL CODE HERE")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text("REPORT ID CONTENT")
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());

        tableBuilder.addRow(Row.builder()
                .add(TextCell.builder().text("INSERT BODY SQL CODE HERE")
                        .lineSpacing(1.8f)
                        .padding(30)
                        .build())
                .add(TextCell.builder().text("BODY CONTENT")
                        .lineSpacing(0.6f)
                        .padding(10)
                        .build())
                .build());
    }
}

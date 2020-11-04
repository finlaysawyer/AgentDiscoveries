package org.softwire.training.models;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TableTest {
    TableCreator tableCreator = new TableCreator();

    @Test
    public void testIHaveATable() throws IOException {
        TestUtils.createAndSaveDocumentWithTables(FILE_NAME,
                tableCreator(),
                createComplexExampleTable()
        );

        CompareResult compareResult = new PdfComparator<>(getExpectedPdfFor(FILE_NAME), getActualPdfFor(FILE_NAME)).compare();
        assertTrue(compareResult.isEqual());
    }
}

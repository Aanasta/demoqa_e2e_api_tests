package helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;

public class JsonValidator {

    public static ProcessingReport validateJsonAgainstSchema(Object response, String expectedSchemaPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String actualBody = mapper.writeValueAsString(response);
        File expectedSchemaFile = new File("src/test/resources/schemas/" + expectedSchemaPath);
        JsonNode expectedJson = JsonLoader.fromFile(expectedSchemaFile);
        JsonNode actualJson = JsonLoader.fromString(actualBody);
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        com.github.fge.jsonschema.main.JsonValidator validator = factory.getValidator();
        ProcessingReport report = null;
        try {
            report = validator.validate(expectedJson, actualJson);
        } catch (ProcessingException e) {
            Assert.fail("JSON validation failed against schema: " + expectedSchemaPath);
        }
        return report;
    }
}

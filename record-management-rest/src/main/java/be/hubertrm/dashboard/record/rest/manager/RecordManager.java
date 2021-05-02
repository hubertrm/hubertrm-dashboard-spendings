package be.hubertrm.dashboard.record.rest.manager;

import be.hubertrm.dashboard.record.rest.dto.CategoryDto;
import be.hubertrm.dashboard.record.rest.dto.OrganisationDto;
import be.hubertrm.dashboard.record.rest.dto.RecordDto;
import be.hubertrm.dashboard.record.rest.dto.SourceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class RecordManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Locale locale;
    private final ResourceBundle resources;
    private final DateTimeFormatter dateFormat;
    private final NumberFormat moneyFormat;
    private final ResourceBundle config = ResourceBundle.getBundle("config");

    private Path reportsFolder = Path.of(config.getString("reports.folder"));

    private RecordDto recordDto;
    private CategoryDto categoryDto;
    private SourceDto sourceDto;
    private OrganisationDto organisationDto;

    public RecordManager(Locale locale) {
        this.locale = locale;
        resources = ResourceBundle.getBundle("resources", this.locale);
        dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(this.locale);
        moneyFormat = NumberFormat.getCurrencyInstance(this.locale);
    }

    public RecordDto createRecord(Long id, LocalDate localDate, CategoryDto categoryDto, SourceDto sourceDto, float amount, String comments) {
        recordDto = new RecordDto(id, localDate, amount, categoryDto, sourceDto, comments);
        return recordDto;
    }

    public CategoryDto createCategory(Long id, String name, LocalDate localDate) {
        categoryDto = new CategoryDto(id, name, localDate);
        return categoryDto;
    }

    public SourceDto createSource(Long id, String name, LocalDate localDate, Long organisationId) {
        sourceDto = new SourceDto(id, name, localDate, organisationId);
        return sourceDto;
    }

    public OrganisationDto createOrganisation(Long id, String name, String address) {
        organisationDto = new OrganisationDto(id, name, address);
        return organisationDto;
    }

    public void printRecordReport() {
        Path recordFile = reportsFolder.resolve(MessageFormat.format(config.getString("report.file"), recordDto.getId()));
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(Files.newOutputStream(recordFile, StandardOpenOption.CREATE),
                StandardCharsets.UTF_8))) {
            out.append(MessageFormat.format(resources.getString("record"),
                    recordDto.getId(),
                    dateFormat.format(recordDto.getPayDate()),
                    moneyFormat.format(recordDto.getAmount()),
                    recordDto.getCategoryDto().getName(),
                    recordDto.getSourceDto().getName(),
                    recordDto.getComments())).append(System.lineSeparator());
            if (categoryDto != null) {
                out.append(MessageFormat.format(resources.getString("category"),
                        categoryDto.getId(),
                        categoryDto.getName(),
                        dateFormat.format(categoryDto.getCreationDate())))
                        .append(System.lineSeparator());
            }
            if (sourceDto != null) {
                out.append(MessageFormat.format(resources.getString("source"),
                        sourceDto.getId(),
                        sourceDto.getName(),
                        dateFormat.format(sourceDto.getCreationDate()),
                        sourceDto.getOrganisationId()))
                        .append(System.lineSeparator());
            }
            if (organisationDto != null) {
                out.append(MessageFormat.format(resources.getString("organisation"),
                        organisationDto.getId(),
                        organisationDto.getName(),
                        organisationDto.getAddress()))
                        .append(System.lineSeparator());
            }
        } catch (IOException e) {
            java.util.logging.Logger.getLogger(RecordManager.class.getName()).log(Level.SEVERE, e,
                    () -> "Error parsing record "+e.getMessage());
        }
    }

    /**
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }
}

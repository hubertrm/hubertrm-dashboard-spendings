package be.hubertrm.dashboard.record.files.service.impl;

import be.hubertrm.dashboard.record.core.dto.CategoryDto;
import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.core.dto.SourceDto;
import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.mapper.CategoryMapper;
import be.hubertrm.dashboard.record.core.mapper.SourceMapper;
import be.hubertrm.dashboard.record.core.service.CategoryService;
import be.hubertrm.dashboard.record.core.service.SourceService;
import be.hubertrm.dashboard.record.files.csv.converter.Converter;
import be.hubertrm.dashboard.record.files.enums.DatePattern;
import be.hubertrm.dashboard.record.files.model.RecordField;
import be.hubertrm.dashboard.record.files.service.RecordDtoService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecordDtoServiceImpl implements RecordDtoService {

    public final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    public final SourceMapper sourceMapper = Mappers.getMapper(SourceMapper.class);
    private DateTimeFormatter dateTimeFormatter;

    @Resource
    public CategoryService categoryService;
    @Resource
    public SourceService sourceService;

    @Resource
    Converter<String, List<String>> converter;

    @Override
    public RecordDto create(String[] fields, String line) {
        return this.create(fields, line, Locale.ROOT);
    }

    @Override
    public RecordDto create(String[] fields, String line, Locale locale) {
        var recordDto = new RecordDto();
        try {
            List<String> elements = converter.convert(line);
            Map<String, RecordField> fieldsMap = getValueRecordFieldMap(locale);
            for (var i = 0; i < fields.length; i++) {
                switch (fieldsMap.getOrDefault(fields[i], RecordField.NOT_SUPPORTED)) {
                    case DATE:
                        recordDto.setPayDate(mapDate(elements.get(i)));
                        break;
                    case PRICE:
                        recordDto.setAmount(mapAmount(elements.get(i)));
                        break;
                    case CATEGORY:
                        recordDto.setCategoryDto(mapCategory(elements.get(i)));
                        break;
                    case SOURCE:
                        recordDto.setSourceDto(mapSource(elements.get(i)));
                        break;
                    case NOTE:
                        recordDto.setComments(elements.get(i));
                        break;
                    case NOT_SUPPORTED:
                    default:
                        log.debug("Field \"{}\" is not supported (element: {}, value: {})", fields[i], i, elements.get(i));
                }
            }
        } catch (ResourceNotFoundException e) {
            log.debug("Could not create Record from line {}.", line, e);
            return null;
        }
        return recordDto;
    }

    public void setDateTimeFormatter(String pattern) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    private Map<String, RecordField> getValueRecordFieldMap(Locale locale) {
        var resource = ResourceBundle.getBundle("record-fields", locale);
        return resource.keySet().stream()
                .collect(Collectors.toMap(resource::getString, this::getMatchingRecordField));
    }

    private RecordField getMatchingRecordField(String value) {
        return Arrays.stream(RecordField.values())
                .filter(recordField -> recordField.getValue().equals(value))
                .findFirst()
                .orElse(RecordField.NOT_SUPPORTED);
    }

    private LocalDate mapDate(String date) {
        if (dateTimeFormatter != null) {
            return date != null ? LocalDate.parse(date, dateTimeFormatter) : null;
        }
        var datePattern = DatePattern.DEFAULT_DATE_PATTERN;
        return date != null ? LocalDate.parse(date, DateTimeFormatter.ofPattern(getDatePattern(datePattern)))  : null;
    }

    private static String getDatePattern(DatePattern datePattern) {
        var resources = ResourceBundle.getBundle("record-files-supported-date-format");
        return resources.getString(datePattern.getValue());
    }

    private Float mapAmount(String amount) {
        var amountPattern = "^\\s*(\\d+(,\\d+)?)\\s*€";
        var pattern = Pattern.compile(amountPattern);
        var matcher = pattern.matcher(amount);
        return matcher.find() ? Float.parseFloat(matcher.group(1).replace(',', '.')) : 0.0f;
    }

    private CategoryDto mapCategory(String categoryName) throws ResourceNotFoundException {
        return categoryMapper.toDto(categoryService.getCategoryByName(categoryName));
    }

    private SourceDto mapSource(String sourceName) throws ResourceNotFoundException {
        return sourceMapper.toDto(sourceService.getSourceByName(sourceName));
    }
}

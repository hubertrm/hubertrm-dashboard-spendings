package be.hubertrm.dashboard.record.files.csv.factory;

import be.hubertrm.dashboard.record.files.csv.converter.Converter;
import be.hubertrm.dashboard.record.files.csv.converter.StringArrayConverter;
import be.hubertrm.dashboard.record.files.csv.converter.StringListConverter;
import be.hubertrm.dashboard.record.files.csv.converter.enums.OutputType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterFactoryTest {

    private ConverterFactory converterFactory;

    @BeforeEach
    void setup() {
        StringArrayConverter stringArrayConverter = new StringArrayConverter();
        StringListConverter stringListConverter = new StringListConverter();
        Set<Converter<String, ? extends List<String>>> converterSet = Set.of(stringArrayConverter, stringListConverter);
        converterFactory = new ConverterFactory(converterSet);
    }

    @Test
    void givenNoMarker_shouldReturnStringToArrayConverter() {
        assertThat(converterFactory.createConverter(OutputType.ARRAY)).isInstanceOf(StringArrayConverter.class);
    }

    @Test
    void givenStringMarker_shouldReturnStringToListConverter() {
        assertThat(converterFactory.createConverter(OutputType.LIST)).isInstanceOf(StringListConverter.class);
    }
}
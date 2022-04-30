package lv.gennadyyonov.spring.jpa.multiple.db.config.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "multiple-db.persistence")
@RequiredArgsConstructor
@ConstructorBinding
@Validated
@Value
public class PersistenceProperties {

    Map<String, DataSource> dataSource = new HashMap<>();
    Map<String, JpaProperties> jpa = new HashMap<>();

    @Validated
    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class DataSource extends DataSourceProperties {

        @NotEmpty
        private String typeName;
    }
}

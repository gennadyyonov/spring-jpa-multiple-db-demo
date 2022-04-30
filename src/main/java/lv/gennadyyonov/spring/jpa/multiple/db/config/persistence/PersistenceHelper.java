package lv.gennadyyonov.spring.jpa.multiple.db.config.persistence;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static java.util.Optional.ofNullable;

@UtilityClass
public class PersistenceHelper {

    @SneakyThrows
    public static DataSource createDataSource(PersistenceProperties persistenceProperties, String name) {
        PersistenceProperties.DataSource properties = getDataSourceProperties(persistenceProperties, name);
        //noinspection unchecked
        Class<DataSource> type = (Class<DataSource>) Class.forName(properties.getTypeName());
        return properties.initializeDataSourceBuilder()
                .type(type)
                .build();
    }

    public static JpaVendorAdapter getJpaVendorAdapter(PersistenceProperties properties, String name) {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        JpaProperties jpaProperties = getJpaProperties(properties, name);
        adapter.setShowSql(jpaProperties.isShowSql());
        if (jpaProperties.getDatabase() != null) {
            adapter.setDatabase(jpaProperties.getDatabase());
        }
        if (jpaProperties.getDatabasePlatform() != null) {
            adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        }
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        return adapter;
    }

    public static EntityManagerFactoryBuilder getEmfBuilder(PersistenceProperties properties, String name,
                                                            JpaVendorAdapter jpaVendorAdapter,
                                                            ObjectProvider<PersistenceUnitManager> puManager) {
        JpaProperties jpaProperties = getJpaProperties(properties, name);
        return new EntityManagerFactoryBuilder(
                jpaVendorAdapter,
                jpaProperties.getProperties(),
                puManager.getIfAvailable()
        );
    }

    public static LocalContainerEntityManagerFactoryBean getEmf(EntityManagerFactoryBuilder emfBuilder,
                                                                DataSource dataSource,
                                                                String persistenceUnit,
                                                                String... packagesToScan) {
        return emfBuilder
                .dataSource(dataSource)
                .persistenceUnit(persistenceUnit)
                .packages(packagesToScan)
                .build();
    }

    public static JpaTransactionManager getJpaTransactionManager(EntityManagerFactory enf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(enf);
        return transactionManager;
    }

    private static PersistenceProperties.DataSource getDataSourceProperties(PersistenceProperties properties,
                                                                            String name) {
        return ofNullable(properties.getDataSource())
                .map(map -> map.get(name))
                .orElseThrow(() -> new IllegalArgumentException(name + " data-source properties MUST NOT be null!"));
    }

    private static JpaProperties getJpaProperties(PersistenceProperties properties, String name) {
        return ofNullable(properties.getJpa())
                .map(map -> map.get(name))
                .orElseThrow(() -> new IllegalArgumentException(name + " jpa properties MUST NOT be null!"));
    }
}

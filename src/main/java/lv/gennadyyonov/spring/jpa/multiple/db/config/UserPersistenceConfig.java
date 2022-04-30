package lv.gennadyyonov.spring.jpa.multiple.db.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.gennadyyonov.spring.jpa.multiple.db.config.persistence.PersistenceHelper;
import lv.gennadyyonov.spring.jpa.multiple.db.config.persistence.PersistenceProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.USER;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.USER_DATA_SOURCE_NAME;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.USER_EMF_BUILDER_NAME;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.USER_ENTITY_MANAGER_FACTORY_NAME;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.USER_ENTITY_SCAN_PACKAGES;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.USER_JPA_REPO_BASE_PACKAGES;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.USER_JPA_VENDOR_ADAPTER_NAME;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.USER_PU;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.USER_TRANSACTION_MANAGER_NAME;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.persistence.PersistenceHelper.getJpaTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(PersistenceProperties.class)
@EnableJpaRepositories(
        basePackages = USER_JPA_REPO_BASE_PACKAGES,
        entityManagerFactoryRef = USER_ENTITY_MANAGER_FACTORY_NAME,
        transactionManagerRef = USER_TRANSACTION_MANAGER_NAME
)
public class UserPersistenceConfig {

    private final PersistenceProperties persistenceProperties;

    @Bean(name = USER_DATA_SOURCE_NAME)
    public DataSource userDataSource() {
        return PersistenceHelper.createDataSource(persistenceProperties, USER);
    }

    @Bean(name = USER_JPA_VENDOR_ADAPTER_NAME)
    public JpaVendorAdapter jpaVendorAdapter() {
        return PersistenceHelper.getJpaVendorAdapter(persistenceProperties, USER);
    }

    @Bean(name = USER_EMF_BUILDER_NAME)
    public EntityManagerFactoryBuilder emfBuilder(@Qualifier(USER_JPA_VENDOR_ADAPTER_NAME) JpaVendorAdapter adapter,
                                                  ObjectProvider<PersistenceUnitManager> persistenceUnitManager) {
        return PersistenceHelper.getEmfBuilder(persistenceProperties, USER, adapter, persistenceUnitManager);
    }

    @Bean(name = USER_ENTITY_MANAGER_FACTORY_NAME)
    public LocalContainerEntityManagerFactoryBean emf(
            @Qualifier(USER_EMF_BUILDER_NAME) EntityManagerFactoryBuilder emfBuilder,
            @Qualifier(USER_DATA_SOURCE_NAME) DataSource dataSource
    ) {
        return PersistenceHelper.getEmf(emfBuilder, dataSource, USER_PU, USER_ENTITY_SCAN_PACKAGES);
    }

    @Bean(name = USER_TRANSACTION_MANAGER_NAME)
    public PlatformTransactionManager userTransactionManager(
            @Qualifier(USER_ENTITY_MANAGER_FACTORY_NAME) EntityManagerFactory enf
    ) {
        return getJpaTransactionManager(enf);
    }
}

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

import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.PRODUCT;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.PRODUCT_DATA_SOURCE_NAME;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.PRODUCT_EMF_BUILDER_NAME;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.PRODUCT_ENTITY_MANAGER_FACTORY_NAME;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.PRODUCT_ENTITY_SCAN_PACKAGES;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.PRODUCT_JPA_REPO_BASE_PACKAGES;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.PRODUCT_JPA_VENDOR_ADAPTER_NAME;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.PRODUCT_PU;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.PRODUCT_TRANSACTION_MANAGER_NAME;
import static lv.gennadyyonov.spring.jpa.multiple.db.config.persistence.PersistenceHelper.getJpaTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(PersistenceProperties.class)
@EnableJpaRepositories(
        basePackages = PRODUCT_JPA_REPO_BASE_PACKAGES,
        entityManagerFactoryRef = PRODUCT_ENTITY_MANAGER_FACTORY_NAME,
        transactionManagerRef = PRODUCT_TRANSACTION_MANAGER_NAME
)
public class ProductPersistenceConfig {

    private final PersistenceProperties persistenceProperties;

    @Bean(name = PRODUCT_DATA_SOURCE_NAME)
    public DataSource productDataSource() {
        return PersistenceHelper.createDataSource(persistenceProperties, PRODUCT);
    }

    @Bean(name = PRODUCT_JPA_VENDOR_ADAPTER_NAME)
    public JpaVendorAdapter jpaVendorAdapter() {
        return PersistenceHelper.getJpaVendorAdapter(persistenceProperties, PRODUCT);
    }

    @Bean(name = PRODUCT_EMF_BUILDER_NAME)
    public EntityManagerFactoryBuilder emfBuilder(@Qualifier(PRODUCT_JPA_VENDOR_ADAPTER_NAME) JpaVendorAdapter adapter,
                                                  ObjectProvider<PersistenceUnitManager> persistenceUnitManager) {
        return PersistenceHelper.getEmfBuilder(persistenceProperties, PRODUCT, adapter, persistenceUnitManager);
    }

    @Bean(name = PRODUCT_ENTITY_MANAGER_FACTORY_NAME)
    public LocalContainerEntityManagerFactoryBean emf(
            @Qualifier(PRODUCT_EMF_BUILDER_NAME) EntityManagerFactoryBuilder emfBuilder,
            @Qualifier(PRODUCT_DATA_SOURCE_NAME) DataSource dataSource
    ) {
        return PersistenceHelper.getEmf(emfBuilder, dataSource, PRODUCT_PU, PRODUCT_ENTITY_SCAN_PACKAGES);
    }

    @Bean(name = PRODUCT_TRANSACTION_MANAGER_NAME)
    public PlatformTransactionManager userTransactionManager(
            @Qualifier(PRODUCT_ENTITY_MANAGER_FACTORY_NAME) EntityManagerFactory enf
    ) {
        return getJpaTransactionManager(enf);
    }
}

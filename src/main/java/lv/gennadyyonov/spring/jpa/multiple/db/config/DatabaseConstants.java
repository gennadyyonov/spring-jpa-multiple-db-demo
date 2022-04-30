package lv.gennadyyonov.spring.jpa.multiple.db.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DatabaseConstants {

    public static final String PRODUCT = "product";
    public static final String PRODUCT_PU = "productPu";
    public static final String PRODUCT_JPA_REPO_BASE_PACKAGES = "lv.gennadyyonov.spring.jpa.multiple.db.dao.product";
    public static final String PRODUCT_ENTITY_SCAN_PACKAGES = "lv.gennadyyonov.spring.jpa.multiple.db.model.product";
    public static final String PRODUCT_DATA_SOURCE_NAME = "productDataSource";
    public static final String PRODUCT_JPA_VENDOR_ADAPTER_NAME = "productJpaVendorAdapter";
    public static final String PRODUCT_EMF_BUILDER_NAME = "productEmfBuilder";
    public static final String PRODUCT_ENTITY_MANAGER_FACTORY_NAME = "productEntityManager";
    public static final String PRODUCT_TRANSACTION_MANAGER_NAME = "productTransactionManager";

    public static final String USER = "user";
    public static final String USER_PU = "userPu";
    public static final String USER_JPA_REPO_BASE_PACKAGES = "lv.gennadyyonov.spring.jpa.multiple.db.dao.user";
    public static final String USER_ENTITY_SCAN_PACKAGES = "lv.gennadyyonov.spring.jpa.multiple.db.model.user";
    public static final String USER_DATA_SOURCE_NAME = "userDataSource";
    public static final String USER_JPA_VENDOR_ADAPTER_NAME = "userJpaVendorAdapter";
    public static final String USER_EMF_BUILDER_NAME = "userEmfBuilder";
    public static final String USER_ENTITY_MANAGER_FACTORY_NAME = "userEntityManager";
    public static final String USER_TRANSACTION_MANAGER_NAME = "userTransactionManager";
}

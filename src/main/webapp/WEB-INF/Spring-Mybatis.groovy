import org.apache.commons.dbcp.BasicDataSource
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.mapper.MapperScannerConfigurer
beans{
    xmlns([ctx: 'http://www.springframework.org/schema/context', mvc: 'http://www.springframework.org/schema/mvc'])
    ctx.'component-scan'('base-package':'com.muyundefeng.spring.controller')
    ctx.'component-scan'('base-package':'com.muyundefeng.spring.service')
    mvc.'annotation-driven'()

    basicDataSource(BasicDataSource){
        username = "root"
        url = "jdbc:mysql://localhost:3306/movieInfo?characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true"
        password = "root"
        driverClassName = "com.mysql.jdbc.Driver"
        maxIdle = "5"
        maxActive = "10"
    }

    sqlSessionFactoryBean(SqlSessionFactoryBean) {
        dataSource = basicDataSource
    }
    mapperScannerConfigurer(MapperScannerConfigurer) {
        basePackage = "com.muyundefeng.spring.mapper"
        sqlSessionFactory = sqlSessionFactoryBean
    }

}
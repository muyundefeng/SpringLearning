import org.apache.commons.dbcp.BasicDataSource
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.mapper.MapperFactoryBean

beans{
    xmlns([ctx: 'http://www.springframework.org/schema/context', mvc: 'http://www.springframework.org/schema/mvc'])
    ctx.'component-scan'('base-package':'com.muyundefeng.spring.controller')
    ctx.'component-scan'('base-package':'com.muyundefeng.spring.service')
    ctx.'component-scan'('base-package':'com.muyundefeng.spring.dao')
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
//    mapperScannerConfigurer(MapperScannerConfigurer) {
//        basePackage = "com.muyundefeng.spring.mapper"
//        sqlSessionFactory = sqlSessionFactoryBean
//    }
    //可以使用mapperFactoryBean来扫描相关了类,每一个mapper借口对应于一类sqlSessionFactoryBean

    mapperFactoryBean(MapperFactoryBean){
        mapperInterface = "com.muyundefeng.spring.mapper.StudentMapper"
        sqlSessionFactory = sqlSessionFactoryBean
    }
    mapperFactoryBean(MapperFactoryBean){
        mapperInterface = "com.muyundefeng.spring.mapper.PersoninfoMapper"
        sqlSessionFactory = sqlSessionFactoryBean
    }

}
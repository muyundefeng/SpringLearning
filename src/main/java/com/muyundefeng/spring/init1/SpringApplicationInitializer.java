package com.muyundefeng.spring.init1;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.GroovyWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by greatdreams on 12/30/15.
 */
public class SpringApplicationInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        GroovyWebApplicationContext applicationContext = new GroovyWebApplicationContext();
        applicationContext.setConfigLocation("/WEB-INF/Spring-Mybatis.groovy");
        DispatcherServlet servlet = new DispatcherServlet(applicationContext);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                "springDispatcher", servlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        System.setProperty("servletContextRealPath", servletContext.getRealPath(""));
    }

    private final void registerFilter(ServletContext servletContext,
                                      boolean insertBeforeOtherFilters, String filterName, Filter filter) {
        FilterRegistration.Dynamic registration = servletContext.addFilter(filterName, filter);
        if (registration == null) {
            throw new IllegalStateException(
                    "Duplicate Filter registration for '" + filterName
                            + "'. Check to ensure the Filter is only configured once.");
        }
        registration.setAsyncSupported(isAsyncSecuritySupported());
        EnumSet<DispatcherType> dispatcherTypes = getSecurityDispatcherTypes();
        registration.addMappingForUrlPatterns(dispatcherTypes, !insertBeforeOtherFilters,
                "/*");
    }

    /**
     * Get the {@link DispatcherType} for the springSecurityFilterChain.
     *
     * @return
     */
    protected EnumSet<DispatcherType> getSecurityDispatcherTypes() {
        return EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR,
                DispatcherType.ASYNC);
    }

    /**
     * Determine if the springSecurityFilterChain should be marked as supporting asynch.
     * Default is true.
     *
     * @return true if springSecurityFilterChain should be marked as supporting asynch
     */
    protected boolean isAsyncSecuritySupported() {
        return true;
    }
}
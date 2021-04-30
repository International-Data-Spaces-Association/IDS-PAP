//package de.fraunhofer.iese.ids.odrl.pap.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class OdrlPapMvcConfig extends WebMvcConfigurationSupport {
//	@Override
//	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images/");
//		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
//		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
//		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/");
//		registry.addResourceHandler("/jQuery/**").addResourceLocations("classpath:/jQuery/");
//	  }
//	
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//	    registry.addViewController("/").setViewName("redirect:/static/index.html");
//	}
//
//}

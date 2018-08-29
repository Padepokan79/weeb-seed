package app.config;

import org.javalite.activeweb.AbstractControllerConfig;
import org.javalite.activeweb.AppContext;
import org.javalite.activeweb.controller_filters.DBConnectionFilter;
import org.javalite.activeweb.controller_filters.TimingFilter;

import app.controllers.auth.TokenController;
import app.controllers.core.GeneratorController;
import app.controllers.example.crud.CustomAllInOneController;
import app.controllers.example.crud.CustomAllInOneDTOController;
import app.controllers.example.crud.CustomOnReadAllController;
import app.controllers.example.crud.DefaultCRUDController;
import app.controllers.example.process.DefaultProcessController;
import app.filters.AuthorizationFilter;

public class AppControllerConfig extends AbstractControllerConfig {

	@SuppressWarnings("unchecked")
	public void init(AppContext context) {
        addGlobalFilters(new TimingFilter());
        addGlobalFilters(new DBConnectionFilter("default", true));
        addGlobalFilters(new AuthorizationFilter()).exceptFor(
        		TokenController.class, 
        		DefaultCRUDController.class,
        		GeneratorController.class,
        		CustomAllInOneController.class,
        		CustomAllInOneDTOController.class,
        		CustomOnReadAllController.class,
        		DefaultProcessController.class);
    }
}

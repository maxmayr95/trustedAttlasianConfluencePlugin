package trusted_connection_plugin.com.maxmayr.macro;

import com.atlassian.applinks.api.ApplicationLink;
import com.atlassian.applinks.api.ApplicationLinkService;
import com.atlassian.applinks.api.application.jira.JiraApplicationType;
import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.net.Request.MethodType;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Trusted implements Macro {
	ApplicationLinkService applicationLinkService;
	@Autowired
	  public Trusted(@ComponentImport final ApplicationLinkService applicationLinkService){
	    this.applicationLinkService = applicationLinkService;
	  }
    public String execute(Map<String, String> map, String s, ConversionContext conversionContext) throws MacroExecutionException {
        ApplicationLink jiraLink = applicationLinkService.getPrimaryApplicationLink(JiraApplicationType.class);
        //We do a simple Get request to get informations from the admin user
        return ApiCaller.makeRequest(jiraLink, MethodType.GET, "/rest/api/2/user?username=admin&expand=groups", null);
    }

    public BodyType getBodyType() { return BodyType.NONE; }

    public OutputType getOutputType() { return OutputType.BLOCK; }
}
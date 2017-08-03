package trusted_connection_plugin.com.maxmayr.macro;

import com.atlassian.applinks.api.ApplicationLink;
import com.atlassian.applinks.api.ApplicationLinkRequest;
import com.atlassian.applinks.api.ApplicationLinkRequestFactory;
import com.atlassian.applinks.api.ApplicationLinkResponseHandler;
import com.atlassian.applinks.api.CredentialsRequiredException;
import com.atlassian.sal.api.net.Response;
import com.atlassian.sal.api.net.ResponseException;
import com.atlassian.sal.api.net.Request.MethodType;

public class ApiCaller {
	public static String makeRequest(ApplicationLink jiraLink,MethodType method, String url,String body){
		final ApplicationLinkRequestFactory requestFactory = jiraLink.createAuthenticatedRequestFactory();
    	try {
    		ApplicationLinkRequest request = requestFactory.createRequest(method, url);
    		if(body!=null && method != MethodType.GET){
    			request.setRequestBody(body);
    		}

    		return request.execute(new ApplicationLinkResponseHandler<String>()
    		{
    			public String credentialsRequired(Response response) throws ResponseException
    			{
    				throw new ResponseException(new CredentialsRequiredException(requestFactory, "Token is invalid"));
    			}
    			public String handle(Response response) throws ResponseException
    			{
    				return response.getResponseBodyAsString();
    			}
    		});
    	} catch (CredentialsRequiredException e) {
    		e.printStackTrace();
    	} catch (Exception e){
    		e.printStackTrace();
    	}finally {

    	}
		return null;
	}
}

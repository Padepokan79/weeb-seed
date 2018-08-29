package app.filters;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import core.io.enums.ActionTypes;
import core.io.enums.HttpResponses;
import core.io.exception.UnAuthorizedException;
import core.io.helper.TokenHelper;
import core.javalite.filter.CoreHttpSupportFilter;
import io.jsonwebtoken.Claims;

public class AuthorizationFilter extends CoreHttpSupportFilter {
	private static Map<String, String> methodMap = new HashMap<String, String>();

	static {
		methodMap.put("create", "c");
		methodMap.put("read", "r");
		methodMap.put("readAll", "r");
		methodMap.put("update", "u");
		methodMap.put("delete", "d");
		methodMap.put("lov", "r");
	}
	
	@Override
    public void before() {
		response().setActionType(ActionTypes.OTHER);
		
        try {
        	Claims claims;
        	try {
        		String header = header("authorization");
		        claims = TokenHelper.parseTokenFromHeader(header);
        	} catch (Exception ex){
        		ex.printStackTrace();
	        	throw new UnAuthorizedException();
        	}
        		
	        if (claims.getExpiration().getTime() < new Date().getTime()){
	        	throw new UnAuthorizedException();
	        }
	        
	        return;
        } 
        catch (UnAuthorizedException e){
        	logError(e);
        	response().setResponseBody(HttpResponses.UNAUTHORIZED);
        } catch (Exception e){
        	logError(e);
        	response().setResponseBody(HttpResponses.FORBIDDEN);        	
        }
        
        sendResponse();
    }
 }
package org.drools.brms.client.rpc;

import org.drools.brms.client.rpc.mock.MockRepositoryServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Creates instances of the repository service for the client code to use.
 * @author Michael Neale
 */
public class RepositoryServiceFactory {

    /**
     * Change this to switch between debug/mock mode (ie web front end only)
     * versus full RPC (which requires the back end be running in some form).
     * Can set it to DEBUG if you want to run it client side only.
     */
    public static boolean DEBUG = true;
    
    public static RepositoryServiceAsync getService() {
        if (DEBUG)
            return getMockService();
        return getRealService(); 
            
    }
    
    private static RepositoryServiceAsync getMockService() {
        return new MockRepositoryServiceAsync();
    }

    private static RepositoryServiceAsync getRealService() {
        // define the service you want to call        
        RepositoryServiceAsync svc =
            (RepositoryServiceAsync) GWT.create(RepositoryService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) svc;
        
        String endpointURL = GWT.getModuleBaseURL() + "jbrmsService";
        System.out.println("Endpoint URL: " + endpointURL);
        
        endpoint.setServiceEntryPoint(endpointURL);
        return svc;
    }
    
}

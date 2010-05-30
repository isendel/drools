package org.drools.task.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.drools.task.BaseTest;
import org.drools.task.Task;
import org.drools.task.query.TaskSummary;
import org.drools.task.service.TaskClientHandler.TaskSummaryResponseHandler;
import org.drools.task.service.responsehandlers.BlockingAddTaskResponseHandler;
import org.drools.task.utils.CollectionUtils;

public abstract class TaskServiceBaseTest extends BaseTest {

	protected TaskServer server;
    protected TaskClient client;

    @SuppressWarnings("unchecked")
    public void testTasksOwnedQueryWithI18N() throws Exception {
        Map<String, Object>  vars = new HashedMap();     
        vars.put( "users", users );
        vars.put( "groups", groups );        
        
        //Reader reader;
        Reader reader = new InputStreamReader( getClass().getResourceAsStream( "../QueryData_TasksOwned.mvel" ) );
        List<Task> tasks = (List<Task>) eval( reader,
                                              vars );
        for ( Task task : tasks ) {
            BlockingAddTaskResponseHandler responseHandler = new BlockingAddTaskResponseHandler();
            client.addTask( task, null, responseHandler );
        }

        // Test UK I18N  
        reader = new InputStreamReader( getClass().getResourceAsStream( "../QueryResults_TasksOwnedInEnglish.mvel" ) );
        Map<String, List<TaskSummary>> expected = (Map<String, List<TaskSummary>>) eval( reader,
                                                                                         vars );

        BlockingAllOpenTasksForUseResponseHandler responseHandler = new BlockingAllOpenTasksForUseResponseHandler();
        client.getTasksOwned( users.get( "peter" ).getId(),
                              "en-UK",
                              responseHandler );
        List<TaskSummary> actual = responseHandler.getResults();
        assertEquals( 3,
                      actual.size() );
        assertTrue( CollectionUtils.equals( expected.get( "peter" ),
                                            actual ) );

        responseHandler = new BlockingAllOpenTasksForUseResponseHandler();
        client.getTasksOwned( users.get( "steve" ).getId(),
                              "en-UK",
                              responseHandler );
        actual = responseHandler.getResults();
        assertEquals( 2,
                      actual.size() );
        assertTrue( CollectionUtils.equals( expected.get( "steve" ),
                                            actual ) );

        responseHandler = new BlockingAllOpenTasksForUseResponseHandler();
        client.getTasksOwned( users.get( "darth" ).getId(),
                              "en-UK",
                              responseHandler );
        actual = responseHandler.getResults();
        assertEquals( 1,
                      actual.size() );
        assertTrue( CollectionUtils.equals( expected.get( "darth" ),
                                            actual ) );

        // Test DK I18N 
        reader = new InputStreamReader( getClass().getResourceAsStream( "../QueryResults_TasksOwnedInGerman.mvel" ) );
        expected = (Map<String, List<TaskSummary>>) eval( reader,
                                                          vars );

        responseHandler = new BlockingAllOpenTasksForUseResponseHandler();
        client.getTasksOwned( users.get( "peter" ).getId(),
                              "en-DK",
                              responseHandler );
        actual = responseHandler.getResults();
        assertEquals( 3,
                      actual.size() );
        assertTrue( CollectionUtils.equals( expected.get( "peter" ),
                                            actual ) );

        responseHandler = new BlockingAllOpenTasksForUseResponseHandler();
        client.getTasksOwned( users.get( "steve" ).getId(),
                              "en-DK",
                              responseHandler );
        actual = responseHandler.getResults();
        assertEquals( 2,
                      actual.size() );
        assertTrue( CollectionUtils.equals( expected.get( "steve" ),
                                            actual ) );

        responseHandler = new BlockingAllOpenTasksForUseResponseHandler();
        client.getTasksOwned( users.get( "darth" ).getId(),
                              "en-DK",
                              responseHandler );
        actual = responseHandler.getResults();
        assertEquals( 1,
                      actual.size() );
        assertTrue( CollectionUtils.equals( expected.get( "darth" ),
                                            actual ) );
    }

    public void testPeopleAssignmentQueries() {
        Map vars = new HashedMap();
        vars.put( "users",
                  users );
        vars.put( "groups",
                  groups );

        Reader reader = new InputStreamReader( getClass().getResourceAsStream( "../QueryData_TasksOwned.mvel" ) );
        List<Task> tasks = (List<Task>) eval( reader,
                                              vars );
        for ( Task task : tasks ) {
            taskSession.addTask( task, null );
        }

        reader = new InputStreamReader( getClass().getResourceAsStream( "../QueryResults_PeopleAssignmentQuerries.mvel" ) );
        Map<String, List<TaskSummary>> expected = (Map<String, List<TaskSummary>>) eval( reader,
                                                                                         vars );

        BlockingAllOpenTasksForUseResponseHandler responseHandler = new BlockingAllOpenTasksForUseResponseHandler();
        client.getTasksAssignedAsTaskInitiator( users.get( "darth" ).getId(),
                                                "en-UK",
                                                responseHandler );
        List<TaskSummary> actual = responseHandler.getResults();
        assertEquals( 1,
                      actual.size() );
        assertTrue( CollectionUtils.equals( expected.get( "darth" ),
                                            actual ) );

        responseHandler = new BlockingAllOpenTasksForUseResponseHandler();
        client.getTasksAssignedAsBusinessAdministrator( users.get( "steve" ).getId(),
                                                        "en-UK",
                                                        responseHandler );
        actual = responseHandler.getResults();
        assertTrue( CollectionUtils.equals( expected.get( "steve" ),
                                            actual ) );

        responseHandler = new BlockingAllOpenTasksForUseResponseHandler();
        client.getTasksAssignedAsExcludedOwner( users.get( "liz" ).getId(),
                                                "en-UK",
                                                responseHandler );
        actual = responseHandler.getResults();
        assertEquals( 2,
                      actual.size() );
        assertTrue( CollectionUtils.equals( expected.get( "liz" ),
                                            actual ) );

        responseHandler = new BlockingAllOpenTasksForUseResponseHandler();
        client.getTasksAssignedAsPotentialOwner( users.get( "bobba" ).getId(),
                                                 "en-UK",
                                                 responseHandler );
        actual = responseHandler.getResults();
        assertEquals( 3,
                      actual.size() );
        assertTrue( CollectionUtils.equals( expected.get( "bobba" ),
                                            actual ) );

        responseHandler = new BlockingAllOpenTasksForUseResponseHandler();
        client.getTasksAssignedAsRecipient( users.get( "sly" ).getId(),
                                            "en-UK",
                                            responseHandler );
        actual = responseHandler.getResults();
        assertEquals( 1,
                      actual.size() );
        assertTrue( CollectionUtils.equals( expected.get( "sly" ),
                                            actual ) );
    }

	public static class BlockingAllOpenTasksForUseResponseHandler
        implements
        TaskSummaryResponseHandler {
        private volatile List<TaskSummary> results;
        private volatile RuntimeException error;

        public synchronized void execute(List<TaskSummary> results) {
            this.results = results;
            notifyAll();
        }

        public synchronized List<TaskSummary> getResults() {
            if ( results == null ) {
                try {
                    wait( 3000 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }

            if ( results == null ) {
                throw new RuntimeException( "Timeout : unable to retrieve results" );
            }

            return results;

        }

        public boolean isDone() {
            synchronized ( results ) {
                return results != null;                
            }
        }

        public void setError(RuntimeException error) {
            this.error = error;            
        }
        
        public RuntimeException getError() {
            return error;
        }

    }

}
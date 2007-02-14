package org.drools.brms.client.admin;

import org.drools.brms.client.common.FormStyleLayout;
import org.drools.brms.client.common.GenericCallback;
import org.drools.brms.client.common.LoadingPopup;
import org.drools.brms.client.rpc.RepositoryServiceFactory;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class StateManager extends Composite {

    private ListBox currentStatuses;


    public StateManager() {
        FormStyleLayout form = new FormStyleLayout("images/status_large.png", "Manage statuses");
        form.addAttribute( "", new HTML("<i>Status tags are for the lifecycle of an asset.</i>") );
        
        currentStatuses = new ListBox();
        currentStatuses.setVisibleItemCount( 7 );
        currentStatuses.setWidth( "50%" );
        
        refreshList( );
        
        form.addAttribute( "Current statuses:", currentStatuses );
        
        form.addAttribute( "Add new status:", newStatusEditor() );
        initWidget( form );
    }

    private void refreshList() {
        LoadingPopup.showMessage( "Loading statuses..." );
        RepositoryServiceFactory.getService().listStates( new GenericCallback() {
            public void onSuccess(Object data) {
                currentStatuses.clear();
                String[] statii = (String[]) data;
                for ( int i = 0; i < statii.length; i++ ) {                    
                    currentStatuses.addItem( statii[i] );
                }               
                LoadingPopup.close();
            }
        });
    }

    private Widget newStatusEditor() {
        HorizontalPanel horiz = new HorizontalPanel();
        final TextBox box = new TextBox();
        
        Button create = new Button("Create");
        create.addClickListener( new ClickListener() {
            public void onClick(Widget w) {
                createStatus(box);                    
            }
        });
        
        horiz.add( box );
        horiz.add( create );

        return horiz;
    }

    
    private void createStatus(final TextBox box) {
        LoadingPopup.showMessage( "Creating status" );
        RepositoryServiceFactory.getService().createState( box.getText(), new GenericCallback() {           
            public void onSuccess(Object data) {
                box.setText( "" );
                refreshList();
                LoadingPopup.close();
            }
        });
        
    }
    
}

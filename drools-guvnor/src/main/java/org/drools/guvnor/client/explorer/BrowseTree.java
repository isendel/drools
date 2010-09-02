/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.guvnor.client.explorer;

import java.util.HashMap;
import java.util.Map;

import org.drools.guvnor.client.common.GenericCallback;
import org.drools.guvnor.client.common.Inbox;
import org.drools.guvnor.client.rpc.*;
import org.drools.guvnor.client.ruleeditor.MultiViewRow;
import org.drools.guvnor.client.rulelist.AssetItemGrid;
import org.drools.guvnor.client.rulelist.AssetItemGridDataLoader;
import org.drools.guvnor.client.rulelist.EditItemEvent;
import org.drools.guvnor.client.images.Images;
import org.drools.guvnor.client.messages.Constants;

import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Command;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;


public class BrowseTree extends AbstractTree {
    private static Constants constants = GWT.create(Constants.class);
    private static Images images = (Images) GWT.create(Images.class);       

    private Map<TreeItem, String> itemWidgets = new HashMap<TreeItem, String>();


    /** Table set up for the inboxes */
    static {
            TableConfig conf = new TableConfig();
            conf.headers = new String[2];
            conf.headers[0] = constants.Name();// "Name ";
            conf.headers[1] = constants.Date();//"Date ";
            conf.headerTypes = new String[2];
            conf.headerTypes[0] = "class java.lang.String";
            conf.headerTypes[1] = "class java.util.Calendar";
            conf.rowsPerPage = 500;
            AssetItemGrid.registerTableConf(conf, Inbox.RECENT_EDITED);
            AssetItemGrid.registerTableConf(conf, Inbox.RECENT_VIEWED);

            conf = new TableConfig();
            conf.headers = new String[3];
            conf.headers[0] = constants.Name();
            conf.headers[1] = constants.Date();
            conf.headers[2] = constants.From();
            conf.headerTypes = new String[3];
            conf.headerTypes[0] = "class java.lang.String";
            conf.headerTypes[1] = "class java.util.Calendar";
            conf.headerTypes[2] = "class java.lang.String";
            conf.rowsPerPage = 500;

            AssetItemGrid.registerTableConf(conf, Inbox.INCOMING);
    }
       
    public BrowseTree(ExplorerViewCenterPanel tabbedPanel) {
        super(tabbedPanel);
        this.name = constants.Browse();
        this.image = images.ruleAsset();
        
        mainTree = ExplorerNodeConfig.getBrowseTree(itemWidgets);

        //Add Selection listener
        mainTree.addSelectionHandler(this);
    }

    // Show the associated widget in the deck panel
    public void onSelection(SelectionEvent<TreeItem> event) {
        TreeItem item = event.getSelectedItem();
        String widgetID = itemWidgets.get(item);
         
/*        //this refreshes the list.
        if (content.equals(ExplorerNodeConfig.CATEGORY_ID)) { 
            //self.getParentNode().replaceChild(ExplorerNodeConfig.getCategoriesStructure(), self);
        } else if (content.equals(ExplorerNodeConfig.STATES_ID)) {   
            //self.getParentNode().replaceChild(ExplorerNodeConfig.getStatesStructure(), self);
        } else */
        	
        if (widgetID.equals(ExplorerNodeConfig.FIND_ID)) {     
            centertabbedPanel.openFind();
        } else if (widgetID.equals(ExplorerNodeConfig.INCOMING_ID) || 
        		widgetID.equals(ExplorerNodeConfig.RECENT_EDITED_ID) ||
        		widgetID.equals(ExplorerNodeConfig.RECENT_VIEWED_ID)) {
       		openInbox(item.getText(), widgetID);
        } else if (widgetID.startsWith(ExplorerNodeConfig.STATES_ID)){
            openState(item.getText(), widgetID);
        } else if (widgetID.startsWith(ExplorerNodeConfig.CATEGORY_ID)){
            openCategory(item.getText(), widgetID);
        }
    }  

    /**
     * Show the inbox of the given name.
     */
    private void openInbox(String title, final String widgetID) {   	
        if (!centertabbedPanel.showIfOpen(widgetID)) {
            AssetItemGrid g = new AssetItemGrid(createEditEvent(), widgetID, new AssetItemGridDataLoader() {
                public void loadData(int startRow, int numberOfRows, GenericCallback<TableDataResult> cb) {
                    RepositoryServiceFactory.getService().loadInbox(widgetID, cb);
                }
            });
            centertabbedPanel.addTab(title, true, g, widgetID);
        }
    }

    /**
     * open a state or category !
     */
    private void openState(String title, String widgetID) {

        if (!centertabbedPanel.showIfOpen(widgetID)) {
        	final String stateName = widgetID.substring(widgetID.indexOf("-") + 1);
       	    final AssetItemGrid list = new AssetItemGrid(createEditEvent(),
                    AssetItemGrid.RULE_LIST_TABLE_ID,
                    new AssetItemGridDataLoader() {
                        public void loadData(int skip, int numberOfRows, GenericCallback cb) {
                             RepositoryServiceFactory.getService().
                                        loadRuleListForState(stateName, skip,
                                                numberOfRows, AssetItemGrid.RULE_LIST_TABLE_ID, cb);
                            
                        }
                    },
                    null);
           final ServerPushNotification push = new ServerPushNotification() {
                public void messageReceived(PushResponse response) {
                        if (response.messageType.equals("statusChange") && (response.message).equals(stateName)) {
                            list.refreshGrid();
                        }
                }
            };
            PushClient.instance().subscribe(push);
            list.addUnloadListener(new Command() {
                public void execute() {
                    PushClient.instance().unsubscribe(push);
                }
            });

            centertabbedPanel.addTab(constants.Status() + title, true, list, widgetID);
        }
    }

    /**
     * open a category 
     */
    private void openCategory(String title, String widgetID) {
        if (!centertabbedPanel.showIfOpen(widgetID)) {
    	    final String categoryName = widgetID.substring(widgetID.indexOf("-") + 1);
            final AssetItemGrid list = new AssetItemGrid(createEditEvent(),
                    AssetItemGrid.RULE_LIST_TABLE_ID,
                    new AssetItemGridDataLoader() {
                        public void loadData(int skip, int numberOfRows, GenericCallback cb) {
                                RepositoryServiceFactory.getService().
                                        loadRuleListForCategories(categoryName, skip, numberOfRows,
                                                AssetItemGrid.RULE_LIST_TABLE_ID, cb);                           
                        }
                    },
                    GWT.getModuleBaseURL() + "feed/category?name=" + categoryName + "&viewUrl=" + getSelfURL());
           final ServerPushNotification push = new ServerPushNotification() {
                public void messageReceived(PushResponse response) {
                        if (response.messageType.equals("categoryChange") && response.message.equals(categoryName)) {
                            list.refreshGrid();
                        }
                }
            };
            PushClient.instance().subscribe(push);
            list.addUnloadListener(new Command() {
                public void execute() {
                    PushClient.instance().unsubscribe(push);
                }
            });

            centertabbedPanel.addTab((constants.CategoryColon()) + title, true, list, widgetID);
        }
    }
    
    private EditItemEvent createEditEvent() {
        return new EditItemEvent() {
            public void open(String uuid) {
                centertabbedPanel.openAsset(uuid);
            }

            public void open(MultiViewRow[] rows) {
                for ( MultiViewRow row: rows) {
                    centertabbedPanel.openAsset( row.uuid );
                }
            }
        };
    }

    /**
     * The URL that will be used to open up assets in a feed.
     * (by tacking asset id on the end, of course !). 
     */
    public static String getSelfURL() {
        String selfURL = Window.Location.getHref();
        if (selfURL.contains("#")) {
            selfURL = selfURL.substring(0, selfURL.indexOf("#"));
        }
        return selfURL;
    }
}

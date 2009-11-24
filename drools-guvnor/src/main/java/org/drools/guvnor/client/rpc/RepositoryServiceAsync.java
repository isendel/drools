package org.drools.guvnor.client.rpc;
/*
 * Copyright 2005 JBoss Inc
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



import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;

/**

 * This is what the remote service will implement, as a servlet.

 * (in hosted/debug mode, you could also use an implementation that was in-process).

 */

public interface RepositoryServiceAsync
    extends
    RemoteService {

	/** PLACE THE FOLLOWING IN RepositoryServiceAsync.java **/

	/** Generated by AsyncInterfaceGenerator hackery */
	public void loadChildCategories(java.lang.String p0, AsyncCallback<java.lang.String[]> cb);
	public void loadRuleListForCategories(java.lang.String p0, int p1, int p2, java.lang.String p3, AsyncCallback<org.drools.guvnor.client.rpc.TableDataResult> cb);
	public void loadRuleListForState(java.lang.String p0, int p1, int p2, java.lang.String p3, AsyncCallback<org.drools.guvnor.client.rpc.TableDataResult> cb);
	public void loadTableConfig(java.lang.String p0, AsyncCallback<org.drools.guvnor.client.rpc.TableConfig> cb);
	public void createCategory(java.lang.String p0, java.lang.String p1, java.lang.String p2, AsyncCallback<java.lang.Boolean> cb);
	public void createNewRule(java.lang.String p0, java.lang.String p1, java.lang.String p2, java.lang.String p3, java.lang.String p4, AsyncCallback<java.lang.String> cb);
	public void createNewImportedRule(java.lang.String p0, java.lang.String p1, AsyncCallback<java.lang.String> cb);
	public void deleteUncheckedRule(java.lang.String p0, java.lang.String p1, AsyncCallback cb);
	public void clearRulesRepository(AsyncCallback cb);
	public void listPackages(AsyncCallback<org.drools.guvnor.client.rpc.PackageConfigData[]> cb);
	public void loadGlobalPackage(AsyncCallback<org.drools.guvnor.client.rpc.PackageConfigData> cb);
	public void listArchivedPackages(AsyncCallback<org.drools.guvnor.client.rpc.PackageConfigData[]> cb);
	public void loadRuleAsset(java.lang.String p0, AsyncCallback<org.drools.guvnor.client.rpc.RuleAsset> cb);
	public void loadAssetHistory(java.lang.String p0, AsyncCallback<org.drools.guvnor.client.rpc.TableDataResult> cb);
	public void loadArchivedAssets(int p0, int p1, AsyncCallback<org.drools.guvnor.client.rpc.TableDataResult> cb);
	public void checkinVersion(org.drools.guvnor.client.rpc.RuleAsset p0, AsyncCallback<java.lang.String> cb);
	public void restoreVersion(java.lang.String p0, java.lang.String p1, java.lang.String p2, AsyncCallback cb);
	public void createPackage(java.lang.String p0, java.lang.String p1, AsyncCallback<java.lang.String> cb);
	public void loadPackageConfig(java.lang.String p0, AsyncCallback<org.drools.guvnor.client.rpc.PackageConfigData> cb);
	public void savePackage(org.drools.guvnor.client.rpc.PackageConfigData p0, AsyncCallback<org.drools.guvnor.client.rpc.ValidatedResponse> cb);
	public void listAssets(java.lang.String p0, java.lang.String[] p1, int p2, int p3, java.lang.String p4, AsyncCallback<org.drools.guvnor.client.rpc.TableDataResult> cb);
	public void listStates(AsyncCallback<java.lang.String[]> cb);
	public void createState(java.lang.String p0, AsyncCallback<java.lang.String> cb);
	public void renameState(java.lang.String p0, java.lang.String p1, AsyncCallback cb);
	public void removeState(java.lang.String p0, AsyncCallback cb);
	public void changeState(java.lang.String p0, java.lang.String p1, boolean p2, AsyncCallback cb);
	public void changeAssetPackage(java.lang.String p0, java.lang.String p1, java.lang.String p2, AsyncCallback cb);
	public void copyAsset(java.lang.String p0, java.lang.String p1, java.lang.String p2, AsyncCallback<java.lang.String> cb);
	public void copyPackage(java.lang.String p0, java.lang.String p1, AsyncCallback cb);
	public void listSnapshots(java.lang.String p0, AsyncCallback<org.drools.guvnor.client.rpc.SnapshotInfo[]> cb);
	public void createPackageSnapshot(java.lang.String p0, java.lang.String p1, boolean p2, java.lang.String p3, AsyncCallback cb);
	public void copyOrRemoveSnapshot(java.lang.String p0, java.lang.String p1, boolean p2, java.lang.String p3, AsyncCallback cb);
	public void quickFindAsset(java.lang.String p0, int p1, boolean p2, AsyncCallback<org.drools.guvnor.client.rpc.TableDataResult> cb);
	public void removeCategory(java.lang.String p0, AsyncCallback cb);
	public void loadSuggestionCompletionEngine(java.lang.String p0, AsyncCallback<org.drools.guvnor.client.modeldriven.SuggestionCompletionEngine> cb);
	public void buildPackage(java.lang.String p0, boolean p1, java.lang.String p2, java.lang.String p3, java.lang.String p4, boolean p5, java.lang.String p6, java.lang.String p7, boolean p8, java.lang.String p9, AsyncCallback<org.drools.guvnor.client.rpc.BuilderResult[]> cb);
	public void getCustomSelectors(AsyncCallback<java.lang.String[]> cb);
	public void buildPackageSource(java.lang.String p0, AsyncCallback<java.lang.String> cb);
	public void buildAssetSource(org.drools.guvnor.client.rpc.RuleAsset p0, AsyncCallback<java.lang.String> cb);
	public void buildAsset(org.drools.guvnor.client.rpc.RuleAsset p0, AsyncCallback<org.drools.guvnor.client.rpc.BuilderResult[]> cb);
	public void renameAsset(java.lang.String p0, java.lang.String p1, AsyncCallback<java.lang.String> cb);
	public void renameCategory(java.lang.String p0, java.lang.String p1, AsyncCallback cb);
	public void archiveAsset(java.lang.String p0, boolean p1, AsyncCallback cb);
	public void removeAsset(java.lang.String p0, AsyncCallback cb);
	public void removePackage(java.lang.String p0, AsyncCallback cb);
	public void renamePackage(java.lang.String p0, java.lang.String p1, AsyncCallback<java.lang.String> cb);
	public void rebuildSnapshots(AsyncCallback cb);
	public void rebuildPackages(AsyncCallback cb);
	public void listRulesInPackage(java.lang.String p0, AsyncCallback<java.lang.String[]> cb);
	public void runScenario(java.lang.String p0, org.drools.guvnor.client.modeldriven.testing.Scenario p1, AsyncCallback<org.drools.guvnor.client.rpc.SingleScenarioResult> cb);
	public void runScenariosInPackage(java.lang.String p0, AsyncCallback<org.drools.guvnor.client.rpc.BulkTestRunResult> cb);
	public void analysePackage(java.lang.String p0, AsyncCallback<org.drools.guvnor.client.rpc.AnalysisReport> cb);
	public void listTypesInPackage(java.lang.String p0, AsyncCallback<java.lang.String[]> cb);
	public void cleanLog(AsyncCallback cb);
	public void showLog(AsyncCallback<org.drools.guvnor.client.rpc.LogEntry[]> cb);
	public void loadDropDownExpression(java.lang.String[] p0, java.lang.String p1, AsyncCallback<java.lang.String[]> cb);
	public void queryFullText(java.lang.String p0, boolean p1, int p2, int p3, AsyncCallback<org.drools.guvnor.client.rpc.TableDataResult> cb);
	public void queryMetaData(org.drools.guvnor.client.rpc.MetaDataQuery[] p0, java.util.Date p1, java.util.Date p2, java.util.Date p3, java.util.Date p4, boolean p5, int p6, int p7, AsyncCallback<org.drools.guvnor.client.rpc.TableDataResult> cb);
	public void listUserPermissions(AsyncCallback cb);
	public void retrieveUserPermissions(java.lang.String p0, AsyncCallback cb);
	public void updateUserPermissions(java.lang.String p0, java.util.Map p1, AsyncCallback cb);
	public void listAvailablePermissionTypes(AsyncCallback<java.lang.String[]> cb);
	public void deleteUser(java.lang.String p0, AsyncCallback cb);
	public void getAssetLockerUserName(java.lang.String p0, AsyncCallback<java.lang.String> cb);
	public void lockAsset(java.lang.String p0, AsyncCallback cb);
	public void unLockAsset(java.lang.String p0, AsyncCallback cb);
	public void installSampleRepository(AsyncCallback cb);
    public void loadDiscussionForAsset(String assetId, AsyncCallback<List<DiscussionRecord>> dr);
    public void addToDiscussionForAsset(String assetId, String comment,  AsyncCallback<List<DiscussionRecord>> response);
    public void clearAllDiscussionsForAsset(String assetId, AsyncCallback cb);
    public void subscribe(AsyncCallback<List<PushResponse>> cb);
    public void loadInbox(String inboxName, AsyncCallback<TableDataResult> cb);
    public void compareSnapshots(java.lang.String p0, java.lang.String p1, java.lang.String p2, AsyncCallback<org.drools.guvnor.client.rpc.SnapshotDiffs> cb);


}
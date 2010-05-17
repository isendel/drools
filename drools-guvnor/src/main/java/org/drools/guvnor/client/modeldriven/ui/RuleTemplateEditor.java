package org.drools.guvnor.client.modeldriven.ui;

import java.util.HashSet;
import java.util.Set;

import org.drools.guvnor.client.common.DirtyableComposite;
import org.drools.guvnor.client.messages.Constants;
import org.drools.guvnor.client.rpc.RuleAsset;
import org.drools.ide.common.client.modeldriven.dt.TemplateModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.GroupingStore;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarMenuButton;
import com.gwtext.client.widgets.event.TabPanelListenerAdapter;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.GridView;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridListenerAdapter;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class RuleTemplateEditor extends DirtyableComposite implements RuleModelEditor {

	private TemplateModel model;
	private GroupingStore store = null;
	private EditorGridPanel grid = null;
	private RuleModeller ruleModeller;
	private Constants constants = ((Constants) GWT.create(Constants.class));
	

	public RuleTemplateEditor(RuleAsset asset) {
		model = (TemplateModel) asset.content;
		TabPanel tPanel = new TabPanel();
		tPanel.setWidth(800);
//		tPanel.setAutoWidth(true);
//		tPanel.setAutoHeight(true);
		
		Panel pnl = new Panel();
//		pnl.setAutoWidth(true);
		pnl.setClosable(false);
		pnl.setTitle("Template Editor");
//		pnl.setAutoHeight(true);
		ruleModeller = new RuleModeller(asset, new TemplateModellerWidgetFactory());
		pnl.add(ruleModeller);
		tPanel.add(pnl);

		pnl = new Panel();
//		pnl.setAutoWidth(true);
		pnl.setClosable(false);
		pnl.setTitle("Template Data");
//		pnl.setAutoHeight(true);
		pnl.add(buildTemplateTable());
		pnl.setId("tplTable");
		tPanel.add(pnl);
		
		tPanel.addListener(new TabPanelListenerAdapter() {
			
			@Override
			public boolean doBeforeTabChange(TabPanel source, Panel newPanel, Panel oldPanel) {
				if ("tplTable".equals(newPanel.getId())) {
					Set<String> keySet = new HashSet<String>(model.getTable().keySet());
					model.putInSync();
					if (!keySet.equals(model.getTable().keySet()) || model.getRowsCount() == 0) {
						newPanel.clear();
						newPanel.add(buildTemplateTable());
					}
				}
				return true;
			}
		});

		tPanel.setActiveTab(0);
		initWidget(tPanel);
	}

	private Widget buildTemplateTable() {

		String[] vars = model.getInterpolationVariablesList();
		if (vars.length == 0) {
			return new Label("");
		}

		FieldDef[] fds = new FieldDef[vars.length];
		ColumnConfig[] cols = new ColumnConfig[fds.length];

		int idx = 0;
		for (String var: vars) {
			cols[idx] = new ColumnConfig();
			cols[idx].setHeader(var);
			cols[idx].setDataIndex(var);
			cols[idx].setSortable(false);
			cols[idx].setWidth(50);
			cols[idx].setResizable(true);
			cols[idx].setEditor(new GridEditor(new TextField()));
			fds[idx] = new StringFieldDef(var);
			idx++;
		}
		final RecordDef recordDef = new RecordDef(fds);
		ArrayReader reader = new ArrayReader(recordDef);
		
		MemoryProxy proxy = new MemoryProxy(model.getTableAsArray());

		ColumnModel cm = new ColumnModel(cols);
		
		for (int i = 0; i < cm.getColumnCount(); i++) {
			cm.setEditable(i, true);
		}
		store = new GroupingStore(proxy, reader);
		store.load();
		grid = new EditorGridPanel(store, cm);
		grid.setStripeRows(true);

//		GroupingView gv = new GroupingView();
		GridView gv = new GridView(); 

		// to stretch it out
		gv.setForceFit(true);
//		gv.setGroupTextTpl("{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"" // NON-NLS
//				+ constants.Items() + "\" : \"" + constants.Item() + "\"]})");

		grid.setView(gv);

		grid.setStore(store);
		grid.setAutoWidth(true);
		grid.setAutoHeight(true);

		Toolbar tb = new Toolbar();
		Menu menu = new Menu();

		menu.addItem(new Item(constants.AddRow(), new BaseItemListenerAdapter() {
			public void onClick(BaseItem item, EventObject e) {
				String[] rowData = new String[recordDef.getFields().length];
				for (int i = 0; i < rowData.length; i++) {
					rowData[i] = "";
				}
				Record newRecord = recordDef.createRecord(rowData);
				store.add(newRecord);
				model.addRow(newRecord.getId(), rowData);
			}
		}));
		
		menu.addItem(new Item(constants.RemoveSelectedRowS(), new BaseItemListenerAdapter() {
			public void onClick(BaseItem item, EventObject e) {
				removeSelectedRows(grid);
			}
		}));
		
		grid.addGridListener(new GridListenerAdapter() {
			
			public void onKeyPress(EventObject e) {
				int k = e.getKey();
				if (k == KeyCodes.KEY_DELETE || k == KeyCodes.KEY_BACKSPACE) {
					removeSelectedRows(grid);
				} 
				else if (k == KeyCodes.KEY_ENTER) {
					int[] selectedCell = grid.getCellSelectionModel().getSelectedCell();
					grid.startEditing(selectedCell[0], selectedCell[1]);
				}
			}
		});
//		grid.setSelectionModel(new RowSelectionModel(false));
		ToolbarMenuButton tbb = new ToolbarMenuButton(constants.Modify(), menu);
		tb.addButton(tbb);
		grid.add(tb);
		
		grid.addEditorGridListener(new EditorGridListenerAdapter() {
			@Override
			public void onAfterEdit(GridPanel grid, Record record, String field, Object newValue, Object oldValue,
					int rowIndex, int colIndex) {
				model.setValue(field, rowIndex, (String) newValue);
			}
		});

		return grid;
	}
	
	private void removeSelectedRows(EditorGridPanel grid) {
		if (com.google.gwt.user.client.Window.confirm(constants.AreYouSureYouWantToDeleteTheSelectedRowS())) {
			int row = grid.getCellSelectionModel().getSelectedCell()[0];
			Record rec = store.getAt(row);
			model.removeRowById(rec.getId());
			store.remove(rec);
		}
	}
	
	@Override
	public void resetDirty() {
		super.resetDirty();
		if (store != null) {
			store.commitChanges();
		}
	}

	public RuleModeller getRuleModeller() {
		return ruleModeller;
	}
}

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

package org.drools.guvnor.client.ruleeditor;

import org.drools.guvnor.client.common.SmallLabel;
import org.drools.guvnor.client.common.ClickableLabel;
import org.drools.guvnor.client.messages.Constants;
import org.drools.ide.common.client.modeldriven.SuggestionCompletionEngine;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.GWT;

public class FactTypeBrowser extends Composite {
    private Constants constants = ((Constants) GWT.create(Constants.class));


    public FactTypeBrowser(SuggestionCompletionEngine sce, final ClickEvent ev) {
		Tree tree = new Tree();

		final VerticalPanel panel = new VerticalPanel();
    
        HorizontalPanel hp = new HorizontalPanel();

        hp.add(new SmallLabel(constants.FactTypes()));
        hp.add(new ClickableLabel(constants.hide(), new ClickListener() {
            public void onClick(Widget sender) {
              panel.setVisible(false);
            }
        }));
		panel.add(hp);

		panel.add(tree);
		if (sce.getFactTypes() != null) {
			for (String type : sce.getFactTypes()) {
				TreeItem it = new TreeItem();
				it.setHTML("<img src=\"images/class.gif\"/><small>" + type + "</small>");
				it.setUserObject(type + "( )");
				tree.addItem(it);

				String[] fields = (String[]) sce.getModelFields(type);
				if (fields != null) {
					for (String field : fields) {
						TreeItem fi = new TreeItem();
						fi.setHTML("<img src=\"images/field.gif\"/><small>" + field + "</small>");
						fi.setUserObject(field);
						it.addItem(fi);
					}
				}
			}
		}

		tree.setStyleName( "category-explorer-Tree" ); //NON-NLS
		tree.addTreeListener(new TreeListener() {
			public void onTreeItemSelected(TreeItem t) {
				Object o = t.getUserObject();
				if (o instanceof String) {
					ev.selected((String) o);
				}
			}
			public void onTreeItemStateChanged(TreeItem arg0) {}
		});

		initWidget(panel);
	}

	public static interface ClickEvent {
		public void selected(String text);
	}

}



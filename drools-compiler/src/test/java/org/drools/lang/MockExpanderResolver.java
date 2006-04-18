package org.drools.lang;
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



import java.util.HashMap;
import java.util.Map;

public class MockExpanderResolver
    implements
    ExpanderResolver {

    private Map resolveCalls = new HashMap();
    private MockExpander expander = new MockExpander();
    
    public Expander get(String name,
                        String config) {
        resolveCalls.put( name, config );
        return expander;
    }

    /**
     * Check if it was called.
     */
    public boolean checkCalled(String name) {
        return resolveCalls.containsKey( name );
    }
    
    public String getConfigFor(String name) {
        return (String) resolveCalls.get( name );
    }
    
    public boolean checkExpanded(String patternOriginal) {
        return expander.checkPattern( patternOriginal );
    }
}
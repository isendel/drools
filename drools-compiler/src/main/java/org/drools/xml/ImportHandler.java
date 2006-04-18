package org.drools.xml;
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



import java.util.HashSet;

import org.drools.lang.descr.FunctionDescr;
import org.drools.lang.descr.PackageDescr;
import org.drools.lang.descr.RuleDescr;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author mproctor
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
class ImportHandler extends BaseAbstractHandler implements Handler
{
    ImportHandler( PackageReader packageReader )
    {
        this.packageReader = packageReader;

        if ( (this.validParents == null) && (validPeers == null) )
        {
            this.validParents = new HashSet( );
            this.validParents.add( Package.class );

            this.validPeers = new HashSet( );
            this.validPeers.add( null );
            this.validPeers.add( ImportEntryDummy.class );
            this.validPeers.add( GlobalDummy.class );
            this.validPeers.add( FunctionDescr.class );
            this.validPeers.add( RuleDescr.class );            

            this.allowNesting = false;
        }
    }

    public Object start( String uri, String localName, Attributes attrs ) throws SAXException
    {
        packageReader.startConfiguration( localName, attrs );
        return null;
    }

    public Object end( String uri, String localName ) throws SAXException
    {
        Configuration config = packageReader.endConfiguration( );
        
        PackageDescr packageDescr = this.packageReader.getPackageDescr();
        
        packageDescr.addImport( config.getText() );
        
        return new ImportEntryDummy();
    }

    public Class generateNodeFor()
    {
        return ImportEntryDummy.class;
    }
}
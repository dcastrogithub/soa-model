/* Copyright 2012 predic8 GmbH, www.predic8.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */

package com.predic8.wsdl

import groovy.xml.QName as GQName
import com.predic8.xml.util.*
import com.predic8.wsdl.creator.*
import groovy.xml.MarkupBuilder

class WSDLImportPcvTest extends GroovyTestCase{

  Definitions wsdl

  void setUp(){
	  def parser = new WSDLParser()
	  wsdl = parser.parse("src\\test\\resources\\import-pcv\\facturacionService.wsdl")
  }
  
  void testCreator() {
    def strWriter = new StringWriter()
    def creator = new WSDLCreator(builder : new MarkupBuilder(strWriter))
	try{
	    creator.createDefinitions(wsdl, new WSDLCreatorContext())
	    def createdWSDL = new XmlSlurper().parseText(strWriter.toString())
		assert(false)
	}
	catch(Throwable t){
		assert t.message.contains("Could not find the portType definition")
	}
  }
}


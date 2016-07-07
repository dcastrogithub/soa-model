package com.predic8.ComplexTypeIssue

import com.predic8.schema.Schema;
import com.predic8.schema.SchemaParser
import com.predic8.schema.creator.SchemaCreator
import com.predic8.schema.creator.SchemaCreatorContext
import com.predic8.wsdl.Definitions
import com.predic8.wsdl.creator.WSDLCreator
import com.predic8.wsdl.creator.WSDLCreatorContext

import groovy.xml.MarkupBuilder

import com.predic8.xml.util.*

class ComplexTypeIssueTest extends GroovyTestCase{

	 Schema schema

	 void setUp() {
	    def parser = new SchemaParser(resourceResolver: new ClasspathResolver())
		schema = parser.parse("/ota/OTA_AirCommonTypes.xsd")
	}
	
	  void testCreatorOutput() {
		System.out.println("Creator Output")
		schema.complexTypes.each { complexType ->
			System.out.println("ComplexType:" + complexType)
		}
		
		schema.attributeGroups.each { attributeGroup ->
			System.out.println("AttributeGroup:" + attributeGroup)
		}
	}

}
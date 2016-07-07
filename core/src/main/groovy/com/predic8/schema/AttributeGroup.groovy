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

package com.predic8.schema;

import java.util.List;

import groovy.xml.*

import com.predic8.soamodel.CreatorContext
import com.predic8.wstool.creator.*

import javax.xml.stream.*

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AttributeGroup extends SchemaComponent{
	
  private static final Logger log = LoggerFactory.getLogger(AttributeGroup.class)

  QName ref
  List<Attribute> attributes = []
  List<AttributeGroup> attributeGroups = []
  AnyAttribute anyAttribute

  protected parseAttributes(token, params){
    super.parseAttributes(token, params)
    ref = getTypeQName(token.getAttributeValue( null , 'ref'))
  }

  protected parseChildren(token, child, params){
    switch (child ){
      case 'attribute' :
        def attribute = new Attribute(schema:schema)
        attribute.parse(token, params)
          attributes << attribute ; break
      case 'attributeGroup' :
        def attributeGroup = new AttributeGroup(schema:schema)
        attributeGroup.parse(token, params)
          attributeGroups << attributeGroup ; break
      case 'anyAttribute' :
        anyAttribute = new AnyAttribute(schema: schema)
        anyAttribute.parse(token, params) ; break
    }
  }

  Attribute getAttribute(String name){
    attributes.find{it.name == name}
  }

  List<Attribute> getAttributesFromRef(){
    if(!ref) return
		log.debug(LOG_INDENT + "Return AttributeGroup for:" + ref)
      schema.getAttributeGroup(ref).allAttributes?.flatten()
  }

  List<Attribute> getAllAttributes(){
	log.debug(LOG_INDENT + " > > > AtributeGroup Attributes:" + attributes)
	log.debug(LOG_INDENT + " > > > AtributeGroup Ref:" + ref)
    def res = attributes.collect() ?: attributesFromRef  ?: []
	log.debug(LOG_INDENT + " > > > > Flattened attributes:" + res)
	log.debug(LOG_INDENT + " > > > AtributeGroup attributeGroups:")
    attributeGroups.each {  
							def derivedAttrs =  it.allAttributes
							log.debug(LOG_INDENT + "     <-- " + derivedAttrs)
							res << derivedAttrs }
	log.debug(LOG_INDENT + "Merged Attributes:" + res)
    res.flatten()
  }

  protected getElementName(){
    'attributeGroup'
  }

  def create(creator, CreatorContext ctx){
    creator.createAttributeGroup(this, ctx.clone())
  }

  def compare(generator, other){
    generator.compareGroups(this, other)
  }

  String toString(){
    "attributeGroup[name= $name, ref=$ref,attributes=$attributes, attributeGroups=$attributeGroups]"
  }
}

package com.netmng.websvc.rest.server.wadl;

import java.util.List;

import com.sun.jersey.api.wadl.config.WadlGeneratorConfig;
import com.sun.jersey.api.wadl.config.WadlGeneratorDescription;
import com.sun.jersey.server.wadl.generators.WadlGeneratorJAXBGrammarGenerator;

public class NetmngWadlGeneratorConfig extends WadlGeneratorConfig {

	@Override
	public List<WadlGeneratorDescription> configure() {
		// TODO Auto-generated method stub
		return 
				/*
				generator(WadlGeneratorApplicationDoc.class)
					
				.prop("applicationDocsStream", "classpath:config/rest/application-doc.xml")
					.generator(WadlGeneratorGrammarsSupport.class)
					.prop("grammarsStream", "classpath:config/rest/application-grammars.xml")         
					.generator(WadlGeneratorResourceDocSupport.class)
					.prop("resourceDocStream", "classpath:config/rest/resourcedoc.xml")
					.descriptions();
				*/
				generator(WadlGeneratorJAXBGrammarGenerator.class).descriptions();
	}

}

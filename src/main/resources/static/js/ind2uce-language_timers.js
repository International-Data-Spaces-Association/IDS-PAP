// default policy template in editor.
var ind2uceXml="<timer xmlns=\'http://www.iese.fraunhofer.de/ind2uce/3.2.46/ind2uceLanguageTimer\' xmlns:tns=\'http://www.iese.fraunhofer.de/ind2uce/3.2.46/ind2uceLanguageTimer\' xmlns:parameter=\'http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter\' xmlns:pip=\'http://www.iese.fraunhofer.de/ind2uce/3.2.46/pip\' xmlns:function=\'http://www.iese.fraunhofer.de/ind2uce/3.2.46/function\' xmlns:event=\'http://www.iese.fraunhofer.de/ind2uce/3.2.46/event\' xmlns:constant=\'http://www.iese.fraunhofer.de/ind2uce/3.2.46/constant\' xmlns:variable=\'http://www.iese.fraunhofer.de/ind2uce/3.2.46/variable\' " +
		"xmlns:variableDeclaration=\'http://www.iese.fraunhofer.de/ind2uce/3.2.46/variableDeclaration\' " +
		"xmlns:valueChanged=\'http://www.iese.fraunhofer.de/ind2uce/3.2.46/valueChanged\' " +
		"xmlns:xsi=\'http://www.w3.org/2001/XMLSchema-instance\' cron=\'\' id=\'\'><event action=\'\'></event></timer>";

// IND2UCE language specification for the timer editor.
var ind2uceSpec1={
	onchange: function(jsSurrogate){
	},
	validate: function(jsElement){
		if(typeof(jsElement)=="string") jsElement=Xonomy.xml2js(jsElement);
		var valid=true;
		var elementSpec=this.elements[jsElement.name];
		if(elementSpec.validate) {
      if(!elementSpec.validate(jsElement)) {  	  
    	  valid=false;}
		}
		for(var iAttribute=0; iAttribute<jsElement.attributes.length; iAttribute++) {
			var jsAttribute=jsElement.attributes[iAttribute];
			var attributeSpec=elementSpec.attributes[jsAttribute.name];
			if(attributeSpec.validate) {
				if(!attributeSpec.validate(jsAttribute)){
					valid=false;
				} 
			}
		}
		for(var iChild=0; iChild<jsElement.children.length; iChild++) {
			if(jsElement.children[iChild].type=="element") {
				var jsChild=jsElement.children[iChild];
				if(!this.validate(jsChild)) {
					valid=false; 
				}
			}
		}
    Xonomy.refresh();
		return valid;
	},
	elements: { 
		"timer": {
			menu: [
				{caption: "Add child <event>", action: Xonomy.newElementChild, actionParameter: "<event action=\'\'/>", hideIf: function(jsElement){return false}},
				{caption: "Add @description", action: Xonomy.newAttribute, actionParameter: {name: "description", value: ""}, hideIf: function(jsElement){return jsElement.hasAttribute("description")}},
			],
			attributes: {
				"cron": {
					asker: Xonomy.askCron,
                    validate: function(jsAttribute){ return isCron(jsAttribute)},
				},
				"id": {
				    asker:Xonomy.askTimerId,
				    validate: function(jsAttribute){ return isValidTimerId(jsAttribute)},
				},
				"description": {
				    asker:Xonomy.askString,
				    menu: [{caption: "Delete", action: Xonomy.deleteAttribute, actionParameter: null, hideIf: function(jsElement){return false}}],
                    validate: function(jsAttribute){ return isStringEmpty(jsAttribute)},
				}
			},
            hasText: false,
            validate: function(jsElement){return hasChildren(jsElement,["event"],1,-1)},
            helpLink: "timer",
		},
        "event": {
        	canDropTo: ["timer"],
              menu: [
                {caption: "Delete", action: Xonomy.deleteElement, actionParameter: null, hideIf: function(jsElement){return false}},
                {caption: "Add <parameter:string>", action: Xonomy.newElementChild, actionParameter: "<parameter:string xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'\' value=\'\'/>", hideIf: function(jsElement){return false;}},
                {caption: "Add <parameter:number>", action: Xonomy.newElementChild, actionParameter: "<parameter:number xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'\' value=\'\'/>", hideIf: function(jsElement){return false;}},
                {caption: "Add <parameter:boolean>", action: Xonomy.newElementChild, actionParameter: "<parameter:boolean xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'\' value=\'\'/>", hideIf: function(jsElement){return false;}},
                {caption: "Add <parameter:object>", action: Xonomy.newElementChild, actionParameter: "<parameter:object xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'\' value=\'\'/>", hideIf: function(jsElement){return false;}},
                {caption: "Add <parameter:list>", action: Xonomy.newElementChild, actionParameter: "<parameter:list xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'\' value=\'\'/>", hideIf: function(jsElement){return false;}},
               ],
              attributes: {
                "action": {
                    asker: Xonomy.askPep,
                    validate: function(jsAttribute){return isValidPepEvent(jsAttribute)},
                },
              },
              validate: function(jsElement){return isNumberOfChildren(jsElement, 0, -1)},
        },
    "parameter:string": {
               canDropTo: ["event"],
               menu: [
                 {caption: "Delete", action: Xonomy.deleteElement, actionParameter: null, hideIf: function(jsElement){return false}},
                 {caption: "Add @value", action: Xonomy.newAttribute, actionParameter: {name: "value", value: ""}, hideIf: function(jsElement){return jsElement.hasAttribute("value")}},
               ],
               attributes: {
                     "name": {
                        asker: Xonomy.askEventParams,
                        validate: function(jsAttribute){ return isStringEmpty(jsAttribute)},
                     },
                     "value": {
                        asker: Xonomy.askString,
                        menu: [{caption: "Delete", action: Xonomy.deleteAttribute, actionParameter: null, hideIf: function(jsElement){return false}}],
                        validate: function(jsAttribute){ return isStringEmpty(jsAttribute)},
                     },
               },
               validate: function(jsElement){return areChildAndValueUsed(jsElement) & isExpressionUsed(jsElement) & hasChildrenIfParentIs(jsElement, "event") & isNumberOfChildren(jsElement,0,1);},
               helpLink: "parameter:string",
        },
        "parameter:number": {
               canDropTo: ["event"],
               menu: [
                 {caption: "Delete", action: Xonomy.deleteElement, actionParameter: null, hideIf: function(jsElement){return false}},
                 {caption: "Add @value", action: Xonomy.newAttribute, actionParameter: {name: "value", value: ""}, hideIf: function(jsElement){return jsElement.hasAttribute("value")}},
               ],
               attributes: {
                     "name": {
                        asker: Xonomy.askEventParams,
                        validate: function(jsAttribute){ return isStringEmpty(jsAttribute)},
                     },
                     "value": {
                       asker: Xonomy.askString,
                       menu: [{caption: "Delete", action: Xonomy.deleteAttribute, actionParameter: null, hideIf: function(jsElement){return false}}],
                       validate: function(jsAttribute){ return isNumber(jsAttribute)},
                     },
               },
               validate: function(jsElement){return areChildAndValueUsed(jsElement) & isExpressionUsed(jsElement) & hasChildrenIfParentIs(jsElement, "event") & isNumberOfChildren(jsElement,0,1);},
               helpLink: "parameter:string",
        },
        "parameter:boolean": {
               canDropTo: ["event"],
               menu: [
                 {caption: "Delete", action: Xonomy.deleteElement, actionParameter: null, hideIf: function(jsElement){return false}},
                 {caption: "Add @value", action: Xonomy.newAttribute, actionParameter: {name: "value", value: ""}, hideIf: function(jsElement){return jsElement.hasAttribute("value")}},
               ],
               attributes: {
                     "name": {
                        asker: Xonomy.askEventParams,
                        validate: function(jsAttribute){ return isStringEmpty(jsAttribute)},
                     },
                     "value": {
                        asker: Xonomy.askPicklist,
                             askerParameter: ["true", "false"],
                        menu: [{caption: "Delete", action: Xonomy.deleteAttribute, actionParameter: null, hideIf: function(jsElement){return false}}],
                     },
               },
               validate: function(jsElement){return areChildAndValueUsed(jsElement) & isExpressionUsed(jsElement) & hasChildrenIfParentIs(jsElement, "event") & isNumberOfChildren(jsElement,0,1);},
               helpLink: "parameter:boolean",
        },
        "parameter:object": {
               canDropTo: ["event"],
               menu: [
                 {caption: "Delete", action: Xonomy.deleteElement, actionParameter: null, hideIf: function(jsElement){return false}},
                 {caption: "Add @value", action: Xonomy.newAttribute, actionParameter: {name: "value", value: ""}, hideIf: function(jsElement){return jsElement.hasAttribute("value")}},
               ],
               attributes: {
                     "name": {
                         asker: Xonomy.askEventParams,
                         validate: function(jsAttribute){ return isStringEmpty(jsAttribute)},
                     },
                     "value": {
                       asker: Xonomy.askString,
                       menu: [{caption: "Delete", action: Xonomy.deleteAttribute, actionParameter: null, hideIf: function(jsElement){return false}}],
                       validate: function(jsAttribute){ return isStringEmpty(jsAttribute)},
                     },
               },
               validate: function(jsElement){return areChildAndValueUsed(jsElement) & isExpressionUsed(jsElement) & hasChildrenIfParentIs(jsElement, "event") & isNumberOfChildren(jsElement,0,1);},
               helpLink: "parameter:object",
        },
        "parameter:list": {
               canDropTo: ["event"],
               menu: [
                 {caption: "Delete", action: Xonomy.deleteElement, actionParameter: null, hideIf: function(jsElement){return false}},
                 {caption: "Add @value", action: Xonomy.newAttribute, actionParameter: {name: "value", value: ""}, hideIf: function(jsElement){return jsElement.hasAttribute("value")}},
               ],
               attributes: {
                     "name": {
                         asker: Xonomy.askEventParams,
                         validate: function(jsAttribute){ return isStringEmpty(jsAttribute)},
                     },
                     "value": {
                       asker: Xonomy.askString,
                       menu: [{caption: "Delete", action: Xonomy.deleteAttribute, actionParameter: null, hideIf: function(jsElement){return false}}],
                       validate: function(jsAttribute){ return isList(jsAttribute)},
                     },
               },
               validate: function(jsElement){return areChildAndValueUsed(jsElement) & isExpressionUsed(jsElement) & hasChildrenIfParentIs(jsElement, "event") & isNumberOfChildren(jsElement,0,1);},
               helpLink: "parameter:list",
        },
  },
};

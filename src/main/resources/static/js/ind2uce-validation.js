function isNotDeprecated(jsElement){
	Xonomy.errors.push({
	      htmlID : jsElement.htmlID,
	      text : "The valueChanged operator is deprecated. Please use valueChanged:boolean, valueChanged:string, valueChanged:numer, valueChanged:object or valueChanged:list. "
	    });
	return false;
}
//check if the event parameter from the variableDeclaration is happening somewhere in the policy
function validateEventParameter(jsAttribute){
	  var element = $("#" + jsAttribute.htmlID);
	  var search = true;
	  var i = 0;
	  while (search && element != null && typeof element != 'undefined' && i < 200) {
	    i++;
	    if (element.attr("data-name") != undefined 
	    		&& (element.attr("data-name").endsWith("mechanism") || element.attr("data-name").endsWith("event"))) {
	      search=false;
	    	break;
	    }
	    element = element.parent();
	  }
	  if(search){
	var xmlDoc = jQuery.parseXML(Xonomy.harvest());
	var x = xmlDoc.getElementsByTagName('mechanism');
//	console.log("nb mechasnism "+x.length);
	var event, livingParams, a, i ;
	for( a=0;a<x.length;a++){
		event = x[a].getAttribute("event");
//		console.log("event: "+event);
		 if (event != "") {
			  livingParams = getParamsOfSelectedEvent(pepList, event);
			    for ( i = 0; i < livingParams.length; i++) {
			    if(livingParams[i].name == jsAttribute.value)
			    	return true;
			    }
			  }
	}
	 
	Xonomy.errors.push({
	      htmlID : jsAttribute.htmlID,
	      text : "The event parameter "+jsAttribute.value+" used in the variable declaration does not appear in any of the used event mechanims in this policy."
	    });
	    return false;
	  }
	  else
		  return true;
}

function getValueChangedUUID(){
    var randomUUID = guid();
    var x= document.getElementsByTagName('valueChanged');
    for(var i=0;i<x.length;i++){
        var id = x[i].getAttribute("id");
        if(id == randomUUID)
            return getValueChangedUUID();
    }
    // console.log("choose identifier "+" UUID "+ randomUUID);
    return randomUUID;
 }

function guid() {
  function s4() {
    return Math.floor((1 + Math.random()) * 0x10000)
      .toString(16)
      .substring(1);
  }
  return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
}

// Finds the currently selected action id from the event (method only used for validation).
function getCurrentActionId(jsElement) {
  var mechanism = jsElement;
  var actionName = "";
  while (!mechanism.name.endsWith("mechanism")) {
    mechanism = mechanism.parent();
  }
  var actionName = mechanism.getAttributeValue("action");
  return actionName;
}

//checks if a variable with this name exists
function variableReferenceExists(jsAttribute,type){
	var xmlDoc = jQuery.parseXML(Xonomy.harvest());
	var x = xmlDoc.getElementsByTagName('variableDeclaration:'+type);
	var variableName, i;
	for( i=0;i<x.length;i++){
		variableName = x[i].getAttribute("name");
		if(variableName==jsAttribute.value)
			return true;
	}
	Xonomy.errors.push({
	      htmlID : jsAttribute.htmlID,
	      text : "The variable reference " + jsAttribute.value + " with the type "+type+" does not exist in the policy."
	    });
	    return false;
	
}

function hasUniqueIdentifier(jsElement,attributeName){
	var id=jsElement.getAttributeValue(attributeName);
	if(id==null || id=== undefined){
		 Xonomy.errors.push({
		      htmlID : jsElement.htmlID,
		      text : "The @"+attributeName+" attribute is mandatory and has to be unique."
		    });
		    return false;
	}
	return true;
}
//Shows an error if the entered String is empty or only contains whitespaces.
function isUnique(jsAttribute) {
  if ($.trim(jsAttribute.value) == "") {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute should not be empty."
    });
    return false;
  }
  var id= jsAttribute.value;
  if (!String(id).match("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}")) {
  	  Xonomy.errors.push({
          htmlID : jsAttribute.htmlID,
          text : "The @" + jsAttribute.name + " attribute must be a valid UUID."
        });
        return false;
  }
  var xmlDoc = jQuery.parseXML(Xonomy.harvest());
	var x = xmlDoc.getElementsByTagName('*');
	var vcIdentifier;
	var nbOcc=0;
	var i;
//	console.log("length: "+ x.length);
	for(i=0;i<x.length;i++){
	    // boolean, string, number, list, object
        if(x[i].tagName.includes('valueChanged'))
        {
//            console.log("name: "+ x[i].tagName);
            vcIdentifier = x[i].getAttribute("id");
            if(String(vcIdentifier)==String(jsAttribute.value))
            nbOcc++;
        }
	}
	if(nbOcc>1){
    Xonomy.errors.push({
          htmlID : jsAttribute.htmlID,
          text : "The ID " + jsAttribute.value + " does already exist but has to be unique."
        });
        return false;
    }
  return true;
};

// Shows an error if the entered String is empty or only contains whitespaces.
function isStringEmpty(jsAttribute) {
  if ($.trim(jsAttribute.value) == "") {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute should not be empty."
    });
    return false;
  }
  return true;
};

// Shows an error if the entered value is not a positive integer.
function isPositiveInteger(jsAttribute) {
  if (/^(0|[1-9]\d*)$/.test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must contain a positive integer."
    });
    return false;
  }
  return true;
};

// Shows an error if the entered value is not an integer.
function isInteger(jsAttribute) {
  if (/^(-){0,1}(0|[1-9]\d*)$/.test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must contain an integer."
    });
    return false;
  }
  return true;
};

// Shows an error if the entered value is not a number.
function isNumber(jsAttribute) {
  if (/^(-)?([0-9]*[.])?[0-9]+([d|f])?$/.test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must contain a number such as 3, -5.0, .2d"
    });
    return false;
  }
  return true;
};

// Shows an error if the entered value is not a number.
function isPointInTime(jsAttribute) {
  if (/^([+-]?0?[1-9]|[12][0-9]|3[01]|\*)\.([+-]?0?[1-9]|1[012]|\*)\.((20)\d\d|\*|[+-]\d{1,3}) ([+-]?[0-9]|0[0-9]|1[0-9]|2[0-3]|\*):([0-5][0-9]|\*|[+-][0-5]?[0-9]):([0-5][0-9]|\*|[+-][0-5]?[0-9])$/
      .test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must contain a absolute or relative point in time such as 01.02.2014 23:00:30 or -1.*.* *:*:*. Absolute time only valid from 21st century."
    });
    return false;
  }
  return true;
};

// Shows an error if the entered value is not a valid Date.
function isValidDate(jsAttribute) {
  if (/^(0[1-9]|1\d|2\d|3[01])\.(0[1-9]|1[0-2])\.(19|20)\d{2}$/
      .test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must be a valid Date such as 01.02.2014"
    });
    return false;
  }
  return true;
};

// Shows an error if the entered value is not a valid time.
function isValidTime(jsAttribute) {
  if (/^([0]?[0-5][0-9]|[0-9]):([0-5][0-9])$/
      .test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must be a valid time such as 23:00"
    });
    return false;
  }
  return true;
};

// Shows an error if the entered value is not a number.
function isTimeInterval(jsAttribute) {
  if (/^(([0-9]+y)?([0-9]+w)?([0-9]+d)?([0-9]+h)?([0-9]+m)?([0-9]+s)?)$/.test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must contain a valid time interval such as 1h, 4y or 56m"
    });
    return false;
  }
  return true;
};

// Shows an error if the entered value is not a valid NC name. NCNames must not start with a digit and only contains digits and letters.
function isNCName(jsAttribute) {
  if (/^[a-zA-Z]([a-zA-Z0-9])*$/.test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must be a valid NCName (must not start with a digit, only contains digits and letters)."
    });
    return false;
  }
  return true;
};

// Shows an error if the entered value is not a list.
function isList(jsAttribute) {
  if (/^((\[([ ]*((\d+)|('[\da-z]*'))[ ]*,[ ]*)*([ ]*((\d*)|('[\da-z]*'))[ ]*){1}\])|(\[([ ]*((\d+)|("[\da-z]*"))[ ]*,[ ]*)*([ ]*((\d*)|("[\da-z]*"))[ ]*){1}\]))$/
      .test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must be a valid List (e.g. [\"a\", \"b\", 3] )."
    });
    return false;
  }
  return true;
};

// Shows an error if the entered String does not represent a valid JSON Object in JSON notation.
function isJSONObject(jsAttribute) {
  try {
    JSON.parse(jsAttribute.value);
  } catch (e) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must be a valid JSON Object (e.g. {\"a\" : \"b\"} )."
    });
    return false;
  }
  return true;
};

// The JSONPath Expression Validation is currently not supported
function isValidExpression(jsAttribute) {
	 if (/^(\$[.]{1,2}[a-zA-Z]+)+([.a-zA-Z0-9\[\]\?\(\)@\s="']+)*$/.test(jsAttribute.value) == false) {
		    Xonomy.warnings.push({
		      htmlID : jsAttribute.htmlID,
		      text : "The JSONPath Expression you entered seems to be wrong, please check if it's valid. Note: this is only an info and does not prevent you from deploying the policy."
        });
        return false;
	 }
  return true;
};

// Shows an error if the entered String does not match the IND2UCE policy id urn schema.
function isValidPolicyId(jsAttribute) {
  if (/^urn:policy(:[a-zA-Z0-9-_\.]+){2}$/.test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must contain a valid policy id, such as: urn:policy:myScope:myPolicyId. It can contain letters (lower or upper case), number and following special characters only -_."
    });
    Xonomy.illegalID=true;
    return false;
  }
  Xonomy.illegalID=false;
  return true;
};


// Shows an error if the entered String does not match the IND2UCE policy id urn schema.
function isValidTimerId(jsAttribute) {
  if (/^urn:timer(:[a-zA-Z0-9-_\.]+){2}$/.test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must contain a valid timer id, such as: urn:timer:myScope:myTimerId. It can contain letters (lower or upper case), number and following special characters only -_."
    });
    Xonomy.illegalID=true;
    return false;
  }
  Xonomy.illegalID=false;
  return true;
};

// Shows an error if the entered String does not match the IND2UCE action id urn schema.
function isValidPepEvent(jsAttribute) {
  if (/^urn:action(:[a-zA-Z0-9-_\.]+){2}$/.test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must contain a valid action id, such as: urn:action:myScope:myAction"
    });
    return false;
  }
  return true;
};

// Shows an error if the entered String does not match the cron syntax.
function isCron(jsAttribute) {
    var result = true;
	$.ajax({
		type : "POST",
		url : "/timer/isValidCronExpression",
		async : false,
		contentType : "application/json",
		data: JSON.stringify( jsAttribute.value ),
		complete : function(data) {
			if (data.status != 200 || data.responseText == "false") {
				 Xonomy.errors.push({
				      htmlID : jsAttribute.htmlID,
				      text : "The @" + jsAttribute.name + " attribute must contain a valid cron syntax, such as: 30 * * * * *. There also must be an upcoming date which matches this expression."
				    });
				    result = false;
			}
		}
	});

  return result;
};

// Shows an error if the entered String does not match the IND2UCE PIP method urn schema.
function isValidPipUrn(jsAttribute) {
  if (/^urn:info(:[a-zA-Z0-9-_\.]+){2}$/.test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must contain a valid pip method urn, such as: urn:info:myScope:myAction"
    });
    return false;
  }
  return true;
};

// Shows an error if the entered String does not match the IND2UCE action id urn schema.
function isValidPxpAction(jsAttribute) {
  if (/^urn:action(:[a-zA-Z0-9-_\.]+){2}$/.test(jsAttribute.value) == false) {
    Xonomy.errors.push({
      htmlID : jsAttribute.htmlID,
      text : "The @" + jsAttribute.name + " attribute must contain a valid id, such as: urn:action:myScope:myAction"
    });
    return false;
  }
  return true;
};

function firstChildIsList(jsElement){
	 Xonomy.warnings.push({
	      htmlID : jsElement.htmlID,
	      text : "Be sure that both the list and the element to be looked for are from the same type."
   });
	if(jsElement.children[0].name.endsWith(":list")) return true;
	else
	 Xonomy.errors.push({
         htmlID : jsElement.htmlID,
         text : "The  @" + jsElement.name + " method checks if a list contains an element. The first child has to be a list. Maybe Regex is the method you are looking for."
       });
	return false;
}

// Shows an error if the number of children is not within the set boundaries.
function isNumberOfChildren(jsElement, min, max) {
  if (max == -1) {
    if (jsElement.children.length < min) {
      if (min == 1) {
        Xonomy.errors.push({
          htmlID : jsElement.htmlID,
          text : "The <" + jsElement.name + "> element must have at least one child."
        });
      } else {
        Xonomy.errors.push({
          htmlID : jsElement.htmlID,
          text : "The <" + jsElement.name + "> element must have at least " + min + " children."
        });
      }
      return false;
    }
    return true;
  } else {
    if (jsElement.children.length < min || jsElement.children.length > max) {
      if (min == 1 && max == 1) {
        Xonomy.errors.push({
          htmlID : jsElement.htmlID,
          text : "The <" + jsElement.name + "> element must have exactly one child."
        });
      } else {
        Xonomy.errors.push({
          htmlID : jsElement.htmlID,
          text : "The <" + jsElement.name + "> element must have at least " + min + " and at most " + max + " children."
        });
      }
      return false;
    }
    return true;
  }
};

// Shows an error if the number of occurances of the specified children in the 'child' array is not within the set boundaries.
function hasChildren(jsElement, children, min, max) {
  if (Array.isArray(children)) {
    var childCount = 0;
    for (var iChild = 0; iChild < jsElement.children.length; iChild++) {
      for (var i = 0; i < children.length; i++) {
        if (jsElement.children[iChild].name == children[i]) {
          childCount++;
        }
      }
    }
    var allowedChildren = "";
    for (var i = 0; i < children.length; i++) {
      allowedChildren += "<" + children[i] + ">";
      if (i == children.length - 2)
        allowedChildren += " or ";
      if (i < children.length - 2)
        allowedChildren += ", ";
    }
    if (max == -1) {
      if (childCount < min) {
        Xonomy.errors.push({
          htmlID : jsElement.htmlID,
          text : "The <" + jsElement.name + "> element must have at least " + min + " " + allowedChildren + " children elements."
        });
        return false;
      }
    } else {
      if (childCount < min || childCount > max) {
        if (min == 1 && max == 1) {
          Xonomy.errors.push({
            htmlID : jsElement.htmlID,
            text : "The <" + jsElement.name + "> element must have exactly one " + allowedChildren + " children element."
          });
        } else {
          Xonomy.errors.push({
            htmlID : jsElement.htmlID,
            text : "The <" + jsElement.name + "> element must have at least " + min + " and at most " + max + " " + allowedChildren
                + " children elements."
          });
        }
        return false;
      }
    }
    return true;
  } else {
    var childCount = 0;
    for (var iChild = 0; iChild < jsElement.children.length; iChild++) {
      if (jsElement.children[iChild].name == children) {
        childCount++;
      }
    }
    if (max == -1) {
      if (childCount < min) {
        Xonomy.errors.push({
          htmlID : jsElement.htmlID,
          text : "The <" + jsElement.name + "> element must have at least " + min + " <" + children + "> child elements."
        });
        return false;
      }
    } else {
      if (childCount < min || childCount > max) {
        if (min == 1 && max == 1) {
          Xonomy.errors.push({
            htmlID : jsElement.htmlID,
            text : "The <" + jsElement.name + "> element must have exactly one <" + children + "> child element."
          });
        } else {
          Xonomy.errors.push({
            htmlID : jsElement.htmlID,
            text : "The <" + jsElement.name + "> element must have at least " + min + " and at most " + max + " <" + children + "> child elements."
          });
        }
        return false;
      }
    }
    return true;
  }
};

// Shows an error if the count operator has invalid children.
function isCountValid(jsElement) {
  return isCountContinuousValid("count",jsElement);
};

//Shows an error if the count operator has invalid children.
function isCountinuousValid(jsElement) {
  return isCountContinuousValid("continuousOccurrence",jsElement);
};

function isCountContinuousValid(functionName,jsElement){
	  var eventOccurence = 0;
	  var when = 0;
	  var valid = true;
	  for (var iChild = 0; iChild < jsElement.children.length; iChild++) {
	    var child = jsElement.children[iChild].name;
	    if (child == "eventOccurrence") {
	      eventOccurence++;
	    } else if (child == "when") {
	      when++;
	    }
	  }
	  
	  if (eventOccurence != 1) {
	    valid = false;
	    Xonomy.errors.push({
	      htmlID : jsElement.htmlID,
	      text : "The <"+functionName+"> element must have exactly one eventOccurrence child element."
	    });
	  }
	  if (when != 1) {
	    valid = false;
	    Xonomy.errors
	        .push({
	          htmlID : jsElement.htmlID,
	          text : "The <"+functionName+"> element must have a <when> child element."
	       });
	  }
	  
	  return valid;
}

// Shows an error if the then and else operators have invalid children.
function isDecisionValid(jsElement) {
  var allow = 0;
  var inhibit = 0;
  var modify = 0;
  var execute = 0;
  var end = 0;
  for (var iChild = 0; iChild < jsElement.children.length; iChild++) {
    var child = jsElement.children[iChild].name;
    if (child == "allow") {
      allow++;
    } else if (child == "inhibit") {
      inhibit++;
    } else if (child == "modify") {
      modify++;
    } else if (child == "execute") {
      execute++;
    }
  }
  var valid = true;
  if (!((allow == 1 && inhibit == 0 && modify == 0) || (allow == 0 && inhibit == 1 && modify == 0) || (allow == 0 && inhibit == 0 && modify > 0) || (allow == 0 && inhibit == 0 && modify == 0 && execute > 0))) {
    valid = false;
    Xonomy.errors.push({
      htmlID : jsElement.htmlID,
      text : "There must be at least one child element. You can add any number of <execute> elements. In addition, you can add either one <allow> element or one <inhibit> element or any number of <modify> elements."
    });
  }
  if (valid)
    return true;
  else
    return false;
};

// Shows an error if the element has children and a specific 'parent' element.
function hasChildrenIfParentIs(jsElement, parent) {
  if (jsElement.parent().name == parent && jsElement.children.length > 0) {
    Xonomy.errors.push({
      htmlID : jsElement.htmlID,
      text : "If <" + jsElement.name + "> has the parent element <" + jsElement.parent().name + ">, then <" + jsElement.name
          + "> must not have child elements."
    });
    return false;
  }
  return true;
};

// Shows an error if the child at the specified 'position' is not equal the specified 'child'.
function childAtPositionXIs(jsElement, child, position) {
  if (position >= jsElement.children.length || jsElement.children[position].name != child) {
    var positionString = parseInt(position) + 1;
    Xonomy.errors.push({
      htmlID : jsElement.htmlID,
      text : "The element <" + jsElement.name + "> expects the child <" + child + "> at position " + positionString + "."
    });
    return false;
  }
  return true;
};

// Shows an error if there is a child parameter element that is not provided by the selected PIP method.
function arePipChildParameterAllowed(jsElement) {
  var allowedParameters;
  var methodIsRegistered = false;
  if (jsElement.getAttributeValue("method") != "") {
    var actionId = jsElement.getAttributeValue("method");

    if (actionId.split(":")[2] != solution.identifier) {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "This information source is only available for policies of solution " + actionId.split(":")[2]
      });
      return false;
    }

    for (var i = 0; i < pipList.length; i++) {
      for (var j = 0; j < pipList[i].componentInterfaces.length; j++) {
        if (pipList[i].componentInterfaces[j].methodName == jsElement.getAttributeValue("method")) {
          allowedParameters = pipList[i].componentInterfaces[j];
          methodIsRegistered = true;
        }
      }
    }
    if (!methodIsRegistered) {
      Xonomy.warnings.push({
        htmlID : jsElement.htmlID,
        text : "The specified PIP method is not registered. Parameters cannot be validated!"
      });
      return true;
    }
    if (allowedParameters === undefined)
      return true;
    if (allowedParameters.parameters.length == 0 && jsElement.children.length > 0) {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "There are no parameters allowed."
      });
      return false;
    }
    var allParamsExist = true;
    var parameterString = "";
    for (var i = 0; i < jsElement.children.length; i++) {
      var child = jsElement.children[i];
      var paramExists = false;
      for (var j = 0; j < allowedParameters.parameters.length; j++) {
        if (i == 0) {
          parameterString += allowedParameters.parameters[j].name + " (" + getSimpleTypeName(allowedParameters.parameters[j].type) + ")";
          if (j == allowedParameters.parameters.length - 2) {
            parameterString += " and ";
          } else if (j < allowedParameters.parameters.length - 2) {
            parameterString += ", ";
          }
        }
        if (child.getAttributeValue("name") == allowedParameters.parameters[j].name
            && child.name == "parameter:" + getSimpleTypeName(allowedParameters.parameters[j].type)) {
          paramExists = true;
        }
      }
      if (!paramExists) {
        allParamsExist = false;
      }
    }
    if (!allParamsExist && parameterString != "") {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "Only the following parameters are allowed: " + parameterString
      });
      return false;
    } else {
      return true;
    }
  } else {
    return true;
  }
};

// Adds missing mandatory parameter child elements to PIP elements depending on the selected PIP method.
function areAllMandatoryPipChildParameterExisting(jsElement) {
  var allowedParameters;
  if (jsElement.getAttributeValue("method") != "") {
    for (var i = 0; i < pipList.length; i++) {
      for (var j = 0; j < pipList[i].componentInterfaces.length; j++) {
        if (pipList[i].componentInterfaces[j].methodName == jsElement.getAttributeValue("method")) {
          allowedParameters = pipList[i].componentInterfaces[j];
        }
      }
    }
    if (allowedParameters === undefined)
      return true;
    var allParamsExist = true;
    var numberOfMandatoryParams = 0;
    var mandatoryParamsStringArray = [];
    for (var j = 0; j < allowedParameters.parameters.length; j++) {
      if (allowedParameters.parameters[j].mandatory == true) {
        numberOfMandatoryParams++;
        mandatoryParamsStringArray.push(allowedParameters.parameters[j].name + " (" + getSimpleTypeName(allowedParameters.parameters[j].type) + ")");
        var paramExists = false;
        for (var i = 0; i < jsElement.children.length; i++) {
          var child = jsElement.children[i];
          if (child.getAttributeValue("name") == allowedParameters.parameters[j].name
              && child.name == "parameter:" + getSimpleTypeName(allowedParameters.parameters[j].type)) {
            paramExists = true;
          }
        }
        if (!paramExists) {
          Xonomy.newElementChild(jsElement.htmlID, "<parameter:" + getSimpleTypeName(allowedParameters.parameters[j].type)
              + " xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'" + allowedParameters.parameters[j].name
              + "\' value=\'\' />");
        }
      }
    }
    var mandatoryParamsString = "";
    for (var i = 0; i < mandatoryParamsStringArray.length; i++) {
      mandatoryParamsString += mandatoryParamsStringArray[i];
      if (i == mandatoryParamsStringArray.length - 2) {
        mandatoryParamsString += " and ";
      } else if (i < mandatoryParamsStringArray.length - 2) {
        mandatoryParamsString += ", ";
      }
    }
    if (!allParamsExist && numberOfMandatoryParams > 0) {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "The following parameters are mandatory: " + mandatoryParamsString
      });
      return false;
    } else {
      return true;
    }
  } else {
    return true;
  }
};

// Shows an error if there is a child parameter element that is not provided by the selected execute action.
function arePxpChildParameterAllowed(jsElement) {
  var allowedParameters;
  var actionIsRegistered = false;
  if (jsElement.getAttributeValue("action") != "") {
    var actionId = jsElement.getAttributeValue("action");
    if (actionId.split(":")[2] != solution.identifier) {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "This action is only available for policies of solution " + actionId.split(":")[2]
      });
      return false;
    }

    for (var i = 0; i < pxpList.length; i++) {
      for (var j = 0; j < pxpList[i].componentInterfaces.length; j++) {
        if (pxpList[i].componentInterfaces[j].methodName == actionId) {
          allowedParameters = pxpList[i].componentInterfaces[j];
          actionIsRegistered = true;
        }
      }
    }
    if (!actionIsRegistered) {
      Xonomy.warnings.push({
        htmlID : jsElement.htmlID,
        text : "The specified execute action is not registered. Parameters cannot be validated!"
      });
      return true;
    }
    if (allowedParameters === undefined)
      return true;
    if (allowedParameters.parameters.length == 0 && jsElement.children.length > 0) {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "There are no parameters allowed."
      });
      return false;
    }
    var allParamsExist = true;
    var parameterString = "";
    for (var i = 0; i < jsElement.children.length; i++) {
      var child = jsElement.children[i];
      var paramExists = false;
      for (var j = 0; j < allowedParameters.parameters.length; j++) {
        if (i == 0) {
          parameterString += allowedParameters.parameters[j].name + " (" + getSimpleTypeName(allowedParameters.parameters[j].type) + ")";
          if (j == allowedParameters.parameters.length - 2) {
            parameterString += " and ";
          } else if (j < allowedParameters.parameters.length - 2) {
            parameterString += ", ";
          }
        }
        if (child.getAttributeValue("name") == allowedParameters.parameters[j].name
            && child.name == "parameter:" + getSimpleTypeName(allowedParameters.parameters[j].type)) {
          paramExists = true;
        }
      }
      if (!paramExists) {
        allParamsExist = false;
      }
    }
    if (!allParamsExist && parameterString != "") {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "Only the following parameters are allowed: " + parameterString
      });
      return false;
    } else {
      return true;
    }
  } else {
    return true;
  }
};

// Adds missing mandatory parameter child elements to execute action elements depending on the selected action.
function areAllMandatoryPxpChildParameterExisting(jsElement) {
  var allowedParameters;
  if (jsElement.getAttributeValue("action") != "") {
    for (var i = 0; i < pxpList.length; i++) {
      for (var j = 0; j < pxpList[i].componentInterfaces.length; j++) {
        if (pxpList[i].componentInterfaces[j].methodName == jsElement.getAttributeValue("action")) {
          allowedParameters = pxpList[i].componentInterfaces[j];
        }
      }
    }
    if (allowedParameters === undefined)
      return true;
    var allParamsExist = true;
    var numberOfMandatoryParams = 0;
    var mandatoryParamsStringArray = [];
    for (var j = 0; j < allowedParameters.parameters.length; j++) {
      if (allowedParameters.parameters[j].mandatory == true) {
        numberOfMandatoryParams++;
        mandatoryParamsStringArray.push(allowedParameters.parameters[j].name + " (" + getSimpleTypeName(allowedParameters.parameters[j].type) + ")");
        var paramExists = false;
        for (var i = 0; i < jsElement.children.length; i++) {
          var child = jsElement.children[i];
          if (child.getAttributeValue("name") == allowedParameters.parameters[j].name
              && child.name == "parameter:" + getSimpleTypeName(allowedParameters.parameters[j].type)) {
            paramExists = true;
          }
        }
        if (!paramExists) {
          Xonomy.newElementChild(jsElement.htmlID, "<parameter:" + getSimpleTypeName(allowedParameters.parameters[j].type)
              + " xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'" + allowedParameters.parameters[j].name
              + "\' value=\'\' />");
        }
      }
    }
    var mandatoryParamsString = "";
    for (var i = 0; i < mandatoryParamsStringArray.length; i++) {
      mandatoryParamsString += mandatoryParamsStringArray[i];
      if (i == mandatoryParamsStringArray.length - 2) {
        mandatoryParamsString += " and ";
      } else if (i < mandatoryParamsStringArray.length - 2) {
        mandatoryParamsString += ", ";
      }
    }
    if (!allParamsExist && numberOfMandatoryParams > 0) {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "The following parameters are mandatory: " + mandatoryParamsString
      });
      return false;
    } else {
      return true;
    }
  } else {
    return true;
  }
};

// Shows an error if there is a child parameter element that is not provided by the selected event.
function arePepChildParameterAllowed(jsElement) {
  var allowedParameters;
  var eventIsRegistered = false;
  if (jsElement.getAttributeValue("action") != "") {
    var actionId = jsElement.getAttributeValue("action");

    if (actionId.split(":")[2] != solution.identifier) {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "This event is only available for policies of solution " + actionId.split(":")[2]
      });
      return false;
    }

    for (var i = 0; i < pepList.length; i++) {
      for (var j = 0; j < pepList[i].interfaceDescriptions.length; j++) {
        if (createActionId(pepList[i].interfaceDescriptions[j].event) == jsElement.getAttributeValue("action")) {
          allowedParameters = pepList[i].interfaceDescriptions[j];
          eventIsRegistered = true;
        }
      }
    }
    if (!eventIsRegistered) {
      Xonomy.warnings.push({
        htmlID : jsElement.htmlID,
        text : "The specified event is not registered. Parameters cannot be validated!"
      });
      return true;
    }
    if (allowedParameters === undefined)
      return true;
    if (allowedParameters && allowedParameters.eventParameterDescription.length == 0 && jsElement.children.length > 0) {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "There are no parameters allowed."
      });
      return false;
    }
    var allParamsExist = true;
    var parameterString = "";
    for (var i = 0; i < jsElement.children.length; i++) {
      var child = jsElement.children[i];
      var paramExists = false;
      if (typeof allowedParameters != 'undefined' && typeof allowedParameters.eventParameterDescription != 'undefined') {
        for (var j = 0; j < allowedParameters.eventParameterDescription.length; j++) {
          if (i == 0) {
            parameterString += allowedParameters.eventParameterDescription[j].name + " ("
                + getSimpleTypeName(allowedParameters.eventParameterDescription[j].type) + ")";
            if (j == allowedParameters.eventParameterDescription.length - 2) {
              parameterString += " and ";
            } else if (j < allowedParameters.eventParameterDescription.length - 2) {
              parameterString += ", ";
            }
          }
          if (child.getAttributeValue("name") == allowedParameters.eventParameterDescription[j].name
              && child.name == "parameter:" + getSimpleTypeName(allowedParameters.eventParameterDescription[j].type)) {
            paramExists = true;
          }
        }
      }
      if (!paramExists) {
        allParamsExist = false;
      }
    }
    if (!allParamsExist && parameterString != "") {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "Only the following parameters are allowed: " + parameterString
      });
      return false;
    } else {
      return true;
    }
  } else {
    return true;
  }
};

// Shows an error if there is a child parameter element that is not provided by the selected modifier.
function areModifierChildParameterAllowed(jsElement) {
  var allowedParameters;
  var modifierIsRegistered = false;
  var actionName = getCurrentActionId(jsElement);
  if (jsElement.getAttributeValue("method") != "") {
    for (var i = 0; i < pepList.length; i++) {
      for (var k = 0; k < pepList[i].modifierDescriptionList.length; k++) {
        if (pepList[i].modifierDescriptionList[k].methodName == jsElement.getAttributeValue("method")) {
          allowedParameters = pepList[i].modifierDescriptionList[k];
          modifierIsRegistered = true;
        }
      }
    }
    if (!modifierIsRegistered) {
      Xonomy.warnings.push({
        htmlID : jsElement.htmlID,
        text : "The specified modifier is not registered. Parameters cannot be validated!"
      });
      return true;
    }
    if (allowedParameters === undefined)
      return true;
    if (jsElement.children.length > 0 && (!allowedParameters || allowedParameters.parameters.length == 0)) {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "There are no parameters allowed."
      });
      return false;
    }
    var allParamsExist = true;
    var parameterString = "";
    for (var i = 0; i < jsElement.children.length; i++) {
      var child = jsElement.children[i];
      var paramExists = false;
      for (var j = 0; j < allowedParameters.parameters.length; j++) {
        if (i == 0) {
          parameterString += allowedParameters.parameters[j].name + " (" + getSimpleTypeName(allowedParameters.parameters[j].type) + ")";
          if (j == allowedParameters.parameters.length - 2) {
            parameterString += " and ";
          } else if (j < allowedParameters.parameters.length - 2) {
            parameterString += ", ";
          }
        }
        if (child.getAttributeValue("name") == allowedParameters.parameters[j].name
            && child.name == "parameter:" + getSimpleTypeName(allowedParameters.parameters[j].type)) {
          paramExists = true;
        }
      }
      if (!paramExists) {
        allParamsExist = false;
      }
    }
    if (!allParamsExist && parameterString != "") {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "Only the following parameters are allowed: " + parameterString
      });
      return false;
    } else {
      return true;
    }
  } else {
    return true;
  }
};

// Adds missing mandatory parameter child elements to function:engine elements depending on the selected modifier.
function areAllMandatoryModifierChildParameterExisting(jsElement) {
  var allowedParameters;
  var actionName = getCurrentActionId(jsElement);
  if (jsElement.getAttributeValue("name") != "") {
    for (var i = 0; i < pepList.length; i++) {
      for (var k = 0; k < pepList[i].modifierDescriptionList.length; k++) {
        if (pepList[i].modifierDescriptionList[k].methodName == jsElement.getAttributeValue("method")) {
          allowedParameters = pepList[i].modifierDescriptionList[k];
        }
      }
    }
    if (allowedParameters === undefined)
      return true;
    var allParamsExist = true;
    var numberOfMandatoryParams = 0;
    var mandatoryParamsStringArray = [];
    for (var j = 0; j < allowedParameters.parameters.length; j++) {
      if (allowedParameters.parameters[j].mandatory == true) {
        numberOfMandatoryParams++;
        mandatoryParamsStringArray.push(allowedParameters.parameters[j].name + " (" + getSimpleTypeName(allowedParameters.parameters[j].type) + ")");
        var paramExists = false;
        for (var i = 0; i < jsElement.children.length; i++) {
          var child = jsElement.children[i];
          if (child.getAttributeValue("name") == allowedParameters.parameters[j].name
              && child.name == "parameter:" + getSimpleTypeName(allowedParameters.parameters[j].type)) {
            paramExists = true;
          }
        }
        if (!paramExists) {
          Xonomy.newElementChild(jsElement.htmlID, "<parameter:" + getSimpleTypeName(allowedParameters.parameters[j].type)
              + " xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'" + allowedParameters.parameters[j].name
              + "\' value=\'\' />");
        }
      }
    }
    var mandatoryParamsString = "";
    for (var i = 0; i < mandatoryParamsStringArray.length; i++) {
      mandatoryParamsString += mandatoryParamsStringArray[i];
      if (i == mandatoryParamsStringArray.length - 2) {
        mandatoryParamsString += " and ";
      } else if (i < mandatoryParamsStringArray.length - 2) {
        mandatoryParamsString += ", ";
      }
    }
    if (!allParamsExist && numberOfMandatoryParams > 0) {
      Xonomy.errors.push({
        htmlID : jsElement.htmlID,
        text : "The following parameters are mandatory: " + mandatoryParamsString
      });
      return false;
    } else {
      return true;
    }
  } else {
    return true;
  }
};

// Shows a warning if an expression is used.
function isExpressionUsed(jsElement) {
  if (typeof jsElement.getAttributeValue("expression") != 'undefined' && jsElement.getAttributeValue("expression") != "") {
    Xonomy.warnings.push({
      htmlID : jsElement.htmlID,
      text : "If a JSON path expression is used, then the type safety cannot be guaranteed!"
    });
  }
  return true;
};

// Shows a warning if a parameter as a value attribute AND a child parameter element.
function areChildAndValueUsed(jsElement) {
  if (typeof jsElement.getAttributeValue("value") != 'undefined' && jsElement.children.length > 0) {
    Xonomy.warnings.push({
      htmlID : jsElement.htmlID,
      text : "The value of the @value attribute overrides the value of the child parameter!"
    });
  }
  else if(typeof jsElement.getAttributeValue("value") == 'undefined' && jsElement.children.length == 0){
    Xonomy.errors.push({
          htmlID : jsElement.htmlID,
          text : "The parameter element must have either one @value attribute or child parameter."
        });
  }
  return true;
};

//Shows a warning if a parameter as a value attribute AND a child parameter element.
function areChildAndFixedtimeUsed(jsElement) {
  var valid = true;
  if (typeof jsElement.getAttributeValue("fixedTime") != 'undefined' && jsElement.children.length > 0) {
    Xonomy.warnings.push({
      htmlID : jsElement.htmlID,
      text : "The value of the @fixedTime attribute overrides the value of the child parameter(s) (start/end)!"
    });
    
  } else if(typeof jsElement.getAttributeValue("fixedTime") == 'undefined' && jsElement.children.length == 0){
    Xonomy.errors.push({
          htmlID : jsElement.htmlID,
          text : "The parameter element must have either one @fixedTime attribute or child parameter (start/end)."
        });
    valid = false;
  }
  
  
  var start = 0;
  var end = 0;
  for (var iChild = 0; iChild < jsElement.children.length; iChild++) {
    var child = jsElement.children[iChild].name;
     if (child == "start") {
      start++;
    } else if (child == "end") {
      end++;
    }
  }
  
  if (start > 1) {
    valid = false;
    Xonomy.errors.push({
      htmlID : jsElement.htmlID,
      text : "The <when> element must not have more then one start tag."
    });
  }
  if (end > 1) {
	    valid = false;
	    Xonomy.errors.push({
	      htmlID : jsElement.htmlID,
	      text : "The <when> element must not have more then one end tag."
	    });
	  }
  
  return valid;
};
// Shows an error if a parameter of e.g executeAction is duplicated (with same name).
function duplicatedParametersNotAllowed(jsElement) {
  for (var i = 0; i < jsElement.children.length; i++) {
    for (var j = i + 1; j < jsElement.children.length; j++) {
      if (jsElement.children[i].name == jsElement.children[j].name) {
        if (jsElement.children[i].getAttributeValue("name") == jsElement.children[j].getAttributeValue("name")) {
          // param duplicated -> error
          Xonomy.errors.push({
            htmlID : jsElement.htmlID,
            text : "Duplicated parameters are not allowed!"
          });
          return false;
        }
      }
    }

  }
  return true;
}

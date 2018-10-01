// Splits the urn in its different parts (split by the :)
function urnSplit(urn, position) {
  var parts = urn.split(":");
  if (parts[position] === undefined) {
    return "";
  } else {
    return parts[position];
  }
};

// Splits the cron in its different parts (split by the blank)
function cronSplit(urn, position) {
  var parts = urn.split(" ");
  if (parts[position] === undefined) {
    return "";
  } else {
    return parts[position];
  }
};

// Splits the date in its different parts (split by the dot, blank and colon)
function pointInTimeSplit(urn, position) {
  var regex = /[ :.]/g;
  var parts = urn.split(regex);
  if (parts[position] === undefined) {
    return "";
  } else {
    return parts[position];
  }
};

// Creates a valid action id out of the event object.
function createActionId(event) {
  return "urn:action:" + event.scope + ":" + event.action;
};

// Finds the currently selected action id from the event from any position in the policy (method only used for the askers).
function getSelectedEventAction(htmlId) {
  var actionName = "";
  var element = $("#" + htmlId);
  var element2 = document.getElementById(htmlId);
  var search = true;
  var i = 0;
  while (search && element != null && typeof element != 'undefined' && i < 200) {
    i++;
    if (element.attr("data-name") != undefined && (element.attr("data-name").endsWith("mechanism") || element.attr("data-name").endsWith("event") || element.attr("data-name").endsWith("policy"))) {
    	break;
    }
    element = element.parent();
  }
  var mechanism,elementId, elementToHarvest ;
  if (element != null && typeof element != 'undefined') {
	  elementId = element.attr("id");
	    elementToHarvest = document.getElementById(elementId);
	    
	  if(element.attr("data-name").endsWith("policy")){
		 var mecha = document.querySelectorAll("[data-name='mechanism']");
		 for(var m=0;m<mecha.length;m++){
			 elementToHarvest = document.getElementById(mecha[m].id);
			 mechanism = Xonomy.harvestElement(elementToHarvest, null);
			 actionName = mechanism.getAttributeValue("event");
			    actionNameEvent = mechanism.getAttributeValue("action");
			    if(actionNameEvent != null){
			        return actionNameEvent;
			    } else if(actionName != null){
			        return actionName;
			    }
		 }
	  }
	  else{
     mechanism = Xonomy.harvestElement(elementToHarvest, null);
    actionName = mechanism.getAttributeValue("event");
    actionNameEvent = mechanism.getAttributeValue("action");
    if(actionNameEvent != null){
        return actionNameEvent;
    } else if(actionName != null){
        return actionName;
    }
	  }
  }
  return "";
};

function getSelectedEventOccurenceAction(htmlId) {
  var actionName = "";
  var element = $("#" + htmlId);
  var element2 = document.getElementById(htmlId);
  var search = true;
  var i = 0;
  while (search && element != null && typeof element != 'undefined' && i < 200) {
    i++;
    if (element.attr("data-name") != undefined && element.attr("data-name").endsWith("eventOccurrence")) {
      break;
    }
    element = element.parent();
  }
  if (element != null && typeof element != 'undefined') {
    var elementId = element.attr("id");
    var elementToHarvest = document.getElementById(elementId);
    var mechanism = Xonomy.harvestElement(elementToHarvest, null);
    actionName = mechanism.getAttributeValue("event");
    return actionName;
  }
  return "";
};

// Returns all timer events.
function getCurrentTimerEvents(htmlId) {
  var timerEvents = [];
  var element = $("#" + htmlId);
  var element2 = document.getElementById(htmlId);
  var i = 0;
  while (element != null && typeof element != 'undefined' && i < 200) {
    i++;
    if (element.attr("data-name") != undefined && element.attr("data-name").endsWith("policy")) {
      break;
    }
    element = element.parent();
  }
  if (element != null && typeof element != 'undefined') {
    var elementId = element.attr("id");
    var elementToHarvest = document.getElementById(elementId);
    var policy = Xonomy.harvestElement(elementToHarvest, null);

    if (policy != null) {
      for (var i = 0; i < policy.children.length; i++) {
        if (policy.children[i].name == "timer") {
          for (var j = 0; j < policy.children[i].children.length; j++) {
            if (policy.children[i].children[j].name == "fireEvent") {
              var actionName = policy.children[i].children[j].getAttributeValue("action");
              if (actionName != "") {
                timerEvents.push(actionName);
              }
            }
          }
        }
      }
    }
  }
  return timerEvents;
};

// Returns all timer events.
function getCurrentVariables(htmlId, type) {
  var variables = [];
  var element = $("#" + htmlId);
  var element2 = document.getElementById(htmlId);
  var i = 0;
  while (element != null && typeof element != 'undefined' && i < 200) {
    i++;
    if (element.attr("data-name") != undefined && element.attr("data-name").endsWith("policy")) {
      break;
    }
    element = element.parent();
  }
  if (element != null && typeof element != 'undefined') {
    var elementId = element.attr("id");
    var elementToHarvest = document.getElementById(elementId);
    var policy = Xonomy.harvestElement(elementToHarvest, null);

    if (policy != null) {
      for (var i = 0; i < policy.children.length; i++) {
        if (policy.children[i].name == "variableDeclaration:" + type) {
          var variableName = policy.children[i].getAttributeValue("name");
          if (variableName != "") {
            variables.push(variableName);
          }
        }
      }
    }
  }
  return variables;
};

// Finds the currently selected modifier for the parameter selection.
function getSelectedModifier(htmlId, fromMenu) {
  var modifierName = "";
  var elementId = $("#" + htmlId).attr("id");
  var elementHtmlId = getParentHtmlIdOfAttribute(elementId);
  var modify = null;
  if (fromMenu) {
    modify = Xonomy.harvestElement(document.getElementById(elementHtmlId), null);
  } else {
    modify = Xonomy.harvestElement(document.getElementById(getParentOfParam(elementHtmlId).htmlID), null);
  }
  modifierName = modify.getAttributeValue("method");
  return modifierName;
};

// Returns the name of the currently selected parameter.
function getSelectedEventParameter(htmlId) {
  var element = $("#" + htmlId);
  var search = true;
  var i = 0;
  while (search && element != null && typeof element != 'undefined' && i < 200) {
    i++;
    if (element.attr("data-name") != undefined
        && (element.attr("data-name").endsWith("modify") || element.attr("data-name").endsWith("parameter:boolean")
            || element.attr("data-name").endsWith("parameter:string") || element.attr("data-name").endsWith("parameter:number")
            || element.attr("data-name").endsWith("parameter:list") || element.attr("data-name").endsWith("parameter:object")
            || element.attr("data-name").endsWith("event:boolean") || element.attr("data-name").endsWith("event:string")
            || element.attr("data-name").endsWith("event:number") || element.attr("data-name").endsWith("event:list") || element.attr(
            "data-name").endsWith("event:object"))) {
      break;
    }
    element = element.parent();
  }
  if (element != null && typeof element != 'undefined') {
    var elementId = element.attr("id");
    var paramEvent = Xonomy.harvestElement(document.getElementById(elementId), null);
    return paramEvent.getAttributeValue("eventParameter");
  }
  return "";
};

// Returns the method of the currently selected PIP.
function getSelectedPipMethod(htmlId) {
  var element = $("#" + htmlId);
  var search = true;
  var i = 0;
  while (search && element != null && typeof element != 'undefined' && i < 200) {
    i++;
    if (element.attr("data-name") != undefined
        && (element.attr("data-name").endsWith("pip:string") || element.attr("data-name").endsWith("pip:number")
            || element.attr("data-name").endsWith("pip:boolean") || element.attr("data-name").endsWith("pip:list") || element.attr("data-name")
            .endsWith("pip:object"))) {
      break;
    }
    element = element.parent();
  }
  if (element != null && typeof element != 'undefined') {
    var elementId = element.attr("id");
    var paramEvent = Xonomy.harvestElement(document.getElementById(elementId), null);
    return paramEvent.getAttributeValue("method");
  }
  return "";
};

// Returns the action name of the currently selected execute action.
function getSelectedPxpAction(htmlId) {
  var element = $("#" + htmlId);
  var search = true;
  var i = 0;
  while (search && element != null && typeof element != 'undefined' && i < 200) {
    i++;
    if (element.attr("data-name") != undefined && (element.attr("data-name").endsWith("execute"))) {
      break;
    }
    element = element.parent();
  }
  if (element != null && typeof element != 'undefined') {
    var elementId = element.attr("id");
    var paramEvent = Xonomy.harvestElement(document.getElementById(elementId), null);
    return paramEvent.getAttributeValue("action");
  }
  return "";
};

// Returns the name of the currently selected parameter.
function getNameOfParameterElement(htmlId) {
  var element = $("#" + htmlId);
  var i = 0;
  while (element != null && typeof element != 'undefined' && i < 200) {
    i++;
    if (element.attr("data-name") != undefined
        && (element.attr("data-name").endsWith("modify") || element.attr("data-name").endsWith("execute")
            || element.attr("data-name").endsWith("event:boolean") || element.attr("data-name").endsWith("event:string")
            || element.attr("data-name").endsWith("event:number") || element.attr("data-name").endsWith("event:list")
            || element.attr("data-name").endsWith("parameter:object") || element.attr("data-name").endsWith("parameter:boolean")
            || element.attr("data-name").endsWith("parameter:string") || element.attr("data-name").endsWith("parameter:number")
            || element.attr("data-name").endsWith("parameter:list") || element.attr("data-name").endsWith("parameter:object"))) {
      break;
    }
    element = element.parent();
  }
  if (element != null && typeof element != 'undefined') {
    var elementId = element.attr("id");
    var paramEvent = Xonomy.harvestElement(document.getElementById(elementId), null);
    return paramEvent.name;
  }
  return "";
};

// Returns the parent element of the currently selected parameter.
function getParentOfParam(htmlId) {
  var element = $("#" + htmlId);
  var search = true;
  var i = 0;
  while (search && element != null && typeof element != 'undefined' && i < 200) {
    i++;
    if (element.attr("data-name") != undefined
        && (element.attr("data-name").endsWith("execute") || element.attr("data-name").endsWith("modify") || element.attr("data-name").endsWith("eventOccurrence")
            || element.attr("data-name").endsWith("pip:string") || element.attr("data-name").endsWith("pip:number")
            || element.attr("data-name").endsWith("pip:list") || element.attr("data-name").endsWith("pip:boolean") || element.attr("data-name")
            .endsWith("pip:object"))) {
      break;
    }
    element = element.parent();
  }
  if (element != null && typeof element != 'undefined') {
    var elementId = element.attr("id");
    var paramEvent = Xonomy.harvestElement(document.getElementById(elementId), null);
    return paramEvent;
    // var parent = paramEvent.parent();
    // return parent;
  }
  return null;
};

// Returns the html id of the element of the currently selected attribute.
function getParentHtmlIdOfAttribute(htmlId) {
  var element = $("#" + htmlId);
  var search = true;
  var i = 0;
  while (search && element != null && typeof element != 'undefined' && i < 200) {
    i++;
    if (element.attr("data-name") != undefined
        && (element.attr("data-name") == "policy" || element.attr("data-name").endsWith("mechanism") || element.attr("data-name") == "event"
            || element.attr("data-name") == "timestep" || element.attr("data-name") == "delay"
            || element.attr("data-name").endsWith("uthorizationDecision") || element.attr("data-name") == "execute"
            || element.attr("data-name") == "modify" || element.attr("data-name") == "event" || element.attr("data-name").startsWith("function:")
            || element.attr("data-name").startsWith("pip:") || element.attr("data-name").startsWith("parameter:") || element.attr("data-name")
            .startsWith("constant:"))) {
      break;
    }
    element = element.parent();
  }
  if (element != null && typeof element != 'undefined') {
    return element.attr("id");
  }
  return null;
};

// Returns all modifiers that the currently selected PEP (based on the selected event action id) provides.
function getModifiersForSelectedEvent(pepList, actionName) {
  var modifiers = null;
  if (actionName != "") {
    for (var i = 0; i < pepList.length; i++) {
      for (var j = 0; j < pepList[i].interfaceDescriptions.length; j++) {
        if (createActionId(pepList[i].interfaceDescriptions[j].event) == actionName) {
          return pepList[i].modifierDescriptionList;
        }
      }
    }
  }
  return modifiers;
};

// Returns all modifiers that the currently selected modifier provides.
function getParamsOfSelectedModifier(pepList, modifierName, actionName) {
  var modifierParams = null;
  if (actionName != "") {
    for (var i = 0; i < pepList.length; i++) {
      for (var k = 0; k < pepList[i].modifierDescriptionList.length; k++) {
        if (pepList[i].modifierDescriptionList[k].methodName == modifierName) {
          modifierParams = pepList[i].modifierDescriptionList[k].parameters;
        }
      }
    }
  }
  return modifierParams;
};

// Returns all parameters that the currently selected event action id provides.
function getParamsOfSelectedEvent(pepList, actionName) {
  var eventParams = null;
  if (actionName != "") {
    for (var i = 0; i < pepList.length; i++) {
      for (var j = 0; j < pepList[i].interfaceDescriptions.length; j++) {
        var event = pepList[i].interfaceDescriptions[j].event;
        var modifierAction = createActionId(event);
        if (modifierAction == actionName) {
          eventParams = pepList[i].interfaceDescriptions[j].eventParameterDescription;
        }
      }
    }
  }
  return eventParams;
};

// Returns all parameters that the currently selected PIP method provides.
function getParamsOfSelectedPipMethod(pipList, htmlId) {
  var methodName = getSelectedPipMethod(htmlId);
  var pipParams = null;
  if (methodName != "") {
    for (var i = 0; i < pipList.length; i++) {
      for (var j = 0; j < pipList[i].componentInterfaces.length; j++) {
        if (pipList[i].componentInterfaces[j].methodName == methodName) {
          pipParams = pipList[i].componentInterfaces[j].parameters;
        }
      }
    }
  }
  return pipParams;
};

// Returns all parameters that the currently selected execute action provides.
function getParamsOfSelectedPxpAction(pxpList, htmlId) {
  var actionName = getSelectedPxpAction(htmlId);
  var pxpParams = null;
  if (actionName != "") {
    for (var i = 0; i < pxpList.length; i++) {
      for (var j = 0; j < pxpList[i].componentInterfaces.length; j++) {
        if (pxpList[i].componentInterfaces[j].methodName == actionName) {
          pxpParams = pxpList[i].componentInterfaces[j].parameters;
        }
      }
    }
  }
  return pxpParams;
};

// Converts the java complex type names into the simple primitive type names.
function getSimpleTypeName(type) {
  type = type.replace("class ", "");
  if (type == "java.lang.String") {
    return "string";
  } else if (type == "java.lang.Long" || type == "java.lang.Integer" || type == "java.lang.Float" || type == "java.lang.Double"
      || type == "java.lang.Number") {
    return "number";
  } else if (type == "java.lang.Boolean") {
    return "boolean";
  } else if (type.startsWith("java.lang.List")) {
    return "list";
  } else {
    return "object";
  }
  return type;
};

// Extracts the field name from a JSONPath expression part (e.g. 'firstName' is extracted from 'firstName[2,4]').
function extractFieldName(expressionPart) {
  if (expressionPart.indexOf("[") > -1) {
    return expressionPart.substring(0, expressionPart.indexOf("["));
  } else {
    return expressionPart;
  }
}

// Extracts the part of a JSONPath expression before the last dot (e.g. '$.person' is extracted from '$.person.firstName').
function getExpressionPrefix(expression) {
  return expression.substring(0, expression.lastIndexOf(".") + 1);
};

// Refreshs the menu for selecting the available child fields of a complex object according to the currently specified JSONPath expression.
function refreshExpressionMenu(expression, selectedActionId, selectedEventParameter) {
  var expressionParts = expression.split(".");
  if (expressionParts[0] == "$") {
    var currentExpressionPrefix = getExpressionPrefix(expression);
    var currentExpressionSuffix = expression.substring(expression.lastIndexOf(".") + 1, expression.length);
    var complexObject = getComplexObjectDescription(currentExpressionPrefix, selectedActionId, selectedEventParameter);
    var keys = [];
    for ( var k in complexObject)
      keys.push(k);
    var html = "";
    for (var i = 0; i < keys.length; i++) {
      if (keys[i].startsWith(extractFieldName(currentExpressionSuffix))) {
        html += "<div class='menuItem techno' onclick='replaceExpressionInput(\"" + currentExpressionPrefix + keys[i] + "\", \"" + selectedActionId
            + "\", \"" + selectedEventParameter + "\");refreshExpressionMenu(\"" + currentExpressionPrefix + keys[i] + "\", \"" + selectedActionId
            + "\", \"" + selectedEventParameter + "\");'>";
        html += currentExpressionPrefix + keys[i];
        html += "</div>";
      }
    }
    document.getElementById("expressionMenu").innerHTML = html;
  }
};

// Replaces the input of the expression asker and triggers the refreshs the field selection menu.
function replaceExpressionInput(expression, selectedActionId, selectedEventParameter) {
  document.getElementById("expressionInput").value = expression;
  document.getElementById("expressionInput").focus();
  refreshExpressionMenu(expression, selectedActionId, selectedEventParameter);
}

// Returns the type description of the currently selected type according to the JSONPath expression.
function getComplexObjectDescription(currentExpressionPrefix, selectedActionId, selectedEventParameter) {
  var expressionParts;
  if (typeof currentExpressionPrefix != 'undefined' && currentExpressionPrefix != "") {
    expressionParts = currentExpressionPrefix.split(".");
  }
  var data = getPTDOfSelectedParameter(selectedActionId, selectedEventParameter);
  var typeToSearch = "";
  var parameterTypeDescription = findTypeInTypeDescriptions(data.type, data.parameterTypeDescription);
  if (typeof expressionParts != 'undefined' && expressionParts.length > 2) {
    for (var j = 1; j < expressionParts.length - 1; j++) {
      if (expressionParts[j] == "") {
        var searchPTD = parameterTypeDescription;
        var foundTypes = [];
        if (j == 1) {
          typeToSearch = data.type;
        } else {
          var keys = [];
          for ( var k in parameterTypeDescription.fields)
            keys.push(k);
          for (var i = 0; i < keys.length; i++) {
            if (keys[i] == extractFieldName(expressionParts[j - 1])) {
              typeToSearch = parameterTypeDescription.fields[keys[i]].typeName;
              break;
            }
          }
        }
        var typeDescriptionForSearch = findTypeInTypeDescriptions(typeToSearch, data.parameterTypeDescription);
        foundTypes = findParameterDescriptionTypes(typeDescriptionForSearch, data.parameterTypeDescription, foundTypes);
        parameterTypeDescription = {
          fields : {}
        };
        for (var k = 0; k < foundTypes.length; k++) {
          var foundTD = findTypeInTypeDescriptions(foundTypes[k], data.parameterTypeDescription).fields;
          for ( var attrname in foundTD) {
            parameterTypeDescription.fields[attrname] = foundTD[attrname];
          }
        }
      } else {
        var keys = [];
        for ( var k in parameterTypeDescription.fields)
          keys.push(k);
        for (var i = 0; i < keys.length; i++) {
          if (keys[i] == extractFieldName(expressionParts[j])) {
            typeToSearch = parameterTypeDescription.fields[keys[i]].typeName;
            break;
          }

        }
        parameterTypeDescription = findTypeInTypeDescriptions(typeToSearch, data.parameterTypeDescription);
      }
    }
  }
  return parameterTypeDescription.fields;
};

// Returns the parameter type description of the currently selected event parameter.
function getPTDOfSelectedParameter(selectedActionId, selectedEventParameter) {
  for (var i = 0; i < pepList.length; i++) {
    for (var j = 0; j < pepList[i].interfaceDescriptions.length; j++) {
      var event = pepList[i].interfaceDescriptions[j].event;
      var actionId = createActionId(event);
      if (actionId == selectedActionId) {
        for (var k = 0; k < pepList[i].interfaceDescriptions[j].eventParameterDescription.length; k++) {
          if (pepList[i].interfaceDescriptions[j].eventParameterDescription[k].name == selectedEventParameter) {
            return pepList[i].interfaceDescriptions[j].eventParameterDescription[k];
          }
        }
      }
    }
  }
}

// Adds all types from the currently selected parameter type description to the 'foundTypes' array.
function findParameterDescriptionTypes(searchPTD, completePTD, foundTypes) {
  var keys = [];
  for ( var k in searchPTD.fields)
    keys.push(k);
  for (var i = 0; i < keys.length; i++) {
    if (searchPTD.fields[keys[i]].jsonType != "PRIMITIVE" && $.inArray(searchPTD.fields[keys[i]].typeName, foundTypes) == -1) {
      foundTypes.push(searchPTD.fields[keys[i]].typeName);
      foundTypes = findParameterDescriptionTypes(findTypeInTypeDescriptions(searchPTD.fields[keys[i]].typeName, completePTD), completePTD, foundTypes);
    }
  }
  if ($.inArray(searchPTD.typeName, foundTypes) == -1) {
    foundTypes.push(searchPTD.typeName);
  }
  return foundTypes;
};

// Returns the parameter type description for a specific 'type'.
function findTypeInTypeDescriptions(type, typeDescriptions) {
  if (type.startsWith("class ")) {
    type = type.split(" ")[1];
  }
  for (var i = 0; i < typeDescriptions.length; i++) {
    if (type == typeDescriptions[i].typeName) {
      return typeDescriptions[i];
    }
  }
  return {};
};

function showHelpModal(link) {
  var dialog = bootbox.dialog({
    message : '<iframe src="' + link + '" style="width:100%; height:75vh;"></iframe>',
    size : 'large',
    className : 'helpModal',
    onEscape: true,
    backdrop: true,
    buttons : {
      openNewWindow : {
        label : 'Open in new window',
        className : 'btn-default',
        callback : function() {
          window.open(link);
        }
      },
      confirm : {
          label : 'Ok',
          className : 'btn-primary'
      }
    },
  });
};
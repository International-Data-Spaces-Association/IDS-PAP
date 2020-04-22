// Asker menu for specifying a policy id.
Xonomy.askPolicyId = function(defaultString) {
  var html = "";
  html += "<div><form onsubmit='Xonomy.answer(\"urn:policy:\" + solution.identifier + \":\" + this.identifier.value); document.getElementById(\"title\").innerHTML = this.identifier.value; document.getElementById(\"breadcrumbtitle\").innerHTML = this.identifier.value;  return false'>";
  html += "urn:policy:" + solution.identifier + ":<input name='identifier' class='formcontrol' value='" + Xonomy.xmlEscape(urnSplit(defaultString, 3))
      + "' style='width:100px'/>";
  html += "<br/><br/><input type='submit' class='btn btn-primary pull-right' value='OK'>";
  html += "</form></div>";
  // }
  return html;
};

// Asker menu for specifying a policy id.
Xonomy.askTimerId = function(defaultString) {
  var html = "";
  html += "<div><form onsubmit='Xonomy.answer(\"urn:timer:\" + solution.identifier + \":\" + this.identifier.value); document.getElementById(\"title\").innerHTML = this.identifier.value; document.getElementById(\"breadcrumbtitle\").innerHTML = this.identifier.value;  return false'>";
  html += "urn:timer:" + solution.identifier + ":<input name='identifier' class='formcontrol' value='" + Xonomy.xmlEscape(urnSplit(defaultString, 3))
      + "' style='width:100px'/>";
  html += "<br/><br/><input type='submit' class='btn btn-primary pull-right' value='OK'>";
  html += "</form></div>";
  // }
  return html;
};

// Asker menu for specifying a cron timing interval.
Xonomy.askCron = function(defaultString) {
  var html = "";
  html += "<div><form onsubmit='Xonomy.answer(this.second.value + \" \" + this.minute.value + \" \" + this.hour.value + \" \" + this.dayOfMonth.value + \" \" + this.month.value + \" \" + this.dayOfWeek.value); return false'>";
  html += "<table  style='border-collapse: separate;border-spacing: 0.5em;'><tbody><tr><td>Sec</td><td>Min</td><td>Hour</td><td>DoM</td><td>Month</td><td>DoW</td></tr>"
  html += "<tr><td><input name='second' style='width:35px' class='formcontrol' value='" + Xonomy.xmlEscape(cronSplit(defaultString, 0))
      + "' style='width:100px'/></td>";
  html += "<td><input name='minute' style='width:35px' class='formcontrol' value='" + Xonomy.xmlEscape(cronSplit(defaultString, 1))
      + "' style='width:100px'/></td>";
  html += "<td><input name='hour' style='width:35px' class='formcontrol' value='" + Xonomy.xmlEscape(cronSplit(defaultString, 2))
      + "' style='width:100px'/></td>";
  html += "<td> <input name='dayOfMonth' style='width:35px' class='formcontrol' value='" + Xonomy.xmlEscape(cronSplit(defaultString, 3))
      + "' style='width:100px'/></td>";
  html += "<td><input name='month' style='width:35px' class='formcontrol' value='" + Xonomy.xmlEscape(cronSplit(defaultString, 4))
      + "' style='width:100px'/></td>";
  html += "<td> <input name='dayOfWeek' style='width:35px' class='formcontrol' value='" + Xonomy.xmlEscape(cronSplit(defaultString, 5))
      + "' style='width:100px'/></td>";
  html += "</tbody></table>";
  html += "<input type='submit' class='btn btn-primary pull-right' value='OK'>";
  html += "</form></div>";
  return html;
};

// Asker menu for specifying a cron timing interval.
Xonomy.askPointInTime = function(defaultString) {
  var html = "";
  html += "<div><form onsubmit='Xonomy.answer(this.day.value + \".\" + this.month.value + \".\" + this.year.value + \" \" + this.hour.value + \":\" + this.minute.value + \":\" + this.second.value); return false'>";
  html += "<table ><tr><td>Day</td><td>Month</td><td>Year</td><td>Hour</td><td>Min</td><td>Sec</td></tr>";
  html += "<tr><td> <input name='day' style='width:35px' class='formcontrol' value='" + Xonomy.xmlEscape(pointInTimeSplit(defaultString, 0))
      + "' style='width:100px'/>.</td>";
  html += "<td> <input name='month' style='width:35px' class='formcontrol' value='" + Xonomy.xmlEscape(pointInTimeSplit(defaultString, 1))
      + "' style='width:100px'/>.</td>";
  html += "<td style='padding-right:15px;' > <input name='year' style='width:60px' class='formcontrol' value='" + Xonomy.xmlEscape(pointInTimeSplit(defaultString, 2))
      + "' style='width:100px'/></td>";
  html += "<td> <input name='hour' style='width:35px' class='formcontrol' value='" + Xonomy.xmlEscape(pointInTimeSplit(defaultString, 3))
      + "' style='width:100px'/>:</td>";
  html += "<td> <input name='minute' style='width:35px' class='formcontrol' value='" + Xonomy.xmlEscape(pointInTimeSplit(defaultString, 4))
      + "' style='width:100px'/>:</td>";
  html += "<td> <input name='second' style='width:35px' class='formcontrol' value='" + Xonomy.xmlEscape(pointInTimeSplit(defaultString, 5))
      + "' style='width:100px'/></td></tr></table><br/>";
  html += "<input type='submit' class='btn btn-primary pull-right' value='OK'>";
  html += "</form></div>";
  // }
  return html;
};

// Asker menu for date
Xonomy.askDate = function(defaultString) {
  var html = "";
  html += "<div><form onsubmit='Xonomy.answer(this.date.value); return false'>";
  html += "Please define the date:<br /><br />";
  html += "&nbsp;&nbsp;Date: <input id='datetimepicker' name='date' placeholder='DD.MM.YYYY' class='formcontrol focusme' value='" + Xonomy.xmlEscape(defaultString)
      + "' style='width:100px' /><br/><br/>";
  html += "<div id='js-datepicker' style='height: 256px'></div>";
  html += "<input type='submit' class='btn btn-primary pull-right' value='OK'>";
  html += "</form></div>";
  // }
  return html;
};

// Asker menu for days
Xonomy.askDays = function(defaultString, picklist) {
  var html = "";
  var currentlist = defaultString.split(",");

  html += "<div><form onsubmit='Xonomy.answer((this.Monday.checked ? this.Monday.name + \",\" : \"\" ) + (this.Tuesday.checked ? this.Tuesday.name + \",\" : \"\" ) + (this.Wednesday.checked ? this.Wednesday.name + \",\" : \"\" ) + (this.Thursday.checked ? this.Thursday.name + \",\" : \"\" ) + (this.Friday.checked ? this.Friday.name + \",\" : \"\" ) + (this.Saturday.checked ? this.Saturday.name + \",\" : \"\" ) + (this.Sunday.checked ? this.Sunday.name : \"\" )); return false'>";

  html += "Please define the days:<br /><br />";

  for (var i = 0; i < picklist.length; i++) {
    if(currentlist.includes(picklist[i]))
    {
      html += "&nbsp;&nbsp;" + picklist[i] + "<input type='checkbox' name='" + picklist[i] + "' class='formcontrol' checked='checked' style='width:100px'/><br/><br/>";
    }else{
      html += "&nbsp;&nbsp;" + picklist[i] + "<input type='checkbox' name='" + picklist[i] + "' class='formcontrol' style='width:100px'/><br/><br/>";
    }

  }

  html += "<input type='submit' class='btn btn-primary pull-right' value='OK'>";
  html += "</form></div>";
  // }
  return html;
};

// Asker menu for time
Xonomy.askTime = function(defaultString) {
  var html = "";
  html += "<div><form onsubmit='Xonomy.answer(this.hour.value + \":\" + this.minute.value); return false'>";
  html += "Please define the time:<br /><br />";
  html += "&nbsp;&nbsp;Hour: <input name='hour' class='formcontrol' value='" + Xonomy.xmlEscape(pointInTimeSplit(defaultString, 0))
      + "' style='width:100px'/><br/><br/>";
  html += "&nbsp;&nbsp;Minute: <input name='minute' class='formcontrol' value='" + Xonomy.xmlEscape(pointInTimeSplit(defaultString, 1))
      + "' style='width:100px'/><br/><br/>";
  html += "<input type='submit' class='btn btn-primary pull-right' value='OK'>";
  html += "</form></div>";
  // }
  return html;
};

// Asker menu for specifying a cron timing interval.
Xonomy.askTimeInterval = function(defaultString) {
  var html = "";
  html += "<div><form onsubmit='Xonomy.answer(insertAmount(\"y\", this.year.value)+insertAmount(\"w\", this.week.value)+insertAmount(\"d\", this.day.value)+insertAmount(\"h\", this.hour.value)+insertAmount(\"m\", this.minute.value)+insertAmount(\"s\", this.second.value)); return false'>";
  // html += "Policy Id Format: urn:policy:&lt;mySolution&gt;:&lt;myIdentifier&gt;<br /><br />";
  // html += "&nbsp;&nbsp;Current solution: " + solution.identifier + "<br /><br />";
  // html += "&nbsp;&nbsp;Identifier: <input name='identifier' class='formcontrol' value='" + Xonomy.xmlEscape(urnSplit(defaultString, 3))
  // + "' style='width:100px'/>";
  html += "Please define the time interval:<br /><br />";
  html += "&nbsp;&nbsp;Years: <input name='year' class='formcontrol' value='" + Xonomy.xmlEscape(getAmount("y", defaultString))
      + "' style='width:100px'/><br/><br/>";
  html += "&nbsp;&nbsp;Weeks: <input name='week' class='formcontrol' value='" + Xonomy.xmlEscape(getAmount("w", defaultString))
      + "' style='width:100px'/><br/><br/>";
  html += "&nbsp;&nbsp;Days: <input name='day' class='formcontrol' value='" + Xonomy.xmlEscape(getAmount("d", defaultString))
      + "' style='width:100px'/><br/><br/>";
  html += "&nbsp;&nbsp;Hours: <input name='hour' class='formcontrol' value='" + Xonomy.xmlEscape(getAmount("h", defaultString))
      + "' style='width:100px'/><br/><br/>";
  html += "&nbsp;&nbsp;Minutes: <input name='minute' class='formcontrol' value='" + Xonomy.xmlEscape(getAmount("m", defaultString))
      + "' style='width:100px'/><br/><br/>";
  html += "&nbsp;&nbsp;Seconds: <input name='second' class='formcontrol' value='" + Xonomy.xmlEscape(getAmount("s", defaultString))
      + "' style='width:100px'/><br/><br/>";
  html += "<input type='submit' class='btn btn-primary pull-right' value='OK'>";
  html += "</form></div>";
  // }
  return html;
};

function insertAmount(currentUnit, value) {
  if (value != "")
    return value + currentUnit;
  else
    return "";
};

function getAmount(currentUnit, value) {
  var myRegexp = /\d*[ywdhms]/g;
  var match;
  do {
    match = myRegexp.exec(value);
    if (match != null) {
      for (var i = 0; i < match.length; i++) {
        if (match[i].substring(match[i].length - 1) == currentUnit)
          return match[i].substring(0, match[i].length - 1);
      }
    }
  } while (match);
  return "";
};

// Asker menu for events.
Xonomy.askEvent = function(defaultString) {
  var html = "";
  html += "<div><form onsubmit='Xonomy.answer(\"urn:action:\" + solution.identifier + \":\" + this.identifier.value); document.getElementById(\"title\").innerHTML = \"Policy \" +\"urn:action:\" + solution.identifier + \":\" + this.identifier.value; return false'>";
  html += "Event Format: urn:action:&lt;mySolution&gt;:&lt;myIdentifier&gt;<br /><br />";
  html += "&nbsp;&nbsp;Current solution: " + solution.identifier + "<br /><br />";
  html += "&nbsp;&nbsp;Identifier: <input name='identifier' class='formcontrol' value='" + Xonomy.xmlEscape(urnSplit(defaultString, 3))
      + "' style='width:100px'/>";
  html += "<br/><br/><input type='submit' class='btn btn-primary pull-right' value='OK'>";
  html += "</form></div>";
  return html;
};

// Asker menu for specifying a PIP method.
Xonomy.askPip = function(defaultString, returnTypes) {
  var html = "";
  var methodCounter = 0;
  for (var i = 0; i < pipList.length; i++) {
    for (var j = 0; j < pipList[i].componentInterfaces.length; j++) {
      for (var k = 0; k < returnTypes.length; k++) {
        if (pipList[i].componentInterfaces[j].returnType == returnTypes[k]) {
          methodCounter++;
        }
      }
    }
  }

  if (methodCounter > 0) {
    html += "<div>";
    html += "<b>Select a registered PIP method:</b><br /><br />"
    html += "<div class='menu'>";
    for (var i = 0; i < pipList.length; i++) {
      for (var j = 0; j < pipList[i].componentInterfaces.length; j++) {
        for (var k = 0; k < returnTypes.length; k++) {
          var rt = pipList[i].componentInterfaces[j].returnType;
          if (rt == returnTypes[k]
              || (returnTypes[k] == "java.lang.Object" && rt != "java.lang.String" && rt != "java.lang.Number" && rt != "java.lang.Integer"
                  && rt != "java.lang.Long" && rt != "java.lang.Float" && rt != "java.lang.Double" && rt != "java.lang.Boolean" && rt != "java.lang.List")) {
            html += "<div class='menuItem techno" + (pipList[i].componentInterfaces[j].methodName == defaultString ? " current" : "")
                + "' onclick='Xonomy.answer(\"" + Xonomy.xmlEscape(pipList[i].componentInterfaces[j].methodName) + "\")'>";
            html += "<span class='punc'>&apos;</span>";
            html += Xonomy.xmlEscape(pipList[i].componentInterfaces[j].methodName);
            html += "<span class='punc'>&apos;</span>";
            html += "</div>";
          }
        }
      }
    }
    html += "</div>";
  } else {
    html += "<b>There is no method available for the selected return type!</b><br />"
  }
  if (Xonomy.mode != 'laic') {
    html += "<div>";
    html += "<div class='ln_solid'></div>";
    html += "<form onsubmit='Xonomy.answer(\"urn:info:\" + solution.identifier + \":\" + this.methodName.value); return false'>";
    html += "<b>Create a method:</b><br /><br />";

    // html += "Scope: <input name='scope' class='formcontrol focusme' value='" + Xonomy.xmlEscape(urnSplit(defaultString, 2))
    // + "' style='width:100px'/>";
    html += "urn:info:" + solution.identifier + ":<input name='methodName' class='formcontrol' value='" + Xonomy.xmlEscape(urnSplit(defaultString, 3)) + "' style='width:100px'/>";
    html += "<br/><br/><input type='submit' class='btn btn-primary pull-right' value='OK'>";
    html += "</form></div>";
    html += "</div>";
  }
  return html;
};

// Asker menu for specifying parameter names for a specific PIP method.
Xonomy.askPipParams = function(defaultString, askerParameter, htmlId) {
  var html = "";
  var paramElementType = getNameOfParameterElement(htmlId).split(":")[1];
  if (pipList.length > 0) {
    var pipParameters = getParamsOfSelectedPipMethod(pipList, htmlId);
    html += "<div>";
    if (pipParameters != null && pipParameters.length > 0) {
      var subhtml = "";
      for (var i = 0; i < pipParameters.length; i++) {
        if (paramElementType == getSimpleTypeName(pipParameters[i].type)) {
          subhtml += "<div class='menuItem techno' onclick='Xonomy.answer(\"" + Xonomy.xmlEscape(pipParameters[i].name) + "\")'>";
          subhtml += "<span class='punc'>&apos;</span>";
          subhtml += Xonomy.xmlEscape(pipParameters[i].name);
          subhtml += "<span class='punc'>&apos;</span>";
          subhtml += " of type: " + getSimpleTypeName(pipParameters[i].type);
          subhtml += "</div>";
        }
      }
      if (subhtml != "") {
        html += "<b>Select a parameter of the selected PIP method:</b><br /><br />"
        html += "<div class='menu'>";
        html += subhtml;
      } else {
        html += "<b>There is no parameter of type: " + paramElementType + "</b><br />"
        html += "<div>";
      }
    } else {
      if (getSelectedPipMethod(htmlId) == "") {
        html += "<b>There is no PIP method selected!</b><br />"
      } else {
        html += "<b>There is no parameter available for the selected PIP method!</b><br />"
      }
    }
    html += "</div>";
  } else {
    html += "<b>There is no PIP registered!</b><br />"
  }
  if (Xonomy.mode != 'laic') {
    html += "<div>";
    html += "<div class='ln_solid'></div>";
    html += "<form onsubmit='Xonomy.answer(this.paramName.value); return false'>";
    html += "<b>Specify a parameter:</b><br /><br />";
    html += "&nbsp;&nbsp;Parameter name: <input name='paramName' class='formcontrol' value='" + Xonomy.xmlEscape(defaultString)
        + "' style='width:100px'/>";
    html += "<br /><br /><input type='submit' class='btn btn-primary pull-right' value='OK'>";
    html += "</form></div>";
    html += "</div>";
  }
  return html;
};

// Asker menu for specifying a execute action name.
Xonomy.askPxp = function(defaultString) {
  var html = "";
  html += "<div>";
  var interfaceReturnType = "";
  if (pxpList.length > 0) {
    html += "<b>Select a registered action:</b><br /><br />"
    html += "<div class='menu'>";
    for (var i = 0; i < pxpList.length; i++) {
      for (var j = 0; j < pxpList[i].componentInterfaces.length; j++) {
        interfaceReturnType = pxpList[i].componentInterfaces[j].returnType;
        if (interfaceReturnType == "java.lang.Boolean" || interfaceReturnType == "boolean") {
          html += "<div class='menuItem techno" + (pxpList[i].componentInterfaces[j].methodName == "java.lang.Boolean" ? " current" : "")
              + "' onclick='Xonomy.answer(\"" + Xonomy.xmlEscape(pxpList[i].componentInterfaces[j].methodName) + "\")'>";
          html += "<span class='punc'>&apos;</span>";
          html += Xonomy.xmlEscape(pxpList[i].componentInterfaces[j].methodName);
          html += "<span class='punc'>&apos;</span>";
          html += "</div>";
        }
      }
    }
    html += "</div>";
  } else {
    html += "<b>There is no execute action available!</b><br />";
  }
  if (Xonomy.mode != 'laic') {
    html += "<div class='ln_solid'></div>";
    html += "<div>";
    html += "<form onsubmit='Xonomy.answer(\"urn:action:\" + solution.identifier + \":\" + this.action.value); return false'>";
    // html += "Scope: <input name='scope' class='formcontrol focusme' value='" + Xonomy.xmlEscape(urnSplit(defaultString, 2))
    // + "' style='width:100px'/>";
    html += "<b>Create an action id:</b><br /><br />";
    html += "urn:action:" + solution.identifier + ":<input name='action' class='formcontrol' value='" + Xonomy.xmlEscape(urnSplit(defaultString, 3))
        + "' style='width:100px'/>";
    html += "<br/><br/><input type='submit' class='btn btn-primary pull-right' value='OK'>";
    html += "</form></div>";
  }
  html += "</div>";
  return html;
};

// Asker menu for specifying parameter names for a specific execute action.
Xonomy.askPxpParams = function(defaultString, askerParameter, htmlId) {
  var html = "";
  html += "<div>";
  var paramElementType = getNameOfParameterElement(htmlId).split(":")[1];
  if (pxpList.length > 0) {
    var pxpParameters = getParamsOfSelectedPxpAction(pxpList, htmlId);
    if (pxpParameters != null && pxpParameters.length > 0) {
      var subhtml = "";
      for (var i = 0; i < pxpParameters.length; i++) {
        if (paramElementType == getSimpleTypeName(pxpParameters[i].type)) {
          subhtml += "<div class='menuItem techno' onclick='Xonomy.answer(\"" + Xonomy.xmlEscape(pxpParameters[i].name) + "\")'>";
          subhtml += "<span class='punc'>&apos;</span>";
          subhtml += Xonomy.xmlEscape(pxpParameters[i].name);
          subhtml += "<span class='punc'>&apos;</span>";
          subhtml += " of type: " + getSimpleTypeName(pxpParameters[i].type);
          subhtml += "</div>";
        }
      }
      if (subhtml != "") {
        html += "<b>Select a parameter of the selected execute action:</b><br /><br />"
        html += "<div class='menu'>";
        html += subhtml;
        html += "</div>";
      } else {
        html += "<b>There is no parameter of type: " + paramElementType + "</b><br />"
        // html += "<div>";
      }
    } else {
      if (getSelectedPxpAction(htmlId) == "") {
        html += "<b>There is no PXP method selected!</b><br />"
      } else {
        html += "<b>There is no parameter available for the selected PXP method!</b><br />"
      }
    }
  } else {
    html += "<b>There is no PXP registered!</b><br />"
  }
  if (Xonomy.mode != 'laic') {
    html += "<div class='ln_solid'></div>";
    html += "<div>";
    html += "<form onsubmit='Xonomy.answer(this.paramName.value); return false'>";
    html += "<b>Specify a parameter:</b><br /><br />";
    html += "&nbsp;&nbsp;Parameter name: <input name='paramName' class='formcontrol' value='" + Xonomy.xmlEscape(defaultString)
        + "' style='width:100px'/>";
    html += "<br /><br /><input type='submit' class='btn btn-primary pull-right' value='OK'>";
    html += "</form></div>";
  }
  html += "</div>";
  return html;
};

// Asker menu for specifying an event action id.
Xonomy.askPep = function(defaultString, askerParameter, htmlId) {
  var html = "";
  var timerEvents = getCurrentTimerEvents(htmlId);
  if (timerEvents.length > 0) {
    html += "<div>";
    html += "<b>Select a timer event:</b><br /><br />"
    html += "<div class='menu'>";
    for (var i = 0; i < timerEvents.length; i++) {
      html += "<div class='menuItem techno' onclick='Xonomy.answer(\"" + Xonomy.xmlEscape(timerEvents[i]) + "\")'>";
      html += "<span class='punc'>&apos;</span>";
      html += Xonomy.xmlEscape(timerEvents[i]);
      html += "<span class='punc'>&apos;</span>";
      html += "</div>";
    }
    html += "</div><br />";
  }
  if (pepList.length > 0) {
    html += "<div>";
    html += "<b>Select a registered event:</b><br /><br />"
    html += "<div class='menu'>";
    for (var i = 0; i < pepList.length; i++) {
      for (var j = 0; j < pepList[i].interfaceDescriptions.length; j++) {
        html += "<div class='menuItem techno" + (pepList[i].interfaceDescriptions[j].methodName == "java.lang.Boolean" ? " current" : "")
            + "' onclick='Xonomy.answer(\"" + Xonomy.xmlEscape(createActionId(pepList[i].interfaceDescriptions[j].event)) + "\")'>";
        html += "<span class='punc'>&apos;</span>";
        html += Xonomy.xmlEscape(createActionId(pepList[i].interfaceDescriptions[j].event));
        html += "<span class='punc'>&apos;</span>";
        html += "</div>";
      }
    }
    html += "</div>";
  }
  if (html == "") {
    html += "<b>There is no registered event!</b><br />";
  }
  if (Xonomy.mode != 'laic') {
    html += "<div class='ln_solid'></div>";
    html += "<div>";
    html += "<form onsubmit='Xonomy.answer(\"urn:action:\" + solution.identifier + \":\" + this.action.value); return false'>";
    html += "<b>Create an action id:</b><br /><br />";
    // html += "Scope: <input name='scope' class='formcontrol focusme' value='" + Xonomy.xmlEscape(urnSplit(defaultString, 2))
    // + "' style='width:100px'/>";
    html += "urn:action:" + solution.identifier + ":<input name='action' class='formcontrol' value='" + Xonomy.xmlEscape(urnSplit(defaultString, 3))
        + "' style='width:100px'/>";
    html += "<br/><br/><input type='submit' class='btn btn-primary pull-right' value='OK'>";
    html += "</form></div>";
  }
  return html;
};

// Asker menu for specifying parameter names for a specific event action.
Xonomy.askEventParams = function(defaultString, askerParameter, htmlId) {
  var html = "";
  var actionName = getSelectedEventAction(htmlId);
  var paramElementType = getNameOfParameterElement(htmlId);
  if (paramElementType != "modify") {
    paramElementType = paramElementType.split(":")[1];
  }
  if (pepList.length > 0) {
    var eventParams = getParamsOfSelectedEvent(pepList, actionName);
    html += "<div>";
    if (eventParams != null && eventParams.length > 0) {
      var subhtml = "";
      for (var i = 0; i < eventParams.length; i++) {
        if (paramElementType == getSimpleTypeName(eventParams[i].type) || getSimpleTypeName(eventParams[i].type) == "object"
            || paramElementType == "modify") {
          subhtml += "<div class='menuItem techno' onclick='Xonomy.answer(\"" + Xonomy.xmlEscape(eventParams[i].name) + "\")'>";
          subhtml += "<span class='punc'>&apos;</span>";
          subhtml += Xonomy.xmlEscape(eventParams[i].name);
          subhtml += "<span class='punc'>&apos;</span>";
          subhtml += " of type: " + getSimpleTypeName(eventParams[i].type);
          subhtml += "</div>";
        }
      }
      if (subhtml != "") {
        html += "<b>Select a parameter of the selected event:</b><br /><br />"
        html += "<div class='menu'>";
        html += subhtml;
      } else {
        html += "<b>There is no parameter of type: " + paramElementType + "</b><br />"
        html += "<div>";
      }
    } else {
      if (actionName == "") {
        html += "<b>There is no event selected!</b><br />"
      } else {
        html += "<b>There is no parameter available for the selected event!</b><br />"
      }
    }
    html += "</div>";
  } else {
    html += "<b>There is no PEP registered!</b><br />";
  }
  if (Xonomy.mode != 'laic') {
    html += "<div class='ln_solid'></div>";
    html += "<div>";
    html += "<form onsubmit='Xonomy.answer(this.paramName.value); return false'>";
    html += "<b>Specify a parameter:</b><br /><br />";
    html += "&nbsp;&nbsp;Parameter name: <input name='paramName' class='formcontrol' value='" + Xonomy.xmlEscape(defaultString)
        + "' style='width:100px'/>";
    html += "<br /><br /><input type='submit' class='btn btn-primary pull-right' value='OK'>";
    html += "</form></div>";
    html += "</div>";
  }
  return html;
};

// Asker menu for specifying parameter names for a specific event action.
Xonomy.askEventOccurenceParams = function(defaultString, askerParameter, htmlId) {
  var html = "";
  var actionName = getSelectedEventOccurenceAction(htmlId);
  var paramElementType = getNameOfParameterElement(htmlId);
  if (paramElementType != "modify") {
    paramElementType = paramElementType.split(":")[1];
  }
  if (pepList.length > 0) {
    var eventParams = getParamsOfSelectedEvent(pepList, actionName);
    html += "<div>";
    if (eventParams != null && eventParams.length > 0) {
      var subhtml = "";
      for (var i = 0; i < eventParams.length; i++) {
        if (paramElementType == getSimpleTypeName(eventParams[i].type) || getSimpleTypeName(eventParams[i].type) == "object"
            || paramElementType == "modify") {
          subhtml += "<div class='menuItem techno' onclick='Xonomy.answer(\"" + Xonomy.xmlEscape(eventParams[i].name) + "\")'>";
          subhtml += "<span class='punc'>&apos;</span>";
          subhtml += Xonomy.xmlEscape(eventParams[i].name);
          subhtml += "<span class='punc'>&apos;</span>";
          subhtml += " of type: " + getSimpleTypeName(eventParams[i].type);
          subhtml += "</div>";
        }
      }
      if (subhtml != "") {
        html += "<b>Select a parameter of the selected event:</b><br /><br />"
        html += "<div class='menu'>";
        html += subhtml;
      } else {
        html += "<b>There is no parameter of type: " + paramElementType + "</b><br />"
        html += "<div>";
      }
    } else {
      if (actionName == "") {
        html += "<b>There is no event selected!</b><br />"
      } else {
        html += "<b>There is no parameter available for the selected event!</b><br />"
      }
    }
    html += "</div>";
  } else {
    html += "<b>There is no PEP registered!</b><br />";
  }
  if (Xonomy.mode != 'laic') {
    html += "<div class='ln_solid'></div>";
    html += "<div>";
    html += "<form onsubmit='Xonomy.answer(this.paramName.value); return false'>";
    html += "<b>Specify a parameter:</b><br /><br />";
    html += "&nbsp;&nbsp;Parameter name: <input name='paramName' class='formcontrol' value='" + Xonomy.xmlEscape(defaultString)
        + "' style='width:100px'/>";
    html += "<br /><br /><input type='submit' class='btn btn-primary pull-right' value='OK'>";
    html += "</form></div>";
    html += "</div>";
  }
  return html;
};

// Asker menu for specifying a modifier.
Xonomy.askModifier = function(defaultString, askerParameter, htmlId) {
  var html = "";
  var actionName = getSelectedEventAction(htmlId);
  if (pepList.length > 0) {
    var modifiers = getModifiersForSelectedEvent(pepList, actionName);
    html += "<div>";
    if (modifiers != null && modifiers.length > 0) {
      html += "<b>Select a registered modifier for selected event:</b><br /><br />"
      html += "<div class='menu'>";
      for (var i = 0; i < modifiers.length; i++) {
        html += "<div class='menuItem techno' onclick='Xonomy.answer(\"" + Xonomy.xmlEscape(modifiers[i].methodName) + "\")'>";
        html += "<span class='punc'>&apos;</span>";
        html += Xonomy.xmlEscape(modifiers[i].methodName);
        html += "<span class='punc'>&apos;</span>";
        html += " for type: " + getSimpleTypeName(modifiers[i].modifierType);
        html += "</div>";
      }
    } else {
      if (actionName == "") {
        html += "<b>There is no event selected!</b><br />"
      } else {
        html += "<b>There is no modifier registered for the selected event action!</b><br />"
      }
    }
    html += "</div>";
  } else {
    html += "<b>There is no PEP registered!</b><br />"
  }
  if (Xonomy.mode != 'laic') {
    html += "<div class='ln_solid'></div>";
    html += "<div>";
    html += "<form onsubmit='Xonomy.answer(this.modifierName.value); return false'>";
    html += "<b>Specify a modifier:</b><br /><br />";
    html += "&nbsp;&nbsp;Modifier: <input name='modifierName' class='formcontrol' value='" + Xonomy.xmlEscape(defaultString)
        + "' style='width:100px'/>";
    html += "<br /><br /><input type='submit' class='btn btn-primary pull-right' value='OK'>";
    html += "</form></div>";
    html += "</div>";
  }

  return html;
};

// Asker menu for specifying parameter names for a specific modifier.
Xonomy.askModifierParams = function(defaultString, askerParameter, htmlId) {
  var html = "";
  var actionName = getSelectedEventAction(htmlId);
  if (pepList.length > 0) {
    // var modifierParams = getParamsOfSelectedModifier(pepList, Xonomy.lastSelectedHtmlId);
    var modifierName = getSelectedModifier(htmlId, false);
    var modifierParams = getParamsOfSelectedModifier(pepList, modifierName, actionName);
    html += "<div>";
    if (modifierParams != null && modifierParams.length > 0) {
      html += "<b>Select a parameter of the selected execute action:</b><br /><br />"
      html += "<div class='menu'>";
      for (var i = 0; i < modifierParams.length; i++) {
        html += "<div class='menuItem techno' onclick='Xonomy.answer(\"" + Xonomy.xmlEscape(modifierParams[i].name) + "\")'>";
        html += "<span class='punc'>&apos;</span>";
        html += Xonomy.xmlEscape(modifierParams[i].name);
        html += "<span class='punc'>&apos;</span>";
        html += " of type: " + getSimpleTypeName(modifierParams[i].type);
        html += "</div>";
      }
    } else {
      if (actionName == "") {
        html += "<b>There is no event selected!</b><br />"
      } else {
        html += "<b>There is no modifier selected!</b><br />"
      }
    }
    html += "</div>";
  } else {
    html += "<b>There is no PEP registered!</b><br />"
  }
  if (Xonomy.mode != 'laic') {
    html += "<div class='ln_solid'></div>";
    html += "<div>";
    html += "<form onsubmit='Xonomy.answer(this.paramName.value); return false'>";
    html += "<b>Specify a parameter:</b><br /><br />";
    html += "&nbsp;&nbsp;Parameter name: <input name='paramName' class='formcontrol' value='" + Xonomy.xmlEscape(defaultString)
        + "' style='width:100px'/>";
    html += "<br /><br /><input type='submit' class='btn btn-primary pull-right' value='OK'>";
    html += "</form></div>";
    html += "</div>";
  }
  return html;
};

// Asker menu proxy for choosing the correct asker depending on the position inside the policy.
Xonomy.askParams = function(defaultString, askerParameter, htmlId) {
  var parent = getParentOfParam(htmlId);
  if (parent.name == "eventOccurrence") {
    return Xonomy.askEventOccurenceParams(defaultString, askerParameter, htmlId);
  } else if (parent.name == "pip:string" || parent.name == "pip:boolean" || parent.name == "pip:number" || parent.name == "pip:list"
      || parent.name == "pip:object") {
    return Xonomy.askPipParams(defaultString, askerParameter, htmlId);
  } else if (parent.name == "modify") {
    return Xonomy.askModifierParams(defaultString, askerParameter, htmlId);
  } else if (parent.name == "execute") {
    return Xonomy.askPxpParams(defaultString, askerParameter, htmlId);
  }
  return "";
};

// Asker menu for specifying an event action id.
Xonomy.askVariables = function(defaultString, askerParameter, htmlId) {
  var html = "";
  var variables = getCurrentVariables(htmlId, askerParameter);
  if (variables.length > 0) {
    html += "<div>";
    html += "<b>Select a variable:</b><br /><br />"
    html += "<div class='menu'>";
    for (var i = 0; i < variables.length; i++) {
      html += "<div class='menuItem techno' onclick='Xonomy.answer(\"" + Xonomy.xmlEscape(variables[i]) + "\")'>";
      html += "<span class='punc'>&apos;</span>";
      html += Xonomy.xmlEscape(variables[i]);
      html += "<span class='punc'>&apos;</span>";
      html += "</div>";
    }
    html += "</div><br />";
  }
  if (html == "") {
    html += "<b>There is no registered variable!</b><br />";
  }
  if (Xonomy.mode != 'laic') {
    html += "<div class='ln_solid'></div>";
    html += "<div>";
    html += "<form onsubmit='Xonomy.answer(this.variable.value); return false'>";
    html += "<b>Set the variable name:</b><br /><br />";
    html += "&nbsp;&nbsp;Variable Name: <input name='variable' class='formcontrol' value='" + Xonomy.xmlEscape(defaultString)
        + "' style='width:100px'/>";
    html += "<br/><br/><input type='submit' class='btn btn-primary pull-right' value='OK'>";
    html += "</form></div>";
  }
  return html;
};

// Asker menu for specifying a JSONPath expression.
Xonomy.askExpression = function(defaultString, askerParameter, htmlId) {
  var html = "";
  var selectedActionId = getSelectedEventAction(htmlId);
  var selectedEventParameter = getSelectedEventParameter(htmlId);
  var registeredPTD = getPTDOfSelectedParameter(selectedActionId, selectedEventParameter);
  var expression = "";
  if (defaultString == "" || !defaultString.startsWith("$.")) {
    expression = "$.";
  } else {
    expression = Xonomy.xmlEscape(defaultString);
  }
  // if the event parameter and action id are selected
  if (selectedActionId != "" && selectedEventParameter != "") {
    // if the selected event parameter is not correctly registered as a complex object
    if (typeof registeredPTD == 'undefined' || registeredPTD.parameterTypeDescription.length == 0) {
      html += "<div>";
      html += "<form onsubmit='Xonomy.answer(this.expression.value); return false'>";
      html += "<b>Specify an expression:</b><br /><br />";
      html += "&nbsp;&nbsp;Expression: <input id='expressionInput' name='expression' class='formcontrol' value='" + expression
          + "' style='width:300px'/>";
      html += "<div class='ln_solid'></div>";
      html += "<b>The selected event parameter is not correctly registered as a complex object!</b><br /><br />";
    } else {
      html += "<div>";
      html += "<form onsubmit='Xonomy.answer(this.expression.value); return false'>";
      html += "<b>Specify an expression:</b><br /><br />";
      html += "&nbsp;&nbsp;Expression: <input id='expressionInput' name='expression' class='formcontrol' value='" + expression
          + "' style='width:300px' onInput='refreshExpressionMenu(this.value, \"" + selectedActionId + "\", \"" + selectedEventParameter + "\")'/>";
      html += "<div class='ln_solid'></div>";
      var complexObject = getComplexObjectDescription(expression, selectedActionId, selectedEventParameter);
      var keys = [];
      for ( var k in complexObject)
        keys.push(k);
      html += "<b>Available parameters:</b><br /><br />";
      html += "<div id='expressionMenu' class='menu'>";
      if (keys.length > 0) {
        for (var i = 0; i < keys.length; i++) {
          html += "<div class='menuItem techno' onclick='replaceExpressionInput(\"" + getExpressionPrefix(expression) + Xonomy.xmlEscape(keys[i])
              + "\", \"" + selectedActionId + "\", \"" + selectedEventParameter + "\")'>";
          html += getExpressionPrefix(expression) + Xonomy.xmlEscape(keys[i]);
          html += "</div>";
        }
      }
      html += "</div>";
    }
  } else {
    html += "<div>";
    html += "<form onsubmit='Xonomy.answer(this.expression.value); return false'>";
    html += "<b>Specify an expression:</b><br /><br />";
    html += "&nbsp;&nbsp;Expression: <input id='expressionInput' name='expression' class='formcontrol' value='" + expression
        + "' style='width:300px'/>";
    html += "<div class='ln_solid'></div>";
    html += "<b>There is either no event action or no event parameter selected!</b><br /><br />";
  }
  html += "<br /><br /><input type='submit' class='btn btn-primary pull-right' value='OK'>";
  html += "</form>";
  html += "</div>";
  return html;
};

// Parameter selection menu for event parameters.
Xonomy.showEventParameterMenu = function() {
  Xonomy.destroyBubble();
  var html = "";
  var eventParams = null;
  var actionName = getSelectedEventAction(Xonomy.lastSelectedHtmlId);
  if (pepList.length > 0) {
    var eventParams = getParamsOfSelectedEvent(pepList, actionName);
    html += "<div>";
    if (eventParams != null && eventParams.length > 0) {
      html += "<b>Select a parameter of the selected event:</b><br /><br />";
      html += "<div class='menu'>";
      for (var i = 0; i < eventParams.length; i++) {
        var parameter = "\"<parameter:" + getSimpleTypeName(eventParams[i].type)
            + " xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'" + Xonomy.xmlEscape(eventParams[i].name)
            + "\' value=\'\'/>\"";
        html += "<div class='menuItem techno' onclick='Xonomy.newElementChild(\"" + Xonomy.lastSelectedHtmlId + "\"," + Xonomy.xmlEscape(parameter)
            + ",true)'>";
        html += "<span class='punc'>&apos;</span>";
        html += Xonomy.xmlEscape(eventParams[i].name);
        html += "<span class='punc'>&apos;</span>";
        html += " of type: " + getSimpleTypeName(eventParams[i].type);
        html += "</div>";
      }
    } else {
      if (actionName == "") {
        html += "<b>There is no event selected!</b><br />"
      } else {
        html += "<b>There is no parameter available for the selected event!</b><br />";
      }
    }
    html += "</div>";
  }
  if (html != "") {
    document.body.appendChild(Xonomy.makeBubble(html));
    Xonomy.showBubble($("#" + Xonomy.lastSelectedHtmlId + " > .tag.opening > .name"));
  }
}

// Parameter selection menu for PIP method parameters.
Xonomy.showPipParameterMenu = function() {
  Xonomy.destroyBubble();
  var html = "";
  var pipParams = null;
  if (pipList.length > 0) {
    var pipParams = getParamsOfSelectedPipMethod(pipList, Xonomy.lastSelectedHtmlId);
    html += "<div>";
    if (pipParams != null && pipParams.length > 0) {
      html += "<b>Select a parameter of the selected PIP method:</b><br /><br />";
      html += "<div class='menu'>";
      for (var i = 0; i < pipParams.length; i++) {
        var parameter = "\"<parameter:" + getSimpleTypeName(pipParams[i].type)
            + " xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'" + Xonomy.xmlEscape(pipParams[i].name)
            + "\' value=\'\'/>\"";
        html += "<div class='menuItem techno' onclick='Xonomy.newElementChild(\"" + Xonomy.lastSelectedHtmlId + "\"," + Xonomy.xmlEscape(parameter)
            + ",true)'>";
        html += "<span class='punc'>&apos;</span>";
        html += Xonomy.xmlEscape(pipParams[i].name);
        html += "<span class='punc'>&apos;</span>";
        html += " of type: " + getSimpleTypeName(pipParams[i].type);
        html += "</div>";
      }
    } else {
      if (getSelectedPipMethod(Xonomy.lastSelectedHtmlId) == "") {
        html += "<b>There is no PIP method selected!</b><br />"
      } else {
        html += "<b>There is no parameter available for the selected PIP method!</b><br />";
      }
    }
    html += "</div>";
  }
  if (html != "") {
    document.body.appendChild(Xonomy.makeBubble(html));
    Xonomy.showBubble($("#" + Xonomy.lastSelectedHtmlId + " > .tag.opening > .name"));
  }
}

// Parameter selection menu for execute action parameters.
Xonomy.showPxpParameterMenu = function() {
  Xonomy.destroyBubble();
  var html = "";
  var pxpParams = null;
  if (pxpList.length > 0) {
    var pxpParams = getParamsOfSelectedPxpAction(pxpList, Xonomy.lastSelectedHtmlId);
    html += "<div>";
    if (pxpParams != null && pxpParams.length > 0) {
      html += "<b>Select a parameter of the selected execute action:</b><br /><br />";
      html += "<div class='menu'>";
      for (var i = 0; i < pxpParams.length; i++) {
        var parameter = "\"<parameter:" + getSimpleTypeName(pxpParams[i].type)
            + " xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'" + Xonomy.xmlEscape(pxpParams[i].name)
            + "\' value=\'\'/>\"";
        html += "<div class='menuItem techno' onclick='Xonomy.newElementChild(\"" + Xonomy.lastSelectedHtmlId + "\"," + Xonomy.xmlEscape(parameter)
            + ",true)'>";
        html += "<span class='punc'>&apos;</span>";
        html += Xonomy.xmlEscape(pxpParams[i].name);
        html += "<span class='punc'>&apos;</span>";
        html += " of type: " + getSimpleTypeName(pxpParams[i].type);
        html += "</div>";
      }
    } else {
      if (getSelectedPxpAction(Xonomy.lastSelectedHtmlId) == "") {
        html += "<b>There is no execute action selected!</b><br />";
      } else {
        html += "<b>There is no parameter available for the selected execute Action!</b><br />";
      }
    }
    html += "</div>";
  }
  if (html != "") {
    document.body.appendChild(Xonomy.makeBubble(html));
    Xonomy.showBubble($("#" + Xonomy.lastSelectedHtmlId + " > .tag.opening > .name"));
  }
}

// Parameter selection menu for modifier parameters.
Xonomy.showModifierParameterMenu = function() {
  Xonomy.destroyBubble();
  var html = "";
  var modifierParams = null;
  var actionName = getSelectedEventAction(Xonomy.lastSelectedHtmlId);
  var modifierName = getSelectedModifier(Xonomy.lastSelectedHtmlId, true);
  if (pepList.length > 0) {
    var modifierParams = getParamsOfSelectedModifier(pepList, modifierName, actionName);
    html += "<div>";
    if (modifierParams != null && modifierParams.length > 0) {
      html += "<b>Select a parameter of the selected event:</b><br /><br />";
      html += "<div class='menu'>";
      for (var i = 0; i < modifierParams.length; i++) {
        var parameter = "\"<parameter:" + getSimpleTypeName(modifierParams[i].type)
            + " xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter' name=\'" + Xonomy.xmlEscape(modifierParams[i].name)
            + "\' value=\'\'/>\"";
        html += "<div class='menuItem techno' onclick='Xonomy.newElementChild(\"" + Xonomy.lastSelectedHtmlId + "\"," + Xonomy.xmlEscape(parameter)
            + ",true)'>";
        html += "<span class='punc'>&apos;</span>";
        html += Xonomy.xmlEscape(modifierParams[i].name);
        html += "<span class='punc'>&apos;</span>";
        html += " of type: " + getSimpleTypeName(modifierParams[i].type);
        html += "</div>";
      }
    } else {
      if (actionName == "") {
        html += "<b>There is no event selected!</b><br />";
      } else if (modifierName == "") {
        html += "<b>There is no modifier selected!</b><br />";
      } else {
        html += "<b>There is no parameter available for the selected modifier!</b><br />"
      }
    }
    html += "</div>";
  }
  if (html != "") {
    document.body.appendChild(Xonomy.makeBubble(html));
    Xonomy.showBubble($("#" + Xonomy.lastSelectedHtmlId + " > .tag.opening > .name"));
  }
}

// Adds a current state of the policy editor to the undo list.
Xonomy.addUndoItem = function() {
  var currentPolicy = Xonomy.harvest();
  if (currentPolicy != Xonomy.undoList[Xonomy.undoList.length - 1]) {
    if (Xonomy.undoList.length - Xonomy.undoListCounter > 0) {
      var undoListSize = Xonomy.undoList.length;
      for (var i = Xonomy.undoListCounter; i < undoListSize; i++) {
        Xonomy.undoList.pop();
      }
    }
    Xonomy.undoList[Xonomy.undoListCounter] = Xonomy.harvest();
    Xonomy.undoListCounter++;
    var undoButton = document.getElementById("undo_button");
    if (Xonomy.undoListCounter > 1 && undoButton.disabled) {
      undoButton.disabled = false;
      undoButton.className = undoButton.className.replace(" disabled", "");
    }
    var redoButton = document.getElementById("redo_button");
    if (!redoButton.disabled) {
      redoButton.disabled = true;
      redoButton.className += " disabled";
    }
  }
}

// Undos the last change made in the policy editor
Xonomy.undo = function() {
  if (Xonomy.undoListCounter > 1) {
    var undoItemCounter = (--Xonomy.undoListCounter) - 1;
    Xonomy.render(Xonomy.undoList[undoItemCounter], document.getElementById("editor"), ind2uceSpec);
    if (undoItemCounter == 0) {
      var undoButton = document.getElementById("undo_button");
      undoButton.disabled = true;
      undoButton.className += " disabled";
    }
    var redoButton = document.getElementById("redo_button");
    if (redoButton.disabled) {
      redoButton.disabled = false;
      redoButton.className = redoButton.className.replace(" disabled", "");
    }
    Xonomy.setDirty(true);
  }
}

// Redos the last change made by an undo in the policy editor
Xonomy.redo = function() {
  if (Xonomy.undoListCounter < Xonomy.undoList.length) {
    Xonomy.render(Xonomy.undoList[Xonomy.undoListCounter++], document.getElementById("editor"), ind2uceSpec);
    var undoButton = document.getElementById("undo_button");
    if (undoButton.disabled) {
      undoButton.disabled = false;
      undoButton.className = undoButton.className.replace(" disabled", "");
    }
    var redoButton = document.getElementById("redo_button");
    if (Xonomy.undoListCounter >= Xonomy.undoList.length) {
      redoButton.disabled = true;
      redoButton.className += " disabled";
    }
    Xonomy.setDirty(true);
  }
}

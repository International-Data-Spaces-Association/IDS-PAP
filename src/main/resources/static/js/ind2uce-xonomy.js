var Xonomy = {
  lang: "", // "en"|"de"|fr"| ...
  mode: "nerd", // "nerd"|"laic"
};


Xonomy.dirty = false;
Xonomy.formSubmit = false;
Xonomy.undoList = [];
Xonomy.undoListCounter = 0;
Xonomy.lastSelectedHtmlId = "";
Xonomy.namespaces = {}; // eg. "xmlns:mbm": "http://lexonista.com"
Xonomy.lastIDNum = 0;
Xonomy.docSpec = null;
Xonomy.notclick = false; // should the latest click-off event be ignored?
Xonomy.clearChars = false; // if true, un-highlight any highlighted characters at the next click-off event
Xonomy.draggingID = null; // what are we dragging?
Xonomy.errors = []; // array of {htmlID: "", text: ""}
Xonomy.warnings = [];
Xonomy.showHelp = false;
Xonomy.helpLinkPrefix = "";
Xonomy.illegalID=false;

Xonomy.setDirty = function(dirty) {
  //console.log("set xononomy dirty to "+dirty);
  Xonomy.dirty = dirty;
  if (Xonomy.illegalID) {
    dirty = false;
    //console.log("reset xononomy dirty to "+dirty);
  }
  document.getElementById("save_button").disabled = !dirty;
  try {
    var dButtons = document.getElementsByName("deploy_button");
    //		console.log("did find "+dButtons.length+ " deploy buttons");
    for (var i = 0; i < dButtons.length; i++) {
      dButtons[i].disabled = dirty;
    }
  } catch (ex) {
    console.log("no such element");
  }
};

Xonomy.setMode = function(mode) {
  if (mode == "nerd" || mode == "laic")
    Xonomy.mode = mode;
  if (mode == "nerd") {
    $(".xonomy").removeClass("laic").addClass("nerd");
  }
  if (mode == "laic") {
    $(".xonomy").removeClass("nerd").addClass("laic");
  }
};

Xonomy.setHelp = function(helpActive,ind2uceSpec) {
  Xonomy.showHelp = helpActive;
  if (Xonomy.lastIDNum > 0) {
    Xonomy.render(Xonomy.harvest(), document.getElementById("editor"), ind2uceSpec);
  }
};

Xonomy.setHelpLink = function(url) {
  Xonomy.helpLinkPrefix = url;
};

Xonomy.xmlEscape = function(str) {
  var escapedString = str;
  escapedString = Xonomy.replaceAll(escapedString, "&", "&amp;");
  escapedString = Xonomy.replaceAll(escapedString, "\"", "&quot;");
  escapedString = Xonomy.replaceAll(escapedString, "'", "&apos;");
  escapedString = Xonomy.replaceAll(escapedString, "<", "&lt;");
  escapedString = Xonomy.replaceAll(escapedString, ">", "&gt;");
  return escapedString;
};

Xonomy.xmlUnescape = function(str) {
  var unescapedString = str;
  unescapedString = Xonomy.replaceAll(unescapedString, "&gt;", ">");
  unescapedString = Xonomy.replaceAll(unescapedString, "&lt;", "<");
  unescapedString = Xonomy.replaceAll(unescapedString, "&apos;", "'");
  unescapedString = Xonomy.replaceAll(unescapedString, "&quot;", "\"");
  unescapedString = Xonomy.replaceAll(unescapedString, "&amp;", "&");
  return unescapedString;
};

Xonomy.escapeRegExp = function(str) {
  return str.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "\\$1");
};

Xonomy.replaceAll = function(str, find, replace) {
  return str.replace(new RegExp(Xonomy.escapeRegExp(find), 'g'), replace);
};

Xonomy.isNamespaceDeclaration = function(attributeName) {
  // Tells you whether an attribute name is a namespace declaration.
  var ret = false;
  if (attributeName == "xmlns")
    ret = true;
  if (attributeName.length >= 6 && attributeName.substring(0, 6) == "xmlns:")
    ret = true;
  return ret;
};

Xonomy.xml2js = function(xml, jsParent) {
  if (typeof (xml) == "string")
    xml = $.parseXML(xml);
  if (xml.documentElement)
    xml = xml.documentElement;
  var js = new Xonomy.surrogate(jsParent);
  js.type = "element";
  js.name = xml.nodeName;
  js.htmlID = "";
  js.attributes = [];
  for (var i = 0; i < xml.attributes.length; i++) {
    var attr = xml.attributes[i];
    if (!Xonomy.isNamespaceDeclaration(attr.nodeName)) {
      if (attr.value == "getValueChangedUUID()")
        attr.value = getValueChangedUUID();
      if (attr.name != "xml:space") {
        js["attributes"].push({
          type: "attribute",
          name: attr.nodeName,
          value: attr.value,
          htmlID: ""
        });
      }
    } else {
      Xonomy.namespaces[attr.nodeName] = attr.value;
    }
  }
  js.children = [];
  for (var i = 0; i < xml.childNodes.length; i++) {
    var child = xml.childNodes[i];
    if (child.nodeType == 1) { // element node
      js["children"].push(Xonomy.xml2js(child, js));
    }
    if (child.nodeType == 3) { // text node
      js["children"].push({
        type: "text",
        value: child.nodeValue,
        htmlID: ""
      });
    }
  }
  js = Xonomy.enrichElement(js);
  return js;
};

Xonomy.js2xml = function(js) {
  var xml = "<" + js.name;
  for (var i = 0; i < js.attributes.length; i++) {
    var att = js.attributes[i];
    xml += " " + att.name + "='" + Xonomy.xmlEscape(att.value) + "'";
  }
  if (js.children.length > 0) {
    var hasText = false;
    for (var i = 0; i < js.children.length; i++) {
      var child = js.children[i];
      if (child.type == "text")
        hasText = true;
    }
    // if (hasText)
    // xml += " xml:space='preserve'";
    xml += ">";
    for (var i = 0; i < js.children.length; i++) {
      var child = js.children[i];
      if (child.type == "text")
        xml += Xonomy.xmlEscape(child.value); // text node
      else if (child.type == "element")
        xml += Xonomy.js2xml(child); // element node
    }
    xml += "</" + js.name + ">";
  } else {
    xml += "/>";
  }
  return xml;
};

Xonomy.enrichElement = function(jsElement) {
  jsElement.hasAttribute = function(name) {
    var ret = false;
    for (var i = 0; i < this.attributes.length; i++) {
      if (this.attributes[i].name == name)
        ret = true;
    }
    return ret;
  };
  jsElement.getAttributeValue = function(name, ifNull) {
    var ret = ifNull;
    for (var i = 0; i < this.attributes.length; i++) {
      if (this.attributes[i].name == name)
        ret = this.attributes[i].value;
    }
    return ret;
  };
  jsElement.hasChildElement = function(name) {
    var ret = false;
    for (var i = 0; i < this.children.length; i++) {
      if (this.children[i].name == name)
        ret = true;
    }
    return ret;
  };
  return jsElement;
};

Xonomy.verifyDocSpec = function() { // make sure the docSpec object has everything it needs
  if (!Xonomy.docSpec || typeof (Xonomy.docSpec) != "object")
    Xonomy.docSpec = {};
  if (!Xonomy.docSpec.elements || typeof (Xonomy.docSpec.elements) != "object")
    Xonomy.docSpec.elements = {};
  if (!Xonomy.docSpec.onchange || typeof (Xonomy.docSpec.onchange) != "function")
    Xonomy.docSpec.onchange = function() {
    };
  if (!Xonomy.docSpec.validate || typeof (Xonomy.docSpec.validate) != "function")
    Xonomy.docSpec.validate = function() {
    };
};

Xonomy.asFunction = function(specProperty, defaultValue) {
  if (typeof (specProperty) == "function")
    return specProperty;
  else if (typeof (specProperty) == typeof (defaultValue))
    return function() {
      return specProperty;
    };
  else
    return function() {
      return defaultValue;
    };
};

Xonomy.verifyDocSpecElement = function(name) { // make sure the DocSpec object has such an element, that the element has everything it needs
  if (!Xonomy.docSpec.elements[name] || typeof (Xonomy.docSpec.elements[name]) != "object")
    Xonomy.docSpec.elements[name] = {};
  var spec = Xonomy.docSpec.elements[name];
  if (!spec.attributes || typeof (spec.attributes) != "object")
    spec.attributes = {};
  if (!spec.menu || typeof (spec.menu) != "object")
    spec.menu = [];
  if (!spec.inlineMenu || typeof (spec.inlineMenu) != "object")
    spec.inlineMenu = [];
  if (!spec.canDropTo || typeof (spec.canDropTo) != "object")
    spec.canDropTo = [];
  if (!spec.mustBeAfter || typeof (spec.mustBeAfter) != "object")
    spec.mustBeAfter = [];
  if (!spec.mustBeBefore || typeof (spec.mustBeBefore) != "object")
    spec.mustBeBefore = [];
  spec.oneliner = Xonomy.asFunction(spec.oneliner, false);
  spec.hasText = Xonomy.asFunction(spec.hasText, false);
  spec.collapsible = Xonomy.asFunction(spec.collapsible, true);
  spec.collapsed = Xonomy.asFunction(spec.collapsed, false);
  for (var i = 0; i < spec.menu.length; i++)
    Xonomy.verifyDocSpecMenuItem(spec.menu[i]);
  for (var i = 0; i < spec.inlineMenu.length; i++)
    Xonomy.verifyDocSpecMenuItem(spec.inlineMenu[i]);
};

Xonomy.verifyDocSpecAttribute = function(elementName, attributeName) {
  var elSpec = Xonomy.docSpec.elements[elementName];
  if (!elSpec.attributes[attributeName] || typeof (elSpec.attributes[attributeName]) != "object")
    elSpec.attributes[attributeName] = {};
  var spec = elSpec.attributes[attributeName];
  if (!spec.asker || typeof (spec.asker) != "function")
    spec.asker = function() {
      return "";
    };
  if (!spec.menu || typeof (spec.menu) != "object")
    spec.menu = [];
  for (var i = 0; i < spec.menu.length; i++)
    Xonomy.verifyDocSpecMenuItem(spec.menu[i]);
};

Xonomy.verifyDocSpecMenuItem = function(menuItem) { // make sure the menu item has all it needs
  if (!menuItem.caption)
    menuItem.caption = "?";
  if (!menuItem.action || typeof (menuItem.action) != "function")
    menuItem.action = function() {
    };
  if (!menuItem.hideIf)
    menuItem.hideIf = function() {
      return false;
    };
};

Xonomy.nextID = function() {
  return "xonomy" + (++Xonomy.lastIDNum);
};

Xonomy.currentID = function() {
  return "xonomy" + (Xonomy.lastIDNum);
};

Xonomy.refresh = function() {
  $(".xonomy .children ").each(function() { // determine whether each element does or doesn't have children:
    if (this.childNodes.length == 0 && !$(this.parentNode).hasClass("hasText"))
      $(this.parentNode).addClass("noChildren");
    else {
      $(this.parentNode).removeClass("noChildren");
      Xonomy.updateCollapsoid(this.parentNode.id);
    }
  });
  var merged = false;
  while (!merged) { // merge adjacent text nodes
    merged = true;
    var textnodes = $(".xonomy .textnode").toArray();
    for (var i = 0; i < textnodes.length; i++) {
      var $this = $(textnodes[i]);
      if ($this.next().hasClass("textnode")) {
        var js1 = Xonomy.harvestText($this.toArray()[0]);
        var js2 = Xonomy.harvestText($this.next().toArray()[0]);
        js1.value += js2.value;
        $this.next().remove();
        $this.replaceWith(Xonomy.renderText(js1));
        merged = false;
        break;
      }
    }
  }
  $(".xonomy .element ").each(function() { // reorder elements if necessary
    var elSpec = Xonomy.docSpec.elements[this.getAttribute("data-name")];
    if (elSpec.mustBeBefore) { // is it after an element it cannot be after? then move it up until it's not!
      var $this = $(this);
      var ok;
      do {
        ok = true;
        for (var ii = 0; ii < elSpec.mustBeBefore.length; ii++) {
          if ($this.prevAll("*[data-name='" + elSpec.mustBeBefore[ii] + "']").toArray().length > 0) {
            $this.prev().before($this);
            ok = false;
          }
        }
      } while (!ok)
    }
    if (elSpec.mustBeAfter) { // is it before an element it cannot be before? then move it down until it's not!
      var $this = $(this);
      var ok;
      do {
        ok = true;
        for (var ii = 0; ii < elSpec.mustBeAfter.length; ii++) {
          if ($this.nextAll("*[data-name='" + elSpec.mustBeAfter[ii] + "']").toArray().length > 0) {
            $this.next().after($this);
            ok = false;
          }
        }
      } while (!ok)
    }
  });
  $(".xonomy .attribute ").each(function() { // reorder attributes if necessary
    var atName = this.getAttribute("data-name");
    var elName = this.parentNode.parentNode.parentNode.getAttribute("data-name");
    var elSpec = Xonomy.docSpec.elements[elName];
    var mustBeAfter = [];
    for ( var sibName in elSpec.attributes) {
      if (sibName == atName)
        break;
      else
        mustBeAfter.push(sibName);
    }
    var mustBeBefore = [];
    var seen = false;
    for ( var sibName in elSpec.attributes) {
      if (sibName == atName)
        seen = true;
      else if (seen)
        mustBeBefore.push(sibName);
    }
    if (mustBeBefore.length > 0) { // is it after an attribute it cannot be after? then move it up until it's not!
      var $this = $(this);
      var ok;
      do {
        ok = true;
        for (var ii = 0; ii < mustBeBefore.length; ii++) {
          if ($this.prevAll("*[data-name='" + mustBeBefore[ii] + "']").toArray().length > 0) {
            $this.prev().before($this);
            ok = false;
          }
        }
      } while (!ok)
    }
    if (mustBeAfter.length > 0) { // is it before an attribute it cannot be before? then move it down until it's not!
      var $this = $(this);
      var ok;
      do {
        ok = true;
        for (var ii = 0; ii < mustBeAfter.length; ii++) {
          if ($this.nextAll("*[data-name='" + mustBeAfter[ii] + "']").toArray().length > 0) {
            $this.next().after($this);
            ok = false;
          }
        }
      } while (!ok)
    }
  });
};


Xonomy.harvest = function() { // harvests the contents of an editor Returns xml-as-string.
  var rootElement = $(".xonomy .element").first().toArray()[0];
  var js = Xonomy.harvestElement(rootElement);
  for ( var key in Xonomy.namespaces) {
    js.attributes.push({
      type : "attribute",
      name : key,
      value : Xonomy.namespaces[key],
      parent : js
    });
  }
  return Xonomy.js2xml(js);
};

Xonomy.harvestElement = function(htmlElement, jsParent) {
  if (typeof htmlElement != 'undefined' && htmlElement != null) {
    var js = new Xonomy.surrogate(jsParent);
    js.type = "element";
    js.name = htmlElement.getAttribute("data-name");
    js.htmlID = htmlElement.id;
    js.attributes = [];
    var htmlAttributes = $(htmlElement).find(".tag.opening > .attributes").toArray()[0];
    for (var i = 0; i < htmlAttributes.childNodes.length; i++) {
      var htmlAttribute = htmlAttributes.childNodes[i];
      if ($(htmlAttribute).hasClass("attribute"))
        js["attributes"].push(Xonomy.harvestAttribute(htmlAttribute, js));
    }
    js.children = [];
    var htmlChildren = $(htmlElement).children(".children").toArray()[0];
    for (var i = 0; i < htmlChildren.childNodes.length; i++) {
      var htmlChild = htmlChildren.childNodes[i];
      if ($(htmlChild).hasClass("element"))
        js["children"].push(Xonomy.harvestElement(htmlChild, js));
      else if ($(htmlChild).hasClass("textnode"))
        js["children"].push(Xonomy.harvestText(htmlChild, js));
    }
    js = Xonomy.enrichElement(js);
    return js;
  } else {
    return null;
  }
};

Xonomy.harvestAttribute = function(htmlAttribute, jsParent) {
  var js = new Xonomy.surrogate(jsParent);
  js.type = "attribute";
  js.name = htmlAttribute.getAttribute("data-name");
  js.htmlID = htmlAttribute.id;
  js.value = htmlAttribute.getAttribute("data-value");
  return js;
};

Xonomy.surrogate = function(jsParent) {
  this.internalParent = jsParent;
};

Xonomy.surrogate.prototype.parent = function() {
  if (!this.internalParent) {
    this.internalParent = Xonomy.harvestParentOf(this);
  }
  return this.internalParent;
};

Xonomy.harvestText = function(htmlText, jsParent) {
  var js = new Xonomy.surrogate(jsParent);
  js.type = "text";
  js.htmlID = htmlText.id;
  js.value = htmlText.getAttribute("data-value");
  return js;
};

Xonomy.harvestParentOf = function(js) {
  var jsParent = null;
  var $parent = $("#" + js.htmlID).parent().closest(".element");
  if ($parent.toArray().length == 1) {
    jsParent = Xonomy.harvestElement($parent.toArray()[0]);
    for (var i = 0; i < jsParent.attributes.length; i++)
      if (jsParent.attributes[i].htmlID == js.htmlID)
        jsParent.attributes[i] = js;
    for (var i = 0; i < jsParent.children.length; i++)
      if (jsParent.children[i].htmlID == js.htmlID)
        jsParent.children[i] = js;
  }
  return jsParent;
};

Xonomy.render = function(data, editor, docSpec) { // renders the contents of an editor The data can be a Xonomy-compliant XML document, a Xonomy-compliant xml-as-string, or a Xonomy-compliant
  // JavaScript object. The editor can be an HTML element, or the string ID of one.
  Xonomy.docSpec = docSpec;
  Xonomy.verifyDocSpec();

  // Clear namespace cache:
  Xonomy.namespaces = {};

  // Convert doc to a JavaScript object, if it isn't a JavaScript object already:
  if (typeof (data) == "string")
    data = $.parseXML(data);
  if (data.documentElement)
    data = Xonomy.xml2js(data);

  // Make sure editor refers to an HTML element, if it doesn't already:
  if (typeof (editor) == "string")
    editor = document.getElementById(editor);
  if (!$(editor).hasClass("xonomy"))
    $(editor).addClass("xonomy"); // make sure it has class "xonomy"
  $(editor).addClass(Xonomy.mode);

  $(editor).hide();
  editor.innerHTML = Xonomy.renderElement(data, editor);
  $(editor).show();

  // Make sure the "click off" handler is attached:
  $(document.body).off("click", Xonomy.clickoff);
  $(document.body).on("click", Xonomy.clickoff);

  // Make sure the "drag end" handler is attached:
  $(document.body).off("dragend", Xonomy.dragend);
  $(document.body).on("dragend", Xonomy.dragend);

  Xonomy.refresh();
  Xonomy.validate();
};

Xonomy.renderElement = function(element) {
  var htmlID = Xonomy.nextID();
  Xonomy.verifyDocSpecElement(element.name);
  var spec = Xonomy.docSpec.elements[element.name];
  var classNames = "element";
  if (spec.canDropTo && spec.canDropTo.length > 0)
    classNames += " draggable";
  var hasText = spec.hasText(element);
  if (hasText)
    classNames += " hasText";
  if (spec.inlineMenu && spec.inlineMenu.length > 0)
    classNames += " hasInlineMenu";
  if (spec.oneliner(element))
    classNames += " oneliner";
  if (!spec.collapsible()) {
    classNames += " uncollapsible";
  } else {
    if (spec.collapsed(element) && element.children.length > 0)
      classNames += " collapsed";
  }
  if (spec.menu.length > 0)
    classNames += " hasMenu"; // not always accurate: whether an element has a menu is actually determined at runtime
  var displayName = element.name;
  if (spec.displayName)
    displayName = "<span class='displayName'>" + Xonomy.textByLang(spec.displayName) + "</span>";
  var html = "";
  html += '<div data-name="' + element.name + '" id="' + htmlID + '" class="' + classNames + '">';
  html += '<span class="connector">';
  html += '<span class="plusminus" onclick="Xonomy.plusminus(\'' + htmlID + '\')"></span>';
  html += '<span class="draghandle" draggable="true" ondragstart="Xonomy.drag(event)"></span>';
  html += '</span>';
  html += '<span class="tag opening" style="background-color: ' + spec.backgroundColour + ';">';
  html += '<span class="punc">&lt;</span>';
  html += '<span class="name" onclick="Xonomy.click(\'' + htmlID + '\', \'openingTagName\')">' + displayName + '</span>';
  if (Xonomy.showHelp) {
    html += '<span class="policy-help"><span class="inside" onclick="showHelpModal(\'' + Xonomy.helpLinkPrefix + '#' + spec.helpLink
        + '\')"></span></span>';
  }
  html += '<span class="policy-error"><span class="inside"  onmouseover="Xonomy.info(\'' + htmlID + '\', \'policy-error\')" onmouseout="removeMsg()"></span></span>';
  html += '<span class="policy-warning"><span class="inside"  onmouseover="Xonomy.info(\'' + htmlID + '\', \'policy-warning\')" onmouseout="removeMsg()"></span></span>';
  html += '<span class="attributes">';
  for (var i = 0; i < element.attributes.length; i++) {
    Xonomy.verifyDocSpecAttribute(element.name, element.attributes[i].name);
    html += Xonomy.renderAttribute(element.attributes[i], element.name);
  }
  html += '</span>';
  html += '<span class="punc slash">/</span>';
  html += '<span class="punc">&gt;</span>';
  html += '</span>';
  html += '<span class="childrenCollapsed" onclick="Xonomy.plusminus(\'' + htmlID + '\', true)">&middot;&middot;&middot;</span>';
  html += '<div class="children">';
  var prevChildType = "";
  if (hasText && (element.children.length == 0 || element.children[0].type == "element")) {
    html += Xonomy.renderText({
      type : "text",
      value : ""
    }); // if inline layout, insert empty text node between two elements
  }
  for (var i = 0; i < element.children.length; i++) {
    var child = element.children[i];
    if (hasText && prevChildType == "element" && child.type == "element") {
      html += Xonomy.renderText({
        type : "text",
        value : ""
      }); // if inline layout, insert empty text node between two elements
    }
    if (child.type == "text")
      html += Xonomy.renderText(child); // text node
    else if (child.type == "element")
      html += Xonomy.renderElement(child); // element node
    prevChildType = child.type;
  }
  if (hasText && element.children.length > 1 && element.children[element.children.length - 1].type == "element") {
    html += Xonomy.renderText({
      type : "text",
      value : ""
    }); // if inline layout, insert empty text node between two elements
  }
  html += '</div>';
  html += '<span class="tag closing" style="background-color: ' + spec.backgroundColour + ';">';
  html += '<span class="punc">&lt;</span>';
  html += '<span class="punc">/</span>';
  html += '<span class="name" onclick="Xonomy.click(\'' + htmlID + '\', \'closingTagName\')">' + displayName + '</span>';
  html += '<span class="punc">&gt;</span>';
  html += '</span>';
  html += '</div>';
  element.htmlID = htmlID;
  return html;
};

Xonomy.renderAttribute = function(attribute, optionalParentName) {
  var htmlID = Xonomy.nextID();
  var readonly = false;
  classNames = "attribute";
  if (readonly)
    classNames += " readonly";

  var displayName = attribute.name;
  if (optionalParentName) {
    var spec = Xonomy.docSpec.elements[optionalParentName].attributes[attribute.name];
    if (spec) {
      if (spec.displayName)
        displayName = "<span class='displayName'>" + Xonomy.textByLang(spec.displayName) + "</span>";
    }
  }

  var html = "";
  html += '<span data-name="' + attribute.name + '" data-value="' + Xonomy.xmlEscape(attribute.value) + '" id="' + htmlID + '" class="' + classNames
      + '">';
  html += '<span class="punc"> </span>';
  var onclick = '';
  if (!readonly)
    onclick = ' onclick="Xonomy.click(\'' + htmlID + '\', \'attributeName\')"';
  html += '<span class="name"' + onclick + '>' + displayName + '</span>';
  html += '<span class="policy-error"><span class="inside" onclick="Xonomy.click(\'' + htmlID + '\', \'policy-error\')"></span></span>';
  html += '<span class="policy-warning"><span class="inside" onclick="Xonomy.click(\'' + htmlID + '\', \'policy-warning\')"></span></span>';
  html += '<span class="punc">=</span>';
  var onclick = '';
  if (!readonly)
    onclick = ' onclick="Xonomy.click(\'' + htmlID + '\', \'attributeValue\')"';
  html += '<span class="valueContainer"' + onclick + '>';
  html += '<span class="punc">&apos;</span>';
  html += '<span class="value">' + Xonomy.xmlEscape(attribute.value) + '</span>';
  html += '<span class="punc">&apos;</span>';
  html += '</span>';
  html += '</span>';
  attribute.htmlID = htmlID;
  return html;
};

Xonomy.renderText = function(text) {
  var htmlID = Xonomy.nextID();
  var classNames = "textnode";
  if ($.trim(text.value) == "")
    classNames += " whitespace";
  if (text.value == "")
    classNames += " empty";
  var html = "";
  html += '<div id="' + htmlID + '" data-value="' + Xonomy.xmlEscape(text.value) + '" class="' + classNames + '">';
  html += '<span class="connector"></span>';
  html += '<span class="value" onclick="Xonomy.click(\'' + htmlID
      + '\', \'text\')"><span class="insertionPoint"><span class="inside"></span></span><span class="dots"></span>' + Xonomy.chewText(text.value)
      + '</span>';
  html += '</div>';
  text.htmlID = htmlID;
  return html;
};

Xonomy.chewText = function(txt) {
  var ret = "";
  ret += "<span class='word'>"; // start word
  for (var i = 0; i < txt.length; i++) {
    if (txt[i] == " ")
      ret += "</span>"; // end word
    var t = Xonomy.xmlEscape(txt[i])
    if (i == 0 && t == " ")
      t = "&nbsp;"; // leading space
    if (i == txt.length - 1 && t == " ")
      t = "&nbsp;"; // trailing space
    ret += "<span class='char'>" + t
        + "<span class='selector'><span class='inside' onclick='Xonomy.charClick(this.parentNode.parentNode)'></span></span></span>";
    if (txt[i] == " ")
      ret += "<span class='word'>"; // start word
  }
  ret += "</span>"; // end word
  return ret;
};

Xonomy.charClick = function(c) {
  Xonomy.clickoff();
  Xonomy.notclick = true;
  if ($(".xonomy .char.on").toArray().length == 1 && $(".xonomy .char.on").closest(".element").is($(c).closest(".element"))) {
    var $element = $(".xonomy .char.on").closest(".element");
    var chars = $element.find(".char").toArray();
    var iFrom = $.inArray($(".xonomy .char.on").toArray()[0], chars);
    var iTill = $.inArray(c, chars);
    if (iFrom > iTill) {
      var temp = iFrom;
      iFrom = iTill;
      iTill = temp;
    }
    for (var i = 0; i < chars.length; i++) { // highlight all chars between start and end
      if (i >= iFrom && i <= iTill)
        $(chars[i]).addClass("on");
    }
    // Save for later the info Xonomy needs to know what to wrap:
    Xonomy.textFromID = $(chars[iFrom]).closest(".textnode").attr("id");
    Xonomy.textTillID = $(chars[iTill]).closest(".textnode").attr("id");
    Xonomy.textFromIndex = $.inArray(chars[iFrom], $("#" + Xonomy.textFromID).find(".char").toArray());
    Xonomy.textTillIndex = $.inArray(chars[iTill], $("#" + Xonomy.textTillID).find(".char").toArray());
    // Show inline menu etc:
    var htmlID = $element.attr("id");
    var content = Xonomy.inlineMenu(htmlID); // compose bubble content
    if (content != "") {
      document.body.appendChild(Xonomy.makeBubble(content)); // create bubble
      Xonomy.showBubble($("#" + htmlID + " .char.on").last()); // anchor bubble to highlighted chars
    }
    Xonomy.clearChars = true;
  } else {
    $(".xonomy .char.on").removeClass("on");
    $(c).addClass("on");
  }
};

Xonomy.plusminus = function(htmlID, forceExpand) {
  var $element = $("#" + htmlID);
  var $children = $element.children(".children");
  if ($element.hasClass("collapsed")) {
    $children.hide();
    $element.removeClass("collapsed");
    if ($element.hasClass("oneliner"))
      $children.fadeIn("fast");
    else
      $children.slideDown("fast");
  } else if (!forceExpand) {
    Xonomy.updateCollapsoid(htmlID);
    if ($element.hasClass("oneliner"))
      $children.fadeOut("fast", function() {
        $element.addClass("collapsed");
      });
    else
      $children.slideUp("fast", function() {
        $element.addClass("collapsed");
      });
  }
};

Xonomy.updateCollapsoid = function(htmlID) {
  var $element = $("#" + htmlID);
  var whisper = "";
  var elementName = $element.data("name");
  var spec = Xonomy.docSpec.elements[elementName];
  if (spec.collapsoid) {
    whisper = spec.collapsoid(Xonomy.harvestElement($element.toArray()[0]));
  } else {
    var abbreviated = false;
    $element.find(".textnode").each(function() {
      var txt = Xonomy.harvestText(this).value;
      for (var i = 0; i < txt.length; i++) {
        if (whisper.length < 35)
          whisper += txt[i];
        else
          abbreviated = true;
      }
      whisper += " ";
    });
    whisper = whisper.replace(/  +/g, " ").replace(/ +$/g, "");
    if (abbreviated && !$element.hasClass("oneliner") && whisper != "...")
      whisper += "...";
  }
  if (whisper == "" || !whisper)
    whisper = "...";
  $element.children(".childrenCollapsed").html(whisper);
};

Xonomy.click = function(htmlID, what) {
  if (!Xonomy.notclick) {
    Xonomy.clickoff();
    $(".xonomy .char.on").removeClass("on");
    if (what == "openingTagName" || what == "closingTagName") {
      $("#" + htmlID).addClass("currentXonomy"); // make the element currentXonomy
      var content = Xonomy.elementMenu(htmlID); // compose bubble content
      if (content != "") {
        document.body.appendChild(Xonomy.makeBubble(content)); // create bubble
        if (what == "openingTagName")
          Xonomy.showBubble($("#" + htmlID + " > .tag.opening > .name")); // anchor bubble to opening tag
        if (what == "closingTagName")
          Xonomy.showBubble($("#" + htmlID + " > .tag.closing > .name")); // anchor bubble to closing tag
      }
      // if there is an input field, set the focus to it
      document.getElementById("js-filterMenu").focus();
    }
    if (what == "attributeName") {
      $("#" + htmlID).addClass("currentXonomy"); // make the attribute currentXonomy
      var content = Xonomy.attributeMenu(htmlID); // compose bubble content
      if (content != "") {
        document.body.appendChild(Xonomy.makeBubble(content)); // create bubble
        Xonomy.showBubble($("#" + htmlID + " > .name")); // anchor bubble to attribute name
      }
    }
    if (what == "attributeValue") {
      $("#" + htmlID + " > .valueContainer").addClass("currentXonomy"); // make attribute value currentXonomy
      var name = $("#" + htmlID).attr("data-name"); // obtain attribute's name
      var value = $("#" + htmlID).attr("data-value"); // obtain currentXonomy value
      var elName = $("#" + htmlID).closest(".element").attr("data-name");
      Xonomy.verifyDocSpecAttribute(elName, name);
      var spec = Xonomy.docSpec.elements[elName].attributes[name];
      var content = spec.asker(value, spec.askerParameter, htmlID); // compose bubble content
      if (content != "") {
        document.body.appendChild(Xonomy.makeBubble(content)); // create bubble
        Xonomy.showBubble($("#" + htmlID + " > .valueContainer > .value")); // anchor bubble to value
        Xonomy.answer = function(val) {
          Xonomy.formSubmit = true;
          var obj = document.getElementById(htmlID);
          if (val != $("#" + htmlID + " > .valueContainer > .value").text()) {
            var html = Xonomy.renderAttribute({
              type : "attribute",
              name : name,
              value : val
            }, elName);
            $(obj).replaceWith(html);
            Xonomy.changed();
          }
          Xonomy.clickoff();
        };
      }
    }
    if (what == "text") {
      $("#" + htmlID).addClass("currentXonomy");
      var value = $("#" + htmlID).attr("data-value"); // obtain currentXonomy value
      var elName = $("#" + htmlID).closest(".element").attr("data-name");
      var spec = Xonomy.docSpec.elements[elName];
      if (typeof (spec.asker) != "function") {
        var content = Xonomy.askLongString(value); // compose bubble content
      } else {
        var content = spec.asker(value, spec.askerParameter, htmlID); // use specified asker
      }
      document.body.appendChild(Xonomy.makeBubble(content)); // create bubble
      Xonomy.showBubble($("#" + htmlID + " > .value")); // anchor bubble to value
      Xonomy.answer = function(val) {
      	Xonomy.formSubmit = true;
        var obj = document.getElementById(htmlID);
        if (val != Xonomy.harvestText(document.getElementById(htmlID)).value) {
          var jsText = {
            type : "text",
            value : val
          };
          var html = Xonomy.renderText(jsText);
          $(obj).replaceWith(html);
          Xonomy.changed(Xonomy.harvestText(document.getElementById(jsText.htmlID)));
        }
        Xonomy.clickoff();
      };
    }
    if (what == "policy-error") {
      // $("#"+htmlID).addClass("currentXonomy");
      var content = ""; // compose bubble content
      for (var iWarning = 0; iWarning < Xonomy.errors.length; iWarning++) {
        var error = Xonomy.errors[iWarning];
        if (error.htmlID == htmlID) {
          content += "<div class='warning'>" + Xonomy.formatCaption(Xonomy.textByLang(error.text)) + "</div>";
        }
      }
      document.body.appendChild(Xonomy.makeBubble(content)); // create bubble
      Xonomy.showBubble($("#" + htmlID + " .policy-error .inside").first()); // anchor bubble to policy-error
    }
    if (what == "policy-warning") {
      // $("#"+htmlID).addClass("currentXonomy");
      var content = ""; // compose bubble content
      for (var iWarning = 0; iWarning < Xonomy.warnings.length; iWarning++) {
        var warning = Xonomy.warnings[iWarning];
        if (warning.htmlID == htmlID) {
          content += "<div class='warning'>" + Xonomy.formatCaption(Xonomy.textByLang(warning.text)) + "</div>";
        }
      }
      document.body.appendChild(Xonomy.makeBubble(content)); // create bubble
      Xonomy.showBubble($("#" + htmlID + " .policy-warning .inside").first()); // anchor bubble to policy-warning
    }
    Xonomy.notclick = true;
  }
};

Xonomy.info = function(htmlID, what) {
  if (!Xonomy.notclick) {
    Xonomy.clickoff();
    $(".xonomy .char.on").removeClass("on");
    if (what == "policy-error") {
      var content = ""; // compose bubble content
      for (var iWarning = 0; iWarning < Xonomy.errors.length; iWarning++) {
        var error = Xonomy.errors[iWarning];
        if (error.htmlID == htmlID) {
          content += "<div class='warning'>" + Xonomy.formatCaption(Xonomy.textByLang(error.text)) + "</div>";
        }
      }
      document.body.appendChild(Xonomy.makeBubble(content)); // create bubble
      Xonomy.showBubble($("#" + htmlID + " .policy-error .inside").first()); // anchor bubble to policy-error
    }
    if (what == "policy-warning") {
      var content = ""; // compose bubble content
      for (var iWarning = 0; iWarning < Xonomy.warnings.length; iWarning++) {
        var warning = Xonomy.warnings[iWarning];
        if (warning.htmlID == htmlID) {
          content += "<div class='warning'>" + Xonomy.formatCaption(Xonomy.textByLang(warning.text)) + "</div>";
        }
      }
      document.body.appendChild(Xonomy.makeBubble(content)); // create bubble
      Xonomy.showBubble($("#" + htmlID + " .policy-warning .inside").first()); // anchor bubble to policy-warning
    }
    Xonomy.notclick = true;
  }
};

function removeMsg() {
  Xonomy.notclick = false;
  Xonomy.clickoff();
}
Xonomy.clickoff = function() { // event handler for the document-wide click-off
  // event.
  if (!Xonomy.notclick) {
    Xonomy.destroyBubble();
    $(".xonomy .currentXonomy").removeClass("currentXonomy");
    if (Xonomy.clearChars) {
      $(".xonomy .char.on").removeClass("on");
      Xonomy.clearChars = false;
    }
  }
  Xonomy.notclick = false;
};

Xonomy.destroyBubble = function() {
  if (document.getElementById("xonomyBubble")) {
    var bubble = document.getElementById("xonomyBubble");
    bubble.parentNode.removeChild(bubble);
  }
};

Xonomy.makeBubble = function(content) {
  Xonomy.destroyBubble();
  var bubble = document.createElement("div");
  bubble.id = "xonomyBubble";
  bubble.className = Xonomy.mode;
  bubble.innerHTML = "<div class='inside' onclick='Xonomy.notclick=true;'>" + "<div id='xonomyBubbleContent'>" + content + "</div>" + "</div>";
  return bubble;
};


Xonomy.showBubble = function($anchor) {
  var $bubble = $("#xonomyBubble");
  var offset = $anchor.offset();
  var left = offset.left;
  var top = offset.top;
  var screenWidth = $("body").width();
  var screenHeight = $(window).height();
  var bubbleHeight = $bubble.outerHeight();
  var width = $anchor.width();
  var availableHeight = 0;

  if (width > 40)
    width = 40;

  var height = $anchor.height();

  if (height > 25)
    height = 25;

  if (left < screenWidth / 2) {
    if (Xonomy.mode == "laic") {
      width = width - 25;
      height = height + 10;
    }
    //check if there is enough place to open the pop-up below
    if (top + bubbleHeight + height > screenHeight) { // not enough room below, open up
      //check if there is enough place to open up
      if (top - bubbleHeight - height < 0) { //also not enough place to open up 
        //then open below anyhow
        //set css-height of xonomy bubble
        availableHeight = screenHeight - top - height;

        //	set minimum size
        if (availableHeight < 150) {
          availableHeight = 150
        }
        availableHeight = availableHeight - 50;

        $('#xonomyBubbleContent div.menu').css('max-height', availableHeight + 'px');
        $('#xonomyBubble.laic #xonomyBubbleContent div.menu').css('max-height', availableHeight + 'px');

        $bubble.css({ top: (top + height) + "px", bottom: "", left: (left + width - 15) + "px" });
      } else { //there is enough place to open up	
        $bubble.css({ top: "", bottom: (screenHeight - top + 5) + "px", left: (left + width - 15) + "px" }); // 5px above for some padding. Anchor using bottom so animation opens upwards.
      }
    } else { //there is enough place to open below
      $bubble.css({ top: (top + height) + "px", bottom: "", left: (left + width - 15) + "px" });
    }
  } else {
    if (Xonomy.mode == "laic") {
      height = height + 10;
    }
    $bubble.addClass("rightAnchored");
    //check if there is enough place to open the pop-up below
    if (top + bubbleHeight + height > screenHeight) { // not enough room below, open up
      //check if there is enough place to open up
      if (top - bubbleHeight - height < 0) { //also not enough place to open up 
        //then open below anyhow
        //set css-height of xonomy bubble
        availableHeight = screenHeight - top - height;

        //	set minimum size
        if (availableHeight < 150) {
          availableHeight = 150;
        }
        availableHeight = availableHeight - 50;

        $('#xonomyBubbleContent div.menu').css('max-height', availableHeight + 'px');
        $('#xonomyBubble.laic #xonomyBubbleContent div.menu').css('max-height', availableHeight + 'px');

        $bubble.css({ top: (top + height) + "px", bottom: "", left: (left + width - 15) + "px" });
      } else { //there is enough place to open up	
        $bubble.css({ top: "", bottom: (screenHeight - top + 5) + "px", left: (left + width - 15) + "px" }); // 5px above for some padding. Anchor using bottom so animation opens upwards.
      }
    } else { //there is enough place to open below
      $bubble.css({ top: (top + height) + "px", bottom: "", left: (left + width - 15) + "px" });
    }

  }
  $bubble.slideDown("fast", function() {
    $bubble.find(".focusme").first().focus(); //if the context menu contains anything with the class name 'focusme', focus it.
  });
};


Xonomy.askString = function(defaultString) {
  var html = "";
  html += "<form onsubmit='Xonomy.answer(this.val.value); return false'>";
  html += "<input name='val' class='formcontrol focusme' value='" + Xonomy.xmlEscape(defaultString) + "'/>";
  html += "&nbsp;&nbsp;<input type='submit' class='btn btn-primary' value='OK'>";
  html += "</form>";
  return html;
};

Xonomy.askLongString = function(defaultString) {
  var html = "";
  html += "<form onsubmit='Xonomy.answer(this.val.value); return false'>";
  html += "<textarea name='val' class='formcontrol focusme' spellcheck='false' style='width: 400px; height: 100px;'>"
      + Xonomy.xmlEscape(defaultString) + "</textarea>";
  html += "<br /><br /><div><input type='submit' class='btn btn-primary pull-right' value='OK'></div>";
  html += "</form>";
  return html;
};

Xonomy.askPicklist = function(defaultString, picklist) {
  var html = "";
  html += "<div class='menu'>";
  for (var i = 0; i < picklist.length; i++) {
    var item = picklist[i];
    if (typeof (item) == "string")
      item = {
        value : item,
        caption : ""
      };
    html += "<div class='menuItem techno" + (item.value == defaultString ? " currentXonomy" : "") + "' onclick='Xonomy.answer(\""
        + Xonomy.xmlEscape(item.value) + "\")'>";
    html += "<span class='punc'>\"</span>";
    html += Xonomy.xmlEscape(item.value);
    html += "<span class='punc'>\"</span>";
    if (item.caption != "")
      html += " <span class='explainer'>" + Xonomy.xmlEscape(Xonomy.textByLang(item.caption)) + "</span>";
    html += "</div>";
  }
  html += "</div>";
  return html;
};

Xonomy.attributeMenu = function(htmlID) {
  Xonomy.lastSelectedHtmlId = htmlID;
  var name = $("#" + htmlID).attr("data-name"); // obtain attribute's name
  var elName = $("#" + htmlID).closest(".element").attr("data-name"); // obtain element's name
  Xonomy.verifyDocSpecAttribute(elName, name);
  var spec = Xonomy.docSpec.elements[elName].attributes[name];
  var html = "";
  for (var i = 0; i < spec.menu.length; i++) {
    var item = spec.menu[i];
    var includeIt = !item.hideIf(Xonomy.harvestAttribute(document.getElementById(htmlID)));
    if (includeIt) {
      html += "<div class='menuItem' onclick='Xonomy.callMenuFunction(Xonomy.docSpec.elements[\"" + elName + "\"].attributes[\"" + name + "\"].menu["
          + i + "], \"" + htmlID + "\")'>";
      html += Xonomy.formatCaption(Xonomy.textByLang(item.caption));
      html += "</div>";
    }
  }
  if (html != "")
    html = "<div class='menu'>" + html + "</div>";
  return html;
};

Xonomy.elementMenu = function(htmlID) {
  // reset the selected menu item
  selectedMenuItem = 0;
  Xonomy.lastSelectedHtmlId = htmlID;
  var elName = $("#" + htmlID).attr("data-name"); // obtain element's name
  var spec = Xonomy.docSpec.elements[elName];
  var html = "";
  for (var i = 0; i < spec.menu.length; i++) {
    var item = spec.menu[i];
    var includeIt = !item.hideIf(Xonomy.harvestElement(document.getElementById(htmlID)));
    if (includeIt) {
      html += "<div class='menuItem' onclick='Xonomy.callMenuFunction(Xonomy.docSpec.elements[\"" + elName + "\"].menu[" + i + "], \"" + htmlID
          + "\")'>";
      html += Xonomy.formatCaption(Xonomy.textByLang(item.caption));
      html += "</div>";
    }
  }
  if (html != "") 
    html = "<div class='menu'><input class='form-control' id='js-filterMenu' type='text' placeholder='Filter...' onfocus='filterMenu()' onkeyup='filterMenu(event)' autofocus='autofocus'><div id='menuElements'>" + html + "</div></div>";
  return html;
};


/**
 * store the selected menu item globally 
 */
var selectedMenuItem = 0;

/*
 *  Filters a menu
 * @param {event} evt
 */
function filterMenu(evt) {

  // get the visible elements
  var visibleElements = $('#menuElements .menuItem:visible');

  if(evt) {
    // get the key code
    var code = evt.charCode || evt.keyCode;

    /*
    * if the pressed key is "esc", clear the input field and
    * reset the visibility
    */
    if(code == 27) { // esc
      /*
       * reset everything if the value is not empty
       * else close the bubble
       */
      if($("#js-filterMenu").val()) {
        $("#js-filterMenu").val('');
        $("#menuElements .menuItem").filter(function() {
          $(this).show();
          $(this).css('background-color', '');
        });
        selectedMenuItem = 0;
      }
      else {
        $('#xonomyBubble').hide();
      }
    }
    else if(code == 13) { // enter
      $(visibleElements[selectedMenuItem]).click();
      // workaround
      $('#editor').click();
    }
    else if(code == 38) { // up
      if(selectedMenuItem > 0) {
        selectedMenuItem--;
      }
      else {
        selectedMenuItem = 0;
      }
      $(visibleElements[selectedMenuItem+1]).css('background-color', '');
    }
    else if(code == 40) { // down
      if(selectedMenuItem < visibleElements.length-1) {
        selectedMenuItem++;
      }
      else {
        selectedMenuItem = visibleElements.length-1;
      }
      $(visibleElements[selectedMenuItem-1]).css('background-color', '');
    }
    else {
      // get the value from the input field
      var value = $('#js-filterMenu').val().toLowerCase();
      $("#menuElements .menuItem").filter(function() {
        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
        $(this).css('background-color', '');
      });
      selectedMenuItem = 0;
    }
  } 
  else { // if there is not event -> focus the first element
    selectedMenuItem = 0;
  }
  $(visibleElements[selectedMenuItem]).css('background-color', '#e7f2fa');
}

Xonomy.inlineMenu = function(htmlID) {
  var elName = $("#" + htmlID).attr("data-name"); // obtain element's name
  var spec = Xonomy.docSpec.elements[elName];
  var html = "";
  for (var i = 0; i < spec.inlineMenu.length; i++) {
    var item = spec.inlineMenu[i];
    var includeIt = !item.hideIf(Xonomy.harvestElement(document.getElementById(htmlID)));
    if (includeIt) {
      html += "<div class='menuItem' onclick='Xonomy.callMenuFunction(Xonomy.docSpec.elements[\"" + elName + "\"].inlineMenu[" + i + "], \"" + htmlID
          + "\")'>";
      html += Xonomy.formatCaption(Xonomy.textByLang(item.caption));
      html += "</div>";
    }
  }
  if (html != "")
    html = "<div class='menu'>" + html + "</div>";
  return html;
};

Xonomy.callMenuFunction = function(menuItem, htmlID) {
  menuItem.action(htmlID, menuItem.actionParameter);
  if (menuItem.action != Xonomy.showModifierParameterMenu && menuItem.action != Xonomy.showEventParameterMenu)
    Xonomy.changed();
};

Xonomy.formatCaption = function(caption) {
  caption = caption.replace(/\<([^\>]+)\>/g,
    "<span class='techno'><span class='punc'>&lt;</span><span class='elName'>$1</span><span class='punc'>&gt;</span></span>");
  caption = caption.replace(/\@"([^\"]+)"/g,
    "<span class='techno'><span class='punc'>\"</span><span class='atValue'>$1</span><span class='punc'>\"</span></span>");
  caption = caption.replace(/\@([^ =]+)=""/g,
    "<span class='techno'><span class='atName'>$1</span><span class='punc'>=\"</span><span class='punc'>\"</span></span>");
  caption = caption
    .replace(/\@([^ =]+)="([^\"]+)"/g,
      "<span class='techno'><span class='atName'>$1</span><span class='punc'>=\"</span><span class='atValue'>$2</span><span class='punc'>\"</span></span>");
  caption = caption.replace(/\@([^ =]+)/g, "<span class='techno'><span class='atName'>$1</span></span>");
  return caption;
};

Xonomy.deleteAttribute = function(htmlID, parameter) {
  Xonomy.clickoff();
  var obj = document.getElementById(htmlID);
  obj.parentNode.removeChild(obj);
  Xonomy.refresh();
};

Xonomy.deleteElement = function(htmlID, parameter) {
  Xonomy.clickoff();
  var obj = document.getElementById(htmlID);
  $(obj).slideUp(function() {
    // var parent = obj.parentNode;
    // if (obj.parentNode != null) {
    // obj.parentNode.removeChild(obj);
    // }
    $(obj).remove();
    Xonomy.refresh();
    Xonomy.validate();
    Xonomy.docSpec.onchange(parent);
    Xonomy.addUndoItem();
  });
};

Xonomy.newAttribute = function(htmlID, parameter) {
  Xonomy.clickoff();
  var $element = $("#" + htmlID);
  if (parameter.value == "getValueChangedUUID()")
    parameter.value = getValueChangedUUID();
  var html = Xonomy.renderAttribute({
    type: "attribute",
    name: parameter.name,
    value: parameter.value
  }, $element.data("name"));
  $("#" + htmlID + " > .tag.opening > .attributes").append(html);
  Xonomy.refresh();
};

Xonomy.newElementChild = function(htmlID, parameter, changed) {
  Xonomy.clickoff();
  var html = Xonomy.renderElement(Xonomy.xml2js(parameter));
  var $html = $(html).hide();
  $("#" + htmlID + " > .children").append($html);
  Xonomy.plusminus(htmlID, true);
  Xonomy.refresh();
  $html.slideDown();
  if (changed) {
    Xonomy.changed();
  }
};


	
Xonomy.newElementBefore = function(htmlID, parameter) {
  Xonomy.clickoff();
  var html = Xonomy.renderElement(Xonomy.xml2js(parameter));
  var $html = $(html).hide();
  $("#" + htmlID).before($html);
  Xonomy.refresh();
  $html.slideDown();
};

Xonomy.newElementAfter = function(htmlID, parameter) {
  Xonomy.clickoff();
  var html = Xonomy.renderElement(Xonomy.xml2js(parameter));
  var $html = $(html).hide();
  $("#" + htmlID).after($html);
  Xonomy.refresh();
  $html.slideDown();
};

Xonomy.replace = function(htmlID, jsNode) {
  Xonomy.clickoff();
  var html = "";
  if (jsNode.type == "element")
    html = Xonomy.renderElement(jsNode);
  if (jsNode.type == "attribute")
    html = Xonomy.renderAttribute(jsNode);
  if (jsNode.type == "text")
    html = Xonomy.renderText(jsNode);
  $("#" + htmlID).replaceWith(html);
  Xonomy.refresh();
};

Xonomy.drag = function(ev) { // called when dragging starts
  Xonomy.clickoff();
  var htmlID = ev.target.parentNode.parentNode.id;
  var $element = $("#" + htmlID);
  var elementName = $element.attr("data-name");
  var elSpec = Xonomy.docSpec.elements[elementName];
  $element.addClass("dragging");
  $(".xonomy .children")
    .append(
      "<div class='elementDropper' ondragover='Xonomy.dragOver(event)' ondragleave='Xonomy.dragOut(event)' ondrop='Xonomy.drop(event)'><div class='inside'></div></div>")
  $(".xonomy .children .element")
    .before(
      "<div class='elementDropper' ondragover='Xonomy.dragOver(event)' ondragleave='Xonomy.dragOut(event)' ondrop='Xonomy.drop(event)'><div class='inside'></div></div>")
  $(".xonomy .children .text")
    .before(
      "<div class='elementDropper' ondragover='Xonomy.dragOver(event)' ondragleave='Xonomy.dragOut(event)' ondrop='Xonomy.drop(event)'><div class='inside'></div></div>")
  $(".xonomy .dragging .elementDropper").remove(); // remove drop targets inside the element being dragged
  $(".xonomy .dragging").prev(".elementDropper").remove(); // remove drop targets from immediately before the element being dragged
  $(".xonomy .dragging").next(".elementDropper").remove(); // remove drop targets from immediately after the element being dragged
  if (elSpec.canDropTo) { // remove the drop target from elements it cannot be dropped into
    var droppers = $(".xonomy .elementDropper").toArray();
    for (var i = 0; i < droppers.length; i++) {
      var dropper = droppers[i];
      var parentElementName = $(dropper.parentNode.parentNode).toArray()[0].getAttribute("data-name");
      if ($.inArray(parentElementName, elSpec.canDropTo) < 0) {
        dropper.parentNode.removeChild(dropper);
      }
    }
  }
  if (elSpec.mustBeBefore) { // remove the drop target from after elements it cannot be after
    var droppers = $(".xonomy .elementDropper").toArray();
    for (var i = 0; i < droppers.length; i++) {
      var dropper = droppers[i];
      for (var ii = 0; ii < elSpec.mustBeBefore.length; ii++) {
        if ($(dropper).prevAll("*[data-name='" + elSpec.mustBeBefore[ii] + "']").toArray().length > 0) {
          dropper.parentNode.removeChild(dropper);
        }
      }
    }
  }
  if (elSpec.mustBeAfter) { // remove the drop target from before elements it cannot be before
    var droppers = $(".xonomy .elementDropper").toArray();
    for (var i = 0; i < droppers.length; i++) {
      var dropper = droppers[i];
      for (var ii = 0; ii < elSpec.mustBeAfter.length; ii++) {
        if ($(dropper).nextAll("*[data-name='" + elSpec.mustBeAfter[ii] + "']").toArray().length > 0) {
          dropper.parentNode.removeChild(dropper);
        }
      }
    }
  }
  Xonomy.draggingID = htmlID;
  ev.dataTransfer.setData("text", htmlID);
};

Xonomy.dragOver = function(ev) {
  ev.preventDefault();
  $(ev.target.parentNode).addClass("activeDropper");
};

Xonomy.dragOut = function(ev) {
  ev.preventDefault();
  $(".xonomy .activeDropper").removeClass("activeDropper");
};

Xonomy.drop = function(ev) {
  ev.preventDefault();
  var node = document.getElementById(Xonomy.draggingID); // the thing we are moving
  $(ev.target.parentNode).replaceWith(node);
  Xonomy.changed();
};

Xonomy.dragend = function(ev) {
  $(".xonomy .attributeDropper").remove();
  $(".xonomy .elementDropper").remove();
  $(".xonomy .dragging").removeClass("dragging");
  Xonomy.refresh();
};

Xonomy.changed = function(jsElement) { // called when the document changes
  Xonomy.validate();
  Xonomy.docSpec.onchange(jsElement); // report that the document has changed
  Xonomy.setDirty(true);
  Xonomy.addUndoItem();
};

Xonomy.validate = function() {
  var js = Xonomy.harvestElement($(".xonomy .element").toArray()[0], null);
  $(".xonomy .found-policy-error").removeClass("found-policy-error");
  $(".xonomy .found-policy-warning").removeClass("found-policy-warning");
  Xonomy.errors = [];
  Xonomy.warnings = [];
  Xonomy.docSpec.validate(js); // validate the document
  for (var iError = 0; iError < Xonomy.errors.length; iError++) {
    var error = Xonomy.errors[iError];
    $("#" + error.htmlID).addClass("found-policy-error");
  }
  for (var iWarning = 0; iWarning < Xonomy.warnings.length; iWarning++) {
    var warning = Xonomy.warnings[iWarning];
    $("#" + warning.htmlID).addClass("found-policy-warning");
  }
};

Xonomy.textByLang = function(str) {
  // str = eg. "en: Delete | de: Löschen | fr: Supprimer"
  var ret = str;
  var segs = str.split("|");
  for (var i = 0; i < segs.length; i++) {
    var seg = $.trim(segs[i]);
    if (seg.indexOf(Xonomy.lang + ":") == 0) {
      ret = seg.substring((Xonomy.lang + ":").length, ret.length);
    }
  }
  ret = $.trim(ret);
  return ret;
};
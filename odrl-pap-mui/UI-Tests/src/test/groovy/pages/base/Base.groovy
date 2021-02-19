package pages.base
import geb.Page

class Base extends Page{
    static content = {
        //dataUri {$("input", name:"target")}
        //dataConsumer {$("div", id:"mui-component-select-consumer")}
        saveButton {$("button", id: "Save")}
        transferButton {$("button", id: "Transfer")}
        addComponentButton {$("button", id: "Add Component")}
    }
    def void clickOnSave() {
        saveButton.click()
    }

    def void setInputField(String field, String str) {
        $("input", name:field).value(str)
    }

    def void setCalenderField(String field, String str) {
        $("input", name:field).click() << str
    }

    def void chooseSelectedField(String field, String str) {
        $("div", id:field).click()
        $("ul", role:"listbox").find("li").find{it.text() == str}.click()
    }

    def Boolean checkPolicy(String str) {
        return $(class:"react-codemirror2", 0).text().contains(str)
    }

    def String printPolicy( ) {
        print($(class:"react-codemirror2", 0).text())
    }

    def void addComponent(String str) {
        addComponentButton.click()
        $("ul", role:"menu").find("li").find{it.text() == str}.click()
    }
}

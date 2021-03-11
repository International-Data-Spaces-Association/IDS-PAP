package pages.base
import geb.Page

class Base extends Page{
    static at = {driver.getCurrentUrl() == url}

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

    def void setSelectInput(String field, String str) {
        $("div", id:field).click()
        $("ul", role:"listbox").find("li").find{it.text() == str}.click()
        sleep(500)
    }

    def Boolean checkPolicy(String str) {
        return $(class:"react-codemirror2", 0).text().contains(str)
    }

    def void printPolicy( ) {
        print($(class:"react-codemirror2", 0).text())
    }

    def void addComponent(String str) {
        addComponentButton.click()
        $("ul", role:"menu").find("li").find{it.text() == str}.click()
        sleep(300)
    }

    def initializePolicyAgreement(String policyType, String uri, String provider, String consumer){
        setSelectInput("mui-component-select-policyType", policyType)
        setInputField("target", uri)
        setInputField("provider", provider)
        setSelectInput("mui-component-select-consumer", consumer)
    }

    def initializePolicyOffer(String policyType, String uri, String provider){
        setSelectInput("mui-component-select-policyType", policyType)
        setInputField("target", uri)
        setInputField("provider", provider)
    }

    def initializePolicyRequest(String policyType, String uri, String consumer){
        setSelectInput("mui-component-select-policyType", policyType)
        setInputField("target", uri)
        setSelectInput("mui-component-select-consumer", consumer)
    }
}

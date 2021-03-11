package pages.base

class InformParty extends Base {
    static url = "http://localhost:3000/policy/InformPolicyForm"


    def addInformedParty( String notificationLevel, String informedParty){
        setSelectInput("mui-component-select-notificationLevel", notificationLevel)
        setInputField("informedParty", informedParty)
    }
}

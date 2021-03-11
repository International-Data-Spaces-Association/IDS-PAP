package pages.base

class AnonymizeInTransit extends  Base{
    static url = "http://localhost:3000/policy/AnonymizeInTransitPolicyForm"

    def initializePolicyDelete( String modificationMethod, String fieldToModify){
        setSelectInput("mui-component-select-modificator", modificationMethod )
        setInputField("fieldToChange", fieldToModify)
    }

    def initializePolicyReplace( String modificationMethod, String valueToReplace, String fieldToModify){
        setSelectInput("mui-component-select-modificator", modificationMethod )
        setInputField("valueToChange", valueToReplace)
        setInputField("fieldToChange", fieldToModify)
    }
}

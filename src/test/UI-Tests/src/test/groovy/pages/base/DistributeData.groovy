package pages.base

class DistributeData extends Base {
    static url = "http://localhost:3000/policy/DistributeData"

    def addPolicyToBeSent(String str){
        setInputField("policy", str)
    }
    def addArtifactState(String str) {
        setSelectInput("mui-component-select-artifactState",str)
    }
}

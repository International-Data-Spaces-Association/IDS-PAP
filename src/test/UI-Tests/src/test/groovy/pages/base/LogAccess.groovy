package pages.base

class LogAccess extends Base {
    static url = "http://localhost:3000/policy/LogAccessPolicyForm"

    def addSystem(String logLevel, String SystemDevice){
        setInputField("systemDevice", SystemDevice)
        setSelectInput("mui-component-select-logLevel", logLevel)
    }
}

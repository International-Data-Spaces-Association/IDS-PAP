package pages.base

import pages.base.Base


class CountAccess extends Base {
    static url = "http://localhost:3000/policy/CountAccessPolicyForm"

    def addCounterValue(String count){
        setInputField("counter", count)
    }
}

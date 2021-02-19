package pages

import geb.Page
import pages.base.Base
import pages.base.CorrectPolicies

class ProvideAccess extends Base {

    static url = "http://localhost:3000/policy/ProvideAccessPolicyForm"

    static at = { title == "Provide Access" }

    static content = {

    }



}
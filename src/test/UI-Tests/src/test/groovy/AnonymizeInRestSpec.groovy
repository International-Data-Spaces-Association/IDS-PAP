import geb.spock.GebReportingSpec
import pages.base.AnonymizeInRest

class AnonymizeInRestSpec extends GebReportingSpec{

    def cleanupSpec() {
    }

    def setup() {
    }

    def "Provide Access Agreement"() {
        given: "You are on the provide access page"
        to AnonymizeInRest
        report("Provide Access Page")

        AnonymizeInRest anonymizeInRest = at AnonymizeInRest
        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        anonymizeInRest.initializePolicyAgreement(policyType, uri, provider, consumer)
        report("Enter some test values")

        when: "You press save"
        anonymizeInRest.clickOnSave()

        then: "Check if the generated policy is correct"
        anonymizeInRest.checkPolicy(policyType)
        anonymizeInRest.checkPolicy(uri)
        anonymizeInRest.checkPolicy("http://example.com/party/my-party")
        anonymizeInRest.checkPolicy("http://example.com/party/consumer-party")
    }

    def "Provide Access Offer"() {
        given: "You are on the provide access page"
        to AnonymizeInRest
        report("Provide Access Page")

        AnonymizeInRest anonymizeInRest = at AnonymizeInRest
        String policyType = "Offer"
        String uri = "http://example.com"
        String provider = "My party"
        anonymizeInRest.initializePolicyOffer(policyType, uri, provider)
        report("Enter some test values")

        when: "You press save"
        anonymizeInRest.clickOnSave()

        then: "Check if the generated policy is correct"
        anonymizeInRest.checkPolicy(policyType)
        anonymizeInRest.checkPolicy(uri)
        anonymizeInRest.checkPolicy("http://example.com/party/my-party")
    }

    def "Provide Access Request"() {
        given: "You are on the provide access page"
        to AnonymizeInRest
        report("Provide Access Page")

        AnonymizeInRest anonymizeInRest = at AnonymizeInRest
        String policyType = "Request"
        String uri = "http://example.com"
        String consumer = "Consumer Party"
        anonymizeInRest.initializePolicyRequest(policyType, uri, consumer)
        report("Enter some test values")

        when: "You press save"
        anonymizeInRest.clickOnSave()

        then: "Check if the generated policy is correct"
        anonymizeInRest.checkPolicy(policyType)
        anonymizeInRest.checkPolicy(uri)
        anonymizeInRest.checkPolicy("http://example.com/party/consumer-party")
    }


}

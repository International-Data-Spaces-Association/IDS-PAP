import geb.spock.GebReportingSpec
import pages.base.CountAccess

class CountAccessSpec extends GebReportingSpec{

    def "Count Access Agreement with count value"() {
        given: "You are on the count access page"
        to CountAccess
        report("Count Access Page")

        CountAccess countAccess = at CountAccess
        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        countAccess.initializePolicyAgreement(policyType, uri, provider, consumer)

        String count = "1234"
        countAccess.addCounterValue(count)

        report("Enter some test values")

        when: "You press save"
        countAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        countAccess.checkPolicy(policyType)
        countAccess.checkPolicy(uri)
        countAccess.checkPolicy("http://example.com/party/my-party")
        countAccess.checkPolicy("http://example.com/party/consumer-party")
        countAccess.checkPolicy(count)
    }

    def "Count Access Offer with count value"() {
        given: "You are on the count access page"
        to CountAccess
        report("Count Access Page")

        CountAccess countAccess = at CountAccess
        String policyType = "Offer"
        String uri = "http://example.com"
        String provider = "My party"
        countAccess.initializePolicyOffer(policyType, uri, provider)

        String count = "1234"
        countAccess.addCounterValue(count)

        report("Enter some test values")

        when: "You press save"
        countAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        countAccess.checkPolicy(policyType)
        countAccess.checkPolicy(uri)
        countAccess.checkPolicy("http://example.com/party/my-party")
        countAccess.checkPolicy(count)
    }

    def "Count Access Request with count value"() {
        given: "You are on the count access page"
        to CountAccess
        report("Count Access Page")

        CountAccess countAccess = at CountAccess
        String policyType = "Request"
        String uri = "http://example.com"
        String consumer = "Consumer Party"
        countAccess.initializePolicyRequest(policyType, uri, consumer)

        String count = "1234"
        countAccess.addCounterValue(count)

        report("Enter some test values")

        when: "You press save"
        countAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        countAccess.checkPolicy(policyType)
        countAccess.checkPolicy(uri)
        countAccess.checkPolicy("http://example.com/party/consumer-party")
        countAccess.checkPolicy(count)
    }
}

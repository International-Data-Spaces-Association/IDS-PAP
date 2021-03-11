import geb.spock.GebReportingSpec
import pages.base.InformParty

class InformPartySpec extends GebReportingSpec{


    def cleanupSpec() {
    }


    def "Inform Party Agreement with notification and informed party values"() {
        given: "You are on the count access page"
        to InformParty
        report("Inform Party Page")

        InformParty informParty = at InformParty
        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        informParty.initializePolicyAgreement(policyType, uri, provider, consumer)

        String informedParty = "My Party"
        String notificationLevel = "OFF"
        informParty.addInformedParty(notificationLevel, informedParty)

        report("Enter some test values")

        when: "You press save"
        informParty.clickOnSave()

        then: "Check if the generated policy is correct"
        informParty.checkPolicy(policyType)
        informParty.checkPolicy(uri)
        informParty.checkPolicy("http://example.com/party/my-party")
        informParty.checkPolicy("http://example.com/party/consumer-party")
        informParty.checkPolicy(notificationLevel)
        informParty.checkPolicy(informedParty)
    }

    def "Inform Party Offer with notification and informed party values"() {
        given: "You are on the count access page"
        to InformParty
        report("Inform Party Page")

        InformParty informParty = at InformParty
        String policyType = "Offer"
        String uri = "http://example.com"
        String provider = "My party"
        informParty.initializePolicyOffer(policyType, uri, provider)

        String informedParty = "My Party"
        String notificationLevel = "OFF"
        informParty.addInformedParty(notificationLevel, informedParty)

        report("Enter some test values")

        when: "You press save"
        informParty.clickOnSave()

        then: "Check if the generated policy is correct"
        informParty.checkPolicy(policyType)
        informParty.checkPolicy(uri)
        informParty.checkPolicy("http://example.com/party/my-party")
        informParty.checkPolicy(notificationLevel)
        informParty.checkPolicy(informedParty)
    }

    def "Inform Party Request with notification and informed party values"() {
        given: "You are on the count access page"
        to InformParty
        report("Inform Party Page")

        InformParty informParty = at InformParty
        String policyType = "Request"
        String uri = "http://example.com"
        String consumer = "Consumer Party"
        informParty.initializePolicyRequest(policyType, uri, consumer)

        String informedParty = "My Party"
        String notificationLevel = "OFF"
        informParty.addInformedParty(notificationLevel, informedParty)

        report("Enter some test values")

        when: "You press save"
        informParty.clickOnSave()

        then: "Check if the generated policy is correct"
        informParty.checkPolicy(policyType)
        informParty.checkPolicy(uri)
        informParty.checkPolicy("http://example.com/party/consumer-party")
        informParty.checkPolicy(notificationLevel)
        informParty.checkPolicy(informedParty)
    }
}

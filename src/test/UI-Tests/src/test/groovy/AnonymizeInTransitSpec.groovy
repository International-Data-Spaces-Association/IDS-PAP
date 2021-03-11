import geb.spock.GebReportingSpec
import pages.base.AnonymizeInTransit

class AnonymizeInTransitSpec extends GebReportingSpec{

    def cleanupSpec() {
    }

    def setup() {
    }

    def "Anonymize in Transit Agreement with Delete Modification Method"() {
        given: "You are on the provide access page"
        to AnonymizeInTransit
        report("Anonymize in Transit")

        AnonymizeInTransit anonymizeInTransit = at AnonymizeInTransit
        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        anonymizeInTransit.initializePolicyAgreement(policyType, uri, provider, consumer)

        String modificationMethod = "Delete modification method"
        String fieldToModify = "Testfield"
        anonymizeInTransit.initializePolicyDelete(modificationMethod, fieldToModify)

        report("Enter some test values")

        when: "You press save"
        anonymizeInTransit.clickOnSave()

        then: "Check if the generated policy is correct"
        anonymizeInTransit.checkPolicy(policyType)
        anonymizeInTransit.checkPolicy(uri)
        anonymizeInTransit.checkPolicy("http://example.com/party/my-party")
        anonymizeInTransit.checkPolicy("http://example.com/party/consumer-party")
        anonymizeInTransit.checkPolicy(fieldToModify)
        anonymizeInTransit.checkPolicy("http://example.com/anonymize/delete")
    }

    def "Anonymize in Transit Agreement with Replace Modification Method"() {
        given: "You are on the provide access page"
        to AnonymizeInTransit
        report("Anonymize in Transit")

        AnonymizeInTransit anonymizeInTransit = at AnonymizeInTransit

        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        anonymizeInTransit.initializePolicyAgreement(policyType, uri, provider, consumer)

        String modificationMethod = "Replace modification method"
        String valueToReplace = "ValueToReplace"
        String fieldToModify = "Testfield"
        anonymizeInTransit.initializePolicyReplace(modificationMethod, valueToReplace, fieldToModify)

        report("Enter some test values")

        when: "You press save"
        anonymizeInTransit.clickOnSave()

        then: "Check if the generated policy is correct"
        anonymizeInTransit.checkPolicy(policyType)
        anonymizeInTransit.checkPolicy(uri)
        anonymizeInTransit.checkPolicy("http://example.com/party/my-party")
        anonymizeInTransit.checkPolicy("http://example.com/party/consumer-party")
        anonymizeInTransit.checkPolicy(fieldToModify)
        anonymizeInTransit.checkPolicy("http://example.com/anonymize/replace")
        anonymizeInTransit.checkPolicy(valueToReplace)
    }

    def "Anonymize in Transit Offer with Delete Modification Method"() {
        given: "You are on the provide access page"
        to AnonymizeInTransit
        report("Anonymize in Transit")

        AnonymizeInTransit anonymizeInTransit = at AnonymizeInTransit
        String policyType = "Offer"
        String uri = "http://example.com"
        String provider = "My party"
        anonymizeInTransit.initializePolicyOffer(policyType, uri, provider)

        String modificationMethod = "Delete modification method"
        String fieldToModify = "Testfield"
        anonymizeInTransit.initializePolicyDelete(modificationMethod, fieldToModify)

        report("Enter some test values")

        when: "You press save"
        anonymizeInTransit.clickOnSave()

        then: "Check if the generated policy is correct"
        anonymizeInTransit.checkPolicy(policyType)
        anonymizeInTransit.checkPolicy(uri)
        anonymizeInTransit.checkPolicy("http://example.com/party/my-party")
        anonymizeInTransit.checkPolicy(fieldToModify)
        anonymizeInTransit.checkPolicy("http://example.com/anonymize/delete")
    }

    def "Anonymize in Transit Offer with Replace Modification Method"() {
        given: "You are on the provide access page"
        to AnonymizeInTransit
        report("Anonymize in Transit")

        AnonymizeInTransit anonymizeInTransit = at AnonymizeInTransit

        String policyType = "Offer"
        String uri = "http://example.com"
        String provider = "My party"
        anonymizeInTransit.initializePolicyOffer(policyType, uri, provider)

        String modificationMethod = "Replace modification method"
        String valueToReplace = "ValueToReplace"
        String fieldToModify = "Testfield"
        anonymizeInTransit.initializePolicyReplace(modificationMethod, valueToReplace, fieldToModify)

        report("Enter some test values")

        when: "You press save"
        anonymizeInTransit.clickOnSave()

        then: "Check if the generated policy is correct"
        anonymizeInTransit.checkPolicy(policyType)
        anonymizeInTransit.checkPolicy(uri)
        anonymizeInTransit.checkPolicy("http://example.com/party/my-party")
        anonymizeInTransit.checkPolicy(fieldToModify)
        anonymizeInTransit.checkPolicy("http://example.com/anonymize/replace")
        anonymizeInTransit.checkPolicy(valueToReplace)
    }

    def "Anonymize in Transit Request with Delete Modification Method"() {
        given: "You are on the provide access page"
        to AnonymizeInTransit
        report("Anonymize in Transit")

        AnonymizeInTransit anonymizeInTransit = at AnonymizeInTransit
        String policyType = "Request"
        String uri = "http://example.com"
        String consumer = "Consumer Party"
        anonymizeInTransit.initializePolicyRequest(policyType, uri, consumer)

        String modificationMethod = "Delete modification method"
        String fieldToModify = "Testfield"
        anonymizeInTransit.initializePolicyDelete(modificationMethod, fieldToModify)

        report("Enter some test values")

        when: "You press save"
        anonymizeInTransit.clickOnSave()

        then: "Check if the generated policy is correct"
        anonymizeInTransit.checkPolicy(policyType)
        anonymizeInTransit.checkPolicy(uri)
        anonymizeInTransit.checkPolicy("http://example.com/party/consumer-party")
        anonymizeInTransit.checkPolicy(fieldToModify)
        anonymizeInTransit.checkPolicy("http://example.com/anonymize/delete")
    }

    def "Anonymize in Transit Request with Replace Modification Method"() {
        given: "You are on the provide access page"
        to AnonymizeInTransit
        report("Anonymize in Transit")

        AnonymizeInTransit anonymizeInTransit = at AnonymizeInTransit

        String policyType = "Request"
        String uri = "http://example.com"
        String consumer = "Consumer Party"
        anonymizeInTransit.initializePolicyRequest(policyType, uri, consumer)

        String modificationMethod = "Replace modification method"
        String valueToReplace = "ValueToReplace"
        String fieldToModify = "Testfield"
        anonymizeInTransit.initializePolicyReplace(modificationMethod, valueToReplace, fieldToModify)

        report("Enter some test values")

        when: "You press save"
        anonymizeInTransit.clickOnSave()

        then: "Check if the generated policy is correct"
        anonymizeInTransit.checkPolicy(policyType)
        anonymizeInTransit.checkPolicy(uri)
        anonymizeInTransit.checkPolicy("http://example.com/party/consumer-party")
        anonymizeInTransit.checkPolicy(fieldToModify)
        anonymizeInTransit.checkPolicy("http://example.com/anonymize/replace")
        anonymizeInTransit.checkPolicy(valueToReplace)
    }
}

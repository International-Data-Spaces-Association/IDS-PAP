import geb.spock.GebReportingSpec
import pages.base.DeleteDataAfter

class DeleteDataAfterSpec extends GebReportingSpec{

    def "Delete Data After Agreement without extra Components"() {
        given: "You are on the page Delete Data After"
        to DeleteDataAfter
        report("Delete Data After Page")

        DeleteDataAfter deleteDataAfter = at DeleteDataAfter
        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        deleteDataAfter.initializePolicyAgreement(policyType, uri, provider, consumer)
        report("Enter some test values")

        when: "You press save"
        deleteDataAfter.clickOnSave()

        then: "Check if the generated policy is correct"
        deleteDataAfter.checkPolicy(policyType)
        deleteDataAfter.checkPolicy(uri)
        deleteDataAfter.checkPolicy("http://example.com/party/my-party")
        deleteDataAfter.checkPolicy("http://example.com/party/consumer-party")
    }

    def "Delete Data After Agreement exact Date and Time"() {
        given: "You are on the page Delete Data After"
        to DeleteDataAfter
        report("Delete Data After Page")

        DeleteDataAfter deleteDataAfter = at DeleteDataAfter
        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        deleteDataAfter.initializePolicyAgreement(policyType, uri, provider, consumer)

        Date date = new Date()
        deleteDataAfter.addExactTimeAndDate(date)

        report("Enter some test values")

        when: "You press save"
        deleteDataAfter.clickOnSave()

        then: "Check if the generated policy is correct"
        deleteDataAfter.checkPolicy(policyType)
        deleteDataAfter.checkPolicy(uri)
        deleteDataAfter.checkPolicy("http://example.com/party/my-party")
        deleteDataAfter.checkPolicy("http://example.com/party/consumer-party")
        deleteDataAfter.checkPolicy(date.format("yyyy-MM-dd")+ "T" + date.format("HH:mm") + "Z")
    }

    def "Delete Data After Agreement some Wait Time"() {
        given: "You are on the page Delete Data After"
        to DeleteDataAfter
        report("Delete Data After Page")

        DeleteDataAfter deleteDataAfter = at DeleteDataAfter
        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        deleteDataAfter.initializePolicyAgreement(policyType, uri, provider, consumer)

        String value = "200"
        String unit = "Hours"
        deleteDataAfter.addWaitBeforeDeleting(value, unit)

        report("Enter some test values")


        when: "You press save"
        deleteDataAfter.clickOnSave()

        then: "Check if the generated policy is correct"
        deleteDataAfter.checkPolicy(policyType)
        deleteDataAfter.checkPolicy(uri)
        deleteDataAfter.checkPolicy("http://example.com/party/my-party")
        deleteDataAfter.checkPolicy("http://example.com/party/consumer-party")
        deleteDataAfter.checkPolicy(value+unit[0])
    }

    def "Delete Data After Offer without extra Components"() {
        given: "You are on the page Delete Data After"
        to DeleteDataAfter
        report("Delete Data After Page")

        DeleteDataAfter deleteDataAfter = at DeleteDataAfter
        String policyType = "Offer"
        String uri = "http://example.com"
        String provider = "My party"
        deleteDataAfter.initializePolicyOffer(policyType, uri, provider)
        report("Enter some test values")

        when: "You press save"
        deleteDataAfter.clickOnSave()

        then: "Check if the generated policy is correct"
        deleteDataAfter.checkPolicy(policyType)
        deleteDataAfter.checkPolicy(uri)
        deleteDataAfter.checkPolicy("http://example.com/party/my-party")
    }

    def "Delete Data After Offer exact Date and Time"() {
        given: "You are on the page Delete Data After"
        to DeleteDataAfter
        report("Delete Data After Page")

        DeleteDataAfter deleteDataAfter = at DeleteDataAfter
        String policyType = "Offer"
        String uri = "http://example.com"
        String provider = "My party"
        deleteDataAfter.initializePolicyOffer(policyType, uri, provider)

        Date date = new Date()
        deleteDataAfter.addExactTimeAndDate(date)

        report("Enter some test values")

        when: "You press save"
        deleteDataAfter.clickOnSave()

        then: "Check if the generated policy is correct"
        deleteDataAfter.checkPolicy(policyType)
        deleteDataAfter.checkPolicy(uri)
        deleteDataAfter.checkPolicy("http://example.com/party/my-party")
        deleteDataAfter.checkPolicy(date.format("yyyy-MM-dd")+ "T" + date.format("HH:mm") + "Z")
    }

    def "Delete Data After Offer some Wait Time"() {
        given: "You are on the page Delete Data After"
        to DeleteDataAfter
        report("Delete Data After Page")

        DeleteDataAfter deleteDataAfter = at DeleteDataAfter
        String policyType = "Offer"
        String uri = "http://example.com"
        String provider = "My party"
        deleteDataAfter.initializePolicyOffer(policyType, uri, provider)

        String value = "200"
        String unit = "Hours"
        deleteDataAfter.addWaitBeforeDeleting(value, unit)

        report("Enter some test values")


        when: "You press save"
        deleteDataAfter.clickOnSave()

        then: "Check if the generated policy is correct"
        deleteDataAfter.checkPolicy(policyType)
        deleteDataAfter.checkPolicy(uri)
        deleteDataAfter.checkPolicy("http://example.com/party/my-party")
        deleteDataAfter.checkPolicy(value+unit[0])
    }

    def "Delete Data After Request without extra Components"() {
        given: "You are on the page Delete Data After"
        to DeleteDataAfter
        report("Delete Data After Page")

        DeleteDataAfter deleteDataAfter = at DeleteDataAfter
        String policyType = "Request"
        String uri = "http://example.com"
        String consumer = "Consumer Party"
        deleteDataAfter.initializePolicyRequest(policyType, uri, consumer)
        report("Enter some test values")

        when: "You press save"
        deleteDataAfter.clickOnSave()

        then: "Check if the generated policy is correct"
        deleteDataAfter.checkPolicy(policyType)
        deleteDataAfter.checkPolicy(uri)
        deleteDataAfter.checkPolicy("http://example.com/party/consumer-party")
    }

    def "Delete Data After Request exact Date and Time"() {
        given: "You are on the page Delete Data After"
        to DeleteDataAfter
        report("Delete Data After Page")

        DeleteDataAfter deleteDataAfter = at DeleteDataAfter
        String policyType = "Request"
        String uri = "http://example.com"
        String consumer = "Consumer Party"
        deleteDataAfter.initializePolicyRequest(policyType, uri, consumer)

        Date date = new Date()
        deleteDataAfter.addExactTimeAndDate(date)

        report("Enter some test values")

        when: "You press save"
        deleteDataAfter.clickOnSave()

        then: "Check if the generated policy is correct"
        deleteDataAfter.checkPolicy(policyType)
        deleteDataAfter.checkPolicy(uri)
        deleteDataAfter.checkPolicy("http://example.com/party/consumer-party")
        deleteDataAfter.checkPolicy(date.format("yyyy-MM-dd")+ "T" + date.format("HH:mm") + "Z")
    }

    def "Delete Data After Request some Wait Time"() {
        given: "You are on the page Delete Data After"
        to DeleteDataAfter
        report("Delete Data After Page")

        DeleteDataAfter deleteDataAfter = at DeleteDataAfter
        String policyType = "Request"
        String uri = "http://example.com"
        String consumer = "Consumer Party"
        deleteDataAfter.initializePolicyRequest(policyType, uri, consumer)

        String value = "200"
        String unit = "Hours"
        deleteDataAfter.addWaitBeforeDeleting(value, unit)

        report("Enter some test values")


        when: "You press save"
        deleteDataAfter.clickOnSave()

        then: "Check if the generated policy is correct"
        deleteDataAfter.checkPolicy(policyType)
        deleteDataAfter.checkPolicy(uri)
        deleteDataAfter.checkPolicy("http://example.com/party/consumer-party")
        deleteDataAfter.checkPolicy(value+unit[0])
    }


}

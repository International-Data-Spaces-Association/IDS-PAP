import geb.spock.GebReportingSpec
import pages.base.CountAccess
import pages.base.DeleteDataAfter
import pages.base.DistributeData

class DistributeDataSpec extends GebReportingSpec{

    def "Distribute Data Agreement with count value"() {
        given: "You are on the distribute data page"
        to DistributeData
        report("Count Access Page")

        DistributeData distributeData = at DistributeData
        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        distributeData.initializePolicyAgreement(policyType, uri, provider, consumer)

        String policyToSent = "http://example.com/policy/third-party-policy"
        String artifactState = "ANONYMIZED"
        distributeData.addArtifactState(artifactState)
        distributeData.addPolicyToBeSent(policyToSent)
        report("Enter some test values")

        when: "You press save"
        distributeData.clickOnSave()

        then: "Check if the generated policy is correct"
        distributeData.checkPolicy(policyType)
        distributeData.checkPolicy(uri)
        distributeData.checkPolicy("http://example.com/party/my-party")
        distributeData.checkPolicy("http://example.com/party/consumer-party")
        distributeData.checkPolicy(policyToSent)
        distributeData.checkPolicy("idsc:"+artifactState)
    }

    def "Distribute Data Offer with count value"() {
        given: "You are on the distribute data page"
        to DistributeData
        report("Count Access Page")

        DistributeData distributeData = at DistributeData
        String policyType = "Offer"
        String uri = "http://example.com"
        String provider = "My party"
        distributeData.initializePolicyOffer(policyType, uri, provider)

        String policyToSent = "http://example.com/policy/third-party-policy"
        String artifactState = "ANONYMIZED"
        distributeData.addArtifactState(artifactState)
        distributeData.addPolicyToBeSent(policyToSent)
        report("Enter some test values")

        when: "You press save"
        distributeData.clickOnSave()

        then: "Check if the generated policy is correct"
        distributeData.checkPolicy(policyType)
        distributeData.checkPolicy(uri)
        distributeData.checkPolicy("http://example.com/party/my-party")
        distributeData.checkPolicy(policyToSent)
        distributeData.checkPolicy("idsc:"+artifactState)
    }

    def "Distribute Data Request with count value"() {
        given: "You are on the distribute data page"
        to DistributeData
        report("Count Access Page")

        DistributeData distributeData = at DistributeData
        String policyType = "Request"
        String uri = "http://example.com"
        String consumer = "Consumer Party"
        distributeData.initializePolicyRequest(policyType, uri, consumer)

        String policyToSent = "http://example.com/policy/third-party-policy"
        String artifactState = "ANONYMIZED"
        distributeData.addArtifactState(artifactState)
        distributeData.addPolicyToBeSent(policyToSent)
        report("Enter some test values")

        when: "You press save"
        distributeData.clickOnSave()

        then: "Check if the generated policy is correct"
        distributeData.checkPolicy(policyType)
        distributeData.checkPolicy(uri)
        distributeData.checkPolicy("http://example.com/party/consumer-party")
        distributeData.checkPolicy(policyToSent)
        distributeData.checkPolicy("idsc:"+artifactState)
    }
}

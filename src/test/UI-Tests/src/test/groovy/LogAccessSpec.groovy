import geb.spock.GebReportingSpec
import pages.base.LogAccess

class LogAccessSpec extends GebReportingSpec{

    def "Log Access Agreement with log level and system device values"() {
        given: "You are on the count access page"
        to LogAccess
        report("Log Access Page")

        LogAccess logAccess = at LogAccess
        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        logAccess.initializePolicyAgreement(policyType, uri, provider, consumer)

        String logLevel = "OFF"
        String systemDevice = "http://example.com/system"
        logAccess.addSystem(logLevel, systemDevice)

        report("Enter some test values")

        when: "You press save"
        logAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        logAccess.checkPolicy(policyType)
        logAccess.checkPolicy(uri)
        logAccess.checkPolicy("http://example.com/party/my-party")
        logAccess.checkPolicy("http://example.com/party/consumer-party")
        logAccess.checkPolicy(logLevel)
        logAccess.checkPolicy(systemDevice)
    }
    def "Log Access Offer with log level and system device values"() {
        given: "You are on the count access page"
        to LogAccess
        report("Log Access Page")

        LogAccess logAccess = at LogAccess
        String policyType = "Offer"
        String uri = "http://example.com"
        String provider = "My party"
        logAccess.initializePolicyOffer(policyType, uri, provider)

        String logLevel = "OFF"
        String systemDevice = "http://example.com/system"
        logAccess.addSystem(logLevel, systemDevice)

        report("Enter some test values")

        when: "You press save"
        logAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        logAccess.checkPolicy(policyType)
        logAccess.checkPolicy(uri)
        logAccess.checkPolicy("http://example.com/party/my-party")
        logAccess.checkPolicy(logLevel)
        logAccess.checkPolicy(systemDevice)
    }
    def "Log Access Request with log level and system device values"() {
        given: "You are on the count access page"
        to LogAccess
        report("Log Access Page")

        LogAccess logAccess = at LogAccess
        String policyType = "Request"
        String uri = "http://example.com"
        String consumer = "Consumer Party"
        logAccess.initializePolicyRequest(policyType, uri, consumer)

        String logLevel = "OFF"
        String systemDevice = "http://example.com/system"
        logAccess.addSystem(logLevel, systemDevice)

        report("Enter some test values")

        when: "You press save"
        logAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        logAccess.checkPolicy(policyType)
        logAccess.checkPolicy(uri)
        logAccess.checkPolicy("http://example.com/party/consumer-party")
        logAccess.checkPolicy(logLevel)
        logAccess.checkPolicy(systemDevice)
    }

}

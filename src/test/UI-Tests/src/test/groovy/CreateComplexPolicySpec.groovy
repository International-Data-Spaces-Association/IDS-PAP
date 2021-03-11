import geb.spock.GebReportingSpec
import pages.base.CreateComplexPolicy

class CreateComplexPolicySpec extends GebReportingSpec {

    def "Create Complex Policy Agreement with all Components"() {
        given: "You are on the count access page"
        to CreateComplexPolicy
        report("Count Access Page")

        CreateComplexPolicy createComplexPolicy = at CreateComplexPolicy
        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        createComplexPolicy.initializePolicyAgreement(policyType, uri, provider, consumer)

        String location = "http://ontologie.es/place/DE"
        String system = "http://example.com/ids-app/risk-management-system"
        String purpose = "Marketing"
        String event = "http://example.com/ids-event:exhibition"
        Date startDate = new Date()
        Calendar c = Calendar.getInstance()
        c.setTime(startDate)
        c.add(Calendar.HOUR, 1)
        Date endDate =  c.getTime()
        String payment = "Rent"
        String price = "999"
        Date beginTime = new Date()
        String numberOfUsage = "234"
        String durationValue = "1234"
        String unit = "Hours"

        createComplexPolicy.setLocation(location)
        createComplexPolicy.selectSystem(system)
        createComplexPolicy.selectPurpose(purpose)
        createComplexPolicy.selectEvent(event)
        createComplexPolicy.setTimeInterval(startDate, endDate)
        createComplexPolicy.setPayment(price, payment)
        createComplexPolicy.setTimeDuration(beginTime)
        createComplexPolicy.setNumberOfUsage(numberOfUsage)
        createComplexPolicy.setTimeDuration(durationValue, unit)
        report("Enter some test values")

        when: "You press save"
        createComplexPolicy.clickOnSave()

        then: "Check if the generated policy is correct"
        createComplexPolicy.checkPolicy("ContractAgreement")
        createComplexPolicy.checkPolicy(uri)
        createComplexPolicy.checkPolicy("http://example.com/party/my-party")
        createComplexPolicy.checkPolicy("http://example.com/party/consumer-party")

        createComplexPolicy.checkPolicy(location)
        createComplexPolicy.checkPolicy(system)
        createComplexPolicy.checkPolicy(purpose)
        createComplexPolicy.checkPolicy(event)
        createComplexPolicy.checkPolicy(price)
        createComplexPolicy.checkPolicy(numberOfUsage)
        createComplexPolicy.checkPolicy(durationValue + unit[0])
    }

    def "Create Complex Policy Offer with all Components"() {
        given: "You are on the count access page"
        to CreateComplexPolicy
        report("Count Access Page")

        CreateComplexPolicy createComplexPolicy = at CreateComplexPolicy
        String policyType = "Offer"
        String uri = "http://example.com"
        String provider = "My party"
        createComplexPolicy.initializePolicyOffer(policyType, uri, provider)

        String location = "http://ontologie.es/place/DE"
        String system = "http://example.com/ids-app/risk-management-system"
        String purpose = "Marketing"
        String event = "http://example.com/ids-event:exhibition"
        Date startDate = new Date()
        Calendar c = Calendar.getInstance()
        c.setTime(startDate)
        c.add(Calendar.HOUR, 1)
        Date endDate =  c.getTime()
        String payment = "Rent"
        String price = "999"
        Date beginTime = new Date()
        String numberOfUsage = "234"
        String durationValue = "1234"
        String unit = "Hours"

        createComplexPolicy.setLocation(location)
        createComplexPolicy.selectSystem(system)
        createComplexPolicy.selectPurpose(purpose)
        createComplexPolicy.selectEvent(event)
        createComplexPolicy.setTimeInterval(startDate, endDate)
        createComplexPolicy.setPayment(price, payment)
        createComplexPolicy.setTimeDuration(beginTime)
        createComplexPolicy.setNumberOfUsage(numberOfUsage)
        createComplexPolicy.setTimeDuration(durationValue, unit)
        report("Enter some test values")

        when: "You press save"
        createComplexPolicy.clickOnSave()

        then: "Check if the generated policy is correct"
        createComplexPolicy.checkPolicy("ContractOffer")
        createComplexPolicy.checkPolicy(uri)
        createComplexPolicy.checkPolicy("http://example.com/party/my-party")

        createComplexPolicy.checkPolicy(location)
        createComplexPolicy.checkPolicy(system)
        createComplexPolicy.checkPolicy(purpose)
        createComplexPolicy.checkPolicy(event)
        createComplexPolicy.checkPolicy(price)
        createComplexPolicy.checkPolicy(numberOfUsage)
        createComplexPolicy.checkPolicy(durationValue + unit[0])
    }

    def "Create Complex Policy Request with all Components"() {
        given: "You are on the count access page"
        to CreateComplexPolicy
        report("Count Access Page")

        CreateComplexPolicy createComplexPolicy = at CreateComplexPolicy
        String policyType = "Request"
        String uri = "http://example.com"
        String consumer = "Consumer Party"
        createComplexPolicy.initializePolicyRequest(policyType, uri, consumer)

        String location = "http://ontologie.es/place/DE"
        String system = "http://example.com/ids-app/risk-management-system"
        String purpose = "Marketing"
        String event = "http://example.com/ids-event:exhibition"
        Date startDate = new Date()
        Calendar c = Calendar.getInstance()
        c.setTime(startDate)
        c.add(Calendar.HOUR, 1)
        Date endDate =  c.getTime()
        String payment = "Rent"
        String price = "999"
        Date beginTime = new Date()
        String numberOfUsage = "234"
        String durationValue = "1234"
        String unit = "Hours"

        createComplexPolicy.setLocation(location)
        createComplexPolicy.selectSystem(system)
        createComplexPolicy.selectPurpose(purpose)
        createComplexPolicy.selectEvent(event)
        createComplexPolicy.setTimeInterval(startDate, endDate)
        createComplexPolicy.setPayment(price, payment)
        createComplexPolicy.setTimeDuration(beginTime)
        createComplexPolicy.setNumberOfUsage(numberOfUsage)
        createComplexPolicy.setTimeDuration(durationValue, unit)
        report("Enter some test values")

        when: "You press save"
        createComplexPolicy.clickOnSave()

        then: "Check if the generated policy is correct"
        createComplexPolicy.checkPolicy("ContractRequest")
        createComplexPolicy.checkPolicy(uri)
        createComplexPolicy.checkPolicy("http://example.com/party/consumer-party")

        createComplexPolicy.checkPolicy(location)
        createComplexPolicy.checkPolicy(system)
        createComplexPolicy.checkPolicy(purpose)
        createComplexPolicy.checkPolicy(event)
        createComplexPolicy.checkPolicy(price)
        createComplexPolicy.checkPolicy(numberOfUsage)
        createComplexPolicy.checkPolicy(durationValue + unit[0])
    }
}

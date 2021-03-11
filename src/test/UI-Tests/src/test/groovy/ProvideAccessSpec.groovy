import geb.spock.GebReportingSpec
import pages.ProvideAccess

class ProvideAccessSpec extends GebReportingSpec{

    def cleanupSpec() {
    }

    def setup() {
    }

    def "Provide Access without extra Components"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        String policyType = "Agreement"
        String uri = "http://example.com"
        String provider = "My party"
        String consumer = "Consumer Party"
        provideAccess.initializePolicyAgreement(policyType, uri, provider, consumer)
        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy(policyType)
        provideAccess.checkPolicy(uri)
        provideAccess.checkPolicy("http://example.com/party/my-party")
        provideAccess.checkPolicy("http://example.com/party/consumer-party")
    }

    def "Provide Access with Location Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        String location = "http://ontologie.es/place/DE"

        provideAccess.setLocation(location)

        provideAccess.setSelectInput("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")

        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy(location)
    }

    def "Provide Access with System Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess

        provideAccess.setSelectInput("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")

        String system = "http://example.com/ids-app/risk-management-system"

        provideAccess.selectSystem(system)



        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy(system)
    }

    def "Provide Access with Purpose Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess

        provideAccess.setSelectInput("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")

        String purpose = "Marketing"

        provideAccess.selectPurpose(purpose)

        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy(purpose)
    }

    def "Provide Access with Event Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        provideAccess.setSelectInput("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")
        String event = "http://example.com/ids-event:exhibition"
        provideAccess.selectEvent(event)
        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy(event)
    }

    def "Provide Access with Interval Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        provideAccess.setSelectInput("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")

        Date startDate = new Date()
        Calendar c = Calendar.getInstance()
        c.setTime(startDate)
        c.add(Calendar.HOUR, 1)
        Date endDate =  c.getTime()
        provideAccess.setTimeInterval(startDate, endDate)

        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        String startDateInst = startDate.format("yyyy-MM-dd")+ "T" + startDate.format("HH:mm") + "Z"
        String endDateInst = endDate.format("yyyy-MM-dd")+ "T" + endDate.format("HH:mm") + "Z"
        provideAccess.checkPolicy(startDateInst)
        provideAccess.checkPolicy(endDateInst)

    }

    def "Provide Access with Payment Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        provideAccess.setSelectInput("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")

        String payment = "Rent"
        String price = "999"


        provideAccess.setPayment(price, payment)
        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy(payment)
        provideAccess.checkPolicy(price)
    }

    def "Provide Access with Duration Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        provideAccess.setSelectInput("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")

        Date beginTime = new Date()
        String durationValue = "Rent"
        String unit = "Hours"
        provideAccess.setTimeDuration(beginTime, durationValue, unit)

        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy(durationValue+unit.getAt(0))
    }
}

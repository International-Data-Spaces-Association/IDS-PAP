import geb.spock.GebReportingSpec
import pages.ProvideAccess
import pages.base.CorrectPolicies
import pages.base.MyDataPage
import spock.lang.Shared
import spock.lang.Stepwise

import java.util.concurrent.TimeUnit

@Stepwise
class ProvideAccessSpec extends GebReportingSpec{
    @Shared cfg

    def setupSpec() {
        //cfg = ConfigReader.getConfiguration()
        //MyDataPage.config = cfg
    }

    def cleanupSpec() {
    }

    def setup() {
        //baseUrl = cfg.urlWithForwardSlash
    }

    def "Provide Access without extra Components"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        String consumer = "Consumer Party"
        String target = "http://example.com"
        provideAccess.chooseSelectedField("mui-component-select-consumer", consumer)
        provideAccess.setInputField("target", target)
        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy('"ids:consumer": "http://example.com/party/consumer-party",')
        provideAccess.checkPolicy('"@id":"'+target+'"')
        at ProvideAccess

    }

    def "Provide Access with Location Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        String location = "http://ontologie.es/place/DE"
        provideAccess.chooseSelectedField("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")
        provideAccess.addComponent("Location")
        provideAccess.setInputField("location", location)
        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy('"@value": "'+location+'",')
        at ProvideAccess

    }

    def "Provide Access with System Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        String system = "http://example.com/ids-app/risk-management-system"
        provideAccess.chooseSelectedField("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")
        provideAccess.addComponent("System")
        provideAccess.chooseSelectedField("mui-component-select-system", system)
        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy('"ids:rightOperand": { "@value": "'+system+'",')

        at ProvideAccess

    }
    def "Provide Access with Purpose Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        String purpose = "Marketing"
        provideAccess.chooseSelectedField("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")
        provideAccess.addComponent("Purpose")
        provideAccess.chooseSelectedField("mui-component-select-purpose", purpose)
        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy('"ids:rightOperand": { "@value": "http://example.com/ids-purpose:'+purpose+'",')
        at ProvideAccess

    }

    def "Provide Access with Event Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        provideAccess.chooseSelectedField("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")
        provideAccess.addComponent("Event")
        provideAccess.chooseSelectedField("mui-component-select-event", "http://example.com/ids-event:exhibition")
        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy("http://example.com/ids-event:exhibition")
        at ProvideAccess

    }

    def "Provide Access with Interval Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        provideAccess.chooseSelectedField("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")
        provideAccess.addComponent("Interval")

        Date startDate = new Date()
        String dataPart = startDate.format("ddMMyyyy")
        String timePart = startDate.format("HHmm")
        provideAccess.setCalenderField("restrictTimeIntervalStart", dataPart + "\t" + timePart)

        Calendar c = Calendar.getInstance()
        c.setTime(startDate)
        c.add(Calendar.HOUR, 1)
        Date endDate =  c.getTime()
        dataPart = endDate.format("ddMMyyyy")
        timePart = endDate.format("HHmm")
        provideAccess.setCalenderField("restrictTimeIntervalEnd", dataPart + "\t" + timePart)
        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        String startDateInst = startDate.format("yyyy-MM-dd")+ "T" + startDate.format("HH:mm") + "Z"
        String endDateInst = endDate.format("yyyy-MM-dd")+ "T" + endDate.format("HH:mm") + "Z"
        provideAccess.checkPolicy("\"@value\": \""+startDateInst+"\",")
        provideAccess.checkPolicy("\"@value\": \""+endDateInst+"\",")
        at ProvideAccess

    }

    def "Provide Access with Payment Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        String payment = "Rent"
        String price = "999"
        provideAccess.chooseSelectedField("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")
        provideAccess.addComponent("Payment")

        provideAccess.setInputField("price", price)
        provideAccess.chooseSelectedField("mui-component-select-payment", payment)
        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy(payment)
        provideAccess.checkPolicy(price)
        at ProvideAccess
    }

    def "Provide Access with Duration Component"() {
        given: "You are on the provide access page"
        to ProvideAccess
        report("Provide Access Page")

        ProvideAccess provideAccess = at ProvideAccess
        Date beginTime = new Date()
        String durationValue = "Rent"
        String unit = "Hours"
        provideAccess.chooseSelectedField("mui-component-select-consumer", "Consumer Party")
        provideAccess.setInputField("target", "http://example.com")
        provideAccess.addComponent("Specify a begin time")

        String dataPart = beginTime.format("ddMMyyyy")
        String timePart = beginTime.format("HHmm")
        provideAccess.setCalenderField("specifyBeginTime", dataPart + "\t" + timePart)
        provideAccess.setInputField("restrictTimeDuration", durationValue)
        provideAccess.chooseSelectedField("restrictTimeDurationUnit", unit)

        report("Enter some test values")

        when: "You press save"
        provideAccess.clickOnSave()

        then: "Check if the generated policy is correct"
        provideAccess.checkPolicy(durationValue+unit.getAt(0))
        //provideAccess.checkPolicy(unit)
        at ProvideAccess
    }
}

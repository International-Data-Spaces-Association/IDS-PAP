package pages

import pages.base.Base

class ProvideAccess extends Base {

    static url = "http://localhost:3000/policy/ProvideAccessPolicyForm"

    def setLocation(String str) {
        addComponent("Location")
        setInputField("location", str)
    }

    def selectSystem(String str) {
        addComponent("System")
        setSelectInput("mui-component-select-system", str)

    }

    def selectPurpose(String str) {
        addComponent("Purpose")
        setSelectInput("mui-component-select-purpose", str)
    }

    def selectEvent(String str) {
        addComponent("Event")
        setSelectInput("mui-component-select-event", str)
    }

    def setTimeInterval(Date startDate, Date endDate) {
        addComponent("Interval")
        String datePart = startDate.format("ddMMyyyy")
        String timePart = startDate.format("HHmm")
        setCalenderField("restrictTimeIntervalStart", datePart + "\t" + timePart)

        datePart = endDate.format("ddMMyyyy")
        timePart = endDate.format("HHmm")
        setCalenderField("restrictTimeIntervalEnd", datePart + "\t" + timePart)
    }
    def setPayment( String price, String payment) {
        addComponent("Payment")
        setInputField("price", price)
        setSelectInput("mui-component-select-payment", payment)
    }
    def setTimeDuration(Date beginTime, String durationValue, String unit){
        String dataPart = beginTime.format("ddMMyyyy")
        String timePart = beginTime.format("HHmm")
        addComponent("Specify a begin time")
        setCalenderField("specifyBeginTime", dataPart + "\t" + timePart)
        setInputField("restrictTimeDuration", durationValue)
        setSelectInput("mui-component-select-restrictTimeDurationUnit", unit)
    }

}
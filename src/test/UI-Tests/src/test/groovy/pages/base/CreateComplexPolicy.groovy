package pages.base

class CreateComplexPolicy extends  Base {
    static url = "http://localhost:3000/policy/ComplexPolicyForm"


    def addAllComponents() {
        addComponent("All")
    }

    def setLocation(String str) {
        addComponent("Restrict Location")
        setInputField("location", str)
    }

    def selectSystem(String str) {
        addComponent("Restrict System")
        setSelectInput("mui-component-select-system", str)

    }

    def selectPurpose(String str) {
        addComponent("Restrict Purpose")
        setSelectInput("mui-component-select-purpose", str)
    }

    def selectEvent(String str) {
        addComponent("Restrict Event")
        setSelectInput("mui-component-select-event", str)
    }

    def setTimeInterval(Date startDate, Date endDate) {
        addComponent("Restrict Time Interval")
        String datePart = startDate.format("ddMMyyyy")
        String timePart = startDate.format("HHmm")
        setCalenderField("restrictTimeIntervalStart", datePart + "\t" + timePart)

        datePart = endDate.format("ddMMyyyy")
        timePart = endDate.format("HHmm")
        setCalenderField("restrictTimeIntervalEnd", datePart + "\t" + timePart)
    }
    def setPayment( String price, String payment) {
        addComponent("Restrict Payment")
        setInputField("price", price)
        setSelectInput("mui-component-select-payment", payment)
    }

    def setTimeDuration(Date beginTime){
        String dataPart = beginTime.format("ddMMyyyy")
        String timePart = beginTime.format("HHmm")
        addComponent("Specify a begin time")
        setCalenderField("specifyBeginTime", dataPart + "\t" + timePart)
    }

    def setNumberOfUsage(String str) {
        addComponent("Restrict Number of Usage")
        setInputField("counter", str)
    }

    def setTimeDuration(String value, String unit) {
        addComponent("Restrict Begin and Duration")
        setInputField("restrictTimeDuration", value)
        setSelectInput("mui-component-select-restrictTimeDurationUnit", unit)
    }
}

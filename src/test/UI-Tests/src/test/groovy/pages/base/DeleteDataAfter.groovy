package pages.base

class DeleteDataAfter extends Base {
    static url = "http://localhost:3000/policy/DeletData"


    def addWaitBeforeDeleting (String value, String unit) {
        addComponent("Specify time to wait before deleting")
        setSelectInput("mui-component-select-timeUnit", unit)
        setInputField("time", value)
    }

    def addExactTimeAndDate (Date date) {
        addComponent("Specify exact time and date")
        String datePart = date.format("ddMMyyyy")
        String timePart = date.format("HHmm")
        setCalenderField("timeAndDate", datePart + "\t" + timePart)
    }

}

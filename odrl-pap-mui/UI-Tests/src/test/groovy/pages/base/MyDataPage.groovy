package pages.base

import geb.Page

abstract class MyDataPage extends Page{
    static ConfigObject config

    static at = {
        browser.getCurrentUrl().toLowerCase().startsWith(config.urlWithoutForwardSlash)
    }
    static content = {
    }

}

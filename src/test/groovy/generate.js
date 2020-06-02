

const groovyspock = function (scriptName){
    var _scriptName = scriptName  || "";
    const locatorType = {
        xpath: (target) => {
            return `by.xpath("${target.replace(/\"/g, "\'")}")`
        },
        css: (target) => {
            return `by.css("${target.replace(/\"/g, "\'")}")`
        },

        id: (target) => {
            return `by.id("${target.replace(/\"/g, "\'")}")`
        },

        link: (target) => {
            return `by.linkText("${target.replace(/\"/g, "\'")}")`
        },

        name: (target) => {
            return `by.name("${target.replace(/\"/g, "\'")}")`
        },

        tag_name: (target) => {
            return `by.tagName("${target.replace(/\"/g, "\'")}")`
        }
    }

    // https://seleniumhq.github.io/selenium/docs/api/javascript/module/selenium-webdriver/index_exports_Key.html
    const specialKeyMap = {
        '\${KEY_LEFT}': 'Key.ARROW_LEFT',
        '\${KEY_UP}': 'Key.ARROW_UP',
        '\${KEY_RIGHT}': 'Key.RIGHT',
        '\${KEY_DOWN}': 'Key.DOWN',
        '\${KEY_PAGE_UP}': 'Key.PAGE_UP',
        '\${KEY_PAGE_DOWN}': 'Key.PAGE_DOWN',
        '\${KEY_BACKSPACE}': 'Key.BACK_SPACE',
        '\${KEY_DEL}': 'Key.DELETE',
        '\${KEY_DELETE}': 'Key.DELETE',
        '\${KEY_ENTER}': 'Key.ENTER',
        '\${KEY_TAB}': 'Key.TAB',
        '\${KEY_HOME}': 'Key.HOME'
    }

    const seleneseCommands = {
        "open": "open('_TARGET_')",
        "click": "click(_BY_LOCATOR_)",
        "clickAndWait": "clickAndWait(_BY_LOCATOR_)",
        "doubleClick": "doubleClick(_BY_LOCATOR_)",
        "doubleClickAndWait": "doubleClickAndWait(_BY_LOCATOR_)",
        "type": "type(_BY_LOCATOR_,'_VALUE_')",
        "typeAndWait": "typeAndWait(_BY_LOCATOR_,_VALUE_)",
        "pause": "pause(_VALUE_)",
        "refresh": "refresh()",
        "selectWindow": "selectWindow()",
        "sendKeys": "sendKeys(_BY_LOCATOR_,_SEND_KEY_);",
        "sendKeysAndWait": "sendKeysAndWait(_BY_LOCATOR_,_SEND_KEY_);",
        "submit": "submit(_BY_LOCATOR_)",
        "submitAndWait": "submitAndWait(_BY_LOCATOR_)",
        "selectFrame":"selectFrame(_BY_LOCATOR_)",
        "select": "select(_BY_LOCATOR_,'option', '_SELECT_OPTION_')",
        "selectAndWait": "selectAndWait(_BY_LOCATOR_,'option', '_SELECT_OPTION_')",
        "goBack": "goBack()",
        "assertConfirmation": "assertConfirmation()",
        "verifyText": "verifyText(_BY_LOCATOR_,`_VALUE_STR_`)",
        "verifyTitle": "verifyTitle(`_VALUE_STR_`);",
        "verifyValue": "verifyValue(_BY_LOCATOR_,'value',`_VALUE_STR_`)",
        "assertText": "assertText(_BY_LOCATOR_,`_VALUE_STR_`)",
        "assertTitle": "assertTitle(`_VALUE_STR_`)",
        "assertValue": "assertValue(_BY_LOCATOR_,'value',`_VALUE_STR_`)",
        "waitForAlertNotPresent": "waitForAlertNotPresent()",
        "waitForAlertPresent": "waitForAlertPresent()",
        "waitForElementPresent": "waitForElementPresent(_BY_LOCATOR_)",
        "waitForElementNotPresent": "waitForElementNotPresent(_BY_LOCATOR_)",
        "waitForTextPresent": "waitForTextPresent(_BY_LOCATOR_,`_VALUE_STR_`)",
        "waitForTextNotPresent": "waitForTextNotPresent(_BY_LOCATOR_), `_VALUE_STR_`)",
        "waitForValue": "waitForValue(_BY_LOCATOR_,`_VALUE_STR_`)",
        "waitForNotValue": "waitForNotValue(_BY_LOCATOR_,`_VALUE_STR_`)",
        "waitForVisible": "waitForVisible(_BY_LOCATOR_)",
        "waitForNotVisible": "waitForNotVisible(_BY_LOCATOR_)",
    }

    const header =
        ""

    const footer =
        ""

    function commandExports(commands) {
        let output = commands.reduce((accObj, commandObj) => {
            let {command, target, value} = commandObj
            let cmd = seleneseCommands[command]
            if (typeof (cmd) == "undefined") {
                accObj.content += `\n\n\t// WARNING: unsupported command ${command}. Object= ${JSON.stringify(commandObj)}\n\n`
                return accObj
            }

            let funcStr = cmd;

            if (typeof (accObj) == "undefined") {
                accObj = {content: ""}
            }

            let targetStr = target.trim().replace(/\'/g, "\\'")
                .replace(/\"/g, '\\"')

            let valueStr = value.trim().replace(/\'/g, "\\'")
                .replace(/\"/g, '\\"')

            let selectOption = value.trim().split("=", 2)[1];

            let locatorStr = locator(target)

            funcStr = funcStr.replace(/_STEP_/g, accObj.step)
                .replace(/_TARGET_STR_/g, targetStr)
                .replace(/_BY_LOCATOR_/g, locatorStr)
                .replace(/_TARGET_/g, target)
                .replace(/_SEND_KEY_/g, specialKeyMap[value])
                .replace(/_VALUE_STR_/g, valueStr)
                .replace(/_VALUE_/g, value)
                .replace(/_SELECT_OPTION_/g, selectOption)

            accObj.step += 1
            accObj.content += `\t\t${funcStr}\n`

            return accObj
        }, {step: 1, content: ""})

        return output
    }

    function locator(target) {
        let locType = target.split("=", 1)

        let selectorStr = target.substr(target.indexOf("=") + 1, target.length)
        let locatorFunc = locatorType[locType]
        if (typeof (locatorFunc) == 'undefined') {
            return `by.xpath("${target.replace(/\"/g, "\'")}")`
            // return 'not defined'
        }

        return locatorFunc(selectorStr)
    }

    function formatter(commands) {
        return header.replace(/_SCRIPT_NAME_/g, _scriptName) +
            commandExports(commands).content +
            footer;
    }

    return {
        formatter,
        locator
    };
}

console.log(groovyspock)

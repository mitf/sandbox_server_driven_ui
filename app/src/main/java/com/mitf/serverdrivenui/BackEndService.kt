package com.mitf.serverdrivenui

interface BackEndService {
    fun getPage(path: String, parameters: Map<String, String>): String
}

class FakeBackEndService : BackEndService {
    var fullname: String? = null
    override fun getPage(path: String, parameters: Map<String, String>): String {
        return when (path) {
            "/" -> initialScreen
            "/check" -> secondScreen.also {
                fullname = parameters["first_name"] + " " + parameters["last_name"]
            }
            "/welcome" -> finalScreen
                .replace("##1##", fullname ?: "")
                .replace("##2##", parameters["first_check"] ?: "")
                .replace("##3##", parameters["second_check"] ?: "")
            else -> initialScreen
        }
    }
}

val initialScreen = """
        {
            "children" : [
                {
                    "viewtype" : "LOGIN_FORM",
                    "children" : [
                        {
                            "viewtype" : "TEXT",
                            "label" : "Login"
                        },
                        {
                            "viewtype" : "TEXT_FIELD",
                            "label" : "First",
                            "data" : "first_name"
                        }, {
                            "viewtype" : "TEXT_FIELD",
                            "label": "Last",
                            "data" : "last_name"
                        }
                    ],
                    "label" : "Submit",
                    "data" : "/check"
                }
            ]
        }
    """

val secondScreen = """
        {
            "children" : [
                {
                    "viewtype" : "TITLE",
                    "label" : "Form Header"
                },
                {
                    "viewtype" : "FORM",
                    "children" : [
                        {
                            "viewtype" : "TEXT",
                            "label" : "Checkboxes"
                        },
                        {
                            "viewtype" : "CHECKBOX",
                            "label" : "First",
                            "data" : "first_check"
                        }, {
                            "viewtype" : "CHECKBOX",
                            "label": "Last",
                            "data" : "last_check"
                        }
                    ],
                    "label" : "Submit",
                    "data" : "/welcome"
                }
            ]
        }
"""

val finalScreen = """
        {
            "children" : [
                {
                    "viewtype" : "TEXT",
                    "label" : "Finished"
                },
                {
                    "viewtype" : "TEXT",
                    "label" : "Full name: ##1##"
                },
                {
                    "viewtype" : "TEXT",
                    "label" : "First Checkbox: ##2##"
                }, {
                    "viewtype" : "TEXT",
                    "label": "Last Checkbox: ##3##"
                }
            ]
        }
"""

val maintenanceScreen = """
        {
            "children" : [
                {
                    "viewtype" : "MAINTENANCE",
                    "label" : "Mohon Maaf"
                    "sublabel" : "Server Sedang Manintenance."
                },
            ]
        }
"""
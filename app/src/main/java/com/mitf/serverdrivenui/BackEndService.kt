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
                fullname = parameters["username"] + " " + parameters["radio"].toString()
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
                            "classType" : [],
                            "viewtype" : "TEXT_FIELD",
                            "label" : "Example Input Field",
                            "data" : "username",
                            "default" : "RESPONSE",
                            "validation" : [
                                "required",
                                "min:3",
                                "max:10"
                            ]
                        }, {
                            "classType" : [
                                "password"
                            ],
                            "viewtype" : "TEXT_FIELD",
                            "label": "Example Password",
                            "data" : "password",
                            "validation" : [
                                "required",
                                "max:16"
                            ]
                        },
                        {
                            "viewtype" : "RADIO",
                            "label" : "Example Radio",
                            "default" : "true",
                            "data" : "radio",
                            "options" : [
                                {
                                    "key" : "Ya",
                                    "value" : "true"
                                },
                                {
                                    "key" : "Tidak",
                                    "value" : "false"
                                }
                            ]
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
                            "viewtype" : "CHECKBOX",
                            "label" : "Checkboxes",
                            "options" : [
                                {
                                    "key" : "Check A",
                                    "value" : "true"
                                },
                                {
                                    "key" : "Check B",
                                    "value" : "false"
                                }
                            ]
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
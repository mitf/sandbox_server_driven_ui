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
                    "viewtype" : "FORM",
                    "children" : [
                        {
                            "viewtype" : "TEXT",
                            "label" : "Login"
                        },
                        {
                            "viewtype" : "TEXT_ROW",
                            "children" : [
                                {
                                    "classType" : [
                                        "singleline",
                                        "text",
                                        "width:80"
                                    ],
                                    "viewtype" : "TEXT_ROW_FIELD",
                                    "label" : "Example Row Number 1",
                                    "data" : "Number Field",
                                    "default" : "",
                                    "width":[
                                    ],
                                    "placeholder": "Area Phone",
                                    "validation" : [
                                        "required",
                                        "max:16"
                                    ]
                                },
                                {
                                    "classType" : [
                                        "singleline",
                                        "number",
                                        "width:260"
                                    ],
                                    "viewtype" : "TEXT_ROW_FIELD",
                                    "label" : "Example Row Number 2",
                                    "data" : "Number Field",
                                    "default" : "",
                                    "placeholder": "tester12345678",
                                    "validation" : [
                                        "required",
                                        "max:16"
                                    ]
                                }
                            ]
                        },
                        {
                            "classType" : [
                                "multiline"
                            ],
                            "viewtype" : "TEXT_FIELD",
                            "label" : "Example Input Field",
                            "data" : "username",
                            "default" : "",
                            "placeholder": "Example: user_1, user.2, user@3",
                            "validation" : [
                                "required",
                                "min:6",
                                "max:10"
                            ]
                        },
                        {
                            "classType" : [
                                "singleline"
                            ],
                            "viewtype" : "TEXT_FIELD",
                            "label" : "Example Input Field",
                            "data" : "username new",
                            "default" : "",
                            "placeholder": "Example: user_1, user.2, user@3",
                            "validation" : [
                                "required",
                                "min:6",
                                "max:10"
                            ]
                        },
                        {
                            "classType" : [
                                "password"
                            ],
                            "viewtype" : "TEXT_FIELD",
                            "label": "Example Password",
                            "data" : "password",
                            "placeholder": "Input password",
                            "validation" : [
                                "required",
                                "max:16"
                            ]
                        },
                        {
                            "classType" : [
                                "singleline",
                                "number"
                            ],
                            "viewtype" : "TEXT_FIELD",
                            "label" : "Example Number Field",
                            "data" : "Number Field",
                            "default" : "",
                            "placeholder": "123456789",
                            "validation" : [
                                "required",
                                "max:16"
                            ]
                        },
                        {
                            "classType" : [
                                "singleline",
                                "currency"
                            ],
                            "viewtype" : "TEXT_FIELD_CURRENCY",
                            "label" : "Example Currency Field",
                            "data" : "Number Field",
                            "default" : "",
                            "placeholder": "Rp10.000",
                            "validation" : [
                                "required",
                                "max:9"
                            ]
                        },
                        {
                            "classType" : [],
                            "viewtype" : "TEXT_FIELD_SELECTOR",
                            "label": "Example Selector 1",
                            "placeholder": "Please select Example Selector",
                            "data" : "selector1",
                            "validation" : [
                                "required"
                            ]
                        },
                        {
                            "classType" : [],
                            "viewtype" : "TEXT_FIELD_SELECTOR",
                            "label": "Example Selector 2",
                            "placeholder": "Please select Example Selector",
                            "data" : "selector2",
                            "validation" : [
                                "required"
                            ]
                        },
                        {
                            "viewtype" : "RADIO",
                            "label" : "Example Radio",
                            "default" : "",
                            "data" : "radio",
                            "options" : [
                                {
                                    "key" : "Ya",
                                    "value" : "1"
                                },
                                {
                                    "key" : "Tidak",
                                    "value" : "2"
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
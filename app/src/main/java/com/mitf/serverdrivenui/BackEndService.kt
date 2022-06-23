package com.mitf.serverdrivenui

interface BackEndService {
    fun getPage(path: String, parameters: Map<String, String>): String
}

class FakeBackEndService : BackEndService {
    var fullname: String? = null
    override fun getPage(path: String, parameters: Map<String, String>): String {
        return when (path) {
            "/" -> newScreen
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
val newScreen = """
    {
    "ui_version": "1.0.0",
    "ui_name": "From Data",
    "data": {
      "date_created": "25 Juni 2022",
      "avatar": "",
      "branch_code": "",
      "email": "superadminsally@finansia.com",
      "employee_id": "",
      "employee_name": "Superadmin",
      "first_name": "Superadmin",
      "id": 1,
      "is_activition": false,
      "name": "Joko",
      "permissions": [
        "create-permission",
        "create-role",
        "create-user",
        "delete-permission",
        "delete-role",
        "delete-user",
        "edit-permission",
        "edit-role",
        "edit-user",
        "view-permission",
        "view-role",
        "view-user"
      ],
      "phone": "",
      "roles": [
        "superadmin"
      ],
      "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxIiwiZXhwIjoxNjU1Nzk0MzcxLCJzdWIiOiIxIn0.tOn3fJ5qGdiPjF6klLsqO6oWWlLmlxRkxX7LmNp2bQtVViLVKlbR9Qy4673aobZuEbYXYCYMNzYAoJMK4PxNCA",
      "user_id": 1,
      "username": "superadminsally"
    },
    "status_code": 200,
    "status_desc": "OK",
    "message": "Success",
    "errors": null,
    "ui_components": [
      {
        "label": "INPUT BIODATA",
        "type": "HEADER",
        "url": "",
        "method": "",
        "attributes": [
          "header",
          "title"
        ],
        "ui_components": [
          {
            "attributes": [
              "text",
              "place_holder: 23 Juni 2022",
              "singleline",
              "disable"
            ],
            "type": "TEXT_BOX",
            "slug": "date_created",
            "label": "Tanggal Dibuat",
            "options": [],
            "validation": [
              "required"
            ]
          },
          {
            "attributes": [
              "text",
              "place_holder: Contoh: Budi",
              "singleline"
            ],
            "type": "TEXT_BOX",
            "slug": "name",
            "label": "Name",
            "options": [],
            "validation": [
              "required",
              "min:0",
              "max:30"
            ]
          },
          {
            "attributes": [
              "number",
              "place_holder: Contoh: 08111111111",
              "singleline"
            ],
            "type": "TEXT_BOX",
            "slug": "phone_number",
            "label": "Nomor Handphone",
            "options": [],
            "validation": [
              "required",
              "min:10",
              "max:12"
            ]
          },
          {
            "attributes": [
              "email",
              "place_holder: Contoh: budi@email.com",
              "singleline"
            ],
            "type": "TEXT_BOX",
            "slug": "email",
            "label": "Email",
            "options": [],
            "validation": [
              "required",
              "min:10",
              "max:50"
            ]
          },
          {
            "attributes": [
              "place_holder: Alamat",
              "singleline",
              "currency"
            ],
            "type": "TEXT_BOX",
            "slug": "address",
            "label": "Alamat Domisili",
            "options": [],
            "validation": [
              "required",
              "min:3",
              "max:50"
            ]
          },
          {
            "attributes": [
              "place_holder: Pilih Profesi/Pekerjaan",
              "singleline"
            ],
            "type": "TEXT_BOX_SELECTOR",
            "slug": "profession",
            "label": "Profesi/Pekerjaan",
            "options": [
                {
                    "id":"1",
                    "value":"value1"
                },
                {
                    "id":"2",
                    "value":"value2"
                }
            ],
            "validation": [
              "required",
              "min:0",
              "max:50"
            ]
          },
          {
            "attributes": [
              "place_holder: Pilih Tipe Lead",
              "singleline",
              "disable"
            ],
            "type": "TEXT_BOX_SELECTOR",
            "slug": "lead_type",
            "label": "Tipe Lead",
            "options": [
                {
                    "id":"TLO",
                    "value":"TLO"
                },
                {
                    "id":"ALL-RISK",
                    "value":"ALL-RISK"
                }
            ],
            "validation": [
              "required",
              "min:0",
              "max:50"
            ]
          },
          {
            "attributes": [
              "place_holder: Pilih Sumber Lead",
              "multiline"
            ],
            "type": "TEXT_BOX_SELECTOR",
            "slug": "lead_source",
            "label": "Sumber Lead",
            "options": [
                {
                    "id":"1",
                    "value":"value21"
                },
                {
                    "id":"2",
                    "value":"value22"
                }
            ],
            "validation": [
              "required",
              "min:0",
              "max:50"
            ]
          },
          {
            "attributes": [
              "place_holder:Mohon berikan catatan jika ada tambahan informasi",
              "multiline"
            ],
            "type": "TEXT_BOX_MULTILINE",
            "slug": "note",
            "label": "Catatan",
            "options": [],
            "validation": [
              "required",
              "min:0",
              "max:200"
            ]
          }
        ]
      }
    ]
} 
"""
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
                            "label" : "Example Input Field Username",
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
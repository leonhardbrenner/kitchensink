package workflows

import schema.Workflow

val printer = Workflow("PrintWorkflow", "INITIAL") {
    State(
        "INITIAL"
    ) {
        Activate("com...[some external code dealing with the activation of a state]")
        Deactivate("com...[some external code dealing with the activation of a state]")
        Label("ACTIVATE", "") {
            Transition("PRINT", "this could be the conditional")
        }
    }
    State(
        "PRINT"
    ) {
        Activate("com...")
        Deactivate("com...")
        Label(
            "PRINT_MGR_EXCEPTION",
            ""
        ) {
            Transition(
                "DENY",
                "com...[some external code to decide whether to transition]"
            )
        }
        Label(
            "PRINT_FAILURE",
            "com...[some external code to decide which to transition to make."
        ) {
            Transition("DENY", "")
            Transition("SOME_OTHER_STATE", "")
        }
        Label(
            "PRINT_SUCCESFUL_ORMD",
            ""
        ) {
            Transition("ORMD", "")
        }
    }
    State(
        "ORMD"
    ) {
        Activate("")
        Deactivate("")
        Label(
            "CONTINUE_BUTTON",
            ""
        ) {
            Transition("RACK", "")
        }
    }
    State(
        "RACK"
    ) {
        Activate("")
        Deactivate("")
        Label(
            "REPRINT_STICKER_BUTTON",
            ""
        ) {
            Transition("PRINT", "")
        }
    }
    State(
        "SOME_OTHER_STATE"
    ) {
        Activate("")
        Deactivate("")
    }
    State(
        "DENY"
    ) {
        Activate("")
        Deactivate("")
    }
}
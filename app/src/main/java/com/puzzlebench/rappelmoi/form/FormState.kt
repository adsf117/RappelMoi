package com.puzzlebench.rappelmoi.form


import com.puzzlebench.rappelmoi.database.Event

sealed class FormState  {
    object ShowEmptyNameError : FormState()
    object ShowInvalidDateError : FormState()
    class SaveSuccessFull(val event: Event) : FormState()
    class SaveSuccessFullUseWorker(val event: Event) : FormState()
    class ShowMessage(val message: String) : FormState()
}
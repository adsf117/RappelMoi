package com.puzzlebench.rappelmoi.form

sealed class FormState {
    object ShowEmptyNameError : FormState()
    object ShowInvalidDateError : FormState()
    object SaveSuccessFull : FormState()
    class ShowMessage(val message: String) : FormState()
}
package com.example.feature.profile.api.action


sealed class UpdateProfileAction {
    data class UpdateEmailField(val input: String): UpdateProfileAction()
    data class UpdateBioField(val input: String): UpdateProfileAction()

    data class UpdatePhotoVisibilityField(val input: String): UpdateProfileAction()
    data class UpdateSavedPhotoVisibilityField(val input:String):UpdateProfileAction()
    data class UpdateWalkVisibilityField(val input: String): UpdateProfileAction()

    data class UpdateFirstNameField(val input: String): UpdateProfileAction()
    data class UpdateLastNameField(val input: String): UpdateProfileAction()

    data object SubmitSaveButton: UpdateProfileAction()
}
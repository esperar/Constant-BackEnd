package esperer.constant.global.error

import esperer.constant.common.error.ErrorProperty
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

data class ErrorResponse(
    val status: Int,
    val message: String
) {
    companion object {
        fun of(errorProperty: ErrorProperty) = ErrorResponse(
            errorProperty.status(),
            errorProperty.message()
        )

        fun of(e: BindingResult): ValidationErrorResponse {
            val errorMap = HashMap<String, String?>()

            for (error: FieldError in e.fieldErrors) {
                errorMap[error.field] = error.defaultMessage
            }

            return ValidationErrorResponse(
                status = GlobalErrorCode.BAD_REQUEST.status(),
                fieldError = errorMap
            )
        }

        fun of(e: DataIntegrityViolationException): DataErrorResponse =
            DataErrorResponse(
                status = GlobalErrorCode.BAD_REQUEST.status(),
                message = e.message.toString()
            )
    }
}

data class ValidationErrorResponse(
    val status: Int,
    val fieldError: Map<String, String?>
)

data class DataErrorResponse(
    val status: Int,
    val message: String
)
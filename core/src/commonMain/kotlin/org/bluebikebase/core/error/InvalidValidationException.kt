package terakoyalabo.core.error

class InvalidValidationException(
    override val message: String,
    override val cause: Throwable? = null,
) : CoreDomainException(message = message, cause = cause)

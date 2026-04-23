package terakoyalabo.core.error

class LawOfTerakoyaException(
    override val message: String,
    override val cause: Throwable? = null
) : CoreDomainException(message = message, cause = cause)

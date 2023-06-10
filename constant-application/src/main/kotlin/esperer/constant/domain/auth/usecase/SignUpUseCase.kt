package esperer.constant.domain.auth.usecase

import esperer.constant.common.annotation.UseCase
import esperer.constant.common.service.SecurityService
import esperer.constant.domain.auth.dto.request.SignUpRequest
import esperer.constant.domain.auth.model.Authority
import esperer.constant.domain.user.model.User
import esperer.constant.domain.user.spi.CommandUserPort
import java.util.*

@UseCase
class SignUpUseCase(
    private val commandUserPort: CommandUserPort,
    private val securityService: SecurityService
) {

    fun execute(request: SignUpRequest){

        val user = User(
            id = UUID.randomUUID(),
            email = request.email,
            password = securityService.encodePassword(request.password),
            name = request.name,
            profileFileName = request.profileFileName ?: "",
            authority = Authority.ROLE_USER
        )

        commandUserPort.save(user)
    }
}
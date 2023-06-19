package esperer.constant.domain.auth.usecase

import esperer.constant.common.service.SecurityService
import esperer.constant.domain.auth.dto.request.SignUpRequest
import esperer.constant.domain.auth.model.Authority
import esperer.constant.domain.user.model.User
import esperer.constant.domain.user.service.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
internal class SignUpUseCaseTest {

    @MockBean
    private lateinit var securityService: SecurityService

    @MockBean
    private lateinit var userService: UserService


    private lateinit var signUpUseCase: SignUpUseCase

    private val email = "test1@gmail.com"
    private val name = "esperer"
    private val profileFileName = "profileFileName"

    private val saveUserStub: User by lazy {
        User(
            id = UUID.randomUUID(),
            email = email,
            password = "encoded_test_password",
            name = name,
            profileFileName = profileFileName,
            authority = Authority.ROLE_USER,
            sprintId = null
        )
    }

    private val requestStub: SignUpRequest by lazy {
        SignUpRequest(
            email = email,
            password = "test_password",
            name = name,
            profileFileName = profileFileName
        )
    }

    @BeforeEach
    fun setUp(){
        signUpUseCase = SignUpUseCase(
            userService,
            securityService
        )
    }

    @Test
    fun `회원가입 성공`(){
        // given
        val userStub = User(
            id = UUID.randomUUID(),
            email = email,
            password = "encoded_test_password",
            name = name,
            profileFileName = profileFileName,
            authority = Authority.ROLE_USER,
            sprintId = null
        )

        given(userService.existsByEmail(requestStub.email)).willReturn(false)

        given(securityService.encodePassword(requestStub.password)).willReturn(userStub.password)

        given(userService.save(userStub)).willReturn(userStub)

        // then
        signUpUseCase.execute(requestStub)

        // when
    }
}
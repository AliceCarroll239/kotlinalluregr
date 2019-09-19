import data.NewUserRequest
import interfaces.NewUserApi
import kotlinx.coroutines.*
import utils.TestUtils
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList
import kotlin.random.Random

data class User(
    val email: String,
    val password: String
)

fun main() {
    val userList = Collections.synchronizedList(ArrayList<User>())
    val counter = AtomicInteger()
    runBlocking {
        val generator = (1..50).map { _ ->
            GlobalScope.launch {
                val newUser = createUser("url")
                userList.add(newUser)
                println("New user " + counter.incrementAndGet().toString() + " " + newUser!!.email + " with password " + newUser!!.password)
            }
        }
        generator.forEach {
            it.join()
        }
    }
}

suspend fun createUser(standURL: String): User? = withContext(Dispatchers.IO) {
    val newUserPrefix = "autotest"
    val newUserEmailPostfix = "@gmail.com"
    val newUserSalt = generateRandomString(10)
    val newUserEmail = "$newUserPrefix$newUserSalt$newUserEmailPostfix"
    val newUserName = "$newUserPrefix$newUserSalt".capitalize()

    val request = TestUtils().retrofitBuilder(standURL)
        .create(NewUserApi::class.java)
        .postMethod(NewUserRequest(email = newUserEmail, name = newUserName)).execute()
    return@withContext if (request.code() == 200) {
        User(request.body()!!.email, request.body()!!.password)
    } else null
}

fun generateRandomString(randomStringLength: Int): String {
    val charPool: List<Char> = ('a'..'z').toList()
    return (1..randomStringLength)
        .map { _ -> Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}

import com.example.module
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.Test
import kotlin.test.assertEquals

class PlaceRouteTests {
    @Test
    fun testGetPlace() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/place/2").apply {
                assertEquals(
                    "{\"id\":\"2\",\"name\":\"Исаакиевский собор\",\"description\":\"Большой и красивый собор с парком и площадью рядом\",\"visitCounter\":\"999\",\"placeImage\":\"img2\",\"latitude\":\"59.933792\",\"longitude\":\"30.306833\"}",
                    response.content
                )
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testGetPlaces() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/place").apply {
                assertEquals(
                    "[{\"id\":\"2\",\"name\":\"Исаакиевский собор\",\"description\":\"Большой и красивый собор с парком и площадью рядом\",\"visitCounter\":\"999\",\"placeImage\":\"img2\",\"latitude\":\"59.933792\",\"longitude\":\"30.306833\"},{\"id\":\"3\",\"name\":\"Сенная площадь\",\"description\":\"Большая площадь с красивым видом, магазинами и кафе\",\"visitCounter\":\"501\",\"placeImage\":\"img3\",\"latitude\":\"59.927016\",\"longitude\":\"30.319184\"},{\"id\":\"4\",\"name\":\"ГУАП\",\"description\":\"Чесменский дворец, страшное место, не советуем туда ходить\",\"visitCounter\":\"0\",\"placeImage\":\"img4\",\"latitude\":\"59.857597\",\"longitude\":\"30.327792\"}]",
                    response.content
                )
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testDeletePlace() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Delete, "/place/1").apply {
                assertEquals(
                    "Place removed correctly",
                    response.content
                )
                assertEquals(HttpStatusCode.Accepted, response.status())
            }
        }
    }

    @Test
    fun testCreatePlace() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/place/{{\"id\":\"228\",\"name\":\"Сад\",\"description\":\"Палевский\",\"visitCounter\":\"1222\",\"placeImage\":\"img1\",\"latitude\":\"59.893228\",\"longitude\":\"30.417227\", \"city\":\"Saint Petersburg\"}}").apply {
                assertEquals(
                    "Place stored correctly",
                    response.content
                )
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }
}
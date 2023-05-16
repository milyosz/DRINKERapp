
import com.example.drinkerapp.pojo.CategoryList
import retrofit2.Call
import retrofit2.http.GET

interface CatAPI {

    @GET("/")
    fun getCategories() : Call<CategoryList>
}
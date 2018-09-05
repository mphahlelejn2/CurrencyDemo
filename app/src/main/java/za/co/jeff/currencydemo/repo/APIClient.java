package za.co.jeff.currencydemo.repo;


import io.reactivex.Maybe;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import za.co.jeff.currencydemo.models.ServerRespond;

public interface APIClient {

    @GET(UrlManager.CODE_LIST_AND_DESCRIPTION)
    Maybe<Response<ResponseBody>> onlineCurrencyListAndDescriptions();

    @GET(UrlManager.API_END_POINT)
    Maybe<ServerRespond> onlineCurrencyValues(@Query("app_id") String app_id);

}

package za.co.jeff.currencydemo.repo;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import za.co.jeff.currencydemo.models.ServerRespond;

public interface IOnlineRepository {
    Maybe<Response<ResponseBody>> onlineCurrencyListAndDescriptions();
    Maybe<ServerRespond> onlineCurrencyValues(String api_key);

}

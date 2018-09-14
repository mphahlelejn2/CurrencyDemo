package za.co.jeff.currencydemo.repo;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import za.co.jeff.currencydemo.models.ServerRespond;

public class OnlineRepositoryImpl implements IOnlineRepository {

    private APIClient apiClient;

    public OnlineRepositoryImpl(APIClient apiClient) {
        this.apiClient=apiClient;
    }

    @Override
    public  Maybe<Response<ResponseBody>> getListOfCurrencyAndDescriptionsFromOnline() {
        return apiClient.onlineCurrencyListAndDescriptions();
    }

    @Override
    public Maybe<ServerRespond> getOnlineCurrencyValues(String api_key) {
        return apiClient.onlineCurrencyValues(api_key);
    }
}

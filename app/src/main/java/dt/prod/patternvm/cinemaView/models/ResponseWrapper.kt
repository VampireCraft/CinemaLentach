package dt.prod.patternvm.cinemaView.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseWrapper<T> : Serializable {
    val status: String? = null

    @SerializedName("results")
    val data: T? = null

    @SerializedName("status_id")
    val statusId: Int? = null

    @SerializedName("has_more")
    val more: Boolean? = null

    @SerializedName("num_results")
    val numResults: Int? = null

    @SerializedName("faultstring")
    val error: String? = null
}

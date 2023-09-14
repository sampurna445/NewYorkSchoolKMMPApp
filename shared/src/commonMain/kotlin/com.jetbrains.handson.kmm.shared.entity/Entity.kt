import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SchoolsListData(
    @SerialName("school_name")
    val schoolName: String? = "",
    @SerialName("city")
    val city: String? = "",
    @SerialName("dbn")
    val dbn: String? = ""

)

@Serializable
data class SchoolSATsData(
    @SerialName("dbn")
    val dbn: String,
    @SerialName("school_name")
    val schoolName: String,
    @SerialName("num_of_sat_test_takers")
    val numOfSATTakers: String,
    @SerialName("sat_critical_reading_avg_score")
    val satReadingAvgScore: String,
    @SerialName("sat_writing_avg_score")
    val satWritingAvgScore: String,
    @SerialName("sat_math_avg_score")
    val satMathAvgScore: String
)

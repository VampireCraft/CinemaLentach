package dt.prod.patternvm.cinemaView.models

import com.google.gson.annotations.SerializedName

class CinemaOffsetModel(
    var display_title: String,
    var mpaa_rating: String,
    var critics_pick: Int?,
    var byline: String,
    var headline: String,
    var summary_short: String,
    var publication_date: String,
    var opening_date: String,
    var date_updated: String,
    var link: LinkModel?,
    var media: MediaModel?

    ) {
    constructor() : this(
        "",
        "",
        null,
        "",
        "",
        "",
        "",
        "",
        "",
        null,
        null
    )
}

class LinkModel(
    var type: String,
    var url: String,
    var suggested_link_text: String
) {
    constructor():this(
        "",
        "",
        ""
    )
}

class MediaModel(
    var type: String,
    var src: String,
    var height: Int,
    var width: Int
)
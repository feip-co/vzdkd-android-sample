package co.feip.vezdecode.data.datasource

import co.feip.vezdecode.data.models.Tag

interface TagsDataSource {

    suspend fun getTags(): List<Tag>

}
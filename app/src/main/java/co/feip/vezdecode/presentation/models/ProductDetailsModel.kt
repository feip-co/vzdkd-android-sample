package co.feip.vezdecode.presentation.models

sealed class ProductDetailsModel {

    data class Header(
        val name: String,
        val description: String
    ) : ProductDetailsModel()

    data class EnergyValue(
        val title: String,
        val value: String
    ) : ProductDetailsModel()

}

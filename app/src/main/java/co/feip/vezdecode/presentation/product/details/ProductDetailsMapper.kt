package co.feip.vezdecode.presentation.product.details

import co.feip.vezdecode.R
import co.feip.vezdecode.ResourceManager
import co.feip.vezdecode.data.models.Product
import co.feip.vezdecode.presentation.models.ProductDetailsModel

class ProductDetailsMapper(
    private val resourceManager: ResourceManager
) {

    fun map(product: Product): List<ProductDetailsModel> =
        listOf(
            ProductDetailsModel.Header(product.name, product.description),
            ProductDetailsModel.EnergyValue(
                resourceManager.getString(R.string.label_weight),
                "${product.measure} ${product.measureUnit}"
            ),
            ProductDetailsModel.EnergyValue(
                resourceManager.getString(R.string.label_energy_value),
                resourceManager.getString(
                    R.string.label_energy_value_measure,
                    product.energyPer100Grams
                )
            ),
            ProductDetailsModel.EnergyValue(
                resourceManager.getString(R.string.label_energy_proteins),
                resourceManager.getString(
                    R.string.label_energy_proteins_measure,
                    product.proteinsPer100Grams
                )
            ),
            ProductDetailsModel.EnergyValue(
                resourceManager.getString(R.string.label_energy_fats),
                resourceManager.getString(
                    R.string.label_energy_fats_measure,
                    product.fatsPer100Grams
                )
            ),
            ProductDetailsModel.EnergyValue(
                resourceManager.getString(R.string.label_energy_carbohydrates),
                resourceManager.getString(
                    R.string.label_energy_carbohydrates_measure,
                    product.carbohydratesPer100Grams
                )
            ),
        )
}
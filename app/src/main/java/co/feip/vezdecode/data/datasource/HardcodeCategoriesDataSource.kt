package co.feip.vezdecode.data.datasource

import co.feip.vezdecode.data.models.Category

class HardcodeCategoriesDataSource : CategoriesDataSource {

    override suspend fun getCategories(): List<Category> = listOf(
        Category(1, "Горячие блюда"),
        Category(2, "Суши"),
        Category(3, "Соусы"),
        Category(4, "Детское меню"),
        Category(5, "Подарочные сертификаты"),
        Category(6, "Напитки"),
        Category(7, "Горячие закуски"),
        Category(8, "Готовим дома"),
        Category(9, "Средства индивидуальной защиты"),
        Category(10, "Салаты")
    )
}
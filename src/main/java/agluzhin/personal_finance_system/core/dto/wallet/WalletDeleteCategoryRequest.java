package agluzhin.personal_finance_system.core.dto.wallet;

/**
 * DTO для запроса на удаление категории из кошелька.
 * @param userId уникальное значение, присвоенное пользователю при создании;
 * @param categoryType тип категории: ДОХОДЫ/РАСХОДЫ;
 * @param categoryName желаемое название категории;
 */
public record WalletDeleteCategoryRequest(
        String userId,
        String categoryType,
        String categoryName
) {
}
